/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.serverappalunos.ws;

import br.com.serverappalunos.vo.Aluno;
import br.com.serverappalunos.facade.AlunoFacade;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author jean.souza
 */
@Path("alunos")
public class AlunosResource {

    @Context
    private UriInfo context;
    private AlunoFacade alunoFacade;

    /**
     * Creates a new instance of AlunosResource
     */
    public AlunosResource() {
        alunoFacade = new AlunoFacade();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response alunos() {
        
        List<Aluno> alunos = alunoFacade.buscarTodosAlunos();
        GenericEntity<List<Aluno>> alunosResponse;
        alunosResponse = new GenericEntity<List<Aluno>>(alunos) {
        };

        return Response.
                ok().
                entity(alunosResponse).
                build();

    }

    @GET
    @Path("/{nr}")
    @Produces(MediaType.APPLICATION_JSON)
    public Aluno buscarAluno(@PathParam("nr") int nr) {
        //Ja faz cast pata int automatico        
        Aluno aluno = alunoFacade.buscarAlunoPorNR(nr);
        
        return aluno;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alunos(Aluno aluno) {
        int nr = alunoFacade.cadastrarAluno(aluno);
        return Response.ok(nr, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Aluno remover(@PathParam("id") int nr) {
        Aluno aluno = alunoFacade.buscarAlunoPorNR(nr);
        try {
            this.alunoFacade.removerAluno(aluno);
        } catch (SQLException ex) {
            Logger.getLogger(AlunosResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aluno;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterar(Aluno aluno) {
        int nr = this.alunoFacade.atualizarAluno(aluno);
        return Response.ok(nr, MediaType.APPLICATION_JSON).build();
    }
}
