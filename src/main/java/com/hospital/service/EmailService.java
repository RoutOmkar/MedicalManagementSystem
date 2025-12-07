package com.hospital.service;

import com.hospital.entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // ----------------- Simple Text Email -----------------
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error sending simple email: " + e.getMessage());
        }
    }

    // ----------------- HTML Email -----------------
    public void sendHtmlMessage(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = HTML
            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Error sending HTML email: " + e.getMessage());
        }
    }

    // ----------------- Email with Attachment -----------------
    public void sendMessageWithAttachment(String to, String subject, String text, File attachment) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(attachment);
            helper.addAttachment(file.getFilename(), file);

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Error sending email with attachment: " + e.getMessage());
        }
    }

    // ----------------- Send Appointment Confirmation -----------------
    public void sendAppointmentConfirmation(Appointment appointment, String patientEmail) {
        String subject = "Appointment Confirmation - Hospital";
        String htmlBody = "<h3>Appointment Confirmed</h3>"
                + "<p>Dear Patient,</p>"
                + "<p>Your appointment with Doctor ID: <b>" + appointment.getApId() + "</b> "
                + "is confirmed.</p>"
                + "<p><b>Date:</b> " + appointment.getAppointmentDate() + "<br>"
                + "<b>Time:</b> " + appointment.getAppointmentTime() + "</p>"
                + "<p>Status: <b>" + appointment.getStatus() + "</b></p>"
                + "<br><p>Thank you for choosing our hospital!</p>";

        sendHtmlMessage(patientEmail, subject, htmlBody);
    }
}
