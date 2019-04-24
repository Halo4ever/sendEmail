package com.neo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;



/**
 * Created by Enzo Cotter on 2019/4/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloServiceTest {



    @Resource
    HelloService helloService;

    @Resource
    TemplateEngine templateEngine;

    @Test
    public void sendHtmlMail() throws Exception {
        String content = "<html>\n"+
                "<body>\n" +
                    "<h3> hello world,这是一封html邮件</h3>"+
                "</body>"+
                "</html>";
        helloService.sendHtmlMail("hhhhalo@126.com","这是一封html邮件",content);
    }

    //测试发送附件邮件
    @Test
    public void sendAttachementsMail() throws MessagingException {
        String filePath = "G:/Java_Test/email/login.html";
        helloService.sendAttachmentsMail("hhhhalo@126.com","这是一封带附件的邮件",
                "这是一封带附件的邮件",filePath);
    }

    //测试发送图片邮件
    @Test
    public void sendInlineResourceMailTest() {
        String imgPath = "G:/Java_Test/email/test.jpg";
        String rscId = "neo001";
        String content = "<html><body>这是有图片的邮件:<img src=\'cid:" + rscId
                +"\'></img></body></html>";

        helloService.sendInlinResourceMain("hhhhalo@126.com","这是一个图片邮件",content, imgPath,rscId);
    }



    @Test
    public void sayHello() throws Exception {
        helloService.sayHello();
    }

    @Test
    public void sendSimpleMailTest(){
        helloService.sendSimpleMail("hhhhalo@126.com","这是第一封邮件","我也不知道问题出在哪里！");
    }


    @Test
    public void testTemplateMailTest() throws MessagingException {
        Context context = new Context();
        context.setVariable("id","006");
        String emailContent = templateEngine.process("emailTemplate",context);

        helloService.sendHtmlMail("hhhhalo@126.com","这是一个模板邮件",emailContent);

    }


}