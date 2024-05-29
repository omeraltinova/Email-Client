    package org.example;

    import java.awt.*;
    import java.io.*;
    import java.nio.charset.StandardCharsets;
    import java.util.*;
    import java.util.List;

    public class EmailReader {


        public static List<Map<String, String>> readEmails(String directoryPath) {

            File emailDir = new File(directoryPath);

            List<Map<String, String>> emailList = new ArrayList<>();

            // emails dizini varsa ve bu dizin gerçekten bir dizin ise
            if (emailDir.exists() && emailDir.isDirectory()) {

                File[] files = emailDir.listFiles();
                if (files != null) {
                    // Her dosya için
                    for (File file : files) {
                        // Dosya ise ve ismi .txt ile bitiyorsa (e-posta dosyaları)
                        if (file.isFile() && file.getName().endsWith(".txt")) {
                            // Dosyayı oku ve e-posta bilgilerini ayrıştır
                            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                                Map<String, String> emailData = new HashMap<>();
                                String line;
                                StringBuilder contentBuilder = new StringBuilder();

                                while ((line = reader.readLine()) != null) {
                                    // "Konu: " ile başlayan satır varsa, konu başlığını al
                                    if (line.startsWith("Konu: ")) {
                                        emailData.put("Konu", line.substring(6));
                                    } else if (line.startsWith("Gönderen: ")) {
                                        // "Gönderen: " ile başlayan satır varsa, gönderen bilgilerini al
                                        emailData.put("Gönderen", line.substring(10));
                                    }else if(line.startsWith("İçerik: ")){
                                        contentBuilder.append(line.substring(8));
                                        while ((line = reader.readLine()) != null) {
                                            // "Parça" ile başlayan satıra kadar olan satırları kontrol edin
                                            if (line.startsWith("Parça")) {
                                                break; // "Parça" ile başlayan satıra ulaşıldığında döngüyü sonlandırın
                                            }
                                            contentBuilder.append(line).append("\n");
                                        }
                                    }
                                    else if (line.startsWith("Düz Metin İçerik: ")) {
                                        // "Düz Metin İçerik: " ile başlayan satır varsa, içeriği al ve geri kalan tüm satırları da içeriğe ekle
                                        contentBuilder.append(line.substring(18)).append("\n");

                                        while ((line = reader.readLine()) != null) {
                                            // "Parça" ile başlayan satıra kadar olan satırları kontrol edin
                                            if (line.startsWith("Parça")) {
                                                break; // "Parça" ile başlayan satıra ulaşıldığında döngüyü sonlandırın
                                            }
                                            contentBuilder.append(line).append("\n");
                                        }
                                    }else if(line.contains("name=")){
                                        line = line.replace("\"","");
                                        line = line.replace(";","");
                                        contentBuilder.append(line.substring(line.indexOf("name="))).append("\n");
                                    }else if(line.contains("name*0=")){
                                        contentBuilder.append(line.substring(line.indexOf("name*0="))).append("\n");
                                    }
                                }
                                // İçeriği tamamlayıp içeriğe ekle
                                emailData.put("İçerik", contentBuilder.toString());
                                // E-posta bilgilerini listeye ekle
                                emailList.add(emailData);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            return emailList;
        }
        public List<String> attachmentsInfo(String names){
            List<String> nameList = new ArrayList<>();
            // nameList.length ile sayıları alınacak
            String[] lines = names.split("\n");

            for(String line : lines){
                if (line.startsWith("name=")) {
                    nameList.add(line.substring(5));
                }else if(line.contains("name*0=")){
                    nameList.add(line.substring(line.indexOf("name*0=") + 8));
                    System.out.println(line.substring(line.indexOf("name*0=") + 8));
                }
            }
            return nameList;
        }
        public void findInAttachments(String name) throws IOException {
            File attachments = new File("attachments");
            File[] files = attachments.listFiles();
            for(File file : files){
                if(file.getName().toString().contains(name)){
                    Desktop.getDesktop().open(file);
                }
            }
        }

    }

