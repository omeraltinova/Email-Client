package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.Map;

import static org.example.AccountSelectionScreen.readAccountsFromFile;

public class GUIMainScreen implements ActionListener{
    //Main frame
    JFrame mainScreen;

    //Panels

    JPanel selectMenu1;
    JPanel mailPanel;
    JPanel showMailPanel;
    JPanel showReceivedMailsPanel;
    JPanel showSentMailsPanel;
    JPanel sendMailPanel;
    JPanel illusionPanel1;
    JPanel illusionPanel2;
    JPanel receivedMailClosePanel;
    JPanel sentMailClosePanel;

    //Buttons

    JButton received;
    JButton sent;
    JButton sendMail;
    JButton mailSendButton;
    JButton showReceivedClose;
    JButton showSentClose;
    JButton sendMailClose;
    JButton signOutButton;
    JButton refreshButton;
    JButton settingsButton;

    //Scrollbars

    JScrollPane receivedScroll;
    JScrollPane sentScroll;
    JScrollPane receivedContentScroll;
    JScrollPane sentContentScroll;
    JScrollPane sendContentScroll;

    //Text Fields and Areas

    JTextField searchBar;
    JTextArea receivedContent;
    JTextField sentSubject;
    JTextArea sentContent;
    JTextField sentTo;
    JTextField sendMailSubject1;
    JTextField sendMailSubject;
    JTextArea sendMailContent;
    JTextField sendMailTo1;
    JTextField sendMailTo;

    //Toolbars
    JToolBar mainScreenToolbar;

    GUIMainScreen(List<Map<String, String>> emails){

        //Pencerenin genel özellikleri

        mainScreen=new JFrame("Email-Client Main Screen");
        mainScreen.setLayout(new BorderLayout());
        mainScreen.setVisible(true);
        mainScreen.setSize(1368,720);
        mainScreen.setLocationRelativeTo(null);
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.getContentPane().setBackground(new Color(33, 33, 33));
        mainScreenToolbar=new JToolBar();
        mainScreenToolbar.setFloatable(false);
        mainScreenToolbar.setBackground(new Color(33, 33, 33));
        refreshButton = new JButton("Refresh");
        settingsButton = new JButton("Settings");
        mainScreenToolbar.add(refreshButton);
        mainScreenToolbar.add(settingsButton);
        mainScreenToolbar.add(Box.createHorizontalGlue());
        JLabel profilePicture = new JLabel(resizeIcon(new ImageIcon("profile-photos/default-picture.png"), 50, 50));
        JLabel nameLabel = new JLabel("John Doe");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        signOutButton=new JButton("Sign Out");
        signOutButton.addActionListener(this);
        mainScreenToolbar.add(profilePicture);
        mainScreenToolbar.add(Box.createRigidArea(new Dimension(10, 0)));
        mainScreenToolbar.add(nameLabel);
        mainScreenToolbar.add(Box.createRigidArea(new Dimension(10, 0)));
        mainScreenToolbar.add(signOutButton);
        mainScreen.add(mainScreenToolbar, BorderLayout.NORTH);

        //Maillerin ve mail gönderme yerinin gözükeceği panel

        mailPanel=new JPanel();
        mainScreen.add(mailPanel,BorderLayout.CENTER);
        mailPanel.setPreferredSize(new Dimension(400,768));
        mailPanel.setLayout(new CardLayout());
        mailPanel.setVisible(true);
        illusionPanel1=new JPanel();
        mailPanel.add(illusionPanel1);
        illusionPanel1.setBackground(new Color(33, 33, 33));

        showMailPanel=new JPanel(new CardLayout());
        mainScreen.add(showMailPanel,BorderLayout.EAST);
        showMailPanel.setPreferredSize(new Dimension(690,550));
        showMailPanel.setVisible(true);
        showMailPanel.setBackground(Color.black);
        illusionPanel2=new JPanel();
        showMailPanel.add(illusionPanel2);
        illusionPanel2.setBackground(new Color(33, 33, 33));

        //Gönderilen,alınan vb. şeyler arasında geçiş yapmak için menü

        selectMenu1 =new JPanel();
        mainScreen.add(selectMenu1,BorderLayout.WEST);
        selectMenu1.setBackground(new Color(33, 33, 33));
        selectMenu1.setPreferredSize(new Dimension(220,768));
        received=new JButton("E-mail Inbox");
        sent=new JButton("Sent Mails");
        sendMail=new JButton("Send a mail");
        selectMenu1.add(received);
        selectMenu1.add(sent);
        selectMenu1.add(sendMail);
        selectMenu1.setLayout(null);
        received.setBounds(10,150,200,30);
        received.addActionListener(this); //tıklayınca açılması için
        sent.setBounds(10,190,200,30);
        sent.addActionListener(this); //tıklayınca açılması için
        sendMail.setBounds(10,230,200,30);
        sendMail.addActionListener(this);
        received.setBackground(new Color(174, 182, 191));
        sent.setBackground(new Color(174, 182, 191));
        sendMail.setBackground(new Color(174, 182, 191));

        //Alınan e-postaların gözükeceği yer

        String[] receivedMailColumns = {"Sender", "Subject"};
        DefaultTableModel receivedMailTableModel = new DefaultTableModel(receivedMailColumns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable receviedMailTable = new JTable(receivedMailTableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    component.setBackground(row % 2 == 0 ? new Color(45, 52, 54) : new Color(60, 63, 65));
                }
                return component;
            }
        };
        receviedMailTable.getTableHeader().setReorderingAllowed(false);
        receivedScroll = new JScrollPane(receviedMailTable);
        for(Map<String, String> email : emails){
            receivedMailTableModel.addRow(new Object[]{email.get("Gönderen"), email.get("Konu")});
        }

        showReceivedMailsPanel=new JPanel(new BorderLayout());
        showReceivedMailsPanel.setBackground(new Color(33, 33, 33));
        showMailPanel.add(showReceivedMailsPanel);
        mailPanel.add(receivedScroll);
        receivedScroll.setVisible(false);
        receivedContent=new JTextArea();
        receivedContent.setBackground(new Color(33, 33, 33));
        receivedContent.setForeground(Color.WHITE);
        receivedContent.setLineWrap(true);
        receivedContent.setWrapStyleWord(true);
        receivedContent.setMargin(new Insets(10, 10, 10, 10));
        receivedContent.setEditable(false);
        showReceivedClose=new JButton("Close");
        showReceivedClose.setBackground(new Color(255, 0, 0));
        showReceivedClose.setForeground(Color.WHITE);
        showReceivedClose.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        receivedContentScroll=new JScrollPane(receivedContent);
        receivedMailClosePanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        receivedMailClosePanel.setBackground(new Color(33, 33, 33));
        showReceivedMailsPanel.add(receivedMailClosePanel,BorderLayout.NORTH);
        receivedMailClosePanel.add(showReceivedClose);
        showReceivedMailsPanel.add(receivedContentScroll,BorderLayout.CENTER);
        showReceivedClose.addActionListener(this);

        receviedMailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        receviedMailTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow=receviedMailTable.getSelectedRow();
            if(selectedRow!=-1){
                illusionPanel2.setVisible(false);
                sendMailPanel.setVisible(false);
                showSentMailsPanel.setVisible(false);
                showReceivedMailsPanel.setVisible(true);
                String subject=emails.get(selectedRow).get("Konu");
                String content=emails.get(selectedRow).get("İçerik");
                String sender=emails.get(selectedRow).get("Gönderen");
                receivedContent.setText("Subject:  "+subject+"\nSender:  "+sender+"\n\nContent:\n"+content);
            }
        });

        receviedMailTable.setFillsViewportHeight(true);
        receviedMailTable.setBackground(new Color(33, 33, 33));
        receviedMailTable.setForeground(Color.WHITE);
        receviedMailTable.setSelectionBackground(new Color(99, 110, 114));
        receviedMailTable.setSelectionForeground(Color.BLACK);
        receviedMailTable.setGridColor(new Color(45, 52, 54));
        receivedScroll.getViewport().setBackground(new Color(33, 33, 33));

        for(int i=0;i<receviedMailTable.getColumnModel().getColumnCount();i++){
            receviedMailTable.getColumnModel().getColumn(i).setResizable(false);
        }

        //Gönderilen e-postaların gözükeceği yer

        String[] sentMailColumns = {"To", "Subject"};
        DefaultTableModel sentMailTableModel=new DefaultTableModel(sentMailColumns,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for(int i=200;i<400;i++){
            sentMailTableModel.addRow(new Object[]{i,i});
        }
        JTable sentMailTable=new JTable(sentMailTableModel);
        sentMailTable.getTableHeader().setReorderingAllowed(false);
        sentScroll=new JScrollPane(sentMailTable);
        showSentMailsPanel=new JPanel(null);
        showMailPanel.add(showSentMailsPanel);
        mailPanel.add(sentScroll);
        sentScroll.setVisible(false);

        showSentClose=new JButton("Close");
        sentSubject=new JTextField();
        sentContent=new JTextArea();
        sentTo=new JTextField();
        sentContentScroll=new JScrollPane(sentContent);
        showSentMailsPanel.add(sentSubject);
        showSentMailsPanel.add(sentContentScroll);
        showSentMailsPanel.add(sentTo);
        showSentMailsPanel.add(showSentClose);
        sentSubject.setBounds(0,0,600,50);
        sentContentScroll.setBounds(0,50,690,480);
        sentTo.setBounds(0,520,690,30);
        showSentClose.setBounds(600,0,90,50);
        showSentClose.addActionListener(this);
        sentSubject.setEditable(false);
        sentContent.setEditable(false);
        sentTo.setEditable(false);
        sentContent.setLineWrap(true);
        sentContent.setWrapStyleWord(true);

        for(int i=0;i<sentMailTable.getColumnModel().getColumnCount();i++){
            sentMailTable.getColumnModel().getColumn(i).setResizable(false);
        }

        sentMailTable.setFillsViewportHeight(true);
        sentScroll.getViewport().setBackground(new Color(33, 33, 33));
        sentMailTable.setBackground(new Color(45, 52, 54));
        sentMailTable.setForeground(Color.WHITE);
        sentMailTable.setSelectionForeground(Color.BLACK);
        sentMailTable.setGridColor(new Color(45, 52, 54));

        //E-posta gönderme paneli

        sendMailPanel=new JPanel(null);
        showMailPanel.add(sendMailPanel);
        mailSendButton=new JButton("Send");
        sendMailSubject=new JTextField();
        sendMailContent=new JTextArea();
        sendMailTo=new JTextField();
        sendMailSubject1=new JTextField();
        sendMailTo1=new JTextField();
        sendContentScroll=new JScrollPane(sendMailContent);
        sendMailClose=new JButton("Close");
        sendMailPanel.add(mailSendButton);
        sendMailPanel.add(sendMailSubject1);
        sendMailPanel.add(sendMailSubject);
        sendMailPanel.add(sendContentScroll);
        sendMailPanel.add(sendMailTo1);
        sendMailPanel.add(sendMailTo);
        sendMailPanel.add(sendMailClose);
        sendMailSubject1.setBounds(0,0,50,50);
        sendMailSubject.setBounds(50,0,550,50);
        sendContentScroll.setBounds(0,50,690,470);
        sendMailTo1.setBounds(0,520,50,30);
        sendMailTo.setBounds(50,520,550,30);
        mailSendButton.setBounds(600,520,90,30);
        sendMailClose.setBounds(600,0,90,50);
        sendMailClose.addActionListener(this);
        sendMailSubject1.setText("Subject:");
        sendMailSubject1.setEditable(false);
        sendMailTo1.setText("To:");
        sendMailTo1.setEditable(false);
        sendMailContent.setLineWrap(true);
        sendMailContent.setWrapStyleWord(true);
        mailSendButton.addActionListener(this);
        mainScreen.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                deleteFilesInDirectory("emails/inbox");
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==received){
            illusionPanel1.setVisible(false);
            sentScroll.setVisible(false);
            receivedScroll.setVisible(true);
        }
        if(e.getSource()==sent){
            illusionPanel1.setVisible(false);
            receivedScroll.setVisible(false);
            sentScroll.setVisible(true);
        }
        if(e.getSource()==sendMail){
            illusionPanel2.setVisible(false);
            showReceivedMailsPanel.setVisible(false);
            showSentMailsPanel.setVisible(false);
            sendMailPanel.setVisible(true);
        }
        if(e.getSource()==mailSendButton){
            MailManagement aa = new MailManagement();
            String sendSubject=sendMailSubject.getText(),sendContent=sendMailContent.getText(),sendTo=sendMailTo.getText();
            System.out.println("Subject:"+sendSubject+"\n");
            System.out.println("Content:"+sendContent+"\n");
            System.out.println("To:"+sendTo);
            aa.sendPlainTextEmail("iamtheone.javaproje@gmail.com",sendTo,sendSubject,sendContent,true);
        }
        if (e.getSource()==showReceivedClose){
            showReceivedMailsPanel.setVisible(false);
            illusionPanel2.setVisible(true);
        }
        if (e.getSource()==sendMailClose){
            sendMailPanel.setVisible(false);
            illusionPanel2.setVisible(true);
        }
        if (e.getSource()==showSentClose){
            showSentMailsPanel.setVisible(false);
            illusionPanel2.setVisible(true);
        }
        if (e.getSource()==signOutButton){
            deleteFilesInDirectory("emails/inbox");
            mainScreen.dispose();
            List<AccountSelectionScreen.Account> accounts = readAccountsFromFile();
            // Start the account selection screen
            new AccountSelectionScreen(accounts);
        }
    }
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    private static void deleteFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }
}
