package kr.co.myhub.app.user.repasitory;

import kr.co.myhub.app.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
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
    
}
