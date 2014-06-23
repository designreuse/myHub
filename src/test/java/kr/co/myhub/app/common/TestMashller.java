package kr.co.myhub.app.common;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.repasitory.UserRepasitory;
import kr.co.myhub.appframework.constant.Result;
import kr.co.myhub.appframework.vo.ApiResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * file   : TestMashller.java
 * date   : 2013. 11. 27.
 * author : jmpark
 * content: XML마샬링 테스트
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 27.   kbtapjm     최초생성
 */
public class TestMashller extends TestConfig {
    
    @Autowired
    private UserRepasitory userRepository;
    
    /* 유저 */
    private User user1;
    private User user2;
    
    @Before
    public void setup() {
        
    }
    
    @Test
    public void test() throws JAXBException {
        
        /*List<User> list = new ArrayList<User>();
        
        user1 = new User();
        user1.setUserId("kbtapjm");
        
        user2 = new User();
        user2.setUserId("tapjm");
        
        list.add(user1);
        list.add(user2);*/
        
        List<User> list = userRepository.findAll();
        
        ApiResponse response = new ApiResponse();
        response.setMessage("list");
        response.setResult(Result.SUCCESS);
        response.setList(list);
        
        JAXBContext jaxbContext = JAXBContext.newInstance(ApiResponse.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
     
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(response, System.out);
    }
    
    @After
    public void after() {
        
    }

}
