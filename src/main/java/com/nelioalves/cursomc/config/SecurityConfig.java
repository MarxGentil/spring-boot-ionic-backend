package com.nelioalves.cursomc.config;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private Environment env;
	
	// endpoints e consultas liberados, sem isso daria erro 401 não autorizado
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**",
	};

	private static final String[] PUBLIC_MATCHERS_GET = { // liberar somente para leitura
			"/h2-console/**",
			"/produtos/**",
			"/categorias/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		if (Arrays.asList(env.getActiveProfiles()).contains("test")){ // test é o profile de test que é a liberação para o h2
			http.headers().frameOptions().disable(); // ou seja, se tiver no profile test, liberar o h2 pelo frameword.
		}
		
		http.cors().and().csrf().disable(); // o nosso projeto não vai criar sessão de usuário, então, não vai precisar dessa proteção CSRF;
		http.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // só permite alterar dentro da aplicação, pelo PostMan por exemplo, não altera.
			.anyRequest().authenticated();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // assigurar que o sistema não vai criar sessão de usuário.
	}
	
	@Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	  }
	
}
