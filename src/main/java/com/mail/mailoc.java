package com.mail;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 

 public class mailoc {

  private static final String SMTP_HOST_NAME = "gmail-smtp-msa.l.google.com";

  private static final String SMTP_AUTH_USER = "bhindwal.mohit@gmail.com";

  private static final String SMTP_AUTH_PWD = "sb!9907021341";
  
  private static final String SUBJECT = "bigup.com";

  String recp;

  String pass;
  
  
  

 

  public void test(String recipent,String username ,String password)

    throws Exception

  {

    this.recp = recipent;

    this.pass = password;

    int port = 40;

    Properties props = new Properties();

 

    props.put("mail.transport.protocol", "smtp");

    props.put("mail.smtp.host", "smtp.gmail.com");

    props.put("mail.smtp.auth", "true");

    props.put("mail.smtp.starttls.enable", "true");

  props.put("mail.smtp.port", "587");
 

    Authenticator auth = new mailoc.SMTPAuthenticator(this);

    Session mailSession = Session.getDefaultInstance(props, auth);

 

    mailSession.setDebug(true);

    Transport transport = mailSession.getTransport();

 

    MimeMessage message = new MimeMessage(mailSession);
String str,result = "";
    BufferedReader fis =  new BufferedReader(new FileReader(new File("D:/email.html")));
    while ((str  = fis.readLine()) != null) {
		result+=str;
	}
    
    result = result.replaceAll("uuser",username );
    result = result.replaceAll("ppwd",password);
    message.setContent(result, "text/html");
    

    message.setFrom(new InternetAddress(SMTP_AUTH_USER));

    message.addRecipient(Message.RecipientType.TO, new InternetAddress(this.recp));
    message.setSubject(SUBJECT);
 

    transport.connect();

    transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));

 

    transport.close();

  }

 

  public static void main(String[] args)

  {

    try

    {
args = new String[]{"bhindwal.mohit@gmail.com","bhindwal.mohit@gmail.com","sb!9907021341"};
    	mailoc o = new mailoc();

      o.test(args[0], args[1],args[2]);

    }

    catch (Exception e)

    {

      System.out.println(e);

    }

  }
  
  class SMTPAuthenticator extends Authenticator

  {

    private SMTPAuthenticator(mailoc parammailoc)

    {

    }

   

    public PasswordAuthentication getPasswordAuthentication()

    {

      String username = SMTP_AUTH_USER;

      String password = SMTP_AUTH_PWD;

      return new PasswordAuthentication(username, password);

    }

  }

}

 
 