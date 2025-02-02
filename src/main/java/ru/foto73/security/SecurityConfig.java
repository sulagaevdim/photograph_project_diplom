package ru.foto73.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(CustomSuccessHandler customSuccessHandler) {
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean //возвращаем кастомный MyUserDetailsService
    public UserDetailsService userDetailsService(){
        return new MyUserDetailsService();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/index",
                            "/login",
                            "/contacts",
                            "/price",
                            "/gallery",
                            "/registration",
                            "/reviews",
                            "/entrance",
                            "/online-record",
                            "/success",
                            "/errorRegistration",
                            "/",
                            "/send-success",
                            "/logout",
                            "/images/**",
                            "/h2-console/**").permitAll(); // страницы, доступные абсолютно всем
                    registry.requestMatchers("/admin/**").hasAuthority("ADMIN"); // страницы, доступные только админу
                    registry.requestMatchers("/user/**").hasAuthority("USER"); // страницы, доступные только зарегистрированному пользователю
                    registry.anyRequest().permitAll(); //все что не попало выше - только авторизованным п-лям
                })
                //.formLogin(it -> it.loginPage("/entrance"))
                //.formLogin(Customizer.withDefaults()) //стандартная форма авторизации
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login") // URL, на который отправляются данные формы
                        //.defaultSuccessUrl("/user/", true) // Куда перенаправлять после успешного входа
                        .successHandler(customSuccessHandler) // Указываем наш кастомный обработчик
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean //Ставим степень кодировки
    public PasswordEncoder passwordEncoder(){
        return new MyPasswordEncoder();
    }
}
