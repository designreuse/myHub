package kr.co.myhub.app.user.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.myhub.app.admin.user.domain.dto.UserDto;
import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.domain.validator.UserValidator;
import kr.co.myhub.app.user.service.UserService;
import kr.co.myhub.appframework.constant.Result;
import kr.co.myhub.appframework.constant.TypeEnum;
import kr.co.myhub.appframework.constant.UserPrivEnum;
import kr.co.myhub.appframework.exception.MyHubException;
import kr.co.myhub.appframework.util.CommonUtil;
import kr.co.myhub.appframework.util.EncryptionUtil;
import kr.co.myhub.appframework.util.MailUtil;
import kr.co.myhub.appframework.vo.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * 
 * file   : UserController.java
 * date   : 2013. 11. 17.
 * author : jmpark
 * content: 유저 웹 요청 처리 (URL Mapping, Data Response)
 * ref : http://www.ilhwan.com/spring-security-example/(회원가입후 자동 로그인)
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
    MessageSourceAccessor msa;
    
    /**
     * application.properties 정보
     */
    @Autowired 
    Properties prop;
    
    /**
     * Session Registry(세션 트래킹)
     */
    @Autowired
    SessionRegistry sessionRegistry;
    
    /**
     * AuthenticationManager
     */
    @Autowired
    protected AuthenticationManager authenticationManager;
    
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
    public String userAdd(Model model) throws Exception {
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
    
    /**
     * 유저 비밀번호 수정 화면
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userPasswordEdit", method = RequestMethod.GET)
    public String userPasswordEdit(Model model,
            @RequestParam(value = "email", required = false) String email) throws Exception {
        
        model.addAttribute("email", email);
        
        return "/user/userPasswordEdit";         
    }
    
    /**
     * 유저 프로필 등록 화면
     * @param model
     * @param userKey
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userProfileAdd", method = RequestMethod.GET)
    public String userProfileAdd(Model model,
            @RequestParam(value = "userKey", required = true) String userKey) throws Exception {
        
        model.addAttribute("userKey", userKey);
        
        return "/user/userProfileAdd";         
    }
    
    // ===================================================================================
    // Data 처리
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
     * 유저 등록
     * @param model
     * @param user
     * @param bindResult
     * @param locale
     * @return
     */
    @RequestMapping(value = "/userCreate", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> userCreate(Model model, 
            @ModelAttribute User user, 
            BindingResult bindResult, 
            Locale locale,
            HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            /* Data Vaildation Check */
            UserValidator userValidator = new UserValidator(TypeEnum.CREATE.getCode());
            userValidator.validate(user, bindResult);
            
            if (bindResult.hasErrors()) {
                FieldError fe = bindResult.getFieldError();
                
                throw new Exception(msa.getMessage(fe.getCode(), locale));
            }
            
            /* 비밀번호 암호화(SHA-256) */
            String encryptPassword = EncryptionUtil.getEncryptPassword(user.getPassword());
            user.setPassword(encryptPassword);
            user.setLastPassword(encryptPassword);
            
            /* 유저 등록 */
            User retUser = userService.insertUser(user);
            
            if (retUser != null) {
                resultMap.put("resultCd", Result.SUCCESS.getCode());
                resultMap.put("resultMsg", msa.getMessage("myhub.error.register.success", locale));
                
                /* 회원가입 이메일 전송 */ 
                this.asyncSendMail(retUser, locale);
                
                /* 회원가입 성공 후 시큐리티 인증 처리 */
                /*UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
                 
                token.setDetails(new WebAuthenticationDetails(request));
                Authentication authenticatedUser = authenticationManager.authenticate(token);
                 
                SecurityContextHolder.getContext().setAuthentication(authenticatedUser);*/
                
            } else {
                resultMap.put("resultCd", Result.FAIL.getCode());
                resultMap.put("resultMsg", msa.getMessage("myhub.error.register.failed", locale));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
        
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
        }
        
        return resultMap;
    }
    
    /**
     * 유저정보 조회 
     * @param model
     * @param principal
     * @param session
     * @param locale
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getUserInfo(Model model, 
            Principal principal, 
            HttpSession session,
            Locale locale) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            User sUser = (User) session.getAttribute("sUser");
            
            if (sUser == null) {
                throw new Exception(msa.getMessage("myhub.label.list.null", locale));
            }
            
            // 유저권한과의 1:1 관계일때 유저정보 조회시 권한정보도 같이 조회처리
            if (log.isDebugEnabled()) {
                // 권한정보 얻기
                String authority = "";
                for (UserPrivEnum userPrivEnum : UserPrivEnum.values()) {
                    if (userPrivEnum.getCode() == sUser.getUserAuth().getPriv()) {
                        authority = userPrivEnum.getText();
                    }
                }
                log.debug("authority : {}", authority);
            }
            
            resultMap.put("resultCd", Result.SUCCESS.getCode());
            resultMap.put("resultMsg", Result.SUCCESS.getText());
            resultMap.put("resultData", sUser);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
        
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
        }
        
        return resultMap;
    }
    
    /**
     * 유저 수정
     * @param model
     * @param user
     * @param bindResult
     * @param locale
     * @param session
     * @return
     */
    @RequestMapping(value = "/userUpdate", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> userUpdate(Model model, 
            @ModelAttribute User user,
            BindingResult bindResult,
            Locale locale,
            HttpSession session) {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            /* Data Vaildation Check */
            UserValidator userValidator = new UserValidator(TypeEnum.UPDATE.getCode());
            userValidator.validate(user, bindResult);
            
            if (bindResult.hasErrors()) {
                FieldError fe = bindResult.getFieldError();
                
                throw new Exception(msa.getMessage(fe.getCode(), locale));
            }
            
            /* 유저 수정 */
            user.setModDt(new Date());
            long result = userService.updateUser(user);
            
            if (result != 0) {
                // 세션에 정보 저장
                User sUser = userService.findByEmail(user.getEmail());
                session.setAttribute("sUser", sUser);
                session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
                
                resultMap.put("resultCd", Result.SUCCESS.getCode());
                resultMap.put("resultMsg", msa.getMessage("myhub.label.result.success", locale));
            } else {
                resultMap.put("resultCd", Result.FAIL.getCode());
                resultMap.put("resultMsg", msa.getMessage("myhub.error.common.fail", locale));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
        
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
        }
        
        return resultMap;
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
    public Map<String, Object> userDelete(Model model, 
            @ModelAttribute User user,
            BindingResult bindResult,
            Locale locale,
            HttpSession session) {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            /* Data Vaildation Check */
            UserValidator userValidator = new UserValidator(TypeEnum.DELETE.getCode());
            userValidator.validate(user, bindResult);
            
            if (bindResult.hasErrors()) {
                FieldError fe = bindResult.getFieldError();
                
                throw new Exception(msa.getMessage(fe.getCode(), locale));
            }
    
            /* 유저 삭제  (Spring Data Jpa에서 삭제는 void형만 존재) */
            userService.deleteUser(user);
            
            resultMap.put("resultCd", Result.SUCCESS.getCode());
            resultMap.put("resultMsg", msa.getMessage("myhub.label.result.success", locale));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
        
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
        }
        
        return resultMap;
    }
    
    /**
     * 이메일 찾기
     * @param model
     * @param phoneNo
     * @return
     */
    @RequestMapping(value = "/emailSearch", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> emailSearch(Model model, @RequestParam(value = "phoneNo", required = true) String phoneNo,
            Locale locale) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            List<User> list = userService.findByPhoneNo(phoneNo);
            if (!list.isEmpty()) {
                resultMap.put("resultCd", Result.SUCCESS.getCode());
                resultMap.put("resultMsg", Result.SUCCESS.getText());
                
                StringBuilder sb = new StringBuilder();
                for (User data : list) {
                    if (data == null) continue;
                    
                    sb.append(data.getUserName().concat(" : ").concat(data.getEmail()).concat("  "));
                }
                
                resultMap.put("resultData", sb.toString());
            } else {
                resultMap.put("resultCd", Result.FAIL.getCode());
                resultMap.put("resultMsg", msa.getMessage("myhub.label.list.null", locale));    
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
            
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
        }
        
        return resultMap;
    }
    
    /**
     * 비밀번호 찾기
     * @param model
     * @param email
     * @return
     */
    @RequestMapping(value = "/passwordSearch", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> passwordSearch(Model model, 
            @RequestParam(value = "email", required = true) String email,
            Locale locale) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            /* 유저정보 조회 */
            User user = userService.findByEmail(email);
            if (user == null) {
                throw new Exception(msa.getMessage("myhub.label.list.null", locale));
            }
            
            /* 임시비밀번호 생성 */
            String tmpPassword = CommonUtil.getTmpPassword();
            
            /* 변경 할 비밀번호 암호화 */
            String password = EncryptionUtil.getEncryptPassword(tmpPassword);
            
            /* 유저 비밀번호 수정 */
            long ret = userService.updatePasswordByEmail(password, user.getLastPassword(), email);
            if (ret == 0) {
                throw new Exception(msa.getMessage("myhub.error.update.error", locale));
            }
            
            /* email 전송 */
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("to", email);
            params.put("subject", msa.getMessage("myhub.label.password.temp.info", locale));
            params.put("content", msa.getMessage("myhub.label.password.temp.send", new Object[]{tmpPassword}, locale));
            
            boolean result = MailUtil.mailsend(params);
            if (!result) {
                throw new Exception(msa.getMessage("myhub.label.password.fail.info", locale));
            }
            
            resultMap.put("resultCd", Result.SUCCESS.getCode());
            resultMap.put("resultMsg", msa.getMessage("myhub.label.password.success.info", locale));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
            
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
        }
        
        return resultMap;
    }
    
    /**
     * 비밀번호 변경
     * @param model
     * @param beforePassword
     * @param afterPassword
     * @param locale
     * @return
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> changePassword(Model model, 
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "beforePassword", required = true) String beforePassword,
            @RequestParam(value = "afterPassword", required = true) String afterPassword,
            @ModelAttribute User checkUser, BindingResult bindResult, 
            Locale locale) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            // 비교할때는 암호화 
            String encBeforePassword = EncryptionUtil.getEncryptPassword(beforePassword);
            String encafterPassword = EncryptionUtil.getEncryptPassword(afterPassword);
            
            /* 유저정보 조회 */
            User user = userService.findByEmail(email);
            if (user == null) {
                throw new Exception(msa.getMessage("myhub.label.list.null", locale));
            }
            
            /* 유저의 현재 비밀번호 일치여부 체크 */
            if (!user.getPassword().equals(encBeforePassword)) {
                throw new Exception(msa.getMessage("myhub.error.befor.password1", locale));
            }
            
            /* 변경 할 비밀번호가 현재 비밀번호와 일치하는지 체크 */
            if (beforePassword.equals(afterPassword)) {
                throw new Exception(msa.getMessage("myhub.error.befor.password2", locale));
            }
            
            /* 변경 할 비밀번호가 이전에 사용한 비밀번호와 동일한지 체크 */
            if (encafterPassword.equals(user.getLastPassword())) {
                throw new Exception(msa.getMessage("myhub.error.befor.password3", locale));
            }
            
            /* 변경 할 비밀번호 비밀번호 변경 규칙 체크  */
            checkUser.setUserId(user.getUserId());
            checkUser.setPassword(afterPassword);
            UserValidator userValidator = new UserValidator(5);
            userValidator.validate(checkUser, bindResult);
            
            if (bindResult.hasErrors()) {
                FieldError fe = bindResult.getFieldError();
                
                throw new Exception(msa.getMessage(fe.getCode(), locale));
            }
            
            /* 유저 비밀번호 수정 */
            long ret = userService.updatePasswordByEmail(encafterPassword, encBeforePassword, email);
            if (ret == 0) {
                throw new Exception(msa.getMessage("myhub.error.update.error", locale));
            }
            
            resultMap.put("resultCd", Result.SUCCESS.getCode());
            resultMap.put("resultMsg", msa.getMessage("myhub.error.befor.password4", locale));
            
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
            
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
        }
        
        return resultMap;
    }
    
    /**
     * 이메일 전송
     * @param user
     * @param locale
     */
    @Async
    public void asyncSendMail(final User user, final Locale locale) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("to", user.getEmail());
                params.put("subject", msa.getMessage("myhub.error.register.success", locale));
                params.put("content", user.getUserName().concat(msa.getMessage("myhub.error.register.success", locale)));
                
                MailUtil.mailsend(params);
            }
        };
        new Thread(task).start();
    }
    
    // ===================================================================================
    // temp(테스트, 임시)
    // ===================================================================================
    
    /**
     * 유저목록(ContentNegotiatingViewResolver)
     * .json, .xml호출하면 json, xml로 데이터 반환
     * @param modelMap
     * @return JSON/XML 데이터 반환
     */
    @RequestMapping(value = "/getUserListToXmlToJson", method = RequestMethod.GET)
    public String getUserListToXmlToJson(ModelMap modelMap, @ModelAttribute UserDto UserDto) {
        ApiResponse response = new ApiResponse();
        List<User> list = null;
        
        try {
            //list = userService.findAllUser(UserDto);
            
            //response.setStatus(StatusEnum.SUCCESS);
            response.setList(list);
            
            modelMap.addAttribute("result", response);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            // Exception result
            //response.setStatus(StatusEnum.FAIL);
            response.setMessage(e.getMessage());
            
            modelMap.addAttribute("result", response);
        }
        
        return null;
    }
    
    /**
     * 현재 접속 중인 사용자 세션정보 목록
     * @param model
     * @return
     */
    @RequestMapping(value = "/getActiveUserList", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getActiveUserList(Model model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HashMap<Object, Date> lastActivityData = new HashMap<Object, Date>();
        
        try {
            // sessionRegistry.getAllPrincipals() : 활성화된 세션을 갖고 있는  Principal 객체(User Detail)
            for (Object principal : sessionRegistry.getAllPrincipals()) {
                
                // 각 Principal이 갖고 있는 세션정보를 담고있는 SessionInformation 객체의 리스트
                for (SessionInformation  session : sessionRegistry.getAllSessions(principal, false)) {
                    log.debug("getLastRequest : {}", session.getLastRequest());
                    log.debug("getSessionId : {}", session.getSessionId());
                    log.debug("getPrincipal : {}", session.getPrincipal());
                    
                    lastActivityData.put(principal, session.getLastRequest());
                }
            }
            
            resultMap.put("resultCd", Result.SUCCESS.getCode());
            resultMap.put("resultMsg", Result.SUCCESS.getText());
            resultMap.put("resultData", lastActivityData);  // TODO: Princapal 객체 JSON 데이터로 파싱 처리
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
            
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
    }
    
    @RequestMapping(value = "/ajaxFileUpload", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String ajaxFileUpload(MultipartHttpServletRequest request, HttpServletResponse response) { 
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        //0. notice, we have used MultipartHttpServletRequest
        
        //1. get the files from the request object
        Iterator<String> itr =  request.getFileNames();
    
        MultipartFile mpf = request.getFile(itr.next());
        System.out.println(mpf.getOriginalFilename() +" uploaded!");
    
        try {
           //just temporary save file info into ufile
            
            log.debug("getBytes length : {}", mpf.getBytes().length); 
            log.debug("getBytes : {}", mpf.getBytes().length);
            log.debug("getContentType : {}", mpf.getContentType());
            log.debug("getOriginalFilename : {}", mpf.getOriginalFilename());   
    
       } catch (IOException e) {
           e.printStackTrace();
       }
        
        return "<img src='http://localhost:8080/spring-mvc-file-upload/rest/cont/get/"+Calendar.getInstance().getTimeInMillis()+"' />";
    }
}
