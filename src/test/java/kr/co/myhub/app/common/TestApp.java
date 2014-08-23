package kr.co.myhub.app.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


/**
 * 
 * file   : TestApp.java
 * date   : 2014. 8. 17.
 * author : jmpark
 * content: 애플리케이션 테스트 
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 8. 17.   jmpark     최초생성
 */
public class TestApp {
    
    public static void main(String[] args) throws Exception  {
        TestApp testApp = new TestApp();
        /*int result = testApp.recursionTest();
        
        String resultCd = "";
        if (result == 0) {
            resultCd = "0000";
        } else {
            resultCd = "9999";
        }
        System.out.println("resultCd : " + resultCd);*/
        
        
        
        
        
    }
    
    /**
     * 파일쓰기
     */
    public void fileWrite() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("C://dev//temp//out.txt"));
            String s = "출력 파일에 저장될 이런 저런 문자열입니다.";

            out.write(s); out.newLine();
            out.write(s); out.newLine();

            out.close();
          } catch (IOException e) {
              System.err.println(e); // 에러가 있다면 메시지 출력
              System.exit(1);
          }
    }
    
    /**
     * 재귀 테스트
     */
    public int recursionTest() {
        
        // 템플릿 데이터 조회 
        List<String> templMain = new ArrayList<String>();
        List<String> templImg = new ArrayList<String>();
        List<String> templStr = new ArrayList<String>();
        List<String> templMsg = new ArrayList<String>();
        
        // 총카운트
        int count = 3000000;
        
        return this.recursionApp(templMain, count);
    }
    
    /**
     * 
     * @param templMain
     * @param count
     * @return
     */
    public int recursionApp(List<String> templMain , int count) {
        
        /* 청구서 조회(100개) */
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        
        /**
         * 100개씩 청구서 생성
         */
        for (String str : list) {
            
            /* 건단위로 예외 처리 */
            try {
                Thread.sleep(100);
                
                /* 청구서 생성(이미지) */
                // 이미지 생성이 실패면 ==> 예외(0)
                
                /* 청구서 생성(문자) */
                
                /* 생성이 성공이면 결과 테이블과 SOAP테이블에 등록  ==> 이거 두개는 하나의 트랜잭션으로 */
                
                // 하나의 청구서 생성이 성공이면 전체 카운트에서 다운
                count = count - 1;
                
                System.out.println(" =============================================  ");
                System.out.println("count : " + count);
                System.out.println(" =============================================  ");
            } catch(Exception e) {
                e.printStackTrace();
                
                // 예외코드가 0이면 이미지 실패, 0이 아닐 경우는 문자 생성시 실패 ==> 생성된 이미지 삭제
                
                continue;
            }
        }
        
        // 청구서가 모두 생성이 되었으면 0을 리턴 아니면 재호출
        if (count == 0) {
            return count;
        } else {
            return recursionApp(templMain, count);
        }
    }
}
