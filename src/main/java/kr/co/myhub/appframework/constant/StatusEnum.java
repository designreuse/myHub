package kr.co.myhub.appframework.constant;

/**
 * 
 * file   : StatusEnum.java
 * date   : 2013. 11. 18.
 * author : jmpark
 * content: 상태정보 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 18.   kbtapjm     최초생성
 */
public enum StatusEnum {
    SUCCESS(0, "SUCCESS", "0000"),
    FAIL(1, "FAIL", "9000");
    
    private int code;
    private String text;
    private String value;
    
    private StatusEnum(int code, String text, String value) {
        this.code = code;
        this.text = text;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
