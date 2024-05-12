package org.example;
import javax.mail.*;
import java.util.Properties;

public class RevieveMail {
    public void fetchEmails() {
        // Gmail hesap bilgileri
        String host = "pop.gmail.com";
        String username = "iamtheone.javaproje@gmail.com"; // Gmail adresinizi buraya yazın
        String password = "dnhz mqsf buou dfxd"; // Gmail şifrenizi buraya yazın

        try {
            // Mail sunucusuna bağlanmak için gerekli özellikler
            Properties props = new Properties();
            props.setProperty("mail.pop3.host", host);
            props.setProperty("mail.pop3.port", "995");
            props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.pop3.socketFactory.fallback", "false");

            // Session oluştur
            Session session = Session.getDefaultInstance(props);
            // Mail sunucusuna bağlan
            Store store = session.getStore("pop3");
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
            }

            // Bağlantıyı kapat
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
