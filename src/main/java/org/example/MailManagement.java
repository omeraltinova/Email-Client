package org.example;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.util.Date;
import java.util.Properties;

import static org.example.Mail.*;

   public class MailManagement extends Mail {

    public static void sendPlainTextEmail(String from, String to, String subject, String message, boolean debug) {
        mailSaver(from,to,subject,message);
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
       public void fetchEmails(int indexToDelete,String boxName) {
           String host = getImapHost();
           String username = getUSERNAME();
           String password = getPASSWORD();

           File emailDir = null;

           // Gelen klasör ismine göre dosya yolunu belirleyin ve büyük harflerle kontrol edin
           if (boxName.equalsIgnoreCase("INBOX")) {
               emailDir = new File("emails/inbox");
               boxName = "INBOX"; // IMAP sunucusu için doğru klasör ismi
           }
           else if (boxName.equalsIgnoreCase("SENT")) {
               emailDir = new File("emails/sent");
               boxName = "SENT"; // IMAP sunucusu için doğru klasör ismi
           }

           // Dosya yolunu kontrol edin ve gerekirse oluşturun
           if (emailDir != null && !emailDir.exists()) {
               emailDir.mkdirs();
           }
           try {
               Properties props = new Properties();
               props.setProperty("mail.imap.host", host);
               props.setProperty("mail.imap.port", "993");
               props.setProperty("mail.imap.ssl.enable", "true");
               props.setProperty("mail.debug", "true");

               Session session = Session.getDefaultInstance(props, new Authenticator() {
                   @Override
                   protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(username, password);
                   }
               });

               Store store = session.getStore("imap");
               store.connect(host, username, password);

               Folder inbox = store.getFolder(boxName);
               inbox.open(Folder.READ_ONLY);

               Message[] messages = inbox.getMessages();

               for (int i = 0; i < messages.length; i++) {
                   Message message = messages[i];
                   if (indexToDelete >= 0 && indexToDelete == i) {
                       message.setFlag(Flags.Flag.DELETED, true);  // Mesajı sil
                       System.out.println("Mesaj silindi: " + message.getSubject());
                   }

                   File emailFile = new File(emailDir, "email_" + (i + 1) + ".txt");

                   try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(emailFile)))) {
                       String subject = MimeUtility.decodeText(message.getSubject());
                       writer.write("Konu: " + subject + "\n");
                       writer.write("Gönderen: " + message.getFrom()[0] + "\n");
                       Object content = message.getContent();
                       if (content instanceof String) {
                           writer.write("İçerik: " + (String) content + "\n");
                       } else if (content instanceof Multipart) {
                           handleMultipart((Multipart) content, writer);
                       } else {
                           writer.write("İçerik: " + decodeContent(content) + "\n");
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
                   saveAttachment(bodyPart, i);
               } else if (bodyPart.isMimeType("text/plain")) {
                   writer.write("Düz Metin İçerik: " + decodeContent(bodyPart.getContent()) + "\n");
               } else if (bodyPart.isMimeType("text/html")) {
                   // HTML içeriği atla
               } else if (bodyPart.getContent() instanceof Multipart) {
                   handleMultipart((Multipart) bodyPart.getContent(), writer);
               } else {
                   writer.write("Diğer İçerik: " + decodeContent(bodyPart.getContent()) + "\n");
               }
           }
       }

       private static void saveAttachment(BodyPart bodyPart, int i) throws MessagingException, IOException {
           File dir = new File("attachments/email_" + (i + 1));
           if (!dir.exists()) dir.mkdirs();
           String fileName = MimeUtility.decodeText(bodyPart.getFileName());
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

       private static String decodeContent(Object content) throws IOException {
           if (content instanceof InputStream) {
               return readInputStream((InputStream) content);
           } else if (content != null) {
               return content.toString();
           }
           return "";
       }

       private static String readInputStream(InputStream is) throws IOException {
           StringBuilder contentBuilder = new StringBuilder();
           try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
               String line;
               while ((line = reader.readLine()) != null) {
                   contentBuilder.append(line).append("\n");
               }
           }
           return contentBuilder.toString();
       }

       public static void mailSaver(String from, String to, String subject, String message){
        String path = "emails/sent/"+from+"/"+subject+".txt";

        String[] content = {subject,
        from,
        to,
        message};
        try{
            File saver = new File(path);
            saver.getParentFile().mkdirs();
            System.out.println(saver.getName() + " adlı dosya oluşturuldu.");
            FileWriter fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);

            for(String line: content){
                bw.write(line);
                bw.newLine();
            }
            bw.flush();
            bw.close();


        }
        catch (Exception e){
            System.out.println("Gönderilen dosya kaydedilemedi.");
            e.printStackTrace();
        }

       }

       public static void mailLister(String email){
           String path = "emails/sent/"+email;
           int i = 0;

           try{
               File lister = new File(path);

               if(!lister.exists()){
                   System.out.println("Gönderilmiş bir mail yok!");
                   return;
               }
               File[] txtFiles = lister.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

               if(txtFiles!=null && txtFiles.length>0){
                   System.out.println("Bulunan dosyalar: ");
                   for(File dosya : txtFiles){
                       System.out.println((i+1) + ". gönderilen mail");
                       System.out.println(dosya.getName());
                       i++;
                   }

               }
               else{
                   System.out.println("Gönderilen bir mail bulunamadı");
               }


           }
           catch (Exception e){
               System.out.println("Dosya bulunamadı");
           }

       }
       
   }

