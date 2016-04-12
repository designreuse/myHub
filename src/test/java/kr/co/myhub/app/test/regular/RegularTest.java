package kr.co.myhub.app.test.regular;

import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * ----------------------------------------------------------
 * @Class Name : RegularTest.java
 * @Description : 
 * @Version : 1.0
 * @author : pjm
 * ----------------------------------------------------------
 * DATA           AUTHOR   DESCRIPTION
 * ----------------------------------------------------------
 * 2016. 4. 12.   pjm       최초작성
 * 
 * ----------------------------------------------------------
 *
 */
public class RegularTest {

    /**
     * <pre>
     * 문자열시작테스트 ^
     *
     * <pre>
     */
    @Test
    public void 문자열시작테스트() {
        String regex = "^[a-zA-Z]{3}[0-9]+";
        assertThat(true, is(matched(regex, "aAb09")));
        assertThat(true, is(matched(regex, "aaa09")));
        assertThat(true, is(matched(regex, "AAA09")));
        assertThat(true, is(matched(regex, "zzZ09")));
        assertThat(true, is(matched(regex, "zzZ00")));

        assertThat(false, is(matched(regex, "zzZ")));
        assertThat(false, is(matched(regex, "12a")));
        assertThat(false, is(matched(regex, "aaaa09")));
    }

    /**
     * <pre>
     * 문자열의종료 $
     *
     * <pre>
     */
    @Test
    public void 문자열의종료() {
        String regex = "a{2}$";
        assertThat(true, is(matched(regex, "aa")));

        assertThat(false, is(matched(regex, "aa11")));
    }

    /**
     * <pre>
     * 임의의한문자를나타냄 .
     *
     * <pre>
     */
    @Test
    public void 임의의한문자를나타냄() {
        String regex = "aaa.bb";
        assertThat(true, is(matched(regex, "aaa1bb")));
        assertThat(true, is(matched(regex, "aaaCbb")));
        assertThat(true, is(matched(regex, "aaaZbb")));
        assertThat(true, is(matched(regex, "aaa!bb")));
        assertThat(true, is(matched(regex, "aaa@bb")));
        assertThat(true, is(matched(regex, "aaa#bb")));
        assertThat(true, is(matched(regex, "aaa$bb")));
        assertThat(true, is(matched(regex, "aaa%bb")));
        assertThat(true, is(matched(regex, "aaa^bb")));
        assertThat(true, is(matched(regex, "aaa&bb")));
        assertThat(true, is(matched(regex, "aaa*bb")));
        assertThat(true, is(matched(regex, "aaa(bb")));
        assertThat(true, is(matched(regex, "aaa)bb")));
        assertThat(true, is(matched(regex, "aaa-bb")));
        assertThat(true, is(matched(regex, "aaa+bb")));
        assertThat(true, is(matched(regex, "aaa=bb")));

        assertThat(false, is(matched(regex, "aaabb")));
        assertThat(false, is(matched(regex, "aaa\bb")));
        assertThat(false, is(matched(regex, "aaa12bb")));
        assertThat(false, is(matched(regex, "aaa$$bb")));
    }

    /**
     * <pre>
     * 앞문자가없을수도무한정있을수도있음 *
     *
     * <pre>
     */
    @Test
    public void 앞문자가없을수도무한정있을수도있음() {
        String regex = "a*b";

        assertThat(true, is(matched(regex, "ab")));
        assertThat(true, is(matched(regex, "aaab")));
        assertThat(true, is(matched(regex, "aab")));
        assertThat(true, is(matched(regex, "b")));

        assertThat(false, is(matched(regex, "aabb")));
        assertThat(false, is(matched(regex, "aaccdd")));
        assertThat(false, is(matched(regex, "a_ab")));
        assertThat(false, is(matched(regex, "abcd")));
    }

    /**
     * <pre>
     * 앞문자가하나이상 +
     *
     * <pre>
     */
    @Test
    public void 앞문자가하나이상() {
        String regex = "abc_A+";

        assertThat(true, is(matched(regex, "abc_A")));
        assertThat(true, is(matched(regex, "abc_AA")));
        assertThat(true, is(matched(regex, "abc_AAA")));

        assertThat(false, is(matched(regex, "abc")));
        assertThat(false, is(matched(regex, "abc_")));
        assertThat(false, is(matched(regex, "abc_AAb")));
        assertThat(false, is(matched(regex, "abc_AA1")));
    }

    /**
     * <pre>
     * 앞문자가없거나하나있음 ?
     *
     * <pre>
     */
    @Test
    public void 앞문자가없거나하나있음() {
        String regex = "abc_B?";

        assertThat(true, is(matched(regex, "abc_")));
        assertThat(true, is(matched(regex, "abc_B")));

        assertThat(false, is(matched(regex, "abc_C")));
        assertThat(false, is(matched(regex, "abc_DD")));
        assertThat(false, is(matched(regex, "abc_11")));
    }

    /**
     * <pre>
     * 문자클래스지정1 []
     *
     * <pre>
     */
    @Test
    public void 문자클래스지정1() {
        String regex = "[a-z0-9]";

        assertThat(true, is(matched(regex, "a")));
        assertThat(true, is(matched(regex, "b")));
        assertThat(true, is(matched(regex, "z")));
        assertThat(true, is(matched(regex, "0")));
        assertThat(true, is(matched(regex, "8")));

        assertThat(false, is(matched(regex, "A")));
        assertThat(false, is(matched(regex, "aa")));
        assertThat(false, is(matched(regex, "a0")));
        assertThat(false, is(matched(regex, "00a")));
    }

    /**
     * <pre>
     * 문자클래스지정2 [^]
     *
     * <pre>
     */
    @Test
    public void 문자클래스지정2() {
        String regex = "[^a-f1-3]";

        assertThat(true, is(matched(regex, "g")));
        assertThat(true, is(matched(regex, "h")));
        assertThat(true, is(matched(regex, "4")));
        assertThat(true, is(matched(regex, "0")));
        assertThat(true, is(matched(regex, "9")));

        assertThat(false, is(matched(regex, "a")));
        assertThat(false, is(matched(regex, "2")));
    }

    /**
     * <pre>
     * 횟수또는범위1 {3}
     * a 3개만 올 수 있음
     *
     * <pre>
     */
    @Test
    public void 횟수또는범위1() {
        String regex = "a{3}b";

        assertThat(true, is(matched(regex, "aaab")));

        assertThat(false, is(matched(regex, "aab")));
        assertThat(false, is(matched(regex, "aaaab")));
        assertThat(false, is(matched(regex, "aaaaaaab")));
        assertThat(false, is(matched(regex, "aaaaaaabb")));
    }

    /**
     * <pre>
     * 횟수또는범위2 {3,}
     * a 3개 이상 올 수 있음
     * <pre>
     */
    @Test
    public void 횟수또는범위2() {
        String regex = "a{3,}b";

        assertThat(true, is(matched(regex, "aaab")));
        assertThat(true, is(matched(regex, "aaaab")));
        assertThat(true, is(matched(regex, "aaaaab")));
        assertThat(true, is(matched(regex, "aaaaaaaaaaaaaaaaaaaaaaaaab")));

        assertThat(false, is(matched(regex, "aab")));
        assertThat(false, is(matched(regex, "ab")));
        assertThat(false, is(matched(regex, "b")));
    }

    /**
     * <pre>
     * 횟수또는범위3 {3,5}
     *  a 3개 이상 5개 이하 올 수 있음
     * <pre>
     */
    @Test
    public void 횟수또는범위3() {
        String regex = "a{3,5}b";

        assertThat(true, is(matched(regex, "aaab")));
        assertThat(true, is(matched(regex, "aaaab")));
        assertThat(true, is(matched(regex, "aaaaab")));

        assertThat(false, is(matched(regex, "aaaaaab")));
        assertThat(false, is(matched(regex, "aaaaaaaaaaab")));

        String regex1 = "a{3,5}.";
        assertThat(true, is(matched(regex1, "aaab")));
        assertThat(false, is(matched(regex1, "aaabb")));

        String regex2 = "a{3,5}.{4}";
        assertThat(true, is(matched(regex2, "aaabbbb")));
        assertThat(false, is(matched(regex2, "aaabbbbb")));
        assertThat(false, is(matched(regex2, "aaab")));
        assertThat(false, is(matched(regex2, "aaabb")));
        assertThat(false, is(matched(regex2, "aaabbb")));
    }

    /**
     * <pre>
     * 소괄호문자를하나의문자로인식 ()
     *
     * <pre>
     */
    @Test
    public void 소괄호문자를하나의문자로인식() {
        String regex = "abc(11){2}";

        assertThat(true, is(matched(regex, "abc1111")));

        assertThat(false, is(matched(regex, "abc11")));
        assertThat(false, is(matched(regex, "ab11")));
        assertThat(false, is(matched(regex, "ab111111")));
    }

    /**
     * <pre>
     * or연산1 |
     *
     * <pre>
     */
    @Test
    public void or연산() {
        String regex = "hello|world";
        assertThat(true, is(matched(regex, "hello")));
        assertThat(true, is(matched(regex, "world")));
        assertThat(false, is(matched(regex, "helloworld")));
        assertThat(false, is(matched(regex, "hello|world")));

        String regex1 = "(hello|world){1}";
        assertThat(true, is(matched(regex1, "hello")));
        assertThat(true, is(matched(regex1, "world")));
        assertThat(false, is(matched(regex1, "helloworld")));
    }

    /**
     * <pre>
     * 알파벳이나숫자 \w
     *
     * <pre>
     */
    @Test
    public void 알파벳이나숫자() {
        String regex = "\\w";
        assertThat(true, is(matched(regex, "a")));
        assertThat(true, is(matched(regex, "Z")));
        assertThat(true, is(matched(regex, "4")));

        assertThat(false, is(matched(regex, "$")));
        assertThat(false, is(matched(regex, "남")));
        assertThat(false, is(matched(regex, "12")));
    }

    /**
     * <pre>
     * 알파벳이나숫자를제외한문자 \W
     *
     * <pre>
     */
    @Test
    public void 알파벳이나숫자를제외한문자() {
        String regex = "\\W";
        assertThat(true, is(matched(regex, "@")));
        assertThat(true, is(matched(regex, ")")));
        assertThat(true, is(matched(regex, "\\")));
        assertThat(false, is(matched(regex, "1")));
        assertThat(false, is(matched(regex, "a")));

        String regex1 = "\\W{3}";
        assertThat(true, is(matched(regex1, "!@#")));
        assertThat(true, is(matched(regex1, ")(*")));
    }

    /**
     * <pre>
     * 숫자만 \d
     *
     * <pre>
     */
    @Test
    public void 숫자만() {
        String regex = "\\d";
        assertThat(true, is(matched(regex, "1")));
        assertThat(true, is(matched(regex, "3")));
        assertThat(true, is(matched(regex, "4")));

        assertThat(false, is(matched(regex, "a")));
        assertThat(false, is(matched(regex, "Z")));
        assertThat(false, is(matched(regex, "11")));
    }

    /**
     * <pre>
     * 숫자를제외한모든문자 \\D
     *
     * <pre>
     */
    @Test
    public void 숫자를제외한모든문자() {
        String regex = "\\D";
        assertThat(true, is(matched(regex, "a")));
        assertThat(true, is(matched(regex, "한")));
        assertThat(true, is(matched(regex, "규")));

        assertThat(false, is(matched(regex, "1")));
        assertThat(false, is(matched(regex, "3")));
        assertThat(false, is(matched(regex, "44")));
    }

    /**
     * <pre>
     * 역슬래스테스트 \
     *
     * <pre>
     */
    @Test
    public void 역슬래스테스트() {
        String regex = "a\\sb";
        assertThat(true, is(matched(regex, "a b")));

        String regex1 = "a\\tb";
        assertThat(true, is(matched(regex1, "a  b")));

        String regex2 = "a\\nb";
        assertThat(true, is(matched(regex2, "a\nb")));
    }

    /**
     * <pre>
     * 대소문자구분안함 (?i)
     *
     * <pre>
     */
    @Test
    public void 대소문자구분안함() {
        String regex = "(?i)abc";
        assertThat(true, is(matched(regex, "abc")));
        assertThat(true, is(matched(regex, "ABC")));
        assertThat(true, is(matched(regex, "aBc")));

        String regex1 = "abc";
        assertThat(false, is(matched(regex1, "ABC")));
        assertThat(false, is(matched(regex1, "abC")));
        assertThat(false, is(matched(regex1, "aBc")));
    }

    @Test
    public void 특수문자테스트() {
        String regex = "^[a-z0-9A-Z]*@#";
        assertThat(true, is(matched(regex, "12@#")));
    }

    /**
     * <pre>
     * 영어숫자만
     *
     * <pre>
     */
    @Test
    public void 영어숫자만() {
        String regex = "^[0-9a-zA-Z]*$";
        assertThat(true, is(matched(regex, "1212dafda")));
        assertThat(true, is(matched(regex, "1")));
        assertThat(true, is(matched(regex, "a")));
        assertThat(true, is(matched(regex, "Z")));
        assertThat(true, is(matched(regex, "")));

        assertThat(false, is(matched(regex, "한")));
    }

    /**
     * <pre>
     * 한글만
     *
     * <pre>
     */
    @Test
    public void 한글만() {
        String regex = "^[가-힣]*$";
        assertThat(true, is(matched(regex, "가")));
        assertThat(true, is(matched(regex, "가나다")));
        assertThat(true, is(matched(regex, "가나다라하햐호호유")));
        assertThat(true, is(matched(regex, "뷁")));
        assertThat(true, is(matched(regex, "힔")));

        assertThat(false, is(matched(regex, "1")));
        assertThat(false, is(matched(regex, "a")));
        assertThat(false, is(matched(regex, "Z")));
    }

    /**
     * <pre>
     * replaceAllTest
     * 한글 삭제
     * <pre>
     */
    @Test
    public void replaceAllTest() {
        String value = "abc한123";
        String hangulDeleteValue = value.replaceAll("[가-힣]*", "");
        assertThat(true, is(matched(hangulDeleteValue, "abc123")));
    }

    /**
     * <pre>
     * 이메일체크
     *
     * <pre>
     */
    @Test
    public void 이메일체크() {
        String emailRegex = "^[a-z0-9A-Z_-]*@[a-z0-9A-Z]*.[a-zA-Z.]*$";
        assertThat(true, is(matched(emailRegex, "lng1982@naver.com")));
        assertThat(true, is(matched(emailRegex, "lng1982@naver.co.kr")));
        assertThat(true, is(matched(emailRegex, "lng1982@daum.net")));
        assertThat(true, is(matched(emailRegex, "lng1982@test.org")));

        assertThat(false, is(matched(emailRegex, "lng1982$@naver.com")));
        assertThat(false, is(matched(emailRegex, "lng1982naver.com")));
        assertThat(false, is(matched(emailRegex, "lng1982naver.com12")));
    }

    /**
     * <pre>
     * 휴대폰체크
     *
     * <pre>
     */
    @Test
    public void 휴대폰체크() {
        String phoneRegex = "^0\\d{2}";
        assertThat(true, is(matched(phoneRegex, "010")));
        assertThat(true, is(matched(phoneRegex, "011")));
        assertThat(true, is(matched(phoneRegex, "019")));
        assertThat(false, is(matched(phoneRegex, "111")));
        assertThat(false, is(matched(phoneRegex, "11a")));

        String phoneRegex1 = "^0\\d{2}\\d{3,4}";
        assertThat(true, is(matched(phoneRegex1, "010866")));
        assertThat(true, is(matched(phoneRegex1, "0108667")));
        assertThat(false, is(matched(phoneRegex1, "01086677")));
        assertThat(false, is(matched(phoneRegex1, "010866771")));

        String phoneRegex2 = "^0\\d{2}\\d{3,4}\\d{4}$";
        assertThat(true, is(matched(phoneRegex2, "01086677100")));
        assertThat(true, is(matched(phoneRegex2, "01086677101")));
        assertThat(true, is(matched(phoneRegex2, "0108667710")));
        assertThat(false, is(matched(phoneRegex2, "010866771000")));
        assertThat(false, is(matched(phoneRegex2, "01086677100a")));
        assertThat(false, is(matched(phoneRegex2, "11086677100a")));
    }

    /**
     * <pre>
     * 주민등록번호
     *
     * <pre>
     */
    @Test
    public void 주민등록번호() {
        String regex = "^\\d{6}(1|2|3|4)";
        assertThat(true, is(matched(regex, "8210111")));
        assertThat(true, is(matched(regex, "8210112")));
        assertThat(true, is(matched(regex, "8210113")));
        assertThat(true, is(matched(regex, "8210114")));
        assertThat(false, is(matched(regex, "8210115")));
        assertThat(false, is(matched(regex, "82101151")));

        String regex1 = "^\\d{6}(1|2|3|4)\\d{6}$";
        assertThat(true, is(matched(regex1, "8210201111111")));
        assertThat(true, is(matched(regex1, "8210202222222")));
        assertThat(true, is(matched(regex1, "8210203333333")));
        assertThat(true, is(matched(regex1, "8210204444444")));
        assertThat(false, is(matched(regex1, "8210205555555")));
        assertThat(false, is(matched(regex1, "82102055555551")));
        assertThat(false, is(matched(regex1, "8210205a55551")));
        assertThat(false, is(matched(regex1, "8210205\55551")));

        String regex2 = "^\\d{6}[1-4]\\d{6}$";
        assertThat(true, is(matched(regex2, "8210201111111")));
        assertThat(true, is(matched(regex2, "8210202111111")));
        assertThat(true, is(matched(regex2, "8210203111111")));
        assertThat(true, is(matched(regex2, "8210204111111")));
    }

    /**
     * <pre>
     * 아이피체크
     *
     * <pre>
     */
    @Test
    public void 아이피체크() {
        String regex = "^\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}$";
        assertThat(true, is(matched(regex, "172.18.167.92")));
        assertThat(true, is(matched(regex, "172.181.167.921")));
        assertThat(true, is(matched(regex, "1.181.167.921")));
        assertThat(true, is(matched(regex, "1.1.167.921")));
        assertThat(true, is(matched(regex, "1.1.7.921")));
        assertThat(true, is(matched(regex, "1.1.7.9")));

        assertThat(false, is(matched(regex, "1721.181.167.921")));
        assertThat(false, is(matched(regex, "172.1811.167.921")));
        assertThat(false, is(matched(regex, "172.181.1671.921")));
        assertThat(false, is(matched(regex, "172.181.167.9211")));
    }

    /**
     * <pre>
     * 파일확장자체크
     *
     * <pre>
     */
    @Test
    public void 파일확장자체크() {
        String regex = "^\\S+.(?i)(png|jpg|bmp|gif)$";
        assertThat(true, is(matched(regex, "test.png")));
        assertThat(true, is(matched(regex, "test.jpg")));
        assertThat(true, is(matched(regex, "test.bmp")));
        assertThat(true, is(matched(regex, "test.gif")));
        assertThat(true, is(matched(regex, "test1#$adf한.gif")));
        assertThat(true, is(matched(regex, "test.GIF")));
        assertThat(true, is(matched(regex, "test.JPG")));
        assertThat(true, is(matched(regex, "test.BMP")));
        assertThat(true, is(matched(regex, "test.PNG")));
        assertThat(true, is(matched(regex, "testA.PNG")));

        assertThat(false, is(matched(regex, "test a.gif")));
        assertThat(false, is(matched(regex, "test a.jpg")));


    }

    @Test
    public void 비밀번호체크() {
        String regex = "^[a-zA-Z0-9~!@#$%^&*()]{8,16}";
        assertThat(true, is(matched(regex, "adfadf1212")));
        assertThat(true, is(matched(regex, "a23d3f31")));
        assertThat(true, is(matched(regex, "rbdn200111111111")));
        assertThat(true, is(matched(regex, "aaaaaaaaaa212121")));
        assertThat(true, is(matched(regex, "aBdAf1234")));
        assertThat(true, is(matched(regex, "abc~!@#$234")));
        assertThat(true, is(matched(regex, "~!@#$%^&*()")));
        assertThat(true, is(matched(regex, "1asfdA~!@af^&*()")));

        assertThat(false, is(matched(regex, "a")));
        assertThat(false, is(matched(regex, "a121212")));
        assertThat(false, is(matched(regex, "a121212a121212a121212")));
    }

    @Test
    public void 비밀번호체크2() {
        String regex = "^([a-zA-Z]+[0-9]+[~!@#$%^&*()]+)";
        assertThat(true, is(matched(regex, "a0@")));
        assertThat(false, is(matched(regex, "a@")));
        assertThat(false, is(matched(regex, "@1")));
    }
    
    /**
     * <pre>
     * groupingTest
     * hello kyu world 문자열을 hello namkyu world 로 바꾸기
     * 간단하게 hello와 world를 그룹으로 지정하기 위해서 괄호로 묶어서 아래와 같이 처리
     * <pre>
     */
    @Test
    public void groupingTest() {
        String result = "hello kyu world".replaceAll("(hello) kyu (world)", "$1 namkyu $2");
        assertThat("hello namkyu world", is(result));
    }

    /**
     * <pre>
     * nonGroupingTest
     * ?: <-- 이 문자열이 non grouping 문자
     * hello kyu world 문자열에서 양쪽의 hello, world를 삭제한 kyu만 출력
     * <pre>
     */
    @Test
    public void nonGroupingTest() {
        String result = "hello kyu world".replaceAll("(?:hello) (kyu) (?:world)", "$1");
        assertThat("kyu", is(result));
    }

    /**
     * <pre>
     * matched
     *
     * <pre>
     * @param regex
     * @param inputTxt
     * @return
     */
    private boolean matched(String regex, String inputTxt) {
        return Pattern.matches(regex, inputTxt);
    }
}
