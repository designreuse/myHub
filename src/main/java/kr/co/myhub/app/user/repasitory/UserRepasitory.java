package kr.co.myhub.app.user.repasitory;

import kr.co.myhub.app.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface UserRepasitory extends JpaRepository<User, Long> {
    
    public User findByUserKey(int userKey);
    
}
