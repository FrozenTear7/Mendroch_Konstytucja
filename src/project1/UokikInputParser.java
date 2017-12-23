package project1;

import java.util.ArrayList;

public class UokikInputParser {
    private int chapter = 0, subChapter = 0, article = 0, point = 0, subPoint = 0, subSubPoint = 0;
    private ArrayList<String> fileArray;
    private Tree root, currentChapter = null, currentSubChapter = null, currentArticle = null, currentPoint = null, currentSubPoint = null;

    public UokikInputParser(ArrayList<String> fileArray) {
        this.fileArray = fileArray;
    }

    public void addToTree(String data) {
        if (data.matches("^DZIAŁ \\D*$")) {
            Tree newChild = new Tree(("Dział " + Integer.toString((++chapter))), data);
            root.addChild(newChild);
            currentChapter = newChild;
            currentSubChapter = null;
        } else if (data.matches("Rozdział \\D*$")) {
            Tree newChild = new Tree("Rozdział " + Integer.toString((++subChapter)), data);
            currentChapter.addChild(newChild);
            currentSubChapter = newChild;
        } else if (data.matches("^Art. [0-9]*(.|\\n)*$")) {
            Tree newChild = new Tree("Artykuł " + Integer.toString((++article)), data);
            if (currentSubChapter != null) {
                currentSubChapter.addChild(newChild);
            } else {
                currentChapter.addChild(newChild);
            }
            currentArticle = newChild;
        } else if (data.matches("^\\d*. \\D*$")) {
            Tree newChild;
            if (currentArticle != null) {
                newChild = new Tree("Artykuł " + Integer.toString((++article)) + " Punkt " + Integer.toString((++point)), data);
                currentArticle.addChild(newChild);
            } else if (currentSubChapter != null) {
                newChild = new Tree("Rozdział " + Integer.toString((++subChapter)) + " Punkt " + Integer.toString((++point)), data);
                currentSubChapter.addChild(newChild);
            } else {
                newChild = new Tree("Dział " + Integer.toString((++chapter)) + " Punkt " + Integer.toString((++point)), data);
                currentChapter.addChild(newChild);
            }
            currentPoint = newChild;
        } else if (data.matches("^\\d*\\) \\D*$")) {
            Tree newChild;
            if (currentPoint != null) {
                newChild = new Tree("Artykuł " + Integer.toString((++article)) + " Punkt " + Integer.toString((++point)) + " Podpunkt " + Integer.toString((++subPoint)), data);
                currentArticle.addChild(newChild);
            } else if (currentArticle != null) {
                newChild = new Tree("Artykuł " + Integer.toString((++article)) + " Podpunkt " + Integer.toString((++subPoint)), data);
                currentArticle.addChild(newChild);
            } else if (currentSubChapter != null) {
                newChild = new Tree("Rozdział " + Integer.toString((++subChapter)) + " Podpunkt " + Integer.toString((++subPoint)), data);
                currentSubChapter.addChild(newChild);
            } else {
                newChild = new Tree("Dział " + Integer.toString((++chapter)) + " Podpunkt " + Integer.toString((++subPoint)), data);
                currentChapter.addChild(newChild);
            }
            currentPoint = newChild;
        } else if (data.matches("^\\d*\\) \\D*$")) {
            Tree newChild;
            if (currentPoint != null) {
                newChild = new Tree("Artykuł " + Integer.toString((++article)) + " Punkt " + Integer.toString((++point)) + " Podpunkt " + Integer.toString((++subPoint)), data);
                currentArticle.addChild(newChild);
            } else if (currentArticle != null) {
                newChild = new Tree("Artykuł " + Integer.toString((++article)) + " Podpunkt " + Integer.toString((++subPoint)), data);
                currentArticle.addChild(newChild);
            } else if (currentSubChapter != null) {
                newChild = new Tree("Rozdział " + Integer.toString((++subChapter)) + " Podpunkt " + Integer.toString((++subPoint)), data);
                currentSubChapter.addChild(newChild);
            } else {
                newChild = new Tree("Dział " + Integer.toString((++chapter)) + " Podpunkt " + Integer.toString((++subPoint)), data);
                currentChapter.addChild(newChild);
            }
            currentPoint = newChild;
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
