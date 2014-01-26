package kr.co.myhub.app.user.repasitory;

import java.util.Date;

import kr.co.myhub.app.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * file   : UserRepasitory.java
 * date   : 2013. 11. 17.
 * author : jmpark
 * content: Spring Data JPA에서  제공하는 JpaRepository를 상속받아 User관련 확장 인터페이스
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 17.   kbtapjm     최초생성
 */
@Repository 
public interface UserRepasitory extends JpaRepository<User, Long> {
    
    /**
     * 유저 키에 해당하는 유저정보 조회
     * @param userKey
     * @return
     */
    public User findByUserKey(Long userKey);
    
    /**
     * E-mail로 유저정보 조회
     * @param email
     * @return
     */
    public User findByEmail(String email);
    
    /**
     * 로그인 결과 수정
     * @param loginFailCount
     * @param loginFailDt
     * @param userId
     * @return
     */
    @Modifying
    @Query("update User set loginFailCount = loginFailCount + 1, loginFailDt = :loginFailDt where userId = :userId")
    public int updateUserLogin(@Param("loginFailDt")Date loginFailDt, @Param("userId")String userId);
    
}
