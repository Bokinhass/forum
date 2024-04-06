package br.com.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations {

  @Autowired
  AutenticacaoService autenticacaoService;
  @Autowired
  private TokenService tokenService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http

        .authorizeHttpRequests((authz) -> authz
            .requestMatchers(HttpMethod.POST, "/auth").permitAll()
            .requestMatchers(HttpMethod.GET, "/topicos", "/topicos/*").permitAll()
            .requestMatchers(HttpMethod.GET, "/h2-console").permitAll()
            .anyRequest()
            .authenticated()
        )
        .sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(new AutenticacaoViaTokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return authentication -> authentication;
  }

  @Autowired
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
  }

  public void configure(WebSecurity web) throws Exception {
  }
}
