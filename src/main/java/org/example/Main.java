package org.example;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
       // CheckMail check = new CheckMail();
//        MailManagement mm = new MailManagement();
//        mm.fetchEmails();
//        mm.sendPlainTextEmail("ruhicenet123_javaproje@outlook.com","skocraft05@gmail.com","test","test",true);
        ReciveMail rm = new ReciveMail();
        rm.fetchEmails();

        List<Map<String, String>> emails = EmailReader.readEmails("emails/inbox");
        //Ana ekranı çağırmak için
        GUIMainScreen anaEkran = new GUIMainScreen(emails);

    }
}