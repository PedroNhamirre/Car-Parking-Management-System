package mz.ac.ucm.carparking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mz.ac.ucm.carparking.connection.ConnectionFactory;
import mz.ac.ucm.carparking.domain.Funcionario;

public class FuncionarioDAO {

    private final String SELECT = "SELECT * FROM funcionario";
    
    public List<Funcionario> read() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(this.SELECT);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(resultSet.getInt("idfuncionario"));
                funcionario.setNome(resultSet.getString("nome"));
                funcionario.setApelido(resultSet.getString("apelido"));
                funcionario.setCargo(resultSet.getString("cargo"));

                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        return funcionarios;
    }
}
