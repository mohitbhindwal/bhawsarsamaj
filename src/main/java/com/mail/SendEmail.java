package com.mail;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail
{

	
    private String to, from, bcc, cc, account, message,  password, subject;

    public SendEmail(String to, String from,String bcc, String cc, String account, String message, String password, String subject)
    {
        setFrom(from);
        setTo(to);
        setBCC(bcc);
        setCC(cc);
        setAccount(account);
        setMessage(message);
        //setUserName(userName);
        setPassword(password);
        setSubject(subject);


    }


    //Setters
    public void setFrom(String from)
    {
        this.from = from;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public void setBCC(String bcc)
    {
        this.bcc = bcc;
    }

    public void setCC(String cc)
    {
        this.cc = cc;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

   /* public void setUserName(String userName)
    {
        this.userName = userName;
    }*/

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }



    //Getters
    public String getFrom()
    {
        return from;
    }

    public String getTo()
    {
        return to;
    }

    public String getBcc()
    {
        return bcc;
    }

    public String getCC()
    {
        return cc;
    }

    public String getMessage()
    {
        return message;
    }

    /*public String getUserName()
    {
        return userName;
    }*/

    public String getPassword()
    {
        return password;
    }

    public String getAccount()
    {
        return account;
    }

    public String getSubject()
    {
        return subject;
    }


    //This method is used to send the email
    public String send()
    {
        String result = "";
        try
        {
            Session mailSession = Session.getInstance(getProperties(), new PasswordAuthenticator());

            MimeMessage msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            msg.setSubject(subject);
            msg.setText(message);

            Transport.send(msg);

            result = "Mail Sent";
        }
        catch(Exception e)
        {
            result = "An error Occured";
            e.printStackTrace();
        }

        return result;
    }

public static void main(String[] args) {
	SendEmail mail =  new SendEmail("bhindwal.mohit@gmail.com", "bigup_astro@yahoo.in", null, null, "GMail", "hi!!!", "sb_9907021341", "bigup.com");
	mail.send();
}
    //This method will return properties appropreiate for the email account
    public Properties getProperties()
    {
        Properties props = new Properties();

        if(getAccount().equals("GMail"))
        {
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable","true");
            props.put("mail.smtp.host","smtp.gmail.com");
            props.put("mail.smtp.port","587");
        }
        else if(getAccount().equals("Yahoo"))
        {
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.port","465");
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.host","smtp.mail.yahoo.com");
            props.put("mail.smtp.port","465");
        }

        return props;
    }



    //This class Authnticates the password
    private class PasswordAuthenticator extends Authenticator
    {

        @Override
        protected PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication("bigup.com", getPassword());
        }
    }
}