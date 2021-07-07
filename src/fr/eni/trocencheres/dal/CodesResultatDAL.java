package fr.eni.dal;

public class CodesResultatDAL {
    /**
     * Echec général quand tentative d'ajouter un objet null
     */
    public static final int INSERT_OBJET_NULL=10000;

    /**
     * Echec général quand erreur non gérée à l'insertion
     */
    public static final int INSERT_OBJET_ECHEC=10001;

    /**
     * Echec de la lecture des lieux de retrait
     */
    public static final int LECTURE_RETRAIT_ECHEC = 10002;
    /**
     * Echec de l'insertion d'un lieu de retrait
     */
    public static final int INSERT_RETRAIT_ECHEC = 10003;
    /**
     * Echec à la modification d'un lieu de retrait
     */
    public static final int UPDATE_RETRAIT_ECHEC = 10004;
    /**
     * Echec à la suppression d'un lieu de retrait
     */
    public static final int DELETE_RETRAIT_ECHEC = 10005;
    /**
     * Echec lors de la lecture dans la table Utilisateurs
     */
    public static final int LECTURE_UTILISATEUR_ECHEC=10006;
    /**
     * Echec lors de l'insertion dans la table Utilisateurs
     */
    public static final int INSERT_UTILISATEUR_NULL=10007;
    /**
     * Echec lors de la mise à jour dans la table Utilisateurs
     */
    public static final int UPDATE_UTILISATEUR_ECHEC=10008;
    /**
     * Echec lors de la suppression dans la table Utilisateurs
     */
    public static final int DELETE_UTILISATEUR_ECHEC = 10009;
    /**
     * Erreur l'adresse mail est déjà présente
     */
    public static final int EMAIL_UTILISATEUR_ECHEC = 10010;
    /**
     * Erreur le pseudo est déjà présent
     */
    public static final int PSEUDO_UTILISATEUR_ECHEC = 10011;
    /**
     * Erreur lors de la restitution des crédits
     */
    public static final int RESTITUTIONPOINTS_UTILISATEUR_ECHEC = 10012;
    /**
     * Lors de l'import de la table enchere dans la dal
     */
    public static final int IMPORT_DAL_ENCHERE = 10050;
    /**
     * Lors de l'import des objets gagnés par l'utilisateur de la table enchere dans la dal
     */
    public static final int IMPORT_DAL_ENCHERE_GAGNE = 10051;
    /**
     * Import des ventes no débutées
     */
    public static final int IMPORT_VENTES_NON_DEBUTEES = 10052;
    /**
     * Import des ventes terminées impossible
     */
    public static final int IMPORT_VENTES_TERMINEES = 10053;
    /**
     *
     */
    public static final int UPDATE_DAL_ENCHERE = 10054;
    /**
     * Echec de la lecture de la table Articles
     */
    public static final int LECTURE_ARTICLE_ECHEC =10100;
    /**
     * Echec de la lecture de la table Articles selon un id
     */
    public static final int LECTURE_ID_ARTICLE_ECHEC=10101;
    /**
     * Echec lors de l'insertion d'un article
     */
    public static final int INSERT_ARTICLE_ECHEC =10102;
    /**
     * Echec lors de la modification d'un article
     */
    public static final int UPDATE_ARTICLE_ECHEC =10103;
    /**
     * Echec lors de la suppression d'un article
     */
    public static final int DELETE_ARTICLE_ERREUR =10104;
    /**
     * Echec de la lecture de la table Catégories
     */
    public static final int LECTURE_CATEGORIE_ECHEC=10105;
    /**
     * Echec de la lecture de la table Catégories select by id
     */
    public static final int LECTURE_ID_CATEGORIE_ECHEC=10106;
    /**
     * Echec lors de l'insertion de la table Catégories
     */
    public static final int INSERT_CATEGORIE_ECHEC=10107;
    /**
     * Echec lors de la mise à jour d'une catégorie
     */
    public static final int UPDATE_CATEGORIE_ECHEC=10108;
    /**
     * Echec lors de la suppression d'un catégorie
     */
    public static final int DELETE_CATEGORIE_ERREUR=10109;
    /**
     * Echec lors de la récupération de l'enchère terminée via un identifiant Utilisateur
     */
    public static final int LECTURE_ID_UTILISATEUR_ECHEC=10110;
    /**
     *
     */
    public static final int LECTURE_ENCHERE_ID_UTILISATEUR_ECHEC=10111;
}

