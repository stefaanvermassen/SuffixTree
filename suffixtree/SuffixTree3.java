package suffixtree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class SuffixTree3/* implements IntervalSearcher */{

    private Node3 root;
    private List<Short> sequence;
    private ActivePoint activePoint;
    private int end = 0;
    //Wanneer we meer dan 1 node maken in de huidige stap, moeten we de vorige en huidige node verbinden met suffix link
    private int stepInserts = 0;
    private Node3 last;
    private int endOfSuffix;
    private int remainder = 1;
    private int totalInserts;
    //Zoekmethodes
    private Node3 foundnode;
    private int counter;
    private HashSet<Integer> locations = new HashSet<Integer>();

    //@Override
    public void construct(List<Short> theSequence) {
        sequence = theSequence;
        sequence.add(Short.MAX_VALUE);
        root = new Node3(null, this);
        activePoint = new ActivePoint(root, null, 0, this);
        //Overlopen van de meegegeven sequence
        for (int i = 0; i < sequence.size(); i++) {
            System.out.println("Nieuwe stap: "+i);
            System.out.println("Remainder:"+remainder);
            endOfSuffix++;
            stepInserts = 0;
            insert();
            end++;
            System.out.println("Remainder:"+remainder);
            System.out.println(this.getActivePoint().getActiveLength());
            System.out.println(this.toString());
        }
    }

    public void insert() {
        if (activePoint.getActiveEdge() == null) {
            System.out.println("AP op NODE");
            //Het AP ligt op een Node
            Node3 node = activePoint.getActiveNode();
            node.insertAtNode(activePoint);
        } else {
            //Het AP ligt op een Edge
            System.out.println("AP op EDGE");
            Edge edge = activePoint.getActiveEdge();
            edge.insertAtEdge(activePoint);
        }
    }

    public int getEnd() {
        return end;
    }
    
    /*Hoeveel inserts we al gedaan hebben in de huidige stap
     Deze methode wordt opgeroepen na het invoegen van een nieuwe boog
     */
    public void increaseInserts() {
        stepInserts++;
    }
    /*
     * Het aantal inserts wordt opgevraagd bij het invoegen van een nieuwe boog
     * Zo kan er gecontroleerd worden of er een suffix link moet gemaakt worden
     */
    public int getInserts() {
        return stepInserts;
    }
    
    //Nodig voor het instellen van de suffix
    public Node3 getLast() {
        return last;
    }

    public void setLast(Node3 node) {
        last = node;
    }
    
    //Originele sequence
    public List<Short> getSequence() {
        return sequence;
    }
    
    //i
    public int getEndOfSuffix() {
        return endOfSuffix;
    }
    
    //Remainder: hoeveel nieuwe suffixen dat we nog moeten toevoegen
    public void increaseRemainder() {
        remainder++;
    }
    
    //Geeft terug of de boom volledig is gemaakt
    public boolean isFinished() {
        return endOfSuffix > sequence.size();
    }
    
    //Remainder kan niet minder dan 1 worden
    public void decreaseRemainder() {
        if (remainder != 1) {
            remainder--;
        }
    }

    public int getRemainder() {
        return remainder;
    }
    
    //Nodig voor de indices in de bladeren, de index is gelijk aan het aantal inserts tot dan toe
    public void increaseTotalInserts() {
        totalInserts++;
    }

    public int getTotalInserts() {
        return totalInserts;
    }

   // @Override
    public boolean contains(List<Short> query) {
        int size = query.size();
        return recursiveContains(query, root, 0, size);
    }

    public boolean recursiveContains(List<Short> query, Node3 node, int index, int size) {

        if (index == size) {
            foundnode = node;
            return true;
        }
        int found = 0;
        int temp = index;
        if (node.getEdges().containsKey(query.get(index))) {
            Edge edge = node.getEdges().get(query.get(index));
            for (int i = edge.getStartOfEdgeList(); i < edge.getEndOfEdgeList(); i++) {
                if (sequence.get(i).equals(query.get(index))) {
                    index++;
                    found = 1;
                } else {
                    found = 0;
                    index = temp;
                    break;
                }
                if (index == size) {
                    foundnode = edge.getEndNode();
                    //nodig voor locate als gevonden node een blad is
                    if (foundnode == null) {
                        locations.add(edge.getIndexFromEndNode());
                    }
                    return true;
                }
            }
            if (found == 1) {
                if (recursiveContains(query, edge.getEndNode(), index, size)) {
                    return true;
                }
                return false;
            }

        }
        return false;
    }

   // @Override
    public int count(List<Short> query) {
        if (query.isEmpty()) {
            return sequence.size();
        }
        if (!contains(query)) {
            return 0;
        } else {
            counter = 0;
            if (foundnode == null) {
                return 1;
            } else {
                findNumberOfLeafs(foundnode);
                return counter;
            }
        }
    }

    public void findNumberOfLeafs(Node3 node) {
        for (Edge edge : node.getEdges().values()) {
            if (edge.getEndNode() == null) {
                counter++;
                locations.add(edge.getIndexFromEndNode());
            } else {
                findNumberOfLeafs(edge.getEndNode());
            }
        }

    }

    //@Override
    public Set<Integer> locate(List<Short> query) {
        locations.clear();
        if (query.isEmpty()) {
            for (int z = 0; z < sequence.size(); z++) {
                locations.add(z);
            }
            return locations;
        } else {
            count(query);
            return locations;
        }
    }

  //  @Override
    public Node3 getRoot() {
        return root;
    }

    public ActivePoint getActivePoint() {
        return activePoint;
    }
    
    public String toString() {
		return Utils.printTreeForGraphViz(this, true);
	}
}
