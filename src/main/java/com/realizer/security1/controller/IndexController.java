package com.realizer.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.realizer.security1.model.User;
import com.realizer.security1.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping({"/", ""})
	public String index()
	{
		// 머스테치 기본폴더 : src/main/resources
		// 뷰리볼버 설정 : templates(prefix), .mustache(suffix) 생략가능
		return "index";
	}
	
	@GetMapping("/user")
	public String user()
	{
		return "user";
	}
	
	@GetMapping("/manager")
	public String manager()
	{
		return "manager";
	}
	
	@GetMapping("/admin")
	public String admin()
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
}
