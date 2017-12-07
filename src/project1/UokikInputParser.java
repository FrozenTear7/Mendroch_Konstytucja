package project1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class UokikInputParser extends AbstractInputParser {
    private int section = 0, article = 0, subArticle = 0, point = 0, ppoint = 0, pppoint = 0;
    private Tree Dparent = null, Aparent = null, Pparent = null, PPparent = null, PPPparent = null, lastParent = null;

    public UokikInputParser(String filePath, String mode, String element, int range1, int range2) {
        super(filePath, mode, element, range1, range2);
    }

    public void parseInputFile() {
        root = new Tree("ROOT", "Zawartosc pliku:");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "Cp1250"))) {
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.matches("^DZIAŁ \\S*$")) {
                    Tree newSection = new Tree("DZIAŁ " + Integer.toString((++section)), sCurrentLine + "\n");
                    Dparent = newSection;
                    root.addChild(newSection);
                } else if (Dparent == null || sCurrentLine.matches(".*\\d{4}-\\d{2}-\\d{2}.*") || sCurrentLine.matches(".*Kancelaria Sejmu.*"))
                    continue;
                else if (sCurrentLine.matches("^Art. [0-9]*. \\w*[,.]+$")) {
                    Tree newArticle = new Tree("Artykuł " + Integer.toString((++article)), sCurrentLine + " ");
                    Aparent = newArticle;
                    Dparent.addChild(newArticle);
                } else if (sCurrentLine.matches("^Art. [0-9]*. \\w*+-$")) {
                    Tree newArticle = new Tree("Artykuł " + Integer.toString((++article)), sCurrentLine.substring(0, sCurrentLine.length() - 1));
                    Aparent = newArticle;
                    Dparent.addChild(newArticle);
                } else if (sCurrentLine.matches("^Art. [0-9]*. \\w*+$") && !sCurrentLine.endsWith(",") && !sCurrentLine.endsWith(".")) {
                    Tree newArticle = new Tree("Artykuł " + Integer.toString((++article)), sCurrentLine + "\n");
                    Aparent = newArticle;
                    Dparent.addChild(newArticle);
                } else if (sCurrentLine.matches("^Art. [0-9]*. [0-9]*. \\w*[,.]+$")) {
                    Tree newArticle = new Tree("Artykuł " + Integer.toString((++article)), sCurrentLine + " ");
                    Aparent = newArticle;
                    Dparent.addChild(newArticle);
                } else if (sCurrentLine.matches("^Art. [0-9]*. [0-9]*. \\w*+-$")) {
                    Tree newArticle = new Tree("Artykuł " + Integer.toString((++article)), sCurrentLine.substring(0, sCurrentLine.length() - 1));
                    Aparent = newArticle;
                    Dparent.addChild(newArticle);
                } else if (sCurrentLine.matches("^Art. [0-9]*. [0-9]*. \\w*$") && !sCurrentLine.endsWith(",") && !sCurrentLine.endsWith(".")) {
                    Tree newArticle = new Tree("Artykuł " + Integer.toString((++article)), sCurrentLine + "\n");
                    Aparent = newArticle;
                    Dparent.addChild(newArticle);
                } else if (sCurrentLine.endsWith("-")) {
                    sCurrentLine = sCurrentLine.substring(0, sCurrentLine.length() - 1);
                    Tree newPoint = new Tree(sCurrentLine);
                    if (PPparent == null)
                        Pparent.addChild(newPoint);
                    else
                        PPparent.addChild(newPoint);
                } else if (!sCurrentLine.endsWith(",") && !sCurrentLine.endsWith(".")) {
                    sCurrentLine += " ";
                    Tree newPoint = new Tree(sCurrentLine);
                    if (PPparent == null)
                        Pparent.addChild(newPoint);
                    else
                        PPparent.addChild(newPoint);
                } else {
                    Tree newPoint = new Tree(sCurrentLine + "\n");
                    if (PPparent == null)
                        Pparent.addChild(newPoint);
                    else
                        PPparent.addChild(newPoint);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        OptionsParser optionsParser = new OptionsParser(mode, element, range1, range2, root);
        optionsParser.printOutput();
    }
}
