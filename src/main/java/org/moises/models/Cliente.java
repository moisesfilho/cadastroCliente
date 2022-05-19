package org.moises.models;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
@Cacheable
public class Cliente {

    @Id
    @SequenceGenerator(name = "clienteSequence", sequenceName = "cliente_id_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "clienteSequence")
    private Integer id;

    @Column(length = 40, unique = true)
    private String nome;

    @Column(length = 11, unique = true)
    private String cpf;

    public Cliente() {
    }

    public Cliente(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String name) {
        this.nome = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
