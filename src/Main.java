import Import.ImportCSV;
import Import.ImportXML;

public class Main {
    public static void main(String[] args) {

        // zalozenia xml -> csv :
        // - brak ';' w TestId="0002"

        String x = "D:/file.xml";   // sciezka wejsciowa XML ->
        String y = "D:/export.csv"; // sciezka wyjsciowa -> CSV
        String x2 = "D:/file.csv";  // sciezka wyjsciowa CSV ->
        String y2 = "D:/export.xml";  // sciezka wyjsciowa -> XML

        ImportXML fp = new ImportXML(x, y);
        //System.out.println(fp.filePath+"\n");
        fp = new ImportXML(); // wykonuje konwersje XML -> CSV
        //System.out.println("\n"+fp.exportPath);
    }
}