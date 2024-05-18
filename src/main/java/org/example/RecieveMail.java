//package org.example;
//import java.io.*;
//import java.util.Properties;
//import javax.mail.*;
//import javax.mail.internet.MimeUtility;
//public class RecieveMail {
//    public void fetchEmails() {
//        String host = "imap.gmail.com";
//        String username = "iamtheone.javaproje@gmail.com";
//        String password = "dnhz mqsf buou dfxd";
//
//        File emailDir = new File("emails/inbox");
//        if (!emailDir.exists()) {
//            emailDir.mkdirs();
//        }
//
//        try {
//            Properties props = new Properties();
//            props.setProperty("mail.imap.host", host);
//            props.setProperty("mail.imap.port", "993");
//            props.setProperty("mail.imap.ssl.enable", "true");
//            props.setProperty("mail.debug", "true");
//
//            Session session = Session.getDefaultInstance(props, new Authenticator() {
//                @Override
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(username, password);
//                }
//            });
//
//            Store store = session.getStore("imap");
//            store.connect(host, username, password);
//
//            Folder inbox = store.getFolder("INBOX");
//            inbox.open(Folder.READ_ONLY);
//
//            Message[] messages = inbox.getMessages();
//
//            for (int i = 0; i < messages.length; i++) {
//                Message message = messages[i];
//                File emailFile = new File(emailDir, "email_" + (i + 1) + ".txt");
//
//                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(emailFile)))) {
//                    String subject = MimeUtility.decodeText(message.getSubject());
//                    writer.write("Konu: " + subject + "\n");
//                    writer.write("Gönderen: " + message.getFrom()[0] + "\n");
//                    Object content = message.getContent();
//                    if (content instanceof String) {
//                        writer.write("İçerik: " + (String) content + "\n");
//                    } else if (content instanceof Multipart) {
//                        handleMultipart((Multipart) content, writer);
//                    } else {
//                        writer.write("İçerik: " + content.toString() + "\n");
//                    }
//                } catch (IOException | MessagingException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            inbox.close(false);
//            store.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void handleMultipart(Multipart multipart, BufferedWriter writer) throws MessagingException, IOException {
//        for (int i = 0; i < multipart.getCount(); i++) {
//            BodyPart bodyPart = multipart.getBodyPart(i);
//            String contentType = bodyPart.getContentType();
//            writer.write("Parça " + (i + 1) + " - İçerik Tipi: \n" + contentType + "\n");
//            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) || bodyPart.getFileName() != null) {
//                saveAttachment(bodyPart, i);
//            } else if (bodyPart.isMimeType("text/plain")) {
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(bodyPart.getInputStream()))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        writer.write("Düz Metin İçerik: " + line + "\n");
//                    }
//                }
//            } else if (bodyPart.isMimeType("text/html")) {
//                writer.write("HTML İçerik: " + bodyPart.getContent() + "\n");
//            } else if (bodyPart.getContent() instanceof Multipart) {
//                handleMultipart((Multipart) bodyPart.getContent(), writer);
//            } else if (bodyPart.getContent() instanceof InputStream) {
//                handleInputStream((InputStream) bodyPart.getContent(), writer);
//            } else {
//                writer.write("Diğer İçerik: " + bodyPart.getContent() + "\n");
//            }
//        }
//    }
//
//    private static void handleInputStream(InputStream is, BufferedWriter writer) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        String line;
//        while ((line = reader.readLine()) != null) {
//            writer.write(line);
//            writer.newLine();
//        }
//    }
//
//    private static void saveAttachment(BodyPart bodyPart, int i) throws MessagingException, IOException {
//        File dir = new File("attachments/email_" + (i + 1));
//        if (!dir.exists()) dir.mkdirs();
//        String fileName = bodyPart.getFileName();
//        File file = new File(dir, fileName);
//        try (InputStream is = bodyPart.getInputStream();
//             FileOutputStream fos = new FileOutputStream(file)) {
//            byte[] buf = new byte[4096];
//            int bytesRead;
//            while ((bytesRead = is.read(buf)) != -1) {
//                fos.write(buf, 0, bytesRead);
//            }
//            System.out.println("Ek kaydedildi: " + file.getAbsolutePath());
//        }
//    }
//
//    public static void mailSaver(String from, String to, String subject, String message) {
//        String path = "emails/sent/" + from + "/" + subject + ".txt";
//
//        String[] content = {subject, from, to, message};
//        try {
//            File saver = new File(path);
//            saver.getParentFile().mkdirs();
//            System.out.println(saver.getName() + " adlı dosya oluşturuldu.");
//            FileWriter fw = new FileWriter(path);
//            BufferedWriter bw = new BufferedWriter(fw);
//
//            for (String line : content) {
//                bw.write(line);
//                bw.newLine();
//            }
//            bw.flush();
//            bw.close();
//        } catch (Exception e) {
//            System.out.println("Gönderilen dosya kaydedilemedi.");
//            e.printStackTrace();
//        }
//    }
//
//    public static void mailLister(String email) {
//        String path = "emails/sent/" + email;
//        int i = 0;
//
//        try {
//            File lister = new File(path);
//
//            if (!lister.exists()) {
//                System.out.println("Gönderilmiş bir mail yok!");
//                return;
//            }
//            File[] txtFiles = lister.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
//
//            if (txtFiles != null && txtFiles.length > 0) {
//                System.out.println("Bulunan dosyalar: ");
//                for (File dosya : txtFiles) {
//                    System.out.println((i + 1) + ". gönderilen mail");
//                    System.out.println(dosya.getName());
//                    i++;
//                }
//            } else {
//                System.out.println("Gönderilen bir mail bulunamadı");
//            }
//        } catch (Exception e) {
//            System.out.println("Dosya bulunamadı");
//        }
//    }
//}
package org.example;

import org.example.Email;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.MimeUtility;

public class RecieveMail {
    public Map<Integer, Email> fetchEmails() {
        Map<Integer, Email> emailMap = new HashMap<>();
        String host = "imap.gmail.com";
        String username = "iamtheone.javaproje@gmail.com";
        String password = "dnhz mqsf buou dfxd";

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

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                Email eMail = new Email();
                String subject = message.getSubject();
                eMail.setSubject(subject);
                eMail.setFrom(message.getFrom()[0].toString());
                Object content = message.getContent();
                if (content instanceof String) {
                    eMail.setContent(content.toString());
                } else if (content instanceof Multipart) {
                    handleMultipart((Multipart) content, eMail);
                } else {
                    eMail.setContent(decodeContent(content));
                }
                emailMap.put(i, eMail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailMap;
    }

    public static void handleMultipart(Multipart multipart, Email email) throws MessagingException, IOException {
        StringBuilder contentBuilder = new StringBuilder();
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) || bodyPart.getFileName() != null) {
                saveAttachment(bodyPart, i);
            } else if (bodyPart.isMimeType("text/plain")) {
                contentBuilder.append(decodeContent(bodyPart.getContent())).append("\n");
            } else if (bodyPart.isMimeType("text/html")) {
                // HTML içeriği atla
            } else if (bodyPart.getContent() instanceof Multipart) {
                handleMultipart((Multipart) bodyPart.getContent(), email);
            } else {
                contentBuilder.append(decodeContent(bodyPart.getContent())).append("\n");
            }
        }
        if (email.getContent() != null) {
            email.setContent(email.getContent() + contentBuilder.toString());
        } else {
            email.setContent(contentBuilder.toString());
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
}
