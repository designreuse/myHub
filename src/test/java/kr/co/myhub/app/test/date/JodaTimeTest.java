package kr.co.myhub.app.test.date;

import kr.co.myhub.app.common.TestConfig;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import org.joda.time.chrono.GJChronology;
import org.joda.time.chrono.GregorianChronology;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * file   : JodaTimeTest.java
 * date   : 2014. 9. 5.
 * author : jmpark
 * content: Joda-Time으로 날짜 연산
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 9. 5.   jmpark     최초생성
 */
public class JodaTimeTest extends TestConfig {

    @Test  // 예제1, 2: 1일 후 구하기
    @Ignore
    public void shouldGetAfterOneDay() {
        Chronology chrono = GregorianChronology.getInstance();
        LocalDate theDay = new LocalDate(1582, 10, 4, chrono);
        String pattern = "yyyy.MM.dd";
        System.out.println("theDay : " + theDay.toString(pattern));
                        
        LocalDate nextDay = theDay.plusDays(1);
        System.out.println("nextDay : " + nextDay.toString(pattern));
    }

    @Test  // 예제2, 2: 1일 후 구하기.
    @Ignore
    public void shouldGetAfterOneDayWithGJChronology() {
        Chronology chrono = GJChronology.getInstance();
        LocalDate theDay = new LocalDate(1582, 10, 4, chrono);
        String pattern = "yyyy.MM.dd";
        System.out.println("theDay : " + theDay.toString(pattern));
        
        LocalDate nextDay = theDay.plusDays(1);
        System.out.println("nextDay : " + nextDay.toString(pattern));
    }

    @Test  // 예제3, 4: 1시간 후 구하기
    @Ignore
    public void shouldGetAfterOneHour() {
        DateTimeZone seoul = DateTimeZone.forID("Asia/Seoul");
        DateTime theTime = new DateTime(1988,5,7,23,0, seoul);
        String pattern = "yyyy.MM.dd HH:mm";
        System.out.println("theTime : " + theTime.toString(pattern));
        System.out.println("seoul : " + seoul.isStandardOffset(theTime.getMillis()));
                
        DateTime after1Hour = theTime.plusHours(1);
        System.out.println("after1Hour : " + after1Hour.toString(pattern));
        System.out.println("seoul : " + seoul.isStandardOffset(after1Hour.getMillis()));
    }
                
    @Test  // 예제 5, 6: 1분 후 구하기
    @Ignore
    public void shouldGetAfterOneMinute() {
        DateTimeZone seoul = DateTimeZone.forID("Asia/Seoul");
        DateTime theTime = new DateTime(1961, 8, 9, 23, 59, seoul);
        String pattern = "yyyy.MM.dd HH:mm";
        System.out.println("theTime : " + theTime.toString(pattern));
                        
        DateTime after1Minute = theTime.plusMinutes(1);
        System.out.println("after1Minute : " + after1Minute.toString(pattern));
    }


    @Test  // 예제 7: 2초 후 구하기
    @Ignore
    public void shouldGetAfterTwoSecond() {
        DateTimeZone utc = DateTimeZone.forID("UTC");
        DateTime theTime = new DateTime(2012, 6, 30, 23, 59, 59, utc);
        String pattern = "yyyy.MM.dd HH:mm:ss";
        System.out.println("theTime : " + theTime.toString(pattern));
                        
        DateTime after2Seconds = theTime.plusSeconds(2);
        System.out.println("after2Seconds : " + after2Seconds.toString(pattern));
   }


    @Test  // 예제 12: 1999년 12월 31일을 지정하는 코드 
    @Ignore
    public void shouldGetDate() {
        LocalDate theDay = new LocalDate(1999, 12, 31);
        
        System.out.println("getYear : " + theDay.getYear());
        System.out.println("getMonthOfYear : " + theDay.getMonthOfYear());
        System.out.println("getDayOfMonth : " + theDay.getDayOfMonth());
    }

    @Test (expected = IllegalFieldValueException.class) // 예제 12 : 1999년 12월 31일을 지정하는 코드의 실수
    @Ignore
    public void shouldNotAcceptWrongMonth() {
        new LocalDate(1999, 13, 31);
    }

    @Test // 예제 13: 요일 확인하기
    @Ignore
    public void shouldGetDayOfWeek() {
        LocalDate theDay = new LocalDate(2014, 1, 1);
                        
        int dayOfWeek = theDay.getDayOfWeek();
        System.out.println("dayOfWeek : " + dayOfWeek);
        System.out.println("dayOfWeek : " + (dayOfWeek == DateTimeConstants.WEDNESDAY));
    }

    @Test(expected = IllegalArgumentException.class) // 예제 14: 잘못 지정한 시간대 ID
    public void shouldThrowExceptionWhenWrongTimeZoneId() {
        DateTimeZone.forID("Seoul/Asia");
    }
}
