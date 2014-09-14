package kr.co.myhub.app.test.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import kr.co.myhub.app.common.TestConfig;

import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * file   : OldJdkDateTest.java
 * date   : 2014. 8. 31.
 * author : jmpark
 * content: 날짜 시간 테스트 (Java)
 * http://helloworld.naver.com/helloworld/textyle/645609#note13
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 8. 31.   jmpark     최초생성
 */
public class OldJdkDateTest extends TestConfig {

    /**
     * 1일 후 시간 구하기
     */
    @Test
    @Ignore
    public void shouldGetAfterOneDay() {
        System.out.println(" ========= shouldGetAfterOneDay ==================  ");
        TimeZone utc = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(utc);
        calendar.set(1582, Calendar.OCTOBER , 4);
        String pattern = "yyyy.MM.dd";
        String theDay = toString(calendar, pattern, utc);
        System.out.println("theDay : " + theDay);
 
        calendar.add(Calendar.DATE, 1);
        String nextDay = toString(calendar, pattern, utc);
        System.out.println("nextDay : " + nextDay);
    }
    
    /**
     * 1시간 후 시간 구하기
     */
    @Test
    @Ignore
    public void shouldGetAfterOneHour() {
        System.out.println(" ========= shouldGetAfterOneHour ==================  ");
        
        // 서울 1988년 5월 7일 23시의 1시간 후
        TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
        Calendar calendar = Calendar.getInstance(seoul);
        calendar.set(1988, Calendar.MAY , 7, 23, 0);
        String pattern = "yyyy.MM.dd HH:mm";
        String theTime = toString(calendar, pattern, seoul);
        System.out.println("theTime : " + theTime);

        // 이는 그 시기에 서울에 적용된 일광절약시간제(Daylight Saving Time), 즉 서머타임 때문이다
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        String after1Hour = toString(calendar, pattern, seoul);
        System.out.println("after1Hour : " + after1Hour);
        
        // 서머타임인지 확인 
        System.out.println("inDaylightTime : " + seoul.inDaylightTime(calendar.getTime()));
    }
    
    /**
     * 1분 후의 시간 구하기
     */
    @Test
    @Ignore
    public void shouldGetAfterOneMinute() {
        System.out.println(" ========= shouldGetAfterOneMinute ==================  ");
        
        // 서울 1961년 8월 9일 23시 59분의 1분 후는
        TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
        Calendar calendar = Calendar.getInstance(seoul);
        calendar.set(1961, Calendar.AUGUST, 9, 23, 59);
        String pattern = "yyyy.MM.dd HH:mm";
        String theTime = toString(calendar, pattern, seoul);
        System.out.println("theTime : " + theTime);
        
        // 1961년 8월 10일은 대한민국의 표준시가 UTC+8:30에서 현재와 같은 UTC+9:00로 변경된 시점
        calendar.add(Calendar.MINUTE, 1);
        String after1Minute = toString(calendar, pattern, seoul);
        System.out.println("after1Minute : " + after1Minute);
    }
    
    /**
     * 2초 후 시간 구하기
     */
    @Test
    @Ignore
    public void shouldGetAfterTwoSecond() {
        System.out.println(" ========= shouldGetAfterTwoSecond ==================  ");
        
        // 협정세계시 2012년 6월 30일 23시 59분 59초의 2초 후
        TimeZone utc = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(utc);
        calendar.set(2012, Calendar.JUNE, 30, 23, 59, 59);
        String pattern = "yyyy.MM.dd HH:mm:ss";
        String theTime = toString(calendar, pattern, utc);
        System.out.println("theTime : " + theTime);
        
        // 012년 6월 30일은 가장 최근에 '윤초'가 적용된 때이다. Java에서 윤초가 Calendar 연산에 적용되지 않는다는 것
        calendar.add(Calendar.SECOND, 2);
        String afterTwoSeconds = toString(calendar, pattern, utc);
        System.out.println("afterTwoSeconds : " + afterTwoSeconds);
    }
    
    /**
     * 1999년 12월 31일을 지정하려다 2000년으로 넘어간 코드
     */
    @Test(expected = IllegalArgumentException.class)
    @Ignore
    public void shouldGetDate() {
         Calendar calendar = Calendar.getInstance();
         calendar.setLenient(false);    //  잘못된 월이 지정된 객체에서 IllegalArgumentException을 던져 준다. 
         //calendar.set(1999, 11, 31); // 0 ~ 11 ==> 월을 12로 기록하면 에러 발생
         calendar.set(1999, 12, 31); // 0 ~ 11 ==> 월을 12로 기록하면 에러 발생
         
         System.out.println("YEAR : " + calendar.get(Calendar.YEAR));
         System.out.println("MONTH : " + calendar.get(Calendar.MONTH));
         System.out.println("DAY_OF_MONTH : " + calendar.get(Calendar.DAY_OF_MONTH));
    }
    
    /**
     * 요일 확인하기
     */
    @Test
    @SuppressWarnings("deprecation")
    @Ignore
    public void shouldGetDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.JANUARY, 1);

        // calendar, date 객체의 요일을 지정하는 값은 다름, 일관성이 없음.
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("dayOfWeek : " + dayOfWeek);
        System.out.println("dayOfWeek : " + (dayOfWeek == Calendar.WEDNESDAY));
        
        Date theDate = calendar.getTime();
        System.out.println("theDate : " + theDate.getDay());    // 요일을 구하는 메서드로는 이름이 모호
    }
    
    /**
     * 잘못 지정한 시간대 ID
     */
    @Test
    public void shouldSetGmtWhenWrongTimeZoneId(){
        TimeZone zone = TimeZone.getTimeZone("Seoul/Asia");
        System.out.println("GMT : " + zone.getID());
    }

    /**
     * 형식에 맞는 날짜 데이터 포맷을 반환
     * @param calendar
     * @param pattern
     * @param zone
     * @return
     */
    private String toString(Calendar calendar, String pattern, TimeZone zone) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(zone);
        return format.format(calendar.getTime());
    }
}
