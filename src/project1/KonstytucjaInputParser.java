package project1;

import java.util.ArrayList;
import java.util.Stack;

public class KonstytucjaInputParser {
    private int chapter = 0, section = 0, article = 0, point = 0, range1, range2;
    private String mode, element;
    private Tree root;
    private ArrayList<String> fileArray;
    private Stack<Tree> parents = new Stack<>();

    public KonstytucjaInputParser(ArrayList<String> fileArray, String mode, String element, int range1, int range2) {
        this.fileArray = fileArray;
        this.mode = mode;
        this.element = element;
        this.range1 = range1;
        this.range2 = range2;
    }

    public String parseKey(String data) {
        String newKey;

        if (data.matches("^Rozdział \\w*$"))
            newKey = "Rozdział " + Integer.toString((++chapter));
        else if (data.matches("^[^a-z]*$"))
            newKey = "Sekcja " + Integer.toString((++section));
        else if (data.matches("^Art. [0-9]*.$"))
            newKey = "Artykuł " + Integer.toString((++article));
        else if (data.matches("^[0-9]{1,2}\\. [\\s\\p{L},.]+$"))
            newKey = "Artykuł " + article + " Punkt " + Integer.toString((++point));
        else
            newKey = "";

        return newKey;
    }

    public void addToTree(String data) {
        parents.peek().addChild(new Tree(parseKey(data), data));
    }

    public Tree parseInputFile() {
        root = new Tree("ROOT", "Zawartosc pliku:");
        parents.add(root);

        for (String newLine : fileArray) {
            addToTree(newLine);
        }

        return root;
    }
}
