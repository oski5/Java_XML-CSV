import Import.ImportCSV;
import Import.ImportXML;

public class Main {
    public static void main(String[] args) {
        String x = "D:/file.xml";
        ImportXML fp = new ImportXML(x);
        System.out.println(fp.filePath);
        fp = new ImportXML();
    }
}