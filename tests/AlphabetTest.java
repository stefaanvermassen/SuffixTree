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
public class AlphabetTest {

    private SuffixTree3 tree;
    private List<Short> sequence;
    private List<Short> query;
    private long constructTime;
    private long locateTime;

    public void test(int alfabetgrootte) {
        tree = new SuffixTree3();
        Random random = new Random();
        long start = 0;
        long end = 0;
        sequence = new ArrayList<Short>();
        for (int i = 0; i <= 5000000; i++) {
            sequence.add((short) (-32768 + random.nextInt(alfabetgrootte)));
        }
        start = System.currentTimeMillis();
        tree.construct(sequence);
        end = System.currentTimeMillis();
        constructTime = (end - start);
        System.out.println("Tree construct in " + constructTime + "ms");
        locateTime = 0;
        for (int i = 0; i < 10000; i++) {
            int startindex = random.nextInt(sequence.size() - 100);
            query = sequence.subList(startindex, startindex + 100);
            start = System.currentTimeMillis();
            tree.locate(query);
            end = System.currentTimeMillis();
            locateTime += (end - start);
        }

        System.out.println(alfabetgrootte + " " + locateTime);


    }

    public static void main(String[] args) {
        AlphabetTest t = new AlphabetTest();
        for (int i = 2; i < 65535; i += 1000) {
            t.test(i);
        }
    }
}
