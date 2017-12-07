package project1;

public class OptionsParser {
    private String mode, element;
    private int range1, range2;
    private Tree root;

    public OptionsParser (String mode, String element, int range1, int range2, Tree root) {
        this.mode = mode;
        this.element = element;
        this.range1 = range1;
        this.range2 = range2;
        this.root = root;
    }

    public void printOutput() {
        if(mode.equals("S")){
            if(range2 == 0) {
                root.printNodeList(root, element + " " + Integer.toString(range1));
            } else {
                for(; range1 <= range2; range1++) {
                    root.printNodeList(root, element + " " + Integer.toString(range1));
                }
            }
        } else if(mode.equals("T")){
            if(range2 == 0) {
                root.printNode(root, element + " " + Integer.toString(range1));
            } else {
                for(; range1 <= range2; range1++) {
                    root.printNode(root, element + " " + Integer.toString(range1));
                }
            }
        } else
            System.out.println("Podano błędny tryb działania!");
    }
}
