package suffixtree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class Node2 extends AbstractTreeNode {
    
    private List<Short> edgeLabels;

    public Node2(Node2 parent, int index, List<Short> edgelabels) {
        super(parent, index);
        this.edgeLabels = edgelabels;
        this.children = new ArrayList<AbstractTreeNode>();
    }
    
    public Node2(){
        edgeLabels = null;
        this.children = new ArrayList<AbstractTreeNode>(0);
    }
    
    public List<Short> getEdge(){
        return edgeLabels;
    }
    
   
}
