package tech.ada.model;

public class Tarefa {

    // atributos, estado
    private Long id;
    private String titulo;
    private boolean concluida; // false

    // metodos, comportamentos
    public Tarefa(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isConcluida() {
        return concluida;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", concluida=" + concluida +
                '}';
    }
}
