package kr.co.myhub.app.user.repasitory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import kr.co.myhub.app.common.TestConfig;
import kr.co.myhub.app.user.domain.User;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestUserRepasitory extends TestConfig {
    
    private static Logger logger = Logger.getLogger(TestUserRepasitory.class);
    
    @Autowired
    private UserRepasitory userRepository;
    
    /* 유저 */
    private User user;
    
    @BeforeClass
    public static void start() {
        logger.debug(" =============================================== ");
        logger.debug(" Test Start ");
        logger.debug(" =============================================== ");
    }
    
    @Before // 해당 클래스, 객체 인스턴스 초깋
    public void setup() {
        user = new User();
        
        logger.debug("Test init");
    }
    
    /**
     * 유저 삭제
     */
    @Test
    public void delete() {
        
        // 1) 해당리스트만 삭제
        List<User> list = userRepository.findAll();
        userRepository.delete(list);
        
        // 2) 엔티티 데이터 전체 삭제
        userRepository.deleteAll();
        
        // 3) 단건 삭제
        for (User user : list) {
            if (user == null) continue;
            
            userRepository.delete(user);
        }
    }
    
    /**
     * 유저 등록
     */
    @Test
    public void save() {
        user.setUserId("kbtapjm");
        user.setEmail("kbtapjm@gmail.com");
        user.setPassword("111222");
        user.setUserName("검은몽스");
        user.setBirthday("19820509");
        user.setGender("0");
        user.setPriv(1);
        
        User retUser = userRepository.save(user) ;
        
        assertNotNull(retUser);
    }
    
    /**
     * 유저 목록, 카운트
     */
    @Test
    public void findAll() {
        // 전체 목록
        List<User> list = userRepository.findAll();
        
        // 총 카운트
        Long count = userRepository.count();
        
        assertEquals(count.intValue(), list.size());
    }
    
    /**
     * 유저 정보 
     */
    @Test
    public void findOne() {
        user.setUserId("tapjm");
        user.setEmail("tapjm@naver.com");
        user.setPassword("111222");
        user.setUserName("박재명");
        user.setBirthday("19000000");
        user.setGender("2");
        user.setPriv(2);
        
        // 등록
        User crtUser = userRepository.save(user) ;
        
        // 조회
        User retuser = userRepository.findByUserKey(crtUser.getUserKey());
    
        assertNotNull(retuser);
    }
    
    /**
     * 유저 수정
     */
    @Test
    public void update() {
        user.setUserId("tapjm5603");
        user.setEmail("tapjm@hanmail.com");
        user.setPassword("111222");
        user.setUserName("나나나");
        user.setBirthday("19000000");
        user.setGender("2");
        user.setPriv(2);
        
        // 등록
        User crtUser = userRepository.saveAndFlush(user);
        
        user.setUserName("바바바바바바바바바");
      
        // 수정
        User retuser = userRepository.saveAndFlush(crtUser) ;
            
        assertNotNull(retuser);
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
