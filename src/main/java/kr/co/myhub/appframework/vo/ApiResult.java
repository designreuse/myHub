package kr.co.myhub.appframework.vo;

import javax.xml.bind.annotation.XmlRootElement;

import kr.co.myhub.appframework.constant.StatusEnum;

/**
 * 
 * file   : ApiResult.java
 * date   : 2013. 11. 19.
 * author : jmpark
 * content: API 결과 담는 Value Object 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 19.   kbtapjm     최초생성
 */
@XmlRootElement
public class ApiResult {
    
    /* 상태  */
    private StatusEnum status;
    
    /* 메시지 */
    private String message;
    
    /* 결과 */
    private Object data;

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
