package br.com.forum.DTO;

import br.com.forum.models.Resposta;

import java.time.LocalDateTime;

public class RespostaDTO {

  private final Long id;
  private final String mensagem;
  private final LocalDateTime dataCriacao;
  private final String nomeAutor;

  public RespostaDTO(Resposta resposta) {
    this.id = resposta.getId();
    this.mensagem = resposta.getMensagem();
    this.dataCriacao = resposta.getDataCriacao();
    this.nomeAutor = resposta.getAutor().getNome();
  }

  public Long getId() {
    return id;
  }

  public String getMensagem() {
    return mensagem;
  }

  public LocalDateTime getDataCriacao() {
    return dataCriacao;
  }

  public String getNomeAutor() {
    return nomeAutor;
  }
}
