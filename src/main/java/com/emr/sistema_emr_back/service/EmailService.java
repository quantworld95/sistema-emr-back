package com.emr.sistema_emr_back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCredenciales(String email, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Credenciales de Acceso");
        message.setText("Su usuario es: " + email + " y su contraseña temporal es: " + password);
        mailSender.send(message);
    }
}
