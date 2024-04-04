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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

  @Autowired
  private TopicoRepository topicoRepository;

  @Autowired
  private CursoRepository cursoRepository;


  @GetMapping
  @Cacheable(value = "listaDeTopicos")
  public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso,
                               @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable paginacao) {

    if (nomeCurso == null) {
      Page<Topico> topicos = topicoRepository.findAll(paginacao);
      return TopicoDTO.converter(topicos);

    } else {
      Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
      return TopicoDTO.converter(topicos);
    }
  }

  @PostMapping
  @Transactional
  @CacheEvict(value = "listaDeTopicos", allEntries = true)
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
  @CacheEvict(value = "listaDeTopicos", allEntries = true)
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
  @CacheEvict(value = "listaDeTopicos", allEntries = true)
  public ResponseEntity<?> apagar(@PathVariable Long id) {
    Optional<Topico> optionalTopico = topicoRepository.findById(id);

    if (optionalTopico.isPresent()) {
      topicoRepository.deleteById(id);

      return ResponseEntity.ok().build();
    }

    return ResponseEntity.notFound().build();
  }
}
