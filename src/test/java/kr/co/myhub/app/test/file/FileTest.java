package kr.co.myhub.app.test.file;

import java.io.File;
import java.io.IOException;

public class FileTest {

    public static void main(String[] args) {
        
        System.out.println("sdsfdsfds");
        
        // 파일 폴더 삭제
        final File dir = new File("C:/dev/test");
        
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                deleteDirectory(dir);        
//            }
//        });
//        
//        thread.setDaemon(true);
//        thread.setName("t1");
//        thread.start();
        
        deleteDirectory(dir);
        
        // 파일 생성
        try {
            if(!dir.exists()){
               dir.mkdir();
            }
            
            File file = new File("C:/dev/test/test.txt");
            
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public static boolean deleteDirectory(File path) {
        System.out.println("deleteDirectory : " + path.exists());
        
        if(!path.exists()) {
            return false;
        }
        
        System.out.println("deleteDirectory22 : " + path.exists());
         
        File[] files = path.listFiles();
        System.out.println("files" + files.length);
        
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                file.delete();
            }
        }
         
        return path.delete();
    }

}
