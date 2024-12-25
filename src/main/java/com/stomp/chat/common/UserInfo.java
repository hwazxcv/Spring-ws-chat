package com.stomp.chat.common;


import com.stomp.chat.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfo {

    private final UserRepository userRepository;


//    public User getUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // 인증되지 않았거나 null일 경우 처리
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new UnauthorizedException("인증된 사용자가 아닙니다.");
//        }
//
//        // JWT 토큰을 추출 (보통 JWT 토큰은 Principal로 저장됨)
//        String token = (String) authentication.getCredentials();
//
//        // 토큰 유효성 검사 (Repository 등을 통해 토큰의 유효성을 확인)
//        User user = userRepository.findByMailVerificationToken(token).orElseThrow(UnauthorizedException::new);
//
//        return user;
//    }



}
