package com.amadeus.fun;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * A utility class for sending e-mail messages
 * 
 * @author www.codejava.net
 * 
 */
public class EmailUtility {
  public static void sendEmail(String host, String port, String toAddress, String fromAddress, String ccAddress,
      String subject, String message) throws AddressException, MessagingException
  {
    // sets SMTP server properties
    Properties properties = new Properties();
    properties.put("mail.smtp.host", host);
    properties.put("mail.smtp.port", port);
    properties.put("mail.smtp.starttls.enable", "false");

    Session session = Session.getDefaultInstance(properties);

    // creates a new e-mail message
    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(fromAddress));
    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
    if ("" != ccAddress) {
      msg.addRecipient(Message.RecipientType.CC, new InternetAddress(ccAddress));
    }
    msg.setSubject(subject);

    MimeMultipart multipart = new MimeMultipart("related");
    // html
    BodyPart messageBodyPart = new MimeBodyPart();
    messageBodyPart.setContent(message + "<br><br>Please DONOT reply to this email id: apt.funcommittee@amadeus.com",
        "text/html");

    // add it
    multipart.addBodyPart(messageBodyPart);

    // put everything together
    msg.setContent(multipart);

    // sends the e-mail
    Transport.send(msg);
  }
}
