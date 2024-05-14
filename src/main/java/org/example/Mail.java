package org.example;

public class Mail{


    static CheckMail checker = new CheckMail();


    private static String USERNAME = "ruhicenet123_javaproje@outlook.com";
    private static String PASSWORD = "RuhiBaba123_Java";
    private static String HOST = "smtp-mail.outlook.com";
    private static String PORT = "587";
    private static String NAME = ""; // Kullanıcı ismi
    private static String IMAGE = "profile-photos/default-picture.png"; // Kullanıcı profil fotosu dosya yolu



    public static String getUSERNAME() {
        return USERNAME;
    }

    public static void setUSERNAME(String USERNAME) {
        Mail.USERNAME = USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static void setPASSWORD(String PASSWORD) {
        Mail.PASSWORD = PASSWORD;
    }

    public static String getHOST() {
        return HOST;
    }

    public static void setHOST(String HOST) {
        Mail.HOST = HOST;
    }

    public static String getPORT() {
        return PORT;
    }

    public static void setPORT(String PORT) {
        Mail.PORT = PORT;
    }

    public static String getNAME() {
        return NAME;
    }

    public static void setNAME(String NAME) {
        Mail.NAME = NAME;
    }

    public static String getIMAGE() {
        return IMAGE;
    }

    public static void setIMAGE(String IMAGE) {
        Mail.IMAGE = IMAGE;
    }

    public void setValues(String USERNAME, String PASSWORD, String HOST, String PORT) {
        Mail.USERNAME=USERNAME;
        Mail.PASSWORD=PASSWORD;
        Mail.HOST=HOST;
        Mail.PORT=PORT;
    }
}
