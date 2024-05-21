package org.example;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.example.AccountSelectionScreen.readAccountsFromFile;

public class Main {
    public static void main(String[] args) {
//        MailManagement mm = new MailManagement();
//        mm.isEmailLegal("iamtheone.javaproje@gmail.com","dnhz mqsf buou dfxd");
//        mm.fetchEmails();
//        mm.sendPlainTextEmail("iamtheone.javaproje@gmail.com","skocraft05@gmail.com","File test","testi",true);

//        MailManagement.mailSaver("iamtheone.javaproje@gmail.com","skocraft05@gmail.com","File test","Model-View-Controller (MVC), yazılım geliştirme sürecinde sıklıkla kullanılan bir tasarım desenidir. Bu desen, bir uygulamanın yapısal bütünlüğünü korumak ve farklı bileşenler arasında net bir ayrım sağlamak için kullanılır.");
//        MailManagement.draftSaver("iamtheone.javaproje@gmail.com","skocraft05@gmail.com","File test","test");
//        MailManagement.mailLister("iamtheone.javaproje@gmail.com");
//        MailManagement.listDraftFiles();


//        MailManagement rm = new MailManagement();
//        rm.fetchEmails(-1,"Sent");

//        List<Map<String, String>> emails = EmailReader.readEmails("emails/inbox");
//        //Ana ekranı çağırmak için
////        GUIMainScreen anaEkran = new GUIMainScreen(emails);
        List<AccountSelectionScreen.Account> accounts = readAccountsFromFile();
//
//        // Start the account selection screen
        new AccountSelectionScreen(accounts);
//
//
//        // Giriş yapılan hesapları döndüren fonksiyon.
//        listTxtFiles();
//        List<String> txtFileNames = listTxtFiles();
//
//        for (String fileName : txtFileNames) {
//            System.out.println(fileName);
//        }
//
        deleteFilesInDirectory("Accounts/inbox");

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
    private static void deleteFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }
}
