package kr.co.myhub.appframework.exception;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;

/**
 * 
 * file   : MyHubException.java
 * date   : 2014. 5. 16.
 * author : jmpark
 * content: 애플리케이션 예외처리 클래스 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 5. 16.   kbtapjm     최초생성
 */
public class MyHubException extends Exception {
    
    private static final long serialVersionUID = 6655460570753239940L;
    
    /* 접근 거부 에러 */ 
    public static final String ACCESS_DENIED = "Access is denied";
    
    /* 에러코드 */
    private String errCd;
    
    /* 에러메시지 */
    private String errMsg;
    
    /* 인수 */
    private Object[] arguments;

    private MyHubException(String errCd) {
        super();
        this.errCd = errCd;
    }

    private MyHubException(String errCd, String errMsg) {
        super(errMsg);
        this.errCd = errCd;
        this.errMsg = errMsg;
    }

    private MyHubException(String errCd, String errMsg, Object[] arguments) {
        super(errMsg);
        this.errCd = errCd;
        this.errMsg = errMsg;
        this.arguments = arguments;
    }

    private MyHubException() {
        super();
    }
    
    private MyHubException(String message, Throwable cause) {
        super(message, cause);
    }

    private MyHubException(Throwable cause) {
        super(cause);
    }

    private MyHubException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getErrCd() {
        return errCd;
    }

    public void setErrCd(String errCd) {
        this.errCd = errCd;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }
    
    /**
     * 에러 메시지 처리
     * @param e
     * @param msa
     * @param locale
     * @return
     */
    public static String getExceptionMsg(Exception e, MessageSourceAccessor msa, Locale locale) {
        if (e == null) return ""; 
        
        String msg = "";
        
        if (e.getMessage().indexOf(MyHubException.ACCESS_DENIED) != -1) {
            msg = msa.getMessage("myhub.error.security.accessdenied", locale);
        } else {
            msg  = e.getMessage();
        }
        
        return msg;
    }

}
