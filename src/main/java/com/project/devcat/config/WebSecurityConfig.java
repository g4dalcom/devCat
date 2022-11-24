package com.project.devcat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // 시큐리티 어노테이션 사용 가능하게 함
public class WebSecurityConfig extends WebSecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                .mvcMatchers("/images/**", "/css/**", "/h2-console/**", "/search", "/posts/**", "/login", "/login/kakao/**",
                        "/signup").permitAll()
                .mvcMatchers(HttpMethod.GET, "/posts", "/posts/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/").permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/");

    }

}
