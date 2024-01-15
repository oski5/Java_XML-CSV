import Import.ImportCSV;
import Import.ImportXML;

public class Main {
    public static void main(String[] args) {

        // zalozenia xml -> csv :
        // - brak ';' w TestId="0002"

        String x = "D:/file.xml";       // sciezka wejsciowa XML ->
        String y = "D:/export.csv";     // sciezka wyjsciowa -> CSV
        String x2 = "D:/file.csv";      // sciezka wejsciowa CSV ->
        String y2 = "D:/export.xml";    // sciezka wyjsciowa -> XML

        ImportXML fxml = new ImportXML(x, y);       // sciezki
        fxml = new ImportXML();                     // wykonuje konwersje XML -> CSV

        ImportCSV fcsv = new ImportCSV(x2, y2);     // sciezki
        fcsv = new ImportCSV();                     // wykonuje konwersje CSV -> XMl
    }
}