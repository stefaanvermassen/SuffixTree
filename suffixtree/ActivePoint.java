package suffixtree;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class ActivePoint {

    private Node3 activeNode;
    private Edge activeEdge;
    private int activeLength;
    private Node3 root;
    private SuffixTree3 tree;

    ActivePoint(Node3 activeNode, Edge activeEdge, int activeLength, SuffixTree3 tree) {
        this.activeNode = activeNode;
        this.activeEdge = activeEdge;
        this.activeLength = activeLength;
        root = activeNode;
        this.tree = tree;
    }

    public void setActiveEdge(Edge edge) {
        activeEdge = edge;
    }
    
    //Wordt opgeroepen als de boom niet veranderd wordt omdat de volgende Short op de boog al overeenkomt met het toe te voegen
    public void increaseActiveLength() {
        activeLength++;
        //Als het AP op het einde van een boog staat, zetten we het AP naar de Node aan deze boog
        if (activeEdge != null && activeEdge.getSize() == activeLength && activeEdge.getEndNode() != null) {
            setToEndNode();
        }
    }
    
    /*Wordt opgeroepen bij het updaten van het AP
    After an insertion from root:
    active_node remains root
    active_edge is set to the first character of the new suffix we need to insert, i.e. b
    active_length is reduced by 1
     
     */
    public void decreaseActiveLength() {
        if (activeLength > 0) {
            activeLength--;
        }
        if (activeEdge != null && activeEdge.getSize() == activeLength && activeEdge.getEndNode() != null) {
            setToEndNode();
        }
    }

    public Node3 getActiveNode() {
        return activeNode;
    }


    public Edge getActiveEdge() {
        return activeEdge;
    }

    public int getActiveLength() {
        return activeLength;
    }

    public void update() {
        if (activeNode == root && tree.getRemainder() == 1) {
          //  System.out.println("Eerste if");
            activeEdge = null;
            activeLength = 0;
        } else if (activeNode == root) {
            System.out.println("2de if");
            System.out.println(tree.getSequence().get(tree.getEnd()-tree.getRemainder()+2));
            //Eerste karakter van de nieuwe in te voegen suffix
            activeEdge = root.getEdges().get(tree.getSequence().get(tree.getEnd()-tree.getRemainder()+2));
            System.out.println("remainder"+tree.getRemainder());
            decreaseActiveLength();
            canonize();
            if (activeLength == 0) {
                activeEdge = null;
            }
        } else if (activeNode.getSuffixLink() != null) {
          //  System.out.println("3de if");
            activeNode = activeNode.getSuffixLink();
            if (activeEdge != null) {
                activeEdge = activeNode.getEdges().get(tree.getSequence().get(activeEdge.getStartOfEdgeList()));
            }
            canonize();
            if (activeLength == 0) {
                activeEdge = null;
            }
        } else {
          //  System.out.println("4de if");
            activeNode = root;
            if (activeEdge != null) {
                activeEdge = activeNode.getEdges().get(tree.getSequence().get(activeEdge.getStartOfEdgeList()));
            }
            canonize();
            if (activeLength == 0) {
                activeEdge = null;
            }
        }
    }
    
    /*Wanneer we een suffixlink volgen en de lengte van de boog is kleiner dan activeLength
     http://stackoverflow.com/questions/10097323/ukkonen-suffix-tree-procedure-canonize-unclear
     */
    public void canonize() {
        while (activeEdge != null && activeEdge.getSize() < activeLength) {
            activeLength = activeLength - activeEdge.getSize();
            activeNode = activeEdge.getEndNode();
            activeEdge = activeNode.getEdges().get(tree.getSequence().get(tree.getEndOfSuffix() - (activeLength + 1)));
        }
        if (activeEdge != null && activeEdge.getSize() == activeLength && activeEdge.getEndNode() != null) {
            setToEndNode();
        }
    }

    private void setToEndNode() {
        activeNode = activeEdge.getEndNode();
        activeEdge = null;
        activeLength = 0;
    }
}
