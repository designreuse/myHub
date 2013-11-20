package kr.co.myhub.appframework.constant;

/**
 * 
 * file   : PatternEnum.java
 * date   : 2013. 11. 20.
 * author : jmpark
 * content: 정규식에 공통으로 사용할 규칙  
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 20.   kbtapjm     최초생성
 */
public enum PatternEnum {
    
    EMAIL_PATTERN(0, "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    
    private int code;
    private String text;
    
    private PatternEnum(int code, String text) {
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
