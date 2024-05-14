package org.example;

public class Mail{


    //static CheckMail checker = new CheckMail();


    private static String USERNAME = "";
    private static String PASSWORD = "";
    private static String HOST = ""; //SMTP HOST
    private static String PORT = ""; //SMTP PORT
    private static String IMAP_HOST = "";
    private static String IMAP_PORT = "";
    private static String NAME = "NoName"; // Kullanıcı ismi
    private static String IMAGE = "profile-photos/default-picture.png"; // Kullanıcı profil fotosu dosya yolu


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

    /*public void setValues(String USERNAME, String PASSWORD, String HOST, String PORT) {
        Mail.USERNAME=USERNAME;
        Mail.PASSWORD=PASSWORD;
        Mail.HOST=HOST;
        Mail.PORT=PORT;
    }*/
}
