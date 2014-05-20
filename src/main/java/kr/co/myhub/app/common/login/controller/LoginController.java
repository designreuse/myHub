package kr.co.myhub.app.common.login.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.myhub.app.common.login.domain.LoginLog;
import kr.co.myhub.app.common.login.service.LoginService;
import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.service.UserService;
import kr.co.myhub.appframework.constant.AccountExpiredEnum;
import kr.co.myhub.appframework.constant.Result;
import kr.co.myhub.appframework.constant.SecurityPoliciesEnum;
import kr.co.myhub.appframework.constant.StatusEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 
 * file   : LoginController.java
 * date   : 2013. 11. 22.
 * author : jmpark
 * content: 로그인 관련 컨트롤러(스프링 시큐리티 핸들러 처리) 
 * 
 * http://www.java-school.net/spring/spring-security.php
 * http://blog.naver.com/alucard99?Redirect=Log&logNo=192570650
 * http://antop.tistory.com/151(로그아웃시 로그 기록)
 * 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 22.   kbtapjm     최초생성
 */
@Controller
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    
    /**
     * messageSource DI
     */
    @Autowired 
    MessageSourceAccessor messageSourceAccessor;
    
    /**
     *  userService DI
     */
    @Autowired
    UserService userService;
    
    /**
     *  loginService DI
     */
    @Autowired
    LoginService loginService;
    
    /**
     * 로그인 화면
     * @param model
     * @param locale
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, Locale locale,
            @RequestParam(value = "error", required = false, defaultValue = "false") Boolean error) throws Exception {
        
        if (error) {
            model.addAttribute("status", StatusEnum.FAIL);
            model.addAttribute("message", "사용자의 세션이 만료되었습니다. 다시 로그인을 하여 주세요. ");
        }
        
        return "/common/login/login";         
    }
    
    /**
     * 메인
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(Model model) throws Exception {
        
        return "/main";         
    }
    
    /**
     * 계정 잠금 체크
     * @param model
     * @param loginId
     * @param locale
     * @return
     */
    @RequestMapping(value = "/isAccountLocked", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> isAccountLocked(Model model, 
            @RequestParam(value = "email", required = true) String email,
            Locale locale) {
      
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> scPolicy = new HashMap<String, Object>();
        
        try {
            boolean result = loginService.isAccountLocked(email, scPolicy);
            if (log.isDebugEnabled()) {
                log.debug("isAccountLocked : {} ", result);    
            }
            
            if (result) {
                String args1 = scPolicy.get(SecurityPoliciesEnum.AccountLockoutDurationValue.getText()).toString();
                String args2 = scPolicy.get(SecurityPoliciesEnum.AccountLockoutThresholdValue.getText()).toString();
                
                String msg = messageSourceAccessor.getMessage("myhub.label.login.msg.accountBlocked", new Object[] {args2, args1}, locale);
                
                resultMap.put("resultCd", Result.SUCCESS.getCode());
                resultMap.put("resultMsg", msg);
            } else {
                resultMap.put("resultCd", Result.SUCCESS.getCode());
                resultMap.put("resultMsg", "");
            }
        } catch (Exception e) {
            log.error("Exception : {}", e.getMessage());
            
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", e.getMessage());
        }
        
        return resultMap;
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
    public String loginSuccess(Model model, 
            Principal principal, 
            HttpSession session, 
            HttpServletRequest request, 
            Locale locale) {
        if (log.isDebugEnabled()) {
            log.debug("====== Login Result : Success ===== ");
        }
        
        Map<String, Object> scPolicy = new HashMap<String, Object>();
        String message = "";
        String code = "";
        
        String email = principal.getName();
        
        try {
            /* 암호만료여부 체크 */
            int isAccountExpired = loginService.isAccountExpired(email, scPolicy);
            
            if (isAccountExpired == AccountExpiredEnum.expired.getValue()) {
                message = messageSourceAccessor.getMessage("myhub.label.login.msg.passwordExpired", locale);
                code = String.valueOf(SecurityPoliciesEnum.MaximumPasswordAgeRegular.getCode());
            } else {
                if (isAccountExpired == AccountExpiredEnum.expiring.getValue()) {
                    String args1 = scPolicy.get("ExpiryDate").toString();
                    
                    message = messageSourceAccessor.getMessage("myhub.label.login.msg.passwordExpiryWarning", new Object[] {args1}, locale);
                    code = String.valueOf(SecurityPoliciesEnum.PasswordExpiryWarning.getCode());
                }
                
                /* 로그인 상태 처리 추가 */
                userService.updateUserSuccessLogin(null, email);
                
                /* 로그인 정보는 필요한 정보만 세팅 */
                User user = userService.findByEmail(email);
                session.setAttribute("sUser", user);
                session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
                
                /* 로그인 이력 추가 */
                LoginLog loginLog = new LoginLog();
                loginLog.setEmail(user.getEmail());
                loginLog.setIpAddress(request.getRemoteAddr());
                loginLog.setLoginDate(new Date());
                loginLog.setUser(user);
                
                loginService.create(loginLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.invalidate();
        }
        
        FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
        fm.put("code", code);
        fm.put("message", message);
        
        return "redirect:/main";
    }
    
    /**
     * 로그인 실패
     * @param modelMap
     * @param session
     * @param locale
     * @return
     */
    @RequestMapping(value = "/loginFailed", method = RequestMethod.GET)
    public String loginFailed(Model model, 
            HttpSession session,
            RedirectAttributes redirectAttr,
            HttpServletRequest request,
            Locale locale) {
        if (log.isDebugEnabled()) {
            log.debug("====== Login Result : Fail ===== ");
        }
        
        // 세션값에 저장
        //redirectAttr.addAttribute("status", StatusEnum.FAIL);
        
        // FlashMap에 전달할 값을 저장한다.
        FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
        fm.put("status", StatusEnum.FAIL);
        fm.put("message", messageSourceAccessor.getMessage("myhub.error.login.fail", locale));
        
        return "redirect:/login";
    }
    
    /**
     * timeout(세션만료)
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/timeout", method = RequestMethod.GET)
    public String timeout(Model model) {
        if (log.isDebugEnabled()) {
            log.debug("====== Login Result : Timeout ===== ");
        }
        
        return "/common/login/timeout";
    }
    
    /**
     * expired(이중로그인시 세션만료)
     * @param model
     * @return
     */
    @RequestMapping(value = "/expired", method = RequestMethod.GET)
    public String expired(Model model) {
        if (log.isDebugEnabled()) {
            log.debug("====== Login Result : Expired ===== ");    
        }
        
        return "/common/login/expired";
    }

}
