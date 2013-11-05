package kr.co.myhub.app.common.login.controller;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * <pre>
 * pacakage : kr.co.myhub.app.common.login.controller 
 * file     : LoginController.java , 2013. 9. 26.
 * author   : jmpark
 * email    : kbtapjm@gmail.com
 * 수정내용
 * ----------------------------------------------
 * 수정일      수정자  수정내용
 * ----------------------------------------------
 * 
 *</pre>
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
    
    


}
