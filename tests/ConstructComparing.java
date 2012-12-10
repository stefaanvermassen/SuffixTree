package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import suffixtree.SuffixTree1;
import suffixtree.SuffixTree2;
import suffixtree.SuffixTree3;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class ConstructComparing {

    private SuffixTree1 tree;
    private SuffixTree2 tree2;
    private SuffixTree3 tree3;
    private ArrayList<Short> sequence;
    private int listSize;
    private boolean outOfMemory;
    private boolean outOfMemory2;
    private long start;
    private long end;

    public ConstructComparing() {
        listSize = 0;
        tree = new SuffixTree1();
        tree2 = new SuffixTree2();
        tree3 = new SuffixTree3();
        sequence = new ArrayList<Short>();
        for (int i = 0; i < 1000; i++) {
            Random random = new Random();
            sequence = new ArrayList<Short>();
            for (int j = 0; j < listSize; j++) {
                sequence.add(new Short((short) (random.nextInt(65535) - 32767)));
            }
            if (!outOfMemory) {
                try {
                    System.out.println("tree1:" + sequence.size() + " " + measureTask(10,1));
                } catch (OutOfMemoryError e) {
                    outOfMemory = true;
                }
            }
            if (!outOfMemory2) {
                try {
                    System.out.println("tree2:" + sequence.size() + " " + measureTask(10,2));
                } catch (OutOfMemoryError e) {
                    outOfMemory2 = true;
                }
            }
            System.out.println("tree3:" + sequence.size() + " " + measureTask(10,3));
            listSize += 5000;
        }
    }

    public long measureTask(long n, int tree) {
        long t1 = System.currentTimeMillis();
        if (tree == 1) {
            ArrayList<Short> list = (ArrayList) sequence.clone();
            for (long i = 0; i < n; i++) {
                this.tree = new SuffixTree1();
                this.tree.construct(list);
            }
        } else if (tree == 2) {
            for (long i = 0; i < n; i++) {
                ArrayList<Short> list = (ArrayList) sequence.clone();
                tree2 = new SuffixTree2();
                tree2.construct(list);
            }
        } else {
            for (long i = 0; i < n; i++) {
                ArrayList<Short> list = (ArrayList) sequence.clone();
                tree3 = new SuffixTree3();
                tree3.construct(list);
            }
        }
        long t2 = System.currentTimeMillis();
        return (t2 - t1) / n;
    }

    public static void main(String[] args) {
        ConstructComparing c = new ConstructComparing();
    }
}
