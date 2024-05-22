package org.example;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.example.AccountSelectionScreen.readAccountsFromFile;

public class Main {
    public static void main(String[] args) {

//        List<String> draftFileNames = MailManagement.listDraftFiles(); // Taslakların listesini görme
//
//        for (String fileName : draftFileNames) {
//            System.out.println(fileName);
//        }aaaaaaa

        // Hesap seçme ekranını başlatmannbvbn
        List<AccountSelectionScreen.Account> accounts = readAccountsFromFile();
        new AccountSelectionScreen(accounts);


//        // Giriş yapılan hesapları döndüren fonksiyon.
//        listTxtFiles();
//        List<String> txtFileNames = listTxtFiles();
//
//        for (String fileName : txtFileNames) {
//            System.out.println(fileName);
//        }
//

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
