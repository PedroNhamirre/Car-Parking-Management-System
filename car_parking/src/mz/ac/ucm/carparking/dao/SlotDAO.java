package mz.ac.ucm.carparking.dao;

import java.util.List;
import mz.ac.ucm.carparking.domain.Slot;
import mz.ac.ucm.carparking.connection.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import mz.ac.ucm.carparking.domain.Carro;

public class SlotDAO {

    private final String SELECT = "SELECT * FROM slot";
    private final String CREATE = "INSERT INTO slot(idslot,status) VALUES(?,?)";
    private final String DELETE = "DELETE FROM slot where idslot=?";

    public void create(Slot slot, JComboBox list) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(CREATE);
            statement.setInt(1, Integer.parseInt((String) list.getItemAt(list.getItemCount() - 1)) + 1);
            statement.setString(2, slot.getStatus());

            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Slot salvo com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public List<Slot> read() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Slot> slots = new ArrayList<>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(this.SELECT);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Slot slot = new Slot();
                slot.setIdSlot(resultSet.getInt("idslot"));
                slot.setCarroMatricula(resultSet.getString("carro_matricula"));
                slot.setStatus(resultSet.getString("status"));

                slots.add(slot);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return slots;
    }

    public void updateMatricula(Slot slot, JComboBox list, Carro carro) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("UPDATE slot SET carro_matricula=?,status=? WHERE idslot=?");
            statement.setString(1, carro.getMatricula());
            statement.setString(2, "occupied");
            statement.setString(3, String.valueOf(list.getSelectedItem()));

            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "salvo com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void delete(JComboBox list) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, Integer.parseInt((String.valueOf( list.getItemAt(list.getItemCount() - 1)))));
            
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Slot removido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
}
