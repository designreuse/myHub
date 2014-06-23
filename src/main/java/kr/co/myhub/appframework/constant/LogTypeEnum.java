package kr.co.myhub.appframework.constant;

/**
 * 
 * file   : LogTypeEnum.java
 * date   : 2014. 6. 23.
 * author : jmpark
 * content: 로그이력 타입 
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 6. 23.   kbtapjm     최초생성
 */
public enum LogTypeEnum {

    /* 로그인 */
    logIn(0, "I"),
    
    /* 로그아웃 */
    logOut(1, "O");
    
    private int code;
    private String text;
    
    private LogTypeEnum(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
