package project1;

import java.util.ArrayList;

public class Tree {
    private String key;
    private String data;
    private int hierarchy;
    private Tree parent;
    private ArrayList<Tree> children;
    private boolean allowPrint = false;

    public Tree(String key, String data, int hierarchy) {
        this.key = key;
        this.data = data;
        this.hierarchy = hierarchy;
        this.children = new ArrayList<>();
    }

    public Tree(String data, int hierarchy) {
        this.data = data;
        this.hierarchy = hierarchy;
        this.children = new ArrayList<>();
    }

    public void addChild(Tree child) {
        child.parent = this;
        this.children.add(child);
    }

    public static int getHierarchy(Tree node) {
        return node.hierarchy;
    }

    public static String getData(Tree node) {
        return node.data;
    }

    public Tree findNode(Tree parent, String key) {
        if (parent.key != null) {
            if (parent.key.equals(key))
                return parent;
            else {
                for (Tree child : parent.children) {
                    Tree result = findNode(child, key);
                    if (result != null)
                        return result;
                }
            }
        }

        return null;
    }

    public void printPreorderList(Tree parent) {
        if (parent.key != null) {
            System.out.println(parent.key);
        }

        if (parent.children.size() != 0)
            for (Tree child : parent.children) {
                printPreorderList(child);
            }
    }

    public void printPreorder(Tree parent) {
        if (parent.data != null) {
            System.out.println(parent.data);

            if (parent.children.size() != 0)
                for (Tree child : parent.children) {
                    printPreorder(child);
                }
        }
    }

    public void printArticlesPreorder(Tree parent, Tree root, String range1, String range2) {
        if (parent.data != null) {
            if (parent.key.equals(range1))
                allowPrint = true;
            else if (parent.key.equals(range2)) {
                allowPrint = false;
                printNode(root, parent.key);
                return;
            }

            if (allowPrint && parent.hierarchy == 3)
                printNode(root, parent.key);

            if (parent.children.size() != 0)
                for (Tree child : parent.children) {
                    printArticlesPreorder(child, root, range1, range2);
                }
        }
    }

    public void printArticlesPreorderList(Tree parent, Tree root, String range1, String range2) {
        if (parent.key != null) {
            if (parent.key.equals(range1))
                allowPrint = true;
            else if (parent.key.equals(range2)) {
                printNodeList(root, parent.key);
                allowPrint = false;
                return;
            }

            if (!allowPrint && parent.hierarchy == 3)
                printNode(root, parent.key);
        }

        if (parent.children.size() != 0)
            for (Tree child : parent.children) {
                printArticlesPreorderList(child, root, range1, range2);
            }
    }

    public void printNode(Tree root, String key) {
        printPreorder(findNode(root, key));
    }

    public void printNodeList(Tree root, String key) {
        Tree nodeToPrint = findNode(root, key);
        if (nodeToPrint != null)
            printPreorderList(nodeToPrint);
    }
}