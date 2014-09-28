package kr.co.myhub.appframework.cache;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.KeyGenerator;

/**
 * 
 * file   : EntityKeyGenerator.java
 * date   : 2014. 9. 24.
 * author : jmpark
 * content: ehcache 키 값을 생성
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 9. 24.   jmpark     최초생성
 */
public class EntityKeyGenerator implements KeyGenerator {
    
    private static final Logger log = LoggerFactory.getLogger(EntityKeyGenerator.class);
    
    public static final int NO_PARAM_KEY = 0;
    public static final int NULL_PARAM_KEY = 53;

    @Override
    public Object generate(Object target, Method method, Object... params) {
        log.debug("=======================================================");
        log.debug("target : {}", target);
        log.debug("method : {}", method);
        log.debug("params : {}", params.length);
        log.debug("=======================================================");
        
        if (params.length == 1) {
            log.debug("params[0] : {}", params[0]);
            
            return (params[0] == null ? NULL_PARAM_KEY : params[0]);
        }
        if (params.length == 0) {
            return NO_PARAM_KEY;
        }
        int hashCode = 17;
        for (Object object : params) {
            log.debug("object : {}", object.hashCode());
            log.debug("object : {}", object.toString());
            
            hashCode = 31 * hashCode + 
                            (object == null ? NULL_PARAM_KEY : object.hashCode());
        }
        
        log.debug("hashCode : {}", hashCode);
        
        return Integer.valueOf(hashCode);
    }
    
    

}
