package Import;

public class toFile {
    public static String[] tab;
    public static String filePath;
    public toFile(String[] table, String path) {
        tab = table;
        filePath = path;
    }
    public toFile(){
        try {
            for (int i = 0; i < tab.length; i++) {
                if (tab[i] != null)
                    System.out.println(tab[i]);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
