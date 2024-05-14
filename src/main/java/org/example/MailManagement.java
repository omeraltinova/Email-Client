   package org.example;
//
//    import jakarta.mail.Authenticator;
//    import jakarta.mail.Message;
//    import jakarta.mail.MessagingException;
//    import jakarta.mail.Multipart;
//    import jakarta.mail.PasswordAuthentication;
//    import jakarta.mail.Session;
//    import jakarta.mail.Transport;
//    import jakarta.mail.internet.InternetAddress;
//    import jakarta.mail.internet.MimeBodyPart;
//    import jakarta.mail.internet.MimeMessage;
//    import jakarta.mail.internet.MimeMultipart;
//    import java.util.Date;
//    import java.util.List;
//    import java.util.Properties;
//
//    public class MailManagement extends Mail{
//
//        public static void sendPlainTextEmail(String from, String to, String subject, String message, boolean debug) {
//
//            Properties prop = new Properties();
//            prop.put("mail.smtp.host", getHOST());
//            prop.put("mail.smtp.port", getPORT());
//            prop.put("mail.smtp.auth", "true");
//            prop.put("mail.smtp.starttls.enable", "true");
//
//
//            Authenticator authenticator = new Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(getUSERNAME(), getPASSWORD());
//                }
//            };
//
//            Session session = Session.getInstance(prop, authenticator);
//            session.setDebug(debug);
//
//            try {
//
//    // create a message with headers
//                MimeMessage msg = new MimeMessage(session);
//                msg.setFrom(new InternetAddress(from));
//                InternetAddress[] address = {new InternetAddress(to)};
//                msg.setRecipients(Message.RecipientType.TO, address);
//                msg.setSubject(subject);
//                msg.setText(message);
//                msg.setSentDate(new Date());
//
//    // send the message
//                Transport.send(msg);
//
//            } catch (MessagingException mex) {
//                mex.printStackTrace();
//                Exception ex = null;
//                if ((ex = mex.getNextException()) != null) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//    }

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class MailManagement extends Mail {

    public static void sendPlainTextEmail(String from, String to, String subject, String message, boolean debug) {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", getHOST());
        prop.put("mail.smtp.port", getPORT());
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getUSERNAME(), getPASSWORD());
            }
        };

        Session session = Session.getInstance(prop, authenticator);
        session.setDebug(debug);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(message);
            msg.setSentDate(new Date());

            Transport.send(msg);

        } catch (MessagingException mex) {
            mex.printStackTrace();
            Exception ex = mex.getNextException();
            if (ex != null) {
                ex.printStackTrace();
            }
        }
    }
    public void fetchEmails() {
        // Gmail hesap bilgileri
        String host = "imap.gmail.com";
        String username = "iamtheone.javaproje@gmail.com"; // Gmail adresinizi buraya yazın
        String password = "dnhz mqsf buou dfxd"; // Gmail şifrenizi buraya yazın

        try {
            // Mail sunucusuna bağlanmak için gerekli özellikler
            Properties props = new Properties();
            props.setProperty("mail.imap.host", host);
            props.setProperty("mail.imap.port", "993");
            props.setProperty("mail.imap.ssl.enable", "true");

            // Session oluştur
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            //session.setDebug(true);
            // Mail sunucusuna bağlan
            Store store = session.getStore("imap");
            store.connect(host, username, password);

            // Inbox klasörüne eriş
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Inbox'taki mesajları al
            Message[] messages = inbox.getMessages();
            System.out.println("Gelen kutusundaki mesajlar:");

            // Mesajları ekrana yazdır
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("Konu: " + message.getSubject());
                System.out.println("Gönderen: " + message.getFrom()[0]);
                System.out.println("İçerik: " + message.getContent().toString());
                Object content = message.getContent();
                if (content instanceof String) {
                    // İçerik metin tipinde ise direkt olarak alınabilir
                    System.out.println("İçerik: " + (String) content);
                }
                else if (content instanceof Multipart)
                {
                    // Multipart içeriği işleme
                    handleMultipart((Multipart) content);
                }
                else {
                    // Diğer durumlarda, içeriği dönüştürme veya işleme yöntemlerinize göre işlem yapılabilir
                    System.out.println("İçerik: " + content.toString());
                }
            }


            // Bağlantıyı kapat
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void handleMultipart(Multipart multipart) throws MessagingException, IOException, IOException {
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            String contentType = bodyPart.getContentType();
            System.out.println("Parça " + (i + 1) + " - İçerik Tipi: " + contentType);
            if (bodyPart.isMimeType("text/plain")) {
                System.out.println("Düz Metin İçerik: " + bodyPart.getContent());
            }
            else if (bodyPart.isMimeType("text/html")) {
                System.out.println("HTML İçerik: " + bodyPart.getContent());
            }
            else if (bodyPart.getContent() instanceof Multipart)
            {
                // Multipart içeren parçaları ayrıştır
                handleMultipart((Multipart) bodyPart.getContent());
            }
            else
            {
                System.out.println("Diğer İçerik: " + bodyPart.getContent());
            }
        }
    }

}

