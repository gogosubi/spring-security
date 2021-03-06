package com.realizer.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.realizer.security1.config.auth.PrincipalDetails;
import com.realizer.security1.model.User;
import com.realizer.security1.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails)
	{
		System.out.println(authentication.getName());
		System.out.println(principalDetails.getUser());
		return "Session 테스트";
	}
	
	@GetMapping("/test/oAuthlogin")
	public @ResponseBody String testOAuthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2User)
	{
		System.out.println(authentication.getName());
		System.out.println(oAuth2User.getAttributes());
		return "OAuth Session 테스트";
	}
	
	@GetMapping({"/", ""})
	public String index()
	{
		// 머스테치 기본폴더 : src/main/resources
		// 뷰리볼버 설정 : templates(prefix), .mustache(suffix) 생략가능
		return "index";
	}
	
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails)
	{
		System.out.println(principalDetails.getUser());
		return "user";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager()
	{
		return "manager";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin()
	{
		return "admin";
	}
	
	// 스프링시큐리티가 구현을 했기 때문에 스프링 시큐리티가 가져감. 
	// -> SecurityConfig를 지정하고 나서는 스프링 시큐리티가 가져가지 않음
	// anyRequest는 다 login 페이지로 이동하도록 설정됨.
	@GetMapping("/loginForm")
	public String loginForm()
	{
		return "loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm()
	{
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(User user)
	{
		System.out.println(user);
		user.setRole("ROLE_USER");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		
		return "redirect:/loginForm";
	}
	
	// 단 건의 접근 권한 정보를 제허할 때 Secured 사용
	@Secured("ROLE_ADMIN")
	@GetMapping("/info")
	public @ResponseBody String info()
	{
		return "개인정보";
	}
	
	// 다 건의 접근 권한 정보를 제어할 때 PreAuthorize 사용
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/data")
	public @ResponseBody String data() 
	{
		return "데이터";
	}
}
