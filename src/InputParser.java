import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InputParser {
    private StringBuilder contentBuilder = new StringBuilder();
    private Scanner input = new Scanner(System.in);
    private String filePath;
    private int chapter = 0, article = 0, point = 0;
    private Tree Rparent, Aparent;

    public InputParser (String filePath) {
        this.filePath = filePath;
    }

    public void parseInputFile () {
        Tree root = new Tree("ROOT", "Zawartosc pliku:");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1250"))) {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.matches(".*\\d{4}-\\d{2}-\\d{2}.*") || sCurrentLine.matches(".*Kancelaria Sejmu.*"))
                    sCurrentLine = "";
                else if(sCurrentLine.endsWith("-"))
                    sCurrentLine = sCurrentLine.substring(0, sCurrentLine.length()-1);
                else if (!sCurrentLine.endsWith(",") && !sCurrentLine.endsWith("."))
                    sCurrentLine += " ";
                else
                    sCurrentLine += "\n";

                if(sCurrentLine.matches("^[^a-z]*$") && sCurrentLine.length() > 2) {
                    Tree newChapter = new Tree("R" + Integer.toString((++chapter)), sCurrentLine);
                    root.addChild(newChapter);
                    Rparent = newChapter;
                } else if(sCurrentLine.matches("^Art. [0-9]*.$")) {
                    Tree newArticle = new Tree("A" + Integer.toString((++article)), sCurrentLine);
                    Rparent.addChild(newArticle);
                    Aparent = newArticle;
                } else if(sCurrentLine.matches("^Art. [0-9]*.$")) {
                    Tree newPoint = new Tree("P" + Integer.toString((++point)), sCurrentLine);
                    Aparent.addChild(newPoint);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        root.printPreorder(root);
    }
}
