package org.Courseplay.TestRunner;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.Courseplay.helper.DataDriven;
 
public class FinalReport extends DataDriven {
 
	public static void ReportMail() throws Throwable {

 
		// Create object of Property file
				Properties props = new Properties();
		 
				// this will set host of server- you can change based on your requirement 
				props.put("mail.smtp.host", "smtp.gmail.com");
		 
				// set the port of socket factory 
				props.put("mail.smtp.socketFactory.port", "465");
		 
				// set socket factory
				props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		 
				// set the authentication to true
				props.put("mail.smtp.auth", "true");
		 
				// set the port of SMTP server
				props.put("mail.smtp.port", "465");
				
				
		 
				// This will handle the complete authentication
				Session session = Session.getDefaultInstance(props,
		 
						new javax.mail.Authenticator() {
					String Name = DataDriven.getConfigDatas("vijayakumar.s@trackdfect.com");
					String Password = DataDriven.getConfigDatas("Td@Selenium123");
		 
							protected PasswordAuthentication getPasswordAuthentication() {
		 
							return new PasswordAuthentication(Name,Password );
		 
							}
		 
						});
		 
				try {
		 
					// Create object of MimeMessage class
					Message message = new MimeMessage(session);
					
		 
					// Set the from address
					message.setFrom(new InternetAddress("vijayakumar.s@trackdfect.com"));
					
					String text = DataDriven.getConfigDatas("To Email");
					System.out.println(text);
		 
					// Set the recipient address
					message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(text));
					
					String cc = DataDriven.getConfigDatas("CC");
					System.out.println(cc);
					
					//CC
					message.addRecipient(Message.RecipientType.CC,new InternetAddress(cc));
					
					LocalDateTime current = LocalDateTime.now();

					DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
					String formatted = current.format(formatter);

					System.out.println("Current Date is: " + formatted);

		            
		                        // Add the subject link
					message.setSubject("CoursePlay Reports " + formatted);
		 
					// Create object to add multimedia type content
					BodyPart messageBodyPart1 = new MimeBodyPart();
		 
					// Set the body of email
					messageBodyPart1.setText("Hi Sir,"+ "\n" +  "\n" +"Here we attached  the Test Scenarios to Report");
		 
					// Create another object to add another content
					MimeBodyPart messageBodyPart2 = new MimeBodyPart();
		 
					// Mention the file which you want to send
					String filename = System.getProperty("user.dir") +"\\src\\test\\resource\\Library\\cucumber-reports\\report.html";
		 
					// Create data source and pass the filename
					DataSource source = new FileDataSource(filename);
		 
					// set the handler
					messageBodyPart2.setDataHandler(new DataHandler(source));
		 
					// set the file
					messageBodyPart2.setFileName(filename);
		 
					// Create object of MimeMultipart class
					Multipart multipart = new MimeMultipart();
		 
					// add body part 1
					multipart.addBodyPart(messageBodyPart2);
		 
					// add body part 2
					multipart.addBodyPart(messageBodyPart1);
		 
					// set the content
					message.setContent(multipart);
		 
					// finally send the email
					Transport.send(message);
		 
					System.out.println("=====Email Sent=====");
		 
				} catch (MessagingException e) {
		 
					throw new RuntimeException(e);
		  
		}
 
	}
	public static void main(String[] args) throws Throwable {
		ReportMail();
	}
 
}