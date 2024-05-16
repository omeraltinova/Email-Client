package org.example;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class GUIMainScreen implements ActionListener{

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

    //Scrollbars

    JScrollPane receivedScroll;
    JScrollPane sentScroll;
    JScrollPane receivedContentScroll;
    JScrollPane sentContentScroll;

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

        JFrame mainScreen=new JFrame("Email-Client Main Screen");
        mainScreen.setLayout(null);
        mainScreen.setVisible(true);
        mainScreen.setSize(1368,720);
        mainScreen.setLocationRelativeTo(null);
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Gönderilen,alınan vb. şeyler arasında geçiş yapmak için menü

        selectMenu1 =new JPanel();
        mainScreen.add(selectMenu1);
        selectMenu1.setBackground(Color.lightGray);
        selectMenu1.setBounds(0,0,220,680);
        received=new JButton("Received");
        sent=new JButton("Sent");
        sendMail=new JButton("Send a mail");
        selectMenu1.add(received);
        selectMenu1.add(sent);
        selectMenu1.add(sendMail);
        selectMenu1.setLayout(null);
        received.setBounds(10,10,200,30);
        received.addActionListener(this); //tıklayınca açılması için
        sent.setBounds(10,50,200,30);
        sent.addActionListener(this); //tıklayınca açılması için
        sendMail.setBounds(10,90,200,30);
        sendMail.addActionListener(this);

        //Alınan e-postaların gözükeceği yer

        receivedMailPanel=new JPanel();
        mainScreen.add(receivedMailPanel);
        receivedMailPanel.setLayout(new BorderLayout());
        receivedMailPanel.setVisible(false);
        receivedMailPanel.setBackground(Color.gray);
        receivedMailPanel.setBounds(220,0,400,680);
        //-----------------------------
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
        showReceivedMailsPanel=new JPanel(new BorderLayout());
        mainScreen.add(showReceivedMailsPanel);
        showReceivedMailsPanel.setBounds(640,50,690,550);
        showReceivedMailsPanel.setBackground(Color.cyan);
        showReceivedMailsPanel.setVisible(true);
        receivedSubject=new JTextField();
        receivedContent=new JTextArea();
        receivedMailSender=new JTextField();
        receivedSubject.setEditable(false);
        receivedContent.setEditable(false);
        receivedMailSender.setEditable(false);
        receivedContentScroll=new JScrollPane(receivedContent);
        showReceivedMailsPanel.add(receivedSubject,BorderLayout.NORTH);
        showReceivedMailsPanel.add(receivedContentScroll,BorderLayout.CENTER);
        showReceivedMailsPanel.add(receivedMailSender,BorderLayout.SOUTH);
        showReceivedMailsPanel.setVisible(false);
        receivedSubject.setPreferredSize(new Dimension(690,50));
        receivedMailSender.setPreferredSize(new Dimension(690,30));
        receviedMailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        receviedMailTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
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
            }
        });

        //Gönderilen e-postaların gözükeceği yer

        sentMailPanel=new JPanel();
        mainScreen.add(sentMailPanel);
        sentMailPanel.setLayout(new BorderLayout());
        sentMailPanel.setVisible(false);
        sentMailPanel.setBackground(Color.gray);
        sentMailPanel.setBounds(220,0,400,680);
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
        showSentMailsPanel=new JPanel(new BorderLayout());
        mainScreen.add(showSentMailsPanel);
        showSentMailsPanel.setBounds(640,50,690,550);
        showSentMailsPanel.setBackground(Color.yellow);
        showSentMailsPanel.setVisible(false);
        sentSubject=new JTextField();
        sentContent=new JTextArea();
        sentTo=new JTextField();
        showSentMailsPanel.add(sentSubject,BorderLayout.NORTH);
        showSentMailsPanel.add(sentContent,BorderLayout.CENTER);
        showSentMailsPanel.add(sentTo,BorderLayout.SOUTH);
        sentSubject.setPreferredSize(new Dimension(690,50));
        sentTo.setPreferredSize(new Dimension(690,30));
        sentSubject.setEditable(false);
        sentContent.setEditable(false);
        sentTo.setEditable(false);

        //E-posta gönderme paneli

        sendMailPanel=new JPanel(null);
        mainScreen.add(sendMailPanel);
        sendMailPanel.setBounds(640,50,690,550);
        sendMailPanel.setBackground(Color.blue);
        sendMailPanel.setVisible(false);
        mailSendButton=new JButton("Send");
        sendMailSubject=new JTextField();
        sendMailContent=new JTextArea();
        sendMailTo=new JTextField();
        sendMailSubject1=new JTextField();
        sendMailTo1=new JTextField();
        sendMailPanel.add(mailSendButton);
        sendMailPanel.add(sendMailSubject1);
        sendMailPanel.add(sendMailSubject);
        sendMailPanel.add(sendMailContent);
        sendMailPanel.add(sendMailTo1);
        sendMailPanel.add(sendMailTo);
        sendMailSubject1.setBounds(0,0,50,50);
        sendMailSubject.setBounds(50,0,640,50);
        sendMailContent.setBounds(0,50,690,470);
        sendMailTo1.setBounds(0,520,50,30);
        sendMailTo.setBounds(50,520,550,30);
        mailSendButton.setBounds(600,520,90,30);
        sendMailSubject1.setText("Subject:");
        sendMailSubject1.setEditable(false);
        sendMailTo1.setText("To:");
        sendMailTo1.setEditable(false);

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
    }
}
