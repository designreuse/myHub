package kr.co.myhub.appframework.util;

/**
 * 
 * file   : CommonUtil.java
 * date   : 2014. 5. 17.
 * author : jmpark
 * content: 공통유틸클래스 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 5. 17.   kbtapjm     최초생성
 */
public class CommonUtil {
    
    public static void main(String args[]) {
        System.out.println(EncryptionUtil.getEncryptPassword(CommonUtil.getTmpPassword()));
    }

    /**
     * 임시 비밀번호 생성
     * @return
     */
    public static String getTmpPassword() {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < 8; i++) {
            // char upperStr = (char)(Math.random() * 26 + 65);
            char lowerStr = (char)(Math.random() * 26 + 97);
            if (i % 2 == 0) {
                sb.append((int)(Math.random() * 10));
            } else {
                sb.append(lowerStr);
            }
        }
        
        return sb.toString();
    }


}
