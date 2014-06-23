package kr.co.myhub.appframework.security;

import java.util.Date;
import java.util.List;

import kr.co.myhub.app.common.login.domain.LogHistory;
import kr.co.myhub.app.common.login.service.LoginService;
import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.service.UserService;
import kr.co.myhub.appframework.constant.LogTypeEnum;
import kr.co.myhub.appframework.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * 
 * file   : SessionDestoryListener.java
 * date   : 2014. 6. 21.
 * author : jmpark
 * content: 로그아웃시(세션소멸) 이벤트 처리 리스너
 * 1) 정상적인 로그아웃  클릭시
 * 2) 웹브라우져 종료시
 *
 * ref : http://antop.tistory.com/151
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 6. 21.   kbtapjm     최초생성
 */
@Component
public class SessionDestoryListener implements ApplicationListener<SessionDestroyedEvent> {
    
    private static final Logger log = LoggerFactory.getLogger(SessionDestoryListener.class);
    
    @Autowired
    LoginService loginService;
    
    @Autowired
    UserService userService;
   
    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("[세션 만료 리스너 ]");
            log.debug(" ------------------------------------------------------------------------------------- ");
            
            // 아이디
            log.debug("SessionDestroyedEvent getId : {}", event.getId());
            
            // 로그아웃 일자(Timestamp)
            log.debug("SessionDestroyedEvent getTimestamp : {}", event.getTimestamp());
            
            // 로그아웃 일자(Date)
            log.debug("SessionDestroyedEvent getTimestampToDate : {}", DateUtil.getTimestampToDate(event.getTimestamp()));
            
            List<SecurityContext> contexts = event.getSecurityContexts();
            if (!contexts.isEmpty()) {
                for (SecurityContext ctx : contexts) {
                    log.debug("SecurityContext getCredentials: {}", ctx.getAuthentication().getCredentials());
                    
                    // 인증 이름
                    log.debug("SecurityContext getName: {}", ctx.getAuthentication().getName());
                    
                    // 권한 정보
                    log.debug("SecurityContext getAuthorities: {}", ctx.getAuthentication().getAuthorities());
                    
                    // 인증정보 상세
                    log.debug("SecurityContext getDetails: {}", ctx.getAuthentication().getDetails());
                    
                    WebAuthenticationDetails wad = (WebAuthenticationDetails) ctx.getAuthentication().getDetails();
                    log.debug("getRemoteAddress : {}", wad.getRemoteAddress()); // IP주소
                    log.debug("getSessionId : {}", wad.getSessionId());         // 세션아이디
                    
                    // 권한정보
                    log.debug("SecurityContext getPrincipal: {}", ctx.getAuthentication().getPrincipal());
                    
                    UserDetails currentUser = (UserDetails) ctx.getAuthentication().getPrincipal();
                    
                    log.debug("currentUser getUsername : {}", currentUser.getUsername());
                    log.debug("currentUser getPassword : {}", currentUser.getPassword());
                    log.debug("currentUser getAuthorities : {}", currentUser.getAuthorities());
                }
            }
            
            log.debug(" ------------------------------------------------------------------------------------- ");    
        }
        
        try {
            List<SecurityContext> contexts = event.getSecurityContexts();
            if (!contexts.isEmpty()) {
                for (SecurityContext ctx : contexts) {
                    String email = ctx.getAuthentication().getName();
                    
                    /* 유저 정보 조회 */
                    User user = userService.findByEmail(email);
                    
                    WebAuthenticationDetails wad = (WebAuthenticationDetails) ctx.getAuthentication().getDetails();
                    
                    /* 로그아웃 이력 추가 */   
                    LogHistory loginLog = new LogHistory();
                    loginLog.setEmail(user.getEmail());
                    loginLog.setIpAddress(wad.getRemoteAddress());
                    loginLog.setLogDate(new Date());
                    loginLog.setLogType(LogTypeEnum.logOut.getText());
                    loginLog.setSessionId(wad.getSessionId());
                    loginLog.setUser(user);
                    
                    loginService.create(loginLog);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
