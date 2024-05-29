package org.example;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import static org.example.AccountSelectionScreen.readAccountsFromFile;

public class Main {
    public static void main(String[] args) throws IOException {
        // Program dizini oluşturma ve gerekli dosyaları indirme
        destroyerOfTheWorlds();

        // İndirilecek dosya URL'leri
        String[] fileURLs = {
                "https://raw.githubusercontent.com/omeraltinova/Email-Client/master/profile-photos/bear.png",
                "https://raw.githubusercontent.com/omeraltinova/Email-Client/master/profile-photos/cat.png",
                "https://raw.githubusercontent.com/omeraltinova/Email-Client/master/profile-photos/rabbit.png",
                "https://raw.githubusercontent.com/omeraltinova/Email-Client/master/profile-photos/panda.png",
                "https://raw.githubusercontent.com/omeraltinova/Email-Client/master/profile-photos/default-picture.png"
        };

        // Dosyaların kaydedileceği dizin
        String saveDir = System.getProperty("user.dir") + "/profile-photos/";

        for (String fileURL : fileURLs) {
            try {
                // URL'den dosya adını çıkaran kısım
                String fileName = Paths.get(new URL(fileURL).getPath()).getFileName().toString();
                String saveFilePath = saveDir + fileName;

                downloadFile(fileURL, saveFilePath);
                System.out.println("Dosya şuraya indirildi: " + saveFilePath);
            } catch (IOException e) {
                System.err.println("Şu dosya indirilemedi: " + fileURL);
                e.printStackTrace();
            }
        }

        // Hesap seçme ekranını başlat
        List<AccountSelectionScreen.Account> accounts = readAccountsFromFile();
        new AccountSelectionScreen(accounts);
    }

    public static List<String> listTxtFiles() {
        File folder = new File("Accounts");
        List<String> txtFileNames = new ArrayList<>();

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

            if (files != null) {
                for (File file : files) {
                    txtFileNames.add(file.getName());
                }
            }
        } else {
            System.out.println("Klasör bulunamadı: " + "Accounts");
        }

        return txtFileNames;
    }
    public static void destroyerOfTheWorlds() {
        // Çalışma dizini
        String currentDir = System.getProperty("user.dir");

        // Oluşturulacak klasörlerin isimleri
        String[] directories = {"/Accounts", "attachments", "emails", "emails/draft","emails/inbox","emails/sent","profile-photos"};

        for (String dir : directories) {
            // Her bir klasörün yolu
            Path path = Paths.get(currentDir, dir);

            try {
                // Klasörü oluştur
                Files.createDirectories(path);
                System.out.println(path.toString() + " adlı klasör oluşturuldu.");
            } catch (IOException e) {
                // Hata durumunda mesaj yazdır
                System.err.println(path.toString()+ " adlı klasör oluşturulurken bir sorun oluştu.");
                e.printStackTrace();
            }
        }
    }
    public static void downloadFile(String fileURL, String saveFilePath) throws IOException {
        URL url = new URL(fileURL);
        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(saveFilePath)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
    }
}
