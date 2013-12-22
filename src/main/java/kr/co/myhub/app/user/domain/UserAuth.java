package kr.co.myhub.app.user.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * file   : UserAuth.java
 * date   : 2013. 12. 9.
 * author : jmpark
 * content: 유저 권한 domain 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 12. 9.   kbtapjm     최초생성
 */
@Entity
@Table(name = "UserAuth")
@XmlRootElement
public class UserAuth implements Serializable {

    private static final long serialVersionUID = 1150248750560957540L;
    
    @Id
    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
    @GeneratedValue(generator = "generator")
    @Column(name = "userAuthKey", nullable = false, unique = true)
    private Long userAuthKey;
    
    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;
    
    @Column(name = "priv", nullable = false)
    private int priv;
    
    @Column(name = "crtDt", nullable = true, insertable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YY-MM-DD hh:mm:ss")
    private Date crtDt;
    
    /* 유저 정보 */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private User user;
    
    public UserAuth() {}

    public UserAuth(String email, int priv, Date crtDt, User user) {
        this.email = email;
        this.priv = priv;
        this.crtDt = crtDt;
        this.user = user;
    }

    public Long getUserAuthKey() {
        return userAuthKey;
    }

    public void setUserAuthKey(Long userAuthKey) {
        this.userAuthKey = userAuthKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    @PrePersist
    public void prePersist() {
        this.crtDt = new Date();
    }
}
