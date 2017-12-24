/*
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
            Tree newChild = new Tree("Dział " + ++chapter, data);
            root.addChild(newChild);
            currentChapter = newChild;
            currentSubChapter = null;
            currentArticle = null;
            currentPoint = null;
            currentSubPoint = null;
        } else if (data.matches("Rozdział \\D*$")) {
            Tree newChild = new Tree("Rozdział " + ++subChapter, data);
            currentChapter.addChild(newChild);
            currentSubChapter = newChild;
        } else if (data.matches("^Art. [0-9]*(.|\\n)*$")) {
            Tree newChild;
            if (currentSubChapter != null) {
                newChild = new Tree("Artykuł " + ++article, data);
                currentSubChapter.addChild(newChild);
            } else {
                newChild = new Tree("Artykuł " + ++article, data);
                currentChapter.addChild(newChild);
            }
            currentArticle = newChild;
            currentPoint = null;
            currentSubPoint = null;
            point = 0; subPoint = 0; subSubPoint = 0;
        } else if (data.matches("^\\d*[a-z]?. \\D*$")) {
            Tree newChild;
            if (currentArticle != null) {
                newChild = new Tree("Artykuł " + article + " Punkt " + ++point, data);
                currentArticle.addChild(newChild);
            } else if (currentSubChapter != null) {
                newChild = new Tree("Rozdział " + subChapter + " Punkt " + ++point, data);
                currentSubChapter.addChild(newChild);
            } else {
                newChild = new Tree("Dział " + chapter + " Punkt " + ++point, data);
                currentChapter.addChild(newChild);
            }
            currentPoint = newChild;
            currentSubPoint = null;
            subPoint = 0; subSubPoint = 0;
        } else if (data.matches("^\\d*[a-z]?\\) .*$")) {
            Tree newChild;
            if (currentPoint != null) {
                newChild = new Tree("Artykuł " + article + " Punkt " + point
                        + " Podpunkt " + ++subPoint, data);
                currentPoint.addChild(newChild);
            } else if (currentArticle != null) {
                newChild = new Tree("Artykuł " + article + " Podpunkt " + ++subPoint, data);
                currentArticle.addChild(newChild);
            } else if (currentSubChapter != null) {
                newChild = new Tree("Rozdział " + subChapter + " Podpunkt " + ++subPoint, data);
                currentSubChapter.addChild(newChild);
            } else {
                newChild = new Tree("Dział " + chapter + " Podpunkt " + ++subPoint, data);
                currentChapter.addChild(newChild);
            }
            currentSubPoint = newChild;
            subSubPoint = 0;
        } else if (data.matches("^[a-z]?\\) .*$")) {
            Tree newChild;
            if (currentPoint != null) {
                newChild = new Tree("Artykuł " + article + " Punkt " + point
                        + " Podpunkt " + subPoint + " Podpodpunkt " + ++subSubPoint, data);
            } else if (currentPoint != null) {
                newChild = new Tree("Artykuł " + article + " Podpunkt " + subPoint
                        + " Podpodpunkt " + subSubPoint, data);
            } else if (currentSubChapter != null) {
                newChild = new Tree("Rozdział " + subChapter + " Podpunkt " + subPoint
                        + " Podpodpunkt " + ++subSubPoint, data);
            } else {
                newChild = new Tree("Dział " + chapter + " Podpunkt " + subPoint
                        + " Podpodpunkt " + ++subSubPoint, data);
            }
            currentSubPoint.addChild(newChild);
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
*/
