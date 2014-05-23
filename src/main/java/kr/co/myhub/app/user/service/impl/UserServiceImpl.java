package kr.co.myhub.app.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.domain.UserAuth;
import kr.co.myhub.app.user.repasitory.UserAuthRepasitory;
import kr.co.myhub.app.user.repasitory.UserRepasitory;
import kr.co.myhub.app.user.service.UserService;
import kr.co.myhub.appframework.constant.UserPrivEnum;
import kr.co.myhub.appframework.util.CommonUtil;
import kr.co.myhub.appframework.util.EncryptionUtil;
import kr.co.myhub.appframework.util.MailUtil;

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

    /**
     * 유저 등록
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true, propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
    public User insertUser(User user) throws Exception {
        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(user.getEmail());
        
        // 권한 설정
        if (user.getUserId().equals("admin")) {
            userAuth.setPriv(UserPrivEnum.SuperUser.getCode());
        } else {
            userAuth.setPriv(UserPrivEnum.Operators.getCode());
        }
        userAuth.setUser(user);
         
        /* 유저 등록  */
        User retUser = userRepasitory.save(user);
        
        /* 유저 권한 등록 */
        userAuthRepasitory.save(userAuth);
        
        /* 회원가입 이메일 전송 */
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("to", user.getEmail());
        params.put("subject", "회원가입을 축하드립니다.");
        params.put("content", "회원가입을 축하드립니다.");
        
        MailUtil.mailsend(params);
        
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

    /**
     * 로그인 결과 수정
     * @param loginFailCount
     * @param loginFailDt
     * @param userId
     * @return
     */
    @Transactional(readOnly = true, propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int updateUserFailLogin(Date loginFailDt, String email) throws Exception {
        return userRepasitory.updateUserFailLogin(loginFailDt, email);
    }
    
    /**
     * 로그인성공시 수정
     * @param email
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int updateUserSuccessLogin(Date loginFailDt, String email) throws Exception {
        return userRepasitory.updateUserSuccessLogin(loginFailDt, email);
    }
    
    /**
     * 비밀번호 검색
     * @param email
     * @throws Exception
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void passwordSearch(String email) throws Exception {
        
        /* 유저정보 조회 */
        User user = userRepasitory.findByEmail(email);
        if (user == null) {
            throw new Exception("유저정보가 존재 하지 않습니다.");
        }
        
        /* 임시비밀번호 생성 */
        String tmpPassword = CommonUtil.getTmpPassword();
        
        /* 유저 비밀번호 수정 */
        String password = EncryptionUtil.getEncryptPassword(tmpPassword);
        int ret = userRepasitory.updatePassword(password, password, email);
        if (ret == 0) {
            throw new Exception("임시 비밀번호 수정이 실패하였습니다.");
        }
        
        // TODO: queryDSl로 변경 필요 (http://whiteship.me/?p=13230)
        
        /* email 전송 */
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("to", email);
        params.put("subject", "임시 비밀번호 안내입니다.");
        params.put("content", "고객님의 임시 비밀번호는  [".concat(tmpPassword).concat("] 입니다."));
        
        boolean result = MailUtil.mailsend(params);
        if (!result) {
            throw new Exception("임시 비밀번호 안내 메일 발송이 실패하였습니다. ");
        }
    }
    
}
