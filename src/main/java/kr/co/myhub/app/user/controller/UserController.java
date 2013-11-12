package kr.co.myhub.app.user.controller;

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
 * pacakage: kr.co.myhub.app.admin.controller 
 * file         : UserController.java , 2013. 11. 8.
 * author    : jmpark
 * email      : kbtapjm@gmail.com
 * 수정내용
 * ----------------------------------------------
 * 수정일      수정자  수정내용
 * ----------------------------------------------
 * 2013. 11. 8.     kbtapjm  최초생성
 *</pre>
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    private static Logger logger = Logger.getLogger(UserController.class);

    /**
     * messageSource DI
     */
    @Autowired 
    MessageSourceAccessor messageSourceAccessor;
    
    /**
     * 사용자 등록 화면(회원가입 화면)
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userCreate", method = RequestMethod.GET)
    public String userCreate(Model model) throws Exception {
        if (logger.isInfoEnabled()) {
            logger.debug("UserController  userCreate Call -----------------------------");
        }
        
        return "/user/userCreate";         
    }
    
    

}
