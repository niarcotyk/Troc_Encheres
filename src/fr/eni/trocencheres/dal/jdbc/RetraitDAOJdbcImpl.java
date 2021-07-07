package fr.eni.trocencheres.dal.jdbc;

import fr.eni.trocencheres.BusinessException;
import fr.eni.trocencheres.bo.Retrait;
import fr.eni.trocencheres.dal.ConnectionProvider;
import fr.eni.trocencheres.dal.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RetraitDAOJdbcImpl implements DAO<Retrait> {
    private static BusinessException businessException = new BusinessException();
    private static final String INSERT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) values (?,?,?,?)";
    private static final String SELECTBYID = "SELECT no_article, rue, code_postal, ville FROM RETRAITS WHERE no_article = ?";
    private static final String SELECTALL = "SELECT no_article, rue, code_postal, ville FROM RETRAITS";
    private static final String UPDATE = "UPDATE RETRAITS SET rue = ?, code_postal = ?, ville = ? WHERE no_article=?";
    private static final String DELETE = "DELETE RETRAITS WHERE no_article = ?";

    @Override
    public List<Retrait> selectAll() throws BusinessException {
        List<Retrait> listeRetraits = new ArrayList<>();
        try (Connection cnx = ConnectionProvider.getConnection();
             Statement stt = cnx.createStatement()) {
            ResultSet rs = stt.executeQuery(SELECTALL);
            while(rs.next()){
                Retrait retrait = new Retrait();
                retrait.setId(rs.getInt("no_article"));
                retrait.setRue(rs.getString("rue"));
                retrait.setCodePostal(rs.getString("code_postal"));
                retrait.setVille(rs.getString("ville"));
                listeRetraits.add(retrait);
            }
            rs.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_RETRAIT_ECHEC);
        }
        return listeRetraits;
    }

    @Override
    public Retrait selectById(int id) throws BusinessException {
        Retrait retrait = null;
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(SELECTBYID)) {
            pstt.setInt(1,id);
            ResultSet rs = pstt.executeQuery();
            if(rs.next()){
                retrait = new Retrait();
                retrait.setId(rs.getInt("no_article"));
                retrait.setRue(rs.getString("rue"));
                retrait.setCodePostal(rs.getString("code_postal"));
                retrait.setVille(rs.getString("ville"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.LECTURE_RETRAIT_ECHEC);
            throw businessException;
        }
        return null;
    }

    /**
     * Insertion de l'objet Retrait dans la table RETRAITS
     * @param retrait
     * @throws BusinessException
     */
    @Override
    public void insert(Retrait retrait) throws BusinessException {
        if(retrait == null){
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.INSERT_OBJET_NULL);
            throw businessException;
        }
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstt.setInt(1, retrait.getId());
            pstt.setString(2, retrait.getRue());
            pstt.setString(3, retrait.getCodePostal());
            pstt.setString(4, retrait.getVille());
            pstt.executeUpdate();
            ResultSet rs = pstt.getGeneratedKeys();
            if(rs.next()){
                retrait.setId(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.INSERT_RETRAIT_ECHEC);
            throw businessException;
        }
    }

    @Override
    public void update(Retrait retrait) throws BusinessException {
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(UPDATE)) {
            pstt.setString(1, retrait.getRue());
            pstt.setString(2, retrait.getCodePostal());
            pstt.setString(3, retrait.getVille());
            pstt.setInt(4, retrait.getId());
            pstt.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.UPDATE_RETRAIT_ECHEC);
            throw businessException;
        }

    }

    /**
     * Supprimer un lieu de retrait
     * @param id
     * @throws BusinessException
     */
    @Override
    public void delete(int id) throws BusinessException {
        try (Connection cnx = ConnectionProvider.getConnection();
             PreparedStatement pstt = cnx.prepareStatement(DELETE)) {
            pstt.setInt(1,id);
            pstt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            businessException.ajouterErreur(fr.eni.dal.CodesResultatDAL.DELETE_RETRAIT_ECHEC);
            throw businessException;
        }

    }
}

