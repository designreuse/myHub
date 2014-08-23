package kr.co.myhub.appframework.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * file   : FileUtil.java
 * date   : 2014. 8. 10.
 * author : jmpark
 * content: 파일 유틸 
 *
 * 수정내용
 * ----------------------------------------------
 * 수정일                   수정자                  수정내용
 * ----------------------------------------------
 * 2014. 8. 10.   jmpark     최초생성
 */
public class FileUtil extends HttpServletRequestWrapper {
    
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    public FileUtil(HttpServletRequest request) {
        super(request);
    }
    
    /**
     * 파일업로드
     * @param multipartFile 파일객체
     * @param uploadPath    업로드 폴더
     * @return
     */
    public static String fileUpload(MultipartFile multipartFile, String uploadPath) {
        if (multipartFile == null || uploadPath.length() == 0) return ""; 
        
        log.debug("multipartFile : {}, {}", multipartFile, uploadPath);
        
        String fileName = "";
        
        try {
            fileName = multipartFile.getOriginalFilename();
            
            /* 파일 폴더 생성 */
            File checkPath = new File(uploadPath);
            if(!checkPath.exists()) {
                checkPath.mkdir();
            } 
            
            /* 파일중복시 인덱스 처리 */
            File uploadedFile = new File(uploadPath, fileName);
            log.debug("uploadedFile : {}", uploadedFile);
            if (uploadedFile.exists()) {
                for(int k = 0;  true; k++) {
                    uploadedFile = new File(uploadPath, "(" + k + ")" + fileName);
                    
                    if(!uploadedFile.exists()) { 
                        fileName = "(" + k + ")" + fileName;
                        break;
                    }
                }
            }
            
            multipartFile.transferTo(new File(uploadPath.concat(File.separator).concat(fileName)));
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
        return fileName;
    }
    
    

}
