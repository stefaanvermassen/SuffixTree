package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import suffixtree.SuffixTree3;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class Benchmark {
    
    private SuffixTree3 tree1;
    private List<Short> sequence;
    private int lijstgrootte;
    
    public Benchmark(){
        lijstgrootte=50000000;
        tree1 = new SuffixTree3();
        for (int w=0; w<100000000; w++){
        Random random = new Random();
        sequence = new ArrayList<Short>();
        for (int i = 0; i <= lijstgrootte; i++) {
            sequence.add(new Short((short) (random.nextInt(65535) - 32767)));
        }
        warmupJvm();
        System.out.println(lijstgrootte+" "+measureTask(5));
        lijstgrootte+=250;
        }
        
    }

    public static void main(String[] args){
        Benchmark bm = new Benchmark();
    }

    public void warmupJvm(){
        System.gc();
        long n=1;
        for (long start = System.nanoTime(); System.nanoTime() - start < 100* 1e8; n *= 2) {
            measureTask(n);
        }
    }
    
    public long measureTask(long n){
        long t1 = System.currentTimeMillis();
        for (long i=0; i<n; i++){
            tree1 = new SuffixTree3();
            tree1.construct(sequence);
        }
        long t2 = System.currentTimeMillis();
        return (t2-t1)/n;
    }
    
    
}
