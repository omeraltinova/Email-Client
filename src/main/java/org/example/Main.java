package org.example;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
          MailManagement check = new MailManagement();
          System.out.println(check.isEmailLegal("ruhicenet123_javaproje@outlook.com","RuhiBaba123_Java")); // Mail gönderme sistem
//        MailManagement mm = new MailManagement();
//       mm.fetchEmails();
         RecieveMail rm = new RecieveMail();
         rm.fetchEmails();

//        MailManagement m1 = new MailManagement();
//
//        m1.sendPlainTextEmail("ruhicenet123_javaproje@outlook.com","skocraft05@gmail.com","test","test",true); // ÇALIŞTIRMAYIN!!!

        List<Map<String, String>> emails = EmailReader.readEmails("emails");
        //Ana ekranı çağırmak için
        GUIMainScreen anaEkran=new GUIMainScreen(emails);

    }
}