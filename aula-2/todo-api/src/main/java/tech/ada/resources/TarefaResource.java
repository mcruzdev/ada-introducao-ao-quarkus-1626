package tech.ada.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.ResponseStatus;
import tech.ada.dto.CriarTarefaDTO;
import tech.ada.model.Tarefa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Classe que vai ser responsavel por criar tarefas,
 * deletar tarefas, atualizar tarefas...
 */
@Path(value = "/tarefas")
public class TarefaResource {

    private static final List<Tarefa> LISTA_DE_TAREFAS = new ArrayList<>();
    private AtomicLong incrementador = new AtomicLong(0);

    @POST
    @ResponseStatus(201)
    public void criarTarefa(CriarTarefaDTO tarefa) {
        System.out.println("Recebi uma tarefa: " + tarefa);

        // CriarTarefaDTO -> Tarefa
        Tarefa novaTarefa = new Tarefa(incrementador.incrementAndGet(), tarefa.titulo());

        LISTA_DE_TAREFAS.add(novaTarefa);
    }

    // Data Transfer Object
    @GET
    public List<Tarefa> listaTarefas() {
        return LISTA_DE_TAREFAS;
    }

}
