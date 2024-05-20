package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountSelectionScreen {
    private JFrame accountSelectionFrame;
    private List<Account> accounts;
    private JPanel accountsPanel;

    public AccountSelectionScreen(List<Account> accounts) {
        this.accounts = accounts != null ? accounts : new ArrayList<>();
        accountSelectionFrame = new JFrame("Hesap Seçimi");
        accountSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accountSelectionFrame.setSize(800, 600);
        accountSelectionFrame.setLayout(new BorderLayout());

        initializeUI();

        accountSelectionFrame.setVisible(true);
    }

    private void initializeUI() {
        JLabel titleLabel = new JLabel("Hoşgeldin", SwingConstants.CENTER);
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

        // Click listener for account panel
        accountPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                super.mouseClicked(e);
                // Display email address of the clicked account
//                File accounts = new File();
            }
        });

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Sil");
        deleteItem.addActionListener(e -> {
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
        JLabel lblEposta = new JLabel("E-posta adresinizi giriniz:");
        lblEposta.setBounds(10, 10, 200, 50);
        JLabel lblSifre = new JLabel("Şifrenizi giriniz:");
        lblSifre.setBounds(10, 70, 200, 50);
        JTextField textEposta = new JTextField();
        textEposta.setBounds(240, 10, 300, 60);
        JPasswordField password = new JPasswordField();
        password.setBounds(240, 70, 300, 60);
        JButton btnOturumAc = new JButton("Oturum Aç");
        btnOturumAc.setBounds(330, 140, 200, 60);

        btnOturumAc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String eposta = textEposta.getText();
                char[] passwordChars = password.getPassword();
                String sifre = new String(passwordChars);

                // Perform your login logic here using eposta and sifre
                System.out.println("E-posta: " + eposta);
                System.out.println("Şifre: " + sifre);

                // Add new account to the list
                accounts.add(new Account("Alice", eposta, "profile-photos/default-picture.png"));
                MailManagement ts1 = new MailManagement();
                ts1.isEmailLegal(eposta,sifre);
                updateAccountsPanel();

                f1.dispose();
            }
        });

        f1.add(lblEposta);
        f1.add(lblSifre);
        f1.add(textEposta);
        f1.add(password);
        f1.add(btnOturumAc);

        f1.setLayout(null);
        f1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f1.setSize(600, 300);
        f1.setVisible(true);
    }
    public static void deleteAccount(String email){
        File silici = new File("Accounts/"+email+".txt");
        silici.delete();
    }
    public static void main(String[] args) {
        // Read account information from files
        List<Account> accounts = readAccountsFromFile();

        // Start the account selection screen
        new AccountSelectionScreen(accounts);
    }

    private static List<Account> readAccountsFromFile() {
        List<Account> accounts = new ArrayList<>();
        File folder = new File("accounts");
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line;
                        String name = null, email = null;
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
                                }
                            }
                        }
                        if (name != null && email != null) {
                            accounts.add(new Account(name, email));
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
