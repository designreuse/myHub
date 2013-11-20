package kr.co.myhub.appframework.constant;

/**
 * 
 * file   : TypeEnum.java
 * date   : 2013. 11. 20.
 * author : jmpark
 * content: Request 요청 Type 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 20.   kbtapjm     최초생성
 */
public enum TypeEnum {
    CREATE(0, "CREATE"),
    READ(1, "READ"),
    UPDATE(2, "UPDATE"),
    DELETE(3, "DELETE"),
    SELECT(4, "SELECT");
    
    private int code;
    private String text;
    
    private TypeEnum(int code, String text) {
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
