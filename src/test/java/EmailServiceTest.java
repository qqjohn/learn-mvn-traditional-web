import com.explorer.email.EmailService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.mail.internet.MimeMessage;

public class EmailServiceTest {

    private GreenMail greenMail;

    @Before
    public void startMailServer() {
        System.err.println("------startMailServer------");
        greenMail = new GreenMail(ServerSetup.SMTP);
        greenMail.setUser("test@gmail.com", "123456");
        greenMail.start();
    }

    @Test
    public void testSendMail() throws Exception {
        System.err.println("------testSendMail------");
        ApplicationContext context = new ClassPathXmlApplicationContext("email.xml");
        EmailService emailService = context.getBean(EmailService.class);
        String subject = "test subject";
        String htmlContent = "<h3>Test</h3>";
        emailService.sendMail("test@gmail.com", subject, htmlContent);
        greenMail.waitForIncomingEmail(2000, 1);
        MimeMessage[] msgs = greenMail.getReceivedMessages();
        Assert.assertEquals(1, msgs.length);
        Assert.assertEquals(subject, msgs[0].getSubject());
        Assert.assertEquals(htmlContent, GreenMailUtil.getBody(msgs[0]).trim());
    }

    @After
    public void stopMailServer() {
        System.err.println("------stopMailServer------");
        greenMail.stop();
    }
}
