package br.com.forum.DTO;

import br.com.forum.models.StatusTopico;
import br.com.forum.models.Topico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DetalhesTopicoDTO {

  private final Long id;
  private final String titulo;
  private final String mensagem;
  private final LocalDateTime dataCriacao;
  private final String nomeAutor;
  private final StatusTopico status;
  private final List<RespostaDTO> resposta;

  public DetalhesTopicoDTO(Topico topico) {
    this.id = topico.getId();
    this.titulo = topico.getTitulo();
    this.mensagem = topico.getMensagem();
    this.dataCriacao = topico.getDataCriacao();
    this.nomeAutor = topico.getAutor().getNome();
    this.status = topico.getStatus();
    this.resposta = new ArrayList<>();
    this.resposta.addAll(topico.getRespostas().stream().map(RespostaDTO::new).toList());
  }

  public Long getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
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

  public StatusTopico getStatus() {
    return status;
  }

  public List<RespostaDTO> getResposta() {
    return resposta;
  }
}
