package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckMail extends Mail{

    public boolean isEmailLegal(String email,String password)
    {
        String patternString = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            System.out.println("Girilen metin bir e-posta adresidir.");
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
        String outLookDomain = "outloook.com";

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
        String outLookDomain = "outloook.com";

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
                getUSERNAME(),
                getPASSWORD(),
                getHOST(),
                getImapHost(),
                getPORT(),
                getImapPort(),
        };
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
    public static String getCheckUSERNAME(){
        return(getUSERNAME());
    }



}
