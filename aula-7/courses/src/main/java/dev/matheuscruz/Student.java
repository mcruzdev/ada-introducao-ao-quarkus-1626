package dev.matheuscruz;

import dev.matheuscruz.validators.CPFValido;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "student")
public class Student extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotNull
    @Size(min = 2, message="Nome invalido. Tente Novamente")
    private String name;

    @Column(name = "age")
    @NotNull
    @Min(value = 16, message = "A idade minima para ser matriculado é de 16 anos")
    @Max(value = 100, message = "Nao aceitaremos matrícula de estudantes com mais de 100 anos de idade")
    private int age;

    @Column(name = "documento_cpf")
//    @CPFValido
    private String documentoCpf;

    @NotNull
    @Column(name = "documento_cnpj", nullable = false)
    private String documentoCnpj;

    @Column(name = "email")
    @Email(message = "Email invalido! Tente novamente!")
    private String email;

    @Digits(integer=3, fraction=2)
    @Column(name = "mensalidade")
    private BigDecimal mensalidade;

    public Student() {

    }

    public Student(String name, int age, String documentoCpf, String documentoCnpj, String email, BigDecimal mensalidade) {
        this.name = name;
        this.age = age;
        this.documentoCpf = documentoCpf;
        this.documentoCnpj = documentoCnpj;
        this.email = email;
        this.mensalidade = mensalidade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDocumentoCpf() {
        return documentoCpf;
    }

    public void setDocumentoCpf(String documentoCpf) {
        this.documentoCpf = documentoCpf;
    }

    public String getDocumentoCnpj() {
        return documentoCnpj;
    }

    public void setDocumentoCnpj(String documentoCnpj) {
        this.documentoCnpj = documentoCnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(BigDecimal mensalidade) {
        this.mensalidade = mensalidade;
    }
}
