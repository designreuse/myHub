package kr.co.myhub.appframework.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * file   : DateUtil.java
 * date   : 2014. 4. 24.
 * author : jmpark
 * content: 공통으로 사용하는 유틸 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 4. 24.   kbtapjm     최초생성
 */
public class DateUtil {

    /**
     * 현재시간을 Timestamp형식으로 반환
     * 
     * Knowledge
     * MYSQL(DateTime 형식의 컬럼일경우에는 타입을 일정하게 맞춰야 시간차이를 계산가능)
     * @return
     */
    public static Timestamp getCurrentDateTimeStamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String today = formatter.format(cal.getTime());
        Timestamp ts = Timestamp.valueOf(today);
        
        return ts;
    }
    
    /**
     * timestamp값을 문자열로 변환
     * @param ts
     * @return
     */
    public static String getTimestampToDate(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        
        Timestamp currentTime = new Timestamp(time); 
        String dateStr = formatter.format(currentTime); 
        
        return dateStr;
        
    }
}
