package br.com.serverappalunos.dao;

import br.com.serverappalunos.vo.Aluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author b1104586
 */
public class AlunoDAO {

    private static String insertaluno = "insert into alunos (cpf,nome,idade,logradouro,numero,complemento,bairro,cep,cidade,estado) values (?,?,?,?,?,?,?,?,?,?)";
    private static String pesquisarnr = "Select * from alunos where nr = ?";
    private static String alteraraluno = "update alunos set cpf=?, nome=?, idade=?, logradouro=?, numero=?, complemento=?, bairro=?,cep=?,cidade=?,estado=? where nr=?";
    private static String listaraluno = "select * from alunos order by nome";
    private String delete = "delete from alunos WHERE nr=?;";

    //Método DAO que salva um aluno recebendo os dados de aluno e endereço e retorna a id do aluno inserido - gerada pelo banco (chave primária)
    public int salvar(Aluno aluno) {

        Connection con = null;
        PreparedStatement stmtAdiciona = null;

        try {
            con = ConnectionFactory.getConnection();
            stmtAdiciona = con.prepareStatement(insertaluno, Statement.RETURN_GENERATED_KEYS);

            // seta os valores
            stmtAdiciona.setString(1, aluno.getCpf());
            stmtAdiciona.setString(2, aluno.getNome());
            stmtAdiciona.setInt(3, aluno.getIdade());

            //Obtém os campos do objeto endereço em aluno e acrescenta no insert pois seerão salvos em uma mesma tabela
            stmtAdiciona.setString(4, aluno.getEndereco().getLogradouro());
            stmtAdiciona.setInt(5, aluno.getEndereco().getNumero());
            stmtAdiciona.setString(6, aluno.getEndereco().getComplemento());
            stmtAdiciona.setString(7, aluno.getEndereco().getBairro());
            stmtAdiciona.setString(8, aluno.getEndereco().getCep());
            stmtAdiciona.setString(9, aluno.getEndereco().getCidade());
            stmtAdiciona.setString(10, aluno.getEndereco().getEstado());

            // executa
            stmtAdiciona.execute();

            //Obtém o id do aluno gerada pelo banco
            ResultSet rs = stmtAdiciona.getGeneratedKeys();
            rs.next();
            long i = rs.getLong(1);
            aluno.setMatricula((int) i);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                stmtAdiciona.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Falha ao fechar stmt.");
            }
            try {
                con.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Falha ao fechar conexão.");
            }

        }
        return (aluno.getMatricula());
    }

    //----------------------------    
    //Método DAO que busca um aluno recebendo seu NR e retorna um objeto aluno, ou NULL para aluno não encontrado
    public Aluno buscarNR(int nr) {

        Connection con = null;
        PreparedStatement stmtPesquisarId = null;
        Aluno aluno = new Aluno();

        try {

            con = ConnectionFactory.getConnection();
            stmtPesquisarId = con.prepareStatement(pesquisarnr);
            // seta os valores
            stmtPesquisarId.setInt(1, nr);
            ResultSet resultSet = stmtPesquisarId.executeQuery();

            while (resultSet.next()) {
                aluno.setMatricula(resultSet.getInt("nr"));
                aluno.setCpf(resultSet.getString("cpf"));
                aluno.setNome(resultSet.getString("nome"));
                aluno.setIdade(resultSet.getInt("idade"));
                aluno.getEndereco().setLogradouro(resultSet.getString("logradouro"));
                aluno.getEndereco().setNumero(resultSet.getInt("numero"));
                aluno.getEndereco().setComplemento(resultSet.getString("complemento"));
                aluno.getEndereco().setBairro(resultSet.getString("bairro"));
                aluno.getEndereco().setCep(resultSet.getString("cep"));
                aluno.getEndereco().setCidade(resultSet.getString("cidade"));
                aluno.getEndereco().setEstado(resultSet.getString("estado"));

                return (aluno);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                stmtPesquisarId.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Falha ao fechar stmt.");
            }
            try {
                con.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Falha ao fechar conexão.");
            }

        }

        //Se nada foi encontrado, retornará null
        return (null);
    }

    //----------------------------   
    //Método DAO que altera os dados de um aluno e retorna NR do aluno que foi alterado ou zero para aluno não encontrado para alteração
    public int alterar(Aluno aluno) {

        Connection con = null;
        PreparedStatement stmtAlterar = null;
        int updateOK = 0;

        try {
            con = ConnectionFactory.getConnection();
            stmtAlterar = con.prepareStatement(alteraraluno, Statement.RETURN_GENERATED_KEYS);

            // seta os valores
            stmtAlterar.setString(1, aluno.getCpf());
            stmtAlterar.setString(2, aluno.getNome());
            stmtAlterar.setInt(3, aluno.getIdade());

            //Obtém os campos do objeto endereço em aluno e seta os dados na string de update
            stmtAlterar.setString(4, aluno.getEndereco().getLogradouro());
            stmtAlterar.setInt(5, aluno.getEndereco().getNumero());
            stmtAlterar.setString(6, aluno.getEndereco().getComplemento());
            stmtAlterar.setString(7, aluno.getEndereco().getBairro());
            stmtAlterar.setString(8, aluno.getEndereco().getCep());
            stmtAlterar.setString(9, aluno.getEndereco().getCidade());
            stmtAlterar.setString(10, aluno.getEndereco().getEstado());
            stmtAlterar.setInt(11, aluno.getMatricula());

            // executa
            updateOK = stmtAlterar.executeUpdate();

            //Obtém o id do aluno gerada pelo banco
            //ResultSet rs = stmtAlterar.getGeneratedKeys();
            //rs.next();
            //long i = rs.getLong(1);
            // aluno.setMatricula((int) i);                   
            if (updateOK > 0) {
                return (aluno.getMatricula());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                stmtAlterar.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Falha ao fechar stmt.");
            }
            try {
                con.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Falha ao fechar conexão.");
            }

        }
        return (updateOK);
    }

    //----------------------------   
    //Método DAO que retorna uma lista de alunos 
    public List<Aluno> listar() {

        Connection con = null;
        PreparedStatement stmtPesquisar = null;

        try {

            con = ConnectionFactory.getConnection();
            stmtPesquisar = con.prepareStatement(listaraluno);
            ResultSet resultSet = stmtPesquisar.executeQuery();
            List<Aluno> alunos = new ArrayList<>();

            while (resultSet.next()) {
                Aluno aluno = new Aluno();
                aluno.setMatricula(resultSet.getInt("nr"));
                aluno.setCpf(resultSet.getString("cpf"));
                aluno.setNome(resultSet.getString("nome"));
                aluno.setIdade(resultSet.getInt("idade"));
                aluno.getEndereco().setLogradouro(resultSet.getString("logradouro"));
                aluno.getEndereco().setNumero(resultSet.getInt("numero"));
                aluno.getEndereco().setComplemento(resultSet.getString("complemento"));
                aluno.getEndereco().setBairro(resultSet.getString("bairro"));
                aluno.getEndereco().setCep(resultSet.getString("cep"));
                aluno.getEndereco().setCidade(resultSet.getString("cidade"));
                aluno.getEndereco().setEstado(resultSet.getString("estado"));

                alunos.add(aluno);
            }
            return (alunos);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                stmtPesquisar.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Falha ao fechar stmt.");
            }
            try {
                con.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Falha ao fechar conexão.");
            }

        }
    }

    public void excluir(Aluno aluno) throws SQLException {

        Connection con = null;
        PreparedStatement stmtExcluir = null;

        try {
            con = ConnectionFactory.getConnection();
            stmtExcluir = con.prepareStatement(delete);
            stmtExcluir.setInt(1, aluno.getMatricula());
            stmtExcluir.executeUpdate();
        } finally {
            try {
                stmtExcluir.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Falha ao fechar stmt.");
            }
            try {
                con.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Falha ao fechar conexão.");
            }

        }

    }

}
