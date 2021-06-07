package com.realizer.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.realizer.security1.config.auth.PrincipalDetails;
import com.realizer.security1.model.User;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
	
	// 구글로부터 받은 userRequest 데이터에 대한 후처리 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		// 1. 구글 로그인 버튼 클릭 -> 구글로그인 창 
		// 2. 로그인 완료 -> 코드를 리턴(OAuth-Client라이브러리) -> AccessToken요청 -> userRequest정보에 담김
		// 3. userRequest정보 -> loadUser함수 호출 -> 구글로부터 회원프로필을 받아준다.
//		System.out.println("userRequest :" + userRequest.getClientRegistration());
//		System.out.println("userRequest :" + userRequest.getAccessToken());
//		System.out.println("userRequest :" + super.loadUser(userRequest).getAttributes());
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		User user = new User();
		// TODO Auto-generated method stub
		return super.loadUser(userRequest);
	}
	
}
