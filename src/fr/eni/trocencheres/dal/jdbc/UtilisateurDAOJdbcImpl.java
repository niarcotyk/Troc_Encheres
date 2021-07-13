package fr.eni.trocencheres.dal.jdbc;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bo.Utilisateur;
import fr.eni.trocencheres.dal.ConnectionProvider;
import fr.eni.trocencheres.dal.DAO;

import javax.swing.text.html.HTMLDocument;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAOJdbcImpl implements DAO<Utilisateur> {
    private static final BusinessException businessException = new BusinessException();
    private final String SELECTALL              ="SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS";
    private final String SELECTBYID             = "SELECT pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE no_utilisateur = ?";
    private final String UPDATE_UTILISATEURS    = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?, mot_de_passe = ?, administrateur = ? WHERE no_utilisateur = ?";
    private final String UPDATE_CREDIT          = "UPDATE UTILISATEURS SET credit = ? WHERE no_utilisateur = ?";
    private final String INSERT                 = "INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    private final String UPDATE_DELETE          = "UPDATE UTILISATEURS SET pseudo = 'compte supprimé', nom = 'compte supprimé', prenom = 'compte supprimé', email = 'compte supprimé', telephone = 'compte supprimé', rue = 'compte supprimé',code_postal = 'supp.', ville='compte supprimé', mot_de_passe='compte supprimé', credit = 0 WHERE no_utilisateur = ?";
    private final String UPDATE_DELETE_ENCHERES = "UPDATE ENCHERES SET etat_enchere = 'annulé' AND der_ench = 0 WHERE no_utilisateur = ?";
    private final String UPDATE_DELETE_ARTICLES = "UPDATE ARTICLES SET etat_article = 'nondisponible' WHERE no_utilisateur = ?";
    private final String SELECTBYMAIL           = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE email = ?";
    private final String SELECTBYPSEUDO         = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE pseudo = ?";
    private final String SELECTBYPSEUDOANDPWD   = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, " +
                                                  "code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE pseudo = ? and  mot_de_passe=?";
    private final String SELECTBYIDCREDIT       = "SELECT montant_enchere, U.no_utilisateur AS Utilisateurenchere FROM V_UTIL_ENCHERES_ARTICLES_CATEGORIES_LEFT_RETRAITS AS V INNER JOIN UTILISATEURS AS U ON U.no_utilisateur = V.utilisateurEnchere WHERE V.no_utilisateur = ?";
    private final String SELECTENCHEREBYIDART   = "SELECT montant_enchere, no_utilisateur FROM ENCHERES WHERE no_article = ? AND der_ench = 1";
    private final String SELECTARTBYIDUTIL      = "SELECT no_article FROM ARTICLES WHERE no_utilisateur = ?";
    private final String SELECTBYIDUTIL         = "SELECT credit FROM UTILISATEURS WHERE no_utilisateur = ?";
    private final String SELECTBYENCHERE        = "SELECT no_article FROM ENCHERES WHERE no_utilisateur = ? AND der_ench = 1";
    private final String UPDATEDELENCHBYIDART   = "UPDATE ENCHERES SET etat_enchere = 'annulé', der_ench = 0 WHERE no_utilisateur = ? AND no_article = ?";

    /**
     * Sélectionner tous les utilisateurs
     * @return liste des utilisateurs présents dans la base de données
     * @throws BusinessException
     */
    @Override
    public List<Utilisateur> selectAll() throws BusinessException{
        List<Utilisateur> listUtilisateurs = new ArrayList<>();
        Utilisateur util = new Utilisateur();
        try (Connection cnx = ConnectionProvider.getConnection();
             Statement stt = cnx.createStatement()) {
            ResultSet rs = stt.executeQuery(SELECTALL);
            while(rs.next()){
                int idUti = rs.getInt("no_utilisateur");
                String pseudoUti = rs.getString("pseudo");
                String nomUti = rs.getString("nom");
                String prenomUti = rs.getString("prenom");
                String emailUti = rs.getString("email");
                String telUti = rs.getString("telephone");
                String rueUti = rs.getString("rue");
                String codePostalUti = rs.getString("code_postal");
                String villeUti = rs.getString("ville");
                String pwdUti = rs.getString("mot_de_passe");
                int creditUti = rs.getInt("credit");
                boolean adminUti = rs.getByte("administrateur") != 0;
                //Création de l'objet
                util.setIdUtilisateur(idUti);
                util.setPseudo(pseudoUti);
                util.setNom(nomUti);
                util.setPrenom(prenomUti);
                util.setEmail(emailUti);
                util.setTelephone(telUti);
                util.setRue(rueUti);
                util.setCodePostal(codePostalUti);
                util.setVille(villeUti);
                util.setMotDePasse(pwdUti);
                util.setCredit(creditUti);
                util.setAdmin(adminUti);

                listUtilisateurs.add(util);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_UTILISATEUR_ECHEC);
            throw businessException;

        }
        return listUtilisateurs;
    }

    /**
     * Sélectionne un utilisateur par rapport à son identifiant
     * @param id
     * @return objet Utilisateur
     * @throws BusinessException
     */
    @Override
    public Utilisateur selectById(int id) throws BusinessException {
        Utilisateur util = null;
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(SELECTBYID)) {
            pstt.setInt(1,id);
            ResultSet rs = pstt.executeQuery();
            if(rs.next()){
                int idUti = id;
                String pseudoUti = rs.getString("pseudo");
                String nomUti = rs.getString("nom");
                String prenomUti = rs.getString("prenom");
                String emailUti = rs.getString("email");
                String telUti = rs.getString("telephone");
                String rueUti = rs.getString("rue");
                String codePostalUti = rs.getString("code_postal");
                String villeUti = rs.getString("ville");
                String pwdUti = rs.getString("mot_de_passe");
                int creditUti = rs.getInt("credit");
                Boolean adminUti = rs.getBoolean("administrateur");
                util = new Utilisateur(idUti, pseudoUti, nomUti, prenomUti, emailUti, telUti, rueUti, codePostalUti, villeUti, pwdUti, creditUti);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_UTILISATEUR_ECHEC);
            throw businessException;
        }
        return util;
    }

    /**
     * Insertion d'un utilisateur dans la base de données
     * Vérification que l'objet passé en paramètre n'est pas null
     * @param util
     */
    @Override
    public void insert(Utilisateur util) throws BusinessException {
        if(util == null){
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.INSERT_OBJET_NULL);
            throw businessException;
        }
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstt.setString(1, util.getPseudo());
            pstt.setString(2, util.getNom());
            pstt.setString(3, util.getPrenom());
            pstt.setString(4, util.getEmail());
            //Gestion numéro de téléphone si null
            if(util.getTelephone() != null){
                pstt.setString(5, util.getTelephone());
            }
            else{
                pstt.setNull(5, Types.CHAR);
            }
            pstt.setString(6, util.getRue());
            pstt.setString(7,util.getCodePostal());
            pstt.setString(8, util.getVille());
            pstt.setString(9, util.getMotDePasse());
            pstt.setInt(10, 0);
            pstt.setBoolean(11, util.isAdmin());
            pstt.executeUpdate();

            ResultSet rs = pstt.getGeneratedKeys();
            if(rs.next()){
                util.setIdUtilisateur(rs.getInt(1));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.INSERT_UTILISATEUR_NULL);
            throw businessException;
        }
    }

    @Override
    public void update(Utilisateur util) throws BusinessException {
        if(util == null){
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.INSERT_OBJET_NULL);
            throw businessException;
        }
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(UPDATE_UTILISATEURS)) {
            pstt.setString(1, util.getPseudo());
            pstt.setString(2, util.getNom());
            pstt.setString(3, util.getPrenom());
            pstt.setString(4, util.getEmail());
            if(util.getTelephone()!=null){
                pstt.setString(5, util.getTelephone());
            }
            else{
                pstt.setNull(5, Types.CHAR);
            }
            pstt.setString(6, util.getRue());
            pstt.setString(7,util.getCodePostal());
            pstt.setString(8, util.getVille());
            pstt.setString(9, util.getMotDePasse());
            pstt.setByte(10, (byte) 0);
            pstt.setInt(11, util.getIdUtilisateur());
            pstt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.UPDATE_UTILISATEUR_ECHEC);
            throw businessException;
        }
    }

    /**
     * Si un utilisateur est supprimé on anonymise son compte afin de garder l'historique
     * Mise à jour de l'état article dans la table Articles et dans la table Enchères
     * redonner les points si enchère en cours pour le futur acquéreur
     * @param id
     * @throws BusinessException
     */
    @Override
    public void delete(int id) throws BusinessException {
        int points = 0;
        int idEnchereur = 0;
        int creditActuel = 0;
        ResultSet rs = null;
        try (Connection cnx = ConnectionProvider.getConnection();) {
            cnx.setAutoCommit(false);
            //Utilisateur a-t-il des articles ?
            PreparedStatement pstt1 = cnx.prepareStatement(SELECTARTBYIDUTIL);
            pstt1.setInt(1,id);
            rs = pstt1.executeQuery();
            //si l'utilisateur possède des articles
            if(rs.isBeforeFirst()){ //rs != null
                while(rs.next()){
                    //Si une enchère est en cours sur chaque article trouvé au niveau de l'utilisateur
                    PreparedStatement pstt2 = cnx.prepareStatement(SELECTENCHEREBYIDART );
                    pstt2.setInt(1, rs.getInt("no_article"));
                    ResultSet rs2 = pstt2.executeQuery();
                    //si oui
                    if( rs2 != null){
                        if(rs2.next()){
                            //Récupère les points de l'enchère
                            points = rs2.getInt("montant_enchere");
                            idEnchereur = rs2.getInt("no_utilisateur");
                            //Récupère les points en cours de l'utilisateur
                            PreparedStatement pstt3 = cnx.prepareStatement(SELECTBYIDUTIL);
                            pstt3.setInt(1, idEnchereur);
                            ResultSet rs3 = pstt3.executeQuery();
                            if(rs3.next()){
                                creditActuel = rs3.getInt("credit");
                            }
                            //Restitue les points
                            PreparedStatement pstt4 = cnx.prepareStatement(UPDATE_CREDIT);
                            pstt4.setInt(1, points + creditActuel);
                            pstt4.setInt(2, idEnchereur);
                            pstt4.executeUpdate();
                            //Mise à jour de la ligne enchère à l'état Annulé
                            PreparedStatement pstt5 = cnx.prepareStatement(UPDATE_DELETE_ENCHERES);
                            pstt5.setInt(1, idEnchereur);
                            pstt5.executeUpdate();
                        }
                    }
                    //Modification de l'état des articles dans la table Articles
                    PreparedStatement pstt6 = cnx.prepareStatement(UPDATE_DELETE_ARTICLES);
                    pstt6.setInt(1, id);
                    pstt6.executeUpdate();
                    cnx.commit();
                    //Fermeture
                    pstt2.close();
                    rs2.close();
                }
            }else{
                //L'utilisateur n'a pas d'article à vendre
                //A-t-il des enchères en cours ?
                PreparedStatement pstt2 = cnx.prepareStatement(SELECTBYENCHERE); //SELECTBYENCHERE
                pstt2.setInt(1, id);
                ResultSet rs2 = pstt2.executeQuery(); //pstt2.executeUpdate();
                while(rs2.next()){
                    int idArt = rs2.getInt("no_article");
                    PreparedStatement pstt3 = cnx.prepareStatement(UPDATEDELENCHBYIDART);
                    pstt3.setInt(1, id);
                    pstt3.setInt(2, idArt);
                    pstt3.executeUpdate();
                    //TODO envoyer un mail au propriétaire de l'objet pour l'avertir de la fin de l'enchère pour remise à dispo
                }
                cnx.commit();
            }
            //Mise à jour du compte à l'état supprimé pour garder l'historique
            PreparedStatement pstt = cnx.prepareStatement(UPDATE_DELETE);
            pstt.setInt(1, id);
            pstt.executeUpdate();
            cnx.commit();
            cnx.setAutoCommit(true);
            //Fermeture des requêtes
            pstt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.DELETE_UTILISATEUR_ECHEC);
            throw businessException;
        }
    }

    /**
     * Vérification de l'unicité du courriel de l'utilisateur
     * @param email
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean verifMail(String email) throws BusinessException {
        boolean unique = false;
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(SELECTBYMAIL)) {
            pstt.setString(1,email);
            ResultSet rs = pstt.executeQuery();
            if(rs.next()){
                unique = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.EMAIL_UTILISATEUR_ECHEC);
            throw businessException;
        }
        return unique;
    }

    /**
     * Vérification de l'unicité du pseudo de l'utilisateur
     * @param pseudo
     * @return
     * @throws BusinessException
     */
    @Override
    public boolean verifUtilisateur(String pseudo) throws BusinessException {
        boolean unique = false;
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(SELECTBYPSEUDO)) {
            pstt.setString(1,pseudo);
            ResultSet rs = pstt.executeQuery();
            //Si la requête ramène un utilisateur, donc existe déjà
            if(rs.next()){
                unique = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.PSEUDO_UTILISATEUR_ECHEC);
            throw businessException;
        }
        return unique;
    }

    /**
     * Restitution des points si le compte est supprimé
     * @param points
     * @param idAcquereur
     * @return
     */
    @Override
    public void restituerPoints(int points, int idAcquereur) throws BusinessException {
        //récupération des points dans la table Enchère et mise à jour des points dans la table Utilisateur.
        try(Connection cnx = ConnectionProvider.getConnection();
            PreparedStatement pstt = cnx.prepareStatement(UPDATE_CREDIT)) {
            if(points != 0 && idAcquereur != 0) {
                pstt.setInt(1, idAcquereur);
                pstt.setInt(2, points);
                pstt.executeUpdate();
            }else{
                businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.RESTITUTIONPOINTS_UTILISATEUR_ECHEC);
                throw businessException;
            }

        } catch (Exception e) {
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_UTILISATEUR_ECHEC);
            throw businessException;
        }

    }

    /**
     * Récupérer un Utilisateur en fonction de son pseudo et son password pour l'acheteur
     * @param pseudo
     * @param pwd
     * @return
     * @throws BusinessException
     */
    public Utilisateur getUtilisateur(String pseudo, String pwd) throws BusinessException {
        Utilisateur util = null;
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(SELECTBYPSEUDOANDPWD)) {
            pstt.setString(1,pseudo);
            pstt.setString(2,pwd);
            ResultSet rs = pstt.executeQuery();
            if(rs.next()){
                int idUti = rs.getInt("no_utilisateur");
                String pseudoUti = rs.getString("pseudo");
                String nomUti = rs.getString("nom");
                String prenomUti = rs.getString("prenom");
                String emailUti = rs.getString("email");
                String telUti = rs.getString("telephone");
                String rueUti = rs.getString("rue");
                String codePostalUti = rs.getString("code_postal");
                String villeUti = rs.getString("ville");
                String pwdUti = "";
                int creditUti = rs.getInt("credit");
                Boolean adminUti = rs.getBoolean("administrateur");
                util = new Utilisateur(idUti, pseudoUti, nomUti, prenomUti, emailUti, telUti, rueUti, codePostalUti, villeUti, pwdUti, creditUti);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_UTILISATEUR_ECHEC);
            throw businessException;
        }
        return util;
    }

    /**
     * Récupération d'un utilisateur par son pseudo, c'est pour le vendeur
     * @param pseudo
     * @return
     * @throws BusinessException
     */
    public Utilisateur selectByPseudo(String pseudo) throws BusinessException {
        Utilisateur vendeur = new Utilisateur();
        if(verifUtilisateur(pseudo)){
            try (
                    Connection cnx = ConnectionProvider.getConnection();
                    PreparedStatement pstt = cnx.prepareStatement(SELECTBYPSEUDO)
            ) {
                pstt.setString(1,pseudo.trim());
                ResultSet rs = pstt.executeQuery();
                if(rs.next()){
                    vendeur.setIdUtilisateur(rs.getInt("no_utilisateur"));
                    vendeur.setPseudo(rs.getString("pseudo"));
                    vendeur.setEmail(rs.getString("email"));
                    vendeur.setVille(rs.getString("ville"));
                }
                rs.close();

            } catch (Exception e) {
                e.printStackTrace();
                businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_UTILISATEUR_ECHEC);
                throw businessException;
            }
            return vendeur;
        }else{
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.PSEUDO_UTILISATEUR_ECHEC);
            throw businessException;
        }

    }
}
