package com.reabilitacao.reabilitacao.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {


    private final String remetente = "memoriaviva13@gmail.com";

    public void enviarEmailConfirmacao(String para, String token) throws IOException {
        String assunto = "Confirmação de Email - Memória Viva";
        String link = "http://localhost:8080/confirmar-email?token=" + token;
        String corpo = "Olá,\n\nClica no link abaixo para confirmar o teu email:\n" + link + "\n\nObrigado!";

        enviar(para, assunto, corpo);
    }

    public void enviarEmailAlteracaoPassword(String para, String token) throws IOException {
        String assunto = "Alteração de Palavra-passe - Memória Viva";
        String link = "http://localhost:8080/recuperar_passe.html?token=" + token;
        String corpo = "Recebemos um pedido para alterar a tua palavra-passe.\n\n" +
                       "Clica no link abaixo para definir uma nova:\n" + link +
                       "\n\nSe não foste tu, ignora este email.";

        enviar(para, assunto, corpo);
    }

    private void enviar(String para, String assunto, String corpo) throws IOException {
        Email from = new Email(remetente);
        Email to = new Email(para);
        Content content = new Content("text/plain", corpo);
        Mail mail = new Mail(from, assunto, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
            System.out.println("✅ Email enviado para: " + para);
        } catch (IOException ex) {
            System.out.println("❌ Erro ao enviar email: " + ex.getMessage());
            throw ex;
        }
    }
}




