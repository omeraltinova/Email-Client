package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mail{


    protected static String USERNAME = "";
    protected static String PASSWORD = "";
    protected static String HOST = ""; //SMTP HOST
    protected static String PORT = ""; //SMTP PORT
    protected static String IMAP_HOST = "";
    protected static String IMAP_PORT = "993";
    protected static String NAME = "NoName"; // Kullanıcı ismi
    protected static String IMAGE = "profile-photos/default-picture.png"; // Kullanıcı profil fotosu dosya yolu


    public static String getUSERNAME() {
        return USERNAME;
    }

    public static void setUSERNAME(String USERNAME2) {
        USERNAME = USERNAME2;
        System.out.println(USERNAME2);
        System.out.println(USERNAME);
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String PASSWORD2) {
        PASSWORD = PASSWORD2;
        System.out.println(PASSWORD);
    }

    public static String getHOST() {
        return HOST;
    }

    public static void setHOST(String HOST2) {
        HOST = HOST2;
    }

    public static String getPORT() {
        return PORT;
    }

    public static void setPORT(String PORT2) {
        PORT = PORT2;
    }

    public static String getNAME() {
        return NAME;
    }

    public static void setNAME(String NAME2) {
        NAME = NAME2;
    }

    public static String getIMAGE() {
        return IMAGE;
    }

    public static void setIMAGE(String IMAGE2) {
        IMAGE = IMAGE2;
    }

    public static String getImapPort() {
        return IMAP_PORT;
    }

    public static void setImapPort(String imapPort2) {
        IMAP_PORT = imapPort2;
    }

    public static String getImapHost() {
        return IMAP_HOST;
    }

    public static void setImapHost(String imapHost) {
        IMAP_HOST = imapHost;
    }

    public boolean isEmailLegal(String email,String password)
    {
        String patternString = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            System.out.println("Girilen metin bir e-posta adresidir.");
            int hesapsayisi = countTxtFiles("Accounts");
            System.out.println("Hesap sayısı "+ hesapsayisi);
            switch (hesapsayisi){
                case 0:
                    setIMAGE("profile-photos/bear.png");
                    break;
                case 1:
                    setIMAGE("profile-photos/cat.png");
                    break;
                case 2:
                    setIMAGE("profile-photos/panda.png");
                    break;
                case 3:
                    setIMAGE("profile-photos/rabbit.png");
                    break;
                default:
                    setIMAGE("profile-photos/default-picture.png");
                    break;
            }
            setUSERNAME(email);
            setPASSWORD(password);
            findPort(email);
            findHost(email);
            setFile(email);
            return(false);

        } else {
            System.out.println("Girilen metin bir e-posta adresi değildir.");
            return(true);
        }


    }

    public static void findPort(String email){
        int startIndex = email.indexOf("@"); // @ indexi
        String googleDomain = "gmail.com";
        String outLookDomain = "outlook.com";

        if(startIndex!=-1){ // Eğer @a'ten sonra bir şey yoksa -1 döndürür
            String domain = email.substring(startIndex+1); // @ işaretinden sonra sona kadarki kısım.

            if(domain.equals(googleDomain) ){
                setPORT("587");
                setImapPort("993");
            } else if (domain.equals(outLookDomain)) {
                setPORT("587");
                setImapPort("993");
            }
        }
    }

    public static void findHost(String email){
        int startIndex = email.indexOf("@"); // @ indexi
        String googleDomain = "gmail.com";
        String outLookDomain = "outlook.com";

        if(startIndex!=-1){ // Eğer @a'ten sonra bir şey yoksa -1 döndürür
            String domain = email.substring(startIndex+1); // @ işaretinden sonra sona kadarki kısım.

            if(domain.equals(googleDomain) ){
                setHOST("smtp.gmail.com");
                setImapHost("imap.gmail.com");
                System.out.println("google domain");
            } else if (domain.equals(outLookDomain)) {
                setHOST("smtp-mail.outlook.com");
                setImapHost("outlook.office365.com");
                System.out.println("outlook domain");
            }
        }
    }

    public void setFile(String email){
        String filePath = ("Accounts/"+email+".txt");
        String[] info = {
                "Name : "+ getNAME(),
                "Email : "+ getUSERNAME(),
                "Şifre : "+ getPASSWORD(),
                "Host : "+ getHOST(),
                "ImapHost : "+ getImapHost(),
                "Port : "+ getPORT(),
                "ImapPort : "+ getImapPort(),
                "Image : " + getIMAGE()};
        try {
            // Dosyayı oluştur
            File file = new File(filePath);

            // Dosyayı aç ve içine yaz
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            for (String line : info) { // 1. satırdan info bitene kadar
                bw.write(line); // satırı yaz
                bw.newLine(); // Yeni satıra geç
            }

            // Buffer'ı temizle ve dosyayı kapat
            bw.flush();
            bw.close();

            System.out.println("Dosya oluşturuldu ve içine yazıldı.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int countTxtFiles(String path) {
        File folder = new File(path);
        int counter = 0;

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

            if (files != null) {
                for (File file : files) {
                    counter++;
                }
            }
        } else {
            System.out.println("Klasör bulunamadı: " + "Accounts");
        }

        return (counter);
    }

}