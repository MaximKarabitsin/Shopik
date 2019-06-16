package ru.sstu.shopik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.sstu.shopik.domain.entities.Order;
import ru.sstu.shopik.domain.entities.OrderProduct;
import ru.sstu.shopik.domain.entities.Product;
import ru.sstu.shopik.domain.entities.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;


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

    @Async
    public void sendConfirmEmail(User user, Locale locale) {
        Map<String, Object> replaces = new HashMap<>();
        replaces.put("token", user.getToken());
        String content = this.build("mail/confirmEmail", replaces, locale);
        String subject = this.messageSource.getMessage("mail.confirm.subject", null, locale);
        this.sendMail(MAIL_NO_REPLY, user.getEmail(), subject, content);
    }

    @Async
    public void sendPasswordRecovery(User user, String newPassword, Locale locale) {
        Map<String, Object> replaces = new HashMap<>();
        replaces.put("newPassword", newPassword);
        replaces.put("login", user.getLogin());
        String content = this.build("mail/passwordRecovery", replaces, locale);
        String subject = this.messageSource.getMessage("mail.passwordRecovery.subject", null, locale);
        this.sendMail(MAIL_NO_REPLY, user.getEmail(), subject, content);
    }

    @Async
    public void sendUserChange(User user) {
        Map<String, Object> replaces = new HashMap<>();
        replaces.put("u", user);
        String content = this.build("mail/userChange", replaces, Locale.ENGLISH);
        String subject = this.messageSource.getMessage("mail.userChange.subject", null, Locale.ENGLISH);
        this.sendMail(MAIL_NO_REPLY, user.getEmail(), subject, content);
    }

    @Async
    public void sendProductChange(Product product) {
        Map<String, Object> replaces = new HashMap<>();
        replaces.put("p", product);
        String content = this.build("mail/productChange", replaces, Locale.ENGLISH);
        String subject = this.messageSource.getMessage("mail.productChange.subject", null, Locale.ENGLISH);
        this.sendMail(MAIL_NO_REPLY, product.getSeller().getEmail(), subject, content);
    }

    @Async
    public void sendOrderBuyer(Order order, long cost) {
        Map<String, Object> replaces = new HashMap<>();
        replaces.put("order", order);
        replaces.put("cost", cost);
        String content = this.build("mail/orderBuyer", replaces, Locale.ENGLISH);
        String subject = this.messageSource.getMessage("mail.orderBuyer.subject", null, Locale.ENGLISH);
        this.sendMail(MAIL_NO_REPLY, order.getBuyer().getEmail(), subject, content);
    }

    @Async
    public void sendOrderSellers(Map<User, Set<OrderProduct>> sellerProducts, Map<User, Long> sellerCost, User buyer) {
        for (Map.Entry<User, Set<OrderProduct>> entry : sellerProducts.entrySet()) {
            User seller = entry.getKey();
            Set<OrderProduct> orderProducts = entry.getValue();
            Map<String, Object> replaces = new HashMap<>();
            replaces.put("orderProducts", orderProducts);
            replaces.put("buyer", buyer);
            replaces.put("cost", sellerCost.get(seller));
            String content = this.build("mail/orderSeller", replaces, Locale.ENGLISH);
            String subject = this.messageSource.getMessage("mail.orderSeller.subject", null, Locale.ENGLISH);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.sendMail(MAIL_NO_REPLY, seller.getEmail(), subject, content);
        }
    }

}
