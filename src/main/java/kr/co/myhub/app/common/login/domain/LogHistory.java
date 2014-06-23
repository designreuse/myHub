package kr.co.myhub.app.common.login.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import kr.co.myhub.app.user.domain.User;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * file   : LogHistory.java
 * date   : 2014. 6. 23.
 * author : jmpark
 * content: 로그 엔티티(로그인, 로그아웃)
 * ref: https://groups.google.com/forum/#!topic/ksug/oXVcsoJgOCc
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 6. 23.   kbtapjm     최초생성
 */
@Entity
@Table(name = "logHistory")
@XmlRootElement
public class LogHistory implements Serializable {

    private static final long serialVersionUID = -8484482196544565101L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "logHistoryKey", nullable = false)
    private Long logHistoryKey;
    
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    
    @Column(name = "ipAddress", nullable = false, length = 100)
    private String ipAddress;
    
    @Column(name = "logDate", nullable = true, insertable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "YY-MM-DD hh:mm:ss")
    private Date logDate;
    
    @Column(name = "logType", nullable = false, length = 2)
    private String logType;
    
    /**
     * User-LoginLog(1:N) 관계 - 로그인이력을 가져올때는 N정보에서 조인컬럼을 설정한다.
     * - fetch
     * LAZY : 연관을 맺고 있는 Entity들을 요청하는 순간 가져오는 설정
     * EAGER : 해당 Entity를 가져올 때 미리 연관을 맺고 있는 Entity들까지 모두 가져오는 것 
     * 참고(http://whiteship.me/?tag=manytoone)
     * 
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userKey", nullable = false)
    private User user;

    public Long getLogHistoryKey() {
        return logHistoryKey;
    }

    public void setLogHistoryKey(Long logHistoryKey) {
        this.logHistoryKey = logHistoryKey;
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

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
