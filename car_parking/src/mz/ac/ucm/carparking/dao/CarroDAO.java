package mz.ac.ucm.carparking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import mz.ac.ucm.carparking.connection.ConnectionFactory;
import mz.ac.ucm.carparking.domain.Carro;

public class CarroDAO {

    private final String CREATE = "INSERT INTO carro(matricula,cor,marca,modelo,ano,proprietario,amount_paid) VALUES(?,?,?,?,?,?,?)";
    private final String SELECT = "SELECT * FROM carro";
    private final String DELETE = "DELETE FROM carro WHERE matricula=?";
    private final String UPDATE = "UPDATE carro SET cor=?,marca=?,modelo=?,ano=?,proprietario=?,amount_paid=? WHERE matricula=?";

    public void create(Carro carro) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(CREATE);
            statement.setString(1, carro.getMatricula());
            statement.setString(2, carro.getCor());
            statement.setString(3, carro.getMarca());
            statement.setString(4, carro.getModelo());
            statement.setInt(5, carro.getAno());
            statement.setInt(6, carro.getProprietario());
            statement.setString(7, carro.getAmountPaid());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Carro> read() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Carro> carros = new ArrayList<>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(this.SELECT);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Carro carro = new Carro();
                carro.setMatricula(resultSet.getString("matricula"));
                carro.setCor(resultSet.getString("cor"));
                carro.setMarca(resultSet.getString("marca"));
                carro.setModelo(resultSet.getString("modelo"));
                carro.setAno(resultSet.getInt("ano"));
                carro.setProprietario(resultSet.getInt("proprietario"));
                carro.setAmountPaid(resultSet.getString("amount_paid"));

                carros.add(carro);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return carros;
    }

    public void delete(JComboBox list) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(DELETE);
            statement.setString(1, String.valueOf(list.getSelectedItem()));
            
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Carro removido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void update(Carro carro) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, carro.getCor());
            statement.setString(2, carro.getMarca());
            statement.setString(3, carro.getModelo());
            statement.setInt(4, carro.getAno());
            statement.setInt(5, carro.getProprietario());
            statement.setString(6, carro.getAmountPaid());
            statement.setString(7,carro.getMatricula());
            
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Carro atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
}
