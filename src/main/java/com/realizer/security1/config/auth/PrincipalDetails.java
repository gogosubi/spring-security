package com.realizer.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.realizer.security1.model.User;

import lombok.Data;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료되면 시큐리티 Session을 생성한다(SecurityContextHolder)
// 오브젝트 타입 => Authentication 타입 객체여야 함.
// Authentication 안에 User정보가 있어야 함.
// User 오브젝트타입 => UserDetails 타입 객체

// Security Session의 Authentication을 꺼내면 결과적으로 UserDetails 객체가 된다.
@Data
public class PrincipalDetails implements UserDetails, OAuth2User 
{
	private User user;
	
	Map<String, Object> attributes;

	public PrincipalDetails(User user) {
		this.user = user;
	}

	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collect = new ArrayList();
		collect.add(
				new GrantedAuthority() 
				{	
					@Override
					public String getAuthority() {
						// TODO Auto-generated method stub
						return user.getRole();
					}
				});
		
		// TODO Auto-generated method stub
		return collect;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		// 우리 사이트는 1년동안 로그인을 안하면 휴면 계정 처리
		// 현재시간 - 로긴시간 => 1년 초과하면 return false;
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
