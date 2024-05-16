package org.example;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Date;
import java.util.Properties;

import static org.example.Mail.*;

   public class MailManagement extends Mail {

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
           String host = "imap.gmail.com";
           String username = "iamtheone.javaproje@gmail.com";
           String password = "dnhz mqsf buou dfxd";

           File emailDir = new File("emails/inbox");
           if (!emailDir.exists()) {
               emailDir.mkdirs();
           }

           try {
               Properties props = new Properties();
               props.setProperty("mail.imap.host", host);
               props.setProperty("mail.imap.port", "993");
               props.setProperty("mail.imap.ssl.enable", "true");


               Session session = Session.getDefaultInstance(props, new Authenticator() {
                   @Override
                   protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(username, password);
                   }
               });

               Store store = session.getStore("imap");
               store.connect(host, username, password);

               Folder inbox = store.getFolder("INBOX");
               inbox.open(Folder.READ_ONLY);

               Message[] messages = inbox.getMessages();

               for (int i = 0; i < messages.length; i++) {
                   Message message = messages[i];
                   File emailFile = new File(emailDir, "email_" + (i + 1) + ".txt");

                   try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(emailFile)))) {
                       writer.write("Konu: " + message.getSubject() + "\n");
                       writer.write("Gönderen: " + message.getFrom()[0] + "\n");
                       Object content = message.getContent();
                       if (content instanceof String) {
                           writer.write("İçerik: " + (String) content + "\n");
                       } else if (content instanceof Multipart) {
                           handleMultipart((Multipart) content, writer);
                       } else {
                           writer.write("İçerik: " + content.toString() + "\n");
                       }
                   } catch (IOException | MessagingException e) {
                       e.printStackTrace();
                   }
               }

               inbox.close(false);
               store.close();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }

       private static void handleMultipart(Multipart multipart, BufferedWriter writer) throws MessagingException, IOException {
           for (int i = 0; i < multipart.getCount(); i++) {
               BodyPart bodyPart = multipart.getBodyPart(i);
               String contentType = bodyPart.getContentType();
               writer.write("Parça " + (i + 1) + " - İçerik Tipi: \n" + contentType + "\n");
               if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) || bodyPart.getFileName() != null) {
                   saveAttachment(bodyPart,i);
               } else if (bodyPart.isMimeType("text/plain")) {
                   try (BufferedReader reader = new BufferedReader(new InputStreamReader(bodyPart.getInputStream()))) {
                       String line;
                       while ((line = reader.readLine()) != null) {
                           writer.write("Düz Metin İçerik: " + line + "\n");
                       }
                   }
               } else if (bodyPart.isMimeType("text/html")) {
                   writer.write("HTML İçerik: " + bodyPart.getContent() + "\n");
               } else if (bodyPart.getContent() instanceof Multipart) {
                   handleMultipart((Multipart) bodyPart.getContent(), writer);
               } else if (bodyPart.getContent() instanceof InputStream) {
                   handleInputStream((InputStream) bodyPart.getContent(), writer);
               } else {
                   writer.write("Diğer İçerik: " + bodyPart.getContent() + "\n");
               }
           }
       }

       private static void handleInputStream(InputStream is, BufferedWriter writer) throws IOException {
           BufferedReader reader = new BufferedReader(new InputStreamReader(is));
           String line;
           while ((line = reader.readLine()) != null) {
               writer.write(line);
               writer.newLine();
           }
       }

       private static void saveAttachment(BodyPart bodyPart,int i) throws MessagingException, IOException {
           File dir = new File("attachments/email_" + (i + 1));
           if (!dir.exists()) dir.mkdirs();
           String fileName = bodyPart.getFileName();
           File file = new File(dir, fileName);
           try (InputStream is = bodyPart.getInputStream();
                FileOutputStream fos = new FileOutputStream(file)) {
               byte[] buf = new byte[4096];
               int bytesRead;
               while ((bytesRead = is.read(buf)) != -1) {
                   fos.write(buf, 0, bytesRead);
               }
               System.out.println("Ek kaydedildi: " + file.getAbsolutePath());
           }
       }
       
   }

