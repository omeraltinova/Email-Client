package org.example;
import javax.mail.*;
import java.io.*;
import java.util.Properties;

public class RecieveMail {
    public void fetchEmails() {
        // Gmail hesap bilgileri
        String host = "imap.gmail.com";
        String username = "iamtheone.javaproje@gmail.com"; // Gmail adresinizi buraya yazın
        String password = "dnhz mqsf buou dfxd"; // Gmail şifrenizi buraya yazın

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("email_contents.txt"))) {
            // Mail sunucusuna bağlanmak için gerekli özellikler
            Properties props = new Properties();
            props.setProperty("mail.imap.host", host);
            props.setProperty("mail.imap.port", "993");
            props.setProperty("mail.imap.ssl.enable", "true");
            //props.setProperty("mail.debug", "true"); // Hata ayıklama bilgilerini etkinleştir

            // Session oluştur
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // Debug için konsolda detaylı bilgi çıktısı al
            //session.setDebug(true);

            // Mail sunucusuna bağlan
            Store store = session.getStore("imap");
            store.connect(host, username, password);

            // Inbox klasörüne eriş
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Inbox'taki mesajları al
            Message[] messages = inbox.getMessages();
            writer.write("Gelen kutusundaki mesajlar:\n");

            // Mesajları dosyaya yazdır ve ekleri kaydet
            for (Message message : messages) {
                writer.write("Konu: " + message.getSubject() + "\n");
                writer.write("Gönderen: " + message.getFrom()[0] + "\n");
                Object content = message.getContent();
                if (content instanceof String) {
                    // İçerik metin tipinde ise direkt olarak alınabilir
                    writer.write("İçerik: " + (String) content + "\n");
                } else if (content instanceof Multipart) {
                    // Multipart içeriği işleme
                    handleMultipart((Multipart) content, writer);
                } else {
                    // Diğer durumlarda, içeriği dönüştürme veya işleme yöntemlerinize göre işlem yapılabilir
                    writer.write("İçerik: " + content.toString() + "\n");
                }
                writer.write("\n"); // Mesajlar arasında boşluk bırak
            }

            // Bağlantıyı kapat
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
            writer.write("Parça " + (i + 1) + " - İçerik Tipi: " + contentType + "\n");
            if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) || bodyPart.getFileName() != null) {
                saveAttachment(bodyPart);
            } else if (bodyPart.isMimeType("text/plain")) {
                writer.write("Düz Metin İçerik: " + bodyPart.getContent() + "\n");
            } else if (bodyPart.isMimeType("text/html")) {
                writer.write("HTML İçerik: " + bodyPart.getContent() + "\n");
            } else if (bodyPart.getContent() instanceof Multipart) {
                // Multipart içeren parçaları ayrıştır
                handleMultipart((Multipart) bodyPart.getContent(), writer);
            } else {
                writer.write("Diğer İçerik: " + bodyPart.getContent() + "\n");
            }
        }
    }

    private static void saveAttachment(BodyPart bodyPart) throws MessagingException, IOException {
        File dir = new File("attachments");
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



