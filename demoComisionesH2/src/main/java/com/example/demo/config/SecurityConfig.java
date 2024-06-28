package com.example.demo.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

	Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	private AuthenticationSuccessHandler success;
    @Bean
    UserDetailsManager userDetailsService(DataSource dataSource) {
    return new JdbcUserDetailsManager(dataSource);}
    
    @Bean
     PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.GET, "/login", "/listar", "/h2-console/**", "/visualizar/**","/crear/comision").permitAll()
                .requestMatchers(HttpMethod.GET, "/css/**", "/js/**", "/img/**").permitAll()  
                .requestMatchers(HttpMethod.POST, "/crear/guardar").permitAll()
                .requestMatchers("/validarComision/**").permitAll()            
                .anyRequest().permitAll())
            	.formLogin(form -> form
                .loginPage("/login")
                .successHandler(success)
                .permitAll());
           
         

    return http.build();
    }
}
