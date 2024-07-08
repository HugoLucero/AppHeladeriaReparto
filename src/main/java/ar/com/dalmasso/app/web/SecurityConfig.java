/*
 * Software creado y diseñado por Hugo Lucero.
* Derechos reservados para Hugo Lucero.
* Para más información contactarse a:
* hlucerodiaz@gmail.com
 */
package ar.com.dalmasso.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManagerBuilder auth;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, AuthenticationManagerBuilder auth) throws Exception {
        this.userDetailsService = userDetailsService;
        this.auth = auth;

        configurerGlobal();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void configurerGlobal() throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/editarProducto/**", "/agregarProducto/**", "/eliminarProducto", "/guardarCargaProducto/**", "/editarExistencia/**", "/eliminarOrden", "/reportesInicio/**", "/agregarOrden/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/")
                        .hasAnyRole("ADMIN", "USER"))
                .formLogin(login -> login
                        .loginPage("/login").permitAll())
                .exceptionHandling(handling -> handling.accessDeniedPage("/errores/403"));
        return http.build();
    }
}
