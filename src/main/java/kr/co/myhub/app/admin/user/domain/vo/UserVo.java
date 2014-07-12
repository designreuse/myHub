package kr.co.myhub.app.admin.user.domain.vo;

import java.util.Date;

/**
 * 
 * file   : UserVo.java
 * date   : 2014. 7. 7.
 * author : jmpark
 * content: 유저정보 VO 
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 7. 7.   kbtapjm     최초생성
 */
public class UserVo {
    
    /* 사용자 key */
    private Long userKey; 
    
    /* 사용자아이디 */
    private String userId;
    
    /* email */
    private String email;
    
    /* 이름 */
    private String userName;
    
    /* 생일 */
    private String birthday;
    
    /* 성별 */
    private String gender;
    
    /* 전화번호 */
    private String phoneNo;
    
    /* 등록일 */
    private Date crtDt;
    
    /* 수정일 */
    private Date modDt;
    
    /* 비밀번호 수정일 */
    private Date passwordModDt;
    
    /* 로그인 실패 카운트 */
    private int loginFailCount;
    
    /* 로그인 실패일자 */
    private Date loginFailDt;
    
    /* 유저권한 정보 */
    private String priv;

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

    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv;
    }
}
