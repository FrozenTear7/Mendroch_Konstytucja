package project1;

abstract class AbstractInputParser {
    protected String filePath, mode, element;
    protected int range1, range2;
    protected Tree root;

    public AbstractInputParser(String filePath, String mode, String element, int range1, int range2) {
        this.filePath = filePath;
        this.mode = mode;
        this.element = element;
        this.range1 = range1;
        this.range2 = range2;
    }
}
