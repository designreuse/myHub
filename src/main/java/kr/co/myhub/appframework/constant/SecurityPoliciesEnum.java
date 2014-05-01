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
    
    /* none */
    None(0, "0", "0"),
    
    /* 사용자 계정과  계정 불일치 */
    NotMatchUsername(1, "Not Match Username", "0"),
    
    /* 최소 패스워드 길이 */
    MinimumPasswordLength(2, "Minimum Password Length", "6"),
    
    /* 최소 한개 이상의 대분자 */
    ContainUppercase(3, "Contain Uppercase", "0"),
    
    /* 최소 한개 이상의 소문자 */
    ContainLowercase(4, "Contain Lowercase", "0"),
    
    /* 최소 한개 이상의 숫자 */
    ContainNumber(5, "Contain Number", "0"),
    
    /* 최소 한개 이상의 알파벳 이외의 문자 */
    ContainNonAlphabetic(6, "Contain Non Alphabetic","0"),
    
    /* 비밀번호 이력 */
    PasswordHistory(8, "Password History", "0"),
    
    /* 비밀번호 이력 값*/
    PasswordHistoryValue(9, "Password History Value", "2"),
    
    /* 최대 암호 사용기간(관리자) (0 : 미적용, 1 : 적용) */
    MaximumPasswordAgeAdmin(10, "Maximum Password Age(Admin)", "1"),
    
    /* 최대 암호 사용기간(관리자) 값 */
    MaximumPasswordAgeValueAdmin(11, "Maximum Password Age Value(Admin)", "30"),
    
    /* 최대 암호 사용기간(일반) (0 : 미적용, 1 : 적용)  */
    MaximumPasswordAgeRegular(12, "Maximum Password Age(Regular)", "1"),
    
    /* 최대 암호 사용기간(일반) 값*/
    MaximumPasswordAgeValueRegular(13, "Maximum Password Age Value(Regular)", "90"),
    
    /* 계정 락 */
    AccountLockout(14, "Account Lockout", "0"),
    
    /* 계정 잠금 기간 값(5분) */
    AccountLockoutDurationValue(15, "Account Lockout Duration Value", "5"),
    
    /* 계정 잠금 임계 값 (3회) */
    AccountLockoutThresholdValue(16, "Account Lockout Threshold Value", "3"),
    
    /* 초기 암호 */
    InitialPassword(17, "Initial Password", "1"),
    
    /* 초기 암호  값*/
    InitialPasswordValue(18, "Initial Password Value", "admin1234"),
    
    /* 최소암호 사용기간  미적용, (0 : 미적용, 1 : 적용) */
    MinimumPasswordAge(19, "Minimum Password Age", "1"),
    
    /* 최소암호 사용기간 값 */
    MinimumPasswordAgeValue(20, "Minimum Password Age Value", "1"),
    
    /* 비밀번호 만료 경고  (0 : 미적용, 1 : 적용) */
    PasswordExpiryWarning(21, "Password Expiry Warning", "1"),
    
    /* 비밀번호 만료 경고  값*/
    PasswordExpiryWarningValue(22, "Password Expiry Warning Value", "14");
    
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
