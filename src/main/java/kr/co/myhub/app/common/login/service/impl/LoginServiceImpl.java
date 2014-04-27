package kr.co.myhub.app.common.login.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.myhub.app.common.login.domain.LoginLog;
import kr.co.myhub.app.common.login.repasitory.LoginRepasitory;
import kr.co.myhub.app.common.login.service.LoginService;
import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.repasitory.UserRepasitory;
import kr.co.myhub.appframework.constant.SecurityPoliciesEnum;
import kr.co.myhub.appframework.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    /**
     * 로그인 이력 등록
     * @param loginLog
     * @return
     * @throws Exception
     */
    public LoginLog create(LoginLog loginLog) throws Exception {
        return loginRepasitory.save(loginLog);
    }

    /**
     * 로그인 이력 조회(by email)
     * @param email
     * @return
     * @throws Exception
     */
    public List<LoginLog> findByEmail(String email) throws Exception {
        return loginRepasitory.findByEmailOrderByLoginDateDesc(email);
    }

    /**
     * 로그인 이력 조회(by loginLogKey)
     * @param loginLogKey
     * @return
     * @throws Exception
     */
    public LoginLog findByLoginLogKey(Long loginLogKey) throws Exception {
        return loginRepasitory.findOne(loginLogKey);
    }

    /**
     * 로그인 이력 목록(페이징)
     * @return
     * @throws Exception
     */
    public Page<LoginLog> findAllLoginLog(PageRequest pageRequest) throws Exception {
        return loginRepasitory.findAll(pageRequest);
    }
    
    /**
     * 로그인 로그 총 목록
     * @return
     * @throws Exception
     */
    public List<LoginLog> findAll() throws Exception {
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
    public LoginLog update(LoginLog loginLog) throws Exception {
        return loginRepasitory.saveAndFlush(loginLog);
    }

    /**
     * 로그인 이력 삭제
     * @param loginLog
     * @throws Exception
     */
    public void delete(LoginLog loginLog) throws Exception {
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
     * 로그아웃 일자 수정
     * @param logoutDate
     * @param loginLogKey
     * @return
     */
    public int setLogoutDateFor(Date logoutDate, Long loginLogKey) throws Exception {
        return loginRepasitory.setLogoutDateFor(logoutDate, loginLogKey);
    }

    /**
     * 계정 락 여부 체크(true: locked, false: unlocked)
     * @param loginId
     * @return
     */
    public boolean isAccountLocked(String email, Map<String, Object> scPolicy) throws Exception {
        log.debug("isAccountLocked email : {}", email);
        
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
            throw new Exception("User Not Found");
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
    
}
