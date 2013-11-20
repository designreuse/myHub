package kr.co.myhub.app.user.controller;

import java.util.Locale;

import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.domain.validator.UserValidator;
import kr.co.myhub.app.user.service.UserService;
import kr.co.myhub.appframework.constant.SecurityPoliciesEnum;
import kr.co.myhub.appframework.constant.StatusEnum;
import kr.co.myhub.appframework.constant.TypeEnum;
import kr.co.myhub.appframework.util.EncryptionUtil;
import kr.co.myhub.appframework.vo.ApiResult;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * file   : UserController.java
 * date   : 2013. 11. 17.
 * author : jmpark
 * content: 유저 웹 요청 처리 (URL Mapping, Data API)
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 17.   kbtapjm     최초생성
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
     *  Service DI
     */
    @Autowired
    UserService userService;
    
    // ===================================================================================
    // Simple URL Mapping
    // ===================================================================================
    
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
    
    // ===================================================================================
    // API
    // ===================================================================================
    
    /**
     * email 중복체크
     * @param model
     * @param email
     * @return 중복유무
     */
    @RequestMapping(value = "/getUserByEmail", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Boolean getUserByEmail(Model model,
            @RequestParam(value = "email", required = true) String email) {
        Boolean result = Boolean.FALSE;
        
        try {
            User user = userService.findByEmail(email);
            
            if (user != null) {
                result = Boolean.TRUE;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * 유저 등록처리
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = "/userSave", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ApiResult userSave(Model model, 
            @ModelAttribute User user,
            BindingResult bindResult,
            Locale locole) {
        
        ApiResult result = new ApiResult();
        
        try {
            /* Data Vaildation Check */
            UserValidator userValidator = new UserValidator(TypeEnum.CREATE);
            userValidator.validate(user, bindResult);
            
            if (bindResult.hasErrors()) {
                if (bindResult.getErrorCount() > 0) {
                    FieldError fe = bindResult.getFieldError();
                    
                    result.setStatus(StatusEnum.FAIL);
                    result.setMessage(messageSourceAccessor.getMessage(fe.getCode(), new Object[] {SecurityPoliciesEnum.MinimumPasswordLength.getValue()}, locole));    
                } else {
                    ObjectError oe = bindResult.getGlobalError();
                    
                    result.setStatus(StatusEnum.FAIL);
                    result.setMessage(messageSourceAccessor.getMessage(bindResult.getGlobalError().getCode(), locole));
                }
                
                return result;
            }
            
            // password encrypt
            String encryptPassword = EncryptionUtil.getEncryptPassword(user.getPassword());
            user.setPassword(encryptPassword);
            
            User retUser = userService.create(user);
            
            // result
            if (retUser == null) {
                result.setStatus(StatusEnum.FAIL);    
            } else {
                result.setStatus(StatusEnum.SUCCESS);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        
            // Exception result
            result.setStatus(StatusEnum.FAIL);
            result.setMessage(e.getMessage());
        }
        
        return result;
    }
    
    

}
