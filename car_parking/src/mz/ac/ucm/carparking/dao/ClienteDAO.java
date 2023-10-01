package mz.ac.ucm.carparking.dao;

import mz.ac.ucm.carparking.domain.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import mz.ac.ucm.carparking.connection.ConnectionFactory;
import mz.ac.ucm.carparking.domain.Carro;

public class ClienteDAO {

    private final String CREATE = "INSERT INTO cliente(idcliente,nome,apelido,contacto,sexo) VALUES(?,?,?,?,?)";
    private final String SELECT = "SELECT * FROM cliente";
    private final String DELETE = "DELETE FROM cliente WHERE idcliente=?";
    private final String UPDATE = "UPDATE cliente SET nome=?,apelido=?,contacto=?,sexo=? WHERE idcliente=?";

    public void create(Cliente cliente, JComboBox list) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(CREATE);

            int nextId = 1; // Default value if list is empty

            // Check if the list is not empty and the last item is not null
            if (list.getItemCount() > 0 && list.getItemAt(list.getItemCount() - 1) != null) {
                nextId = Integer.parseInt((String) list.getItemAt(list.getItemCount() - 1)) + 1;
            }

            statement.setInt(1, nextId);
            statement.setString(2, cliente.getNome());
            statement.setString(3, cliente.getApelido());
            statement.setString(4, cliente.getContacto());
            statement.setString(5, cliente.getSexo());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }


    public List<Cliente> read() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Cliente> clientes = new ArrayList<>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(this.SELECT);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(resultSet.getInt("idcliente"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setApelido(resultSet.getString("apelido"));
                cliente.setSexo(resultSet.getString("sexo"));
                cliente.setContacto(resultSet.getString("contacto"));

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return clientes;
    }
    
    public void delete(JComboBox list) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, Integer.parseInt((String.valueOf( list.getSelectedItem()))));
            
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void update(Cliente cliente) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getApelido());
            statement.setString(3, cliente.getContacto());
            statement.setString(4, cliente.getSexo());
            statement.setInt(5, cliente.getIdCliente());
            
            
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
}
