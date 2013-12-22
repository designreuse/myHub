package kr.co.myhub.app.user.repasitory;

import kr.co.myhub.app.user.domain.UserAuth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * file   : UserAuthRepasitory.java
 * date   : 2013. 12. 12.
 * author : jmpark
 * content: 유저권한 Repository
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 12. 12.   kbtapjm     최초생성
 */
@Repository
public interface UserAuthRepasitory extends JpaRepository<UserAuth, Long>{

}
