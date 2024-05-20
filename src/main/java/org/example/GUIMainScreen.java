package org.example;

//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.List;
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
//    GUIMainScreen(List<Map<String, String>> emails){
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
//        for(Map<String, String> email : emails){
//            receivedMailTableModel.addRow(new Object[]{email.get("Gönderen"), email.get("Konu")});
//
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
//                String subject=emails.get(selectedRow).get("Konu");
//                String content=emails.get(selectedRow).get("İçerik");
//                String sender=emails.get(selectedRow).get("Gönderen");
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
//    public void forResearchThing(List<Map<String, String>> emails, String query) {
//        query = query.toLowerCase();
//        for (Map<String, String> email : emails) {
//            if (email.get("Gönderen").toLowerCase().contains(query) ||
//                    email.get("Konu").toLowerCase().contains(query) ||
//                    email.get("İçerik").toLowerCase().contains(query)) {
//
//                takeSearchResult(email.get("Gönderen"), email.get("Konu"), email.get("İçerik"));
//            }
//        }
//    }
//    public void takeSearchResult(String sender,String subject, String content){
//
//    }
//}

//CHATGPT DESTEĞİYLE UI
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class GUIMainScreen implements ActionListener{
    //Main frame
    JFrame mainScreen;

    //Panels

    JPanel selectMenu1;
    JPanel receivedMailPanel;
    JPanel showReceivedMailsPanel;
    JPanel sentMailPanel;
    JPanel showSentMailsPanel;
    JPanel sendMailPanel;

    //Buttons

    JButton received;
    JButton sent;
    JButton sendMail;
    JButton mailSendButton;
    JButton showReceivedClose;
    JButton showSentClose;
    JButton sendMailClose;
    JButton signOutButton;

    //Scrollbars

    JScrollPane receivedScroll;
    JScrollPane sentScroll;
    JScrollPane receivedContentScroll;
    JScrollPane sentContentScroll;
    JScrollPane sendContentScroll;

    //Text Fields and Areas

    JTextField receivedSubject;
    JTextArea receivedContent;
    JTextField receivedMailSender;
    JTextField sentSubject;
    JTextArea sentContent;
    JTextField sentTo;
    JTextField sendMailSubject1;
    JTextField sendMailSubject;
    JTextArea sendMailContent;
    JTextField sendMailTo1;
    JTextField sendMailTo;
    GUIMainScreen(List<Map<String, String>> emails){

        //Pencerenin genel özellikleri

        mainScreen=new JFrame("Email-Client Main Screen");
        mainScreen.setLayout(null);
        mainScreen.setVisible(true);
        mainScreen.setSize(1368,720);
        mainScreen.setLocationRelativeTo(null);
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.getContentPane().setBackground(new Color(46, 64, 83 ));

        //Gönderilen,alınan vb. şeyler arasında geçiş yapmak için menü

        selectMenu1 =new JPanel();
        mainScreen.add(selectMenu1);
        selectMenu1.setBackground(new Color(93, 109, 126));
        selectMenu1.setBounds(0,50,220,630);
        received=new JButton("E-mail Inbox");
        sent=new JButton("Sent Mails");
        sendMail=new JButton("Send a mail");
        signOutButton=new JButton("Sign Out");
        selectMenu1.add(received);
        selectMenu1.add(sent);
        selectMenu1.add(sendMail);
        selectMenu1.add(signOutButton);
        selectMenu1.setLayout(null);
        received.setBounds(10,150,200,30);
        received.addActionListener(this); //tıklayınca açılması için
        sent.setBounds(10,190,200,30);
        sent.addActionListener(this); //tıklayınca açılması için
        sendMail.setBounds(10,230,200,30);
        sendMail.addActionListener(this);
        signOutButton.setBounds(10,580,200,30);
        signOutButton.addActionListener(this);
        received.setBackground(new Color(174, 182, 191));
        sent.setBackground(new Color(174, 182, 191));
        sendMail.setBackground(new Color(174, 182, 191));
        signOutButton.setBackground(new Color(174, 182, 191));

        //Alınan e-postaların gözükeceği yer

        receivedMailPanel=new JPanel();
        mainScreen.add(receivedMailPanel);
        receivedMailPanel.setLayout(new BorderLayout());
        receivedMailPanel.setVisible(false);
        receivedMailPanel.setBounds(220,50,400,630);
        String[] receivedMailColumns = {"Sender", "Subject"};
        DefaultTableModel receivedMailTableModel = new DefaultTableModel(receivedMailColumns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable receviedMailTable = new JTable(receivedMailTableModel);
        receviedMailTable.getTableHeader().setReorderingAllowed(false);
        receivedScroll = new JScrollPane(receviedMailTable);
        for(Map<String, String> email : emails){
            receivedMailTableModel.addRow(new Object[]{email.get("Gönderen"), email.get("Konu")});
        }
        receivedMailPanel.add(receivedScroll,BorderLayout.CENTER);
        showReceivedMailsPanel=new JPanel(null);
        mainScreen.add(showReceivedMailsPanel);
        showReceivedMailsPanel.setBounds(640,50,690,550);
        showReceivedMailsPanel.setVisible(false);
        receivedSubject=new JTextField();
        receivedContent=new JTextArea();
        receivedMailSender=new JTextField();
        showReceivedClose=new JButton("Close");
        receivedSubject.setEditable(false);
        receivedContent.setEditable(false);
        receivedMailSender.setEditable(false);
        receivedContentScroll=new JScrollPane(receivedContent);
        showReceivedMailsPanel.add(receivedSubject);
        showReceivedMailsPanel.add(receivedContentScroll);
        showReceivedMailsPanel.add(receivedMailSender);
        showReceivedMailsPanel.add(showReceivedClose);
        showReceivedClose.addActionListener(this);
        receivedSubject.setBounds(0,0,600,50);
        showReceivedClose.setBounds(600,0,90,50);
        receivedContentScroll.setBounds(0,50,690,480);
        receivedMailSender.setBounds(0,520,690,30);
        receivedContent.setLineWrap(true);
        receivedContent.setWrapStyleWord(true);
        receviedMailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        receviedMailTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow=receviedMailTable.getSelectedRow();
            if(selectedRow!=-1){
                showReceivedMailsPanel.setVisible(false);
                sendMailPanel.setVisible(false);
                showSentMailsPanel.setVisible(false);
                showReceivedMailsPanel.setVisible(true);
                String subject=emails.get(selectedRow).get("Konu");
                String content=emails.get(selectedRow).get("İçerik");
                String sender=emails.get(selectedRow).get("Gönderen");
                receivedSubject.setText("Subject:   "+subject);
                receivedContent.setText(content);
                receivedMailSender.setText("Sender:   "+sender);
            }
        });
        receviedMailTable.setFillsViewportHeight(true);
        receivedScroll.getViewport().setBackground(new Color(174, 182, 191));
        receviedMailTable.setBackground(new Color(174, 182, 191));
        receviedMailTable.getTableHeader().setBackground(new Color(108, 112, 118));
        for(int i=0;i<receviedMailTable.getColumnModel().getColumnCount();i++){
            receviedMailTable.getColumnModel().getColumn(i).setResizable(false);
        }

        //Gönderilen e-postaların gözükeceği yer

        sentMailPanel=new JPanel();
        mainScreen.add(sentMailPanel);
        sentMailPanel.setLayout(new BorderLayout());
        sentMailPanel.setVisible(false);
        sentMailPanel.setBounds(220,50,400,630);
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
        sentMailPanel.add(sentScroll,BorderLayout.CENTER);
        showSentMailsPanel=new JPanel(null);
        mainScreen.add(showSentMailsPanel);
        showSentMailsPanel.setBounds(640,50,690,550);
        showSentMailsPanel.setVisible(false);
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
        sentMailTable.setFillsViewportHeight(true);
        sentScroll.getViewport().setBackground(new Color(174, 182, 191));
        sentMailTable.getTableHeader().setBackground(new Color(108, 112, 118));
        sentMailTable.setBackground(new Color(174, 182, 191));
        for(int i=0;i<sentMailTable.getColumnModel().getColumnCount();i++){
            sentMailTable.getColumnModel().getColumn(i).setResizable(false);
        }

        //E-posta gönderme paneli

        sendMailPanel=new JPanel(null);
        mainScreen.add(sendMailPanel);
        sendMailPanel.setBounds(640,50,690,550);
        sendMailPanel.setVisible(false);
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
    }

   @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==received){
            sentMailPanel.setVisible(false);
            showSentMailsPanel.setVisible(false);
            sendMailPanel.setVisible(false);
            receivedMailPanel.setVisible(true);
        }
        if(e.getSource()==sent){
            receivedMailPanel.setVisible(false);
            showReceivedMailsPanel.setVisible(false);
            sendMailPanel.setVisible(false);
            sentMailPanel.setVisible(true);
        }
       if(e.getSource()==sendMail){
           showSentMailsPanel.setVisible(false);
           showReceivedMailsPanel.setVisible(false);
           sendMailPanel.setVisible(true);
       }
       if(e.getSource()==mailSendButton){
           String sendSubject=sendMailSubject.getText(),sendContent=sendMailContent.getText(),sendTo=sendMailTo.getText();
           System.out.println("Subject:"+sendSubject+"\n");
           System.out.println("Content:"+sendContent+"\n");
           System.out.println("To:"+sendTo);
       }
       if (e.getSource()==showReceivedClose){
           showReceivedMailsPanel.setVisible(false);
       }
       if (e.getSource()==sendMailClose){
           sendMailPanel.setVisible(false);
       }
       if (e.getSource()==showSentClose){
           showSentMailsPanel.setVisible(false);
       }
       if (e.getSource()==signOutButton){
           mainScreen.dispose();
       }
    }
}





// HOCANIN İSTEDİĞİ METHOD İLE YAPILAN UI
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellRenderer;
//import java.awt.*;
//import java.util.List;
//import java.util.Map;
//
//public class GUIMainScreen {
//    private JFrame mainScreen;
//    private JTable receivedMailTable;
//    private JTextArea emailDetails;
//    private JPanel detailsPanel;
//    private DefaultTableModel receivedMailTableModel;
//    private List<Map<String, String>> emails;
//
//    public GUIMainScreen(List<Map<String, String>> emails) {
//        this.emails = emails;
//        mainScreen = new JFrame("Email Client Main Screen");
//        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainScreen.setSize(1368, 720);
//        mainScreen.setLayout(new BorderLayout());
//
//        initializeUI();
//
//        mainScreen.setVisible(true);
//    }
//
//    private void initializeUI() {
//        // ToolBar
//        JToolBar toolBar = new JToolBar();
//        toolBar.setFloatable(false);
//        toolBar.setBackground(new Color(33, 33, 33));
//
//        JButton refreshButton = new JButton("Refresh");
//        JButton settingsButton = new JButton("Settings");
//
//        configureToolBarButton(refreshButton);
//        configureToolBarButton(settingsButton);
//
//        toolBar.add(refreshButton);
//        toolBar.add(settingsButton);
//
//        // Profile Picture and Name with Sign Out button
//        toolBar.add(Box.createHorizontalGlue());
//        JLabel profilePicture = new JLabel(resizeIcon(new ImageIcon("profile-photos/default-picture.png"), 50, 50));
//        JLabel nameLabel = new JLabel("John Doe");
//        nameLabel.setForeground(Color.WHITE);
//        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
//
//        JButton signOutButton = new JButton("Sign Out");
//        configureToolBarButton(signOutButton);
//
//        toolBar.add(profilePicture);
//        toolBar.add(Box.createRigidArea(new Dimension(10, 0)));
//        toolBar.add(nameLabel);
//        toolBar.add(Box.createRigidArea(new Dimension(10, 0)));
//        toolBar.add(signOutButton);
//
//        mainScreen.add(toolBar, BorderLayout.NORTH);
//
//        // Sidebar
//        JPanel selectMenu1 = new JPanel();
//        selectMenu1.setLayout(new GridBagLayout());
//        selectMenu1.setBackground(new Color(33, 33, 33));
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = GridBagConstraints.RELATIVE;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.insets = new Insets(5, 5, 5, 5);
//
//        JButton received = new JButton("Inbox");
//        JButton sent = new JButton("Sent");
//        JButton sendMail = new JButton("Compose");
//
//        configureButton(received);
//        configureButton(sent);
//        configureButton(sendMail);
//
//        selectMenu1.add(received, gbc);
//        selectMenu1.add(sent, gbc);
//        selectMenu1.add(sendMail, gbc);
//
//        mainScreen.add(selectMenu1, BorderLayout.WEST);
//
//        // Email List
//        String[] receivedMailColumns = {"Sender", "Subject"};
//        receivedMailTableModel = new DefaultTableModel(receivedMailColumns, 0) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//
//        for (Map<String, String> email : emails) {
//            receivedMailTableModel.addRow(new Object[]{email.get("Gönderen"), email.get("Konu")});
//        }
//
//        receivedMailTable = new JTable(receivedMailTableModel) {
//            @Override
//            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//                Component component = super.prepareRenderer(renderer, row, column);
//                if (!isRowSelected(row)) {
//                    component.setBackground(row % 2 == 0 ? new Color(45, 52, 54) : new Color(60, 63, 65));
//                }
//                return component;
//            }
//        };
//
//        // Use the custom renderer for multi-line support
//        receivedMailTable.setDefaultRenderer(Object.class, new MultiLineTableCellRenderer());
//        receivedMailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        receivedMailTable.getSelectionModel().addListSelectionListener(e -> {
//            int selectedRow = receivedMailTable.getSelectedRow();
//            if (selectedRow != -1) {
//                showEmailDetails(emails.get(selectedRow));
//            }
//        });
//
//        receivedMailTable.setBackground(new Color(45, 52, 54));
//        receivedMailTable.setForeground(Color.WHITE);
//        receivedMailTable.setSelectionBackground(new Color(99, 110, 114));
//        receivedMailTable.setSelectionForeground(Color.BLACK);
//        receivedMailTable.setGridColor(new Color(45, 52, 54)); // Set grid color to match background
//
//        // Set the background color for the JScrollPane
//        JScrollPane emailListScrollPane = new JScrollPane(receivedMailTable);
//        emailListScrollPane.getViewport().setBackground(new Color(33, 33, 33));
//
//        // Email Details
//        detailsPanel = new JPanel(new BorderLayout());
//        detailsPanel.setBackground(new Color(33, 33, 33));
//
//        emailDetails = new JTextArea();
//        emailDetails.setEditable(false);
//        emailDetails.setLineWrap(true);
//        emailDetails.setWrapStyleWord(true);
//        emailDetails.setBackground(new Color(33, 33, 33));
//        emailDetails.setForeground(Color.WHITE);
//        emailDetails.setMargin(new Insets(10, 10, 10, 10));
//
//        JScrollPane emailDetailsScrollPane = new JScrollPane(emailDetails);
//
//        // Close button
//        JButton closeButton = new JButton("Close");
//        closeButton.setFocusPainted(false);
//        closeButton.setBackground(new Color(255, 0, 0));
//        closeButton.setForeground(Color.WHITE);
//        closeButton.setFont(new Font("Arial", Font.BOLD, 12));
//        closeButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//        closeButton.addActionListener(e -> emailDetails.setText(""));
//
//        JPanel closeButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        closeButtonPanel.setBackground(new Color(33, 33, 33));
//        closeButtonPanel.add(closeButton);
//
//        detailsPanel.add(emailDetailsScrollPane, BorderLayout.CENTER);
//        detailsPanel.add(closeButtonPanel, BorderLayout.NORTH);
//
//        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, emailListScrollPane, detailsPanel);
//        splitPane.setDividerLocation(400);
//
//        mainScreen.add(splitPane, BorderLayout.CENTER);
//    }
//
//    private void configureButton(JButton button) {
//        button.setFocusPainted(false);
//        button.setBackground(new Color(99, 110, 114));
//        button.setForeground(Color.WHITE);
//        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
//        button.setFont(new Font("Arial", Font.PLAIN, 16));
//    }
//
//    private void configureToolBarButton(JButton button) {
//        button.setFocusPainted(false);
//        button.setBackground(new Color(33, 33, 33));
//        button.setForeground(Color.WHITE);
//        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
//        button.setFont(new Font("Arial", Font.PLAIN, 16));
//    }
//
//    private void showEmailDetails(Map<String, String> email) {
//        String details = "Sender: " + email.get("Gönderen") + "\n" + "Subject: " + email.get("Konu") + "\n\n" + email.get("İçerik");
//
//        emailDetails.setText(details);
//    }
//
//    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
//        Image image = icon.getImage();
//        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        return new ImageIcon(resizedImage);
//    }
//
//    // Custom cell renderer for multi-line text
//    private class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
//        public MultiLineTableCellRenderer() {
//            setLineWrap(true);
//            setWrapStyleWord(true);
//            setOpaque(true);
//        }
//
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value,
//                                                       boolean isSelected, boolean hasFocus, int row, int column) {
//            setText(value != null ? value.toString() : "");
//            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
//            setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
//            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Adjust as needed
//
//            // Adjust the row height to fit the content
//            setSize(table.getColumnModel().getColumn(column).getWidth(), Integer.MAX_VALUE);
//            int preferredHeight = getPreferredSize().height;
//            int rowHeight = table.getRowHeight(row);
//            if (preferredHeight > rowHeight && preferredHeight > 0) {
//                table.setRowHeight(row, preferredHeight);
//            }
//
//            return this;
//        }
//    }
//
//}

//import javax.swing.*;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableCellRenderer;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class GUIMainScreen implements ActionListener {
//    // Main frame
//    JFrame mainScreen;
//
//    // Panels
//
//    JPanel selectMenu1;
//    JPanel mailPanel;
//    JPanel showMailPanel;
//    JPanel showReceivedMailsPanel;
//    JPanel showSentMailsPanel;
//    JPanel sendMailPanel;
//    JPanel illusionPanel1;
//    JPanel illusionPanel2;
//    JPanel receivedMailClosePanel;
//    JPanel sentMailClosePanel;
//
//    // Buttons
//
//    JButton received;
//    JButton sent;
//    JButton sendMail;
//    JButton mailSendButton;
//    JButton showReceivedClose;
//    JButton showSentClose;
//    JButton sendMailClose;
//    JButton signOutButton;
//    JButton refreshButton;
//    JButton settingsButton;
//    JButton searchButton;
//
//    // Scrollbars
//
//    JScrollPane receivedScroll;
//    JScrollPane sentScroll;
//    JScrollPane receivedContentScroll;
//    JScrollPane sentContentScroll;
//    JScrollPane sendContentScroll;
//
//    // Text Fields and Areas
//
//    JTextArea receivedContent;
//    JTextField sentSubject;
//    JTextArea sentContent;
//    JTextField sentTo;
//    JTextField sendMailSubject1;
//    JTextField sendMailSubject;
//    JTextArea sendMailContent;
//    JTextField sendMailTo1;
//    JTextField sendMailTo;
//    JTextField searchField;
//
//    // Toolbars
//    JToolBar mainScreenToolbar;
//
//    // Email data
//    List<Map<String, String>> emails;
//    DefaultTableModel receivedMailTableModel;
//
//    GUIMainScreen(List<Map<String, String>> emails) {
//        this.emails = emails;
//
//        // Pencerenin genel özellikleri
//        mainScreen = new JFrame("Email-Client Main Screen");
//        mainScreen.setLayout(new BorderLayout());
//        mainScreen.setVisible(true);
//        mainScreen.setSize(1368, 720);
//        mainScreen.setLocationRelativeTo(null);
//        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainScreen.getContentPane().setBackground(new Color(33, 33, 33));
//
//        mainScreenToolbar = new JToolBar();
//        mainScreenToolbar.setFloatable(false);
//        mainScreenToolbar.setBackground(new Color(33, 33, 33));
//
//        // Arama çubuğu ve buton ekle
//        searchField = new JTextField(20);
//        searchButton = new JButton("Search");
//        searchButton.addActionListener(this);
//
//        searchField.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                filterEmails();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                filterEmails();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                filterEmails();
//            }
//        });
//
//        mainScreenToolbar.add(searchField);
//        mainScreenToolbar.add(searchButton);
//
//        refreshButton = new JButton("Refresh");
//        settingsButton = new JButton("Settings");
//        mainScreenToolbar.add(refreshButton);
//        mainScreenToolbar.add(settingsButton);
//        mainScreenToolbar.add(Box.createHorizontalGlue());
//
//        JLabel profilePicture = new JLabel(resizeIcon(new ImageIcon("profile-photos/default-picture.png"), 50, 50));
//        JLabel nameLabel = new JLabel("John Doe");
//        nameLabel.setForeground(Color.WHITE);
//        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
//        signOutButton = new JButton("Sign Out");
//        signOutButton.addActionListener(this);
//        mainScreenToolbar.add(profilePicture);
//        mainScreenToolbar.add(Box.createRigidArea(new Dimension(10, 0)));
//        mainScreenToolbar.add(nameLabel);
//        mainScreenToolbar.add(Box.createRigidArea(new Dimension(10, 0)));
//        mainScreenToolbar.add(signOutButton);
//        mainScreen.add(mainScreenToolbar, BorderLayout.NORTH);
//
//        // Maillerin ve mail gönderme yerinin gözükeceği panel
//        mailPanel = new JPanel();
//        mainScreen.add(mailPanel, BorderLayout.CENTER);
//        mailPanel.setPreferredSize(new Dimension(400, 768));
//        mailPanel.setLayout(new CardLayout());
//        mailPanel.setVisible(true);
//        illusionPanel1 = new JPanel();
//        mailPanel.add(illusionPanel1);
//        illusionPanel1.setBackground(new Color(33, 33, 33));
//
//        showMailPanel = new JPanel(new CardLayout());
//        mainScreen.add(showMailPanel, BorderLayout.EAST);
//        showMailPanel.setPreferredSize(new Dimension(690, 550));
//        showMailPanel.setVisible(true);
//        showMailPanel.setBackground(Color.black);
//        illusionPanel2 = new JPanel();
//        showMailPanel.add(illusionPanel2);
//        illusionPanel2.setBackground(new Color(33, 33, 33));
//
//        // Gönderilen, alınan vb. şeyler arasında geçiş yapmak için menü
//        selectMenu1 = new JPanel();
//        mainScreen.add(selectMenu1, BorderLayout.WEST);
//        selectMenu1.setBackground(new Color(33, 33, 33));
//        selectMenu1.setPreferredSize(new Dimension(220, 768));
//        received = new JButton("E-mail Inbox");
//        sent = new JButton("Sent Mails");
//        sendMail = new JButton("Send a mail");
//        selectMenu1.add(received);
//        selectMenu1.add(sent);
//        selectMenu1.add(sendMail);
//        selectMenu1.setLayout(null);
//        received.setBounds(10, 150, 200, 30);
//        received.addActionListener(this); // tıklayınca açılması için
//        sent.setBounds(10, 190, 200, 30);
//        sent.addActionListener(this); // tıklayınca açılması için
//        sendMail.setBounds(10, 230, 200, 30);
//        sendMail.addActionListener(this);
//        received.setBackground(new Color(174, 182, 191));
//        sent.setBackground(new Color(174, 182, 191));
//        sendMail.setBackground(new Color(174, 182, 191));
//
//        // Alınan e-postaların gözükeceği yer
//        String[] receivedMailColumns = {"Sender", "Subject"};
//        receivedMailTableModel = new DefaultTableModel(receivedMailColumns, 0) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//        JTable receivedMailTable = new JTable(receivedMailTableModel) {
//            @Override
//            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//                Component component = super.prepareRenderer(renderer, row, column);
//                if (!isRowSelected(row)) {
//                    component.setBackground(row % 2 == 0 ? new Color(45, 52, 54) : new Color(60, 63, 65));
//                }
//                return component;
//            }
//        };
//        receivedMailTable.getTableHeader().setReorderingAllowed(false);
//        receivedScroll = new JScrollPane(receivedMailTable);
//        for (Map<String, String> email : emails) {
//            receivedMailTableModel.addRow(new Object[]{email.get("Gönderen"), email.get("Konu")});
//        }
//
//        showReceivedMailsPanel = new JPanel(new BorderLayout());
//        showReceivedMailsPanel.setBackground(new Color(33, 33, 33));
//        showMailPanel.add(showReceivedMailsPanel);
//        mailPanel.add(receivedScroll);
//        receivedScroll.setVisible(false);
//        receivedContent = new JTextArea();
//        receivedContent.setBackground(new Color(33, 33, 33));
//        receivedContent.setForeground(Color.WHITE);
//        receivedContent.setLineWrap(true);
//        receivedContent.setWrapStyleWord(true);
//        receivedContent.setMargin(new Insets(10, 10, 10, 10));
//        receivedContent.setEditable(false);
//        showReceivedClose = new JButton("Close");
//        showReceivedClose.setBackground(new Color(255, 0, 0));
//        showReceivedClose.setForeground(Color.WHITE);
//        showReceivedClose.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//        receivedContentScroll = new JScrollPane(receivedContent);
//        receivedMailClosePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        receivedMailClosePanel.setBackground(new Color(33, 33, 33));
//        showReceivedMailsPanel.add(receivedMailClosePanel, BorderLayout.NORTH);
//        receivedMailClosePanel.add(showReceivedClose);
//        showReceivedMailsPanel.add(receivedContentScroll, BorderLayout.CENTER);
//        showReceivedClose.addActionListener(this);
//
//        receivedMailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        receivedMailTable.getSelectionModel().addListSelectionListener(e -> {
//            int selectedRow = receivedMailTable.getSelectedRow();
//            if (selectedRow != -1) {
//                illusionPanel2.setVisible(false);
//                sendMailPanel.setVisible(false);
//                showSentMailsPanel.setVisible(false);
//                showReceivedMailsPanel.setVisible(true);
//                String subject = emails.get(selectedRow).get("Konu");
//                String content = emails.get(selectedRow).get("İçerik");
//                String sender = emails.get(selectedRow).get("Gönderen");
//                receivedContent.setText("Subject:  " + subject + "\nSender:  " + sender + "\n\nContent:\n" + content);
//            }
//        });
//
//        receivedMailTable.setFillsViewportHeight(true);
//        receivedMailTable.setBackground(new Color(33, 33, 33));
//        receivedMailTable.setForeground(Color.WHITE);
//        receivedMailTable.setSelectionBackground(new Color(99, 110, 114));
//        receivedMailTable.setSelectionForeground(Color.BLACK);
//        receivedMailTable.setGridColor(new Color(45, 52, 54));
//        receivedScroll.getViewport().setBackground(new Color(33, 33, 33));
//
//        for (int i = 0; i < receivedMailTable.getColumnModel().getColumnCount(); i++) {
//            receivedMailTable.getColumnModel().getColumn(i).setResizable(false);
//        }
//
//        // Gönderilen e-postaların gözükeceği yer
//
//        String[] sentMailColumns = {"To", "Subject"};
//        DefaultTableModel sentMailTableModel = new DefaultTableModel(sentMailColumns, 0) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//        for (int i = 200; i < 400; i++) {
//            sentMailTableModel.addRow(new Object[]{i, i});
//        }
//        JTable sentMailTable = new JTable(sentMailTableModel);
//        sentMailTable.getTableHeader().setReorderingAllowed(false);
//        sentScroll = new JScrollPane(sentMailTable);
//        showSentMailsPanel = new JPanel(null);
//        showMailPanel.add(showSentMailsPanel);
//        mailPanel.add(sentScroll);
//        sentScroll.setVisible(false);
//
//        showSentClose = new JButton("Close");
//        sentSubject = new JTextField();
//        sentContent = new JTextArea();
//        sentTo = new JTextField();
//        sentContentScroll = new JScrollPane(sentContent);
//        showSentMailsPanel.add(sentSubject);
//        showSentMailsPanel.add(sentContentScroll);
//        showSentMailsPanel.add(sentTo);
//        showSentMailsPanel.add(showSentClose);
//        sentSubject.setBounds(0, 0, 600, 50);
//        sentContentScroll.setBounds(0, 50, 690, 480);
//        sentTo.setBounds(0, 520, 690, 30);
//        showSentClose.setBounds(600, 0, 90, 50);
//        showSentClose.addActionListener(this);
//        sentSubject.setEditable(false);
//        sentContent.setEditable(false);
//        sentTo.setEditable(false);
//        sentContent.setLineWrap(true);
//        sentContent.setWrapStyleWord(true);
//
//        for (int i = 0; i < sentMailTable.getColumnModel().getColumnCount(); i++) {
//            sentMailTable.getColumnModel().getColumn(i).setResizable(false);
//        }
//
//        sentMailTable.setFillsViewportHeight(true);
//        sentScroll.getViewport().setBackground(new Color(33, 33, 33));
//        sentMailTable.setBackground(new Color(45, 52, 54));
//        sentMailTable.setForeground(Color.WHITE);
//        sentMailTable.setSelectionForeground(Color.BLACK);
//        sentMailTable.setGridColor(new Color(45, 52, 54));
//
//        // E-posta gönderme paneli
//
//        sendMailPanel = new JPanel(null);
//        showMailPanel.add(sendMailPanel);
//        mailSendButton = new JButton("Send");
//        sendMailSubject = new JTextField();
//        sendMailContent = new JTextArea();
//        sendMailTo = new JTextField();
//        sendMailSubject1 = new JTextField();
//        sendMailTo1 = new JTextField();
//        sendContentScroll = new JScrollPane(sendMailContent);
//        sendMailClose = new JButton("Close");
//        sendMailPanel.add(mailSendButton);
//        sendMailPanel.add(sendMailSubject1);
//        sendMailPanel.add(sendMailSubject);
//        sendMailPanel.add(sendContentScroll);
//        sendMailPanel.add(sendMailTo1);
//        sendMailPanel.add(sendMailTo);
//        sendMailPanel.add(sendMailClose);
//        sendMailSubject1.setBounds(0, 0, 50, 50);
//        sendMailSubject.setBounds(50, 0, 550, 50);
//        sendContentScroll.setBounds(0, 50, 690, 470);
//        sendMailTo1.setBounds(0, 520, 50, 30);
//        sendMailTo.setBounds(50, 520, 550, 30);
//        mailSendButton.setBounds(600, 520, 90, 30);
//        sendMailClose.setBounds(600, 0, 90, 50);
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
//        if (e.getSource() == received) {
//            illusionPanel1.setVisible(false);
//            sentScroll.setVisible(false);
//            receivedScroll.setVisible(true);
//        }
//        if (e.getSource() == sent) {
//            illusionPanel1.setVisible(false);
//            receivedScroll.setVisible(false);
//            sentScroll.setVisible(true);
//        }
//        if (e.getSource() == sendMail) {
//            illusionPanel2.setVisible(false);
//            showReceivedMailsPanel.setVisible(false);
//            showSentMailsPanel.setVisible(false);
//            sendMailPanel.setVisible(true);
//        }
//        if (e.getSource() == mailSendButton) {
//            MailManagement aa = new MailManagement();
//            String sendSubject = sendMailSubject.getText(), sendContent = sendMailContent.getText(), sendTo = sendMailTo.getText();
//            System.out.println("Subject:" + sendSubject + "\n");
//            System.out.println("Content:" + sendContent + "\n");
//            System.out.println("To:" + sendTo);
//            aa.sendPlainTextEmail("iamtheone.javaproje@gmail.com", sendTo, sendSubject, sendContent, true);
//        }
//        if (e.getSource() == showReceivedClose) {
//            showReceivedMailsPanel.setVisible(false);
//            illusionPanel2.setVisible(true);
//        }
//        if (e.getSource() == sendMailClose) {
//            sendMailPanel.setVisible(false);
//            illusionPanel2.setVisible(true);
//        }
//        if (e.getSource() == showSentClose) {
//            showSentMailsPanel.setVisible(false);
//            illusionPanel2.setVisible(true);
//        }
//        if (e.getSource() == signOutButton) {
//            mainScreen.dispose();
//        }
//        if (e.getSource() == searchButton) {
//            filterEmails();
//        }
//    }
//
//    private void filterEmails() {
//        String query = searchField.getText().toLowerCase();
//        List<Map<String, String>> filteredEmails = emails.stream()
//                .filter(email -> email.get("Gönderen").toLowerCase().contains(query) ||
//                        email.get("Konu").toLowerCase().contains(query))
//                .collect(Collectors.toList());
//
//        // Clear the table
//        receivedMailTableModel.setRowCount(0);
//
//        // Add filtered rows
//        for (Map<String, String> email : filteredEmails) {
//            receivedMailTableModel.addRow(new Object[]{email.get("Gönderen"), email.get("Konu")});
//        }
//    }
//
//    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
//        Image image = icon.getImage();
//        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        return new ImageIcon(resizedImage);
//    }
//}
