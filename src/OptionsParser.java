public class OptionsParser {
    private String mode, element;
    private Tree root;

    public OptionsParser(String mode, String element, Tree root) {
        this.mode = mode;
        this.element = element;
        this.root = root;
    }

    public void printOutput() {
        if(mode.equals("S")){
            root.printNodeList(root, element);
        } else if(mode.equals("T")){
            root.printNode(root, element);
        } else
            System.out.println("Podano błędny tryb działania!");
    }
}
