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
import org.springframework.data.domain.Sort;

/**
 * * 새로 만들기 Ctrl+N
* Quick Access Ctrl+3
* 코드 자동완성 Ctrl+Space
* import 정리 Ctrl+Shift+O
* 빨리 수정 Ctrl+1
* 한 줄 삭제 Ctrl+D
* 코드 정렬 Ctrl+Shift+F


* 선언한 곳으로 F3 또는 Ctrl+클릭
* 이전 위치로 Alt+←
* 파일 아웃라인 Ctrl+O
* 상속 구조 Ctrl+T
* 선택 문자 찾기 Ctrl+K
* 행번호로 이동 Ctrl+L
* 파일명으로 찾기 Ctrl+Shift+R
* 프로젝트 텍스트 검색 Ctrl+H

* 실행 Ctrl+F11
* 단축키 목록 Ctrl+Shift+L
* 단축키 설정 Ctrl+Shift+L 두 번

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
