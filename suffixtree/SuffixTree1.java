package suffixtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class SuffixTree1 implements IntervalSearcher {

    private List<Short> sequence = null;
    private Node1 root = null;
    private Node1 found = null;
    private int counter = 0;
    private HashSet<Integer> locations = new HashSet<Integer>();
    private int position;

    @Override
    public void construct(List<Short> sequence) {
        this.sequence = sequence;
        sequence.add(SENTINEL);
        root = new Node1();
        int index = 0;
        for (int i = 0; i < sequence.size(); i++) {
            List<Short> list = sequence.subList(index, sequence.size());
            addInTree(list, index);
            index++;
        }
        sequence.remove(sequence.size()-1);

    }

    public void addInTree(List<Short> suffix, int index) {
        Node1 node = searchPosition(root, suffix, 0);
        for (int j = position; j < suffix.size(); j++) {
            Node1 child = new Node1(node, suffix.get(j), node.getDepth() + 1, index);
            node.getChildren().add(child);
            node = child;
        }
    }

    private Node1 searchPosition(Node1 startNode, List<Short> suffix, int position) {
        Collection<AbstractTreeNode> children = startNode.getChildren();
        for (AbstractTreeNode c : children) {
            Node1 child = (Node1) c;
            if (child.getEdge().equals(suffix.get(position))) {
                position++;
                if (position == suffix.size()) {
                    return child;
                }
                this.position = position;
                return searchPosition(child, suffix, position);
            }
        }
        this.position = position;
        return startNode;
    }

    @Override
    public boolean contains(List<Short> query) {
        int size = query.size();
        return recursiveContains(query, root, 0, size);
    }

    public boolean recursiveContains(List<Short> query, Node1 node, int index, int size) {
        if (index == size) {
            found = node;
            return true;
        }
        for (AbstractTreeNode c : node.getChildren()) {
            Node1 child = (Node1) c;
            if (child.getEdge().equals(query.get(index))) {
                index++;
                if (recursiveContains(query, child, index, size)) {
                    return true;
                }
                return false;
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
            findNumberOfLeafs(found);
            return counter;
        }
    }

    public void findNumberOfLeafs(Node1 node) {
        if (node.isLeaf()) {
            counter++;
            locations.add(node.getIndex());
        } else {
            for (AbstractTreeNode c : node.getChildren()) {
                Node1 child = (Node1) c;
                findNumberOfLeafs(child);
            }
        }
    }

    @Override
    public Set<Integer> locate(List<Short> query) {
        locations.clear();
        if (query.isEmpty()) {
            //eindteken eraf
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
}
