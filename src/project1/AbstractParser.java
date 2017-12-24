package project1;

import java.util.ArrayList;
import java.util.Stack;

import static project1.Tree.getHierarchy;

public abstract class AbstractParser {
    protected int section = 1;
    protected ArrayList<String> fileArray;
    protected ArrayList<String> stringArray = new ArrayList<>();
    protected Tree root;
    protected Stack<Tree> nodeStack = new Stack<>();

    public AbstractParser(ArrayList<String> fileArray) {
        this.fileArray = fileArray;
    }

    protected String getNodeName(String data) {
        String result = "";

        if (data.matches("^DZIAŁ \\D*$")) {
            if (data.matches("^DZIAŁ \\D{1}$"))
                result = data.substring(0, 7);
            else if (data.matches("^DZIAŁ \\D{2}$"))
                result = data.substring(0, 8);
            else if (data.matches("^DZIAŁ \\D{3}$"))
                result = data.substring(0, 9);
            else if (data.matches("^DZIAŁ \\D{4}$"))
                result = data.substring(0, 10);
        } else if (data.matches("^Rozdział \\D*$")) {
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
        } else if (data.matches("^\\d*[.)]{1} (.|\\n)*$")) {
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

    protected void addNode(String data, int hierarchy) {
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
            while (hierarchy != getHierarchy(nodeStack.peek()) + 1) {
                nodeStack.pop();
            }
            newNode = new Tree(constructKey(data, hierarchy), data, hierarchy);
            stringArray.set(hierarchy, getNodeName(data));
            nodeStack.peek().addChild(newNode);
            nodeStack.push(newNode);
        }
    }
}
