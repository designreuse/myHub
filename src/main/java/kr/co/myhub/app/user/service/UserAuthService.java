package kr.co.myhub.app.user.service;

import kr.co.myhub.app.user.domain.UserAuth;

/**
 * 
 * file   : UserAuthService.java
 * date   : 2013. 12. 12.
 * author : jmpark
 * content: 유저권한 서비스 인터페이스 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 12. 12.   kbtapjm     최초생성
 */
public interface UserAuthService {
    
    /**
     * 유저권한 등록
     * @param userAuth
     * @return
     * @throws Exception
     */
    public UserAuth create(UserAuth userAuth) throws Exception;

}
