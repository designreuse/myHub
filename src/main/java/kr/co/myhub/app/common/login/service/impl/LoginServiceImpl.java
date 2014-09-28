package kr.co.myhub.app.common.login.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.myhub.app.admin.domain.dto.LogHistoryDto;
import kr.co.myhub.app.common.login.domain.LogHistory;
import kr.co.myhub.app.common.login.domain.QLogHistory;
import kr.co.myhub.app.common.login.repasitory.LoginRepasitory;
import kr.co.myhub.app.common.login.repasitory.support.LogHistoryDao;
import kr.co.myhub.app.common.login.service.LoginService;
import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.domain.UserAuth;
import kr.co.myhub.app.user.repasitory.UserAuthRepasitory;
import kr.co.myhub.app.user.repasitory.UserRepasitory;
import kr.co.myhub.appframework.constant.AccountExpiredEnum;
import kr.co.myhub.appframework.constant.SecurityPoliciesEnum;
import kr.co.myhub.appframework.constant.UseEnum;
import kr.co.myhub.appframework.constant.UserPrivEnum;
import kr.co.myhub.appframework.util.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.Predicate;

/**
 * 
 * file   : LoginServiceImpl.java
 * date   : 2013. 11. 28.
 * author : jmpark
 * content: 로그인 관련 서비스 구현 클래스 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 28.   kbtapjm     최초생성
 */
@Service("LoginLogService")
public class LoginServiceImpl implements LoginService {
    
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Resource
    LoginRepasitory loginRepasitory;
    
    @Resource
    UserRepasitory userRepasitory;
    
    @Resource
    UserAuthRepasitory userAuthRepasitory;
    
    @Resource
    LogHistoryDao logHistoryDao;

    /**
     * 로그인 이력 등록
     * @param loginLog
     * @return
     * @throws Exception
     */
    public LogHistory create(LogHistory loginLog) throws Exception {
        return loginRepasitory.save(loginLog);
    }

    /**
     * 로그인 이력 조회(by email)
     * @param email
     * @return
     * @throws Exception
     */
    @Cacheable(value = "listCache", key = "#email")
    public List<LogHistory> findByEmail(String email) throws Exception {
        return loginRepasitory.findByEmailOrderByLogDateDesc(email);
    }

    /**
     * 로그인 이력 조회(by loginLogKey)
     * @param loginLogKey
     * @return
     * @throws Exception
     */
    @Cacheable(value = "listCache", key = "#loginLogKey")
    public LogHistory findByLoginLogKey(Long loginLogKey) throws Exception {
        return loginRepasitory.findOne(loginLogKey);
    }

    /**
     * 로그인 이력 목록(페이징)
     * @return
     * @throws Exception
     */
    public Page<LogHistory> findAllLoginLog(PageRequest pageRequest) throws Exception {
        return loginRepasitory.findAll(pageRequest);
    }
    
    /**
     * 로그인 로그 총 목록
     * @return
     * @throws Exception
     */
    public List<LogHistory> findAll() throws Exception {
        return loginRepasitory.findAll();
    }

    /**
     * 로그인 이력 총카운트
     * @return
     * @throws Exception
     */
    public Long findAllCount() throws Exception {
        return loginRepasitory.count();
    }

    /**
     * 로그인 이력 수정
     * @param loginLog
     * @return
     * @throws Exception
     */
    public LogHistory update(LogHistory loginLog) throws Exception {
        return loginRepasitory.saveAndFlush(loginLog);
    }

    /**
     * 로그인 이력 삭제
     * @param loginLog
     * @throws Exception
     */
    public void delete(LogHistory loginLog) throws Exception {
        loginRepasitory.delete(loginLog);
    }

    /**
     * 로그인 이력 전체 삭제
     * @throws Exception
     */
    public void deleteAll() throws Exception {
        loginRepasitory.deleteAll();
    }

    /**
     * 계정 락 여부 체크(true: locked, false: unlocked)
     * @param loginId
     * @return
     */
    public boolean isAccountLocked(String email, Map<String, Object> scPolicy) throws Exception {
        int blockUser = 0;          // 블락 유저
        int limitFailCount = 0;     // 계정 잠금 임계 값 
        int limitBlockTime = 0;     // 계정 잠금 기간 값
        int timeGapMin = 0;         // 시간차이
        
        /* 로그인 정책 객체에 닫기 */
        for (SecurityPoliciesEnum sp : SecurityPoliciesEnum.values()) {
            scPolicy.put(sp.getText(), sp.getValue());
        }
        
        /* 보안정책을 DB로 관리하는 경우에는 select해서 기본적으로 enum에 설정되어있는 정책과 합친다. */
        
        blockUser = Integer.parseInt(scPolicy.get(SecurityPoliciesEnum.AccountLockout.getText()).toString());
        limitFailCount = Integer.parseInt(scPolicy.get(SecurityPoliciesEnum.AccountLockoutThresholdValue.getText()).toString());
        limitBlockTime = Integer.parseInt(scPolicy.get(SecurityPoliciesEnum.AccountLockoutDurationValue.getText()).toString());
        
        log.debug("[Lock Options] blockUser : {}", blockUser);
        log.debug("[Lock Options] limitFailCount : {}", limitFailCount);
        log.debug("[Lock Options] limitBlockTime : {}", limitBlockTime);
        
        /* 락체크 */
        //if (blockUser == 0) return false;
        
        /* 유저 조회 (DB) */
        User user = userRepasitory.findByEmail(email);
        if (user == null) {
            throw new Exception("사용자 정보가 존재하지 않습니다.");
        }
        
        // 로그인 실패일자가 없는 경우
        if (user.getLoginFailDt() == null) {
            return false;
        }
        
        /* 마지막 로그인 실패시간과 현재시간과의 차이 계산 */
        Timestamp currentTime = DateUtil.getCurrentDateTimeStamp();
        Date loginFailDt = user.getLoginFailDt();
        Timestamp lastTime = new Timestamp(loginFailDt.getTime());
        
        if (currentTime != null && lastTime != null) {
            timeGapMin = (int)(currentTime.getTime() - lastTime.getTime()) / (60 * 1000);
        }
        
        log.debug("[TIME] currentTime : {}", currentTime);
        log.debug("[TIME] lastTime : {}", lastTime);
        log.debug("[TIME] timeGapMin : {}", timeGapMin);
        
        /* Lock인지 판단 */
        int failedCount = user.getLoginFailCount();
        log.debug("failedCount : {}", failedCount);
        
        log.debug("1 : {}", failedCount != 0);
        log.debug("2 : {}", limitFailCount <= failedCount);
        log.debug("3 : {}", limitBlockTime > timeGapMin);
        
        if(failedCount != 0 && limitFailCount <= failedCount && limitBlockTime > timeGapMin) {
            log.info("Account locked out");
            return true;
        }
       
        return false;
    }
    
    /**
     * 계정 만료 여부 체크(-1 : expired, 0 : expiring, 1 : ok) 
     * @param email
     * @param scPolicy
     * @return
     * @throws Exception
     */
    public int isAccountExpired(String email, Map<String, Object> scPolicy) throws Exception {
        long timeGapDay = 0;    // 일자차이
        long expiryDay  = 0;    // 만료일자
        long remainedDays = 0;  // 남은기간
        
        int maximumPasswordAgeAdmin;
        int maximumPasswordAgeRegular;
        int maximumPasswordAgeValueAdmin;
        int maximumPasswordAgeValueRegular;
        int passwordExpiryWarning;
        int passwordExpiryWarningValue;
        
        /* 로그인 정책 객체에 닫기 */
        for (SecurityPoliciesEnum sp : SecurityPoliciesEnum.values()) {
            scPolicy.put(sp.getText(), sp.getValue());
        }
        
        /* 보안정책을 DB로 관리하는 경우에는 select해서 기본적으로 enum에 설정되어있는 정책과 합친다. */
        
        maximumPasswordAgeAdmin = Integer.parseInt(scPolicy.get(SecurityPoliciesEnum.MaximumPasswordAgeAdmin.getText()).toString());
        maximumPasswordAgeRegular = Integer.parseInt(scPolicy.get(SecurityPoliciesEnum.MaximumPasswordAgeRegular.getText()).toString());
        maximumPasswordAgeValueAdmin = Integer.parseInt(scPolicy.get(SecurityPoliciesEnum.MaximumPasswordAgeValueAdmin.getText()).toString());
        maximumPasswordAgeValueRegular = Integer.parseInt(scPolicy.get(SecurityPoliciesEnum.MaximumPasswordAgeValueRegular.getText()).toString());
        passwordExpiryWarning = Integer.parseInt(scPolicy.get(SecurityPoliciesEnum.PasswordExpiryWarning.getText()).toString());
        passwordExpiryWarningValue = Integer.parseInt(scPolicy.get(SecurityPoliciesEnum.PasswordExpiryWarningValue.getText()).toString());
        
        /* 유저 조회 (DB) */
        User user = userRepasitory.findByEmail(email);
        if (user == null) {
            throw new Exception("User Not Found");
        }
        
        /* 유저  권한정보 조회 (DB) */
        UserAuth userAuth = userAuthRepasitory.findByEmail(email);
        if (userAuth == null) {
            throw new Exception("User Auth Not Found");
        }
        
        /* 암호만료 권한 적용 여부 체크 */
        if (userAuth.getPriv() == UserPrivEnum.SuperUser.getCode()) {
            if (maximumPasswordAgeAdmin == UseEnum.Use.getCode()) {
                expiryDay = maximumPasswordAgeValueAdmin;
            } else {
                return AccountExpiredEnum.ok.getValue();
            }
        } else {
            if (maximumPasswordAgeRegular == UseEnum.Use.getCode()) {
                expiryDay = maximumPasswordAgeValueRegular;
            } else {
                return AccountExpiredEnum.ok.getValue();
            }
        }
        
        /* 마지막 비밀번호 변경일과 현재시간과의 차이 계산 */
        Timestamp currentTime = DateUtil.getCurrentDateTimeStamp();     // 현재시간
        Date passwordModDt = user.getPasswordModDt();
        Timestamp lastTime = new Timestamp(passwordModDt.getTime());    // 마지막 패스워드 변경일
        
        if (currentTime != null && lastTime != null) {
            timeGapDay = (currentTime.getTime() - lastTime.getTime()) / (24 * 60 * 60 * 1000);
            remainedDays = expiryDay - timeGapDay;
        } else {
            throw new Exception("The time value is null");
        }
        
        log.info("expiryDay : " + expiryDay + " Till now : " + timeGapDay + ", Remained till the time : " + remainedDays);
        log.info("passwordExpiryWarning : " + passwordExpiryWarning + " passExpiryValue : " + (remainedDays - passwordExpiryWarningValue));
        
        /* 계정만료 여부 체크 */
        if (remainedDays < 0) {
            return AccountExpiredEnum.expired.getValue();
        } else if (remainedDays >= 0 && passwordExpiryWarning != 0 && remainedDays - passwordExpiryWarningValue <= 0) {
            GregorianCalendar cal=new GregorianCalendar(); 
            cal.setTime(lastTime);
            cal.add(GregorianCalendar.DATE, Long.valueOf(expiryDay).intValue());
            Date date = cal.getTime();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd"); 
            String expiryDate = fmt.format(date);
            scPolicy.put("ExpiryDate", expiryDate);
            
            log.info("--- exiring ---" + String.format("%s", scPolicy.get("ExpiryDate")));
            
            return AccountExpiredEnum.expiring.getValue();
        }
        
        return AccountExpiredEnum.ok.getValue();
    }
    
    /**
     * 로그이력 목록
     * @param logHistoryDto
     * @return
     * @throws Exception
     */
    @Cacheable(value = "listCache", key = "#logHistoryDto")
    public Page<LogHistory> findAllLogHistory(LogHistoryDto logHistoryDto) throws Exception {
        Predicate predicate = null;
        QLogHistory qLogHistory = QLogHistory.logHistory;
        
        // 페이지 설정 초기화
        logHistoryDto.setPageInit();
        
        // 검색어 세팅
        if (!StringUtils.isEmpty(logHistoryDto.getSearchWord())) {
            String searchWord = logHistoryDto.getSearchWord();
            
            switch(logHistoryDto.getSearchType()) {
            case "email":
                if (!StringUtils.isEmpty(logHistoryDto.getLogType())) {
                    predicate = qLogHistory.email.eq(searchWord).and(qLogHistory.logType.eq(logHistoryDto.getLogType()));   
                } else {
                    predicate = qLogHistory.email.eq(searchWord);    
                }
                break;
            case "userKey":
                if (!StringUtils.isEmpty(logHistoryDto.getLogType())) {
                    predicate = qLogHistory.user.userKey.eq(Long.valueOf(searchWord)).and(qLogHistory.logType.eq(logHistoryDto.getLogType()));   
                } else {
                    predicate = qLogHistory.user.userKey.eq(Long.valueOf(searchWord));
                }
                break;
            }
        } else {
            if (!StringUtils.isEmpty(logHistoryDto.getLogType())) {
                predicate = qLogHistory.logType.eq(logHistoryDto.getLogType());
            }
        }
        
        /* 페이지  정보 */
        Direction Direction = logHistoryDto.getSortType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = new Sort(Direction, logHistoryDto.getSortName());
        
        PageRequest pageRequest = new PageRequest(logHistoryDto.getPage() - 1, logHistoryDto.getRows(), sort);
        
        return loginRepasitory.findAll(predicate, pageRequest);
    }
    
    /**
     * 로그이력 카운트
     * @param logHistoryDto
     * @return
     * @throws Exception
     */
    //@Cacheable(value = "listCountCache", key = "#logHistoryDto.logType.concat('-').concat(#logHistoryDto.searchType).concat('-').concat(#logHistoryDto.searchWord)")
    public Long findAllLogHistoryCount(LogHistoryDto logHistoryDto) throws Exception {
        Predicate predicate = null;
        QLogHistory qLogHistory = QLogHistory.logHistory;
        
        // 검색어 세팅
        if (!StringUtils.isEmpty(logHistoryDto.getSearchWord())) {
            String searchWord = logHistoryDto.getSearchWord();
            
            switch(logHistoryDto.getSearchType()) {
            case "email":
                if (!StringUtils.isEmpty(logHistoryDto.getLogType())) {
                    predicate = qLogHistory.email.eq(searchWord).and(qLogHistory.logType.eq(logHistoryDto.getLogType()));   
                } else {
                    predicate = qLogHistory.email.eq(searchWord);    
                }
                break;
            case "userKey":
                if (!StringUtils.isEmpty(logHistoryDto.getLogType())) {
                    predicate = qLogHistory.user.userKey.eq(Long.valueOf(searchWord)).and(qLogHistory.logType.eq(logHistoryDto.getLogType()));   
                } else {
                    predicate = qLogHistory.user.userKey.eq(Long.valueOf(searchWord));
                }
                break;
            }
        } else {
            if (!StringUtils.isEmpty(logHistoryDto.getLogType())) {
                predicate = qLogHistory.logType.eq(logHistoryDto.getLogType());
            }
        }
        
        return loginRepasitory.count(predicate);
    }
    
    /**
     * 로그 이력 삭제
     * @param LogHistoryList
     * @throws Exception
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void deleteListLogHistory(List<LogHistory> LogHistoryList) throws Exception {
        for (LogHistory logHistory : LogHistoryList) {
            if (logHistory == null) continue; 
            
            loginRepasitory.delete(logHistory);
        }
    }
    
    /**
     * 사용자의 로그전체 삭제
     * @param userKey
     * @throws Exception
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void deleteLogHistoryByUserkey(Long userKey) throws Exception {
        logHistoryDao.deleteLogHistoryByUserkey(userKey);
    }
    
    /**
     * 유저 정보 조회
     * @param email
     * @return
     * @throws Exception
     */
    public LogHistory selectLogHistoryByEmailAndLogType(String email, String logType) throws Exception {
        return logHistoryDao.selectLogHistoryByEmailAndLogType(email, logType);
    }
    
}
