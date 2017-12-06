import java.util.ArrayList;

public class Tree {
    private String key;
    private String data;
    private Tree parent;
    private ArrayList<Tree> children;

    public Tree(String key, String data) {
        this.key = key;
        this.data = data;
        this.children = new ArrayList<>();
    }

    public Tree(String data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public void addChild(Tree child) {
        child.parent = this;
        this.children.add(child);
    }

    public void printPreorder(Tree parent) {
        if(parent.data != null) {
            if(!key.isEmpty())
                System.out.println(parent.key + " " + parent.data);
            else
                System.out.println(parent.data);

            if(parent.children.size() != 0)
                for(Tree child : parent.children) {
                    printPreorder(child);
                }
        }
    }
}