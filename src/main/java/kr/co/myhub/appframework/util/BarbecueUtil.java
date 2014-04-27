package kr.co.myhub.appframework.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

public class BarbecueUtil {
    
    /* 바코드 타입 */
    public static final String CODE_128 = "1000";
    public static final String CODE_128C = "1001";
    public static final String CODE_2OF5 = "1002";
    public static final String CODE_2OF5IL = "1003";
    public static final String CODE_UPCA = "1004";
    public static final String CODE_PDF417 = "2000";
    public static final String CODE_PDF417Trunc = "2001";
    public static final String CODE_MATRIX = "2002";
    public static final String CODE_QRCODE = "2003";
    
    // 기프티콘 바코드 사이즈
    public static final String GIFTICON_HEIGHT_SIZE = "60";
    public static final String GIFTICON_WIDTH_SIZE = "5";
    
    // 업로드 경로
    public final static String PATH = "C:\\temp\\";
    
    // 파일 확장자
    public final static String FILE_EXT_JPG = ".jpg";
    public final static String FILE_EXT_PNG = ".png";
    public final static String FILE_EXT_GIF = ".gif";
    
    /**
     * 
     * @param barType : 바코드 타입
     * @param barData : 바코드 번호
     * @param barWidth : 바코드 width
     * @param barHeight : 바코드 height
     * @param color
     * @return
     */
    public static Barcode createBar(String barType, String barData, String barWidth, String barHeight, Color color) {
        System.out.println("BarType=[" + barType + "] barData=[" + barData + "] barWidth[" + barWidth + "] barHeight[" + barHeight + "]");
        
        Barcode barcode = null;
 
        try {
            barcode = BarbecueUtil.drawBarcode(barType, barData, Integer.parseInt(barWidth), Integer.parseInt(barHeight), color);
        } catch (BarcodeException ex) {
            System.out.println("[Barcode generated Error] : " + ex.getMessage());
        } catch (OutputException ex) {
            System.out.println("[Barcode generated Error] : " + ex.getMessage());
        }
       
        return barcode;
    }

    /**
     * 
     * @param barType : 바코드 타입
     * @param barData : 바코드 번호
     * @param barWidth : 바코드 width
     * @param barHeight : 바코드 height
     * @param color
     * @return
     * @throws BarcodeException
     * @throws OutputException
     */
    public static Barcode drawBarcode(String barType, String barData, int barWidth, int barHeight, Color color) 
            throws BarcodeException, OutputException {
        Barcode barcode = null;
        
        switch(barType) {
        case BarbecueUtil.CODE_128:
            barcode = BarcodeFactory.createCode128(barData);
            break;
        case BarbecueUtil.CODE_128C:
            barcode = BarcodeFactory.createCode128C(barData);
            break;
        case BarbecueUtil.CODE_2OF5:
            barcode = BarcodeFactory.createStd2of5(barData);
            break;
        case BarbecueUtil.CODE_2OF5IL:
            barcode = BarcodeFactory.createInt2of5(barData);
            break;
        case BarbecueUtil.CODE_PDF417:
            barcode = BarcodeFactory.createPDF417(barData);
            break;
        default:
            barcode = BarcodeFactory.createCode128(barData);
            break;
        }
        
        barcode.setBarWidth(barWidth);
        barcode.setBarHeight(barHeight);
        barcode.setDrawingText(true);
        
        if(color != null) barcode.setBackground(color);
        BufferedImage image = new BufferedImage(barcode.getWidth(), barcode.getHeight(), 10);
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        barcode.draw(g2d, 0, 0);
 
        return barcode;
    }

    /**
     * barcode create test
     */
    public static void main(String[] args) {
        try {
            String cpNo = "1234 5678 3456 2198";
            
            // 바코드 생성
            Barcode barcode = BarbecueUtil.createBar(BarbecueUtil.CODE_128C, cpNo, BarbecueUtil.GIFTICON_WIDTH_SIZE, BarbecueUtil.GIFTICON_HEIGHT_SIZE, Color.WHITE);
            
            // 파일 생성
            String fileName = BarbecueUtil.getFileName().concat(BarbecueUtil.FILE_EXT_JPG);
            File file = new File(PATH + fileName);
            
            // 업로드된 파일에 바코드 이미지 생성
            BarcodeImageHandler.saveJPEG(barcode, file);
        } catch (OutputException e) {
            e.printStackTrace();
        } 

    }
    
    public static String getFileName() {
        SecureRandom prng = null;
        byte[] result = null;
        
        try {
            prng = SecureRandom.getInstance("SHA1PRNG");
            String randomNum = new Integer(prng.nextInt()).toString();
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            result = sha.digest(randomNum.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return hexEncode(result);
    }
    
    public static String hexEncode(byte[] aInput) {
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[(b&0xf0) >> 4]);
            result.append(digits[b&0x0f]);
        }
        return result.toString();
    }

}
