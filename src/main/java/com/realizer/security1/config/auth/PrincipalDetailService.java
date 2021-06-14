package com.realizer.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.realizer.security1.model.User;
import com.realizer.security1.repository.UserRepository;

// 시큐리티 설정에서 loginProcessingURL이 걸려있기 때문에
// login요청이 오면 자동으로 UserDetailsService 탕비으로 IOC 되어있는 loadUserByUsername함수가 자동 실행됨.
@Service
public class PrincipalDetailService implements UserDetailsService 
{
	
	@Autowired
	private UserRepository userRepository;

	// 시큐리티 Session(내부 Authentication(내부 UserDetails))
	// 해당 함수 종료시 @@AuthenticationPrincipal 어노테이션이 만들어진다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User userEntity = userRepository.findByUsername(username);
		
		if ( userEntity != null )
		{
			return new PrincipalDetails(userEntity);
		}
		
		
		return null;
	}

}
