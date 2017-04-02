package br.com.serverappalunos.facade;

import br.com.serverappalunos.vo.Aluno;
import br.com.serverappalunos.dao.AlunoDAO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jean.souza
 */
public class AlunoFacade {

    private AlunoDAO alunoDao;

    public AlunoFacade() {
        alunoDao = new AlunoDAO();
    }

    public List<Aluno> buscarTodosAlunos() {
        return this.alunoDao.listar();
    }
    
    public Aluno buscarAlunoPorNR(int matricula)
    {
        return this.alunoDao.buscarNR(matricula);
    }
    
    public int cadastrarAluno(Aluno aluno)
    {
        return this.alunoDao.salvar(aluno);
    }
    
     public int atualizarAluno(Aluno aluno)
    {
        return this.alunoDao.alterar(aluno);
    }
    
    public void removerAluno(Aluno aluno) throws SQLException
    {
        this.alunoDao.excluir(aluno);
    }
}
