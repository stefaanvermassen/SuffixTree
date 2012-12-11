package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import suffixtree.AltIntervalSearcher;
import suffixtree.SuffixTree3;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class SearchOperationsTest {

    private List<Short> sequence;
    private List<Short> query;
    private SuffixTree3 tree;
    private long constructTime;
    private long locateTime;
    private long totalTime;
    private long altLocateTime;
    private AltIntervalSearcher alt;

    public SearchOperationsTest(int lijstgrootte, int numberOfQueries) {
        Random random = new Random();
        tree = new SuffixTree3();
        alt = new AltIntervalSearcher();
        sequence = new ArrayList<Short>();
        for (int i = 0; i <= lijstgrootte; i++) {
            sequence.add((short) random.nextInt(5));
        }
        long start = System.currentTimeMillis();
        tree.construct(sequence);
        long end = System.currentTimeMillis();
        alt.construct(sequence);
        constructTime = end - start;
        System.out.println("De construct duurde " + constructTime +"ms");
        for (int z = 0; z < 100; z++) {
            locateTime = 0;
            altLocateTime = 0;
            totalTime = 0;
            for (int i = 0; i < numberOfQueries; i++) {
                int startindex = random.nextInt(sequence.size() - 100);
                query = sequence.subList(startindex, startindex + 100);
                start = System.currentTimeMillis();
                tree.locate(query);
                end = System.currentTimeMillis();
                locateTime += (end - start);
                start = System.currentTimeMillis();
                alt.locate(query);
                end = System.currentTimeMillis();
                altLocateTime += (end-start);
            }
            totalTime = locateTime + constructTime;
            System.out.println("tree:"+numberOfQueries+" "+totalTime);
            System.out.println("alt:"+numberOfQueries+" "+altLocateTime);
            numberOfQueries += 50;
        }
    }

    public static void main(String[] args) {
        SearchOperationsTest uma = new SearchOperationsTest(5000000, 100);
    }
}
