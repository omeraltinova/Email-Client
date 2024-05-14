package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class AttachmentOpener {

    public static void main(String[] args) {
        String emailContentFilePath = "email_contents.txt";
        String attachmentsFolderPath = "attachments/";

        try (BufferedReader reader = new BufferedReader(new FileReader(emailContentFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("name=") && (line.contains(".png") || line.contains(".jpeg"))) {
                    String[] parts = line.split("name=");
                    String fileNameWithEquals = parts[1].trim(); // "name=" ifadesinden sonra gelen kısmı alır ve boşlukları temizler
                    String[] fileNameParts = fileNameWithEquals.split(";"); // Dosya adını almak için ";" karakterine göre böler
                    System.out.println(fileNameParts[0]);
                    String fileName = fileNameParts[0].trim();
                    System.out.println(fileName);// Dosya adı
                    String[] fileNameExtensionParts = fileName.split("\\."); // Dosya adını ve uzantısını almak için noktaya göre böler
                    String extension = fileNameExtensionParts[1].trim(); // Dosya uzantısı
                    String attachmentFileName = fileName; // Dosya adı
                    String attachmentFilePath = attachmentsFolderPath + attachmentFileName; // Dosyanın tam yolu

                    File attachmentFile = new File(attachmentFilePath);
                    if (attachmentFile.exists()) {
                        openAttachment(attachmentFilePath);
                    } else {
                        System.out.println("Ek dosyası bulunamadı: " + attachmentFileName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void openAttachment(String filePath) {
        // Dosyayı açmak için kullanılacak yöntemi burada uygulayabilirsiniz
        // Bu örnekte, sadece dosyanın tam yolunu yazdırıyoruz
        System.out.println("Dosya açılıyor: " + filePath);
    }
}



