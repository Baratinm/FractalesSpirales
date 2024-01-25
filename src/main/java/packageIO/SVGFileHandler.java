package packageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SVGFileHandler {
    private String filePath;
    private FileWriter monWriter;
    

    public SVGFileHandler(String filePath) {
        this.filePath = filePath;
        try {
            createFile();
            monWriter = new FileWriter(filePath);
            fillHeader();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void fillHeader() throws IOException{
        monWriter.write("<svg width=\"1000\" height=\"1000\" xmlns=\"http://www.w3.org/2000/svg\">");
    }

    public void printFile() throws FileNotFoundException{
        
        try (Scanner scan = new Scanner(new File(filePath))){
            while (scan.hasNextLine()) {
                System.out.println(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Le fichier n'a pas été trouvé");
        }
    }

    public void createFile() throws IOException{
        try{
        File monFile = new File(filePath);
        //String completePath = monFile.getAbsolutePath();
        monFile.createNewFile();
        } catch (IOException e){
            throw e;
        }
    }

    
    public void writeToFile() throws IOException{

        Scanner scan  = new Scanner(System.in);
        System.out.println("ecris un truc, laisse la ligne vide si tu veux arreter d'ecrire");
        String ligne = scan.nextLine();
        while (ligne !="") {
            monWriter.write(ligne+"\n");
            ligne = scan.nextLine();

        }
        scan.close();
    }
    public void addLine(double x1, double y1, double x2, double y2){
        try {
            monWriter.write("<line x1=\""+x1+"\" y1=\""+y1+"\" x2=\""+x2+"\" y2=\""+y2+"\" style=\"stroke:rgb(0,0,0);stroke-width:1\"/>\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //<line x1="0" y1="0" x2="200" y2="200" style="stroke:rgb(255,0,0);stroke-width:2" />
        //"<line x1=\""+x1+"\" y1=\""+y1+"\" x2=\""+x2+"\" y2=\""+y2+"\" style=\"stroke:rgb(255,0,0);stroke-width:2\" />

    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "TestFileHandler [filePath=" + filePath + "]";
    }

    public void close() throws IOException {
        try{
            monWriter.write("</svg>");
            monWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
