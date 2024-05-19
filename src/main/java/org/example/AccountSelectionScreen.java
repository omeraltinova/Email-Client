package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AccountSelectionScreen {
    private JFrame accountSelectionFrame;
    private List<Account> accounts;
    private JPanel accountsPanel;

    public AccountSelectionScreen(List<Account> accounts) {
        this.accounts = accounts;
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
                // Hesap ekleme işlemi burada yapılacak
                System.out.println("Hesap ekleme butonuna tıklandı");
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

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Sil");
        deleteItem.addActionListener(e -> {
            accounts.remove(account);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Account> dummyAccounts = new ArrayList<>();
            dummyAccounts.add(new Account("Alice", "alice@example.comasdaasdasd", "profile-photos/default-picture.png"));
            dummyAccounts.add(new Account("Bob", "bob@example.comasdasdasd", "profile-photos/default-picture.png"));

            new AccountSelectionScreen(dummyAccounts);
        });
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


