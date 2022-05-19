package org.moises.dto;

import java.util.ArrayList;
import java.util.List;

import io.netty.util.internal.StringUtil;

public class ClienteDTO {
  private Integer id;

  private String nome;

  private String cpf;

  private List<String> erros;

  public ClienteDTO() {
  }

  public ClienteDTO(String nome) {
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

  public List<String> getErros() {
    if (this.erros == null) {
      this.erros = new ArrayList<>();
    }

    return this.erros;
  }

  public void addErro(String erro) {
    if (this.erros == null) {
      this.erros = new ArrayList<>();
    }

    if (!StringUtil.isNullOrEmpty(erro)) {
      this.erros.add(erro);
    }
  }

  public String getErrosString() {
    String errosString = "";

    if (this.erros == null || this.erros.isEmpty())
      return errosString;

    for (String erro : erros) {
      errosString += "\n" + erro;
    }

    return errosString;
  }
}
