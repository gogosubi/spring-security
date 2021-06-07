package com.realizer.security1.config;

import org.springframework.beans.factory.annotation.Autowired;

// 카카오톡인 경우
// 1. 코드받기(인증)
// 2. 엑세스토큰(권한)
// 3. 사용자프로필 정보를 가져오고
// 4. 그 정보를 토대로 회원가입을 자동 진행하기도 함

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.realizer.security1.config.oauth.PrincipalOauth2UserService;

@Configuration
// securedEnabled = true // 특정 함수에서 @Secured() 어노테이션을 이용하여 접근 권한 정보를 제어할 때(하나의 ROLE 정의) 
// prePostEnabled = true // 특정 함수에서 @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')"), PostAuthroize 어노테이션을 이용하여 접근 권한 정보를 제어할 때(다수의 ROLE정의)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	PrincipalOauth2UserService principalOauth2UserService;
	
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
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // ADMIN 디렉토리 이하에는 ADMIN만 접근 가능하다.
			.anyRequest().permitAll() // 그 이 외의 페이지에는 다 접근 가능 
			.and()                    // 만약 권한을 가진 페이지 접근시
			.formLogin()              // 로그인이 되지 않은 경우에는
			.loginPage("/loginForm")  // 다 로그인 페이지로 이동하라
			//loginForm.html에서 넘어오는 변수명에 대한 설정 부분 
			//.usernameParameter("바꾸고싶은 변수명"); // SpringSecurity에서는 username을 ID 변수로 사용하고 있는데 만약 다른 변수를 변경하려 할 때 사용함.
			//.passwordParameter("pwd")            // SpringSecurity에서는 password를 비밀번호 변수로 사용하고 있는데 만약 다른 변수를 변경하려 할 때 사용함.
			.loginProcessingUrl("/login") // login주소가 호출되면 시큐리티가 낚아채서 대신 로그인 해줌.
			.defaultSuccessUrl("/")
			.and()					// OAuth로그인 설정
			.oauth2Login()
			.loginPage("/loginForm") // 구글로그인이 완료된 후 후처리 필요함 TIP. 코드X, 엑세스토큰+사용자프로필정보 가져옴
			.userInfoEndpoint() 
			.userService(principalOauth2UserService)
			;
	}

	@GetMapping("/info")
	public @ResponseBody String info()
	{
		return "개인정보";
	}
	
}
