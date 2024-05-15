package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class EmailViewerGUI extends JFrame {
    private JTable emailTable;
    private JTextArea emailContent;

    public EmailViewerGUI(List<Map<String, String>> emails) {
        setTitle("Email Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columnNames = {"Konu", "Gönderen"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Map<String, String> email : emails) {
            tableModel.addRow(new Object[]{email.get("Konu"), email.get("Gönderen")});
        }

        emailTable = new JTable(tableModel);
        emailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        emailTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = emailTable.getSelectedRow();
            if (selectedRow != -1) {
                String content = emails.get(selectedRow).get("İçerik");
                emailContent.setText(content);
            }
        });

        emailContent = new JTextArea();
        emailContent.setEditable(false);
        emailContent.setLineWrap(true);
        emailContent.setWrapStyleWord(true);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(emailTable), new JScrollPane(emailContent));
        splitPane.setDividerLocation(200);

        add(splitPane, BorderLayout.CENTER);
    }

}