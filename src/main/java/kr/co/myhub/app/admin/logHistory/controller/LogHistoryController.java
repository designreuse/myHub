package kr.co.myhub.app.admin.logHistory.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kr.co.myhub.app.admin.domain.dto.LogHistoryDto;
import kr.co.myhub.app.admin.domain.vo.LogHistoryVoList;
import kr.co.myhub.app.common.login.domain.LogHistory;
import kr.co.myhub.app.common.login.service.LoginService;
import kr.co.myhub.appframework.constant.Result;
import kr.co.myhub.appframework.exception.MyHubException;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * file   : LogHistoryController.java
 * date   : 2014. 7. 17.
 * author : jmpark
 * content: 로그이력 웹요청 처리 
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 7. 17.   kbtapjm     최초생성
 */
@Controller
@RequestMapping(value = "/admin/logHistory")
public class LogHistoryController {
    
    private static final Logger log = LoggerFactory.getLogger(LogHistoryController.class);
    
    /**
     * messageSource DI
     */
    @Autowired 
    MessageSourceAccessor msa;
    
    @Autowired
    LoginService loginService;
    
    /**
     * 로그이력 화면
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logHistoryList", method = RequestMethod.GET)
    public String userSearch(Model model) throws Exception {
        return "/admin/logHistory/logHistoryList";         
    }
    
    /**
     * 사용자별 로그이력 화면
     * @param model
     * @param userKey
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userLogHistoryListPopup", method = RequestMethod.GET)
    public String userLogHistoryListPopup(Model model,
            @RequestParam(value = "userKey", required = true) int userKey) throws Exception {
        
        model.addAttribute("userKey", userKey);
        
        return "/admin/logHistory/userLogHistoryListPopup";         
    }
    
    /**
     * 로그이력 목록 가져오기
     * @param model
     * @param logHistoryDto
     * @param locale
     * @return
     */
    @RequestMapping(value = "/getLogHistoryList", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getLogHistoryList(Model model,
            @ModelAttribute LogHistoryDto logHistoryDto,
            Locale locale) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            // 로그 목록
            Page<LogHistory> logHistoryList = loginService.findAllLogHistory(logHistoryDto);
            
            // 로그 카운트
            long count = loginService.findAllLogHistoryCount(logHistoryDto);
            
            // 결과 세팅
            LogHistoryVoList LogHistoryVoList = new LogHistoryVoList();
            LogHistoryVoList.setPage(logHistoryDto.getPage());
            LogHistoryVoList.setRecords((int) count);
            LogHistoryVoList.setTotal(logHistoryList.getTotalPages());
            LogHistoryVoList.setList(logHistoryList.getContent());
            
            resultMap.put("resultCd", Result.SUCCESS.getCode());
            resultMap.put("resultMsg", Result.SUCCESS.getText());
            resultMap.put("resultData", LogHistoryVoList);
            
        } catch(Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
        
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
        }
        
        return resultMap;
    }
    
    /**
     * 로그 이력 삭제
     * @param model
     * @param LogHistoryList
     * @param locale
     * @return
     */
    @RequestMapping(value = "/logHistoryDelete", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> logHistoryDelete(Model model,
            @RequestBody List<LogHistory> LogHistoryList,
            Locale locale) throws Exception {
        
        if (log.isDebugEnabled()) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonStr = mapper.writeValueAsString(LogHistoryList);
            log.debug("jsonStr : {}", jsonStr);
        }
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            /* 로그이력 삭제*/
            loginService.deleteListLogHistory(LogHistoryList);
            
            resultMap.put("resultCd", Result.SUCCESS.getCode());
            resultMap.put("resultMsg", Result.SUCCESS.getText());
        } catch(Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
        
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
        }
        
        return resultMap;
    }
    
    /**
     * 사용자의 로그이력 전체삭제
     * @param model
     * @param userKey
     * @param locale
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logHistoryDeleteAll", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> logHistoryDeleteAll(Model model,
            @RequestParam(value = "userKey", required = true) Long userKey,
            Locale locale) throws Exception {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            loginService.deleteLogHistoryByUserkey(userKey);
            
            resultMap.put("resultCd", Result.SUCCESS.getCode());
            resultMap.put("resultMsg", Result.SUCCESS.getText());
        } catch(Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
        
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
        }
        
        return resultMap;
    }
    
    /* ============================================================================================  */
    /* temp  */
    /* ============================================================================================  */
    
    @RequestMapping(value = "/getDefaultData", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getDefaultData(Model model,
            @ModelAttribute LogHistoryDto logHistoryDto,
            Locale locale) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        try {
            /* 샘플 */
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
            
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("useYn", "Y");
            dataMap.put("xPos", "462");
            dataMap.put("yPos", "107");
            dataMap.put("fontText", "돋움체");
            dataMap.put("fontSize", "40");
            dataMap.put("fontType", "Y");
            dataMap.put("under", "N");
            
            listMap.add(dataMap);
            
            dataMap = new HashMap<String, Object>();
            dataMap.put("useYn", "N");
            dataMap.put("xPos", "462");
            dataMap.put("yPos", "107");
            dataMap.put("fontText", "바탕체");
            dataMap.put("fontSize", "40");
            dataMap.put("fontType", "Y");
            dataMap.put("under", "N");
            
            listMap.add(dataMap);
            
            dataMap = new HashMap<String, Object>();
            dataMap.put("useYn", "Y");
            dataMap.put("xPos", "462");
            dataMap.put("yPos", "107");
            dataMap.put("fontText", "바탕체");
            dataMap.put("fontSize", "20");
            dataMap.put("fontType", "N");
            dataMap.put("under", "N");
            
            listMap.add(dataMap);
            
            resultMap.put("resultCd", Result.SUCCESS.getCode());
            resultMap.put("resultMsg", Result.SUCCESS.getText());
            resultMap.put("resultData", listMap);
            
        } catch(Exception e) {
            e.printStackTrace();
            log.error("Exception : {}", e.getMessage());
        
            resultMap.put("resultCd", Result.FAIL.getCode());
            resultMap.put("resultMsg", MyHubException.getExceptionMsg(e, msa, locale));
        }
        
        return resultMap;
    }


}
