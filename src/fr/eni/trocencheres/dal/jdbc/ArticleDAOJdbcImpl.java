package fr.eni.trocencheres.dal.jdbc;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bo.*;
import fr.eni.trocencheres.dal.ConnectionProvider;
import fr.eni.trocencheres.dal.DAO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArticleDAOJdbcImpl implements DAO<Article> {
    private BusinessException businessException = new BusinessException();
    //Requêtes
    private static final String SELECT_ALL                                  = "SELECT TOP(6) no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, etat_article, photo, vues,no_categorie, libelle, no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM V_ARTICLES_CATEGORIES_UTILISATEURS ORDER BY date_fin_encheres DESC";
    private static final String INSERT_ARTICLE                              = "INSERT INTO ARTICLES(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, etat_article, no_utilisateur, no_categorie) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ARTICLE                              = "UPDATE ARTICLES SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?, prix_vente = ?, etat_article = ?, photo = ?, no_utilisateur = ?, no_categorie = ?, vues = ? where no_article=?";
    private static final String DELETE_ARTICLE                              = "DELETE FROM ARTICLES WHERE id=?";
    //encheres ouvertes
    private static String SELECT_BY_DATE_SUP_DEB_ENCH_AND_INF_FIN_ENCHERE   ="SELECT arts_no_articles, arts_nom_article, arts_prix_initial, arts_prix_vente, encs_montant_enchere, cats_libelle, utils_pseudo, arts_date_fin_encheres FROM V_ARTICLES_CATEGORIES_UTILISATEURS_ENCHERES WHERE ?>=arts_date_debut_encheres AND ?<= arts_date_fin_encheres ";
    //Mes encheres en Cours
    private static String SELECT_BY_ID_DATE_DER_ENCHERE                     ="SELECT arts_no_articles, arts_nom_article, arts_prix_initial, arts_prix_vente, encs_montant_enchere, cats_libelle, utils_pseudo, arts_date_fin_encheres FROM V_ARTICLES_CATEGORIES_UTILISATEURS_ENCHERES WHERE arts_date_debut_encheres <= ? AND  ? <= arts_date_fin_encheres AND  encs_no_utilisateur=? AND encs_etat_enchere <> 'Annule'  AND encs_derniere_enchere=1 ";
    //Mes encheres Remportees
    private static String SELECT_BY_ID_AND_ETATENCHERE                      ="SELECT arts_no_articles, arts_nom_article, arts_prix_initial, arts_prix_vente, encs_montant_enchere, cats_libelle, utils_pseudo, arts_date_fin_encheres FROM V_ARTICLES_CATEGORIES_UTILISATEURS_ENCHERES WHERE encs_no_acquereur=? AND encs_etat_enchere='Vendu'";
    //mes ventes en cours
    private static String SELECT_BY_ID_AND_DATES_ENCHERE                    ="SELECT arts_no_articles, arts_nom_article, arts_prix_initial, arts_prix_vente, encs_montant_enchere, cats_libelle, utils_pseudo, arts_date_fin_encheres FROM V_ARTICLES_CATEGORIES_UTILISATEURS_ENCHERES WHERE arts_no_utilisateur=? AND ?<=arts_date_fin_encheres AND arts_date_debut_encheres<=? ";
    //mes ventes non débutees
    private static String SELECT_BY_ID_DATE_INF_DEB_ENCHERE                 ="SELECT arts_no_articles, arts_nom_article, arts_prix_initial, arts_prix_vente, encs_montant_enchere, cats_libelle, utils_pseudo, arts_date_fin_encheres FROM V_ARTICLES_CATEGORIES_UTILISATEURS_ENCHERES WHERE ?< arts_date_debut_encheres AND arts_no_utilisateur=? ";
    //mes ventes terminees
    private static String SELECT_BY_ID_DATE_SUP_FIN_ENCHERE                 ="SELECT arts_no_articles, arts_nom_article, arts_prix_initial, arts_prix_vente, encs_montant_enchere, cats_libelle, utils_pseudo, arts_date_fin_encheres FROM V_ARTICLES_CATEGORIES_UTILISATEURS_ENCHERES WHERE ?>arts_date_fin_encheres AND arts_no_utilisateur=? ";
    //recherche par fitre et catégorie
    private static String SELECT_BY_NOM_ARTICLE_NO_CATEGORIE                ="SELECT articles.no_article, articles.nom_article,articles.prix_initial,articles.prix_vente,pseudo,ARTICLES.date_fin_encheres,montant_enchere,libelle, C.no_categorie FROM V_UTILISATEURS_ENCHERES_ARTICLES LEFT OUTER JOIN ARTICLES ON V_UTILISATEURS_ENCHERES_ARTICLES.no_utilisateur = ARTICLES.no_utilisateur LEFT JOIN CATEGORIES C on C.no_categorie = ARTICLES.no_categorie WHERE der_ench = 1 AND pseudo<>'compte supprimé' ";
    //Données pour la page enchère
    private static String SELECT_BY_ID_JOIN                                 = "SELECT U.no_utilisateur, U.pseudo, U.nom, U.prenom, U.email, U.telephone, U.rue AS rueUtilisateur, U.code_postal AS codePostalUtilisateur, U.ville AS villeUtilisateur, U.credit, E.date_enchere, E.montant_enchere, E.etat_enchere, E.no_acquereur, A.no_article, A.nom_article, A.description, A.date_debut_encheres, A.date_fin_encheres, A.prix_initial, A.prix_vente, " +
                                                                              "A.etat_article,  R.rue AS rueRetrait, R.code_postal AS codePostalRetrait, R.ville AS villeRetrait, C.no_categorie, C.libelle, UEncheres.pseudo AS pseudoEnchere, UEncheres.credit AS creditEncheres " +
                                                                              "FROM UTILISATEURS  AS U " +
                                                                              "INNER JOIN ARTICLES AS A ON U.no_utilisateur = A.no_utilisateur " +
                                                                              "LEFT JOIN ENCHERES AS E ON A.no_article = E.no_article " +
                                                                              "INNER JOIN CATEGORIES AS C ON A.no_categorie = C.no_categorie " +
                                                                              "LEFT JOIN RETRAITS AS R ON R.no_article = A.no_article " +
                                                                              "LEFT JOIN UTILISATEURS AS UEncheres ON E.no_utilisateur = UEncheres.no_utilisateur " +
                                                                              "WHERE A.no_article = ? AND U.pseudo <> 'compte supprimé' ORDER BY montant_enchere DESC";
    //Données pour la page Acquisition
    private static String SELECT_BY_ENCHERE_REMPORTEE                       ="SELECT nom_article, description, prix_vente, prix_initial, rueUtilisateur, codePostalUtilisateur, villeUtilisateur, rueRetrait, codePostalRetrait, villeRetrait, pseudo, telephone FROM V_UTIL_ENCHERES_ARTICLES_CATEGORIES_LEFT_RETRAITS WHERE no_acquereur = ?";
    //UtilisateurEnchérisseur
    private static String SELECT_BY_ID_ENCHERISSEUR                         ="SELECT credit FROM UTILISATEURS WHERE no_utilisateur = ?";

    /**
     * Récupère toutes les données de la table article
     *
     * @return listeArticle
     * @throws BusinessException LECTURE_ARTICLE_ECHEC
     */
    @Override
    public List<Article> selectAll() throws BusinessException {
        List<Article> listeArticle = new ArrayList<>();
        List<Utilisateur> listeUtilisateur = new ArrayList<>();
        List<Categorie> listeCategorie = new ArrayList<>();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            Statement pstmt = cnx.createStatement();
            ResultSet rs = pstmt.executeQuery(SELECT_ALL);
            Utilisateur utilisateurEnCours = new Utilisateur();
            while (rs.next()) {
                if (rs.getInt(1) != utilisateurEnCours.getIdUtilisateur()) {
                    utilisateurEnCours = new Utilisateur();
                    utilisateurEnCours.setIdUtilisateur(rs.getInt(13));
                    utilisateurEnCours.setPseudo(rs.getString(14));
                    utilisateurEnCours.setNom(rs.getString(15));
                    utilisateurEnCours.setPrenom(rs.getString(16));
                    utilisateurEnCours.setEmail(rs.getString(17));
                    utilisateurEnCours.setTelephone(rs.getString(18));
                    utilisateurEnCours.setRue(rs.getString(19));
                    utilisateurEnCours.setCodePostal(rs.getString(20));
                    utilisateurEnCours.setVille(rs.getString(21));
                    utilisateurEnCours.setMotDePasse(rs.getString(22));
                    utilisateurEnCours.setCredit(rs.getInt(23));
                    boolean admin = true;
                    if (rs.getByte(24) == 0) {
                        admin = false;
                    }
                    utilisateurEnCours.setAdmin(admin);
                    listeUtilisateur.add(utilisateurEnCours);
                }
                Categorie categorieEnCours = new Categorie();
                if (rs.getInt(1) != categorieEnCours.getNoCategorie()) {
                    categorieEnCours.setNoCategorie(rs.getInt(1));
                    categorieEnCours.setLibelle(rs.getString(2));
                    listeCategorie.add(categorieEnCours);
                }
                Article articleEnCours = new Article();
                articleEnCours.setIdArticle(rs.getInt("no_article"));
                articleEnCours.setNomArticle(rs.getString("nom_article"));
                articleEnCours.setDescription(rs.getString("description"));
                LocalDate dateDebut = rs.getDate("date_debut_encheres").toLocalDate();
                LocalDate dateFin = rs.getDate("date_fin_encheres").toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dateDebutFormat = dateDebut.format(formatter);
                String dateFinFormat = dateFin.format(formatter);
                articleEnCours.setDateDebutFormat(dateDebutFormat);
                articleEnCours.setDateFinFormat(dateFinFormat);
                articleEnCours.setPrixInitial(rs.getInt("prix_initial"));
                articleEnCours.setPrixVente(rs.getInt("prix_vente"));
                articleEnCours.setEtat_Article(rs.getString("etat_article"));
                articleEnCours.setPhoto(rs.getString("photo"));
                articleEnCours.setVues(rs.getInt("vues"));
                articleEnCours.setPseudoVendeur(rs.getString("pseudo"));
                articleEnCours.setUtilisateur(utilisateurEnCours);
                articleEnCours.setCategorie(categorieEnCours);
                listeArticle.add(articleEnCours);
            }

        } catch (Exception e) {
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_ARTICLE_ECHEC);
            throw businessException;
        }
        return listeArticle;
    }

    @Override
    public Article selectById(int id) {
        Article art = null;
        return null;
    }

    /**
     * Insertion d'article dans la table article
     *
     * @param article
     * @throws BusinessException
     */
    @Override
    public void insert(Article article) throws BusinessException {
        if (article == null) {
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.INSERT_OBJET_ECHEC);
            throw businessException;
        }

        try (Connection cnx = ConnectionProvider.getConnection()) {
            try {
                cnx.setAutoCommit(false);
                PreparedStatement pstmt;
                ResultSet rs;
                if (article.getIdArticle() == 0) {
                    pstmt = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, article.getNomArticle());
                    pstmt.setString(2, article.getDescription());
                    pstmt.setDate(3, java.sql.Date.valueOf(article.getDateDebutEncheres()));
                    pstmt.setDate(4, java.sql.Date.valueOf(article.getDateFinEncheres()));
                    pstmt.setInt(5, article.getPrixInitial());
                    pstmt.setInt(6, article.getPrixVente());
                    pstmt.setString(7, article.getEtat_Article());
                    pstmt.setInt(8, article.getUtilisateur().getIdUtilisateur());
                    pstmt.setInt(9, article.getCategorie().getNoCategorie());
                    pstmt.executeUpdate();
                    rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        article.setIdArticle(rs.getInt(1));
                    }
                    rs.close();
                    pstmt.close();
                    cnx.commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
                cnx.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.INSERT_ARTICLE_ECHEC);
            throw businessException;
        }
    }

    /**
     * Update tous les paramètres d'un article
     *
     * @param article
     * @throws BusinessException
     */
    @Override
    public void update(Article article) throws BusinessException {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(UPDATE_ARTICLE);
            pstmt.setString(1, article.getNomArticle());
            pstmt.setString(2, article.getDescription());
            pstmt.setDate(3, java.sql.Date.valueOf(article.getDateDebutEncheres()));
            pstmt.setDate(4, java.sql.Date.valueOf(article.getDateFinEncheres()));
            pstmt.setInt(5, article.getPrixInitial());
            pstmt.setInt(6, article.getPrixVente());
            pstmt.setString(7, article.getEtat_Article());
            pstmt.setString(8, article.getPhoto());
            pstmt.setInt(9, article.getUtilisateur().getIdUtilisateur());
            pstmt.setInt(10, article.getCategorie().getNoCategorie());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.UPDATE_ARTICLE_ECHEC);
            throw businessException;
        }
    }

    /**
     * Supprime un article selon son id
     *
     * @param id
     * @throws BusinessException
     */
    @Override
    public void delete(int id) throws BusinessException {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(DELETE_ARTICLE);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.DELETE_ARTICLE_ERREUR);
            throw businessException;
        }
    }

    /**
     * Méthode pour récupérer les différentes données pour la page enchère
     *
     * @param idArticle no_article
     * @param idEncherisseur no_utilisateur
     * @return
     */
    @Override
    public List<Article> selectByEnchere(int idArticle, int idEncherisseur) throws BusinessException {
        List<Article> listeArticles = new ArrayList<>();
        List<Utilisateur> listeUtilisateurs = new ArrayList<>();
        List<Categorie> listeCategories = new ArrayList<>();
        List<Enchere> listeEncheres = new ArrayList<>();
        List<Retrait> listeRetraits = new ArrayList<>();
        int credit = 0;
        if (idArticle == 0) {
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_ID_ARTICLE_ECHEC);
            throw businessException;
        } else {
            try (Connection cnx = ConnectionProvider.getConnection();
                 PreparedStatement pstt = cnx.prepareStatement(SELECT_BY_ID_JOIN)) {
                pstt.setInt(1, idArticle);
                ResultSet rs = pstt.executeQuery();
                //Récupération des crédits de l'enchérisseur
                PreparedStatement pstt1 = cnx.prepareStatement(SELECT_BY_ID_ENCHERISSEUR);
                pstt1.setInt(1,idEncherisseur);
                ResultSet rs1 = pstt1.executeQuery();
                if(rs1.next()){
                    credit = rs1.getInt(1);
                }
                rs1.close();
                pstt1.close();
                Utilisateur utilisateurEnCours = new Utilisateur();
                Enchere enchereEnCours = new Enchere();
                boolean passe = false;
                while (rs.next()) {
                    //Si l'identifiant de la requête est différent de l'identifiant de l'objet
                    if (rs.getInt("no_utilisateur") != utilisateurEnCours.getIdUtilisateur()) {
                        utilisateurEnCours.setIdUtilisateur(rs.getInt("no_utilisateur"));
                        utilisateurEnCours.setPseudo(rs.getString("pseudo"));
                        utilisateurEnCours.setNom(rs.getString("nom"));
                        utilisateurEnCours.setPrenom(rs.getString("prenom"));
                        utilisateurEnCours.setEmail(rs.getString("email"));
                        utilisateurEnCours.setTelephone(rs.getString("telephone"));
                        utilisateurEnCours.setRue(rs.getString("rueUtilisateur"));
                        utilisateurEnCours.setCodePostal(rs.getString("codePostalUtilisateur"));
                        utilisateurEnCours.setVille(rs.getString("villeUtilisateur"));
                        utilisateurEnCours.setCredit(credit);
                        listeUtilisateurs.add(utilisateurEnCours);
                        passe = true;
                    } else {
                        passe = false;
                    }
                    //Catégorie
                    Categorie categorieEnCours = new Categorie();
                    if (rs.getInt("no_categorie") != categorieEnCours.getNoCategorie()) {
                        categorieEnCours.setNoCategorie(rs.getInt("no_categorie"));
                        categorieEnCours.setLibelle(rs.getString("libelle"));
                        listeCategories.add(categorieEnCours);
                    }

                    //Retrait
                    Retrait retraitEnCours = new Retrait();
                    if (rs.getInt("no_article") != retraitEnCours.getId()) {
                        retraitEnCours.setId(rs.getInt("no_article"));
                        retraitEnCours.setRue(rs.getString("rueRetrait"));
                        retraitEnCours.setCodePostal(rs.getString("codePostalRetrait"));
                        retraitEnCours.setVille(rs.getString("villeRetrait"));
                        listeRetraits.add(retraitEnCours);
                    }
                    //Enchere
                    if (rs.getInt("no_article") != enchereEnCours.getIdArticle() && rs.getInt("no_utilisateur") != enchereEnCours.getIdUtilisateur()) {
                        if (enchereEnCours.getMontantEnchere() >= rs.getInt("montant_enchere") || enchereEnCours.getIdArticle() == 0) {
                            enchereEnCours.setIdArticle(rs.getInt("no_article"));
                            enchereEnCours.setIdUtilisateur(rs.getInt("no_utilisateur"));
                            enchereEnCours.setDateEnchere(rs.getTimestamp("date_enchere"));
                            enchereEnCours.setMontantEnchere(rs.getInt("montant_enchere"));
                            enchereEnCours.setEtatEnchere(rs.getString("etat_enchere"));
                            enchereEnCours.setNoAcquereur(rs.getInt("no_acquereur"));
                            enchereEnCours.setNomAcquereur(rs.getString("pseudoEnchere"));
                            listeEncheres.add(enchereEnCours);
                        }
                    }
                    //Article
                    Article articleEnCours = new Article();
                    if (rs.getInt("no_article") != articleEnCours.getIdArticle()) {
                        //Teste de l'utilisateur
                        if (passe) {
                            articleEnCours.setIdArticle(rs.getInt("no_article"));
                            articleEnCours.setNomArticle(rs.getString("nom_article"));
                            articleEnCours.setDescription(rs.getString("description"));
                            articleEnCours.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
                            articleEnCours.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
                            articleEnCours.setPrixInitial(rs.getInt("prix_initial"));
                            articleEnCours.setPrixVente(rs.getInt("prix_vente"));
                            articleEnCours.setEtat_Article(rs.getString("etat_article"));
                            articleEnCours.setUtilisateur(utilisateurEnCours);
                            articleEnCours.setCategorie(categorieEnCours);
                            articleEnCours.setRetrait(retraitEnCours);
                            articleEnCours.setEnchere(enchereEnCours);
                            listeArticles.add(articleEnCours);
                        }
                    }
                } //Fin de la boucle while
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listeArticles;
    }

    /**
     * Méthode pour récupérer les différentes données pour la page Acquisition
     *
     * @param id no_utilisateur
     * @return Objet Article avec les données d'autres objets : Utilisateur, Enchère, Retrait
     */
    @Override
    public Article selectByEnchereRemportee(int id) throws BusinessException {
        Article art = null;
        if (id == 0) {
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_ID_UTILISATEUR_ECHEC);
            throw businessException;
        } else {
            try (Connection cnx = ConnectionProvider.getConnection();
                 PreparedStatement pstt = cnx.prepareStatement(SELECT_BY_ENCHERE_REMPORTEE)) {
                pstt.setInt(1, id);
                ResultSet rs = pstt.executeQuery();
                Utilisateur util = new Utilisateur();
                Retrait retrait = null;
                if (rs.next()) {
                    art = new Article();
                    art.setNomArticle(rs.getString("nom_article"));
                    art.setDescription(rs.getString("description"));
                    art.setPrixInitial(rs.getInt("prix_initial"));
                    art.setPrixVente(rs.getInt("prix_vente"));
                    //Si pas d'adresse renseignée dans la table RETRAITS
                    if (rs.getString("rueRetrait") == "") {
                        util.setRue(rs.getString("rueUtilisateur"));
                        util.setCodePostal(rs.getString("codePostalUtilisateur"));
                        util.setVille(rs.getString("villeUtilisateur"));
                        util.setPseudo(rs.getString("pseudo"));
                        util.setTelephone(rs.getString("telephone"));
                    }else{
                        retrait = new Retrait();
                        retrait.setRue(rs.getString("rueRetrait"));
                        retrait.setCodePostal(rs.getString("codePostalRetrait"));
                        retrait.setVille(rs.getString("villeRetrait"));
                        util.setPseudo(rs.getString("pseudo"));
                        util.setTelephone(rs.getString("telephone"));
                    }
                    if (retrait == null) {
                        art.setUtilisateur(util);
                    } else {
                        art.setUtilisateur(util);
                        art.setRetrait(retrait);
                    }
                } else {
                    businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_ENCHERE_ID_UTILISATEUR_ECHEC);
                    throw businessException;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return art;
    }

    /**
     * Recherche par mot clé et catégorie
     * @param idUtilisateur
     * @param filtre
     * @param noCategorie
     * @return
     */
    public List<Article> rechercheParFiltreEtNoCategorie(int idUtilisateur, String filtre, int noCategorie) {
        List<Article> listInfoArticle = new ArrayList<>();
        String requestSql = null;
        String restrictionsComplementaire = "";
        Boolean filtreSaisi = false;
        Boolean categorieSelect = false;

        try (
                Connection cxn = ConnectionProvider.getConnection();
        ) {

            if (filtre != "") {
                restrictionsComplementaire += " AND articles.nom_article LIKE '%'+?+'%' ";
                filtreSaisi = true;

            }
            if (noCategorie != 0) {
                restrictionsComplementaire += " AND C.no_categorie=? ";
                categorieSelect = true;
            }
            //Preparation de la requete
            requestSql = SELECT_BY_NOM_ARTICLE_NO_CATEGORIE + restrictionsComplementaire;

            PreparedStatement ptt = cxn.prepareStatement(requestSql);

            if (filtreSaisi && categorieSelect) {
                ptt.setString(1, filtre);
                ptt.setInt(2, noCategorie);
            }
            if (filtreSaisi && !categorieSelect) {
                ptt.setString(1, filtre);
            }
            if (!filtreSaisi && categorieSelect) {
                ptt.setInt(1, noCategorie);
            }

            ResultSet rs = ptt.executeQuery();
            while (rs.next()) {
                Article infoArticle = new Article();

                infoArticle.setIdArticle(rs.getInt("no_article"));
                infoArticle.setPrix(Math.max(rs.getInt("prix_initial"), Math.max(rs.getInt("prix_vente"),
                        rs.getInt("montant_enchere"))));
                infoArticle.setNomArticle(rs.getString("nom_article"));
                infoArticle.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
                infoArticle.setPseudoVendeur(rs.getString("pseudo"));

                listInfoArticle.add(infoArticle);
            }
            ptt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_ARTICLE_ECHEC);
        }

        return listInfoArticle;
    }

    /**
     * retour les encheres en cours pour l'utilisateur
     *
     * @param idUtilisateur
     * @param filtre
     * @param noCategorie
     * @return
     */
    public List<Article> selectByIdDateDerEnchere(int idUtilisateur, String filtre, int noCategorie) {
        List<Article> listInfoArticle = new ArrayList<>();
        String requestSql = null;
        String restrictionsComplementaire = "";
        Boolean filtreSaisi = false;
        Boolean categorieSelect = false;

        try (
                Connection cxn = ConnectionProvider.getConnection();
        )
        {

            if (filtre != "") {
                restrictionsComplementaire += "AND arts_nom_article LIKE '%'+?+'%'";
                filtreSaisi = true;

            }
            if (noCategorie != 0) {
                restrictionsComplementaire += "AND cats_no_categorie=?";
                categorieSelect = true;
            }
            //Preparation de la requete
            requestSql = SELECT_BY_ID_DATE_DER_ENCHERE + restrictionsComplementaire + " ORDER BY arts_no_articles,  encs_montant_enchere DESC";

            PreparedStatement ptt = cxn.prepareStatement(requestSql);

            ptt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ptt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            ptt.setInt(3, idUtilisateur);

            if (filtreSaisi && categorieSelect) {
                ptt.setString(4, filtre);
                ptt.setInt(5, noCategorie);
            }
            if (filtreSaisi && !categorieSelect) {
                ptt.setString(4, filtre);
            }
            if (!filtreSaisi && categorieSelect) {
                ptt.setInt(4, noCategorie);
            }

            ResultSet rs = ptt.executeQuery();
            while (rs.next()) {
                Article infoArticle = new Article();

                infoArticle.setIdArticle(rs.getInt("arts_no_articles"));
                infoArticle.setPrix(Math.max(rs.getInt("arts_prix_initial"), Math.max(rs.getInt("arts_prix_vente"),
                        rs.getInt("encs_montant_enchere"))));
                infoArticle.setNomArticle(rs.getString("arts_nom_article"));
                infoArticle.setDateFinEncheres(rs.getDate("arts_date_fin_encheres").toLocalDate());
                infoArticle.setPseudoVendeur(rs.getString("utils_pseudo"));

                listInfoArticle.add(infoArticle);

            }
            ptt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_ARTICLE_ECHEC);
        }

        //retrait des doublons
        int i=0;
        while((i+1)<listInfoArticle.size()){
            if (listInfoArticle.get(i).getIdArticle() == listInfoArticle.get(i + 1).getIdArticle()) {
                listInfoArticle.remove(i + 1);
                i--;
            }
            i++;
        }

        return listInfoArticle;
    }

    /**
     * retourne la liste des infoArticles des encheres remportees par l'utilisateur en fonction du filtre saisie et de la catégorie selectionnée
     *
     * @param idUtilisateur
     * @param filtre
     * @param noCategorie
     * @return
     */
    public List<Article> selectByIdAndEtatEnchere(int idUtilisateur, String filtre, int noCategorie) {
        List<Article> listInfoArticle = new ArrayList<>();
        String requestSql = null;
        String restrictionsComplementaire = "";
        Boolean filtreSaisi = false;
        Boolean categorieSelect = false;

        try (
                Connection cxn = ConnectionProvider.getConnection();
        ) {

            if (filtre != "") {
                restrictionsComplementaire += "AND arts_nom_article LIKE '%'+?+'%'";
                filtreSaisi = true;

            }
            if (noCategorie != 0) {
                restrictionsComplementaire += "AND cats_no_categorie=?";
                categorieSelect = true;
            }
            //Preparation de la requete
            requestSql = SELECT_BY_ID_AND_ETATENCHERE + restrictionsComplementaire;

            PreparedStatement ptt = cxn.prepareStatement(requestSql);
            ptt.setInt(1, idUtilisateur);

            if (filtreSaisi && categorieSelect) {
                ptt.setString(2, filtre);
                ptt.setInt(3, noCategorie);
            }
            if (filtreSaisi && !categorieSelect) {
                ptt.setString(2, filtre);
            }
            if (!filtreSaisi && categorieSelect) {
                ptt.setInt(2, noCategorie);
            }

            ResultSet rs = ptt.executeQuery();
            while (rs.next()) {
                Article infoArticle = new Article();
                infoArticle.setIdArticle(rs.getInt("arts_no_articles"));
                infoArticle.setPrix(Math.max(rs.getInt("arts_prix_initial"), Math.max(rs.getInt("arts_prix_vente"),
                        rs.getInt("encs_montant_enchere"))));
                infoArticle.setNomArticle(rs.getString("arts_nom_article"));
                infoArticle.setDateFinEncheres(rs.getDate("arts_date_fin_encheres").toLocalDate());
                infoArticle.setPseudoVendeur(rs.getString("utils_pseudo"));

                listInfoArticle.add(infoArticle);
            }
            ptt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_ARTICLE_ECHEC);
        }

        return listInfoArticle;
    }

    /**
     * retourne la liste des infoArticles des encheres ouvertes (en cours) en fonction du filtre saisie et de la catégorie selectionnée
     *
     * @param idUtilisateur
     * @param filtre
     * @param noCategorie
     * @return
     */
    public List<Article> selectByDateSupDebEnchereAndInfFinEnchere(int idUtilisateur, String filtre, int noCategorie) {
        List<Article> listInfoArticle = new ArrayList<>();
        String requestSql = null;
        String restrictionsComplementaire = "";
        Boolean filtreSaisi = false;
        Boolean categorieSelect = false;

        try (
                Connection cxn = ConnectionProvider.getConnection();
        ) {
            if (filtre != "") {
                restrictionsComplementaire += "AND arts_nom_article LIKE '%'+?+'%'";
                filtreSaisi = true;

            }
            if (noCategorie != 0) {
                restrictionsComplementaire += "AND cats_no_categorie=?";
                categorieSelect = true;
            }

            //Preparation de la requete
            requestSql = SELECT_BY_DATE_SUP_DEB_ENCH_AND_INF_FIN_ENCHERE + restrictionsComplementaire;

            PreparedStatement ptt = cxn.prepareStatement(requestSql);
            ptt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ptt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));

            if(filtreSaisi && categorieSelect){
                ptt.setString(3, filtre);
                ptt.setInt(4, noCategorie);
            }

            if (filtreSaisi && !(categorieSelect)) {
                ptt.setString(3, filtre);
            }

            if (!(filtreSaisi) && categorieSelect) {
                ptt.setInt(3, noCategorie);
            }

            ResultSet rs = ptt.executeQuery();
            while (rs.next()) {
                Article infoArticle = new Article();
                infoArticle.setIdArticle(rs.getInt("arts_no_articles"));
                infoArticle.setPrix(Math.max(rs.getInt("arts_prix_initial"), Math.max(rs.getInt("arts_prix_vente"),
                        rs.getInt("encs_montant_enchere"))));
                infoArticle.setNomArticle(rs.getString("arts_nom_article"));
                infoArticle.setDateFinEncheres(rs.getDate("arts_date_fin_encheres").toLocalDate());
                infoArticle.setPseudoVendeur(rs.getString("utils_pseudo"));

                listInfoArticle.add(infoArticle);
            }
            ptt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_ARTICLE_ECHEC);
        }

        return listInfoArticle;
    }

    /**
     * retourne la liste des infoArticles des ventes terminées de l'utilisateur (uniquement les articles qu'il a mis en vente) en fonction du filtre saisie et de la catégorie selectionnée.
     *
     * @param idUtilisateur
     * @param filtre
     * @param noCategorie
     * @return
     */
    public List<Article> selectByIdAndDateSupFinEnchere(int idUtilisateur, String filtre, int noCategorie) {
        List<Article> listInfoArticle = new ArrayList<>();
        String requestSql = null;
        String restrictionsComplementaire = "";
        Boolean filtreSaisi = false;
        Boolean categorieSelect = false;

        try (
                Connection cxn = ConnectionProvider.getConnection();
        ) {
            if (filtre != "") {
                restrictionsComplementaire += "AND arts_nom_article LIKE '%'+?+'%'";
                filtreSaisi = true;

            }
            if (noCategorie != 0) {
                restrictionsComplementaire += "AND cats_no_categorie=?";
                categorieSelect = true;
            }
            //Preparation de la requete
            requestSql = SELECT_BY_ID_DATE_SUP_FIN_ENCHERE + restrictionsComplementaire;

            PreparedStatement ptt = cxn.prepareStatement(requestSql);
            ptt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ptt.setInt(2, idUtilisateur);

            if (filtreSaisi && categorieSelect) {
                ptt.setString(3, filtre);
                ptt.setInt(4, noCategorie);
            }
            if (filtreSaisi && !categorieSelect) {
                ptt.setString(3, filtre);
            }
            if (!filtreSaisi && categorieSelect) {
                ptt.setInt(3, noCategorie);
            }

            ResultSet rs = ptt.executeQuery();
            while (rs.next()) {
                Article infoArticle = new Article();
                infoArticle.setIdArticle(rs.getInt("arts_no_articles"));
                infoArticle.setPrix(Math.max(rs.getInt("arts_prix_initial"), Math.max(rs.getInt("arts_prix_vente"),
                        rs.getInt("encs_montant_enchere"))));
                infoArticle.setNomArticle(rs.getString("arts_nom_article"));
                infoArticle.setDateFinEncheres(rs.getDate("arts_date_fin_encheres").toLocalDate());
                infoArticle.setPseudoVendeur(rs.getString("utils_pseudo"));

                listInfoArticle.add(infoArticle);
            }
            ptt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_ARTICLE_ECHEC);
        }

        return listInfoArticle;
    }

    /**
     * retourne la liste des infoArticles des ventes en cours non débutées
     *
     * @param idUtilisateur
     * @param filtre
     * @param noCategorie
     * @return
     */
    public List<Article> selectByIdDateInfDebEnchere(int idUtilisateur, String filtre, int noCategorie) {
        List<Article> listInfoArticle = new ArrayList<>();
        String requestSql = null;
        String restrictionsComplementaire = "";
        Boolean filtreSaisi = false;
        Boolean categorieSelect = false;

        try (
                Connection cxn = ConnectionProvider.getConnection();
        ) {

            if (filtre != "") {
                restrictionsComplementaire += "AND arts_nom_article LIKE '%'+?+'%'";
                filtreSaisi = true;

            }
            if (noCategorie != 0) {
                restrictionsComplementaire += "AND cats_no_categorie=?";
                categorieSelect = true;
            }
            //Preparation de la requete
            requestSql = SELECT_BY_ID_DATE_INF_DEB_ENCHERE + restrictionsComplementaire;

            PreparedStatement ptt = cxn.prepareStatement(requestSql);
            ptt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ptt.setInt(2, idUtilisateur);

            if (filtreSaisi && categorieSelect) {
                ptt.setString(3, filtre);
                ptt.setInt(4, noCategorie);
            }
            if (filtreSaisi && !categorieSelect) {
                ptt.setString(3, filtre);
            }
            if (!filtreSaisi && categorieSelect) {
                ptt.setInt(3, noCategorie);
            }

            ResultSet rs = ptt.executeQuery();
            while (rs.next()) {
                Article infoArticle = new Article();
                infoArticle.setIdArticle(rs.getInt("arts_no_articles"));
                infoArticle.setPrix(Math.max(rs.getInt("arts_prix_initial"), Math.max(rs.getInt("arts_prix_vente"),
                        rs.getInt("encs_montant_enchere"))));
                infoArticle.setNomArticle(rs.getString("arts_nom_article"));
                infoArticle.setDateFinEncheres(rs.getDate("arts_date_fin_encheres").toLocalDate());
                infoArticle.setPseudoVendeur(rs.getString("utils_pseudo"));

                listInfoArticle.add(infoArticle);
            }
            ptt.close();
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_ARTICLE_ECHEC);
        }

        return listInfoArticle;
    }

    /**
     * return la liste des infoArticles des ventes en cours en fonction du filtre saisie et de la catégorie selectionnée
     *
     * @param idUtilisateur
     * @param filtre
     * @param noCategorie
     * @return
     */
    public List<Article> selectByIdAndDatesEnchere(int idUtilisateur, String filtre, int noCategorie) {
        List<Article> listInfoArticle = new ArrayList<>();
        String requestSql = null;
        String restrictionsComplementaire = "";
        Boolean filtreSaisi = false;
        Boolean categorieSelect = false;

        try (
                Connection cxn = ConnectionProvider.getConnection();
        ) {

            if (filtre != "") {
                restrictionsComplementaire += "AND arts_nom_article LIKE '%'+?+'%'";
                filtreSaisi = true;

            }
            if (noCategorie != 0) {
                restrictionsComplementaire += "AND cats_no_categorie=?";
                categorieSelect = true;
            }
            //Preparation de la requete
            requestSql = SELECT_BY_ID_AND_DATES_ENCHERE + restrictionsComplementaire +" ORDER BY arts_no_articles,  encs_montant_enchere DESC";

            PreparedStatement ptt = cxn.prepareStatement(requestSql);
            ptt.setInt(1, idUtilisateur);
            ptt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            ptt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));

            if (filtreSaisi && categorieSelect) {
                ptt.setString(4, filtre);
                ptt.setInt(5, noCategorie);
            }
            if (filtreSaisi && !categorieSelect) {
                ptt.setString(4, filtre);
            }
            if (!filtreSaisi && categorieSelect) {
                ptt.setInt(4, noCategorie);
            }

            ResultSet rs = ptt.executeQuery();
            while (rs.next()) {
                Article infoArticle = new Article();
                infoArticle.setIdArticle(rs.getInt("arts_no_articles"));
                infoArticle.setPrix(Math.max(rs.getInt("arts_prix_initial"), Math.max(rs.getInt("arts_prix_vente"),
                        rs.getInt("encs_montant_enchere"))));
                infoArticle.setNomArticle(rs.getString("arts_nom_article"));
                infoArticle.setDateFinEncheres(rs.getDate("arts_date_fin_encheres").toLocalDate());
                infoArticle.setPseudoVendeur(rs.getString("utils_pseudo"));

                listInfoArticle.add(infoArticle);
            }
            ptt.close();
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_ARTICLE_ECHEC);
        }

        //retrait des doublons
        int i=0;
        while((i+1)<listInfoArticle.size()){
            if (listInfoArticle.get(i).getIdArticle() == listInfoArticle.get(i + 1).getIdArticle()) {
                listInfoArticle.remove(i + 1);
                i--;
            }
            i++;
        }

        return listInfoArticle;
    }

}
