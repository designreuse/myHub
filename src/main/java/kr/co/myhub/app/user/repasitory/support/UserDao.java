package kr.co.myhub.app.user.repasitory.support;

import java.util.Date;

import kr.co.myhub.app.user.domain.QUser;
import kr.co.myhub.app.user.domain.User;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * 
 * file   : UserDao.java
 * date   : 2014. 6. 15.
 * author : jmpark
 * content: UserDao(queryDsl의 다양한 쿼리 지원)
 * http://netframework.tistory.com/entry/13-queryDSL-Spring-Data-JPA
 * http://www.querydsl.com/static/querydsl/2.1.0/reference/html/ch02s02.html#d0e306
 * http://whiteship.me/?p=13231
 * 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 6. 15.   kbtapjm     최초생성
 */
@Repository
public class UserDao extends QueryDslRepositorySupport {
    
    private UserDao() {
        super(User.class);
    }
    
    /**
     * 유저 수정
     * @param user
     * @return
     * @throws Exception
     */
    public long updateUserByUserKey(User user) throws Exception {
        QUser qUser = QUser.user;
        long result = update(qUser)
            .where(qUser.userKey.eq(user.getUserKey()))
            .set(qUser.userName, user.getUserName())
            .set(qUser.birthday, user.getBirthday())
            .set(qUser.phoneNo, user.getPhoneNo())
            .set(qUser.gender, user.getGender())
            .set(qUser.modDt, new Date())
            .execute();
        
        return result;
    }
    
    /**
     * 유저 로그인 정보 수정
     * @param user
     * @return
     * @throws Exception
     */
    public long updateUserLoginByEmail(boolean isLoginSuccess, String email) throws Exception {
        QUser qUser = QUser.user;
        
        long result = 0;
        
        if (isLoginSuccess) {
            result = update(qUser)
                    .where(qUser.email.eq(email))
                    .set(qUser.loginFailDt, null)
                    .set(qUser.loginFailCount, 0)
                    .execute();    
        } else {
            result = update(qUser)
                    .where(qUser.email.eq(email))
                    .set(qUser.loginFailDt, new Date())
                    .set(qUser.loginFailCount, 0)
                    .execute();
        }
        
        return result;
    }
    
    /**
     * 비밀번호 수정
     * @param password
     * @param email
     * @return
     * @throws Exception
     */
    public long updatePasswordByEmail(String password, String lastPassword, String email) throws Exception {
        QUser qUser = QUser.user;
        long result = update(qUser)
            .where(qUser.email.eq(email))
            .set(qUser.password, password)
            .set(qUser.lastPassword, lastPassword)
            .set(qUser.passwordModDt, new Date())
            .execute();
        
        return result;
    }
    
    /**
     * 유저 삭제
     * @param userKey
     * @return
     */
    public long deleteUserByUserKey(long userKey) throws Exception {
        QUser qUser = QUser.user;
        long result = delete(qUser).where(qUser.userKey.eq(userKey)).execute();
                
        return result;
    }

}
