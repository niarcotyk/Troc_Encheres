package fr.eni.trocencheres.servlet;

public class CodesResultatServlet {
    /**
     * Echec général quand tentative d'ajouter un objet null
     */
    public static final int REGLE_UTILISATEUR_NOUVEAUMOTDEPASSE_ERREUR = 30000;
    /**
     * Echec lors de l'insertion du compte utilisateur dans la base de données.
     */
    public static final int RECORD_UTILISATEUR = 30051;
    /**
     * Echec lors de l'import des données
     */
    public static final int IMPORT_UTILISATEUR = 30052;

    public static final int ENCHERE_MONTANT_ECHEC = 30053;
    /**
     * Echec lors de la lecture des données utilisateur
     */
    public static final int LECTURE_UTILISATEUR_ECHEC = 30054;

    public static final int ERREUR_REGLE_DE_GESTION = 30055;
    /**
     * Les mot de passe ne sont pas identiques
     */
    public static final int MDP_DIFFERENT = 30100;

}
