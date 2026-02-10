package tech.ada;

import org.junit.jupiter.api.Test;
import tech.ada.dto.CriarTarefaDTO;
import tech.ada.resources.TarefaResource;

class TarefaResourceTest {

    @Test
    void criaTarefa() {
        TarefaResource tarefaResource = new TarefaResource();
        CriarTarefaDTO estudarOop = new CriarTarefaDTO("Estudar OOP");
        tarefaResource.criarTarefa(
                estudarOop
        );
    }
}