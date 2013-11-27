package kr.co.myhub.appframework.vo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import kr.co.myhub.app.user.domain.User;
import kr.co.myhub.appframework.constant.StatusEnum;

/**
 * 
 * file   : ApiResponse.java
 * date   : 2013. 11. 27.
 * author : jmpark
 * content: API 리스트형식의 JSON,XML 응답 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 27.   kbtapjm     최초생성
 */
@XmlRootElement(name = "result")
@XmlAccessorType (XmlAccessType.FIELD)
public class ApiResponse {

    /* 상태  */
    private StatusEnum status;
    
    /* 메시지 */
    private String message;
    
    /* 결과 */
    private List<User> list = null;

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

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }
}
