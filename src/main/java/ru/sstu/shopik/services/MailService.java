package ru.sstu.shopik.services;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.sstu.shopik.domain.entities.User;


@Service
public class MailService {

	private static String MAIL_FROM_DEFAULT = "no-reply@projm.com";
	
	@Autowired
	private TemplateEngine templateEngine;
	
	private JavaMailSender mailSender;
	 
    @Autowired
    private MessageSource messageSource;

    @Autowired
    public MailService(JavaMailSender mailSender) {
    	this.mailSender = mailSender;
    }
    
    public String build(String template, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        
        return templateEngine.process(template, context);
    }
    
    
    public void sendMail(String from, String to, String subject, String msg) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(msg, true);
            mailSender.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public void sendConfirmEmail(User user){
        Map<String, Object> replaces = new HashMap<>();
        replaces.put("token", user.getToken());
        String content = this.build("mail/confirmEmail", replaces);
        //String subject = messageSource.getMessage("mail.test.subject", null, Locale.ENGLISH);

        sendMail(MAIL_FROM_DEFAULT, user.getEmail(), "subject", content);


    }

    public void sendTestEmail() {
        Map<String, Object> replaces = new HashMap<>();
        

        	replaces.put("fullName", "LOGIN");

        
        String content = this.build("mail/test-email", replaces);
        //String subject = messageSource.getMessage("mail.test.subject", null, Locale.ENGLISH);

        sendMail(MAIL_FROM_DEFAULT, "user@mail.ru", "subject", content);
    }

    
}
