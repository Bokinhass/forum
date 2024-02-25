package br.com.forum.DTO;

import br.com.forum.models.Topico;
import br.com.forum.repository.TopicoRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class TopicoAtt {

  @NotNull
  @NotEmpty
  @Length(min = 5)
  private String titulo;

  @NotNull
  @NotEmpty
  @Length(min = 5)
  private String mensagem;

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getMensagem() {
    return mensagem;
  }

  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public Topico atualizar(Long id, TopicoRepository topicoRepository) {
    Topico topico = topicoRepository.getOne(id);

    topico.setTitulo(this.titulo);
    topico.setMensagem(this.mensagem);

    return topico;
  }
}
