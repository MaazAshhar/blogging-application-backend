package com.ashhar.blogappapis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ashhar.blogappapis.security.CustomUserDetailService;
import com.ashhar.blogappapis.security.JwtAuthenticationEntryPoint;
import com.ashhar.blogappapis.security.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	public static final String[] PUBLIC_URLS = {"/api/v1/auth/**", "/v3/api-docs", "/v3/api-docs/**", "/v2/api-docs",
            "/swagger-resources/**", "/swagger-ui/**", "/webjars/**","/api/v1/auth/login","/swagger-ui/index.html",
            "/swagger-ui.html"

    };
	public static final String[] ADMIN_URLS = {"/api/users/"};

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf
                        .disable())
                .authorizeHttpRequests((request)->request
                		.requestMatchers(PUBLIC_URLS)
                		.permitAll()
                		.requestMatchers(ADMIN_URLS).hasRole("ADMIN")
                		.anyRequest()
                		.authenticated())
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                		.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement((sessionManagement) -> sessionManagement
                		.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();

        return defaultSecurityFilterChain;
	}

    @Bean
    PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }

    @Bean
    AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
