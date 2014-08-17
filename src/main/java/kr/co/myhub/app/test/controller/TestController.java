package kr.co.myhub.app.test.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import kr.co.myhub.app.admin.user.domain.dto.UserDto;
import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.service.UserService;
import kr.co.myhub.appframework.constant.Result;
import kr.co.myhub.appframework.exception.MyHubException;
import kr.co.myhub.appframework.vo.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 
 * file   : TestController.java
 * date   : 2014. 8. 17.
 * author : jmpark
 * content: 테스트 컨트롤러(각종 테스트 모음0 
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 8. 17.   jmpark     최초생성
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {
    
    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    
   /**
    * 
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
    * ServletContext
    */
   @Autowired
   private ServletContext servletContext;
   
   /**
    * RestTemplate
    */
   @Autowired
   private RestTemplate restTemplate;
   
   /**
    *  Service DI
    */
   @Autowired
   private UserService userService;
   
   // ===================================================================================
   // Simple URL Mapping
   // ===================================================================================
   
   /**
    * 테스트 페이지
    * @param model
    * @return
    * @throws Exception
    */
   @RequestMapping(value = "/testList", method = RequestMethod.GET)
   public String testList(Model model) throws Exception {
       return "/test/testList";         
   }
   
   // ===================================================================================
   // Data 처리
   // ===================================================================================
   
   /**
    * 스케쥴 시작
    * http://javacan.tistory.com/entry/29
    * 
    * @param model
    * @param locale
    * @return
    */
   @RequestMapping(value = "/setScheduleStart", method = RequestMethod.POST, produces = "application/json")
   @ResponseBody
   public Map<String, Object> setScheduleStart(Model model,
           Locale locale) {
       Map<String, Object> resultMap = new HashMap<String, Object>();
       
       try {
           // time 세팅(일정시간마다 호출) true -> 데몬 스레드로 실행
           final Timer timer = new Timer("timer_task");
           
           TimerTask task = new TimerTask() {
               public void run() {
                   log.debug(" ############################################################################################################   ");
                   
                   try {
                       long userKey = 13L;
                       
                       User user = userService.findByUserKey(userKey);
                       log.debug("user : {}", user);
                       
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                   
                   log.debug(" ############################################################################################################   ");
               }
           };
           
           // 태스크 지연시간
           long delay = 0;
           // 폴링시간
           long period = 1000 * 15;    // 10초
           
           // 스케쥴 설정
           timer.schedule(task, delay, period);
                       
           resultMap.put("resultCd", Result.SUCCESS.getCode());
           resultMap.put("resultMsg", Result.SUCCESS.getText());
       } catch (Exception e) {
           e.printStackTrace();
           log.error("Exception : {}", e.getMessage());
           
           resultMap.put("resultCd", Result.FAIL.getCode());
           resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
       }
       
       return resultMap;
   }
   
   /**
    * 
    * @param model
    * @param userKey
    * @param locale
    * @param headers   클라이언트 헤더정보
    * @param userAgent  user-agent 
    * @param jSessionId  쿠키값 추출
    * @return
    */
   @RequestMapping(value = "/getRestDetail", method = RequestMethod.POST, produces = "application/json")
   @ResponseBody
   public Map<String, Object> getRestDetail(Model model,
           @RequestParam("userKey") long userKey,
           Locale locale,
           @RequestHeader HttpHeaders headers,
           @RequestHeader(value = "user-agent", required = true, defaultValue = "default") String userAgent,
           @CookieValue(value = "JSESSIONID", required = true) String jSessionId) {
       Map<String, Object> resultMap = new HashMap<String, Object>();
       
       try {
           
           /* RestTemplate  테스트 */
           
           // 헤더정보
           HttpEntity<String> requestEntity = new HttpEntity<String>(headers); 

           String url = "http://localhost:8080/user/getUserDetail/{UserKey}";
           ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class, String.valueOf(userKey)); 
           log.debug("getBody : {}", responseEntity.getBody());
           
           //String result = restTemplate.getForObject("http://localhost:8080/user/getUserDetail/{UserKey}", String.class, String.valueOf(userKey));
           //log.debug("result : {}", result);
           
           resultMap.put("resultCd", Result.SUCCESS.getCode());
           resultMap.put("resultMsg", Result.SUCCESS.getText());
       } catch (Exception e) {
           e.printStackTrace();
           log.error("Exception : {}", e.getMessage());
           
           resultMap.put("resultCd", Result.FAIL.getCode());
           resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
       }
       
       return resultMap;
   }
   
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
