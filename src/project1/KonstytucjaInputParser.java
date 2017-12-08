package project1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class KonstytucjaInputParser extends AbstractInputParser {
    private int chapter = 0, section = 0, article = 0, point = 0;
    private Tree Rparent = null, Sparent = null, Aparent = null, Pparent = null;

    public KonstytucjaInputParser(String filePath, String mode, String element, int range1, int range2) {
        super(filePath, mode, element, range1, range2);
    }

    public void parseInputFile() {
        root = new Tree("ROOT", "Zawartosc pliku:");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1250"))) {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.matches("^Rozdział \\w*$")) {
                    Tree newChapter = new Tree("Rozdział " + Integer.toString((++chapter)), sCurrentLine + "\n");
                    root.addChild(newChapter);
                    Rparent = newChapter;
                    section = 0;
                } else if (Rparent == null || sCurrentLine.matches(".*\\d{4}-\\d{2}-\\d{2}.*") || sCurrentLine.matches(".*Kancelaria Sejmu.*"))
                    continue;
                else if (sCurrentLine.matches("^[^a-z]*$")) {
                    Tree newSection = new Tree("Sekcja " + Integer.toString((++section)), sCurrentLine + "\n");
                    Rparent.addChild(newSection);
                    Sparent = newSection;
                } else if (sCurrentLine.matches("^Art. [0-9]*.$")) {
                    Tree newArticle = new Tree("Artykuł " + Integer.toString((++article)), sCurrentLine + "\n");
                    Sparent.addChild(newArticle);
                    Aparent = newArticle;
                    Pparent = null;
                    point = 0;
                } else if (sCurrentLine.matches("^[0-9]{1,2}\\. [\\s\\p{L},.]+$")) {
                    Tree newPoint = new Tree("Artykuł " + article + " Punkt " + Integer.toString((++point)), sCurrentLine + "\n");
                    Pparent = newPoint;
                    Aparent.addChild(newPoint);
                } else if (sCurrentLine.matches("^[0-9]{1,2}\\. [\\s\\p{L}]+-$")) {
                    Tree newPoint = new Tree("Artykuł " + article + " Punkt " + Integer.toString((++point)), sCurrentLine.substring(0, sCurrentLine.length() - 1));
                    Pparent = newPoint;
                    Aparent.addChild(newPoint);
                } else if (sCurrentLine.matches("^[0-9]{1,2}\\. [\\s\\p{L}]+$") && !sCurrentLine.endsWith(",") && !sCurrentLine.endsWith(".")) {
                    Tree newPoint = new Tree("Artykuł " + article + " Punkt " + Integer.toString((++point)), sCurrentLine + " ");
                    Pparent = newPoint;
                    Aparent.addChild(newPoint);
                } else if (sCurrentLine.endsWith("-")) {
                    sCurrentLine = sCurrentLine.substring(0, sCurrentLine.length() - 1);
                    Tree newPoint = new Tree(sCurrentLine);
                    if (Pparent == null)
                        Aparent.addChild(newPoint);
                    else
                        Pparent.addChild(newPoint);
                } else if (!sCurrentLine.endsWith(",") && !sCurrentLine.endsWith(".")) {
                    sCurrentLine += " ";
                    Tree newPoint = new Tree(sCurrentLine);
                    if (Pparent == null)
                        Aparent.addChild(newPoint);
                    else
                        Pparent.addChild(newPoint);
                } else {
                    Tree newPoint = new Tree(sCurrentLine + "\n");
                    if (Pparent == null)
                        Aparent.addChild(newPoint);
                    else
                        Pparent.addChild(newPoint);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        OptionsParser optionsParser = new OptionsParser(mode, element, range1, range2, root);
        optionsParser.printOutput();
    }
}
