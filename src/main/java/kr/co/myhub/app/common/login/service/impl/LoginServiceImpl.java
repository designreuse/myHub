package kr.co.myhub.app.common.login.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import kr.co.myhub.app.common.login.domain.LoginLog;
import kr.co.myhub.app.common.login.repasitory.LoginRepasitory;
import kr.co.myhub.app.common.login.service.LoginService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * 
 * file   : LoginServiceImpl.java
 * date   : 2013. 11. 28.
 * author : jmpark
 * content: 로그인 관련 서비스 구현 클래스 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 28.   kbtapjm     최초생성
 */
@Service("LoginLogService")
public class LoginServiceImpl implements LoginService {

    @Resource
    LoginRepasitory loginRepasitory;

    /**
     * 로그인 이력 등록
     * @param loginLog
     * @return
     * @throws Exception
     */
    public LoginLog create(LoginLog loginLog) throws Exception {
        return loginRepasitory.save(loginLog);
    }

    /**
     * 로그인 이력 조회(by email)
     * @param email
     * @return
     * @throws Exception
     */
    public List<LoginLog> findByEmail(String email) throws Exception {
        return loginRepasitory.findByEmailOrderByLoginDateDesc(email);
    }

    /**
     * 로그인 이력 조회(by loginLogKey)
     * @param loginLogKey
     * @return
     * @throws Exception
     */
    public LoginLog findByLoginLogKey(Long loginLogKey) throws Exception {
        return loginRepasitory.findOne(loginLogKey);
    }

    /**
     * 로그인 이력 목록(페이징)
     * @return
     * @throws Exception
     */
    public Page<LoginLog> findAllLoginLog(PageRequest pageRequest) throws Exception {
        return loginRepasitory.findAll(pageRequest);
    }
    
    /**
     * 로그인 로그 총 목록
     * @return
     * @throws Exception
     */
    public List<LoginLog> findAll() throws Exception {
        return loginRepasitory.findAll();
    }

    /**
     * 로그인 이력 총카운트
     * @return
     * @throws Exception
     */
    public Long findAllCount() throws Exception {
        return loginRepasitory.count();
    }

    /**
     * 로그인 이력 수정
     * @param loginLog
     * @return
     * @throws Exception
     */
    public LoginLog update(LoginLog loginLog) throws Exception {
        return loginRepasitory.saveAndFlush(loginLog);
    }

    /**
     * 로그인 이력 삭제
     * @param loginLog
     * @throws Exception
     */
    public void delete(LoginLog loginLog) throws Exception {
        loginRepasitory.delete(loginLog);
    }

    /**
     * 로그인 이력 전체 삭제
     * @throws Exception
     */
    public void deleteAll() throws Exception {
        loginRepasitory.deleteAll();
    }

    /**
     * 로그아웃 일자 수정
     * @param logoutDate
     * @param loginLogKey
     * @return
     */
    public int setLogoutDateFor(Date logoutDate, Long loginLogKey) {
        return loginRepasitory.setLogoutDateFor(logoutDate, loginLogKey);
    }
    
}
