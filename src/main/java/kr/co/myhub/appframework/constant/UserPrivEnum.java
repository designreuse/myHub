package kr.co.myhub.appframework.constant;

/**
 * 
 * file   : UserPrivEnum.java
 * date   : 2014. 5. 29.
 * author : jmpark
 * content: 사용자 권한 정보
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 5. 29.   kbtapjm     최초생성
 */
public enum UserPrivEnum {
    SuperUser(0, "SuperUser"),  // 총관리자
    Operators(1, "Operators"),  // 운영자
    Guests(2, "Guests");        // 일반유저
    
    private int code;
    private String text;
    
    private UserPrivEnum(int code, String text) {
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
