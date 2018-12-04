package com.explorer.email;

import com.explorer.exception.ServiceException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    private String systemMail;

    @Override
    public void sendMail(String to, String subject, String htmlContent) throws ServiceException {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(msg);
            messageHelper.setTo(to);
            messageHelper.setFrom(systemMail);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlContent, true);
            javaMailSender.send(msg);
        } catch (Exception e) {
            throw new ServiceException("send email fail", e);
        }
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String getSystemMail() {
        return systemMail;
    }

    public void setSystemMail(String systemMail) {
        this.systemMail = systemMail;
    }
}
