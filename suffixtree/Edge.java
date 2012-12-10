/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package suffixtree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Edge implements Iterable<Short>{
    /* Houdt de lijst op de edge bij door middel van een start en eindindex van de sequence*/
    private int startOfEdgeList;
    //Als endOfEdgeList niet -1 is, stopt de edge, anders loopt de edge door tot het einde van de sequence
    private int endOfEdgeList = -1;
    private Node3 endNode;
    private SuffixTree3 tree;
    private int indexFromEndNode;

    public Edge(int start, SuffixTree3 tree) {
        this.startOfEdgeList = start;
        this.tree = tree;
    }

    public void insertAtEdge(ActivePoint activePoint) {
        Short current = tree.getSequence().get(tree.getEndOfSuffix() - 1);
        //Als de volgende Short op de boog gelijk is aan de toe te voegen short, AP updaten!
        if (current.equals(tree.getSequence().get(startOfEdgeList + tree.getActivePoint().getActiveLength()))) {
            activePoint.increaseActiveLength();
            tree.increaseRemainder();
        } else {
            //Komt niet overeen, we kunnen dus toevoegen, boog moet gesplitst worden!
            Node3 newNode = new Node3(this, tree);
            Edge oldEdge = new Edge(startOfEdgeList + activePoint.getActiveLength(), tree);
            Edge newEdge = new Edge(tree.getEndOfSuffix() - 1, tree);
            newEdge.setIndexFromEndNode(tree.getTotalInserts());
            tree.increaseTotalInserts();
            oldEdge.endOfEdgeList = endOfEdgeList;
            oldEdge.endNode = endNode;
            oldEdge.indexFromEndNode = indexFromEndNode;
            newNode.insertEdgeInMap(newEdge);
            newNode.insertEdgeInMap(oldEdge);
            //Update endNode en nieuwe endpositie van node
            this.endNode = newNode;
            endOfEdgeList = startOfEdgeList + activePoint.getActiveLength();
            if (tree.getInserts() > 0) {
                tree.getLast().setSuffixLink(newNode);
            }
            tree.setLast(newNode);
            tree.increaseInserts();
//            System.out.println("Deze edge is net toegevoegd");
//            for(int i=startOfEdgeList; i< endOfEdgeList; i++){
//                System.out.print(tree.getSequence().get(i)+",");
//            }
            activePoint.update();
            
            /*Er moeten geen nieuwe suffixen toegevoegd worden in deze stap
             isFinished() geeft terug of de boom volledig is aangemaakt
             */
            if (tree.getRemainder()==1 || tree.isFinished()) {
                //Dit gaat niets doen als de remainder 1 is
                System.out.println("Tis gedaan!");
                tree.decreaseRemainder();
            } else {
                //Er moeten nog nieuwe suffixen toegevoegd worden, maar 1 minder als daarnet
                System.out.println("Nog een keer inserten!");
                tree.decreaseRemainder();
                tree.insert();
            }
        }
    }

    public int getStartOfEdgeList() {
        return startOfEdgeList;
    }

    public int getEndOfEdgeList() {
        if (endOfEdgeList != -1) {
            return endOfEdgeList;
        } else {
            return tree.getEnd();
        }
    }

    public int getSize() {
        int end;
        if (endOfEdgeList != -1) {
            end = endOfEdgeList;
        } else {
            end = tree.getEnd();
        }
        return end - startOfEdgeList;
    }

    public Node3 getEndNode() {
        return endNode;
    }

   
    public void setIndexFromEndNode(int index){
        indexFromEndNode = index;
    }
    
    public int getIndexFromEndNode(){
        return indexFromEndNode;
    }

    @Override
    public Iterator<Short> iterator() {
        		return new Iterator<Short>() {
			private int currentPosition = startOfEdgeList;
			private boolean hasNext = true;

			public boolean hasNext() {
				return hasNext;
			}

			@SuppressWarnings("unchecked")
			public Short next() {
				if(endOfEdgeList == -1)
					hasNext = !tree.getSequence().get(currentPosition).equals(Short.MAX_VALUE);
				else
					hasNext = currentPosition < getEndOfEdgeList()-1;
				return (Short) tree.getSequence().get(currentPosition++);
			}

			public void remove() {
				throw new UnsupportedOperationException(
						"The remove method is not supported.");
			}
		};
    }
}
