package kr.co.myhub.appframework.constant;

/**
 * 
 * file   : Security.java
 * date   : 2014. 6. 6.
 * author : jmpark
 * content: 인증 코드 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 6. 6.   kbtapjm     최초생성
 */
public enum Security {
    TokenExpired("9001", "Token Expired"),
    TokenExpiring("9002", "Token Expiring"),
    AuthenticationFail("9003", "Authentication Fail");
    
    private String code;
    private String text;
    
    private Security(String code, String text) {
        this.code = code;
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
