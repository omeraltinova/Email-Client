package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountSelectionScreen {
    private JFrame accountSelectionFrame;
    private List<Account> accounts;
    private JPanel accountsPanel;
    static int i=1;

    public AccountSelectionScreen(List<Account> accounts) {
        this.accounts = accounts != null ? accounts : new ArrayList<>();
        accountSelectionFrame = new JFrame("Hesap Seçimi");
        accountSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accountSelectionFrame.setSize(800, 600);
        accountSelectionFrame.setLayout(new BorderLayout());
        accountSelectionFrame.setLocationRelativeTo(null);

        initializeUI();

        accountSelectionFrame.setVisible(true);
    }

    private void initializeUI() {
        JLabel titleLabel = new JLabel("WELCOME!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(45, 52, 54));

        accountsPanel = new JPanel();
        accountsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        accountsPanel.setBackground(new Color(33, 33, 33));

        updateAccountsPanel();

        accountSelectionFrame.add(titleLabel, BorderLayout.NORTH);
        accountSelectionFrame.add(accountsPanel, BorderLayout.CENTER);
    }

    private void updateAccountsPanel() {
        accountsPanel.removeAll();

        for (Account account : accounts) {
            JPanel accountPanel = createAccountPanel(account);
            accountsPanel.add(accountPanel);
        }

        if (accounts.size() < 4) {
            JButton addButton = new JButton("+");
            addButton.setPreferredSize(new Dimension(100, 100));
            addButton.setFont(new Font("Arial", Font.BOLD, 36));
            addButton.setFocusPainted(false);
            addButton.setBackground(new Color(45, 52, 54));
            addButton.setForeground(Color.WHITE);
            addButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            addButton.addActionListener(e -> {
                accountSelectionFrame.dispose();
                System.out.println("Hesap ekleme butonuna tıklandı");
                KAYITEKRANI();
            });
            accountsPanel.add(addButton);
        }

        accountsPanel.revalidate();
        accountsPanel.repaint();
    }


    private JPanel createAccountPanel(Account account) {
        JPanel accountPanel = new JPanel(new BorderLayout());
        accountPanel.setPreferredSize(new Dimension(100, 100));
        accountPanel.setBackground(new Color(45, 52, 54));

        JLabel accountImageLabel = new JLabel();
        accountImageLabel.setIcon(resizeIcon(new ImageIcon(account.getProfilePicturePath()), 100, 100));
        accountImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nameLabel = new JLabel(account.getName(), SwingConstants.CENTER);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel emailLabel = new JLabel(account.getEmail(), SwingConstants.CENTER);
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 10));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(new Color(45, 52, 54));
        textPanel.add(nameLabel, BorderLayout.NORTH);
        textPanel.add(emailLabel, BorderLayout.SOUTH);

        accountPanel.add(accountImageLabel, BorderLayout.CENTER);
        accountPanel.add(textPanel, BorderLayout.SOUTH);
        Map<String, String> accountInfo = new HashMap<>();
        // Click listener for account panel
        accountPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // Display email address of the clicked account
//Map<String,
                File accountSelection = new File("Accounts");
                File[] files = accountSelection.listFiles();

                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().equals(account.email + ".txt")) {
                            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                                String line;
                                while ((line = br.readLine()) != null) {
                                    String[] parts = line.split(":");
                                    if (parts.length == 2) {
                                        String key = parts[0].trim();
                                        String value = parts[1].trim();
                                        switch (key) {
                                            case "Name":
                                                Mail.setUSERNAME(value);
                                                accountInfo.put("Name",value);
                                                break;
                                            case "Email":
                                                MailManagement.setUSERNAME(value);
                                                accountInfo.put("Email",value);
                                                break;
                                            case "Şifre":
                                                MailManagement.setPASSWORD(value);
                                                break;
                                            case "Host":
                                                MailManagement.setHOST(value);
                                                break;
                                            case "ImapHost":
                                                MailManagement.setImapHost(value);
                                                break;
                                            case "Port":
                                                MailManagement.setPORT(value);
                                                break;
                                            case "ImapPort":
                                                MailManagement.setImapPort(value);
                                                break;
                                            case "Image":
                                                MailManagement.setIMAGE(value);
                                                accountInfo.put("Image",value);
                                                break;
                                        }
                                    }
                                }
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                            MailManagement mm = new MailManagement();
                            mm.fetchEmails("","inbox");
                            mm.fetchEmails("","sent");
                       //     Map<String, String> accountInfo = EmailReader.readEmails("Accounts");
                            List<Map<String, String>> receivedEmails = EmailReader.readEmails("emails/inbox");
                            List<Map<String, String>> sentEmails = EmailReader.readEmails("emails/sent");
                            if(receivedEmails.size()==0){
                                JOptionPane.showMessageDialog(accountSelectionFrame, "Hatalı şifre girdiniz\nHesap silindi tekrar giriş yapın");
                                File silici = new File("Accounts/"+account.getEmail()+".txt");
                                silici.delete();
                                accounts.remove(account);
                                updateAccountsPanel();
                            }
                            else{
                                //Ana ekranı çağırmak için
                                GUIMainScreen anaEkran = new GUIMainScreen((List<Map<String, String>>) receivedEmails,(List<Map<String, String>>) sentEmails,(Map<String,String>) accountInfo);
                                accountSelectionFrame.dispose();

                            }
                        }
                    }
                }

            }
        });

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Sil");
        deleteItem.addActionListener(e -> {
            i--;
            accounts.remove(account);
            deleteAccount(account.email);
            updateAccountsPanel();
        });
        popupMenu.add(deleteItem);

        accountPanel.setComponentPopupMenu(popupMenu);

        return accountPanel;
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public void KAYITEKRANI() {
        JFrame f1 = new JFrame("Giris Yapma Ekrani");
        JLabel lblName=new JLabel("Enter your name:");
        lblName.setForeground(Color.WHITE);
        lblName.setBounds(10,10,200,50);
        JLabel lblEposta = new JLabel("Enter your e-Mail adress:");
        lblEposta.setForeground(Color.WHITE);
        lblEposta.setBounds(10, 80, 200, 50);
        JLabel lblSifre = new JLabel("Enter your password:");
        lblSifre.setForeground(Color.WHITE);
        lblSifre.setBounds(10, 150, 200, 50);
        JTextField textName=new JTextField();
        textName.setBounds(240,10,300,60);
        JTextField textEposta = new JTextField();
        textEposta.setBounds(240, 80, 300, 60);
        JPasswordField password = new JPasswordField();
        password.setBounds(240, 150, 300, 60);
        JButton btnOturumAc = new JButton("Sign in");
        btnOturumAc.setBackground(new Color(45, 52, 54));
        btnOturumAc.setForeground(Color.WHITE);
        btnOturumAc.setBounds(330, 220, 200, 60);
        btnOturumAc.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        btnOturumAc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name=textName.getText();
                String eposta = textEposta.getText();
                char[] passwordChars = password.getPassword();
                String sifre = new String(passwordChars);
                MailManagement.setNAME(name);
                MailManagement ts1 = new MailManagement();
                boolean check = ts1.isEmailLegal(eposta,sifre);

                if(!check){
                    // Perform your login logic here using eposta and sifre
                    System.out.println("Name:" + name);
                    System.out.println("E-posta: " + eposta);
                    System.out.println("Şifre: " + sifre);

                    // Add new account to the list
                    i = Mail.countTxtFiles("Accounts");
                    if (i==1)
                        accounts.add(new Account(name, eposta, "profile-photos/bear.png"));
                    else if (i==2)
                        accounts.add(new Account(name, eposta, "profile-photos/cat.png"));
                    else if (i==3)
                        accounts.add(new Account(name, eposta, "profile-photos/panda.png"));
                    else if (i==4)
                        accounts.add(new Account(name, eposta, "profile-photos/rabbit.png"));


                }
                else{
                    System.out.println("Geçersiz domain adresi.");
                    JOptionPane.showMessageDialog(f1, "Desteklenmeyen domain girdiniz" );
                }



                updateAccountsPanel();
                f1.dispose();
                accountSelectionFrame.setVisible(true);
            }
        });

        f1.add(lblName);
        f1.add(lblEposta);
        f1.add(lblSifre);
        f1.add(textName);
        f1.add(textEposta);
        f1.add(password);
        f1.add(btnOturumAc);

        f1.getContentPane().setBackground(new Color(33, 33, 33));
        f1.setLayout(null);
        f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f1.setSize(600, 330);
        f1.setLocationRelativeTo(null);
        f1.setVisible(true);
    }
    public static void deleteAccount(String email){
        File silici = new File("Accounts/"+email+".txt");
        silici.delete();
    }
//    public static void main(String[] args) {
//        // Read account information from files
//        List<Account> accounts = readAccountsFromFile();
//
//        // Start the account selection screen
//        new AccountSelectionScreen(accounts);
//    }

    public static List<Account> readAccountsFromFile() {
        List<Account> accounts = new ArrayList<>();
        File folder = new File("accounts");
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        String name = null, email = null, image = null;
                        while ((line = br.readLine()) != null) {
                            String[] parts = line.split(":");
                            if (parts.length == 2) {
                                String key = parts[0].trim();
                                String value = parts[1].trim();
                                switch (key) {
                                    case "Name":
                                        name = value;
                                        break;
                                    case "Email":
                                        email = value;
                                        break;
                                    case "Image":
                                        image = value;
                                        break;
                                }
                            }
                        }
                        if (name != null && email != null) {
                            accounts.add(new Account(name, email,image));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return accounts;
    }

    public static class Account {
        private String name;
        private String email;
        private String profilePicturePath;

        public Account(String name, String email, String profilePicturePath) {
            this.name = name;
            this.email = email;
            this.profilePicturePath = profilePicturePath;
        }

        public Account(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getProfilePicturePath() {
            return profilePicturePath;
        }
    }
}
