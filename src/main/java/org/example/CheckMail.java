package org.example;

public class CheckMail {
    private String email;
    public boolean isEmailIllegal() {
        int index = this.email.indexOf("@");
        if (index == 0 || this.email.length() - index <= 5) {
            System.out.println("This e-mail is unvalid please use a valid e-mail");
            return true;
        }
        return false;
    }
}
