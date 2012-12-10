package suffixtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class Node3 implements Iterable<Edge>  {
    
    //HashMap met als de key het eerste element op de boog(dit is uniek!) en de boog zelf
    private HashMap<Short, Edge> edgesFromNode = new HashMap<Short, Edge>();
    //Boog die in de node aankomt
    private Edge incomingEdge;
    private SuffixTree3 tree;
    //Indien er een suffixlink bestaat met deze Node, is dit veld niet langer null
    private Node3 link;

    public Node3(Edge incomingEdge, SuffixTree3 tree) {
        this.incomingEdge = incomingEdge;
        this.tree = tree;
    }

    public void insertAtNode(ActivePoint activePoint) {
        Short item = tree.getSequence().get(tree.getEndOfSuffix() - 1);
         /* Geval 1: Er moet een nieuwe boog worden aangemaakt uit de Node aangezien
         * er geen boog begint met het eerste karakter van de huidige suffix
         */
        if (!edgesFromNode.containsKey(item)) {
            //Maak een nieuwe boog aan
            Edge newEdge = new Edge(tree.getEndOfSuffix() - 1, tree);
            newEdge.setIndexFromEndNode(tree.getTotalInserts());
            tree.increaseTotalInserts();
            //Steek de eerste Short als key, en de boog zelf als value in de HashMap edgesFromNode
            edgesFromNode.put(tree.getSequence().get(tree.getEndOfSuffix() - 1), newEdge);
            //Verhoog de startOfSuffix na een succesvolle insert :-)
            activePoint.update();
            if (tree.getInserts() > 0 && !this.equals(tree.getRoot())) {
                tree.getLast().setSuffixLink(this);
            }
            if (tree.getRemainder()==1 || tree.isFinished()) {
                System.out.println("Tis gedaan");
                tree.decreaseRemainder();                
            } else {
                System.out.println("Nog een keer inserten!");
                tree.decreaseRemainder();
                tree.insert();
            }
        } else {
            /* Geval 2: Als de suffix dat we willen toevoegen al in de boom zit, wordt de boom niet veranderd,
             * enkel het AP
             */
            /*
             * Wanneer we meer dan 1 Node maken in de huidige stap, moeten we de vorige en deze
             * verbinden met een suffix link
             */
            if (tree.getInserts() > 0 && activePoint.getActiveNode() != tree.getRoot()) {
                tree.getLast().setSuffixLink(activePoint.getActiveNode());
                tree.setLast(activePoint.getActiveNode());
            }
            activePoint.setActiveEdge(edgesFromNode.get(item));
            activePoint.increaseActiveLength();
            tree.increaseRemainder();
        }
    }

    public void insertEdgeInMap(Edge edge) {
        edgesFromNode.put(tree.getSequence().get(edge.getStartOfEdgeList()), edge);
    }

    public Node3 getSuffixLink() {
        return link;
    }

    void setSuffixLink(Node3 node) {
        link = node;
    }
    
    public HashMap<Short,Edge> getEdges(){
        return edgesFromNode;
    }

    //@Override
    public boolean isRoot() {
        return incomingEdge == null;
    }

    //@Override
    public boolean isLeaf() {
        return edgesFromNode.values().isEmpty();
    }

    //@Override
    public int getIndex() {
        return incomingEdge.getIndexFromEndNode();
    }

   // @Override
    public Collection<Node3> getChildren() {
        ArrayList<Node3> children = new ArrayList<Node3>();
        for (Edge e : edgesFromNode.values()) {
            children.add(e.getEndNode());
        }
        return children;
    }

    public Edge getIncomingEdge() {
        return incomingEdge;
    }

    @Override
    public Iterator<Edge> iterator() {
        return edgesFromNode.values().iterator();
    }
}
