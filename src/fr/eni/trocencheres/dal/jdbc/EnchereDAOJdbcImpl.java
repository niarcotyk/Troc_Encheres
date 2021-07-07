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
    private static final String UPDATE = "UPDATE ENCHERES SET no_utilisateur = ?, date_enchere = ?, montant_enchere = ?, etat_enchere = 'NonVendu', der_ench = 1 WHERE no_article = ?";
    private static final String INSERT = "INSERT INTO ENCHERES(no_utilisateur, no_article, date_enchere, montant_enchere, etat_enchere, der_ench) VALUES(?,?,?,?,'NonVendu', 1)";
    private static final String SELECT_BY_ACQ_ET_ART_VENDU ="SELECT * FROM ENCHERES WHERE (no_acquereur=? and etat_enchere='Vendu')";
    private static final String SELECT_BY_ID_UTIL_AND_ID_ART = "SELECT date_enchere, montant_enchere, etat_enchere, no_acquereur FROM ENCHERES WHERE no_article = ? AND no_utilisateur = ?";
    private static final String SELECT_BY_ID_ART = "SELECT no_utilisateur, montant_enchere, der_ench FROM ENCHERES WHERE no_article = ? AND etat_enchere = 'NonVendu' ORDER BY montant_enchere DESC";
    private static final String SELECT_BY_ID_UTILISATEUR = "SELECT credit FROM UTILISATEURS WHERE no_utilisateur = ?";
    private static final String UPDATE_UTILISATEUR_CREDIT = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
    private static final String SELECT_BEFORE_UPDATE = "SELECT * FROM ENCHERES WHERE no_utilisateur = ? AND no_article = ? AND montant_enchere = ?";
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
     * UPDATE ou INSERT en fonction de l'action d'enchérir
     * @param enchere
     * @throws BusinessException
     */
    @Override
    public void update(Enchere enchere) throws BusinessException {
        int pointsEnchere = 0;
        int pointsUtilisateurAvant = 0;
        int idEnchereur = 0;
        int pointsUtilisateurActuel = 0;
        boolean der_ench = false;
        PreparedStatement pstmt2 = null;

        try (Connection cnx = ConnectionProvider.getConnection()) {
            try {
                //Récupérer la plus grosse enchère en cours via no_Article
                PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID_ART);
                pstmt.setInt(1, enchere.getIdArticle());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    idEnchereur = rs.getInt("no_utilisateur");
                    pointsEnchere = rs.getInt("montant_enchere");
                    if (rs.getByte("der_ench") != 0) {
                        der_ench = true;
                    }
                }

                    //Récupère les points actuels du + gros enchèreur
                    PreparedStatement pstmt1 = cnx.prepareStatement(SELECT_BY_ID_UTILISATEUR);
                    pstmt1.setInt(1, idEnchereur);
                    ResultSet rs1 = pstmt1.executeQuery();
                    if (rs1.next()) {
                        pointsUtilisateurAvant = rs1.getInt("credit");
                    }
                    cnx.setAutoCommit(false);
                    PreparedStatement pstt = cnx.prepareStatement(SELECT_BEFORE_UPDATE);
                    pstt.setInt(1, enchere.getIdUtilisateur());
                    pstt.setInt(2, enchere.getIdArticle());
                    pstt.setInt(3, enchere.getMontantEnchere());
                    ResultSet rSA = pstt.executeQuery();
                    if (rSA.next()) {
                        pstmt2 = cnx.prepareStatement(UPDATE);
                        pstmt2.setInt(1, enchere.getIdUtilisateur());
                        pstmt2.setTimestamp(2, enchere.getDateEnchere());
                        pstmt2.setInt(3, enchere.getMontantEnchere());
                        pstmt2.setInt(4, enchere.getIdArticle());
                        pstmt2.executeUpdate();
                        cnx.commit();
                        cnx.setAutoCommit(true);
                        //si tout se passe bien,
                        if (pstmt2 != null) {
                            //Récupère les points du nouvel enchérisseur
                            PreparedStatement pstmt3 = cnx.prepareStatement(SELECT_BY_ID_UTILISATEUR);
                            pstmt3.setInt(1, enchere.getIdUtilisateur());
                            ResultSet rs2 = pstmt3.executeQuery();
                            if (rs2.next()) {
                                pointsUtilisateurActuel = rs2.getInt("credit");
                            }
                            //Rendre les points au dernier enchèreur avant
                            PreparedStatement pstmt4 = cnx.prepareStatement(UPDATE_UTILISATEUR_CREDIT);
                            pstmt4.setInt(1, (pointsEnchere + pointsUtilisateurAvant));
                            pstmt4.setInt(2, idEnchereur);
                            pstmt4.executeUpdate();
                            //Enlever les points de l'enchèreur en cours
                            PreparedStatement pstmt5 = cnx.prepareStatement(UPDATE_UTILISATEUR_CREDIT);
                            pstmt5.setInt(1, (pointsUtilisateurActuel - enchere.getMontantEnchere()));
                            pstmt5.setInt(2, enchere.getIdUtilisateur());
                            pstmt5.executeUpdate();
                            //Mise à jour de la table ENCHERES
                            PreparedStatement pstt1 = cnx.prepareStatement(UPDATE_DER_ENCH);
                            pstt1.setInt(1, enchere.getIdArticle());
                            pstt1.setInt(2, idEnchereur);
                            pstt1.executeUpdate();
                            //UPDATE_DER_ENCH
                            //fermeture
                            pstmt3.close();
                            pstmt4.close();
                            pstmt5.close();
                            rs2.close();
                        }
                    }
                    pstt.close();
                    pstmt.close();
                    pstmt1.close();
                    if (pstmt2 != null) {
                        pstmt2.close();
                    }
                    rs.close();
                    rs1.close();
                    rSA.close();
            } catch (Exception e) {
                e.printStackTrace();
                cnx.rollback();
                throw e;
            }

        }catch (Exception e){
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.UPDATE_DAL_ENCHERE);
            throw new BusinessException();
        }finally{
            //Si pas d'enchères en cours alors insertion
            if(pstmt2 == null){
                try(Connection cnx = ConnectionProvider.getConnection();
                    PreparedStatement pstmt1 = cnx.prepareStatement(INSERT)){
                    pstmt1.setInt(1, enchere.getIdUtilisateur());
                    pstmt1.setInt(2,enchere.getIdArticle());
                    pstmt1.setTimestamp(3,enchere.getDateEnchere());
                    pstmt1.setInt(4, enchere.getMontantEnchere());
                    pstmt1.executeUpdate();
                    //Récupère les points du nouvel enchérisseur
                    PreparedStatement pstmt3 = cnx.prepareStatement(SELECT_BY_ID_UTILISATEUR);
                    pstmt3.setInt(1,enchere.getIdUtilisateur());
                    ResultSet rs = pstmt3.executeQuery();
                    if(rs.next()){
                        pointsUtilisateurActuel = rs.getInt("credit");
                    }
                    //Retirer les points de l'utilisateur qui vient d'enchèrir
                    PreparedStatement pstmt4 = cnx.prepareStatement(UPDATE_UTILISATEUR_CREDIT);
                    pstmt4.setInt(1, (pointsUtilisateurActuel - enchere.getMontantEnchere()));
                    pstmt4.setInt(2,enchere.getIdUtilisateur());
                    pstmt4.executeUpdate();
                    pstmt1.close();
                    pstmt3.close();
                    pstmt4.close();
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
