package kr.co.myhub.appframework.constant;

/**
 * 
 * file   : Result.java
 * date   : 2014. 5. 16.
 * author : jmpark
 * content: 결과
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 5. 16.   kbtapjm     최초생성
 */
public enum Result {
    SUCCESS("0000", "Success"),
    FAIL("9999", "Fail");
    
    private String code;
    private String text;
    
    private Result(String code, String text) {
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
