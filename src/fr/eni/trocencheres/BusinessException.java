package fr.eni.trocencheres;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception{
    private  List<Integer> listeCodesErreur;

    public BusinessException() {
        super();
        this.listeCodesErreur = new ArrayList<>();
    }

    /**
     *
     * @param code Code de l'erreur.
     * Doit avoir un message associé dans un fichier properties.
     */
    public void ajouterErreur(int code)
    {
        if(!this.listeCodesErreur.contains(code))
        {
            this.listeCodesErreur.add(code);
        }
    }

    /**
     * Savoir si des erreurs existent
     * @return
     */
    public boolean possedeErreurs()
    {
        //test de la liste
        return this.listeCodesErreur.size()>0;
    }

    /**
     * Récupère la liste des erreurs sous forme de liste
     * @return
     */
    public List<Integer> getListeCodesErreur()
    {

        return this.listeCodesErreur;
    }

}
