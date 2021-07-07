package fr.eni.trocencheres.bo;

import java.time.LocalDate;

public class Article {
    private int idArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private int prixInitial;
    private int prixVente;
    private String etat_Article;
    private String photo;
    private int vues;
    private String pseudoVendeur;
    private int prix;
    private String dateDebutFormat;
    private String dateFinFormat;
    private Utilisateur utilisateur;
    private Categorie categorie;
    private Retrait retrait;
    private Enchere enchere;

    public Article() {
    }

    public Article(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial, int prixVente, String etat_Article, String photo, int vues) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etat_Article = etat_Article;
        this.photo = photo;
        this.vues = vues;
    }

    public Article(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial, int prixVente, String etat_Article, String photo, int vues) {
        this.idArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etat_Article = etat_Article;
        this.photo = photo;
        this.vues = vues;
    }

    public Article(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial, int prixVente, String etat_Article, String photo, Utilisateur utilisateur, Categorie categorie) {
        this.idArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etat_Article = etat_Article;
        this.photo = photo;
        this.utilisateur = utilisateur;
        this.categorie = categorie;
    }

    /**
     *
     * @param noArticle
     * @param nomArticle
     * @param description
     * @param dateDebutEncheres
     * @param dateFinEncheres
     * @param prixInitial
     * @param prixVente
     * @param etat_Article
     * @param photo
     * @param utilisateur
     * @param categorie
     * @param vues
     */
    public Article(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int prixInitial, int prixVente, String etat_Article, String photo, Utilisateur utilisateur, Categorie categorie, int vues) {
        this.idArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.etat_Article = etat_Article;
        this.photo = photo;
        this.utilisateur = utilisateur;
        this.categorie = categorie;
        this.vues = vues;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDate getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDate dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public int getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(int prixInitial) {
        this.prixInitial = prixInitial;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public String getEtat_Article() {
        return etat_Article;
    }

    public void setEtat_Article(String etat_Article) {
        this.etat_Article = etat_Article;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getVues() {
        return vues;
    }

    public void setVues(int vues) {
        this.vues = vues;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Retrait getRetrait() {
        return retrait;
    }

    public void setRetrait(Retrait retrait) {
        this.retrait = retrait;
    }

    public Enchere getEnchere() {
        return enchere;
    }

    public void setEnchere(Enchere enchere) {
        this.enchere = enchere;
    }

    public String getPseudoVendeur() {
        return pseudoVendeur;
    }

    public void setPseudoVendeur(String pseudoVendeur) {
        this.pseudoVendeur = pseudoVendeur;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getDateDebutFormat() {
        return dateDebutFormat;
    }

    public void setDateDebutFormat(String dateDebutFormat) {
        this.dateDebutFormat = dateDebutFormat;
    }

    public String getDateFinFormat() {
        return dateFinFormat;
    }

    public void setDateFinFormat(String dateFinFormat) {
        this.dateFinFormat = dateFinFormat;
    }
}
