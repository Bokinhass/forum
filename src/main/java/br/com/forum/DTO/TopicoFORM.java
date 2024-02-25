package br.com.forum.DTO;

import br.com.forum.models.Curso;
import br.com.forum.models.Topico;
import br.com.forum.repository.CursoRepository;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class TopicoFORM {

  @NotNull
  @NotEmpty
  @Length(min = 5)
  private String titulo;

  @NotNull
  @NotEmpty
  @Length(min = 5)
  private String mensagem;

  @NotNull
  @NotEmpty
  private String nomeCurso;

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

  public String getNomeCurso() {
    return nomeCurso;
  }

  public void setNomeCurso(String nomeCurso) {
    this.nomeCurso = nomeCurso;
  }

  public Topico converter(CursoRepository cursoRepository) {
    Curso curso = cursoRepository.findByNome(nomeCurso);

    return new Topico(titulo, mensagem, curso);
  }
}
