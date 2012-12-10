package suffixtree;

import java.util.Collection;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public abstract class AbstractTreeNode  implements TreeNode{
    
    private AbstractTreeNode parent;
    protected Collection<AbstractTreeNode> children = null;
    private int index;

    public AbstractTreeNode(AbstractTreeNode parent, int index){
        this.parent = parent;
        this.index = index;
    }
    
    //Constructor for root
    public AbstractTreeNode(){
        parent = null;
        index = -1;
    }
    
    @Override
    public boolean isRoot() {
        return this.parent == null;
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Collection<AbstractTreeNode> getChildren() {
        return children;
    }
    
    public void addChild(AbstractTreeNode child){
        children.add(child);
    }
    
}
