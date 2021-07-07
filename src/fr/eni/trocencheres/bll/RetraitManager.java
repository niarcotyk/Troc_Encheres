package fr.eni.trocencheres.bll;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bo.Retrait;
import fr.eni.trocencheres.dal.DAO;
import fr.eni.trocencheres.dal.DAOFactory;

import java.util.ArrayList;
import java.util.List;

public class RetraitManager {
    private final DAO retraitDao;
    private static BusinessException businessException = new BusinessException();

    public RetraitManager() {
        retraitDao = DAOFactory.getRetraitDAO();
    }

    /**
     * Ajouter le lieu dans la table Retrait avec vérification de celui-ci
     * @param retrait
     * @return
     * @throws BusinessException
     */
    public Retrait ajouterLieu(Retrait retrait) throws BusinessException {
        validerAdresse(retrait,  businessException);
        if (!businessException.possedeErreurs()) {
            retraitDao.insert(retrait);
        }
        else{
            throw businessException;
        }
        return retrait;
    }

    /**
     * Recherche par Id retrait
     * @param id
     * @return objet Retrait
     * @throws BusinessException
     */
    public Retrait rechercheParCle(int id) throws BusinessException {
        Retrait retrait = (Retrait) retraitDao.selectById(id);
        return retrait;
    }

    /**
     * Modifier le lieu retrait
     * @param retrait
     * @throws BusinessException
     */
    public void modifierRetrait(Retrait retrait) throws BusinessException {
        validerAdresse(retrait,  businessException);
        if(!businessException.possedeErreurs()){
            retraitDao.update(retrait);
        }
        else{
            throw businessException;
        }
    }

    /**
     * Liste tous les lieux de retrait
     * @return
     */
    public List<Retrait> trouverTous(){
        List<Retrait> listeRetraits = new ArrayList<>();
        try {
            listeRetraits = retraitDao.selectAll();
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        return listeRetraits;
    }

    /**
     * Méthode pour vérifier que les champs sont remplis car obligatoires
     * @param retrait
     * @param businessException
     */
    public void validerAdresse(Retrait retrait, BusinessException businessException){
        if(retrait.getRue() == null || retrait.getRue().trim().equals("") || retrait.getRue().length() > 30){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_RETRAITS_RUE_ERREUR);
        }
        if(retrait.getCodePostal() == null ||retrait.getCodePostal().trim().equals("") || retrait.getCodePostal().length() > 5){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_RETRAITS_CODEPOSTAL_ERREUR);
        }
        if(retrait.getVille() == null ||retrait.getVille().trim().equals("") || retrait.getVille().length() > 30){
            businessException.ajouterErreur(CodesResultatBLL.REGLE_RETRAITS_VILLE_ERREUR);
        }
    }
}
