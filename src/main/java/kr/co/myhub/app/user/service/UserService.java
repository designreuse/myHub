package kr.co.myhub.app.user.service;

import java.util.Date;
import java.util.List;

import kr.co.myhub.app.user.domain.User;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * 
 * file   : UserService.java
 * date   : 2013. 11. 17.
 * author : jmpark
 * content: 유저 관련 서비스 인터페이스 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 17.   kbtapjm     최초생성
 */
public interface UserService {
    
   /**
    * 유저 등록
    * @param user
    * @return
    * @throws Exception
    */
    public User insertUser(User user) throws Exception;
    
    /**
     * E-mail로 유저 정보 조회
     * @param email
     * @return
     * @throws Exception
     */
    public User findByEmail(String email) throws Exception;
    
    /**
     * 유저 정보 조회
     * @param userKey
     * @return
     * @throws Exception
     */
    public User findByUserKey(Long userKey) throws Exception;
    
    /**
     * 유저 목록
     * @return
     * @throws Exception
     */
    public List<User> findAllUser() throws Exception;
    
    /**
     * 유저 카운트
     * @return
     * @throws Exception
     */
    public Long findAllCount() throws Exception;
    
    /**
     * 유저 수정
     * @param user
     * @return
     * @throws Exception
     */
    public Long updateUser(User user) throws Exception;
    
    /**
     * 유저 삭제
     * @param user
     * @return
     * @throws Exception
     */
    public void delete(User user) throws Exception;
    
    /**
     * 로그인 결과 수정
     * @param loginFailCount
     * @param loginFailDt
     * @param userId
     * @return
     */
    public int updateUserFailLogin(Date loginFailDt, String email) throws Exception;
    
    /**
     * 로그인성공시 수정
     * @param email
     * @return
     */
    public int updateUserSuccessLogin(Date loginFailDt, String email) throws Exception;
    
    /**
     * 이메일 검색
     * @return
     * @throws Exception
     */
    public List<User> findByPhoneNo(String phoneNo) throws Exception;
    
    /**
     * 비밀번호 수정
     * @param password
     * @param lastPassword
     * @param passwordModDt
     * @param email
     * @return
     */
    //@PreAuthorize("hasRole('ROLE_ADMIN')") // 유효한 인증된 사용자만이 접근
    public int updatePassword(String password, String lastPassword, Date passwordModDt, String email);
    
}
