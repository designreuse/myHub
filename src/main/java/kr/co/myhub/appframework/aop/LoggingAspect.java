package kr.co.myhub.appframework.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * file   : LoggingAspect.java
 * date   : 2014. 5. 10.
 * author : jmpark
 * content: 파라미터 로그 처리(Spring AOP)
 * 수정내용
 * 참조 : http://openframework.or.kr/framework_reference/spring/ver2.x/html/aop.html
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 5. 10.   kbtapjm     최초생성
 */
@Aspect
@Component
public class LoggingAspect {
    
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     *  결합점(Join points)을 지정하여 충고(Advice)가 언제 실행될지를 지정하는데 사용
     */
    @Pointcut("execution(public * kr.co.myhub..controller..*(..))")
    private void targetMethod() {
        // pointcut annotation 값을 참조하기 위한 dummy method
    }
    
    /**
     * 메소드 실행 전
     * @param joinPoint
     */
    @Before("targetMethod()")
    public void allControllerBefore(JoinPoint joinPoint) {
        log.info("Method Before!");
    }
    
    /**
     * 대상 메소드 수행 전
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "targetMethod()")
    public Object loggingAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("\n");
        log.info("**************************************************************************************************");
        log.info("<aop> hooked method -->: " + ((null == joinPoint.getTarget()) ? "" : joinPoint.getTarget().getClass()) + "." + joinPoint.getSignature().getName() +"()");
        log.info("<aop> hooked arguments : " + Arrays.toString(joinPoint.getArgs()));
        
        Object result = joinPoint.proceed();    //continue on the intercepted method
   
        return result;
    }
    
    /**
     * 메소드 실행 후
     * @param joinPoint
     */
    @After("targetMethod()")
    public void allControllerAfter(JoinPoint joinPoint) {   
        log.info("Method After!");
    }
    
    /**
     * 메소드 실행 후 리턴 값 확인
     * @param joinPoint
     * @param ret
     */
    @AfterReturning(pointcut="execution(public * kr.co.myhub..controller..*(..))", returning="result")
    public void allControllerAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("return value is [" + result.toString() + "]");
        log.info("**************************************************************************************************");
    }
    
    /**
     * 메소드 실행 후 예외 값 확인
     * @param joinPoint
     * @param error
     */
    @AfterThrowing(pointcut = "execution(public * kr.co.myhub..controller..*(..))",throwing= "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.info("logAfterThrowing() is running!");
        log.info("Exception : " + error);
    }

}
