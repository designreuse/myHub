package kr.co.myhub.app.user.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import kr.co.myhub.app.common.TestConfig;
import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.appframework.util.EncryptionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * file   : TestUserService.java
 * date   : 2013. 11. 19.
 * author : jmpark
 * content: Service Test 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 19.   kbtapjm     최초생성
 */
public class TestUserService extends TestConfig {
    
    private static final Logger log = LoggerFactory.getLogger(TestUserService.class);

    @Autowired
    UserService userService;
    
    /* 유저 */
    private User user;
    
    @BeforeClass
    public static void start() {
        log.debug(" =============================================== ");
        log.debug(" Test Start ");
        log.debug(" =============================================== ");
    }
    
    @Before // 해당 클래스, 객체 인스턴스 초기화
    public void setup() {
        user = new User();
        
        log.debug("Test init");
    }
    
    /**
     * 유저 등록
     * @throws Exception 
     */
    @Test
    public void create() throws Exception {
        user.setUserId("kbtapjm");
        user.setEmail("kbtapjm@gmail.com");
        user.setPassword(EncryptionUtil.getEncryptPassword("111222"));
        user.setUserName("검은몽스");
        user.setBirthday("19820509");
        user.setGender("0");
                
        User retUser = userService.insertUser(user);
        
        assertNotNull(retUser);
    }
    
    /**
     * 유저 정보 조회
     * @throws Exception
     */
    @Test
    public void findOne() throws Exception {
        User retUser = userService.findByUserKey((long) 1);
        
        assertNotNull(retUser);
    }
    
    /**
     * 유저 목록 카운트
     * @throws Exception
     */
    @Test
    public void findAll() throws Exception {
        // 전체 목록
        List<User> list = userService.findAllUser();        
        // 총 카운트
        Long count = userService.findAllCount();
        
        log.debug(" =============================================== ");
        log.debug("findAll : {}", count);
        log.debug(" =============================================== ");
        
        assertEquals(count.intValue(), list.size()); 
    }
    
    /**
     * 유저 수정
     * @throws Exception
     */
    @Test
    public void update() throws Exception {
        user.setUserKey((long) 2);
        user.setUserId("tapjm");
        user.setEmail("tapjm@gmail.com");
        user.setPassword(EncryptionUtil.getEncryptPassword("111222"));
        user.setUserName("수정 테스트");
        user.setBirthday("19820509");
        user.setGender("0");
                
        User retUser = userService.insertUser(user);
        
        assertNotNull(retUser);
    }
    
    /**
     * 유저 삭제
     * @throws Exception
     */
    @Test
    public void delete() throws Exception {
        user.setUserKey((long) 1);
        
        userService.delete(user);
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
