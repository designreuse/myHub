package kr.co.myhub.app.common.login.repasitory.support;

import kr.co.myhub.app.common.login.domain.LogHistory;
import kr.co.myhub.app.common.login.domain.QLogHistory;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

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
    
    /**
     * 로그정보 조회
     * @param email
     * @return
     * @throws Exception
     */
    public LogHistory selectLogHistoryByEmailAndLogType(String email, String logType) throws Exception {
        JPQLQuery query = new JPAQuery(getEntityManager());
        QLogHistory qLogHistory = QLogHistory.logHistory;
        
        LogHistory logHistory = query.from(qLogHistory)
                .where(qLogHistory.email.eq(email).and(qLogHistory.logType.eq(logType)))
                .orderBy(qLogHistory.logDate.desc())
                .limit(1)
                .uniqueResult(qLogHistory);
        
        return logHistory;
        
    }
    
    /**
     * 하이버네이트 세션 가져오기
     * @return
     */
    public Session getSession() {
        return (Session) getEntityManager().getDelegate();
    }

}
