package tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import suffixtree.AltIntervalSearcher;
import suffixtree.SuffixTree1;
import suffixtree.SuffixTree3;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class ErrorTest {

    Random random;
    AltIntervalSearcher alt;
    SuffixTree3 tree;
    boolean succes;

    public ErrorTest() {
        random = new Random();
        alt = new AltIntervalSearcher();
        tree = new SuffixTree3();
        for (int i = 0; i < 100000; i++) {
            succes = startTest();
            if (succes) {
                System.out.println("Resultaten komen overeen!");
            } else {
                System.out.println("FOUT!");
                break;
            }
        }


    }

    public static void main(String[] args) {
        ErrorTest test = new ErrorTest();
    }

    public boolean startTest() {
        tree = new SuffixTree3();
        List<Short> sequence = new ArrayList<Short>();
        for (int i = 0; i < 9000; i++) {
            sequence.add(new Short((short) (random.nextInt(65535) - 32767)));
        }
        List<Short> query = new ArrayList<Short>();
        for (int i = 0; i < random.nextInt(10); i++) {
            query.add(new Short((short) (random.nextInt(65535) - 32767)));
        }
        int a = random.nextInt(sequence.size() - query.size());
        int b = a + query.size();
        for (int z = 0; z < random.nextInt(50); z++) {
            for (int i = a; i < b; i++) {
                sequence.add(i, query.get(i - a));
            }
        }
        alt.construct(sequence);
        tree.construct(sequence);
        boolean altbool = alt.contains(query);
        boolean treebool = tree.contains(query);
        int altint = alt.count(query);
        int treeint = tree.count(query);
        Set<Integer> altset = new HashSet<Integer>();
        Set<Integer> treeset = new HashSet<Integer>();
        altset = alt.locate(query);
        treeset= tree.locate(query);
        if (altbool == treebool && altint == treeint && altset.containsAll(treeset) && treeset.containsAll(altset)) {
            return true;
        } else {
            System.out.println(query.size());
            System.out.println(sequence.size());
            System.out.println(altint);
            System.out.println(treeint);
            System.out.println(altset);
            System.out.println(treeset);
            return false;
        }

    }
}
