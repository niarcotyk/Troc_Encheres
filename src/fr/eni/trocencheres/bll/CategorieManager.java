package fr.eni.trocencheres.bll;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bo.Categorie;
import fr.eni.trocencheres.dal.DAO;
import fr.eni.trocencheres.dal.DAOFactory;

import java.util.ArrayList;
import java.util.List;

public class CategorieManager {
    private DAO<Categorie> categorieDAO;
    private BusinessException businessException = new BusinessException();

    public CategorieManager() {
        categorieDAO = DAOFactory.getCategorieDAO();
    }

    /**
     * Affiche toutes les catégories pour le remplissage de la combobox
     * @return
     */
    public List<Categorie> AfficherCategories(){
        List<Categorie> listeCategorie = new ArrayList<>();
        try {
            listeCategorie = categorieDAO.selectAll();
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return listeCategorie;
    }

    /**
     * Retourne le libellé d'une catégorie en fonction de son identifiant
     * @param id
     * @return
     * @throws BusinessException
     */
    public Categorie ChoisirCategorie(int id) throws BusinessException {
        Categorie cat = null;
        //Vérification que l'identifiant n'est pas vide
        if(id!=0){
            cat = categorieDAO.selectById(id);
        }else{
            businessException.ajouterErreur(CodesResultatBLL.REGLE_CATEGORIES_ID_ERREUR);
        }
        return cat;
    }
}
