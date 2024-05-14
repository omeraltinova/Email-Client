package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckMail extends Mail{
    public boolean isEmailLegal(String email)
    {
        String patternString = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            System.out.println("Girilen metin bir e-posta adresidir.");
            return(false);

        } else {
            System.out.println("Girilen metin bir e-posta adresi değildir.");
            return(true);
        }


    }

    public static String findPort(String email){
        int startIndex = email.indexOf("@");

        return("true");
    }

    public static String findServer(String email){
        return("31");
    }

    public void setFile(){
    }
}
