package kr.co.myhub.app.user.service.impl;

import javax.annotation.Resource;

import kr.co.myhub.app.user.domain.UserAuth;
import kr.co.myhub.app.user.repasitory.UserAuthRepasitory;
import kr.co.myhub.app.user.service.UserAuthService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("UserAuthService")
public class UserAuthServiceImpl implements UserAuthService {

    @Resource
    UserAuthRepasitory userAuthRepasitory;

    /**
     * 유저권한 등록
     * @param userAuth
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public UserAuth create(UserAuth userAuth) throws Exception {
        return userAuthRepasitory.save(userAuth);
    }
    
}
