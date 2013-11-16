package kr.co.myhub.appframework.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.StringUtils;

/**
 * 
 * file   : EncryptionUtil.java
 * date   : 2013. 11. 16.
 * author : jmpark
 * content: SHA-256 (Sechre Hash Standard) 
 * 1) 160bit 의 해쉬를 제공
 * 2) SHA(Secure Hash Algorithm, 안전한 해시 알고리즘) 
 * 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 16.   kbtapjm     최초생성
 */
public class EncryptionUtil {

    /**
     * 암호화(SHA-256 : 복호화가 불가능한 hash값으로 암호화 처리)
     * @param 암호화 처리 할 문자
     * @return hash값으로 암호화된 문자열
     */
    public static String getEncryptStr(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        
        String encryptStr = "";
        
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            StringBuilder sb = new StringBuilder();
            
            for (int i = 0 ; i < byteData.length ; i++) {
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            encryptStr = sb.toString();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        
        return encryptStr;
    }

}
