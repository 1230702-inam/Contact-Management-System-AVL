package application;

public class FileManager {

    private ContactManager manager;

    public FileManager(ContactManager manager) {
        this.manager = manager;
    }

    // تحميل من ملف
    public int loadFromFile(String filePath) {
        int count = 0;
        try {
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts.length == 4) {
                    String name = parts[0].trim();
                    String phone = parts[1].trim();
                    String email = parts[2].trim();
                    String category = parts[3].trim();

                    Contact c = new Contact(name, phone, email, category);
                    if (manager.addContact(c)) {
                        count++;
                    }
                }
            }
            br.close();
        } catch (java.io.IOException e) {
            System.out.println("error while download: " + e.getMessage());
        }
        return count;
    }

    // حفظ لملف
    public void saveToFile(String filePath) {
        try {
            java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(filePath));
            StringBuilder sb = new StringBuilder();
            manager.getAllForSave(sb);
            bw.write(sb.toString());
            bw.close();
        } catch (java.io.IOException e) {
            System.out.println("error whille saving: " + e.getMessage());
        }
    }
}