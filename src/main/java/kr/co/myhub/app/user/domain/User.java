package kr.co.myhub.app.user.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import kr.co.myhub.app.common.login.domain.LogHistory;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * file   : User.java
 * date   : 2013. 11. 28.
 * author : jmpark
 * content: 유저 도메인 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 28.   kbtapjm     최초생성
 */
@Entity
@Table(name = "user")
@XmlRootElement
public class User implements Serializable { 

    private static final long serialVersionUID = -4189088277865932249L;
    
    /* 사용자 key */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userkey", nullable = false)
    private Long userKey; 
    
    /* 사용자아이디 */
    @Column(name = "userid", nullable = false, length = 50)
    private String userId;
    
    /* email */
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;
    
    /* 비밀번호 */
    @Column(name = "password", nullable = false, length = 100)
    @JsonIgnore
    private String password;
    
    /* 이름 */
    @Column(name = "username", nullable = false, length = 100)
    private String userName;
    
    /* 생일 */
    @Column(name = "birthday", nullable = false, length = 8)
    private String birthday;
    
    /* 성별 */
    @Column(name = "gender", nullable = false)
    private String gender;
    
    /* 전화번호 */
    @Column(name = "phoneNo", nullable = false, length = 11)
    private String phoneNo;
    
    /* 등록일 */
    @Column(name = "crtDt", nullable = true, insertable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YY-MM-DD hh:mm:ss")
    private Date crtDt;
    
    /* 수정일 */
    @Column(name = "modDt", nullable = true, insertable = false, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YY-MM-DD hh:mm:ss")
    private Date modDt;
    
    /* 비밀번호 수정일 */
    @Column(name = "passwordModDt", nullable = true, insertable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YY-MM-DD hh:mm:ss")
    private Date passwordModDt;
    
    /* 마지막 비밀번호 */
    @Column(name = "lastPassword", nullable = false, length = 100)
    @JsonIgnore
    private String lastPassword;
    
    /* 로그인 실패 카운트 */
    @Column(name = "loginFailCount", nullable = false)
    private int loginFailCount;
    
    /* 로그인 실패일자 */
    @Column(name = "loginFailDt", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YY-MM-DD hh:mm:ss")
    private Date loginFailDt;
    
    /* 프로필  (TODO: 이미지 관리하는 테이블로 추후 변경) ==> 게시판 개발시 */ 
    @Column(name = "profile", nullable = true, length = 100)
    private String profile;
    
    /**
     * 로그 이력 조회(테이블 관계가 있는 경우에는 맵핑되는 도메인에 설정을 하는것이 좋다.)
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnore
    private Set<LogHistory> logHistory = new HashSet<LogHistory>();
    
    /**
     * 유저권한관의 1:1 관계 정보 로딩
     * optional false: 결코 Null일 수 없다는 뜻
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    @JsonIgnore
    private UserAuth userAuth;

    public Long getUserKey() {
        return userKey;
    }

    public void setUserKey(Long userKey) {
        this.userKey = userKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Date getCrtDt() {
        return this.crtDt;
    }

    public void setCrtDt(Date crtDt) {
        this.crtDt = crtDt;
    }
    
    public Date getModDt() {
        return this.modDt;
    }

    public void setModDt(Date modDt) {
        this.modDt = modDt;
    }

    public Date getPasswordModDt() {
        return this.passwordModDt;
    }

    public void setPasswordModDt(Date passwordModDt) {
        this.passwordModDt = passwordModDt;
    }

    public String getLastPassword() {
        return this.lastPassword;
    }

    public void setLastPassword(String lastPassword) {
        this.lastPassword = lastPassword;
    }

    public int getLoginFailCount() {
        return this.loginFailCount;
    }

    public void setLoginFailCount(int loginFailCount) {
        this.loginFailCount = loginFailCount;
    }

    public Date getLoginFailDt() {
        return this.loginFailDt;
    }

    public void setLoginFailDt(Date loginFailDt) {
        this.loginFailDt = loginFailDt;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Set<LogHistory> getLogHistory() {
        return logHistory;
    }

    public void setLogHistory(Set<LogHistory> logHistory) {
        this.logHistory = logHistory;
    }

    public UserAuth getUserAuth() {
        return this.userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
        this.userAuth.setUser(this);
    }
    
    /**
     * default 날짜 설정
     */
    @PrePersist
    public void prePersist() {
        this.crtDt = new Date();
        this.passwordModDt = new Date();
    }
    
    @PreUpdate
    public void preUpdate() {
        this.modDt = new Date();
    }
}
