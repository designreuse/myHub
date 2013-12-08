package kr.co.myhub.app.login.repasitory;


import java.util.List;

import kr.co.myhub.app.common.TestConfig;
import kr.co.myhub.app.common.login.domain.LoginLog;
import kr.co.myhub.app.common.login.repasitory.LoginRepasitory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 
 * file   : TestLoginRepasitory.java
 * date   : 2013. 12. 6.
 * author : jmpark
 * content: LoginRepasitory 테스트
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 12. 6.   kbtapjm     최초생성
 */
public class TestLoginRepasitory extends TestConfig {
    
    private static final Logger log = LoggerFactory.getLogger(TestLoginRepasitory.class);
    
    @Autowired
    LoginRepasitory loginRepasitory;
    
    private LoginLog loginLog;
    
    @BeforeClass
    public static void start() {
        log.debug(" =============================================== ");
        log.debug(" Test Start ");
        log.debug(" =============================================== ");
    }
    
    @Before
    public void setup() {
        loginLog = new LoginLog();
    }
    
    @Test
    public void findAll() {
        String email = "kbtapjm@gmail.com";
        List<LoginLog> list = null;
        
        list = loginRepasitory.findByEmailOrderByLoginDateDesc(email);
        
        log.debug(" =============================================== ");
        log.debug(" list : " + list.size());
        log.debug(" =============================================== ");
        
        for (LoginLog loginLog : list) {
            log.debug("loginLog : {}", loginLog.getLoginLogKey());
        }
        
        log.debug(" =============================================== ");
        Sort sort = new Sort(Sort.Direction.ASC, "loginDate");
        list = loginRepasitory.findByEmail(email, sort);
        
        for (LoginLog loginLog : list) {
            log.debug("loginLog : {}", loginLog.getLoginLogKey());
        }
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
