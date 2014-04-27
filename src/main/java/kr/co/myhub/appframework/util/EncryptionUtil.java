package kr.co.myhub.appframework.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.util.StringUtils;

/**
 * TODO: SHA-512 적용
 * file   : EncryptionUtil.java
 * date   : 2013. 11. 16.
 * author : jmpark
 * content: SHA-256 (Sechre Hash Standard) 
 * 1) 160bit 의 해쉬를 제공
 * 2) SHA(Secure Hash Algorithm, 안전한 해시 알고리즘) 
 * 3) 단방향 해시(복호화가 불가능)
 * 
 * 참조 : http://helloworld.naver.com/helloworld/318732
 * 
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2013. 11. 16.   kbtapjm     최초생성
 */
public class EncryptionUtil {
 
    /**
     * 암호화(SHA-256)
     * @param 암호화 처리 할 문자
     * @return hash값으로 암호화된 문자열
     */
    public static String getEncryptPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            return password;
        }
        
        String encryptStr = "";
        
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(password.getBytes());
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
    
    /**
     * hash 알고리즘 생성 (PBKDF2WithHmacSHA1) 
     * @param password
     * @return
     */
    public static String getEncryptPBKDF2(String password) {
        if (StringUtils.isEmpty(password)) {
            return password;
        } 
        
        // The result - null if any failure
        byte[] pbkdf2_result = null;
        
        // encrypt
        String encryptPassword = "";
       
        try {
            
            // Convert password and salt to character array/byte array
            char[] chars = password.toCharArray();
            byte[] salt = getSalt().getBytes();
            
            // Define number of iterations and output size
            int iterations = 10;
            int result_size = 256;
            
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            
            // PBEKeySpec(char[] password, byte[] salt, int iterationCount, int keyLength) 
            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, result_size);
            
            // Generate the password hash
            SecretKey secretKey = skf.generateSecret(spec);
            
            // Immediately zero the password from memory
            spec.clearPassword();
            
            // Get the resulting byte array of our PBKDF2 hash
            pbkdf2_result = secretKey.getEncoded();
            
            encryptPassword = iterations + ":" + toHex(salt) + ":" + toHex(pbkdf2_result);
                    
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e1) {
            e1.printStackTrace();
        }
        
        return encryptPassword;
    }
    
    /**
     * 암호하 솔트
     * 단방향 해시 함수에서 다이제스트를 생성할 때 추가되는 바이트 단위의 임의의 문자열
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getSalt() throws NoSuchAlgorithmException {
        
        // VERY important to use SecureRandom instead of just Random
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        
        // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        
        return salt.toString();
    }

    /**
     * toHex 코드 생성
     * @param array
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
    
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException  {
        String originalPassword = "qwer1234";
        
        String encryptPBKDF2 = getEncryptPBKDF2(originalPassword);
        String getSha256 = getEncryptPassword(originalPassword);
        
        System.out.println("PBKDF2 : " +  encryptPBKDF2);
        System.out.println("SHA256 : " + getSha256);
    }

}
