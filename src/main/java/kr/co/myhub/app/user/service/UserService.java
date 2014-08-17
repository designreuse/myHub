package kr.co.myhub.app.user.service;

import java.util.List;

import kr.co.myhub.app.admin.user.domain.dto.UserDto;
import kr.co.myhub.app.user.domain.User;

import org.springframework.data.domain.Page;
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
     * (사용자 인증 : 관리자, 매니져, 일반 사용자)
     * @param userKey
     * @return
     * @throws Exception
     */
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
    public User findByUserKey(Long userKey) throws Exception;
    
    /**
     * 유저 목록
     * (사용자 인증 : 관리자)
     * @param userDto
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<User> findAllUser(UserDto userDto) throws Exception;
    
    /**
     * 유저 카운트
     * (사용자 인증 : 관리자)
     * @param userDto
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Long findAllUserCount(UserDto userDto) throws Exception;
    
    /**
     * 유저 수정
     * (사용자 인증 : 작성자, 관리자)
     * @param user
     * @return
     * @throws Exception
     */
    @PreAuthorize("#user.email == principal.username or hasRole('ROLE_ADMIN')")
    public long updateUser(User user) throws Exception;
    
    /**
     * 유저 삭제
     * (사용자 인증 : 작성자, 관리자)
     * @param user
     * @throws Exception
     */
    @PreAuthorize("#user.email == principal.username or hasRole('ROLE_ADMIN')")
    public void deleteUser(User user) throws Exception;
    
    /**
     * 유저 로그인 정보 수정
     * @param isLoginSuccess
     * @param email
     * @return
     * @throws Exception
     */
    public long updateUserLogin(boolean isLoginSuccess, String email) throws Exception;
    
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
     * @param email
     * @return
     */
    public long updatePasswordByEmail(String password, String lastPassword, String email) throws Exception;
    
    /**
     * 유저 계정 락 초기화
     * @param userList
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void updateUserLockInit(List<User> userList) throws Exception;
    
    /**
     * 유저 프로필 수정
     * @param profile
     * @param userKey
     * @return
     * @throws Exception
     */
    public long updateUserProfile(String profile, long userKey) throws Exception;
    
}
