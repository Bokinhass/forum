package br.com.forum.repository;

import br.com.forum.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByEmail(String email);
}
