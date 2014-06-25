package kr.co.myhub.appframework.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * 
 * file   : AjaxAwareAuthenticationEntryPoint.java
 * date   : 2014. 6. 25.
 * author : jmpark
 * content: 인증 필터 체크(커스텀 필터를 쓰기위한 설정)
 * ref:
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 6. 25.   kbtapjm     최초생성
 */
public class AjaxAwareAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    
    /**
     * Default AJAX request Header
     */
    private String ajaxHaeder = "X-AjaxRequest";

    private AjaxAwareAuthenticationEntryPoint(String loginUrl) {
        super(loginUrl);
    }

    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        
        if (isAjaxRequest(request)) {
            response.sendError(403, "Forbidden");
        } else {
            super.commence(request, response, authException);
        }
    }
    
    /**
     * ajax요청인지 체크
     * @param req
     * @return
     */
    private boolean isAjaxRequest(HttpServletRequest req) {
        return req.getHeader(ajaxHaeder) != null && req.getHeader(ajaxHaeder).equals(Boolean.TRUE.toString());
    }
}
