package com.techouts.service;

import java.io.StringWriter;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Service("email")
@Component
public class EmailService {
	Logger logger=Logger.getLogger(EmailService.class);
	//@Resource(name="mailSender")
	  private MailSender mailSender; // MailSender interface defines a strategy
										// for sending simple mails
	  private VelocityEngine velocityEngine;
//	  private Mail mail;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

    @Async
	public void readyToSendEmail(String toAddress, String fromAddress, String subject, String msgBody) {
       
    	try {
			Thread.sleep(1000*10);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
        
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(fromAddress);
		msg.setTo(toAddress);
		msg.setSubject(subject);
		
    	/*Template template = velocityEngine.getTemplate("./templates/" + templateName);

		  VelocityContext velocityContext = new VelocityContext();
		  velocityContext.put("firstName", "Yashwant");
		  velocityContext.put("lastName", "Chavan");
		  velocityContext.put("location", "Pune");
		  
		  StringWriter stringWriter = new StringWriter();
		  
	  template.merge(velocityContext, stringWriter);
*/		  
		msg.setText(msgBody);
		mailSender.send(msg);
	}

	

}
