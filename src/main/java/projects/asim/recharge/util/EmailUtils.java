package projects.asim.recharge.util;

import java.util.Properties;

import jakarta.mail.Session;
import jakarta.mail.Message;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Transport;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.AddressException;

public class EmailUtils {

    // ========================================
    // Section: Email Validation & OTP Dispatch
    // ========================================

    public static boolean sendOTPEmail(String toEmail, String otp) {

        String fromEmail = "hafizmuhammadasimofficial@gmail.com";
        String appPassword = "yxkv nxyz raca ibbv";

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, appPassword);
            }
        });

        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("OTP Verification");

            String htmlContent = "<html>"
                    + "<body style='font-family: Arial, sans-serif; color: #000; line-height: 1.5;'>"
                    + "<h2>OTP Verification</h2>"
                    + "<p>Dear User,</p>"
                    + "<p>Your One-Time Password (OTP) is:</p>"
                    + "<h1 style='font-weight: bold;'>" + otp + "</h1>"
                    + "<p>This code is valid for <b>3 minutes</b>. Please do not share it with anyone.</p>"
                    + "<p style='font-size: 12px; color: #555; margin-top: 20px;'>"
                    + "If you did not request this, please ignore this email."
                    + "</p>"
                    + "</body>"
                    + "</html>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            return true;

        } catch (Exception e) {

            ConsoleUtils.displayErrorMessage("Failed to send OTP. Please try again.");
            return false;

        }

    }

    public static boolean isValidEmail(String email) {

        try {

            InternetAddress emailAddr = new InternetAddress(email);

            emailAddr.validate();
            return true;

        } catch (AddressException ex) {

            return false;

        }

    }
    
}
