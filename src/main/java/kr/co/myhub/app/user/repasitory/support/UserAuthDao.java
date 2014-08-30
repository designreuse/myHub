package kr.co.myhub.app.user.repasitory.support;

import kr.co.myhub.app.user.domain.QUserAuth;
import kr.co.myhub.app.user.domain.UserAuth;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * 
 * file   : UserAuthDao.java
 * date   : 2014. 6. 16.
 * author : jmpark
 * content: 유저권한 queryDsl 쿼리 지원 
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 6. 16.   kbtapjm     최초생성
 */
@Repository
public class UserAuthDao extends QueryDslRepositorySupport {

    private UserAuthDao() {
        super(UserAuth.class);
    }

    /**
     * 유저 권한 삭제
     * @param userKey
     * @return
     * @throws Exception
     */
    public long deleteUserAuthByUserKey(long userKey) throws Exception {
        QUserAuth qUserAuth = QUserAuth.userAuth;
        long result = delete(qUserAuth).where(qUserAuth.userKey.eq(userKey)).execute();
        
        return result;
    }

}
