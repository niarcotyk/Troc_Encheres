package fr.eni.trocencheres.bll;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bo.Enchere;
import fr.eni.trocencheres.bo.Utilisateur;
import fr.eni.trocencheres.dal.DAO;
import fr.eni.trocencheres.dal.DAOFactory;

import java.util.List;

public class EnchereManager {

    private DAO<Enchere> enchereDAO;
    private BusinessException businessException = new BusinessException();
    private DAO<Utilisateur> utilisateurDAO;

    public EnchereManager() {
        enchereDAO = DAOFactory.getEnchereDAO();
        utilisateurDAO = DAOFactory.getUtilisateurDAO();
    }

    /**
     * Renvoie toute la liste des enchères
     * @return
     * @throws BusinessException
     */
    public List<Enchere> importEncheres() throws BusinessException {
        List<Enchere> listEnchere=null;

        try {
            listEnchere = enchereDAO.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            businessException.ajouterErreur(CodesResultatBLL.IMPORT_BLL_ENCHERE);

        }
        return listEnchere;
    }

    /**
     *
     * @param id_acquereur
     * @return
     * @throws BusinessException
     */
    public List<Enchere> encheresGagnées(int id_acquereur) throws BusinessException {
        List<Enchere> listEnchere = null;

        try {
            listEnchere = enchereDAO.selectByAcqEtArtVendu(id_acquereur);
        } catch (Exception e) {
            e.printStackTrace();
            businessException.ajouterErreur(CodesResultatBLL.IMPORT_BLL_ENCHERES_GAGNEES);
        }
        return listEnchere;
    }

    /**
     *
     * @param enchere
     * @throws BusinessException
     */
    public void miseAJourEnchere(Enchere enchere) throws BusinessException{
        try {
            //Vérification du montant enchère avec crédit dispo de l'utilisateur
            //Création de l'objet Utilisateur
            Utilisateur util = utilisateurDAO.selectById(enchere.getIdUtilisateur());
            if(enchere.getMontantEnchere() < util.getCredit()) {
                enchereDAO.update(enchere);
            }else{
                businessException.ajouterErreur(CodesResultatBLL.UPDATE_BLL_ENCHERES);
            }

        } catch (BusinessException e) {
            e.printStackTrace();
            businessException.ajouterErreur(CodesResultatBLL.UPDATE_BLL_ENCHERES);
        }
    }
}
