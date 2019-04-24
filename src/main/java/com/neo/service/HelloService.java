package com.neo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.file.FileSystem;

/**
 * Created by Enzo Cotter on 2019/4/24.
 */
@Service
public class HelloService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JavaMailSender mailSender; //添加依赖后可以直接引用

    @Value("${spring.mail.username}")
    private String from;

    public void sayHello(){
        System.out.println("Hello World");
    }

    public void sendSimpleMail(String to,String subject,
                               String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);
        mailSender.send(message);
    }

    public void sendHtmlMail(String to,String subject,String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        mailSender.send(message);
    }

    //发送附件邮件
    public void sendAttachmentsMail(String to,String subject,String content,
                                   String filePath)  {


        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName,file);
//        helper.addAttachment(fileName+"_test",file);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    //发送带图片的附件
    public void sendInlinResourceMain(String to,String subject,String content,
                                      String rscPath,String rscId)  {

        logger.info("发送静态邮件开始：{},{},{},{},{}",to,subject,content,rscPath,rscId);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId,res);
            mailSender.send(message);
            logger.info("发送静态图片邮件成功");
        } catch (MessagingException e) {
            logger.error("发送静态邮件失败:",e);
        }

    }



}
