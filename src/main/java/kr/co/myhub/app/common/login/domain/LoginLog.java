package kr.co.myhub.app.common.login.domain;

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
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "loginLog")
@XmlRootElement
public class LoginLog implements Serializable {

    private static final long serialVersionUID = -8484482196544565101L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loginlogkey", nullable = false)
    private Long loginLogKey;
    
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    
    @Column(name = "ipaddress", nullable = false, length = 100)
    private String ipAddress;
    
    @Column(name = "logindate", nullable = true, insertable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YY-MM-DD hh:mm:ss")
    private Date loginDate;
    
    @Column(name = "logoutdate", nullable = true, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YY-MM-DD hh:mm:ss")
    private Date logoutDate;
    
    @PrePersist
    public void prePersist() {
        this.loginDate = new Date();
        this.logoutDate = new Date();
    }

    public Long getLoginLogKey() {
        return loginLogKey;
    }

    public void setLoginLogKey(Long loginLogKey) {
        this.loginLogKey = loginLogKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }
}
