package kr.co.myhub.appframework.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.myhub.appframework.constant.PatternEnum;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * 
 * file   : MyHubValidator.java
 * date   : 2013. 11. 18.
 * author : jmpark
 * content: validator 관련 확장(서버 데이터 검증) - ValidationUtils을 이용
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 18.   kbtapjm     최초생성
 */
public class MyHubValidator extends ValidationUtils {

    /**
     * Object(filed)에서 해당 데이터를 정수형으로 변환한다.
     * @param errors
     * @param field
     * @return
     * @throws Exception
     */
    public static int getInt(Errors errors, String field) throws Exception {
        return Integer.parseInt(errors.getFieldValue(field).toString());
    }
    
    /**
     * Object(filed)에서  해당 데이터를 문자열로 변환한다.
     * @param errors
     * @param field
     * @return
     * @throws Exception
     */
    public static String getString(Errors errors, String field) throws Exception {
        return errors.getFieldValue(field).toString();
    }
    
    /**
     * error 코드를 global로 지정한다.
     * @param errors
     * @param msgKey
     */
    public static void reject(Errors errors, String msgKey){
        errors.reject(msgKey);
    }
    
    /**
     * error 코드와 swap 문자열이 존재 할 경우
     * @param errors
     * @param msgKey
     * @param arguments
     */
    public static void reject(Errors errors, String msgKey, Object[] arguments){
        errors.reject(msgKey, arguments, null);
    }
    
    /**
     * error 코드와 field 를 직접 지정
     * @param errors
     * @param field
     * @param msgKey
     */
    public static void rejectValue(Errors errors, String field, String msgKey) {
        errors.rejectValue(field, msgKey, null, null);
    }
    
    /**
     * error 코드와 field 를 직접 지정하며 swap 문자열이 존재 할 경우
     * @param errors
     * @param field
     * @param msgKey
     * @param arguments
     */
    public static void rejectValue(Errors errors, String field, String msgKey, Object[] arguments ){
        errors.rejectValue(field, msgKey, arguments, null);
    }
    
    // ====================================================================================================
    // Number
    // ====================================================================================================
    
    /**
     * 숫자형 데이터인지 체크한다.
     * @param errors
     * @param field
     * @param msgKey
     */
    public static void rejectIsNumber(Errors errors, String field, String msgKey) {
        try {
            String value = getString(errors, field);

            Pattern pattern = Pattern.compile("[^\\d]");
            Matcher matcher = pattern.matcher(value);
            
            if (matcher.find()) {
                rejectValue(errors, field, msgKey);
            }
        } catch (Exception e) {
            rejectValue(errors, field, msgKey);
        }
    }
    
    /**
     * email형식인지 체크한다.
     * @param errors
     * @param field
     * @param msgKey
     */
    public static void rejectIsEmail(Errors errors, String field, String msgKey) {
        try {
            String value = getString(errors, field);

            Pattern pattern = Pattern.compile(PatternEnum.EMAIL_PATTERN.getText());
            Matcher matcher = pattern.matcher(value);
            
            if (!matcher.find()) {
                rejectValue(errors, field, msgKey);
            }
        } catch (Exception e) {
            rejectValue(errors, field, msgKey);
        }
    }
    
    // ====================================================================================================
    // String
    // ====================================================================================================
    
    /**
     * 문자열의 길이를 체크한다. (maxlength)
     * @param errors
     * @param field
     * @param msgKey
     * @param max
     */
    public static void rejectMaxlength(Errors errors, String field, String msgKey, int max) {
        try {
            String value = getString(errors, field);
            
            if (value.length() > max) {
                rejectValue(errors, field, msgKey);
            }
            
        } catch (Exception e) {
            rejectValue(errors, field, msgKey);
        }
    }
    
    /**
     * 문자열의 길이가 다른지 체크한다.
     * @param errors
     * @param field
     * @param msgKey
     * @param length
     */
    public static void rejectNotEqualslength(Errors errors, String field, String msgKey, int length) {
        try {
            String value = getString(errors, field);
            
            if (value.length() != length) {
                rejectValue(errors, field, msgKey);
            }
        } catch (Exception e) {
            rejectValue(errors, field, msgKey);
        }
    }
}
