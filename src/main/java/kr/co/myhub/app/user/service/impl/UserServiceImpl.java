package kr.co.myhub.app.user.service.impl;

import javax.annotation.Resource;

import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.repasitory.UserRepasitory;
import kr.co.myhub.app.user.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserService")
public class UserServiceImpl implements UserService {
    
    private static Logger logger = Logger.getLogger(UserService.class);
    
    @Resource
    UserRepasitory userRepasitory;

    /**
     * 유저 등록
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public User create(User user) throws Exception {
        logger.debug("user : " + user);
        
        return userRepasitory.save(user);
    }
    
    


}
