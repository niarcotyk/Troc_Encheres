package fr.eni.trocencheres.bll;

public class CodesResultatBLL {
    /**
     * Echec la rue du lieu de retrait ne respecte pas les règles définies
     */
    public static final int REGLE_RETRAITS_RUE_ERREUR = 20000;
    /**
     * Echec le code postal du lieu de retrait ne respecte pas les règles définies
     */
    public static final int REGLE_RETRAITS_CODEPOSTAL_ERREUR = 20001;
    /**
     * Echec la ville du lieu de retrait ne respecte pas les règles définies
     */
    public static final int REGLE_RETRAITS_VILLE_ERREUR = 20002;
    /**
     * Echec le pseudo de l'Utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_UTILISATEURS_PSEUDO_ERREUR = 20003;
    /**
     * Echec le nom de l'Utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_UTILISATEURS_NOM_ERREUR = 20004;
    /**
     * Echec le prenom de l'Utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_UTILISATEURS_PRENOM_ERREUR = 20005;
    /**
     * Echec l'email de l'Utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_UTILISATEURS_EMAIL_ERREUR = 20006;
    /**
     * Echec le telephone de l'Utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_UTILISATEURS_TEL_ERREUR = 20007;
    /**
     * Echec la rue de l'Utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_UTILISATEURS_RUE_ERREUR = 20008;
    /**
     * Echec le code postal de l'Utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_UTILISATEURS_CODEPOSTAL_ERREUR = 20009;
    /**
     * Echec la ville de l'Utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_UTILISATEURS_VILLE_ERREUR = 20010;
    /**
     * Echec le mot de passe de l'Utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_UTILISATEURS_MOTDEPASSE_ERREUR = 20011;
    /**
     * Echec le pseudo de l'Utilisateur ne respecte pas les règles définies
     */
    public static final int UTILISATEURS_PSEUDO_ERREUR = 20012;
    /**
     * Echec l'email de l'Utilisateur ne respecte pas les règles définies
     */
    public static final int UTILISATEURS_EMAIL_ERREUR = 20013;

    public static final int UTILISATEUR_PSEUDO_NONRENSEIGNE = 20014;

    public static final int UTILISATEURS_PSEUDO_PWD_ERREUR = 20015;
    /**
     * Echec suite à import des données à la bll
     */
    public static final int IMPORT_BLL_ENCHERE = 20050;
    public static final int IMPORT_BLL_ENCHERES_GAGNEES=20051;
    public static final int UTILISATEUR_CONNECTION_IMPOSSIBLE = 20052;
    public static final int UPDATE_BLL_ENCHERES = 20053;

    public static final int PSEUDO_UTILISATEUR_ECHEC = 20054;
    /**
     * Echec de la sélection par Id de catégorie
     */
    public static final int REGLE_CATEGORIES_ID_ERREUR = 20100;
    /**
     * Echec de la sélection par Id de catégorie
     */
    public static final int REGLE_ARTICLE_PRIX_INITIAL_ERREUR = 20101;
    /**
     * Echec de la sélection par Id de catégorie
     */
    public static final int REGLE_ARTICLE_CATEGORIE_NUL_ERREUR = 20102;
    /**
     * Echec de la sélection par Id de catégorie
     */
    public static final int REGLE_ARTICLE_DESCRIPTION_SAISIE_ERREUR = 20103;
    /**
     * Echec de la sélection par Id de catégorie
     */
    public static final int REGLE_ARTICLE_NOM_SAISIE_ERREUR = 20104;
    /**
     * Echec de la date de début enchère
     */
    public static final int REGLE_ARTICLE_DATE_DEBUT_ENCHERE = 20105;
    /**
     * Echec de la date de fin enchère
     */
    public static final int REGLE_ARTICLE_DATE_FIN_ENCHERE = 20106;
    /**
     * Echec de la date de début par rapport à la date de fin d'enchère
     */
    public static final int REGLE_ARTICLE_DATE_DEBUT_DATE_FIN_ENCHERE = 20107;
}
