package project1;

import java.util.ArrayList;
import java.util.Stack;

import static project1.Tree.getHierarchy;

public class KonstytucjaInputParser {
    private int section = 1;
    private ArrayList<String> fileArray;
    private ArrayList<String> stringArray = new ArrayList<>();
    private Tree root;
    private Stack<Tree> nodeStack = new Stack<>();

    public KonstytucjaInputParser(ArrayList<String> fileArray) {
        this.fileArray = fileArray;
    }

    private String getNodeName(String data) {
        String result = "";

        if (data.matches("^Rozdział \\D*$")) {
            if (data.matches("^Rozdział \\D{1}$"))
                result = data.substring(0, 10);
            else if (data.matches("^Rozdział \\D{2}$"))
                result = data.substring(0, 11);
            else if (data.matches("^Rozdział \\D{3}$"))
                result = data.substring(0, 12);
            else if (data.matches("^Rozdział \\D{4}$"))
                result = data.substring(0, 13);
        } else if (data.matches("^[^a-z]*$")) {
            result = "Sekcja " + section;
        } else if (data.matches("^Art. [0-9]*(.|\\n)*$")) {
            if (data.matches("^Art. \\b\\d\\b.(.|\\n)*$"))
                result = data.substring(0, 7);
            else if (data.matches("^Art. \\b\\d{2}\\b.(.|\\n)*$"))
                result = data.substring(0, 8);
            else if (data.matches("^Art. \\b\\d{3}\\b.(.|\\n)*$"))
                result = data.substring(0, 9);
            else if (data.matches("^Art. \\b\\d{4}\\b.(.|\\n)*$"))
                result = data.substring(0, 10);
        } else if (data.matches("^\\d*. (.|\\n)*$")) {
            if (data.matches("^\\b\\d\\b. (.|\\n)*$"))
                result = data.substring(0, 2);
            else if (data.matches("^\\b\\d{2}\\b. (.|\\n)*$"))
                result = data.substring(0, 3);
            else if (data.matches("^\\b\\d{3}\\b. (.|\\n)*$"))
                result = data.substring(0, 4);
            else if (data.matches("^\\b\\d{4}\\b. (.|\\n)*$"))
                result = data.substring(0, 5);
        }

        return result;
    }

    private String constructKey(String data, int hierarchy) {
        String key = "";

        if (hierarchy == 1 || hierarchy == 2 || hierarchy == 3)
            key = getNodeName(data);
        else {
            key = stringArray.get(3) + getNodeName(data);
        }

        return key;
    }

    private void addNode(String data, int hierarchy) {
        Tree newNode;

        if (hierarchy > getHierarchy(nodeStack.peek())) {
            newNode = new Tree(constructKey(data, hierarchy), data, hierarchy);
            stringArray.set(hierarchy, getNodeName(data));
            nodeStack.peek().addChild(newNode);
            nodeStack.push(newNode);
        } else if (hierarchy == getHierarchy(nodeStack.peek())) {
            nodeStack.pop();
            newNode = new Tree(constructKey(data, hierarchy), data, hierarchy);
            stringArray.set(hierarchy, getNodeName(data));
            nodeStack.peek().addChild(newNode);
            nodeStack.push(newNode);
        } else {
            while(hierarchy != getHierarchy(nodeStack.peek())+1) {
                nodeStack.pop();
            }
            newNode = new Tree(constructKey(data, hierarchy), data, hierarchy);
            stringArray.set(hierarchy, getNodeName(data));
            nodeStack.peek().addChild(newNode);
            nodeStack.push(newNode);
        }
    }

    public void addToTree(String data) {
        if (data.matches("^Rozdział \\D*$")) {
            addNode(data, 1);
        } else if (data.matches("^[^a-z]*$")) {
            addNode(data, 2);
            section++;
        } else if (data.matches("^Art. [0-9]*(.|\\n)*$")) {
            addNode(data, 3);
        } else {
            addNode(data, 4);
        }
    }

    public Tree parseInputFile() {
        this.root = new Tree("ROOT", "Zawartosc pliku:", 0);
        nodeStack.push(root);
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
