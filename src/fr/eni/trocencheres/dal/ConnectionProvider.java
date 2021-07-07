package fr.eni.trocencheres.dal;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.SQLException;
import javax.naming.Context;
import javax.sql.DataSource;
import java.sql.Connection;


public class ConnectionProvider {

    private static DataSource dataSource;

    static
    {
        Context context;
        try {
            context = new InitialContext();
            ConnectionProvider.dataSource = (DataSource)context.lookup("java:comp/env/jdbc/bdd");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossible d'accéder à la base de données");
        }
    }


    public static Connection getConnection() throws SQLException
    {
        return ConnectionProvider.dataSource.getConnection();
    }
}
