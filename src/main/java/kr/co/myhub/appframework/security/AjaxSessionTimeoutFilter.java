package kr.co.myhub.appframework.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

/**
 * 
 * file   : AjaxSessionTimeoutFilter.java
 * date   : 2014. 6. 25.
 * author : jmpark
 * content: ajax통신에 대한 세션 타임아웃 체크
 * ref:
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 6. 25.   kbtapjm     최초생성
 */
public class AjaxSessionTimeoutFilter implements Filter {
    
    private static final Logger log = LoggerFactory.getLogger(AjaxSessionTimeoutFilter.class);

    /**
     * Default AJAX request Header
     */
    private String ajaxHaeder = "X-AjaxRequest";
    
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        log.debug("AjaxSessionTimeoutFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        if (isAjaxRequest(req)) {
            try {
                chain.doFilter(req, res);
            } catch (AccessDeniedException e) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
            } catch (AuthenticationException e) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            chain.doFilter(req, res);
        }
    }
    
    @Override
    public void destroy() {
        log.debug("AjaxSessionTimeoutFilter destroy");
    }
    
    /**
     * ajax요청인지 체크
     * @param req
     * @return
     */
    private boolean isAjaxRequest(HttpServletRequest req) {
        return req.getHeader(ajaxHaeder) != null && req.getHeader(ajaxHaeder).equals(Boolean.TRUE.toString());
    }
    
    /**
     * Set AJAX Request Header (Default is AJAX)
     * @param ajaxHeader
     */
    public void setAjaxHaeder(String ajaxHeader) {
        this.ajaxHaeder = ajaxHeader;
    }
}
