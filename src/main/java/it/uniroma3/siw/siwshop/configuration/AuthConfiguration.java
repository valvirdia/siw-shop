package it.uniroma3.siw.siwshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                // AUTORIZZAZIONE
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/", "/index", "/register", "/login", "/css/**", "/images/**", "/favicon.ico", "/product-photo/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/**", "/formAddProduct").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                // LOGIN
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/success", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                // LOGOUT (versione moderna e sicura)
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Reindirizza alla home dopo il logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                );
        return http.build();
    }
}