package io.irwansyahdev96.readcollection.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import io.irwansyahdev96.readcollection.filter.SecurityServletFilter;

@Configuration
public class SecurityConfig {

    // @Bean
    // public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
    //                                                    UserDetailsService userService) throws Exception {

    //     return http
    //             .getSharedObject(AuthenticationManagerBuilder.class)
    //             .userDetailsService(userService)
    //             .passwordEncoder(passwordEncoder).and().build();
    // }

    @Bean
    public List<RequestMatcher> requestMatchers(){
        final List<RequestMatcher> matchers = new ArrayList<>();

        matchers.add(new AntPathRequestMatcher("/users/**",HttpMethod.GET.name()));

        return matchers;
    }

    @Bean
    public WebSecurityCustomizer customizer() {
        return web -> requestMatchers().forEach((r)-> web.ignoring().requestMatchers(r));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity,final SecurityServletFilter securityServletFilter)
            throws Exception {
        httpSecurity.cors(Customizer.withDefaults());
        httpSecurity.csrf().disable();
        httpSecurity.addFilterAt(securityServletFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource configurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:8082"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
