package kr.co.myhub.app.common;

import java.awt.Font;
import java.awt.GraphicsEnvironment;


public class TestApp {
    
    public static void main(String[] args) throws Exception  {
        
        // 로컬의 시스템 폰트 가져오는 법
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = ge.getAllFonts();

        for (int i = 0; i < fonts.length; i++) {
            System.out.print(fonts[i].getFontName() + " : ");
            System.out.println(fonts[i].getFamily());
        }
    }
    
    /**
     * 폰트 스타일 지정
     */
    /*public void setStyle(Boolean bold, Boolean italic){
        if(bold == true && italic == false)
            lblMain.setFont(new Font(lblMain.getFont().getFontName(), Font.BOLD, lblMain.getFont().getSize()));
        else if(bold == false && italic == true){
          lblMain.setFont(new Font(lblMain.getFont().getFontName(), Font.ITALIC, lblMain.getFont().getSize()));
        }
        else if(bold ==true && italic == true){
            lblMain.setFont(new Font(lblMain.getFont().getFontName(), Font.BOLD+Font.ITALIC, lblMain.getFont().getSize()));
        }
        else if(bold == false && italic == false){
           lblMain.setFont(new Font(lblMain.getFont().getFontName(), Font.PLAIN, lblMain.getFont().getSize()));
        }
    }*/
    
    public static String fontStyleCodeToFontStyleString(int styleCode) {
        String styleName;
        switch (styleCode) {
            case Font.PLAIN:            styleName = "Font.PLAIN";       break;
            case Font.ITALIC:           styleName = "Font.ITALIC";      break;
            case Font.BOLD:             styleName = "Font.BOLD";        break;
            case Font.ITALIC+Font.BOLD: styleName = "ITALIC+Font.BOLD"; break;
            default: throw new IllegalArgumentException(
                    "fontStyleCodeToFontStyleString: Unknown font code: " +
                    styleCode);
        };
        return styleName;
    }

}
