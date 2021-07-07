package fr.eni.trocencheres.bll;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bo.Article;
import fr.eni.trocencheres.dal.DAO;
import fr.eni.trocencheres.dal.DAOFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleManager {
    private final DAO<Article> articleDAO;
    private final BusinessException businessException = new BusinessException();

    public ArticleManager() {
        articleDAO = DAOFactory.getArticleDAO();
    }

    /**
     * Ramène tous les articles en base
     * @return
     */
    public List<Article> affichageArticles() {
        List<Article> listeArticle = new ArrayList<>();
        try {
            listeArticle = articleDAO.selectAll();
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return listeArticle;
    }

    /**
     * ajouter un article dans la base
     * @param article
     * @return
     * @throws BusinessException
     */
    public Article ajouterArticle(Article article) throws BusinessException {
        validerCaracteristique(article, businessException);
        VerifDates(article.getDateDebutEncheres(), article.getDateFinEncheres());
        if(!businessException.possedeErreurs()){
            articleDAO.insert(article);
        }
        else{
            throw businessException;
        }
        return article;
    }

    /**
     * Vérification des dates de début et de fin d'enchère
     * @param dateDebutArticle
     * @param dateFinArticle
     * @return
     */
    public void VerifDates(LocalDate dateDebutArticle, LocalDate dateFinArticle) {
        if(dateDebutArticle.toString().isEmpty()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_DATE_DEBUT_ENCHERE);
        }
        if(dateFinArticle.toString().isEmpty()){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_DATE_FIN_ENCHERE);
        }
        if(dateFinArticle.isBefore(dateDebutArticle)){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_DATE_DEBUT_DATE_FIN_ENCHERE);
        }
    }


    /**
     * return listArticle en fonction du filtre et de la catégorie selectionnée
     * @param idUtilisateur
     * @param filtre
     * @param idCategorie
     * @return
     * @throws BusinessException
     */
    public List<Article> rechercheParFiltreEtNoCategorie(int idUtilisateur, String filtre, int idCategorie) throws BusinessException {
        List<Article> listInfoArticle = null;

        listInfoArticle =   articleDAO.rechercheParFiltreEtNoCategorie(idUtilisateur,filtre, idCategorie);
        return listInfoArticle;
    }

    /**
     * return listArticle en fonction de l'id utilisateur et de l'id categories (Ventes en cours de l'utilisateur)
     * @param idUtilisateur
     * @param idCategorie
     * @return
     * @throws BusinessException
     */
    public List<Article> MesVentesEnCours(int idUtilisateur, String filtre, int idCategorie) throws BusinessException {
        List<Article> listInfoArticle = null;

        listInfoArticle =   articleDAO.selectByIdAndDatesEnchere(idUtilisateur,filtre, idCategorie);
        return listInfoArticle;
    }

    /**
     *
     * @param idUtilisateur
     * @param filtre
     * @param noCategorie
     * @return
     * @throws BusinessException
     */
    public List<Article> encheresOuvertes(int idUtilisateur, String filtre, int noCategorie) throws BusinessException {
        List<Article> listInfoArticles = null;

        listInfoArticles = articleDAO.selectByDateSupDebEnchereAndInfFinEnchere(idUtilisateur, filtre, noCategorie );
        return listInfoArticles;
    }

    /**
     *
     * @param idUtilisateur
     * @param filtreSaisi
     * @param noCatSelect
     * @return
     * @throws BusinessException
     */
    public List<Article> MesVentesNonDebutees(int idUtilisateur, String filtreSaisi, int noCatSelect) throws BusinessException {
        List<Article> listInfoArticles = null;

        listInfoArticles = articleDAO.selectByIdDateInfDebEnchere(idUtilisateur, filtreSaisi, noCatSelect);
        return listInfoArticles;
    }

    /**
     *
     * @param idUtilisateur
     * @param filtreSaisi
     * @param noCatSelect
     * @return
     * @throws BusinessException
     */
    public List<Article> mesEncheresEnCours(int idUtilisateur, String filtreSaisi, int noCatSelect) throws BusinessException {
        List<Article> listInfoArticles = null;

        listInfoArticles = articleDAO.selectByIdDateDerEnchere(idUtilisateur, filtreSaisi, noCatSelect);
        return listInfoArticles;
    }

    /**
     *
     * @param idUtilisateur
     * @param filtreSaisi
     * @param noCatSelect
     * @return
     * @throws BusinessException
     */
    public List<Article> MesVentesTerminees(int idUtilisateur, String filtreSaisi, int noCatSelect) throws BusinessException {
        List<Article> listInfoArticles = null;

        listInfoArticles = articleDAO.selectByIdAndDateSupFinEnchere(idUtilisateur, filtreSaisi, noCatSelect);
        return listInfoArticles;

    }

    /**
     * Choisir un Article selon les enchères pour PageEncherir
     */
    public List<Article> ChoisirArticlesEncherir(int idArticle){
        List<Article> listeArticles = null;
        try {
            listeArticles = articleDAO.selectByEnchere(idArticle);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return listeArticles;
    }

    public List<Article> mesEncheresRemportees(int idUtilisateur, String filtreSaisi, int noCatSelect) throws BusinessException {
        List<Article> listInfoArticles = null;

        listInfoArticles = articleDAO.selectByIdAndEtatEnchere(idUtilisateur, filtreSaisi, noCatSelect);
        return listInfoArticles;
    }

    /**
     * Récupère les informations de l'enchère remportée via l'identifiant utilisateur
     * @param idUtilisateur
     * @return objet Article avec les objets : Utilisateur, Retrait
     * @throws BusinessException
     */
    public Article ChoisirArticlesRemportes(int idUtilisateur) throws BusinessException {
        Article art = null;
        art = articleDAO.selectByEnchereRemportee(idUtilisateur);
        return art;
    }


    /**
     * Valider les caractéristiques d'un article
     * @param article
     * @param bE
     */
    private void validerCaracteristique(Article article, BusinessException bE) {
        if (article.getNomArticle() == null || article.getNomArticle().trim().equals("")){
            bE.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_NOM_SAISIE_ERREUR);
        }
        if(article.getDescription() == null || article.getDescription().trim().equals("") || article.getDescription().length()>200){
            bE.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_DESCRIPTION_SAISIE_ERREUR);
        }
        if(article.getCategorie().getNoCategorie() == 0){
            bE.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_CATEGORIE_NUL_ERREUR);
        }
        if(article.getPrixInitial() < 0) {
            bE.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_PRIX_INITIAL_ERREUR);
        }
    }
}
