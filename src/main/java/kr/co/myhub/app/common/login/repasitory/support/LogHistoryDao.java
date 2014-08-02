package kr.co.myhub.app.common.login.repasitory.support;

import kr.co.myhub.app.common.login.domain.LogHistory;
import kr.co.myhub.app.common.login.domain.QLogHistory;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * 
 * file   : LoginDao.java
 * date   : 2014. 8. 2.
 * author : jmpark
 * content: 로그인 DAO 
 * ref:
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 8. 2.   kbtapjm     최초생성
 */
@Repository
public class LogHistoryDao extends QueryDslRepositorySupport {

    private LogHistoryDao() {
        super(LogHistory.class);
    }
    
    /**
     * 사용자의 로그이력 삭제
     * @param userKey
     * @return
     */
    public long deleteLogHistoryByUserkey(Long userKey) throws Exception {
        QLogHistory qLogHistory = QLogHistory.logHistory;
        
        long result = delete(qLogHistory).where(qLogHistory.user.userKey.eq(userKey)).execute();
        
        return result;
    }
    
    

}
