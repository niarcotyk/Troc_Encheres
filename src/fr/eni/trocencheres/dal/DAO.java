package fr.eni.trocencheres.dal;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bo.Article;
import fr.eni.trocencheres.bo.Enchere;
import fr.eni.trocencheres.bo.Utilisateur;

import java.util.List;

public interface DAO<T> {

    List<T> selectAll() throws BusinessException;
    T selectById(int id) throws BusinessException;
    void insert(T object) throws BusinessException;
    void update(T object) throws BusinessException;
    void delete(int id) throws BusinessException;

    default void restituerPoints(int points, int idAcquereur) throws BusinessException {
    }

    default boolean verifMail(String email) throws BusinessException {
        return false;
    }
    default boolean verifUtilisateur(String nom) throws BusinessException {
        return false;
    }
    default Utilisateur getUtilisateur(String pseudo, String pwd) throws BusinessException {
        return null;
    }
    default List<Article> selectByIdDateFinEnchere(int idUtilisateur, int idCategorie) throws BusinessException{
        return null;
    }

    /**
     * Selection d'un article avec les informations pour PageEncherir
     * @param id
     * @return
     * @throws BusinessException
     */
    default List<Article> selectByEnchere(int id) throws BusinessException {
        return null;
    }

    default Article selectByEnchereRemportee(int id) throws BusinessException {
        return null;
    }


    default List<Article> selectByIdAndDatesEnchere(int idUtilisateur, String filtre, int noCategorie) throws BusinessException{return null;}
    default List<Article> selectByIdDateInfDebEnchere(int idUtilisateur, String filtre, int noCategorie) throws BusinessException{return null;}
    default List<Article> selectByIdAndDateSupFinEnchere(int idUtilisateur, String filtre, int noCategorie) throws BusinessException{return null;}

    default List<Article> selectByDateSupDebEnchereAndInfFinEnchere(int idUtilisateur, String filtre, int noCategorie) throws BusinessException{return null;}
    default List<Article> selectByIdAndEtatEnchere(int idUtilisateur, String filtre, int noCategorie) throws BusinessException{return null;}
    default List<Article> selectByIdDateDerEnchere(int idUtilisateur, String filtre, int noCategorie) throws BusinessException{return null;}
    default List<Article> rechercheParFiltreEtNoCategorie(int idUtilisateur, String filtre, int noCategorie)throws BusinessException{return null;}

    default Utilisateur selectByPseudo(String pseudo) throws BusinessException {return null;}

    default List<Enchere> selectByAcqEtArtVendu(int acquereur) throws BusinessException {return null;}

}
