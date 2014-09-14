package kr.co.myhub.app.test.date;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesException;

import kr.co.myhub.app.common.TestConfig;

import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * file   : Jsr310Test.java
 * date   : 2014. 9. 5.
 * author : jmpark
 * content: JSR-310: 새로운 Java의 날짜 API
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 9. 5.   jmpark     최초생성
 */
public class Jsr310Test extends TestConfig {

    @Test  // 예제 1, 2: 1일 후 구하기
    @Ignore
    public void shouldGetAfterOneDay() {
        LocalDate theDay = IsoChronology.INSTANCE.date(1582, 10, 4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        System.out.println("theDay : " + theDay.format(formatter));
                
        LocalDate nextDay = theDay.plusDays(1);
        System.out.println("nextDay : " + nextDay.format(formatter));
    }
                
    @Test  // 예제 3, 4: 1시간 후 구하기
    @Ignore
    public void shouldGetAfterOneHour() {
        ZoneId seoul = ZoneId.of("Asia/Seoul");
        ZonedDateTime theTime = ZonedDateTime.of(1988, 5, 7, 23, 0, 0, 0, seoul);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        System.out.println("theTime : " + theTime.format(formatter));
        
        ZoneRules seoulRules = seoul.getRules();
        System.out.println("seoulRules : " + seoulRules.isDaylightSavings(Instant.from(theTime)));
        
        ZonedDateTime after1Hour = theTime.plusHours(1);
        System.out.println("after1Hour : " + after1Hour.format(formatter));
        System.out.println("seoulRules : " + seoulRules.isDaylightSavings(Instant.from(after1Hour)));
    }
                
    @Test  // 예제5, 6: 1분 후 구하기
    @Ignore
    public void shouldGetAfterOneMinute() {
         ZoneId seoul = ZoneId.of("Asia/Seoul");
         ZonedDateTime theTime = ZonedDateTime.of(1961, 8, 9, 23, 59, 59, 0, seoul);
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
         System.out.println("theTime : " + theTime.format(formatter));
                        
         ZonedDateTime after1Minute = theTime.plusMinutes(1);
         System.out.println("after1Minute : " + after1Minute.format(formatter));
    }

    @Test // 예제 7: 2초 후 구하기
    @Ignore
    public void shouldGetAfterTwoSecond() {
        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime theTime = ZonedDateTime.of(2012, 6, 30, 23, 59, 59, 0, utc);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        System.out.println("theTime : " + theTime.format(formatter));
                
        ZonedDateTime after2Seconds = theTime.plusSeconds(2);
        System.out.println("after2Seconds : " + after2Seconds.format(formatter));
    }

    @Test // 예제 12: 1999년 12월 31일을 지정하는 코드
    @Ignore
    public void shouldGetDate() {
        LocalDate theDay = LocalDate.of(1999, 12, 31);
        
        System.out.println("getYear : " + theDay.getYear());
        System.out.println("getMonthOfYear : " + theDay.getMonthValue());
        System.out.println("getDayOfMonth : " + theDay.getDayOfMonth());
    }

    @Test(expected = DateTimeException.class) // 예제 12: 1999년 12월 31일을 지정하는 코드의 실수
    @Ignore
    public void shouldNotAcceptWrongDate() {
        LocalDate.of(1999, 13, 31);
    }
                
    @Test // 예제 13: 요일 확인하기
    @Ignore
    public void shouldGetDayOfWeek() {
        LocalDate theDay = LocalDate.of(2014, 1, 1);
                
        DayOfWeek dayOfWeek = theDay.getDayOfWeek();
        System.out.println("dayOfWeek : " + dayOfWeek);
        System.out.println("dayOfWeek : " + (dayOfWeek == DayOfWeek.WEDNESDAY));
    }
    
    @Test(expected = ZoneRulesException.class) // 예제 14: 잘못 지정한 시간대 ID
    public void shouldThrowExceptionWhenWrongTimeZoneId(){
         ZoneId.of("Seoul/Asia");
    }
}
