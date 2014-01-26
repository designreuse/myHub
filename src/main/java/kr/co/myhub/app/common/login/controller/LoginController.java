package kr.co.myhub.app.common.login.controller;

import java.net.InetAddress;
import java.security.Principal;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.myhub.app.common.login.domain.LoginLog;
import kr.co.myhub.app.common.login.service.LoginService;
import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.service.UserService;
import kr.co.myhub.appframework.constant.StatusEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 
 * file   : LoginController.java
 * date   : 2013. 11. 22.
 * author : jmpark
 * content: 로그인 관련 컨트롤러(스프링 시큐리티 핸들러 처리) 
 * 
 * http://www.java-school.net/spring/spring-security.php
 * http://blog.naver.com/alucard99?Redirect=Log&logNo=192570650
 * 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 22.   kbtapjm     최초생성
 */
@Controller
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    
    /**
     * messageSource DI
     */
    @Autowired 
    MessageSourceAccessor messageSourceAccessor;
    
    /**
     *  userService DI
     */
    @Autowired
    UserService userService;
    
    /**
     *  loginService DI
     */
    @Autowired
    LoginService loginService;
    
    /**
     * 로그인 화면
     * @param model
     * @param locale
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, Locale locale) throws Exception {
        if (log.isInfoEnabled()) {
            log.debug(" =====> Local : " + locale);
        }
        
        return "/common/login/login";         
    }
    
    /**
     * 메인
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Model model) throws Exception {
        
        return "/common/main";         
    }
    
    /**
     * 로그인 성공
     * @param modelMap
     * @param principal
     * @param session
     * @param locale
     * @return
     */
    @RequestMapping(value = "/loginSuccess", method = RequestMethod.GET)
    public String loginSuccess(Model model, Principal principal, HttpSession session, Locale locale) {
        if (log.isDebugEnabled()) {
            log.debug("=========================================================");
            log.debug("Login Success!!!");
            log.debug("=========================================================");
        }
        
        String email = principal.getName();
        
        try {
            // TODO: 계정 암호 만료 여부 확인 로직 추가(보안정책) -> 주기적으로 암호를 바꿔야되는 상황에 체크해서 알려준다.
            
            // TODO: 로그인 상태 처리 추가
            
            /* TODO: 로그인 정보는 필요한 정보만 세팅 */
            User user = userService.findByEmail(email);
            session.setAttribute("sUser", user);
            
            /* 로그인 이력 추가 */
            LoginLog loginLog = new LoginLog();
            loginLog.setEmail(user.getEmail());
            loginLog.setIpAddress(InetAddress.getLocalHost().getHostAddress());
            loginLog.setLoginDate(new Date());
            loginLog.setUser(user);
            
            loginService.create(loginLog);
            
        } catch (Exception e) {
            e.printStackTrace();
            session.invalidate();
        }
        
        return "redirect:/main";
    }
    
    /**
     * 로그인 실패
     * @param modelMap
     * @param session
     * @param locale
     * @return
     */
    @RequestMapping(value = "/loginFailed", method = RequestMethod.GET)
    public String loginFailed(Model model, 
            HttpSession session,
            RedirectAttributes redirectAttr,
            HttpServletRequest request,
            Locale locale) {
        if (log.isDebugEnabled()) {
            log.debug("=========================================================");
            log.debug("Login Fail!!!");    
            log.debug("=========================================================");
        }
        
        // 세션값에 저장
        //redirectAttr.addAttribute("status", StatusEnum.FAIL);
        
        // FlashMap에 전달할 값을 저장한다.
        FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
        fm.put("status", StatusEnum.FAIL);
        fm.put("message", messageSourceAccessor.getMessage("myhub.error.login.fail", locale));
        
        return "redirect:/login";
    }
    
    /**
     * timeout(세션만료)
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/timeout", method = RequestMethod.GET)
    public String timeout(Model model) {
        
        return "/common/login/timeout";
    }


}
