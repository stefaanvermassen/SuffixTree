package suffixtree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class SuffixTree2 implements IntervalSearcher {

    private List<Short> sequence = null;
    private Node2 root;
    private Node2 foundnode = null;
    private int counter = 0;
    private HashSet<Integer> locations = new HashSet<Integer>();

    @Override
    public void construct(List<Short> sequence) {
        this.sequence = sequence;
        sequence.add(SENTINEL);
        root = new Node2();
        int index = sequence.size() - 1;
        for (int i = 0; i < sequence.size(); i++) {
            List<Short> suffix = sequence.subList(index, sequence.size());
            addInTree(root, suffix, index);
            index--;
        }
    }

    private void insertWholeSuffix(Node2 node, int index, List<Short> suffix) {
        Node2 child = new Node2(node, index, suffix);
        node.addChild(child);
        if (node != root) {
            node.addChild(null);
        }
    }

    public void addInTree(Node2 startNode, List<Short> suffix, int index) {
        if (startNode.isLeaf()) {
            insertWholeSuffix(startNode, index, suffix);
        } else {
            int howManyFound = 0;
            for (AbstractTreeNode c : startNode.getChildren()) {
                Node2 child = (Node2) c;
                if (child != null) {
                    // howManyFound = aantal shorts er op de edge van het kind overeenkomen met te toe te voegen suffix
                    howManyFound = 0;
                    for (int i = 0; i < child.getEdge().size(); i++) {
                        if (suffix.get(i).equals(child.getEdge().get(i))) {
                            howManyFound++;
                        } else {
                            break;
                        }
                    }
                    //Er bestaat reeds een boog met 1 of meerdere overeenkomende shorts
                    if (howManyFound > 0) {
                        //De boog komt helemaal overeen
                        if (howManyFound == child.getEdge().size()) {
                            addInTree(child, suffix.subList(howManyFound, suffix.size()), index);
                            break;
                            //Er moet gesplitst worden
                        } else {
                            Node2 firstNode = new Node2(startNode, child.getIndex(), child.getEdge().subList(0, howManyFound));
                            Node2 secondNode = new Node2(firstNode, child.getIndex(), child.getEdge().subList(howManyFound, child.getEdge().size()));
                            firstNode.addChild(secondNode);
                            secondNode.children = child.getChildren();
                            Node2 childChild = new Node2(firstNode, index, suffix.subList(howManyFound, suffix.size()));
                            startNode.addChild(firstNode);
                            startNode.children.remove(child);
                            firstNode.addChild(childChild);
                            break;
                        }
                    }
                }
            }
            //Geen enkele boog van geen enkel kind komt overeen, ook geen deel
            if (howManyFound == 0) {
                Node2 child;
                if (!startNode.equals(root)) {
                    child = new Node2(startNode, index, suffix.subList(startNode.getEdge().size() - 1, suffix.size()));
                } else {
                    child = new Node2(startNode, index, suffix);
                }
                startNode.addChild(child);
            }

        }
    }

    @Override
    public boolean contains(List<Short> query) {
        int size = query.size();
        return recursiveContains(query, root, 0, size);
    }

    public boolean recursiveContains(List<Short> query, Node2 node, int index, int size) {
        if (index == size) {
            foundnode = node;
            return true;
        }
        for (AbstractTreeNode c : node.getChildren()) {
            Node2 child = (Node2) c;
            int found = 1;
            if (child != null) {
                int temp = index;
                for (int i = 0; i < child.getEdge().size(); i++) {
                    if (child.getEdge().get(i).equals(query.get(index))) {
                        index++;
                    } else {
                        found = 0;
                        index = temp;
                        break;
                    }
                    if (index == size) {
                        foundnode = child;
                        return true;
                    }
                }
                if (found == 1) {
                    if (recursiveContains(query, child, index, size)) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;

    }

    @Override
    public int count(List<Short> query) {
        if (query.isEmpty()) {
            return sequence.size();
        }
        if (contains(query) == false) {
            return 0;
        } else {
            counter = 0;
            findNumberOfLeafs(foundnode);
            return counter;
        }
    }

    public void findNumberOfLeafs(Node2 node) {
        if (node.isLeaf()) {
            counter++;
            locations.add(node.getIndex());
        } else {
            for (AbstractTreeNode c : node.getChildren()) {
                Node2 child = (Node2) c;
                findNumberOfLeafs(child);
            }
        }
    }

    @Override
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

    @Override
    public TreeNode getRoot() {
        return root;
    }

    public void printShiz() {
        printShiz(root, 0);
    }

    public void printShiz(TreeNode node, int depth) {
        for (TreeNode c : node.getChildren()) {
            Node2 ccc = (Node2) c;
            System.out.print("|-");
            for (int j = 0; j < depth; j++) {
                System.out.print("---");
            }
            System.out.print("> ");
            if (ccc == null) {
                System.out.println("null");
                continue;
            }
            int i = 0;
            System.out.print("[");
            for (Short s : ccc.getEdge()) {
                i++;
                if (i != ccc.getEdge().size()) {
                    System.out.print(s.toString() + ", ");
                } else {
                    System.out.print(s.toString());
                }
            }
            System.out.print("] " + ccc.getIndex());
            if (i != 0) {
                System.out.println();
            }
            printShiz(ccc, depth + 1);
        }
    }
}
