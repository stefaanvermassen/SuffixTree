package suffixtree;

import java.util.ArrayList;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class Node1 extends AbstractTreeNode{
    private Short edgeLabel;
    private int depth;
    
    public Node1(Node1 parent, Short edgeLabel, int depth, int index){
        super(parent, index);
        this.edgeLabel = edgeLabel;
        this.depth = depth;
        this.children = new ArrayList<AbstractTreeNode>();
    }
    
    //Constructor for root
    public Node1(){
        this.children = new ArrayList<AbstractTreeNode>();
        edgeLabel = null;
        depth = 0;
    }

    public Short getEdge(){
        return edgeLabel;
    }
    
    public int getDepth(){
        return depth;
    }
}
