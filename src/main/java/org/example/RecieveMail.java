package org.example;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.SubjectTerm;

public class RecieveMail {
    public static void main(String[] args) {
        // IMAP sunucusu ayarları
        String host = "imap.example.com";
        String username = "your-email@example.com";
        String password = "your-password";

        // JavaMail oturum özellikleri
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imap");
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.ssl.enable", "true");

        try {
            // Oturum oluştur
            Session emailSession = Session.getDefaultInstance(properties);

            // IMAP Store'a bağlan
            Store store = emailSession.getStore("imap");
            store.connect(host, username, password);

            // "Inbox" klasörünü aç
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            // Silmek istediğiniz e-postayı bulun (örneğin konu başlığına göre)
            Message[] messages = emailFolder.search(new SubjectTerm("Silinecek e-posta konusu"));

            if (messages.length > 0) {
                for (Message message : messages) {
                    // E-postayı sil
                    message.setFlag(Flags.Flag.DELETED, true);
                }
                System.out.println("E-posta(lar) başarıyla silindi.");
            } else {
                System.out.println("Belirtilen konu başlığına sahip e-posta bulunamadı.");
            }

            // Klasörü kapat ve bağlantıyı kes
            emailFolder.close(true);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}