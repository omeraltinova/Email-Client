package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("MMMMmmmherbaaaa ARkadaşlarrrr ");
        CheckMail check = new CheckMail();
        check.isEmailLegal("as@hotmail.com");
        MailManagement mm = new MailManagement();
       mm.fetchEmails();
    }
}