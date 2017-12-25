package project1;

public class OptionsParser {
    private String mode, element, range1 = null, range2 = null;
    private Tree root;

    public OptionsParser(String mode, String element, Tree root) {
        this.mode = mode;
        this.element = element;
        this.root = root;
    }

    public OptionsParser(String mode, String range1, String range2, Tree root) {
        this.mode = mode;
        this.range1 = range1;
        this.range2 = range2;
        this.root = root;
    }

    public void printOutput() {
        if (mode.equals("S")) {
            if (element != null && element.equals("ALL")) {
                root.printPreorderList(root);
            } else if (range1 == null && range2 == null) {
                root.printNodeList(root, element);
            } else if (range1 != null && range2 != null && root.findNode(root, range1) != null &&  root.findNode(root, range2) != null) {
                root.printArticlesPreorderList(root, root, range1, range2);
            } else {
                System.out.println("Błędne dane!");
            }
        } else if (mode.equals("T")) {
            if (element != null && element.equals("ALL")) {
                root.printPreorder(root);
            } else if (range1 == null && range2 == null) {
                root.printNode(root, element);
            } else if (range1 != null && range2 != null && root.findNode(root, range1) != null &&  root.findNode(root, range2) != null) {
                root.printArticlesPreorder(root, root, range1, range2);
            } else {
                System.out.println("Błędne dane!");
            }
        } else
            System.out.println("Podano błędny tryb działania!");
    }
}
