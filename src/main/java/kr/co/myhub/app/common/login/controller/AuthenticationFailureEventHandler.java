package kr.co.myhub.app.common.login.controller;

import java.util.Date;

import kr.co.myhub.app.user.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * 
 * file   : AuthenticationFailureEventHandler.java
 * date   : 2014. 1. 25.
 * author : jmpark
 * content: 스프링시큐리티에서 로그인 실패에 대한 처리 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 1. 25.   kbtapjm     최초생성
 */
@Component
public class AuthenticationFailureEventHandler implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        log.debug(" ----------------------------------------------------  ");
        log.debug(" getTimestamp : {}", event.getTimestamp());                          // 로그인 실패시간
        log.debug(" getException : {}", event.getException());                          // 로그인 에러내용
        log.debug(" getCredentials : {}", event.getAuthentication().getCredentials());  // 로그인시 패스워드
        log.debug(" getDetails : {}", event.getAuthentication().getDetails());          // 로그인실패 세션 아이디          
        log.debug(" getPrincipal : {}", event.getAuthentication().getPrincipal());      // 로그인 아이디
        log.debug(" getAuthorities : {}", event.getAuthentication().getAuthorities());
        log.debug(" getName : {}", event.getAuthentication().getName());                // 로그인 아이디
        log.debug(" ----------------------------------------------------  ");
        
        /* 로그인 실패시 실패카운트를 업데이트  */
        Object userId = event.getAuthentication().getPrincipal();
        
        try {
            int result = userService.updateUserLogin(new Date(), userId.toString()); 
            log.debug("result : {}", result);
            
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error : " + e.getMessage());
        }
    }
}
