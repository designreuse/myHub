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
    SUCCESS("0000"),
    FAIL("9999");
    
    private String code;
    
    private Result(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
