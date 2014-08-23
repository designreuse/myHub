package kr.co.myhub.app.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import kr.co.myhub.app.admin.user.domain.dto.UserDto;
import kr.co.myhub.app.user.domain.QUser;
import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.app.user.domain.UserAuth;
import kr.co.myhub.app.user.repasitory.UserAuthRepasitory;
import kr.co.myhub.app.user.repasitory.UserRepasitory;
import kr.co.myhub.app.user.repasitory.support.UserDao;
import kr.co.myhub.app.user.service.UserService;
import kr.co.myhub.appframework.constant.UserPrivEnum;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.Predicate;

/**
 * 
 * file   : UserServiceImpl.java
 * date   : 2013. 11. 17.
 * author : jmpark
 * content: 유저 관련 서비스 구현체 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 17.   kbtapjm     최초생성
 */
@Service("UserService")
public class UserServiceImpl implements UserService  {
    
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Resource
    UserRepasitory userRepasitory;
    
    @Resource
    UserAuthRepasitory userAuthRepasitory;
    
    @Resource
    UserDao userDao;
    
    /**
     * 유저 등록
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public User insertUser(User user) throws Exception {
        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(user.getEmail());
        userAuth.setPriv(UserPrivEnum.Guests.getCode());
        
        userAuth.setUser(user);
        user.setUserAuth(userAuth);
        
        /* 유저 등록  */
        User retUser = userRepasitory.save(user);
        userAuthRepasitory.save(userAuth);
        
        return retUser;
    }

    /**
     * E-mail로 유저 정보 조회
     * @param email
     * @return
     * @throws Exception
     */
    public User findByEmail(String email) throws Exception {
        return userRepasitory.findByEmail(email);
    }

    /**
     * 유저 정보 조회
     * @param userKey
     * @return
     * @throws Exception
     */
    public User findByUserKey(Long userKey) throws Exception {
        return userRepasitory.findByUserKey(userKey);
    }

    /**
     * 유저 목록
     * @return
     * @throws Exception
     * ref 
     * http://whiteship.me/?cat=3078
     * http://i-proving.com/2014/05/06/spring-data-jparepository-and-querydsl/
     * http://netframework.tistory.com/entry/12-queryDSL-Spring-Data-JPA
     * 
     */
    public Page<User> findAllUser(UserDto userDto) throws Exception {
        Predicate predicate = null;
        QUser qUser = QUser.user;
        
        // 페이지 설정 초기화
        userDto.setPageInit();
        
        // 검색어 세팅
        if (!StringUtils.isEmpty(userDto.getSearchWord())) {
            String searchWord = userDto.getSearchWord();
            
            switch(userDto.getSearchType()) {
            case "name":
                if (!StringUtils.isEmpty(userDto.getGender())) {
                    predicate = qUser.userName.like("%".concat(searchWord).concat("%")).and(qUser.gender.eq(userDto.getGender()));
                } else {
                    predicate = qUser.userName.like("%".concat(searchWord).concat("%"));    
                }
                
                break;
            case "email":
                if (!StringUtils.isEmpty(userDto.getGender())) {
                    predicate = qUser.email.eq(searchWord).and(qUser.gender.eq(userDto.getGender()));    
                } else {
                    predicate = qUser.email.eq(searchWord);
                }
                
                break;
            case "birthday":
                if (!StringUtils.isEmpty(userDto.getGender())) {
                    predicate = qUser.birthday.eq(searchWord).and(qUser.gender.eq(userDto.getGender()));
                } else {
                    predicate = qUser.birthday.eq(searchWord);    
                }
                
                break;
            case "phoneNo":
                if (!StringUtils.isEmpty(userDto.getGender())) {
                    predicate = qUser.phoneNo.eq(searchWord).and(qUser.gender.eq(userDto.getGender()));
                } else {
                    predicate = qUser.phoneNo.eq(searchWord);    
                }
                
                break;
            }
        } else {
            // 성별
            if (!StringUtils.isEmpty(userDto.getGender())) {
                predicate = qUser.gender.eq(userDto.getGender());
            }
        }
        
        /* 페이지  정보 */
        Direction Direction = userDto.getSortType().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = new Sort(Direction, userDto.getSortName());
        
        PageRequest pageRequest = new PageRequest(userDto.getPage() - 1, userDto.getRows(), sort);
        
        return userRepasitory.findAll(predicate, pageRequest);
    }

    /**
     * 유저 카운트
     * (사용자 인증 : 관리자)
     * @param userDto
     * @return
     * @throws Exception
     */
    public Long findAllUserCount(UserDto userDto) throws Exception {
        Predicate predicate = null;
        
        QUser qUser = QUser.user;
        
        // 검색어 세팅
        if (!StringUtils.isEmpty(userDto.getSearchWord())) {
            String searchWord = userDto.getSearchWord();
            
            switch(userDto.getSearchType()) {
            case "name":
                if (!StringUtils.isEmpty(userDto.getGender())) {
                    predicate = qUser.userName.like("%".concat(searchWord).concat("%")).and(qUser.gender.eq(userDto.getGender()));
                } else {
                    predicate = qUser.userName.like("%".concat(searchWord).concat("%"));    
                }
                
                break;
            case "email":
                if (!StringUtils.isEmpty(userDto.getGender())) {
                    predicate = qUser.email.eq(searchWord).and(qUser.gender.eq(userDto.getGender()));    
                } else {
                    predicate = qUser.email.eq(searchWord);
                }
                
                break;
            case "birthday":
                if (!StringUtils.isEmpty(userDto.getGender())) {
                    predicate = qUser.birthday.eq(searchWord).and(qUser.gender.eq(userDto.getGender()));
                } else {
                    predicate = qUser.birthday.eq(searchWord);    
                }
                
                break;
            case "phoneNo":
                if (!StringUtils.isEmpty(userDto.getGender())) {
                    predicate = qUser.phoneNo.eq(searchWord).and(qUser.gender.eq(userDto.getGender()));
                } else {
                    predicate = qUser.phoneNo.eq(searchWord);    
                }
                
                break;
            }
        } else {
            // 성별
            if (!StringUtils.isEmpty(userDto.getGender())) {
                predicate = qUser.gender.eq(userDto.getGender());
            }    
        }
        
        return userRepasitory.count(predicate);
    }

    /**
     * 유저 수정
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public long updateUser(User user) throws Exception {
        return userDao.updateUserByUserKey(user);
    }

    /**
     * 유저 삭제
     * 1) Spring Data JPA api 사용(유저  테이블과 관계를 형성하고 있는데 데이터 모두 삭제 처리)
     * 2) queryDsl api 사용
     * 
     * @param user 키값만 필요하지만 validation 체크를 위해서는 엔티티 객체를 사용
     * @return
     * @throws Exception
     */
    @Transactional
    public void deleteUser(User user) throws Exception {
        /* 삭제 할 유저 정보 조회  */
        User deleteUser = userRepasitory.findByUserKey(user.getUserKey());
        
        /* 유저 정보 삭제 */
        userRepasitory.delete(deleteUser);
    }
    
    /**
     * 유저 로그인 정보 수정
     * @param isLoginSuccess
     * @param email
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public long updateUserLogin(boolean isLoginSuccess, String email) throws Exception {
        int loginFailCount = 0;
        
        // 로그인 실패 인경우
        if (!isLoginSuccess) {
            User user = userDao.selectUserByEmail(email);
            loginFailCount = user.getLoginFailCount() + 1;     // 실패카운트 증가
        }
         
        return userDao.updateUserLoginByEmail(isLoginSuccess, loginFailCount, email);
    }
    
    /**
     * 이메일 검색
     * @return
     * @throws Exception
     */
    public List<User> findByPhoneNo(String phoneNo) throws Exception {
        return userRepasitory.findByPhoneNo(phoneNo);
    }
    
    /**
     * 비밀번호 수정
     * @param password
     * @param lastPassword
     * @param email
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public long updatePasswordByEmail(String password, String lastPassword, String email) throws Exception {
        return userDao.updatePasswordByEmail(password, lastPassword, email);
    }
    
    /**
     * 유저 계정 락 초기화
     * @param userList
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateUserLockInit(List<User> userList) throws Exception {
        
        for (User user : userList) {
            if (user == null) continue;
         
            userDao.updateUserLockInit(user.getUserKey());
        }
    }
    
    /**
     * 유저 프로필 수정
     * @param profile
     * @param userKey
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void updateUserProfile(String profile, long userKey) throws Exception {
        userDao.updateUserProfile(profile, userKey);
    }
}
