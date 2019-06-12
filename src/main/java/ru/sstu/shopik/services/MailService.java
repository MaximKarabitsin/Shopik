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

	private static String MAIL_NO_REPLY = "no-reply@shopik.com";
	
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender mailSender;
	 
    @Autowired
    private MessageSource messageSource;

    public String build(String template, Map<String, Object> variables, Locale locale) {
        Context context = new Context();
        context.setVariables(variables);
        context.setLocale(locale);
        
        return this.templateEngine.process(template, context);
    }
    
    
    public void sendMail(String from, String to, String subject, String msg) {
        try {
            MimeMessage message = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(msg, true);
            this.mailSender.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public void sendConfirmEmail(User user, Locale locale){
        Map<String, Object> replaces = new HashMap<>();
        replaces.put("token", user.getToken());
        String content = this.build("mail/confirmEmail", replaces, locale);
        String subject = this.messageSource.getMessage("mail.confirm.subject", null, locale);
        this.sendMail(MAIL_NO_REPLY, user.getEmail(), subject, content);
    }

    public void sendPasswordRecovery(User user, String newPassword, Locale locale){
        Map<String, Object> replaces = new HashMap<>();
        replaces.put("newPassword", newPassword);
        replaces.put("login", user.getLogin());
        String content = this.build("mail/passwordRecovery", replaces, locale);
        String subject = this.messageSource.getMessage("mail.passwordRecovery.subject", null, locale);
        this.sendMail(MAIL_NO_REPLY, user.getEmail(), subject, content);
    }

    public void sendUserChange(User user){
        Map<String, Object> replaces = new HashMap<>();
        replaces.put("u", user);
        String content = this.build("mail/userChange", replaces, Locale.ENGLISH);
        String subject = this.messageSource.getMessage("mail.userChange.subject", null, Locale.ENGLISH);
        this.sendMail(MAIL_NO_REPLY, user.getEmail(), subject, content);
    }
}
