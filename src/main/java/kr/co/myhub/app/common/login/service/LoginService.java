package kr.co.myhub.app.common.login.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import kr.co.myhub.app.common.login.domain.LoginLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 
 * file   : LoginService.java
 * date   : 2013. 11. 28.
 * author : jmpark
 * content: 로그인 처리 서비스 인터페이스
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 28.   kbtapjm     최초생성
 */
public interface LoginService {

    /**
     * 로그인 이력 등록
     * @param loginLog
     * @return
     * @throws Exception
     */
    public LoginLog create(LoginLog loginLog) throws Exception;
    
    /**
     * 로그인 이력 조회(by email)
     * @param email
     * @return
     * @throws Exception
     */
    public List<LoginLog> findByEmail(String email) throws Exception;
    
    /**
     * 로그인 이력 조회(by loginLogKey)
     * @param loginLogKey
     * @return
     * @throws Exception
     */
    public LoginLog findByLoginLogKey(Long loginLogKey) throws Exception;
    
    /**
     * 로그인 이력 목록(페이징)
     * @return
     * @throws Exception
     */
    public Page<LoginLog> findAllLoginLog(PageRequest pageRequest) throws Exception;
    
    /**
     * 로그인 로그 총 목록
     * @return
     * @throws Exception
     */
    public List<LoginLog> findAll() throws Exception;
    
    /**
     * 로그인 이력 총카운트
     * @return
     * @throws Exception
     */
    public Long findAllCount() throws Exception;
    
    /**
     * 로그인 이력 수정
     * @param loginLog
     * @return
     * @throws Exception
     */
    public LoginLog update(LoginLog loginLog) throws Exception;
    
    /**
     * 로그인 이력 삭제
     * @param loginLog
     * @throws Exception
     */
    public void delete(LoginLog loginLog) throws Exception;
    
    /**
     * 로그인 이력 전체 삭제
     * @throws Exception
     */
    public void deleteAll() throws Exception;
    
    /**
     * 로그아웃 일자 수정
     * @param logoutDate
     * @param loginLogKey
     * @return
     */
    public int setLogoutDateFor(Date logoutDate, Long loginLogKey) throws Exception;
    
    /**
     * 계정 락 여부 체크(true : locked, false : unlocked)
     * @param email
     * @return
     */
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean isAccountLocked(String email, Map<String, Object> scPolicy) throws Exception;
    
    /**
     * 계정 만료 여부 체크(-1 : expired, 0 : expiring, 1 : ok) 
     * @param email
     * @param scPolicy
     * @return
     * @throws Exception
     */
    public int isAccountExpired(String email, Map<String, Object> scPolicy) throws Exception;
}
