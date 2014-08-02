package kr.co.myhub.app.common.login.service;

import java.util.List;
import java.util.Map;

import kr.co.myhub.app.admin.domain.dto.LogHistoryDto;
import kr.co.myhub.app.common.login.domain.LogHistory;

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
    public LogHistory create(LogHistory loginLog) throws Exception;
    
    /**
     * 로그인 이력 조회(by email)
     * @param email
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<LogHistory> findByEmail(String email) throws Exception;
    
    /**
     * 로그인 이력 조회(by loginLogKey)
     * @param loginLogKey
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public LogHistory findByLoginLogKey(Long loginLogKey) throws Exception;
    
    /**
     * 로그인 이력 목록(페이징)
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Page<LogHistory> findAllLoginLog(PageRequest pageRequest) throws Exception;
    
    /**
     * 로그인 로그 총 목록
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<LogHistory> findAll() throws Exception;
    
    /**
     * 로그인 이력 총카운트
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Long findAllCount() throws Exception;
    
    /**
     * 로그인 이력 수정
     * @param loginLog
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public LogHistory update(LogHistory loginLog) throws Exception;
    
    /**
     * 로그인 이력 삭제
     * @param loginLog
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void delete(LogHistory loginLog) throws Exception;
    
    /**
     * 로그인 이력 전체 삭제
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteAll() throws Exception;
    
    /**
     * 계정 락 여부 체크(true : locked, false : unlocked)
     * @param email
     * @return
     */
    public boolean isAccountLocked(String email, Map<String, Object> scPolicy) throws Exception;
    
    /**
     * 계정 만료 여부 체크(-1 : expired, 0 : expiring, 1 : ok) 
     * @param email
     * @param scPolicy
     * @return
     * @throws Exception
     */
    public int isAccountExpired(String email, Map<String, Object> scPolicy) throws Exception;
    
    /**
     * 로그이력 목록
     * @param logHistoryDto
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Page<LogHistory> findAllLogHistory(LogHistoryDto logHistoryDto) throws Exception;
    
    /**
     * 로그이력 카운트
     * @param logHistoryDto
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Long findAllLogHistoryCount(LogHistoryDto logHistoryDto) throws Exception;
    
    /**
     * 로그 이력 삭제
     * @param LogHistoryList
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteListLogHistory(List<LogHistory> LogHistoryList) throws Exception;
    
    /**
     * 사용자의 로그전체 삭제
     * @param userKey
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteLogHistoryByUserkey(Long userKey) throws Exception;
}
