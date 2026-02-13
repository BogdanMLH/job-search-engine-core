package com.jobapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // <--- Не забудьте импорт
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final String jwkSetUri = "https://rrtegtldqqtdgxspjowu.supabase.co/auth/v1/.well-known/jwks.json";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Подключаем CORS конфигурацию, которую мы описали ниже
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // 2. РАЗРЕШАЕМ ВСЕ Preflight (OPTIONS) запросы
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 3. Разрешаем доступ к вакансиям
                        .requestMatchers("/api/jobs/**").permitAll()

                        // 4. ВАЖНО: Разрешаем доступ к регистрации и синхронизации без токена
                        .requestMatchers("/api/users/**").permitAll()

                        // Все остальное требует авторизации
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {
                    jwt.decoder(jwtDecoder());
                }));

        return http.build();
    }

    // Бин для декодера (ваш код)
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri)
                .jwsAlgorithm(SignatureAlgorithm.ES256)
                .build();
    }

    // 5. Бин настройки CORS (перенесен из старого файла)
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Разрешаем фронтенд
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:80"));

        // Разрешаем методы
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Разрешаем заголовки (включая Authorization)
        configuration.setAllowedHeaders(List.of("*"));

        // Разрешаем передачу куки/кредов (важно для некоторых запросов)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}