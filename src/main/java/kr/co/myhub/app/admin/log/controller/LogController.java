package kr.co.myhub.app.admin.log.controller;

import kr.co.myhub.app.common.login.controller.LoginController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * file   : LogController.java
 * date   : 2014. 5. 31.
 * author : jmpark
 * content: 로그(로그인 이력) 관련 웹 요청 처리
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 5. 31.   kbtapjm     최초생성
 */
@Controller
@RequestMapping(value = "/admin/log")
public class LogController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    
    /**
     * messageSource DI
     */
    @Autowired 
    MessageSourceAccessor messageSourceAccessor;
    
    /**
     * 로그 목록(이력 조회)
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logList", method = RequestMethod.GET)
    public String userSearch(Model model) throws Exception {
        return "/admin/log/logList";         
    }
    
    

}
