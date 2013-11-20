package kr.co.myhub.app.user.service;

import static org.junit.Assert.assertNotNull;
import kr.co.myhub.app.common.TestConfig;
import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.domain.validator.UserValidator;
import kr.co.myhub.appframework.util.EncryptionUtil;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
    
    private static Logger logger = Logger.getLogger(TestUserService.class);

    @Autowired
    UserService userService;
    
    /* 유저 */
    private User user;
    
    @BeforeClass
    public static void start() {
        System.out.println(" =============================================== ");
        System.out.println(" Test Start ");
        System.out.println(" =============================================== ");
    }
    
    @Before // 해당 클래스, 객체 인스턴스 초기화
    public void setup() {
        user = new User();
        
        System.out.println("Test init");
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
        user.setPriv(1);
                
        User retUser = userService.create(user);
        
        assertNotNull(retUser);
    }
    
    @After
    public void after() {
        
    }
    
    @AfterClass
    public static void end() {
        logger.debug(" =============================================== ");
        logger.debug(" Test End ");
        logger.debug(" =============================================== ");
    }
    
}
