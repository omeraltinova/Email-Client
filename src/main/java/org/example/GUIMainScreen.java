package org.example;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

public class GUIMainScreen {
    private JFrame mainScreen;
    private JTable receivedMailTable;
    private JTextArea emailDetails;
    private JPanel detailsPanel;
    private DefaultTableModel receivedMailTableModel;
    private List<Map<String, String>> emails;

    public GUIMainScreen(List<Map<String, String>> emails) {
        this.emails = emails;
        mainScreen = new JFrame("Email Client Main Screen");
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.setSize(1368, 720);
        mainScreen.setLayout(new BorderLayout());

        initializeUI();

        mainScreen.setVisible(true);
    }

    private void initializeUI() {
        // ToolBar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(33, 33, 33));

        JButton refreshButton = new JButton("Refresh");
        JButton settingsButton = new JButton("Settings");

        configureToolBarButton(refreshButton);
        configureToolBarButton(settingsButton);

        toolBar.add(refreshButton);
        toolBar.add(settingsButton);

        mainScreen.add(toolBar, BorderLayout.NORTH);

        // Sidebar
        JPanel selectMenu1 = new JPanel();
        selectMenu1.setLayout(new GridLayout(0, 1));
        selectMenu1.setBackground(new Color(45, 52, 54));

        JButton received = new JButton("Inbox");
        JButton sent = new JButton("Sent");
        JButton sendMail = new JButton("Compose");
        JButton signOutButton = new JButton("Sign Out");

        configureButton(received);
        configureButton(sent);
        configureButton(sendMail);
        configureButton(signOutButton);

        selectMenu1.add(received);
        selectMenu1.add(sent);
        selectMenu1.add(sendMail);
        selectMenu1.add(Box.createVerticalGlue());
        selectMenu1.add(signOutButton);

        mainScreen.add(selectMenu1, BorderLayout.WEST);

        // Email List
        String[] receivedMailColumns = {"Sender", "Subject"};
        receivedMailTableModel = new DefaultTableModel(receivedMailColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Map<String, String> email : emails) {
            receivedMailTableModel.addRow(new Object[]{email.get("Gönderen"), email.get("Konu")});
        }

        receivedMailTable = new JTable(receivedMailTableModel);
        receivedMailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        receivedMailTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = receivedMailTable.getSelectedRow();
                if (selectedRow != -1) {
                    showEmailDetails(emails.get(selectedRow));
                }
            }
        });

        // Right-click context menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = receivedMailTable.getSelectedRow();
                if (selectedRow != -1) {
                    receivedMailTableModel.removeRow(selectedRow);
                    emails.remove(selectedRow);
                }
            }
        });
        popupMenu.add(deleteItem);

        receivedMailTable.setComponentPopupMenu(popupMenu);
        receivedMailTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = receivedMailTable.rowAtPoint(e.getPoint());
                receivedMailTable.getSelectionModel().setSelectionInterval(row, row);
            }
        });

        JScrollPane emailListScrollPane = new JScrollPane(receivedMailTable);
        emailListScrollPane.setPreferredSize(new Dimension(400, mainScreen.getHeight()));

        // Email Details
        detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(new Color(33, 33, 33));

        emailDetails = new JTextArea();
        emailDetails.setEditable(false);
        emailDetails.setLineWrap(true);
        emailDetails.setWrapStyleWord(true);
        emailDetails.setBackground(new Color(33, 33, 33));
        emailDetails.setForeground(Color.WHITE);
        emailDetails.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane emailDetailsScrollPane = new JScrollPane(emailDetails);
        detailsPanel.add(emailDetailsScrollPane, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, emailListScrollPane, detailsPanel);
        splitPane.setDividerLocation(400);

        mainScreen.add(splitPane, BorderLayout.CENTER);
    }

    private void configureButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(99, 110, 114));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFont(new Font("Arial", Font.PLAIN, 18));
    }

    private void configureToolBarButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(45, 52, 54));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    private void showEmailDetails(Map<String, String> email) {
        String details = "Sender: " + email.get("Gönderen") + "\n"
                + "Subject: " + email.get("Konu") + "\n\n"
                + email.get("İçerik");
        emailDetails.setText(details);
    }

    public static void main(String[] args) {
        List<Map<String, String>> emails = List.of(
                Map.of("Gönderen", "alice@example.com", "Konu", "Meeting Reminder", "İçerik", "Don't forget our meeting tomorrow at 10 AM."),
                Map.of("Gönderen", "bob@example.com", "Konu", "Project Update", "İçerik", "The project is on track for the deadline."),
                Map.of("Gönderen", "carol@example.com", "Konu", "Lunch Plans", "İçerik", "How about lunch tomorrow?")
        );

        SwingUtilities.invokeLater(() -> new GUIMainScreen(emails));
    }
}







//package org.example;
//
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.Map;
//
//public class GUIMainScreen implements ActionListener{
//    //Main frame
//    JFrame mainScreen;
//
//    //Panels
//
//    JPanel selectMenu1;
//    JPanel receivedMailPanel;
//    JPanel showReceivedMailsPanel;
//    JPanel sentMailPanel;
//    JPanel showSentMailsPanel;
//    JPanel sendMailPanel;
//
//    //Buttons
//
//    JButton received;
//    JButton sent;
//    JButton sendMail;
//    JButton mailSendButton;
//    JButton showReceivedClose;
//    JButton showSentClose;
//    JButton sendMailClose;
//    JButton signOutButton;
//
//    //Scrollbars
//
//    JScrollPane receivedScroll;
//    JScrollPane sentScroll;
//    JScrollPane receivedContentScroll;
//    JScrollPane sentContentScroll;
//    JScrollPane sendContentScroll;
//
//    //Text Fields and Areas
//
//    JTextField receivedSubject;
//    JTextArea receivedContent;
//    JTextField receivedMailSender;
//    JTextField sentSubject;
//    JTextArea sentContent;
//    JTextField sentTo;
//    JTextField sendMailSubject1;
//    JTextField sendMailSubject;
//    JTextArea sendMailContent;
//    JTextField sendMailTo1;
//    JTextField sendMailTo;
//
//    GUIMainScreen(Map<Integer, Email> emails){
//
//        //Pencerenin genel özellikleri
//
//        mainScreen=new JFrame("Email-Client Main Screen");
//        mainScreen.setLayout(null);
//        mainScreen.setVisible(true);
//        mainScreen.setSize(1368,720);
//        mainScreen.setLocationRelativeTo(null);
//        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainScreen.getContentPane().setBackground(new Color(46, 64, 83 ));
//
//        //Gönderilen,alınan vb. şeyler arasında geçiş yapmak için menü
//
//        selectMenu1 =new JPanel();
//        mainScreen.add(selectMenu1);
//        selectMenu1.setBackground(new Color(93, 109, 126));
//        selectMenu1.setBounds(0,50,220,630);
//        received=new JButton("E-mail Inbox");
//        sent=new JButton("Sent Mails");
//        sendMail=new JButton("Send a mail");
//        signOutButton=new JButton("Sign Out");
//        selectMenu1.add(received);
//        selectMenu1.add(sent);
//        selectMenu1.add(sendMail);
//        selectMenu1.add(signOutButton);
//        selectMenu1.setLayout(null);
//        received.setBounds(10,150,200,30);
//        received.addActionListener(this); //tıklayınca açılması için
//        sent.setBounds(10,190,200,30);
//        sent.addActionListener(this); //tıklayınca açılması için
//        sendMail.setBounds(10,230,200,30);
//        sendMail.addActionListener(this);
//        signOutButton.setBounds(10,580,200,30);
//        signOutButton.addActionListener(this);
//        received.setBackground(new Color(174, 182, 191));
//        sent.setBackground(new Color(174, 182, 191));
//        sendMail.setBackground(new Color(174, 182, 191));
//        signOutButton.setBackground(new Color(174, 182, 191));
//
//        //Alınan e-postaların gözükeceği yer
//
//        receivedMailPanel=new JPanel();
//        mainScreen.add(receivedMailPanel);
//        receivedMailPanel.setLayout(new BorderLayout());
//        receivedMailPanel.setVisible(false);
//        receivedMailPanel.setBounds(220,50,400,630);
//        String[] receivedMailColumns = {"Sender", "Subject"};
//        DefaultTableModel receivedMailTableModel = new DefaultTableModel(receivedMailColumns, 0){
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//        JTable receviedMailTable = new JTable(receivedMailTableModel);
//        receviedMailTable.getTableHeader().setReorderingAllowed(false);
//        receivedScroll = new JScrollPane(receviedMailTable);
//        for (Email email : emails.values()) {
//            receivedMailTableModel.addRow(new Object[]{email.getFrom(), email.getSubject()});
//        }
//        receivedMailPanel.add(receivedScroll,BorderLayout.CENTER);
//        showReceivedMailsPanel=new JPanel(null);
//        mainScreen.add(showReceivedMailsPanel);
//        showReceivedMailsPanel.setBounds(640,50,690,550);
//        showReceivedMailsPanel.setVisible(false);
//        receivedSubject=new JTextField();
//        receivedContent=new JTextArea();
//        receivedMailSender=new JTextField();
//        showReceivedClose=new JButton("Close");
//        receivedSubject.setEditable(false);
//        receivedContent.setEditable(false);
//        receivedMailSender.setEditable(false);
//        receivedContentScroll=new JScrollPane(receivedContent);
//        showReceivedMailsPanel.add(receivedSubject);
//        showReceivedMailsPanel.add(receivedContentScroll);
//        showReceivedMailsPanel.add(receivedMailSender);
//        showReceivedMailsPanel.add(showReceivedClose);
//        showReceivedClose.addActionListener(this);
//        receivedSubject.setBounds(0,0,600,50);
//        showReceivedClose.setBounds(600,0,90,50);
//        receivedContentScroll.setBounds(0,50,690,480);
//        receivedMailSender.setBounds(0,520,690,30);
//        receivedContent.setLineWrap(true);
//        receivedContent.setWrapStyleWord(true);
//        receviedMailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        receviedMailTable.getSelectionModel().addListSelectionListener(e -> {
//            int selectedRow=receviedMailTable.getSelectedRow();
//            if(selectedRow!=-1){
//                showReceivedMailsPanel.setVisible(false);
//                sendMailPanel.setVisible(false);
//                showSentMailsPanel.setVisible(false);
//                showReceivedMailsPanel.setVisible(true);
//                String subject=emails.get(selectedRow).getSubject();
//                String content=emails.get(selectedRow).getContent();
//                String sender=emails.get(selectedRow).getFrom();
//                receivedSubject.setText("Subject:   "+subject);
//                receivedContent.setText(content);
//                receivedMailSender.setText("Sender:   "+sender);
//            }
//        });
//        receviedMailTable.setFillsViewportHeight(true);
//        receivedScroll.getViewport().setBackground(new Color(174, 182, 191));
//        receviedMailTable.setBackground(new Color(174, 182, 191));
//        receviedMailTable.getTableHeader().setBackground(new Color(108, 112, 118));
//        for(int i=0;i<receviedMailTable.getColumnModel().getColumnCount();i++){
//            receviedMailTable.getColumnModel().getColumn(i).setResizable(false);
//        }
//
//        //Gönderilen e-postaların gözükeceği yer
//
//        sentMailPanel=new JPanel();
//        mainScreen.add(sentMailPanel);
//        sentMailPanel.setLayout(new BorderLayout());
//        sentMailPanel.setVisible(false);
//        sentMailPanel.setBounds(220,50,400,630);
//        String[] sentMailColumns = {"To", "Subject"};
//        DefaultTableModel sentMailTableModel=new DefaultTableModel(sentMailColumns,0){
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//        for(int i=200;i<400;i++){
//            sentMailTableModel.addRow(new Object[]{i,i});
//        }
//        JTable sentMailTable=new JTable(sentMailTableModel);
//        sentMailTable.getTableHeader().setReorderingAllowed(false);
//        sentScroll=new JScrollPane(sentMailTable);
//        sentMailPanel.add(sentScroll,BorderLayout.CENTER);
//        showSentMailsPanel=new JPanel(null);
//        mainScreen.add(showSentMailsPanel);
//        showSentMailsPanel.setBounds(640,50,690,550);
//        showSentMailsPanel.setVisible(false);
//        showSentClose=new JButton("Close");
//        sentSubject=new JTextField();
//        sentContent=new JTextArea();
//        sentTo=new JTextField();
//        sentContentScroll=new JScrollPane(sentContent);
//        showSentMailsPanel.add(sentSubject);
//        showSentMailsPanel.add(sentContentScroll);
//        showSentMailsPanel.add(sentTo);
//        showSentMailsPanel.add(showSentClose);
//        sentSubject.setBounds(0,0,600,50);
//        sentContentScroll.setBounds(0,50,690,480);
//        sentTo.setBounds(0,520,690,30);
//        showSentClose.setBounds(600,0,90,50);
//        showSentClose.addActionListener(this);
//        sentSubject.setEditable(false);
//        sentContent.setEditable(false);
//        sentTo.setEditable(false);
//        sentContent.setLineWrap(true);
//        sentContent.setWrapStyleWord(true);
//        sentMailTable.setFillsViewportHeight(true);
//        sentScroll.getViewport().setBackground(new Color(174, 182, 191));
//        sentMailTable.getTableHeader().setBackground(new Color(108, 112, 118));
//        sentMailTable.setBackground(new Color(174, 182, 191));
//        for(int i=0;i<sentMailTable.getColumnModel().getColumnCount();i++){
//            sentMailTable.getColumnModel().getColumn(i).setResizable(false);
//        }
//
//        //E-posta gönderme paneli
//
//        sendMailPanel=new JPanel(null);
//        mainScreen.add(sendMailPanel);
//        sendMailPanel.setBounds(640,50,690,550);
//        sendMailPanel.setVisible(false);
//        mailSendButton=new JButton("Send");
//        sendMailSubject=new JTextField();
//        sendMailContent=new JTextArea();
//        sendMailTo=new JTextField();
//        sendMailSubject1=new JTextField();
//        sendMailTo1=new JTextField();
//        sendContentScroll=new JScrollPane(sendMailContent);
//        sendMailClose=new JButton("Close");
//        sendMailPanel.add(mailSendButton);
//        sendMailPanel.add(sendMailSubject1);
//        sendMailPanel.add(sendMailSubject);
//        sendMailPanel.add(sendContentScroll);
//        sendMailPanel.add(sendMailTo1);
//        sendMailPanel.add(sendMailTo);
//        sendMailPanel.add(sendMailClose);
//        sendMailSubject1.setBounds(0,0,50,50);
//        sendMailSubject.setBounds(50,0,550,50);
//        sendContentScroll.setBounds(0,50,690,470);
//        sendMailTo1.setBounds(0,520,50,30);
//        sendMailTo.setBounds(50,520,550,30);
//        mailSendButton.setBounds(600,520,90,30);
//        sendMailClose.setBounds(600,0,90,50);
//        sendMailClose.addActionListener(this);
//        sendMailSubject1.setText("Subject:");
//        sendMailSubject1.setEditable(false);
//        sendMailTo1.setText("To:");
//        sendMailTo1.setEditable(false);
//        sendMailContent.setLineWrap(true);
//        sendMailContent.setWrapStyleWord(true);
//        mailSendButton.addActionListener(this);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource()==received){
//            sentMailPanel.setVisible(false);
//            showSentMailsPanel.setVisible(false);
//            sendMailPanel.setVisible(false);
//            receivedMailPanel.setVisible(true);
//        }
//        if(e.getSource()==sent){
//            receivedMailPanel.setVisible(false);
//            showReceivedMailsPanel.setVisible(false);
//            sendMailPanel.setVisible(false);
//            sentMailPanel.setVisible(true);
//        }
//        if(e.getSource()==sendMail){
//            showSentMailsPanel.setVisible(false);
//            showReceivedMailsPanel.setVisible(false);
//            sendMailPanel.setVisible(true);
//        }
//        if(e.getSource()==mailSendButton){
//            String sendSubject=sendMailSubject.getText(),sendContent=sendMailContent.getText(),sendTo=sendMailTo.getText();
//            System.out.println("Subject:"+sendSubject+"\n");
//            System.out.println("Content:"+sendContent+"\n");
//            System.out.println("To:"+sendTo);
//        }
//        if (e.getSource()==showReceivedClose){
//            showReceivedMailsPanel.setVisible(false);
//        }
//        if (e.getSource()==sendMailClose){
//            sendMailPanel.setVisible(false);
//        }
//        if (e.getSource()==showSentClose){
//            showSentMailsPanel.setVisible(false);
//        }
//        if (e.getSource()==signOutButton){
//            mainScreen.dispose();
//        }
//    }
//}