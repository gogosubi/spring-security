package com.realizer.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Bean
	public BCryptPasswordEncoder encodePwd()
	{
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable();
		
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated() // USER 디렉토리 이하에는 로긴이 되어야 접근 가능하고
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // MANAGER 디렉토리 이하에는 ADMIN과 MANAGER만 접근 가능하고
			.antMatchers("/admin/**").access("hasRole('ROLE_MANAGER')") // ADMIN 디렉토리 이하에는 ADMIN만 접근 가능하다.
			.anyRequest().permitAll() // 그 이 외의 페이지에는 다 접근 가능 
			.and()                    // 만약 권한을 가진 페이지 접근시
			.formLogin()              // 로그인이 되지 않은 경우에는
			.loginPage("/login")      // 다 로그인 페이지로 이동하라
			;
	}

	
}
