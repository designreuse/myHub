package kr.co.myhub.app.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kr.co.myhub.app.common.login.repasitory.LoginRepasitory;
import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.domain.UserAuth;
import kr.co.myhub.app.user.repasitory.UserAuthRepasitory;
import kr.co.myhub.app.user.repasitory.UserRepasitory;
import kr.co.myhub.app.user.repasitory.support.UserDao;
import kr.co.myhub.app.user.service.UserService;
import kr.co.myhub.appframework.constant.UserPrivEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * file   : UserServiceImpl.java
 * date   : 2013. 11. 17.
 * author : jmpark
 * content: 유저 관련 서비스 구현체 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 17.   kbtapjm     최초생성
 */
@Service("UserService")
public class UserServiceImpl implements UserService  {
    
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Resource
    UserRepasitory userRepasitory;
    
    @Resource
    UserAuthRepasitory userAuthRepasitory;
    
    @Resource
    LoginRepasitory loginRepasitory;
    
    @Resource
    UserDao userDao;
    
    /**
     * 유저 등록
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public User insertUser(User user) throws Exception {
        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(user.getEmail());
        userAuth.setPriv(UserPrivEnum.Guests.getCode());
        
        userAuth.setUser(user);
        user.setUserAuth(userAuth);
        
        /* 유저 등록  */
        User retUser = userRepasitory.save(user);
        //userAuthRepasitory.save(userAuth);
        
        return retUser;
    }

    /**
     * E-mail로 유저 정보 조회
     * @param email
     * @return
     * @throws Exception
     */
    public User findByEmail(String email) throws Exception {
        return userRepasitory.findByEmail(email);
    }

    /**
     * 유저 정보 조회
     * @param userKey
     * @return
     * @throws Exception
     */
    public User findByUserKey(Long userKey) throws Exception {
        return userRepasitory.findByUserKey(userKey);
    }

    /**
     * 유저 목록
     * @return
     * @throws Exception
     */
    public List<User> findAllUser() throws Exception {
        return userRepasitory.findAll();
    }

    /**
     * 유저 카운트
     * @return
     * @throws Exception
     */
    public Long findAllCount() throws Exception {
        return userRepasitory.count();
    }

    /**
     * 유저 수정
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public long updateUser(User user) throws Exception {
        return userDao.updateUserByUserKey(user);
    }

    /**
     * 유저 삭제
     * 1) Spring Data JPA api 사용(유저  테이블과 관계를 형성하고 있는데 데이터 모두 삭제 처리)
     * 2) queryDsl api 사용
     * 
     * @param user 키값만 필요하지만 validation 체크를 위해서는 엔티티 객체를 사용
     * @return
     * @throws Exception
     */
    @Transactional
    public void deleteUser(User user) throws Exception {
        /* 삭제 할 유저 정보 조회  */
        User deleteUser = userRepasitory.findByUserKey(user.getUserKey());
        
        /* 유저 정보 삭제 */
        userRepasitory.delete(deleteUser);
    }
    
    /**
     * 유저 로그인 정보 수정
     * @param isLoginSuccess
     * @param email
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public long updateUserLogin(boolean isLoginSuccess, String email) throws Exception {
         return userDao.updateUserLoginByEmail(isLoginSuccess, email);
    }
    
    /**
     * 이메일 검색
     * @return
     * @throws Exception
     */
    public List<User> findByPhoneNo(String phoneNo) throws Exception {
        return userRepasitory.findByPhoneNo(phoneNo);
    }
    
    /**
     * 비밀번호 수정
     * @param password
     * @param lastPassword
     * @param email
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public long updatePasswordByEmail(String password, String lastPassword, String email) throws Exception {
        return userDao.updatePasswordByEmail(password, lastPassword, email);
    }
}
