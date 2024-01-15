package Import;
import Import.toFile;

import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImportXML{
    public static String filePath; // sciezka do pliku
    public static String exportPath; // sciezka do pliku
    public String[] tab; // tablica pomocnicza
    List<String> data = new ArrayList<>(); // lista danych
    String[] output; // tablica wyjsciowa

    public ImportXML(String file, String export) {
        filePath = file;
        exportPath = export;
    }

    // usuwa z ciagu znak o podanym indeksie
    private String delString(String input, int index){
        return input.substring(0, index) + input.substring(index + 1);
    }
    // usuwa z ciagu podany znak
    private String delChar(String input, char c){
        int index = input.indexOf(c);
        return delString(input, index);
    }
    // usuwa ciag od startIndex do endIndex
    private String delString(String input, int startIndex, int endIndex){
        return input.substring(0, startIndex) + input.substring(endIndex);
    }
    // zmienia znak w ciagu na inny
    private String changeStringChar(String input, char startChar, char endChar){
        return input.substring(0, input.indexOf(startChar)) + endChar + input.substring(input.indexOf(startChar) + 1);
    }
    // zwraca ilosc liter
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
    // usuwa null z tablicy
    private String[] RemoveNullVal(String[] input){
        List<String> list = new ArrayList<String>();
        for(String s : input) {
            if(s != null && s.length() > 0) {
                list.add(s);
            }
        }
        input = list.toArray(new String[list.size()]);
        return input;
    }

    public ImportXML() {
        try {
            // Tworzy scierzke do pliku
            Path path = Paths.get(filePath);
            // Tworzy liste
            List<String> lines = Files.readAllLines(path);
            // Przepisuje liste do tablicy
            tab = lines.toArray(new String[lines.size()]);
            output = new String[tab.length];
            // Iteruje linie po lini
            int i; // iterator i
            int j; // iterator j
            int k; // iterator k
            int num_of_tabs = 0; // ilosc tab
            int temp = 1; // zmienna pomocnicza
            String LineZero = "";
            String[] LineZeroT = new String[tab.length];

            // bazowa konwersja
            for (i = 0; i < tab.length; i++) {
                // usuwanie 0 lini
                if(tab[i].contains("<?xml")){
                    tab[i] = tab[i].replace(tab[i], "");
                    continue;
                }

                // okreslanie ilosci spacji
                if(Character.isWhitespace(tab[i].charAt(num_of_tabs)) || '<' == tab[i].charAt(num_of_tabs))
                    num_of_tabs+=2;
                if('<' != tab[i].charAt(num_of_tabs))
                    num_of_tabs-=2;

                //usuwanie pierwszej i ostatniej lini
                if(num_of_tabs == 0){
                    tab[i] = tab[i].replace(tab[i], "");
                }

                //##########    Wyświetl ilosc spacji   ##########
                //System.out.println("### "+"T:"+num_of_tabs+" ###");
                //################################################

                // usuwanie spacji
                for(j=0; j<num_of_tabs; j++){
                    tab[i] = delChar(tab[i], ' ');
                }

                // pobieranie danych
                if(num_of_tabs == 4 ){
                    if(tab[i].contains("<") || tab[i].contains(">")){
                        tab[i] = delChar(tab[i], '<');
                        tab[i] = changeStringChar(tab[i], '>', ';');
                        tab[i] = delString(tab[i], tab[i].indexOf('<'), tab[i].indexOf('>')+1);
                    }
                }
                if(num_of_tabs == 2 ){
                    if(temp == 1) {
                        tab[i] = delString(tab[i], tab[i].indexOf('<'), tab[i].indexOf(' ') + 1);
                        j=1;
                        while (j>0) {
                            if(tab[i].contains("=")){
                                tab[i] = delChar(tab[i], '=');
                            }
                            else j--;
                        }
                        tab[i] = delChar(tab[i], '>');
                        temp--;
                    }else{
                        tab[i] = tab[i].replace(tab[i], "");
                        temp++;
                    }
                }

                // wypisz linie
                // System.out.println(tab[i]);

                // przepisuje dane do list
                if(!(tab[i].isEmpty())) {
                    //System.out.println(tab[i]);
                    data.add(tab[i]);
                }else{
                    //headers.add(tab[i]);
                    //System.out.println("#USUNIETO# " + tab[i]);
                }
            }

            k = 0;
            // pobieranie naglowkow
            for(i = 0; i < data.size(); i++) {
                String t = data.get(i);
                String h = ""; // headers
                int pom = 0; // pomocnicze
                int counter = 0;

                // zewnetrzne
                if(data.get(i).contains("\"")) {
                    // headers
                    h = t;
                    for(j = 0; j<((countChars(t,'\"'))/2); j++){
                        h = delString(h, h.indexOf('\"'), nextIndexOf(h, 1, '\"')+1);
                        counter++;
                    }
                    pom = 1;
                }
                // wewnetrzne
                else if(data.get(i).contains(";")) {
                    // headers
                    h = t;
                    h = delString(h, h.indexOf(';'), h.length());
                }

                // wpistwanie naglowkow do LineZero i LineZeroT
                if(pom == 1){
                    t = h;
                    for(j = 0; j <= countChars(h, ' '); j++){
                        t = h;
                        if(j == 0){
                            t = delString(h, h.indexOf(' '), h.length());
                        }
                        else{
                            t = delString(h, 0, nextIndexOf(h, j-1, ' ')+1);
                            if(t.indexOf(' ') != -1) {
                                t = delString(t, t.indexOf(' '), t.length());
                            }
                        }
                        if(!LineZero.contains(t)){
                            if(j == 0) {
                                LineZero = LineZero + "_" + t;
                                LineZeroT[k] = t;
                                k++;
                            }
                            else {
                                LineZero = LineZero + ",_" + t;
                                LineZeroT[k] = t;
                                k++;
                            }
                        }
                    }
                    pom = 0;
                }
                else{
                    if(!LineZero.contains(h)){
                        if(LineZero.isEmpty()) {
                            LineZero = LineZero +  h;
                            LineZeroT[k] = h;
                            k++;
                        }
                        else {
                            LineZero = LineZero + "," + h;
                            LineZeroT[k] = h;
                            k++;
                        }
                    }
                }

                //System.out.println("HEADER:   "+h);
                //.out.println("DATA:    "+d);
            }

            LineZeroT = RemoveNullVal(LineZeroT);
            String[] LineNextT = new String[LineZeroT.length];
            k = 0;
            // pobieranie danych
            for(i = 0; i < data.size(); i++){
                String t = data.get(i);
                String h = ""; // headers
                String d = ""; // data
                int pom = 0; // pomocnicze
                int counter = 0;

                // zewnetrzne
                if(data.get(i).contains("\"")) {
                    // headers
                    h = t;
                    for(j = 0; j<((countChars(t,'\"'))/2); j++){
                        h = delString(h, h.indexOf('\"'), nextIndexOf(h, 1, '\"')+1);
                        counter++;
                    }
                    // data
                    d = t;
                    d = delString(d, 0, d.indexOf('\"')+1);
                    for(j = 1; j<=counter; j++){
                        if(j==counter) {
                            d = delString(d,d.indexOf('\"'));
                        }
                        else {
                            d = delString(d, d.indexOf('\"'), nextIndexOf(d, 1, '\"'));
                            d = changeStringChar(d, '\"', ';');
                        }
                    }
                    k++;
                    pom = 1;
                }
                // wewnetrzne
                else if(data.get(i).contains(";")) {
                    // headers
                    h = t;
                    h = delString(h, h.indexOf(';'), h.length());
                    // data
                    d = t;
                    d = delString(d, 0, d.indexOf(';')+1);
                }

                // wpistwanie naglowkow do LineZero i LineZeroT
                if(pom == 1){
                    t = h;
                    for(j = 0; j <= countChars(h, ' '); j++){
                        t = h;
                        if(j == 0){
                            t = delString(h, h.indexOf(' '), h.length());
                        }
                        else{
                            t = delString(h, 0, nextIndexOf(h, j-1, ' ')+1);
                            if(t.indexOf(' ') != -1) {
                                t = delString(t, t.indexOf(' '), t.length());
                            }
                        }
                        if(!LineZero.contains(t)){
                            if(j == 0) {
                                /*
                                LineZero = LineZero + "_" + t;
                                LineZeroT[k] = t;
                                k++;
                                */
                            }
                            else {
                                /*
                                LineZero = LineZero + ",_" + t;
                                LineZeroT[k] = t;
                                k++;
                                */
                            }
                        }
                    }
                    pom = 0;
                }
                else{
                    if(!LineZero.contains(h)){
                        if(LineZero.isEmpty()) {
                            /*
                            LineZero = LineZero +  h;
                            LineZeroT[k] = h;
                            k++;
                            */
                        }
                        else {
                            /*
                            LineZero = LineZero + "," + h;
                            LineZeroT[k] = h;
                            k++;
                            */
                        }
                    }
                }

                if(h.contains(" ")) {
                    for (j = 0; j <= countChars(h, ' '); j++) {
                        if (j == 0) {
                            t = delString(h, h.indexOf(' '), h.length());
                        } else if (j == countChars(h, ' ')) {
                            t = delString(h, 0, nextIndexOf(h, j - 1, ' ') + 1);
                        } else {
                            t = delString(h, 0, nextIndexOf(h, j - 1, ' ') + 1);
                            t = delString(t, t.indexOf(' '), t.length());
                        }
                        for(int l = 0; l < LineZeroT.length; l++){
                            if(t.contains(LineZeroT[l])){
                                //System.out.println("T"+l+"-"+k+": " + t);
                                if(j==0){
                                    LineNextT[l] = delString(d, d.indexOf(';'), d.length());
                                    //System.out.println("LineNextT["+l+"]:"+LineNextT[l]);
                                }else if(j == countChars(h, ' ')){
                                    LineNextT[l] = delString(d, 0, nextIndexOf(d, j-1, ';')+1);
                                    //System.out.println("LineNextT["+l+"]:"+LineNextT[l]);
                                }else{
                                    LineNextT[l] = delString(d, 0, nextIndexOf(d, j - 1, ';') + 1);
                                    LineNextT[l] = delString(LineNextT[l], LineNextT[l].indexOf(';'), LineNextT[l].length());
                                    //System.out.println("LineNextT["+l+"]:"+LineNextT[l]);
                                }
                            }
                            //System.out.println("output: "+LineNextT[l]);
                        }
                        for(int l = 0; l < LineNextT.length; l++){
                            //System.out.println("l:"+l+"   k:"+k);
                            if(LineNextT[l]==null){
                                LineNextT[l]="";
                            }
                            if(l==0){
                                output[k] = LineNextT[l] + ",";
                            }else if(l == LineNextT.length-1){
                                output[k] = output[k] + LineNextT[l];
                            }else {
                                output[k] = output[k] + LineNextT[l] + ",";
                            }
                        }
                    }
                    for(int l = 0; l < LineNextT.length; l++){
                        LineNextT[l] = null;
                    }
                }
                else{
                    for (j = 0; j <= countChars(h, ' '); j++) {
                        for(int l = 0; l < LineZeroT.length; l++){
                            if(t.contains(LineZeroT[l])){
                                if(j==0) {
                                    LineNextT[l] = d;
                                }
                            }
                        }
                        for(int l = 0; l < LineNextT.length; l++){
                            //System.out.println("l:"+l+"   k:"+k);
                            if(LineNextT[l]==null){
                                LineNextT[l]="";
                            }
                            if(LineNextT[l]!="") {
                                if (l == 0) {
                                    output[k] = LineNextT[l] + ",";
                                } else if (l == LineNextT.length - 1) {
                                    output[k] = output[k] + LineNextT[l];
                                } else {
                                    output[k] = output[k] + LineNextT[l] + ",";
                                }
                            }
                        }
                    }
                    for(int l = 0; l < LineNextT.length; l++){
                        LineNextT[l] = null;
                    }
                }
            }

            output[0] = LineZero;
            //for(i = 0; i < output.length; i++){
            //    System.out.println("output["+i+"]: "+ output[i]);
            //}

            // export tablicy
            toFile tt = new toFile(output, exportPath);
            tt = new toFile();

        } catch (Exception e) {
            // Pokazuje bledy zwiazane ze scierzka pliku
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
    }
}
