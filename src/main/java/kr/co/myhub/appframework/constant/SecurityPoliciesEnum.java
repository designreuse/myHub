package kr.co.myhub.appframework.constant;

/**
 * 
 * file   : SecurityPoliciesEnum.java
 * date   : 2013. 11. 20.
 * author : jmpark
 * content: 보안 정책  
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 20.   kbtapjm     최초생성
 */
public enum SecurityPoliciesEnum {
    
    None(0, "0", "0"),
    NotMatchUsername(1, "Not Match Username", "0"),
    MinimumPasswordLength(2, "Minimum Password Length", "6"),
    ContainUppercase(3, "Contain Uppercase", "0"),
    ContainLowercase(4, "Contain Lowercase", "0"),
    ContainNumber(5, "Contain Number", "0"),
    ContainNonAlphabetic(6, "Contain Non Alphabetic","0");
    
    private int code;
    private String text;
    private String value;
    
    private SecurityPoliciesEnum(int code, String text, String value) {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
