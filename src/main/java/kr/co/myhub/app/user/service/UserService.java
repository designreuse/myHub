package kr.co.myhub.app.user.service;

import kr.co.myhub.app.user.domain.User;

public interface UserService {
    
   /**
    * 유저 등록
    * @param user
    * @return
    * @throws Exception
    */
    public User create(User user) throws Exception;
}
