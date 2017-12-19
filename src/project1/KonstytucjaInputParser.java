package project1;

import java.util.ArrayList;

public class KonstytucjaInputParser {
    private int chapter = 0, section = 0, article = 0, point = 0;
    private ArrayList<String> fileArray;
    private Tree root, currentChapter, currentSection, currentArticle;

    public KonstytucjaInputParser(ArrayList<String> fileArray) {
        this.fileArray = fileArray;
    }

    public void addToTree(String data) {
        if (data.matches("^Rozdział \\D*$")) {
            Tree newChild = new Tree(("Rozdział " + Integer.toString((++chapter))), data);
            root.addChild(newChild);
            currentChapter = newChild;
        } else if (data.matches("^[^a-z]*$")) {
            Tree newChild = new Tree("Sekcja " + Integer.toString((++section)), data);
            currentChapter.addChild(newChild);
            currentSection = newChild;
        } else if (data.matches("^Art. [0-9]*(.|\\n)*$")) {
            Tree newChild = new Tree("Artykuł " + Integer.toString((++article)), data);
            currentSection.addChild(newChild);
            currentArticle = newChild;
        } else {
            Tree newChild = new Tree("Artykuł " + article + " Punkt " + Integer.toString((++point)), data);
            currentArticle.addChild(newChild);
        }
    }

    public Tree parseInputFile() {
        this.root = new Tree("ROOT", "Zawartosc pliku:");

        for (String newLine : fileArray) {
            addToTree(newLine);
        }

        return root;
    }
}
