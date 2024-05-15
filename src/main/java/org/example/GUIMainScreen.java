package org.example;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
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
    JPanel sentMailPanel;
    //Buttons
    JButton received;
    JButton sent;
    //Scrollbars
    JScrollPane receivedScroll;
    JScrollPane sentScroll;
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
        selectMenu1.add(received);
        selectMenu1.add(sent);
        selectMenu1.setLayout(null);
        received.setBounds(10,10,200,30);
        received.addActionListener(this); //tıklayınca açılması için
        sent.setBounds(10,50,200,30);
        sent.addActionListener(this); //tıklayınca açılması için

        //Alınan e-postaların gözükeceği yer

        receivedMailPanel=new JPanel();
        mainScreen.add(receivedMailPanel);
        receivedMailPanel.setLayout(new BoxLayout(receivedMailPanel,BoxLayout.Y_AXIS));
        receivedMailPanel.setVisible(false);
        receivedMailPanel.setBackground(Color.gray);
        receivedMailPanel.setBounds(220,0,400,680);
        //-----------------------------
        String[] columnNames = {"Sender", "Subject"};
        /*Object[][] data = {
                {"alice@example.com", "Meeting Reminder"},
                {"bob@example.com", "Project Update"},
                {"charlie@example.com", "Invitation"}
        };*/
        DefaultTableModel receivedTableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(receivedTableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        for(Map<String, String> email : emails){
            receivedTableModel.addRow(new Object[]{email.get("Gönderen"), email.get("Konu")});
        }
        receivedMailPanel.add(scrollPane);
        //------------------------------
        //Gönderilen e-postaların gözükeceği yer
        sentMailPanel=new JPanel();
        mainScreen.add(sentMailPanel);
        sentMailPanel.setLayout(null);
        sentMailPanel.setVisible(false);
        sentMailPanel.setBackground(Color.green);
        sentMailPanel.setBounds(220,0,400,680);
    }

   @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==received){
            sentMailPanel.setVisible(false);
            receivedMailPanel.setVisible(true);
        }
        if(e.getSource()==sent){
            receivedMailPanel.setVisible(false);
            sentMailPanel.setVisible(true);
        }
    }
}
