package mz.ac.ucm.carparking.connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

    //linha abaixo chama o driver
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    //caminho do banco de dados, porta
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/carparking1";

    //nome do usuário do MySQL
    private static final String DATABASE_USER = "root";

    //senha do banco de dados
    private static final String DATABASE_PASSWORD = "pedrinho2003";

    //método responsável por estabelecer a conexão com a base de dados
    public static Connection getConnection() {

        try {
            //faz com que a classe seja carregada pela JVM
            Class.forName(DRIVER);

            //cria a conexão com o banco de dados
            Connection myConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);

            return myConnection;
        } catch (ClassNotFoundException | SQLException e) {
            return null;
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void closeConnection(Connection connection, PreparedStatement statement) {

        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        closeConnection(connection);

    }

    public static void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet) {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

        closeConnection(connection, statement);
    }

}
