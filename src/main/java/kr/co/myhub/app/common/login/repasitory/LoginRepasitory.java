package kr.co.myhub.app.common.login.repasitory;

import java.util.List;

import kr.co.myhub.app.common.login.domain.LogHistory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * file   : LoginRepasitory.java
 * date   : 2013. 11. 28.
 * author : jmpark
 * content: 확장한 쿼리 메소드
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 28.   kbtapjm     최초생성
 */
@Repository
//@Transactional(readOnly = true) 
// Repasitory의 기본적인 설정, 확장하는 케이스는 메서드에 선언해서 사용, 단 서비스단에서 트랜잭셔널이 사용이 되면 서비스 트랜잭셔널이 적용
public interface LoginRepasitory extends JpaRepository<LogHistory, Long>{

    /**
     * 로그 이력 조회(by email)
     * @param email
     * @return
     */
    public List<LogHistory> findByEmailOrderByLogDateDesc(String email);
    
    /**
     * 정렬 값을 이용한 이력 조회
     * @param email
     * @param sort
     * @return
     */
    public List<LogHistory> findByEmail(String email, Sort sort);
    
    /**
     * 페이지 속성을 이용한 이력 조회
     * @param email
     * @param pageable
     * @return
     */
    public List<LogHistory> findByEmail(String email, Pageable pageable);
    
}
