package kr.co.myhub.appframework.util;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * E-mail 전송처리
 * @author jmpark
 *
 */
@Component
public class MailUtil {
    
    private static final Logger log = LoggerFactory.getLogger(MailUtil.class);
    
    /**
     * static 변수에는 주입이 되지 않는다. 그래서 PostConstruct를 통해서 변수에 다시 할당을 한다.
     */
    private static String ID;
    private static String PASSWORD;
    private static String HOST;
    private static String PORT;
    private static String AUTH;
    private static String FROM_MAIL;
    
    @Value("#{prop['mail.id']}") 
    private String pirvateId;
    
    @Value("#{prop['mail.password']}") 
    private String pirvatePassword;
    
    @Value("#{prop['mail.host']}") 
    private String pirvateHost;
    
    @Value("#{prop['mail.port']}") 
    private String pirvatePort;
    
    @Value("#{prop['mail.auth']}") 
    private String pirvateAuth;
    
    @Value("#{prop['mail.email']}") 
    private String pirvateEmail;
    
    @PostConstruct
    public void init(){
        ID = pirvateId;
        PASSWORD = pirvatePassword;
        HOST = pirvateHost;
        PORT = pirvatePort;
        AUTH = pirvateAuth;
        FROM_MAIL = pirvateEmail;
    }
    
    /**
     * 메일 전송
     * @param params
     * @return
     */
    public static boolean mailsend(Map<String, Object> params) {
        String sendContent = null;
        boolean result = false;
        
        String to = params.get("to").toString();
        String subject = params.get("subject").toString();
        String content = params.get("content").toString();
      
        try {
            Message message = new MimeMessage(mailServerInitialize());
            message.setFrom(new InternetAddress(MailUtil.FROM_MAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            //message.setText(content);
            message.setSentDate(new Date());
            
            MimeBodyPart mail_text = new MimeBodyPart();
            
            Multipart mpt = new MimeMultipart();
            sendContent = content;
            mail_text.setContent("<h1>" + sendContent + "</h1>", "text/html;charset=euc-kr");
                        
            mpt.addBodyPart(mail_text);
            message.setContent(mpt);
            
            Transport.send(message);
            result = Boolean.TRUE;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
        if (log.isDebugEnabled()) {
            log.debug("[mailsend] : {}", result);
        }
        
        return result;
    }
    
    /**
     * 자바 메일 설정
     * @return session
     */
    public static Session mailServerInitialize() {
        Properties props = new Properties();
        props.put("mail.smtp.host", MailUtil.HOST);
        props.put("mail.smtp.socketFactory.port", MailUtil.PORT);
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", MailUtil.AUTH);
        props.put("mail.smtp.port", MailUtil.PORT);
     
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() { 
                return new PasswordAuthentication(MailUtil.ID, MailUtil.PASSWORD); 
            }
        });
        
        return session;
    }
  
}
