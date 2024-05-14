package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("MMMMmmmherbaaaa ARkadaşlarrrr ");
        CheckMail check = new CheckMail();
        System.out.println(check.isEmailLegal("as@hotmail.com"));
//        MailManagement mm = new MailManagement();
//       mm.fetchEmails();
        RecieveMail rm = new RecieveMail();
        rm.fetchEmails();

        MailManagement m1 = new MailManagement();

        m1.sendPlainTextEmail("ruhicenet123_javaproje@outlook.com","skocraft05@gmail.com","test","test",true);



    }
}