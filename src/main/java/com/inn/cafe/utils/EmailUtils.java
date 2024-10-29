package com.inn.cafe.utils;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {
  @Autowired private JavaMailSender emailSender;

  public void sendSimpleMessage(String sender, String subject, String body, List<String> receptors) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("disagli12@gmail.com");
    message.setText(body);
    message.setSubject(subject);
    message.setTo(sender);

    if (receptors != null && !receptors.isEmpty())
      message.setCc(this.getCCArray(receptors));

    this.emailSender.send(message);
  }

  private String[] getCCArray(List<String> receptors) {
    String[] cc = new String[receptors.size()];
    for (int i = 0, size = receptors.size(); i < size; i++)
      cc[i] = receptors.get(i);

    return cc;
  }
}
