package project1;

import java.util.ArrayList;


public class KonstytucjaInputParser extends AbstractParser {
    public KonstytucjaInputParser(ArrayList<String> fileArray) {
        super(fileArray);
    }

    public void addToTree(String data) {
        if (data.matches("^Rozdział \\D*$")) {
            addNode(data, 1);
        } else if (data.matches("^[^a-z]*$")) {
            addNode(data, 2);
            section++;
        } else if (data.matches("^Art. [0-9]*(.|\\n)*$")) {
            addNode(data, 3);
        } else if (data.matches("^\\d*\\. (.|\\n)*$")) {
            addNode(data, 4);
        } else
            addNode(data, 5);
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
