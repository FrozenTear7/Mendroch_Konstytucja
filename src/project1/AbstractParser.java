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

    private String extractArticleKey (String data) {
        String result = "";
        int index = 0, dots = 0;
        boolean end = false;

        while(!end) {
            if(data.charAt(index) == '.') {
                dots++;
            }

            if(dots == 2) {
                end = true;
                result = data.substring(0, index+1);
            }
            else
                index++;
        }

        return result;
    }

    private String extractKey (String data) {
        String result = "";
        int index = 0;
        boolean end = false;

        while(!end) {
            if(data.charAt(index) == ')' || data.charAt(index) == '.') {
                end = true;
                result = data.substring(0, index+1);
            }
            else
                index++;
        }

        return result;
    }

    protected String getNodeName(String data) {
        String result = "";

        if (data.matches("^DZIAŁ \\D*(.|\\n)*$")) {
            if (data.matches("^DZIAŁ \\b\\D{1}\\b(.|\\n)*$"))
                result = data.substring(0, 7);
            else if (data.matches("^DZIAŁ \\b\\D{2}\\b(.|\\n)*$"))
                result = data.substring(0, 8);
            else if (data.matches("^DZIAŁ \\b\\D{3}\\b(.|\\n)*$"))
                result = data.substring(0, 9);
            else if (data.matches("^DZIAŁ \\b\\D{4}\\b(.|\\n)*$"))
                result = data.substring(0, 10);
        } else if (data.matches("^Rozdział \\S*(.|\\n)*$")) {
            if (data.matches("^Rozdział \\S{1}(.|\\n)*$"))
                result = data.substring(0, 10);
            else if (data.matches("^Rozdział \\S{2}(.|\\n)*$"))
                result = data.substring(0, 11);
            else if (data.matches("^Rozdział \\S{3}(.|\\n)*$"))
                result = data.substring(0, 12);
            else if (data.matches("^Rozdział \\S{4}(.|\\n)*$"))
                result = data.substring(0, 13);
        } else if (data.matches("^[^a-z]*$")) {
            result = "Sekcja " + section;
        } else if (data.matches("^Art. [0-9]*(.|\\n)*$")) {
            result = extractArticleKey(data);
        } else if (data.matches("^\\d+[a-z]?[.)]{1} (.|\\n)*$")) {
            result = extractKey(data);
        } else if (data.matches("^[a-z]*\\) (.|\\n)*$")) {
                result = data.substring(0, 2);
        }

        return result;
    }

    private String constructKey(String data, int hierarchy) {
        String key = "";

        if (hierarchy == 1 || hierarchy == 2 || hierarchy == 3) {
            key = getNodeName(data);
            stringArray.set(4, "");
            stringArray.set(5, "");
            stringArray.set(6, "");
        }
        else if (hierarchy == 4) {
            key = stringArray.get(3) + getNodeName(data);
        } else if (hierarchy == 5) {
            key = stringArray.get(3) + stringArray.get(4) + getNodeName(data);
        } else if (hierarchy == 6) {
            key = stringArray.get(3) + stringArray.get(4) + stringArray.get(5) + getNodeName(data);
        } else if (hierarchy == 7) {
            key = stringArray.get(3) + stringArray.get(4) + stringArray.get(5) + stringArray.get(6) + getNodeName(data);
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
            while (hierarchy < getHierarchy(nodeStack.peek())+1) {
                nodeStack.pop();
            }
            newNode = new Tree(constructKey(data, hierarchy), data, hierarchy);
            stringArray.set(hierarchy, getNodeName(data));
            nodeStack.peek().addChild(newNode);
            nodeStack.push(newNode);
        }
    }
}
