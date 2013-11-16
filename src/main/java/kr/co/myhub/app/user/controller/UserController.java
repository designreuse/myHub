package kr.co.myhub.app.user.controller;

import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
     *  Service DI
     */
    @Autowired
    UserService userService;
    
    /**
     * 사용자 등록 화면(회원가입 화면)
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userCreate", method = RequestMethod.GET)
    public String userCreate(Model model) throws Exception {
        
        return "/user/userCreate";         
    }
    
    /**
     * 사용자 정보찾기 화면
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userSearch", method = RequestMethod.GET)
    public String userSearch(Model model) throws Exception {
        
        return "/user/userSearch";         
    }
    
    /**
     * 유저 등록처리
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/userSave", method = RequestMethod.POST)
    @ResponseBody
    public User userSave(Model model, @ModelAttribute User user) {
        if (logger.isInfoEnabled()) {
            logger.debug("UserController  userSave Call -----------------------------");
        }
        
        // TODO: Spring Validator
        
        // TODO: JSON, XML data return 
        
        
        User result = null;
        
        try {
            result = userService.create(user);
            
            // TODO: API result 타입으로 변경
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    

}
