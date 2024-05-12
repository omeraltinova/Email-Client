package org.example;

public class CheckMail {
//    private String email;
    public boolean isEmailLegal(String email)
    {
        int index = email.indexOf("@");
        if (index == 0 || index == -1) {
            System.out.println("This e-mail is unvalid please use a valid e-mail");// EXCEPTİON !!!
            return false;
        }
        
        return true;
    }
}
