package fr.eni.trocencheres.dal.jdbc;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bo.Article;
import fr.eni.trocencheres.bo.Enchere;
import fr.eni.trocencheres.dal.ConnectionProvider;
import fr.eni.trocencheres.dal.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EnchereDAOJdbcImpl implements DAO<Enchere> {

    private static final BusinessException businessException = new BusinessException();

    private static final String SELECTALL = "SELECT * FROM ENCHERES";
    private static final String UPDATE = "UPDATE ENCHERES SET no_utilisateur = ?, date_enchere = GETDATE(), montant_enchere = ?, etat_enchere = 'NonVendu', der_ench = 1 WHERE no_article = ?";
    private static final String INSERT = "INSERT INTO ENCHERES(no_utilisateur, no_article, date_enchere, montant_enchere, etat_enchere, der_ench) VALUES(?,?,GETDATE(),?,'NonVendu', 1)";
    private static final String SELECT_BY_ACQ_ET_ART_VENDU ="SELECT * FROM ENCHERES WHERE (no_acquereur=? and etat_enchere='Vendu')";
    private static final String SELECT_BY_ID_UTIL_AND_ID_ART = "SELECT date_enchere, montant_enchere, etat_enchere, no_acquereur FROM ENCHERES WHERE no_article = ? AND no_utilisateur = ?";
    private static final String SELECT_BY_ID_ART = "SELECT no_utilisateur, montant_enchere, der_ench FROM ENCHERES WHERE no_article = ? AND etat_enchere = 'NonVendu' AND der_ench = 1";
    private static final String SELECT_BY_ID_UTILISATEUR = "SELECT credit FROM UTILISATEURS WHERE no_utilisateur = ?";
    private static final String UPDATE_UTILISATEUR_CREDIT = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
    private static final String SELECT_BEFORE_UPDATE = "SELECT * FROM ENCHERES WHERE no_utilisateur = ? AND no_article = ? AND der_ench = 1";
    private static final String UPDATE_DER_ENCH = "UPDATE ENCHERES SET der_ench = 0 WHERE no_article = ? AND no_utilisateur = ?";

    @Override
    public List<Enchere> selectAll() throws BusinessException {
        List<Enchere> listEnchere = new ArrayList<>();
        Enchere enchere = new Enchere();
        try (
                Connection cnx = ConnectionProvider.getConnection();
                Statement stt = cnx.createStatement()
        ) {

            ResultSet rs = stt.executeQuery(SELECTALL);
            while (rs.next()) {
                enchere.setIdUtilisateur(   rs.getInt("no_utilisateur"));
                enchere.setIdArticle(rs.getInt("no_article"));
                enchere.setDateEnchere( rs.getTimestamp("date_enchere"));
                enchere.setMontantEnchere( rs.getInt("montant_enchere"));
                enchere.setEtatEnchere(rs.getString("etat_enchere"));
                enchere.setNoAcquereur( rs.getInt("no_acquereur"));

                listEnchere.add(enchere);
            }
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.IMPORT_DAL_ENCHERE);
            throw new BusinessException();
        }
        return listEnchere;
    }

    @Override
    public Enchere selectById(int id) throws BusinessException {
        return null;
    }

    @Override
    public void insert(Enchere object) throws BusinessException {

    }

    /**
     * INSERT dans la table ENCHERES car la clé est la combinaison de Utilisateur, Article et Montant enchère
     * @param enchere
     * @throws BusinessException
     */
    @Override
    public void update(Enchere enchere) throws BusinessException {
        int pointsEnchere = 0;
        int pointsUtilisateurAvant = 0;
        int idEncherisseur = 0;
        int pointsUtilisateurActuel = 0;
        PreparedStatement pstmt2 = null;

        try (Connection cnx = ConnectionProvider.getConnection()) {
            try {
                //Récupérer la plus grosse enchère en cours via no_Article
                PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID_ART);
                pstmt.setInt(1, enchere.getIdArticle());
                ResultSet rs = pstmt.executeQuery();
                //Si enchère existe
                if (rs.next()) {
                    idEncherisseur = rs.getInt("no_utilisateur");
                    pointsEnchere = rs.getInt("montant_enchere");
                }
                //Si il y a déjà eu une enchère
                if(idEncherisseur != 0) {
                    //si l'utilisateur n'est pas déjà le dernier enchérisseur
                    if (idEncherisseur != enchere.getNoAcquereur()) {
                        //Récupère les points actuels du + gros enchèreur
                        PreparedStatement pstmt1 = cnx.prepareStatement(SELECT_BY_ID_UTILISATEUR);
                        pstmt1.setInt(1, idEncherisseur);
                        ResultSet rs1 = pstmt1.executeQuery();
                        if (rs1.next()) {
                            pointsUtilisateurAvant = rs1.getInt("credit");
                        }
                        cnx.setAutoCommit(false);
                        //Insertion de la nouvelle enchère(celle-ci est composée de no_util, no_art et montant_enchere)
                        pstmt2 = cnx.prepareStatement(INSERT);
                        pstmt2.setInt(1, enchere.getIdUtilisateur());
                        pstmt2.setInt(2, enchere.getIdArticle());
                        pstmt2.setInt(3, enchere.getMontantEnchere());
                        pstmt2.executeUpdate();
                        //Restituer les points à l'enchérisseur d'avant
                        PreparedStatement pstmt3 = cnx.prepareStatement(UPDATE_UTILISATEUR_CREDIT);
                        pstmt3.setInt(1, (pointsEnchere + pointsUtilisateurAvant));
                        pstmt3.setInt(2, idEncherisseur);
                        pstmt3.executeUpdate();
                        //Récupération des points de l'utilisateur voulant enchérir
                        PreparedStatement pstmt4 = cnx.prepareStatement(SELECT_BY_ID_UTILISATEUR);
                        pstmt4.setInt(1, enchere.getIdUtilisateur());
                        ResultSet rs2 = pstmt4.executeQuery();
                        if (rs2.next()) {
                            pointsUtilisateurActuel = rs2.getInt("credit");
                        }
                        //Retirer les points de l'enchérisseur actuel
                        PreparedStatement pstmt5 = cnx.prepareStatement(UPDATE_UTILISATEUR_CREDIT);
                        pstmt5.setInt(1, (pointsUtilisateurActuel - enchere.getMontantEnchere()));
                        pstmt5.setInt(2, enchere.getIdUtilisateur());
                        pstmt5.executeUpdate();
                        //Mettre à jour l'ancienne enchère pour lui enlever le statut de dernière enchère
                        PreparedStatement pstmt6 = cnx.prepareStatement(UPDATE_DER_ENCH);
                        pstmt6.setInt(1, enchere.getIdArticle());
                        pstmt6.setInt(2, idEncherisseur);
                        pstmt6.executeUpdate();
                        //Fermeture
                        pstmt1.close();
                        pstmt3.close();
                        pstmt4.close();
                        pstmt5.close();
                        pstmt6.close();
                        rs1.close();
                        rs2.close();
                        cnx.commit();
                        cnx.setAutoCommit(true);
                    } else {
                        //L'enchérisseur ne peut enchérir sur lui-même
                        businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.ENCHERES_DEJA_REALISEES);
                        throw new BusinessException();
                    }
                }else{
                    //Pas d'enchère préalable
                    pstmt2 = cnx.prepareStatement(INSERT);
                    pstmt2.setInt(1, enchere.getIdUtilisateur());
                    pstmt2.setInt(2, enchere.getIdArticle());
                    pstmt2.setInt(3, enchere.getMontantEnchere());
                    pstmt2.executeUpdate();
                    //Récupération des points de l'utilisateur voulant enchérir
                    PreparedStatement pstmt3 = cnx.prepareStatement(SELECT_BY_ID_UTILISATEUR);
                    pstmt3.setInt(1, enchere.getIdUtilisateur());
                    ResultSet rs1 = pstmt3.executeQuery();
                    if (rs1.next()) {
                        pointsUtilisateurActuel = rs1.getInt("credit");
                    }
                    //Enlever les points de l'utilisateur actuel
                    PreparedStatement pstmt4 = cnx.prepareStatement(UPDATE_UTILISATEUR_CREDIT);
                    pstmt4.setInt(1, (pointsUtilisateurActuel - enchere.getMontantEnchere()));
                    pstmt4.setInt(2, enchere.getIdUtilisateur());
                    pstmt4.executeUpdate();
                    pstmt2.close();
                    pstmt3.close();
                    pstmt4.close();
                    rs1.close();
                    cnx.commit();
                    cnx.setAutoCommit(true);
                }
                pstmt.close();
                rs.close();
                pstmt2.close();
            } catch (Exception e) {
                e.printStackTrace();
                cnx.rollback();
                throw e;
            }

        }catch (Exception e){
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.UPDATE_DAL_ENCHERE);
            throw new BusinessException();
        }
    }

    @Override
    public void delete(int id) throws BusinessException {

    }

    /**
     * retourne une liste des enchères par acquéreur gagnant et dont l'article est vendu
     * @param acquereur
     * @return
     * @throws
     */
    @Override
    public List<Enchere> selectByAcqEtArtVendu(int acquereur) throws BusinessException {
        List<Enchere> listEnchere = new ArrayList<>();

        try (
                Connection cnx = ConnectionProvider.getConnection()
        )
        {
            PreparedStatement pst = cnx.prepareStatement(SELECT_BY_ACQ_ET_ART_VENDU);
            pst.setInt(1,acquereur );
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.IMPORT_DAL_ENCHERE_GAGNE);
            throw new BusinessException();

        }
        return listEnchere;
    }
}
