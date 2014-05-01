package kr.co.myhub.appframework.constant;

/**
 * 
 * file   : UseEnum.java
 * date   : 2014. 4. 29.
 * author : jmpark
 * content: 사용여부
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 4. 29.   kbtapjm     최초생성
 */
public enum UseEnum {

    UnUse(0, "UnUse"),
    Use(1, "Use");
    
    
    private int code;
    private String text;
    
    private UseEnum(int code, String text) {
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
