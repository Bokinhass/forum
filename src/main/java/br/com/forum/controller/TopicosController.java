package br.com.forum.controller;

import br.com.forum.DTO.DetalhesTopicoDTO;
import br.com.forum.DTO.TopicoAtt;
import br.com.forum.DTO.TopicoDTO;
import br.com.forum.DTO.TopicoFORM;
import br.com.forum.models.Topico;
import br.com.forum.repository.CursoRepository;
import br.com.forum.repository.TopicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

  @Autowired
  private TopicoRepository topicoRepository;

  @Autowired
  private CursoRepository cursoRepository;

  @GetMapping
  public List<TopicoDTO> lista(String nomeCurso) {
    if (nomeCurso == null) {
      List<Topico> topicos = topicoRepository.findAll();
      return TopicoDTO.converter(topicos);

    } else {
      List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
      return TopicoDTO.converter(topicos);
    }
  }

  @PostMapping
  @Transactional
  public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoFORM form, UriComponentsBuilder uriBuilder) {
    Topico topico = form.converter(cursoRepository);
    topicoRepository.save(topico);

    URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

    return ResponseEntity.created(uri).body(new TopicoDTO(topico));
  }

  @GetMapping("/{id}")
  public ResponseEntity<DetalhesTopicoDTO> detalhar(@PathVariable Long id) {
    Optional<Topico> topico = topicoRepository.findById(id);

    if (topico.isPresent()) {
      return ResponseEntity.ok(new DetalhesTopicoDTO(topico.get()));
    }

    return ResponseEntity.notFound().build();
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoAtt att) {
    Optional<Topico> optionalTopico = topicoRepository.findById(id);

    if (optionalTopico.isPresent()) {
      Topico topico = att.atualizar(id, topicoRepository);

      return ResponseEntity.ok(new TopicoDTO(topico));
    }

    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<?> apagar(@PathVariable Long id) {
    Optional<Topico> optionalTopico = topicoRepository.findById(id);

    if (optionalTopico.isPresent()) {
      topicoRepository.deleteById(id);

      return ResponseEntity.ok().build();
    }

    return ResponseEntity.notFound().build();
  }
}
