import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InputParser {
    private Scanner input = new Scanner(System.in);
    private String filePath, mode, element;
    private int chapter = 0, section = 0, article = 0, point = 0;
    private Tree Rparent, Sparent, Aparent;

    public InputParser(String filePath, String mode, String element) {
        this.filePath = filePath;
        this.mode = mode;
        this.element = element;
    }

    public void parseInputFile() {
        Tree root = new Tree("ROOT", "Zawartosc pliku:");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1250"))) {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.matches(".*\\d{4}-\\d{2}-\\d{2}.*") || sCurrentLine.matches(".*Kancelaria Sejmu.*"))
                    continue;
                else if (sCurrentLine.matches("^Rozdział \\w*$") && sCurrentLine.length() > 2) {
                    Tree newChapter = new Tree("Rozdział " + Integer.toString((++chapter)), sCurrentLine + "\n");
                    root.addChild(newChapter);
                    Rparent = newChapter;
                    section = 0;
                } else if (sCurrentLine.matches("^[^a-z]*$") && sCurrentLine.length() > 2) {
                    Tree newSection = new Tree("Sekcja " + Integer.toString((++section)), sCurrentLine + "\n");
                    Rparent.addChild(newSection);
                    Sparent = newSection;
                } else if (sCurrentLine.matches("^Art. [0-9]*.$")) {
                    Tree newArticle = new Tree("Artykuł " + Integer.toString((++article)), sCurrentLine + "\n");
                    Sparent.addChild(newArticle);
                    Aparent = newArticle;
                    point = 0;
                } else if (sCurrentLine.matches("^[0-9]{1,2}\\. [\\s\\p{L}]+$") && !sCurrentLine.endsWith(",") && !sCurrentLine.endsWith(".")) {
                    Tree newPoint = new Tree("Punkt " + Integer.toString((++point)), sCurrentLine + " ");
                    Aparent.addChild(newPoint);
                } else if (sCurrentLine.matches("^[0-9]{1,2}\\. [\\s\\p{L}]+$")) {
                    Tree newPoint = new Tree("Punkt " + Integer.toString((++point)), sCurrentLine + "\n");
                    Aparent.addChild(newPoint);
                } else if (sCurrentLine.endsWith("-")) {
                    sCurrentLine = sCurrentLine.substring(0, sCurrentLine.length() - 1);
                    Tree newPoint = new Tree( sCurrentLine);
                    Aparent.addChild(newPoint);
                } else if (!sCurrentLine.endsWith(",") && !sCurrentLine.endsWith(".")) {
                    sCurrentLine += " ";
                    Tree newPoint = new Tree(sCurrentLine);
                    Aparent.addChild(newPoint);
                } else {
                    Tree newPoint = new Tree(sCurrentLine + "\n");
                    Aparent.addChild(newPoint);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        OptionsParser optionsParser = new OptionsParser(mode, element, root);
        optionsParser.printOutput();
    }
}
