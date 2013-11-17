package kr.co.myhub.app.user.service;

import kr.co.myhub.app.user.domain.User;

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
    public User create(User user) throws Exception;
    
    /**
     * E-mail로 유저정보 조회
     * @param email
     * @return
     * @throws Exception
     */
    public User findByEmail(String email) throws Exception;
    
    
}
