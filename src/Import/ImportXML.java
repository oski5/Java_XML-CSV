package Import;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class ImportXML{
    public static String filePath;

    public ImportXML(String x) {
        filePath = x;
    }

    public ImportXML() {
        try {
            // Tworzy scierzke do pliku
            Path path = Paths.get(filePath);
            // Tworzy liste
            List<String> lines = Files.readAllLines(path);
            // Przepisuje liste do tablicy
            String[] tab = lines.toArray(new String[lines.size()]);
            // Iteruje linie po lini
            int i; // iterator
            int num_of_tabs = 0; // ilosc tab
            String tab_key = "\t";
            for (i = 0; i < tab.length; i++) {
                if(tab[i].contains("<?xml")) continue;

                // okreslanie ilosci spacji
                if(Character.isWhitespace(tab[i].charAt(num_of_tabs)) || '<' == tab[i].charAt(num_of_tabs))
                    num_of_tabs+=2;
                if('<' != tab[i].charAt(num_of_tabs))
                    num_of_tabs-=2;

                //##########    Wyświetl ilosc spacji   ##########
                System.out.println("### "+"T:"+num_of_tabs+" ###");
                //################################################

                // tu trzeba zrobic usuwanie znakow
                //tab[i] = tab[i].replace("</>", "");

                // wypisz linie
                System.out.println(tab[i]);
            }

            //##########    Wyświetl ilosc spacji   ##########
            //System.out.println("### "+"T:"+num_of_tabs+" ###");
            //################################################

        } catch (IOException e) {
            // Pokazuje bledy zwiazane ze scierzka pliku
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }
}
