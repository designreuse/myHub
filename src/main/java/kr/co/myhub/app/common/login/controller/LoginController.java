package kr.co.myhub.app.common.login.controller;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * file   : LoginController.java
 * date   : 2013. 11. 22.
 * author : jmpark
 * content: 로그인 관련 컨트롤러(스프링 시큐리티 핸들러 처리) 
 * 
 * http://www.java-school.net/spring/spring-security.php
 * http://blog.naver.com/PostView.nhn?blogId=belldie&logNo=30157181107
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
    private static Logger logger = Logger.getLogger(LoginController.class);
    
    /**
     * messageSource DI
     */
    @Autowired 
    MessageSourceAccessor messageSourceAccessor;
    
    /**
     * 로그인 화면
     * @param model
     * @param locale
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, Locale locale) throws Exception {
        if (logger.isInfoEnabled()) {
            logger.debug(" =====> Local : " + locale);
        }
        
        return "/common/login/login";         
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
    public String loginSuccess(ModelMap modelMap, Principal principal, HttpSession session, Locale locale) {
        logger.debug("Login Success!!!");
        
        return "/common/main";
    }
    
    /**
     * 로그인 실패
     * @param modelMap
     * @param session
     * @param locale
     * @return
     */
    @RequestMapping(value = "/loginFailed", method = RequestMethod.GET)
    public String loginFailed(ModelMap modelMap, HttpSession session, Locale locale) {
        logger.debug("Login Fail!!!");
        
        return "/common/login/login";
    }
    
    /**
     * timeout(세션만료)
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/timeout", method = RequestMethod.GET)
    public String timeout(ModelMap modelMap) {
        
        return "/common/login/timeout";
        
    }


}
