package kr.co.myhub.app.user.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = -4189088277865932249L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userkey", nullable = false)
    private int userKey;
    
    @Column(name = "userid", nullable = false, length = 50)
    private String userId;
    
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;
    
    @Column(name = "password", nullable = false, length = 12)
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
    
    /**
     * default 날짜 설정
     */
    @PrePersist
    public void prePersist() {
        this.crtDt = new Date();
        this.modDt = new Date();
    }

    public int getUserKey() {
        return userKey;
    }

    public void setUserKey(int userKey) {
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
}
