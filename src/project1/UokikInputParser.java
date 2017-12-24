package project1;

import java.util.ArrayList;


public class UokikInputParser extends AbstractParser {
    public UokikInputParser(ArrayList<String> fileArray) {
        super(fileArray);
    }

    public void addToTree(String data) {
        if (data.matches("^DZIAŁ (.|\\n)*$")) {
            addNode(data, 1);
        } else if (data.matches("^Rozdział (.|\\n)*$")) {
            addNode(data, 2);
        } else if (data.matches("^Art. [0-9]*(.|\\n)*$")) {
            addNode(data, 3);
        } else if (data.matches("^\\d+[a-z]?\\. (.|\\n)*$")) {
            addNode(data, 4);
        } else if (data.matches("^\\d+[a-z]?\\) (.|\\n)*$")) {
            addNode(data, 5);
        } else if (data.matches("^[a-z]{1,2}\\) (.|\\n)*$")) {
            addNode(data, 6);
        } else
            addNode(data, 7);
    }

    public Tree parseInputFile() {
        this.root = new Tree("ROOT", "Zawartosc pliku:", 0);
        nodeStack.push(root);
        stringArray.add("");
        stringArray.add("");
        stringArray.add("");
        stringArray.add("");
        stringArray.add("");
        stringArray.add("");
        stringArray.add("");
        stringArray.add("");

        for (String newLine : fileArray) {
            addToTree(newLine);
        }

        return root;
    }
}
