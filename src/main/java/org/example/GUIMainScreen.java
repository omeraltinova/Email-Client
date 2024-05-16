package org.example;

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