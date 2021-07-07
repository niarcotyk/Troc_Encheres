package fr.eni.trocencheres.bo;

import java.sql.Timestamp;

public class Enchere {
    private int id;
    private int idArticle;
    private int idUtilisateur;
    private Timestamp dateEnchere;
    private int montantEnchere;
    private String etatEnchere;
    private boolean der_ench;
    private Utilisateur utilisateur;
    private Article article;
    private int noAcquereur;
    private String nomAcquereur;

    public Enchere() {
    }

    public Enchere(Utilisateur utilisateur, Article article) {
        this.utilisateur = utilisateur;
        this.article = article;
    }

    public Enchere(Timestamp dateEnchere, int montantEnchere, String etatEnchere, int noAcquereur, String nomAcquereur) {
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
        this.etatEnchere = etatEnchere;
        this.noAcquereur = noAcquereur;
        this.nomAcquereur = nomAcquereur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(Timestamp dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    public String getEtatEnchere() {
        return etatEnchere;
    }

    public void setEtatEnchere(String etatEnchere) {
        this.etatEnchere = etatEnchere;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getNoAcquereur() {
        return noAcquereur;
    }

    public void setNoAcquereur(int noAcquereur) {
        this.noAcquereur = noAcquereur;
    }

    public String getNomAcquereur() {
        return nomAcquereur;
    }

    public void setNomAcquereur(String nomAcquereur) {
        this.nomAcquereur = nomAcquereur;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    public boolean isDer_ench() {
        return der_ench;
    }

    public void setDer_ench(boolean der_ench) {
        this.der_ench = der_ench;
    }
}
