package kr.co.myhub.app.user.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.domain.validator.UserValidator;
import kr.co.myhub.app.user.service.UserService;
import kr.co.myhub.appframework.constant.SecurityPoliciesEnum;
import kr.co.myhub.appframework.constant.StatusEnum;
import kr.co.myhub.appframework.constant.TypeEnum;
import kr.co.myhub.appframework.constant.UserPrivEnum;
import kr.co.myhub.appframework.util.EncryptionUtil;
import kr.co.myhub.appframework.vo.ApiResponse;
import kr.co.myhub.appframework.vo.ApiResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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
    @RequestMapping(value = "/userAdd", method = RequestMethod.GET)
    public String userCreate(Model model) throws Exception {
        
        return "/user/userAdd";         
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
     * 유저 정보 화면
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model) throws Exception {
        
        return "/user/userInfo";         
    }
    
    /**
     * 유저 수정 화면
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userEdit", method = RequestMethod.GET)
    public String userEdit(Model model) throws Exception {
        
        return "/user/userEdit";         
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
    @RequestMapping(value = "/getUserDuplicateCheck", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Boolean getUserDuplicateCheck(Model model,
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
    @RequestMapping(value = "/userCreate", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ApiResult userCreate(Model model, 
            @ModelAttribute User user,
            BindingResult bindResult,
            Locale locole) {
        
        ApiResult result = new ApiResult();
        
        try {
            /* Data Vaildation Check */
            UserValidator userValidator = new UserValidator(TypeEnum.CREATE.getCode());
            userValidator.validate(user, bindResult);
            
            if (bindResult.hasErrors()) {
                if (bindResult.getErrorCount() > 0) {
                    FieldError fe = bindResult.getFieldError();
                    
                    result.setStatus(StatusEnum.FAIL);
                    result.setMessage(messageSourceAccessor.getMessage(fe.getCode(), new Object[] {SecurityPoliciesEnum.MinimumPasswordLength.getValue()}, locole));    
                } else {
                    //ObjectError oe = bindResult.getGlobalError();
                    
                    result.setStatus(StatusEnum.FAIL);
                    result.setMessage(messageSourceAccessor.getMessage(bindResult.getGlobalError().getCode(), locole));
                }
                
                return result;
            }
            
            // password encrypt
            String encryptPassword = EncryptionUtil.getEncryptPassword(user.getPassword());
            user.setPassword(encryptPassword);
            
            // 마지막 패스워드 값 설정
            user.setLastPassword(encryptPassword);
            
            User retUser = userService.create(user);
            
            // result
            if (retUser != null) {
                result.setStatus(StatusEnum.SUCCESS);
            } else {
                result.setStatus(StatusEnum.FAIL);    
            }
            
            // TODO: 유저등록(회원가입) 후 이메일 전송 기능 추가
            
        } catch (Exception e) {
            e.printStackTrace();
        
            // Exception result
            result.setStatus(StatusEnum.FAIL);
            result.setMessage(e.getMessage());
        }
        
        return result;
    }
  
    /**
     * 유저정보 조회 
     * @param model
     * @param userKey
     * @return
     */
    @RequestMapping(value = "/getUserByUserKey/{userKey}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ApiResult getUserByUserKey(Model model, 
            @PathVariable("userKey") Long userKey) {
        ApiResult result = new ApiResult();
        User retuser = null;
        
        try {
            retuser = userService.findByUserKey(userKey);
            
            result.setStatus(StatusEnum.SUCCESS);
            result.setData(retuser);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // Exception result
            result.setStatus(StatusEnum.FAIL);
            result.setMessage(e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 이메일로 유저정보 조회
     * @param model
     * @param email
     * @return
     */
    @RequestMapping(value = "/getUserByEmail", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ApiResult getUserByEmail(Model model, 
            @RequestParam("email") String email,
            Locale locole) {
        ApiResult result = new ApiResult();
        User retuser = null;
        
        // get방식일때는  String문자는 인코딩처리 필수
        if (log.isDebugEnabled()) {
            log.debug(" ====================================================== ");
            log.debug(" email : " + email);
            log.debug(" ====================================================== ");    
        }
        
        try {
            /* 필수값 체크 (조회는 validator 사용 안함) */
            if (email == null || email.length() == 0) {
                result.setStatus(StatusEnum.FAIL);
                result.setMessage(messageSourceAccessor.getMessage("myhub.label.input.email.address", locole));
                
                return result;
            }
            
            retuser = userService.findByEmail(email);
            
            result.setStatus(StatusEnum.SUCCESS);
            result.setData(retuser);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // Exception result
            result.setStatus(StatusEnum.FAIL);
            result.setMessage(e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 유저목록
     * @param model
     * @return
     */
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ApiResult getUserList(Model model) {
        ApiResult result = new ApiResult();
        List<User> list = null;
        
        try {
            list = userService.findAllUser();
            
            result.setStatus(StatusEnum.SUCCESS);
            result.setData(list);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // Exception result
            result.setStatus(StatusEnum.FAIL);
            result.setMessage(e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 유저목록(ContentNegotiatingViewResolver)
     * .json, .xml호출하면 json, xml로 데이터 반환
     * @param modelMap
     * @return JSON/XML 데이터 반환
     */
    @RequestMapping(value = "/getUserListToXmlToJson", method = RequestMethod.GET)
    public String getUserListToXmlToJson(ModelMap modelMap) {
        ApiResponse response = new ApiResponse();
        List<User> list = null;
        
        try {
            list = userService.findAllUser();
            
            response.setStatus(StatusEnum.SUCCESS);
            response.setList(list);
            
            modelMap.addAttribute("result", response);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // Exception result
            response.setStatus(StatusEnum.FAIL);
            response.setMessage(e.getMessage());
            
            modelMap.addAttribute("result", response);
        }
        
        return null;
    }
    
    /**
     * 유저 수정
     * @param model
     * @param user
     * @param bindResult
     * @param locole
     * @return
     */
    @RequestMapping(value = "/userUpdate", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ApiResult userUpdate(Model model, 
            @ModelAttribute User user,
            BindingResult bindResult,
            Locale locole) {
        
        ApiResult result = new ApiResult();
        
        try {
            /* Data Vaildation Check */
            UserValidator userValidator = new UserValidator(TypeEnum.UPDATE.getCode());
            userValidator.validate(user, bindResult);
            
            if (bindResult.hasErrors()) {
                if (bindResult.getErrorCount() > 0) {
                    FieldError fe = bindResult.getFieldError();
                    
                    result.setStatus(StatusEnum.FAIL);
                    result.setMessage(messageSourceAccessor.getMessage(fe.getCode(), new Object[] {SecurityPoliciesEnum.MinimumPasswordLength.getValue()}, locole));    
                } else {
                    //ObjectError oe = bindResult.getGlobalError();
                    
                    result.setStatus(StatusEnum.FAIL);
                    result.setMessage(messageSourceAccessor.getMessage(bindResult.getGlobalError().getCode(), locole));
                }
                
                return result;
            }
            
            // password encrypt
            String encryptPassword = EncryptionUtil.getEncryptPassword(user.getPassword());
            user.setPassword(encryptPassword);
            
            user.setModDt(new Date());
            
            User retUser = userService.update(user);
            
            // result
            if (retUser != null) {
                result.setStatus(StatusEnum.SUCCESS);
            } else {
                result.setStatus(StatusEnum.FAIL);    
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        
            // Exception result
            result.setStatus(StatusEnum.FAIL);
            result.setMessage(e.getMessage());
        }
        
        return result;
    }
    
    /**
     * 유저 삭제
     * @param model
     * @param user
     * @param bindResult
     * @param locole
     * @return
     */
    @RequestMapping(value = "/userDelete", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ApiResult userDelete(Model model, 
            @ModelAttribute User user,
            BindingResult bindResult,
            Locale locole) {
        
        ApiResult result = new ApiResult();
        
        try {
            /* Data Vaildation Check */
            UserValidator userValidator = new UserValidator(TypeEnum.DELETE.getCode());
            userValidator.validate(user, bindResult);
            
            if (bindResult.hasErrors()) {
                FieldError fe = bindResult.getFieldError();
                
                result.setStatus(StatusEnum.FAIL);
                result.setMessage(messageSourceAccessor.getMessage(fe.getCode(), locole));
                
                return result;
            }
            
            // Spring Data Jpa에서 Delete는 void형만 존재
            userService.delete(user);
            
            result.setStatus(StatusEnum.SUCCESS);
            
        } catch (Exception e) {
            e.printStackTrace();
        
            // Exception result
            result.setStatus(StatusEnum.FAIL);
            result.setMessage(e.getMessage());
        }
        
        return result;
    }
}
