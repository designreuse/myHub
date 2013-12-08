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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import kr.co.myhub.app.common.login.domain.LoginLog;

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
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userkey", nullable = false)
    private Long userKey;
    
    @Column(name = "userid", nullable = false, length = 50)
    private String userId;
    
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;
    
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    
    @Column(name = "username", nullable = false, length = 100)
    private String userName;
    
    @Column(name = "birthday", nullable = false, length = 8)
    private String birthday;
    
    @Column(name = "gender", nullable = false)
    private String gender;
    
    @Column(name = "priv", nullable = false)
    private int priv;
    
    @Column(name = "crtdt", nullable = true, insertable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YY-MM-DD hh:mm:ss")
    private Date crtDt;
    
    @Column(name = "modDt", nullable = true, insertable = false, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YY-MM-DD hh:mm:ss")
    private Date modDt;
    
    /* 비밀번호 수정일 */
    @Column(name = "passwordModDt", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YY-MM-DD hh:mm:ss")
    private Date passwordModDt;
    
    /* 마지막 비밀번호 */
    @Column(name = "lastPassword", nullable = false, length = 100)
    private String lastPassword;
    
    /* 로그인 실패 카운트 */
    @Column(name = "loginFailCount", nullable = false)
    private int loginFailCount;
    
    /* 로그인 실패일자 */
    @Column(name = "loginFailDt", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YY-MM-DD hh:mm:ss")
    private Date loginFailDt;
    
    /**
     * 로그인 이력 조회(테이블 관계가 있는 경우에는 맵핑되는 도메인에 설정을 하는것이 좋다.)
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="user")
    private Set<LoginLog> loginLog = new HashSet<LoginLog>();
    
    /**
     * default 날짜 설정
     */
    @PrePersist
    public void prePersist() {
        this.crtDt = new Date();
        this.modDt = new Date();
    }
    
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

    public int getPriv() {
        return priv;
    }

    public void setPriv(int priv) {
        this.priv = priv;
    }

    public Date getCrtDt() {
        return crtDt;
    }

    public void setCrtDt(Date crtDt) {
        this.crtDt = crtDt;
    }
    
    public Date getModDt() {
        return modDt;
    }

    public void setModDt(Date modDt) {
        this.modDt = modDt;
    }

    public Date getPasswordModDt() {
        return passwordModDt;
    }

    public void setPasswordModDt(Date passwordModDt) {
        this.passwordModDt = passwordModDt;
    }

    public String getLastPassword() {
        return lastPassword;
    }

    public void setLastPassword(String lastPassword) {
        this.lastPassword = lastPassword;
    }

    public int getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(int loginFailCount) {
        this.loginFailCount = loginFailCount;
    }

    public Date getLoginFailDt() {
        return loginFailDt;
    }

    public void setLoginFailDt(Date loginFailDt) {
        this.loginFailDt = loginFailDt;
    }

    public Set<LoginLog> getLoginLog() {
        return loginLog;
    }

    public void setLoginLog(Set<LoginLog> loginLog) {
        this.loginLog = loginLog;
    }
}
