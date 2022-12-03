package com.aladin.retailserviceddd.core.config;

import com.aladin.retailserviceddd.core.config.jwt.JWTFilter;
import com.aladin.retailserviceddd.core.config.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .disable().headers().frameOptions().disable().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/retail/product/change-amount-product").hasAuthority("ROLE_USER")
                .antMatchers("/api/retail/pre-deposited/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/api/retail/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/api/**").authenticated()
                .and().addFilterBefore(new JWTFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
        // @formatter:on
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
