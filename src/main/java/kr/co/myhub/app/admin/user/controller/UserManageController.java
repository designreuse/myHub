package kr.co.myhub.app.admin.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.myhub.app.admin.user.domain.dto.UserDto;
import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.service.UserService;
import kr.co.myhub.appframework.constant.Result;
import kr.co.myhub.appframework.exception.MyHubException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    MessageSourceAccessor msa;
    
    /**
     *  Service DI
     */
    @Autowired
    UserService userService;
    
    // ===================================================================================
    // Simple URL Mapping
    // ===================================================================================
    
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
    
    // ===================================================================================
    // Data 처리
    // ===================================================================================
    
    /**
     * 유저 목록
     * @param model
     * @param UserDto
     * @param locale
     * @return
     */
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getUserList(Model model,
            @ModelAttribute UserDto UserDto,
            Locale locale) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            // 목록
            Page<User> users = userService.findAllUser(UserDto);
            
            resultMap.put("resultCd", Result.SUCCESS.getCode());
            resultMap.put("resultMsg", Result.SUCCESS.getText());
            resultMap.put("resultData", users.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
        
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
        }
        
        return resultMap;
    }


}
