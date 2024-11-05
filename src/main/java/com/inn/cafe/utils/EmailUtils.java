package com.inn.cafe.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {
  @Autowired private JavaMailSender emailSender;

  public void sendSimpleMessage(
      String sender, String subject, String body, List<String> receptors) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("disagli12@gmail.com");
    message.setText(body);
    message.setSubject(subject);
    message.setTo(sender);

    if (receptors != null && !receptors.isEmpty()) message.setCc(this.getCCArray(receptors));

    this.emailSender.send(message);
  }

  private String[] getCCArray(List<String> receptors) {
    String[] cc = new String[receptors.size()];
    for (int i = 0, size = receptors.size(); i < size; i++) cc[i] = receptors.get(i);

    return cc;
  }

  public void forgotPasswordEmail(String to, String subject, String password)
      throws MessagingException {
    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setFrom("lologre2012@gmail.com");
    helper.setTo(to);
    helper.setSubject(subject);
    String htmlMsg =
        "<p><b>Your login details for Cafe Management System</b><br><b>Email: </b> "
            + to
            + "<br><b>Password: </b> "
            + password
            + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";

    message.setContent(htmlMsg, "text/html");
    emailSender.send(message);
  }
}
