package org.example;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import static org.example.Mail.*;

   public class MailManagement extends Mail {

       private static String MANAGE_USERNAME = getUSERNAME();
       private static String MANAGE_PASSWORD = getPASSWORD();
       private static String MANAGE_HOST = getHOST(); //SMTP HOST
       private static String MANAGE_PORT = getPORT(); //SMTP PORT
       private static String MANAGE_IMAP_HOST = getImapHost();
       private static String MANAGE_IMAP_PORT = getImapPort();


    public static void sendPlainTextEmail(String from, String to, String subject, String message, boolean debug) {

        String host = getHOST();
        String password = getPASSWORD();
        //System.out.println("Şifre + " + password);
        System.out.println("Oluşturulan host: "+ host);

        Properties prop = new Properties();
        prop.put("mail.smtp.host",host);
        prop.put("mail.smtp.port","587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getUSERNAME(), password);
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
        String host = getHOST();
        String username = getUSERNAME(); // Gmail adresinizi buraya yazın
        String password = getPASSWORD(); // Gmail şifrenizi buraya yazın

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

       public static String getManageUsername() {
           return MANAGE_USERNAME;
       }

       public static String getManagePassword() {
           return MANAGE_PASSWORD;
       }

       public static String getManageHost() {
           return MANAGE_HOST;
       }

       public static String getManagePort() {
           return MANAGE_PORT;
       }

       public static String getManageImapHost() {
           return MANAGE_IMAP_HOST;
       }

       public static String getManageImapPort() {
           return MANAGE_IMAP_PORT;
       }
   }

