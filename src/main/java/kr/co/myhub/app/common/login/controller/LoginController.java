package kr.co.myhub.app.common.login.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.myhub.app.common.login.domain.LogHistory;
import kr.co.myhub.app.common.login.service.LoginService;
import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.service.UserService;
import kr.co.myhub.appframework.constant.AccountExpiredEnum;
import kr.co.myhub.appframework.constant.LogTypeEnum;
import kr.co.myhub.appframework.constant.Result;
import kr.co.myhub.appframework.constant.Security;
import kr.co.myhub.appframework.constant.SecurityPoliciesEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    MessageSourceAccessor msa;
    
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
     * 로그인
     * @param model
     * @param locale
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/login", "/"}, method = RequestMethod.GET)
    public String login(Model model, Locale locale,
            @RequestParam(value = "error", required = false, defaultValue = "none") String error) throws Exception {
        
        // 토큰만료시 에러 내용  TODO: 세션만료에 대한 처리도 추가 필요
        if (error.equals("expired")) {
            model.addAttribute("resultCd", Result.FAIL.getCode());
            model.addAttribute("resultMsg", msa.getMessage("myhub.error.login.expired", locale));
        }
        
        return "/login";         
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
            if (result) {
                String args1 = scPolicy.get(SecurityPoliciesEnum.AccountLockoutDurationValue.getText()).toString();
                String args2 = scPolicy.get(SecurityPoliciesEnum.AccountLockoutThresholdValue.getText()).toString();
                
                String msg = msa.getMessage("myhub.label.login.msg.accountBlocked", new Object[] {args2, args1}, locale);
                
                resultMap.put("resultCd", Result.FAIL.getCode());
                resultMap.put("resultMsg", msg);
            } else {
                resultMap.put("resultCd", Result.SUCCESS.getCode());
                resultMap.put("resultMsg", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
            
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", msa.getMessage("myhub.error.login.fail", locale));
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
        String resultMsg = "";
        String resultCd = "";
        
        /**
         * 스프링 시큐리티 인증 통과 후 계정 체크,  로그인 이력 등록, db처리 작업이 있는 경우에는
         * 각종 예외가 발생 할 가능성이 있기 때문에 성공페이지로 이동하기전 로그인 페이지에서 모든걸 처리하는게 최선의 방법
         * 모든 케이스가 성공이면 메인페이지로 리다이렉트
         */
        try {
            String email = principal.getName();
            
            /* 암호만료여부 체크 */
            int isAccountExpired = loginService.isAccountExpired(email, scPolicy);
            
            // 비밀번호 만료 => 로그인 하지 못함
            if (isAccountExpired == AccountExpiredEnum.expired.getValue()) {
                resultMsg = msa.getMessage("myhub.label.login.msg.passwordExpired", locale);
                resultCd = Security.TokenExpired.getCode();
            } else {
                
                // 비밀번호 만료 진행 => 경고로 알림
                if (isAccountExpired == AccountExpiredEnum.expiring.getValue()) {
                    String args1 = scPolicy.get("ExpiryDate").toString();
                    
                    resultMsg = msa.getMessage("myhub.label.login.msg.passwordExpiryWarning", new Object[] {args1}, locale);
                    resultCd = Security.TokenExpiring.getCode();
                } else {
                    resultCd = Result.SUCCESS.getCode();
                }
                
                /* 로그인 상태 처리 변경  */
                userService.updateUserLogin(true, email);
                
                /* 세션에 로그인 정보 저장  */
                User sUser = userService.findByEmail(email);
                
                session.setAttribute("sUser", sUser);
                session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
                
                /* 로그인 이력 추가  */
                LogHistory loginLog = new LogHistory();
                loginLog.setEmail(sUser.getEmail());
                loginLog.setIpAddress(request.getRemoteAddr());
                loginLog.setLogDate(new Date());
                loginLog.setLogType(LogTypeEnum.logIn.getText());
                loginLog.setUser(sUser);
                
                loginService.create(loginLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.invalidate();
            
            resultCd = Security.AuthenticationFail.getCode();
            resultMsg =  msa.getMessage("myhub.error.common.fail", locale);            
        }
        
        // 로그인 결과 확인
        if (log.isDebugEnabled()) {
            log.debug("resultCd : {}", resultCd);
            log.debug("resultMsg : {}", resultMsg);    
        }
        
        model.addAttribute("resultCd", resultCd);
        model.addAttribute("resultMsg", resultMsg);
        
        return "/common/login/loginResult";  
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
        
        /**
         * addAttribute : URL에 파라미터가 노출
         * addFlashAttribute : 리다이렉트시 세션 값에 저장 후 소멸
         */
        // redirectAttr.addFlashAttribute("resultCd", Result.FAIL.getCode());
        // redirectAttr.addFlashAttribute("resultMsg", msa.getMessage("myhub.error.login.fail", locale));
        
        // FlashMap에 전달할 값을 저장한다.
        /*FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
        fm.put("resultCd", Result.FAIL.getCode());
        fm.put("resultMsg", msa.getMessage("myhub.error.login.fail", locale));*/
        
        model.addAttribute("resultCd", Security.AuthenticationFail.getCode());
        model.addAttribute("resultMsg", msa.getMessage("myhub.error.login.fail", locale));
        
        return "/common/login/loginResult";  
    }
    
    /**
     * 접근 거부
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String timeout(Model model, HttpServletRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("====== Page : AccessDenied ===== ");
        }
        
        // 접근거부 상세 내용
        AccessDeniedException ex = (AccessDeniedException) request.getAttribute("SPRING_SECURITY_403_EXCEPTION");
        log.debug("ex : {}", ex.getMessage());
        
        return "/common/auth/accessDenied";
    }
    
    /**
     * expired(이중로그인시 세션만료)
     * @param model
     * @return
     */
    @RequestMapping(value = "/expired", method = RequestMethod.GET)
    public String expired(Model model) {
        if (log.isDebugEnabled()) {
            log.debug("====== Page : Expired ===== ");    
        }
        
        return "/common/auth/expired";
    }
    
    /**
     * 타임아웃
     * @param model
     * @return
     */
    @RequestMapping(value = "/timeout", method = RequestMethod.GET)
    public String timeout(Model model) {
        if (log.isDebugEnabled()) {
            log.debug("====== Page : Timeout ===== ");    
        }
        
        return "/common/auth/timeout";
    }
}
