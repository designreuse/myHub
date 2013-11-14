package kr.co.myhub.service;

import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.repasitory.UserRepasitory;
import kr.co.myhub.common.AbstractServiceTest;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends AbstractServiceTest {
    
    private static Logger logger = Logger.getLogger(UserServiceTest.class);

    @Autowired
    private UserRepasitory userRepository;
    
    private User user;
    
    @Before
    public void setup() {
        user = new User();
        user.setUserKey(0);
        user.setUserId("kbtapjm");
        user.setEmail("kbtapjm@gmail.com");
        user.setPassword("111222");
        user.setUserName("검은몽스");
        user.setBirthday("19820509");
        user.setGender("0");
        user.setPriv(1);
        
    }
    
    @Test
    public void save() {
        userRepository.save(user);
        
    }
    
    @After
    public void after() {
        logger.debug("UserServiceTest after");
    }

}
