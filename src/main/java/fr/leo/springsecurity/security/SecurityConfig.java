package fr.leo.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// Avant on héritait de la classe WebSecuriteAdapter !
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public InMemoryUserDetailsManager  inMemoryUserDetailsManager() {			
		
		// {noop} on demande à SpringSecurity de ne pas utiliser le password encoder !
		return new InMemoryUserDetailsManager(
				User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build(),
				User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build(),
				User.withUsername("user3").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()
				);
	}

	//Ci dessous on créer un filtre, creer un objet SecurityFilterChain
	@Bean // pour que la méthode se lance au démarrage
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
		
		httpSecurity.formLogin().loginPage("/login").permitAll();
		
		httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**", "h2-console/**").permitAll();
		httpSecurity.rememberMe();
		// On supprime !
		//httpSecurity.formLogin();
		
		//httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
		//httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
		
		// Ci dessous, on dit a SpringSecurity : " toutes les requetes doivent etre authentifiées"
		httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
		
		httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");
		return httpSecurity.build();
	}
}
