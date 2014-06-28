package kr.co.myhub.app.admin.user;

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
 * file   : UserManageController.java
 * date   : 2014. 6. 28.
 * author : jmpark
 * content: 유저관리(어드민) 
 * ref:
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 6. 28.   kbtapjm     최초생성
 */
@Controller
@RequestMapping(value = "/admin/userManage")
public class UserManageController {
    
 private static final Logger log = LoggerFactory.getLogger(UserManageController.class);
    
    /**
     * messageSource DI
     */
    @Autowired 
    MessageSourceAccessor messageSourceAccessor;
    
    /**
     * 유저 목록
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public String userList(Model model) throws Exception {
        return "/admin/user/userList";         
    }


}
