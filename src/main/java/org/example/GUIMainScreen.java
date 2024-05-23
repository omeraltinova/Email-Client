package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Map;

import static org.example.AccountSelectionScreen.accountInfo;
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
    JPanel illusionPanel3;
    JPanel receivedMailClosePanel;
    JPanel sentMailClosePanel;
    JPanel sendMailTopPanel;
    JPanel sendMailBottomPanel;

    //Buttons

    JButton received;
    JButton sent;
    JButton sendMail;
    JButton mailSendButton;
    JButton mailSaveButton;
    JButton showReceivedClose;
    JButton showSentClose;
    JButton sendMailClose;
    JButton signOutButton;
    JButton refreshButton;

    //Scrollbars

    JScrollPane receivedScroll;
    JScrollPane sentScroll;
    JScrollPane sendScroll;
    JScrollPane receivedContentScroll;
    JScrollPane sentContentScroll;
    JScrollPane sendContentScroll;

    //Text Fields and Areas

    JTextArea receivedContent;
    JTextField receivedMailSearchBar;
    JTextArea sentContent;
    JTextField sentMailSearchbar;
    JTextField sendMailSubject;
    JTextArea sendMailContent;
    JTextField sendMailTo;

    //Toolbars & Menus & Popup Menus and menu items
    JMenuBar mainScreenMenubar;
    JPopupMenu receivedMailPopupMenu;
    JMenuItem receivedMailDelete;
    JPopupMenu sentMailPopupMenu;
    JMenuItem sentMailDelete;
    JMenu mailSearchOptions;
    JMenu receivedMailSearchOptions;
    JMenu sentMailSearchOptions;
    JMenuItem searchReceivedSubject;
    JMenuItem searchSender;
    JMenuItem searchReceivedContent;
    JMenuItem searchSentSubject;
    JMenuItem searchSentTo;
    JMenuItem searchSentContent;

    //Labels

    JLabel sendMailTo1;
    JLabel sendMailSubject1;
    JLabel illusionLabel1;
    JLabel illusionLabel2;
    JLabel illusionLabel3;
    JLabel illusionLabel4;
    JLabel illusionLabel5;

    //Tables and models

    DefaultTableModel receivedMailTableModel;
    JTable receivedMailTable;
    DefaultTableModel sentMailTableModel;
    JTable sentMailTable;
    DefaultTableModel sendMailTableModel;
    JTable sendMailTable;

    List<Map<String, String>> draftEmailsReset;

    static int j=0;

    GUIMainScreen(List<Map<String, String>> receivedEmails,List<Map<String, String>> sentEmails,Map<String, String> accountInfo,List<Map<String, String>> draftEmails){


        //Pencerenin genel özellikleri

        mainScreen=new JFrame("Email-Client Main Screen");
        mainScreen.setLayout(new BorderLayout());
        mainScreen.setVisible(true);
        mainScreen.setSize(1368,720);
        mainScreen.setLocationRelativeTo(null);
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreen.getContentPane().setBackground(new Color(33, 33, 33));

        mainScreenMenubar=new JMenuBar();
        mainScreenMenubar.setBackground(new Color(33, 33, 33));
        illusionPanel3=new JPanel(new CardLayout());
        illusionPanel3.setBackground(new Color(33, 33, 33));
        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(33, 33, 33));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        receivedMailSearchBar=new JTextField("Search in received mails");
        receivedMailSearchBar.setMargin(new Insets(0, 10, 0, 10));
        receivedMailSearchBar.setBackground(new Color(45, 52, 54));
        receivedMailSearchBar.setForeground(Color.WHITE);
        sentMailSearchbar=new JTextField("Search in sent mails");
        sentMailSearchbar.setMargin(new Insets(0, 10, 0, 10));
        sentMailSearchbar.setBackground(new Color(45, 52, 54));
        sentMailSearchbar.setForeground(Color.WHITE);
        illusionLabel1=new JLabel();

        mailSearchOptions=new JMenu("Search Options");
        mailSearchOptions.setForeground(Color.WHITE);
        searchReceivedSubject=new JMenuItem("Subject");
        searchReceivedSubject.addActionListener(this);
        searchSender=new JMenuItem("Sender");
        searchSender.addActionListener(this);
        searchReceivedContent=new JMenuItem("Content");
        searchReceivedContent.addActionListener(this);
        searchSentSubject=new JMenuItem("Subject");
        searchSentSubject.addActionListener(this);
        searchSentTo=new JMenuItem("To");
        searchSentTo.addActionListener(this);
        searchSentContent=new JMenuItem("Content");
        searchSentContent.addActionListener(this);

        mailSearchOptions.add(searchReceivedSubject);
        mailSearchOptions.add(searchSender);
        mailSearchOptions.add(searchReceivedContent);
        mailSearchOptions.add(searchSentSubject);
        mailSearchOptions.add(searchSentTo);
        mailSearchOptions.add(searchSentContent);
        mailSearchOptions.setVisible(false);
        searchReceivedSubject.setVisible(false);
        searchSender.setVisible(false);
        searchReceivedContent.setVisible(false);
        searchSentSubject.setVisible(false);
        searchSentTo.setVisible(false);
        searchSentContent.setVisible(false);

        mainScreenMenubar.add(refreshButton);
        mainScreenMenubar.add(mailSearchOptions);
        mainScreenMenubar.add(illusionPanel3);
        illusionPanel3.add(illusionLabel1);
        illusionPanel3.add(receivedMailSearchBar);
        illusionPanel3.add(sentMailSearchbar);
        receivedMailSearchBar.setVisible(false);
        sentMailSearchbar.setVisible(false);
        mainScreenMenubar.add(Box.createHorizontalGlue());
        JLabel profilePicture = new JLabel(resizeIcon(new ImageIcon(accountInfo.get("Image")), 50, 50));
        JLabel nameLabel = new JLabel(accountInfo.get("Name"));
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setBackground(new Color(33, 33, 33));
        nameLabel.setForeground(Color.WHITE);
        signOutButton=new JButton("Sign Out");
        signOutButton.addActionListener(this);
        signOutButton.setBackground(new Color(33, 33, 33));
        signOutButton.setForeground(Color.WHITE);
        signOutButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        mainScreenMenubar.add(profilePicture);
        mainScreenMenubar.add(Box.createRigidArea(new Dimension(10, 0)));
        mainScreenMenubar.add(nameLabel);
        mainScreenMenubar.add(Box.createRigidArea(new Dimension(10, 0)));
        mainScreenMenubar.add(signOutButton);
        mainScreen.add(mainScreenMenubar, BorderLayout.NORTH);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refrestMainScreen();
            }
        });

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
        received.setBackground(new Color(45, 52, 54));
        received.setForeground(Color.WHITE);
        received.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        sent.setBackground(new Color(45, 52, 54));
        sent.setForeground(Color.WHITE);
        sent.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        sendMail.setBackground(new Color(45, 52, 54));
        sendMail.setForeground(Color.WHITE);
        sendMail.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        //Alınan e-postaların gözükeceği yer

        String[] receivedMailColumns = {"Sender", "Subject"};
        receivedMailTableModel = new DefaultTableModel(receivedMailColumns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        receivedMailTable = new JTable(receivedMailTableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    component.setBackground(row % 2 == 0 ? new Color(45, 52, 54) : new Color(60, 63, 65));
                }
                return component;
            }
        };
        receivedMailTable.getTableHeader().setReorderingAllowed(false);
        receivedScroll = new JScrollPane(receivedMailTable);
        for(Map<String, String> email : receivedEmails){
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

        receivedMailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        receivedMailTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow=receivedMailTable.getSelectedRow();
            if(selectedRow!=-1) {
                illusionPanel2.setVisible(false);
                sendMailPanel.setVisible(false);
                showSentMailsPanel.setVisible(false);
                showReceivedMailsPanel.setVisible(true);
                String subject = receivedEmails.get(selectedRow).get("Konu");
                String content = receivedEmails.get(selectedRow).get("İçerik");
                String sender = receivedEmails.get(selectedRow).get("Gönderen");
                receivedContent.setText("Subject:  " + subject + "\nSender:  " + sender + "\n\nContent:\n" + content);

            }
        });

        receivedMailTable.setFillsViewportHeight(true);
        receivedMailTable.setBackground(new Color(33, 33, 33));
        receivedMailTable.setForeground(Color.WHITE);
        receivedMailTable.setSelectionBackground(new Color(99, 110, 114));
        receivedMailTable.setSelectionForeground(Color.BLACK);
        receivedMailTable.setGridColor(new Color(45, 52, 54));
        receivedScroll.getViewport().setBackground(new Color(33, 33, 33));

        for(int i=0;i<receivedMailTable.getColumnModel().getColumnCount();i++){
            receivedMailTable.getColumnModel().getColumn(i).setResizable(false);
        }
        receivedMailPopupMenu=new JPopupMenu();
        receivedMailDelete=new JMenuItem("Sil");
        receivedMailPopupMenu.add(receivedMailDelete);
        receivedMailTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = receivedMailTable.rowAtPoint(e.getPoint());
                    receivedMailTable.setRowSelectionInterval(row, row);
                    receivedMailPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        receivedMailDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = receivedMailTable.getSelectedRow();
                MailManagement mm = new MailManagement();
                String sub = receivedEmails.get(row).get("Konu");;

                if (row != -1) {
                    showReceivedMailsPanel.setVisible(false);
                    illusionPanel2.setVisible(true);
                    receivedMailTableModel.removeRow(row);
                    receivedEmails.remove(row);
                    mm.fetchEmails(sub,"INBOX");
                }

            }
        });

        //Gönderilen e-postaların gözükeceği yer

        String[] sentMailColumns = {"To", "Subject"};
        sentMailTableModel=new DefaultTableModel(sentMailColumns,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        sentMailTable=new JTable(sentMailTableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    component.setBackground(row % 2 == 0 ? new Color(45, 52, 54) : new Color(60, 63, 65));
                }
                return component;
            }
        };
        sentMailTable.getTableHeader().setReorderingAllowed(false);
        sentScroll=new JScrollPane(sentMailTable);
        for(Map<String, String> email : sentEmails){
            sentMailTableModel.addRow(new Object[]{email.get("Gönderen"), email.get("Konu")});
        }
        mailPanel.add(sentScroll);
        sentScroll.setVisible(false);
        showSentMailsPanel=new JPanel(new BorderLayout());
        showSentMailsPanel.setBackground(new Color(33, 33, 33));
        showMailPanel.add(showSentMailsPanel);
        sentContent=new JTextArea();
        sentContent.setBackground(new Color(33, 33, 33));
        sentContent.setForeground(Color.WHITE);
        sentContent.setLineWrap(true);
        sentContent.setWrapStyleWord(true);
        sentContent.setMargin(new Insets(10, 10, 10, 10));
        sentContent.setEditable(false);
        showSentClose=new JButton("Close");
        showSentClose.setBackground(new Color(255, 0, 0));
        showSentClose.setForeground(Color.WHITE);
        showSentClose.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        sentContentScroll=new JScrollPane(sentContent);
        sentMailClosePanel=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sentMailClosePanel.setBackground(new Color(33, 33, 33));
        showSentMailsPanel.add(sentMailClosePanel,BorderLayout.NORTH);
        sentMailClosePanel.add(showSentClose);
        showSentMailsPanel.add(sentContentScroll,BorderLayout.CENTER);
        showSentClose.addActionListener(this);

        for(int i=0;i<sentMailTable.getColumnModel().getColumnCount();i++){
            sentMailTable.getColumnModel().getColumn(i).setResizable(false);
        }
        sentMailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sentMailTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow=sentMailTable.getSelectedRow();
            if(selectedRow!=-1) {
                illusionPanel2.setVisible(false);
                sendMailPanel.setVisible(false);
                showReceivedMailsPanel.setVisible(false);
                showSentMailsPanel.setVisible(true);
                String subject = sentEmails.get(selectedRow).get("Konu");
                String content = sentEmails.get(selectedRow).get("İçerik");
                String to = sentEmails.get(selectedRow).get("Gönderen");
                sentContent.setText("Subject:  " + subject + "\nTo:  " + to + "\n\nContent:\n" + content);
            }
        });

        sentMailTable.setFillsViewportHeight(true);
        sentMailTable.setBackground(new Color(33, 33, 33));
        sentMailTable.setForeground(Color.WHITE);
        sentMailTable.setSelectionBackground(new Color(99, 110, 114));
        sentMailTable.setSelectionForeground(Color.BLACK);
        sentMailTable.setGridColor(new Color(45, 52, 54));
        sentScroll.getViewport().setBackground(new Color(33, 33, 33));

        sentMailPopupMenu=new JPopupMenu();
        sentMailDelete=new JMenuItem("Sil");
        sentMailPopupMenu.add(sentMailDelete);
        sentMailTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = sentMailTable.rowAtPoint(e.getPoint());
                    sentMailTable.setRowSelectionInterval(row, row);
                    sentMailPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        sentMailDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = sentMailTable.getSelectedRow();
                MailManagement mm = new MailManagement();
                String sub = receivedEmails.get(row).get("Konu");
                if (row != -1) {
                    showSentMailsPanel.setVisible(false);
                    illusionPanel2.setVisible(true);
                    sentMailTableModel.removeRow(row);
                    sentEmails.remove(row);
                    mm.fetchEmails(sub,"sent");
                }
            }
        });

        //E-posta gönderme paneli

        String[] sendMailColumns = {"To", "Subject"};
        sendMailTableModel=new DefaultTableModel(sendMailColumns,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        sendMailTable=new JTable(sendMailTableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    component.setBackground(row % 2 == 0 ? new Color(45, 52, 54) : new Color(60, 63, 65));
                }
                return component;
            }
        };
        for(Map<String, String> email : draftEmails){
            sendMailTableModel.addRow(new Object[]{email.get("Gönderen"), email.get("Konu")});
        }
        sendMailTable.getTableHeader().setReorderingAllowed(false);
        sendScroll=new JScrollPane(sendMailTable);
        mailPanel.add(sendScroll);
        sendScroll.setVisible(false);
        sendMailPanel=new JPanel(new BorderLayout());
        showMailPanel.add(sendMailPanel);
        sendMailTopPanel=new JPanel(new FlowLayout());
        sendMailTopPanel.setBackground(new Color(33,33,33));
        sendMailBottomPanel=new JPanel(new FlowLayout());
        sendMailBottomPanel.setBackground(new Color(33,33,33));
        sendMailSubject=new JTextField();
        sendMailSubject.setBackground(new Color(45, 52, 54));
        sendMailSubject.setForeground(Color.WHITE);
        sendMailContent=new JTextArea();
        sendMailContent.setBackground(new Color(45, 52, 54));
        sendMailContent.setForeground(Color.WHITE);
        sendMailContent.setMargin(new Insets(10, 10, 10, 10));
        sendMailTo=new JTextField();
        sendMailTo.setBackground(new Color(45, 52, 54));
        sendMailTo.setForeground(Color.WHITE);
        sendMailSubject1=new JLabel();
        sendMailSubject1.setBackground(new Color(33,33,33));
        sendMailSubject1.setForeground(Color.WHITE);
        sendMailTo1=new JLabel();
        sendMailTo1.setForeground(Color.WHITE);
        sendContentScroll=new JScrollPane(sendMailContent);
        sendMailClose=new JButton("Close");
        sendMailClose.setBackground(new Color(255, 0, 0));
        sendMailClose.setForeground(Color.WHITE);
        sendMailClose.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        mailSendButton=new JButton("Send");
        mailSendButton.setBackground(new Color(45, 52, 54));
        mailSendButton.setForeground(Color.WHITE);
        mailSaveButton=new JButton("Save");
        mailSaveButton.setBackground(new Color(45, 52, 54));
        mailSaveButton.setForeground(Color.WHITE);
        sendMailPanel.add(sendMailTopPanel,BorderLayout.NORTH);
        sendMailPanel.add(sendContentScroll,BorderLayout.CENTER);
        sendMailPanel.add(sendMailBottomPanel,BorderLayout.SOUTH);
        sendMailTopPanel.add(sendMailSubject1);
        sendMailTopPanel.add(sendMailSubject);
        sendMailTopPanel.add(sendMailClose);
        sendMailSubject.setPreferredSize(new Dimension(500,30));
        sendMailBottomPanel.add(sendMailTo1);
        sendMailBottomPanel.add(sendMailTo);
        sendMailBottomPanel.add(mailSaveButton);
        sendMailBottomPanel.add(mailSendButton);
        sendMailTo.setPreferredSize(new Dimension(500,30));
        sendMailClose.addActionListener(this);
        sendMailSubject1.setText("Subject:");
        sendMailTo1.setText("To:");
        sendMailContent.setLineWrap(true);
        sendMailContent.setWrapStyleWord(true);
        mailSendButton.addActionListener(this);
        mailSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MailManagement saver = new MailManagement();
                saver.draftSaver(Mail.getUSERNAME(),sendMailTo.getText(),sendMailSubject.getText(),sendMailContent.getText());
                sendMailSubject.setText("");
                sendMailContent.setText("");
                sendMailTo.setText("");
            }
        });
        for(int i=0;i<sendMailTable.getColumnModel().getColumnCount();i++){
            sendMailTable.getColumnModel().getColumn(i).setResizable(false);
        }
        sendMailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sendMailTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = sendMailTable.getSelectedRow();
            if (selectedRow != -1) {
                illusionPanel2.setVisible(false);
                showSentMailsPanel.setVisible(false);
                showReceivedMailsPanel.setVisible(false);
                sendMailPanel.setVisible(true);
                String subject = draftEmails.get(selectedRow).get("Konu");
                String content = draftEmails.get(selectedRow).get("İçerik");
                String to = draftEmails.get(selectedRow).get("Gönderen");
                sendMailSubject.setText(subject);
                sendMailContent.setText(content);
                sendMailTo.setText(to);
            }
        });
        sendMailTable.setFillsViewportHeight(true);
        sendMailTable.setBackground(new Color(33, 33, 33));
        sendMailTable.setForeground(Color.WHITE);
        sendMailTable.setSelectionBackground(new Color(99, 110, 114));
        sendMailTable.setSelectionForeground(Color.BLACK);
        sendMailTable.setGridColor(new Color(45, 52, 54));
        sendScroll.getViewport().setBackground(new Color(33, 33, 33));

        mainScreen.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                deleteFilesInDirectory("emails/inbox");
                deleteFilesInDirectory("emails/sent");
            }
        });


        receivedMailSearchBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forResearchThing(receivedEmails,receivedMailSearchBar.getText());
            }
        });

        sentMailSearchbar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forResearchThing(sentEmails,sentMailSearchbar.getText());
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==received){
            illusionPanel1.setVisible(false);
            sentScroll.setVisible(false);
            sendScroll.setVisible(false);
            illusionLabel1.setVisible(false);
            sentMailSearchbar.setVisible(false);
            receivedMailSearchBar.setVisible(true);
            receivedMailSearchBar.setText("Search in received mails");
            mailSearchOptions.setVisible(true);
            mailSearchOptions.setText("Search Options");
            searchSentSubject.setVisible(false);
            searchSentTo.setVisible(false);
            searchSentContent.setVisible(false);
            searchReceivedSubject.setVisible(true);
            searchSender.setVisible(true);
            searchReceivedContent.setVisible(true);
            receivedScroll.setVisible(true);
        }
        if(e.getSource()==sent){
            illusionPanel1.setVisible(false);
            receivedScroll.setVisible(false);
            sendScroll.setVisible(false);
            illusionLabel1.setVisible(false);
            receivedMailSearchBar.setVisible(false);
            sentMailSearchbar.setVisible(true);
            sentMailSearchbar.setText("Search in sent mails");
            mailSearchOptions.setVisible(true);
            mailSearchOptions.setText("Search Options");
            searchReceivedSubject.setVisible(false);
            searchSender.setVisible(false);
            searchReceivedContent.setVisible(false);
            searchSentSubject.setVisible(true);
            searchSentTo.setVisible(true);
            searchSentContent.setVisible(true);
            sentScroll.setVisible(true);
        }
        if(e.getSource()==sendMail){
            illusionPanel1.setVisible(false);
            receivedScroll.setVisible(false);
            sentScroll.setVisible(false);
            illusionPanel2.setVisible(false);
            showReceivedMailsPanel.setVisible(false);
            showSentMailsPanel.setVisible(false);
            illusionLabel1.setVisible(false);
            receivedMailSearchBar.setVisible(false);
            sentMailSearchbar.setVisible(false);
            sentMailSearchbar.setText("Search in sent mails");
            mailSearchOptions.setVisible(false);
            sendScroll.setVisible(true);
            sendMailPanel.setVisible(true);
            sendMailSubject.setText("");
            sendMailContent.setText("");
            sendMailTo.setText("");
        }
        if(e.getSource()==mailSendButton){
            MailManagement aa = new MailManagement();
            String sendSubject=sendMailSubject.getText(),sendContent=sendMailContent.getText(),sendTo=sendMailTo.getText();
            System.out.println("Subject:"+sendSubject+"\n");
            System.out.println("Content:"+sendContent+"\n");
            System.out.println("To:"+sendTo);
            aa.sendPlainTextEmail(Mail.getUSERNAME(),sendTo,sendSubject,sendContent,true);
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
            deleteFilesInDirectory("emails/sent");
            mainScreen.dispose();
            List<AccountSelectionScreen.Account> accounts = readAccountsFromFile();
            new AccountSelectionScreen(accounts);
        }
//        if (e.getSource()==mailSaveButton){
//            MailManagement saver = new MailManagement();
//            saver.draftSaver(Mail.getUSERNAME(),sendMailTo.getText(),sendMailSubject.getText(),sendMailContent.getText());
//            sendMailTableModel.setRowCount(0);
//        }
        if (e.getSource()==searchReceivedSubject){
            mailSearchOptions.setText("Subject");
        }
        if (e.getSource()==searchSender){
            mailSearchOptions.setText("Sender");
        }
        if (e.getSource()==searchReceivedContent){
            mailSearchOptions.setText("Content");
        }
        if (e.getSource()==searchSentSubject){
            mailSearchOptions.setText("Subject");
        }
        if (e.getSource()==searchSentTo){
            mailSearchOptions.setText("To");
        }
        if (e.getSource()==searchSentContent){
            mailSearchOptions.setText("Content");
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
    public void forResearchThing(List<Map<String, String>> emails, String input) {
        input = input.toLowerCase();
        for (Map<String, String> email : emails) {
            if (mailSearchOptions.getText()=="Subject"){
                if (email.get("Konu").toLowerCase().contains(input))
                    takeSearchResult(email.get("Gönderen"), email.get("Konu"), email.get("İçerik"));
            }
            if (mailSearchOptions.getText()=="Sender"){
                if (email.get("Gönderen").toLowerCase().contains(input))
                    takeSearchResult(email.get("Gönderen"), email.get("Konu"), email.get("İçerik"));
            }
            if (mailSearchOptions.getText()=="Content"){}{
                if(email.get("İçerik").toLowerCase().contains(input))
                    takeSearchResult(email.get("Gönderen"), email.get("Konu"), email.get("İçerik"));
            }
            if (mailSearchOptions.getText()=="To"){
                takeSearchResult(email.get("Gönderen"), email.get("Konu"), email.get("İçerik"));
            }
        }
    }
    public void takeSearchResult(String sender,String subject, String content){
        System.out.println("Subject\n"+subject+"\nsender\n"+sender+"\n\nContent\n\n"+content+"\n\n\n");
    }
    public void refrestMainScreen(){
        mainScreen.dispose();
        MailManagement mm = new MailManagement();
        mm.fetchEmails("","inbox");
        mm.fetchEmails("","sent");
        List<Map<String, String>> receivedEmails = EmailReader.readEmails("emails/inbox");
        List<Map<String, String>> sentEmails = EmailReader.readEmails("emails/sent");
        List<Map<String, String>> draftEmails = EmailReader.readEmails("emails/draft/"+MailManagement.getUSERNAME());
        GUIMainScreen anaEkran = new GUIMainScreen((List<Map<String, String>>) receivedEmails,(List<Map<String, String>>) sentEmails,(Map<String,String>) accountInfo,(List<Map<String, String>>) draftEmails);
    }
}
