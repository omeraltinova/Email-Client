package org.example;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        MailManagement mm = new MailManagement();
        mm.isEmailLegal("iamtheone.javaproje@gmail.com","dnhz mqsf buou dfxd");
//        mm.fetchEmails();
//        mm.sendPlainTextEmail("iamtheone.javaproje@gmail.com","skocraft05@gmail.com","File test","testi",true);

        MailManagement.mailSaver("iamtheone.javaproje@gmail.com","skocraft05@gmail.com","File test","Model-View-Controller (MVC), yazılım geliştirme sürecinde sıklıkla kullanılan bir tasarım desenidir. Bu desen, bir uygulamanın yapısal bütünlüğünü korumak ve farklı bileşenler arasında net bir ayrım sağlamak için kullanılır.");
        MailManagement.mailSaver("iamtheone.javaproje@gmail.com","skocraft05@gmail.com","File test2","2. mail");

        MailManagement.mailLister("iamtheone.javaproje@gmail.com");


//        RecieveMail rm = new RecieveMail();
//        rm.fetchEmails();

        List<Map<String, String>> emails = EmailReader.readEmails("emails/inbox");
        //Ana ekranı çağırmak için
        GUIMainScreen anaEkran = new GUIMainScreen(emails);

    }
}