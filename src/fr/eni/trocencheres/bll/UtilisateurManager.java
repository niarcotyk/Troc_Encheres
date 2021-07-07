package fr.eni.trocencheres.bll;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bo.Utilisateur;
import fr.eni.trocencheres.dal.DAO;
import fr.eni.trocencheres.dal.DAOFactory;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurManager {

    private DAO<Utilisateur> utilisateurDao;
    private BusinessException businessException = new BusinessException();

    public UtilisateurManager() {

        utilisateurDao = DAOFactory.getUtilisateurDAO();
    }

    /**
     * Ramène la liste de tous les utilisateurs
     *
     * @return
     */
    public List<Utilisateur> trouverTous() {
        List<Utilisateur> listeUtilisateurs = new ArrayList<>();
        try {
            listeUtilisateurs = utilisateurDao.selectAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listeUtilisateurs;
    }

    /**
     * Choisir un utilisateur en fonction de son identifiant
     *
     * @param id
     * @return
     * @throws BusinessException
     */
    public Utilisateur choisirUtilisateur(int id) throws BusinessException {
        return utilisateurDao.selectById(id);
    }

    /**
     * retourne l'id du pseudo en parametre.
     * @param pseudo
     * @return
     * @throws BusinessException
     */
    public Utilisateur choisirUtilisateurPseudo(String pseudo) throws BusinessException {

        utilisateurDao = DAOFactory.getUtilisateurDAO();

        Utilisateur Vendeur = null;
        try {
            Vendeur = utilisateurDao.selectByPseudo(pseudo);
        } catch (BusinessException e) {
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.PSEUDO_UTILISATEUR_ECHEC);
            throw businessException;
        }

        return Vendeur;
    }

    /**
     * Ajoute un utilisateur
     * @param util
     * @return
     * @throws BusinessException
     */
    public Utilisateur ajouterUtilisateur(Utilisateur util) throws BusinessException {
        //vérification des coordonnées
        validerCoordonnees(util, businessException);
        //vérification util existe déjà
        verifUtilisateur(util.getPseudo(),businessException);
        //vérification du mail
        verifEmail(util.getEmail(),businessException);
        if(!businessException.possedeErreurs()){
            utilisateurDao.insert(util);
        }
        else{
            throw businessException;
        }
        return util;
    }

    /**
     * Mise à jour utilisateur
     * @param util
     * @throws BusinessException
     */
    public void miseAJourUtilisateur(Utilisateur util) throws BusinessException {
        validerCoordonnees(util, businessException);
        if(!businessException.possedeErreurs()){
            utilisateurDao.update(util);
        }else{
            throw businessException;
        }
    }

    /**
     * Suppression d'un utilisateur en mettant à jour celui-ci en compte supprimé
     * @param id
     * @throws BusinessException
     */
    public void supprimer(int id) throws BusinessException {
        utilisateurDao.delete(id);
    }

    /**
     * Renvoie l'objet Utilisateur via son pseudo et password
     * @param pseudo
     * @param pwd
     * @return
     * @throws BusinessException
     */
    public Utilisateur getUtilisateur(String pseudo, String pwd) throws BusinessException {
        Utilisateur util = null;
        if(pseudo != null && pwd != null) {
            util = utilisateurDao.getUtilisateur(pseudo, pwd);
        }else{
            businessException.ajouterErreur(CodesResultatBLL.UTILISATEURS_PSEUDO_PWD_ERREUR);
        }
        return util;
    }

    /**
     * Vérification que le Pseudo est unique dans la base de données
     * @param pseudo
     * @param bE
     * @throws BusinessException
     */
    private void verifUtilisateur(String pseudo, BusinessException bE) throws BusinessException {
        if(pseudo != null) {
            boolean utilisateurExiste = utilisateurDao.verifUtilisateur(pseudo);
            if (utilisateurExiste) {
                bE.ajouterErreur(CodesResultatBLL.UTILISATEURS_PSEUDO_ERREUR);
            }
        }else{
            bE.ajouterErreur(CodesResultatBLL.UTILISATEUR_PSEUDO_NONRENSEIGNE);
        }
    }

    /**
     * Vérification de l'unicité du courriel
     * @param email
     * @param bE
     * @throws BusinessException
     */
    private void verifEmail(String email, BusinessException bE) throws BusinessException {
        boolean emailExiste = utilisateurDao.verifMail(email);
        if(emailExiste){
            bE.ajouterErreur(CodesResultatBLL.UTILISATEURS_EMAIL_ERREUR);
        }
    }

    /**
     * Méthode pour vérifier que les champs sont remplis car obligatoires
     *
     * @param util
     * @param bE
     */
    private void validerCoordonnees(Utilisateur util, BusinessException bE) {
        if (util.getPseudo() == null || util.getPseudo().trim().equals("")){
            bE.ajouterErreur(CodesResultatBLL.REGLE_UTILISATEURS_PSEUDO_ERREUR);
        }
        if(util.getNom() == null || util.getNom().trim().equals("") || util.getNom().length()>30 || util.getNom().contains("[0-9]")){
            bE.ajouterErreur(CodesResultatBLL.REGLE_UTILISATEURS_NOM_ERREUR);
        }
        if(util.getPrenom() == null || util.getPrenom().trim().equals("") || util.getPrenom().length() >30 || util.getNom().contains("[0-9]")){
            bE.ajouterErreur(CodesResultatBLL.REGLE_UTILISATEURS_PRENOM_ERREUR);
        }
        if(util.getEmail() == null || util.getEmail().trim().equals("") || util.getEmail().length() > 75
                || !util.getEmail().contains("@")){
            bE.ajouterErreur(CodesResultatBLL.REGLE_UTILISATEURS_EMAIL_ERREUR);
        }
        if(util.getTelephone() == null || util.getTelephone().trim().equals("") || util.getTelephone().length() > 15
                || util.getTelephone().contains("[a-zA-Z]")) {
            bE.ajouterErreur(CodesResultatBLL.REGLE_UTILISATEURS_TEL_ERREUR);
        }
        if( util.getRue() == null || util.getRue().trim().equals("") || util.getRue().length() >30){
            bE.ajouterErreur(CodesResultatBLL.REGLE_UTILISATEURS_RUE_ERREUR);
        }
        if(util.getCodePostal() == null ||  util.getCodePostal().trim().equals("") || util.getCodePostal().length() > 10 || util.getCodePostal().contains("[a-zA-Z]")){
            bE.ajouterErreur(CodesResultatBLL.REGLE_UTILISATEURS_CODEPOSTAL_ERREUR);
        }
        if(util.getVille() == null || util.getVille().trim().equals("") || util.getVille().length() >30 || util.getNom().contains("[0-9]")){
            bE.ajouterErreur(CodesResultatBLL.REGLE_UTILISATEURS_VILLE_ERREUR);
        }
        if(util.getMotDePasse() == null || util.getMotDePasse().trim().equals("") || util.getMotDePasse().length() >30) {
            bE.ajouterErreur(CodesResultatBLL.REGLE_UTILISATEURS_MOTDEPASSE_ERREUR);
        }
    }
}
