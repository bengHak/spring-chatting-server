package com.byunghak.chatapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 기본값이 on인 csrf 취약점 보안을 해제한다.
                .headers()
                .frameOptions().sameOrigin() // SockJS는 기본적으로 HTML iframe 요소를 통한 전송을 허용하지 않도록 설정되는데 해당 내용을 해제한다.
                .and().formLogin() // 권한없이 페이지 접근하면 로그인 페이지로 이동한다.
                .and().authorizeRequests()
                .antMatchers("/chat/**").hasRole("USER") // chat 으로 시작하는 리소스에 대한 접근 권한 설정
                .anyRequest().permitAll(); // 나머지 리소스에 대한 접근 설정
    }
    /**
     * 테스트를 위해 In-Memory 에 계정을 임의로 생성한다.
     * 서비스에 사용시에는 DB데이터를 이요하도록 수정이 필요하다.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("11")
                .password("{noop}1234")
                .roles("USER")
            .and()
                .withUser("22")
                .password("{noop}1234")
                .roles("USER")
            .and()
                .withUser("33")
                .password("{noop}1234")
                .roles("GUEST");
    }
}
