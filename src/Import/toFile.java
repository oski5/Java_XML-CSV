package Import;

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
public class toFile {
    public static String[] tab;
    public static String filePath;
    public toFile(String[] table, String path) {
        tab = table;
        filePath = path;
    }
    public toFile() throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        try {
            for (String line : tab) {
                if(line != null) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }
        } finally {
            bufferedWriter.close();
        }
    }
}
