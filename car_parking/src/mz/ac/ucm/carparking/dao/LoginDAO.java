package mz.ac.ucm.carparking.dao;

import mz.ac.ucm.carparking.connection.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import mz.ac.ucm.carparking.domain.Funcionario;
import mz.ac.ucm.carparking.domain.Login;

public class LoginDAO {

    private final String CREATE = "INSERT INTO login(username,u_password,tipo,idfuncionario) VALUES(?,?,?,?)";
    private final String SELECT = "SELECT * FROM login";
    private final String DELETE = "DELETE FROM login WHERE username=?";

    //método para autenticar usuário no login
    public ResultSet authUser(Login user1) {

        Connection myConnection = null;
        PreparedStatement myPreparedStatement = null;
        ResultSet myResultSet = null;

        String sql = "SELECT * from login WHERE username=? AND u_password=? AND tipo=? ";

        try {
            myConnection = ConnectionFactory.getConnection();

            myPreparedStatement = myConnection.prepareStatement(sql);
            myPreparedStatement.setString(1, user1.getUsername());
            myPreparedStatement.setString(2, user1.getU_password());
            myPreparedStatement.setString(3, String.valueOf(user1.getTipo()));

            myResultSet = myPreparedStatement.executeQuery();

            return myResultSet;

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "UserDAO: " + error, "ERROR", JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }

    public void create(Funcionario funcionario, JComboBox list, JTextField username, JTextField password) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(CREATE);
            statement.setString(1, username.getText());
            statement.setString(2, password.getText());
            statement.setString(3, String.valueOf(list.getSelectedItem()));
            statement.setInt(4, funcionario.getIdFuncionario());

            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "USER criado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Login> read() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Login> logins = new ArrayList<>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(this.SELECT);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Login login = new Login();
                login.setUsername(resultSet.getString("username")); 
                login.setTipo(resultSet.getString("tipo"));
                login.setIdFuncionario(resultSet.getInt("idfuncionario"));

                logins.add(login);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return logins;
    }

    public void delete(JTextField texto) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(DELETE);
            statement.setString(1, texto.getText());

            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "USER removido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
}
