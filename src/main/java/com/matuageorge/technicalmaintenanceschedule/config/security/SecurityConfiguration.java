package com.matuageorge.technicalmaintenanceschedule.config.security;

import com.matuageorge.technicalmaintenanceschedule.config.security.jwt.JwtFilter;
import com.matuageorge.technicalmaintenanceschedule.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .mvcMatchers("/admin/**").hasAnyAuthority(Role.ADMINISTRATOR.name())
                .mvcMatchers("/auth").permitAll()
                .mvcMatchers("/**").hasAnyAuthority(Role.TECHNICIAN.name(), Role.ADMINISTRATOR.name())
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
