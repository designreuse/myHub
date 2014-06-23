package kr.co.myhub.app.login.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.InetAddress;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import kr.co.myhub.app.common.TestConfig;
import kr.co.myhub.app.common.login.domain.LogHistory;
import kr.co.myhub.app.common.login.service.LoginService;
import kr.co.myhub.app.login.repasitory.TestLoginRepasitory;
import kr.co.myhub.app.user.domain.User;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 
 * file   : TestLoginService.java
 * date   : 2013. 11. 29.
 * author : jmpark
 * content: Login Service 테스트 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 29.   kbtapjm     최초생성
 */
public class TestLoginService extends TestConfig {
    
    private static final Logger log = LoggerFactory.getLogger(TestLoginRepasitory.class);
    
    @Autowired
    LoginService loginService;
    
    private LogHistory loginLog;
    
    @BeforeClass
    public static void start() {
        log.debug(" =============================================== ");
        log.debug(" Test Start ");
        log.debug(" =============================================== ");
    }
    
    @Before // 해당 클래스, 객체 인스턴스 초기화
    public void setup() {
        loginLog = new LogHistory();
    }
    
    /**
     * 로그인 로그 등록
     * @throws Exception 
     */
    @Test
    public void create() throws Exception {
        loginLog.setEmail("tapjm@naver.com");
        loginLog.setIpAddress(InetAddress.getLocalHost().getHostAddress());
        loginLog.setLogDate(new Date());
        
        // 유저정보
        User user = new User();
        user.setUserKey((long) 5);
        
        loginLog.setUser(user);
        
        LogHistory result = loginService.create(loginLog);
        
        assertNotNull(result);
    }
    
    /**
     * 로그인 로그 조회
     * @throws Exception
     */
    @Test
    public void findOne() throws Exception {
        Long loginLogKey = (long) 11;
        
        LogHistory result = loginService.findByLoginLogKey(loginLogKey);
        
        assertNotNull(result);
    }
    
    /**
     * 로그인 목록 조회
     * @throws Exception
     */
    @Test
    public void findAll() throws Exception {
        List<LogHistory> list = null;
        
        // email로 조회
        list  = loginService.findByEmail("kbtapjm@gmail.com");
        log.debug(" ===========> findByEmail : " + list.size());
 
        assertNotNull(list);
        
        int page = 1;   // 페이지
        int size = 10;  // 목록 카운트
        
        PageRequest pageRequest = new PageRequest(page - 1, size, Sort.Direction.DESC, "loginDate");
        
        // 목록(paging, sort)
        Page<LogHistory> pageInfo = loginService.findAllLoginLog(pageRequest);
        
        int current = pageInfo.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, pageInfo.getTotalPages());
        
        log.debug(" ======================================================================== ");
        log.debug("size : {}", pageInfo.getSize());
        log.debug("current : {}", current);
        log.debug("begin : {}", begin);
        log.debug("end : {}", end);
        log.debug("sort : {}", pageInfo.getSort());
        
        Iterator<LogHistory> iter = pageInfo.iterator();
        while(iter.hasNext()) {
            LogHistory vo = iter.next();
            
            log.debug("loginKey : " + vo.getLogHistoryKey());
            
            // many to one User
            log.debug("getEmail : " + vo.getUser().getEmail());
        }
        log.debug(" ======================================================================== ");
        
        // 전체 목록
        list = loginService.findAll();
        
        // 전체 카운트
        Long count = loginService.findAllCount();
        
        assertEquals(count.intValue(), list.size());
    }
    
    /**
     * 로그인 로그 수정
     * @throws Exception
     */
    @Test
    public void update() throws Exception {
        /*loginLog.setLoginLogKey((long) 1);
        loginLog.setLogoutDate(new Date());  
        
        LoginLog result = loginService.update(loginLog);
        
        assertNotNull(result);*/
        
        //int ret = loginService.setLogoutDateFor(new Date(), (long) 11);
        log.debug(" ===========> update : " + 0);
        
        assertEquals(1, 1);
    }
    
    /**
     * 로그인 로그 삭제
     * @throws Exception
     */
    @Test
    @Ignore
    public void delete() throws Exception {
        loginLog.setLogHistoryKey((long) 11);
        
        loginService.delete(loginLog);
    }
    
    @After
    public void after() {
        
    }
    
    @AfterClass
    public static void end() {
        log.debug(" =============================================== ");
        log.debug(" Test End ");
        log.debug(" =============================================== ");
    }
}
