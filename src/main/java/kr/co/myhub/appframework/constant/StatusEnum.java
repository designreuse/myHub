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
    SUCCESS(0, "SUCCESS"),
    FAIL(0, "FAIL");
    
    private int code;
    private String text;
    
    private StatusEnum(int code, String text) {
        this.code = code;
        this.text = text;
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
}
