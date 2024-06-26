package org.example;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import javax.mail.search.SubjectTerm;
import javax.swing.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            File ahmediSilen = new File("emails/draft/"+from+"/"+subject+".txt");
            ahmediSilen.delete();
            System.out.println("Taslak varsa silindi mi? " + ahmediSilen.delete());
            JOptionPane.showMessageDialog(null,"Mesaj sunucuya teslim edildi!","Uçuşa geçtik!",JOptionPane.INFORMATION_MESSAGE);

        } catch (MessagingException mex) {
            mex.printStackTrace();
            Exception ex = mex.getNextException();
            if (ex != null) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,"Mesaj sunucuya teslim edilemedi","Geldik yoktunuz.",JOptionPane.ERROR_MESSAGE);

        }
    }
       public void fetchEmails(String sub, String boxName) {
           String host = getImapHost();
           String username = getUSERNAME();
           String password = getPASSWORD();

           File emailDir = null;


           if (boxName.equalsIgnoreCase("INBOX")) {
               emailDir = new File("emails/inbox");
               boxName = "INBOX";
           } else if (boxName.equalsIgnoreCase("SENT") && host.equals("imap.gmail.com")) {
               emailDir = new File("emails/sent");
               boxName = "[Gmail]/Sent Mail";
           } else if (boxName.equalsIgnoreCase("SENT") && host.equals("outlook.office365.com")) {
               emailDir = new File("emails/sent");
               boxName = "Sent";
           }


           if (emailDir != null && !emailDir.exists()) {
               emailDir.mkdirs();

           }
           try {
               Properties props = new Properties();
               props.setProperty("mail.imap.host", host);
               props.setProperty("mail.imap.port", "993");
               props.setProperty("mail.imap.ssl.enable", "true");
//               props.setProperty("mail.debug", "true");

               Session session = Session.getDefaultInstance(props, new Authenticator() {
                   @Override
                   protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(username, password);
                   }
               });

               Store store = session.getStore("imap");
               store.connect(host, username, password);

               Folder inbox = store.getFolder(boxName);
               inbox.open(Folder.READ_WRITE);

               if (!sub.equals("")) {
                   Message[] messages = inbox.search(new SubjectTerm(sub));

                   for (int i = 0; i < messages.length; i++) {
                       Message message = messages[i];

                       if (message.getSubject().equals(sub)) {
                           message.setFlag(Flags.Flag.DELETED, true);
                           System.out.println("Mesaj silindi: " + message.getSubject());
                           break;
                       }
                   }
               } else {
                   int messageCount = inbox.getMessageCount();
                   Message[] messages;
                   if (messageCount > 5) {
                       messages = inbox.getMessages(messageCount - 4, messageCount);
                   } else {
                       messages = inbox.getMessages();
                   }


                   for (int i = 0; i < messages.length; i++) {
                       Message message = messages[i];
                       File emailFile = new File(emailDir, "email_" + (i + 1) + ".txt");

                       try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(emailFile)))) {
                           String subject = MimeUtility.decodeText(message.getSubject());
                           writer.write("Konu: " + subject + "\n");
                           if (boxName.equalsIgnoreCase("INBOX")) {
                               Address[] fromAddresses = message.getFrom();

                               if (fromAddresses != null && fromAddresses.length > 0) {
                                   InternetAddress from = (InternetAddress) fromAddresses[0];
                                   String senderName = from.getPersonal();
                                   String senderEmail = from.getAddress();
                                   writer.write("Gönderen: " + senderName + " <" + senderEmail + ">\n");
                               }
                           } else {
                               Address[] toAddresses = message.getRecipients(Message.RecipientType.TO);

                               if (toAddresses != null && toAddresses.length > 0) {
                                   for (Address address : toAddresses) {
                                       InternetAddress to = (InternetAddress) address;
                                       String recipientName = to.getPersonal();
                                       String recipientEmail = to.getAddress();
                                       writer.write("Gönderen: " + (recipientName != null ? recipientName : "") + " <" + recipientEmail + ">\n");
                                   }
                               }
                           }

                           Object content = message.getContent();
                           if (content instanceof String) {
                               writer.write("İçerik: " + (String) content + "\n");
                           } else if (content instanceof Multipart) {
                               handleMultipart((Multipart) content, writer);
                           } else {
                               writer.write("İçerik: " + decodeContent(content) + "\n");
                           }
                       } catch (IOException | MessagingException e) {
                           JOptionPane.showMessageDialog(null, "Mesajlar sunucudan alınırken bir sorun oluştu.", "Kapıyı açar mısınız?", JOptionPane.ERROR_MESSAGE);
                           e.printStackTrace();
                       }
                   }

                   inbox.expunge();
                   store.close();
               }
               } catch(Exception e){
                   e.printStackTrace();
               }


       }

       private static void handleMultipart(Multipart multipart, BufferedWriter writer) throws MessagingException, IOException {
           for (int i = 0; i < multipart.getCount(); i++) {
               BodyPart bodyPart = multipart.getBodyPart(i);
               String contentType = bodyPart.getContentType();
               writer.write("Parça " + (i + 1) + " - İçerik Tipi: \n" + contentType + "\n");
               if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) || bodyPart.getFileName() != null) {
                   saveAttachment(bodyPart);
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

       private static void saveAttachment(BodyPart bodyPart) throws MessagingException, IOException {
           File dir = new File("attachments");
           if (!dir.exists()) {
               if (!dir.mkdirs()) {
                   throw new IOException("Attachment directory cannot be created: " + dir.getAbsolutePath());
               }
           }

           String fileName = MimeUtility.decodeText(bodyPart.getFileName());
           File file = new File(dir, fileName);

           try (InputStream is = new BufferedInputStream(bodyPart.getInputStream());
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
               byte[] buf = new byte[3*1024*1024];
               int bytesRead;
               while ((bytesRead = is.read(buf)) != -1) {
                   bos.write(buf, 0, bytesRead);
               }
               System.out.println("Attachment saved: " + file.getAbsolutePath());
           } catch (FileNotFoundException e) {
               System.err.println("File not found: " + e.getMessage());
               e.printStackTrace();
           } catch (IOException e) {
               System.err.println("I/O error: " + e.getMessage());
               e.printStackTrace();
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

//       public static void mailLister(String email){
//           String path = "emails/sent/"+email;
//           int i = 0;
//
//           try{
//               File lister = new File(path);
//
//               if(!lister.exists()){
//                   System.out.println("Gönderilmiş bir mail yok!");
//                   return;
//               }
//               File[] txtFiles = lister.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
//
//               if(txtFiles!=null && txtFiles.length>0){
//                   System.out.println("Bulunan dosyalar: ");
//                   for(File dosya : txtFiles){
//                       System.out.println((i+1) + ". gönderilen mail");
//                       System.out.println(dosya.getName());
//                       i++;
//                   }
//
//               }
//               else{
//                   System.out.println("Gönderilen bir mail bulunamadı");
//               }
//
//
//           }
//           catch (Exception e){
//               System.out.println("Dosya bulunamadı");
//           }
//
//       }
//       public static List<String> listDraftFiles() {
//           File folder = new File("emails/draft/"+getUSERNAME());
//           List<String> txtFileNames = new ArrayList<>();
//
//           if (folder.exists() && folder.isDirectory()) {
//               File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
//
//               if (files != null) {
//                   for (File file : files) {
//                       txtFileNames.add(file.getName());
//                   }
//               }
//           } else {
//               System.out.println("Klasör bulunamadı: ");
//           }
//
//           return txtFileNames;
//       }

       public static boolean draftSaver(String from, String to, String subject, String message){
           String path = "emails/draft/"+from+"/"+subject+".txt";

           String patternString = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";

           Pattern pattern = Pattern.compile(patternString);
           Matcher matcher = pattern.matcher(to);

           if (matcher.matches()) {
               String[] content = { "Konu: "+ subject,
                       from,
                       "Gönderen: "+ to,
                       "İçerik: "+ message};
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
                   JOptionPane.showMessageDialog(null, "Taslak kaydedildi!\nEkranı yenileyin!");
                   return true;


               }
               catch (Exception e){
                   System.out.println("Gönderilen dosya kaydedilemedi.");
                   JOptionPane.showMessageDialog(null, "Taslak kaydetme başarısız oldu. :(","Hata",JOptionPane.ERROR_MESSAGE);
                   e.printStackTrace();
               }


           }
           else{
               JOptionPane.showMessageDialog(null,"Girdiğiniz mail adresi geçersiz!","Hay Allah! Karıştırdınız herhalde?",JOptionPane.ERROR_MESSAGE);
               return false;
           }


           return false;
       }
       
   }

