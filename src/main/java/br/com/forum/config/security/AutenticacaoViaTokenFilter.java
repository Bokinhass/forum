package br.com.forum.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

  private final TokenService tokenService;

  public AutenticacaoViaTokenFilter(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String token = recuperarToken(request);
    boolean valido = tokenService.isTokenValido(token);
    System.out.println(valido);

    filterChain.doFilter(request, response);
  }

  private String recuperarToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");

    if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
      return null;
    }

    return token.substring(7);
  }
}
