package kr.co.myhub.appframework.constant;

/**
 * 
 * file   : AccountExpiredEnum.java
 * date   : 2014. 5. 1.
 * author : jmpark
 * content: 계정 암호 만료 결과 처리 값 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 5. 1.   kbtapjm     최초생성
 */
public enum AccountExpiredEnum {
    /* expired */
    expired(0, "expired", -1),
    
    /* expiring */
    expiring(1, "expiring", 0),
    
    /* ok */
    ok(2, "ok", 1);
    
    private int code;
    private String text;
    private int value;
    
    private AccountExpiredEnum(int code, String text, int value) {
        this.code = code;
        this.text = text;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
