package fr.eni.trocencheres.bo;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    private int idUtilisateur;
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String rue;
    private String CodePostal;
    private String ville;
    private String motDePasse;
    private int credit;
    private boolean admin;
    private List<Article> listeArticles;

    public Utilisateur(){
        listeArticles = new ArrayList<>();
    }

    /**
     *
     * @param pIdUtilisateur
     * @param pPseudo
     * @param pNom
     * @param pPrenom
     * @param pEmail
     * @param pTelephone
     * @param pRue
     * @param pCodePostal
     * @param pVille
     * @param pMotDePasse
     */
    public Utilisateur(int pIdUtilisateur, String pPseudo, String pNom, String pPrenom, String pEmail, String pTelephone, String pRue, String pCodePostal, String pVille, String pMotDePasse) {
        idUtilisateur = pIdUtilisateur;
        pseudo = pPseudo;
        nom = pNom;
        prenom = pPrenom;
        email = pEmail;
        telephone = pTelephone;
        rue = pRue;
        CodePostal = pCodePostal;
        ville = pVille;
        motDePasse = pMotDePasse;
    }

    /**
     *
     * @param pIdUtilisateur
     * @param pPseudo
     * @param pNom
     * @param pPrenom
     * @param pEmail
     * @param pTelephone
     * @param pRue
     * @param pCodePostal
     * @param pVille
     * @param pMotDePasse
     * @param pAdmin
     */
    public Utilisateur(int pIdUtilisateur, String pPseudo, String pNom, String pPrenom, String pEmail, String pTelephone, String pRue, String pCodePostal, String pVille, String pMotDePasse, boolean pAdmin) {
        idUtilisateur = pIdUtilisateur;
        pseudo = pPseudo;
        nom = pNom;
        prenom = pPrenom;
        email = pEmail;
        telephone = pTelephone;
        rue = pRue;
        CodePostal = pCodePostal;
        ville = pVille;
        motDePasse = pMotDePasse;
        admin = pAdmin;
    }

    /**
     *
     * @param pIdUtilisateur
     * @param pPseudo
     * @param pNom
     * @param pPrenom
     * @param pEmail
     * @param pTelephone
     * @param pRue
     * @param pCodePostal
     * @param pVille
     * @param pMotDePasse
     * @param pCredit
     */
    public Utilisateur(int pIdUtilisateur, String pPseudo, String pNom, String pPrenom, String pEmail, String pTelephone, String pRue, String pCodePostal, String pVille, String pMotDePasse, int pCredit) {
        idUtilisateur = pIdUtilisateur;
        pseudo = pPseudo;
        nom = pNom;
        prenom = pPrenom;
        email = pEmail;
        telephone = pTelephone;
        rue = pRue;
        CodePostal = pCodePostal;
        ville = pVille;
        motDePasse = pMotDePasse;
        credit = pCredit;
    }

    /**
     * Permet de s'assurer que l'article est lié à cet utilisateur
     * cohérence des données
     * @param nouvelArticle
     */
    public void addArticle(Article nouvelArticle){
        if(nouvelArticle.getUtilisateur()==this){
            listeArticles.add(nouvelArticle);
        }
    }
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return CodePostal;
    }

    public void setCodePostal(String codePostal) {
        CodePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Article> getListeArticles() {
        return listeArticles;
    }

    public void setListeArticles(List<Article> listeArticles) {
        this.listeArticles = listeArticles;
    }
}
