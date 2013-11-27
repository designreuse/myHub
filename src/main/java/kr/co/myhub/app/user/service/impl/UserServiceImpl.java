package kr.co.myhub.app.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.repasitory.UserRepasitory;
import kr.co.myhub.app.user.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
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
public class UserServiceImpl implements UserService {
    
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
        return userRepasitory.save(user);
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
     * 유저 수정
     * @param user
     * @return
     * @throws Exception
     */
    public User update(User user) throws Exception {
        return userRepasitory.saveAndFlush(user);
    }

    /**
     * 유저 삭제
     * @param user
     * @return
     * @throws Exception
     */
    public void delete(User user) throws Exception {
        userRepasitory.delete(user);
    }
}
