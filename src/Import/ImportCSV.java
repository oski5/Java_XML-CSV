package Import;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImportCSV{
    public static String filePath; // sciezka do pliku
    public static String exportPath; // sciezka do pliku
    public String[] tab; // tablica pomocnicza
    List<String> data = new ArrayList<>(); // lista danych
    String[] output; // tablica wyjsciowa

    public ImportCSV(String file, String export) {
        filePath = file;
        exportPath = export;
    }

    // usuwa ciag od startIndex do endIndex
    private String delString(String input, int startIndex, int endIndex){
        return input.substring(0, startIndex) + input.substring(endIndex);
    }
    private Integer countChars(String input, char letter){
        int x = 0;
        for(int i=0; i<input.length(); i++){
            if(input.charAt(i) == letter)
                x++;
        }
        return x;
    }
    // znajduje n-ta litere
    private Integer nextIndexOf(String input, int index, char letter){
        int o = 0; // output
        int temp = 0;
        index++;
        for(int i=0; i< input.length()+1; i++){
            if(temp == index){
                o=i;
                break;
            }
            else if(input.charAt(i) == letter) {
                o=i;
                temp++;
            }
        }
        o--;
        return o;
    }

    public ImportCSV(){
        try{
            // Tworzy scierzke do pliku
            Path path = Paths.get(filePath);
            // Tworzy liste
            List<String> lines = Files.readAllLines(path);
            // Przepisuje liste do tablicy
            tab = lines.toArray(new String[lines.size()]);
            int counter = countChars(tab[0], ',')+1; // ilosc naglowkow
            String[] headers = new String[counter]; // naglowki
            String[] data = new String[counter]; // naglowki

            output = new String[(counter+2)*(tab.length-1)+3];

            output[0] = "<?xml version=\"1.0\"?>";
            output[1] = "<File>";
            output[(counter+2)*(tab.length-1)+2] = "</File>";

            int tabi = 2;

            for(int i=0; i<tab.length; i++){
                String l = tab[i];
                System.out.println(l);
                // headers
                if(i==0){
                    for(int j=0; j<counter; j++){
                        if(j==0){
                            headers[j] = delString(l, l.indexOf(','), l.length());
                        }else if(j == counter-1){
                            headers[j] = delString(l, 0, nextIndexOf(l, j-1, ',')+1);
                        }else{
                            headers[j] = delString(l, 0, nextIndexOf(l, j-1, ',')+1);
                            headers[j] = delString(headers[j], headers[j].indexOf(','), headers[j].length());
                        }
                    }
                }
                // data
                else{
                    for(int j=0; j<counter; j++){
                        if(j==0){
                            data[j] = delString(l, l.indexOf(','), l.length());
                        }else if(j == counter-1){
                            data[j] = delString(l, 0, nextIndexOf(l, j-1, ',')+1);
                        }else{
                            data[j] = delString(l, 0, nextIndexOf(l, j-1, ',')+1);
                            data[j] = delString(data[j], data[j].indexOf(','), data[j].length());
                        }
                    }
                    int k =0;
                    for(int j=0; j<(counter+2); j++){
                        if(j==0) {
                            output[tabi] = " <FileData>";
                            tabi++;
                        }else if(j==(counter+1)){
                            output[tabi] = " </FileData>";
                            tabi++;
                        }else{
                            output[tabi] = "    <"+headers[k]+">" + data[k] + "</"+headers[k]+">";
                            k++;
                            tabi++;
                        }
                    }
                }
            }

            System.out.println("=====");

            for(int i=0; i<output.length; i++){
                System.out.println(output[i]);
            }

            toFile tt = new toFile(output, exportPath);
            tt = new toFile();

        }catch (Exception e){
            System.out.println(e);
        }
    }
}