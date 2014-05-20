package kr.co.myhub.app.user.domain.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.appframework.constant.SecurityPoliciesEnum;
import kr.co.myhub.appframework.validator.MyHubValidator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 
 * file   : UserValidator.java
 * date   : 2013. 11. 18.
 * author : jmpark
 * content: 유저 데이터 검증 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 18.   kbtapjm     최초생성
 */
public class UserValidator extends MyHubValidator implements Validator {
    
    /* User 엔티티  */
    private User user;
    
    /* Error 객체  */
    private Errors errors;
    
    /* Type */
    private int type;
    
    int minimumPasswordLength = 0;
    
    /* Constructors */
    public UserValidator(int type) {
        this.type = type;
    }
    
    /**
     * clazz : 체크 대상의 Class 오브젝트 
     * return : cls 형의 오브젝트가 이 클래스에 할당할 수 있을지 어떨지를 나타내는 boolean
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    /**
     * validate 
     */
    @Override
    public void validate(Object target, Errors errors) {
        this.user = (User)target;
        this.errors = errors;
        
        switch(type) {
        case 0: // Create
            this.validateCreate();
            break;
        case 2: // Update
            this.validateUpdate();
            break;
        case 3: // Delete
            this.validateDelete();
            break;
        }
    }
    
    /**
     * 유저 등록 체크
     */
    public void validateCreate() {
        MyHubValidator.rejectIfEmptyOrWhitespace(errors, "email", "myhub.label.input.email.address");
        MyHubValidator.rejectIsEmail(errors, "email", "myhub.label.input.vaild.email.address");
        MyHubValidator.rejectIfEmptyOrWhitespace(errors, "userId", "myhub.label.input.id");
        MyHubValidator.rejectIfEmptyOrWhitespace(errors, "password", "myhub.label.input.password");
        MyHubValidator.rejectIfEmptyOrWhitespace(errors, "userName", "myhub.label.input.name");
        MyHubValidator.rejectIfEmptyOrWhitespace(errors, "birthday", "myhub.label.input.birthdy");
        MyHubValidator.rejectNotEqualslength(errors, "birthday", "myhub.label.input.birthdy.length", 8);
        MyHubValidator.rejectIfEmptyOrWhitespace(errors, "gender", "myhub.label.select.gender");
        
        // 패스워드 검증
        if (StringUtils.isNotEmpty(user.getPassword())) {
            //this.validatePassword();
        }
    }
    
    /**
     * 유저 수정 체크
     */
    public void validateUpdate() {
        MyHubValidator.rejectIfEmptyOrWhitespace(errors, "userKey", "myhub.label.input.key");
        MyHubValidator.rejectIfEmptyOrWhitespace(errors, "password", "myhub.label.input.password");
        MyHubValidator.rejectIfEmptyOrWhitespace(errors, "userName", "myhub.label.input.name");
        MyHubValidator.rejectIfEmptyOrWhitespace(errors, "birthday", "myhub.label.input.birthdy");
        MyHubValidator.rejectNotEqualslength(errors, "birthday", "myhub.label.input.birthdy.length", 8);
        MyHubValidator.rejectIfEmptyOrWhitespace(errors, "gender", "myhub.label.select.gender");
        
        // 패스워드 검증
        if (StringUtils.isNotEmpty(user.getPassword())) {
            //this.validatePassword();
        }
    }
    
    /**
     * 유저 삭제 체크
     */
    public void validateDelete() {
        MyHubValidator.rejectIfEmptyOrWhitespace(errors, "userKey", "myhub.label.input.key");
    }
    
    /**
     * 비밀번호 체크
     */
    public void validatePassword() {
        Pattern pattern = null;
        Matcher matcher = null;
        
        /**
         * ems 보안정책을 관리하는 부분들이 있을 경우 정책에 따라서 체크를 하거나 안할수 있다.
         * 모든정책은  상수(Enum)에 정의를 하고  정책을 관리하는 부분을 admin으로 만들어서 관리한다.
         */
        
        // ============================================================================================
        // 1) NotMatchUsername(암호는 사용자 계정과 일치해서는 안된다.)
        // ============================================================================================
        if (StringUtils.isEmpty(user.getUserId()) || user.getUserId().equals(user.getPassword())) {
            MyHubValidator.rejectValue(errors, "password", "myhub.label.user.passwordNotMatchUsername");
        }
        
        // ============================================================================================
        // 2) Contain Uppercase(암호는 최소 하나 이상의 대문자(A-Z)를 포함해야 한다.)
        // ============================================================================================
        pattern = Pattern.compile("^.*[A-Z]");
        matcher = pattern.matcher(user.getPassword());
        
        if (!matcher.find()) {
            MyHubValidator.rejectValue(errors, "password", "myhub.label.user.passwordContainUppercase");
        }
        
        // ============================================================================================
        // 3) Contain Lowercase(암호는 최소 하나 이상의 소문자(a-z)를 포함해야 한다.)
        // ============================================================================================
        pattern = Pattern.compile("^.*[a-z]");
        matcher = pattern.matcher(user.getPassword());
        
        if (!matcher.find()) {
            MyHubValidator.rejectValue(errors, "password", "myhub.label.user.passwordContainLowercase");
        }
        
        // ============================================================================================
        // 4) Contain Number(암호는 최소 하나 이상의 숫자(0-9)를 포함해야 한다.)
        // ============================================================================================
        pattern = Pattern.compile("^.*[0-9]");
        matcher = pattern.matcher(user.getPassword());
        
        if (!matcher.find()) {
            MyHubValidator.rejectValue(errors, "password", "myhub.label.user.passwordContainNumber");
        }
        
        // ============================================================================================
        // 5) Contain Non Alphabetic(암호는 최소 하나 이상의 알파벳 이외의 문자(!@#$%^&*.,`)를 포함해야 한다.)
        // ============================================================================================
        pattern = Pattern.compile("^.*[a-zA-Z]*(!|@|#|[$]|%|\\^|&|[*]|[.]|,|`)");
        matcher = pattern.matcher(user.getPassword());
        
        if (!matcher.find()) {
            MyHubValidator.rejectValue(errors, "password", "myhub.label.user.passwordContainNonAlphabetic");
        }
        
        // ============================================================================================
        // 6) Minimum Password Length(최소 암호 길이: <n> 문자)
        // ============================================================================================
        minimumPasswordLength = Integer.parseInt(SecurityPoliciesEnum.MinimumPasswordLength.getValue());
        
        if (user.getPassword().length() < minimumPasswordLength) {
            MyHubValidator.rejectValue(errors, "password", "myhub.label.user.passwordMinimumPasswordLength", new String[] {String.valueOf(minimumPasswordLength)});
        }
    }
}
