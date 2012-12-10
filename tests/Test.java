package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import suffixtree.AltIntervalSearcher;
import suffixtree.SuffixTree1;
import suffixtree.SuffixTree2;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class Test {

    private Random random;
    private long start;
    private long end;
    private int sequentiegrootte = 1000;
    private int querygrootte = 0;
    
    public Test(){
    }

    public static void main(String[] args) {
        Test test = new Test();
        for (int i = 0; i < 100; i++) {
            test.startTest();
        }
    }

    public void startTest() {
        System.gc();
        List<Short> sequence = new ArrayList<Short>();
        List<Short> query = new ArrayList<Short>();
        random = new Random();
        for (int i = 0; i < sequentiegrootte; i++) {
            sequence.add(new Short((short) (random.nextInt(65536) - 32768)));
        }
        while (querygrootte <= sequentiegrootte) {
            List<Short> seq = new ArrayList<Short>();
            seq.addAll(sequence);
            for (int i = 0; i < querygrootte; i++) {
                query.add(new Short((short) (random.nextInt(65536) - 32768)));
            }
            System.out.format("%-109s%n", "---------------------------------------------------------------------------------------");
            System.out.format("|%-22s|%-20s|%-20s|%-10s|%-10s|%n", " Opdracht", " Snelheid", " Algoritme", " Seq/Query", " Correct");
            System.out.format("%-109s%n", "---------------------------------------------------------------------------------------");
            SuffixTree1 tree = new SuffixTree1();
            SuffixTree2 tree2 = new SuffixTree2();
            AltIntervalSearcher alt = new AltIntervalSearcher();
            reset();
            start = System.currentTimeMillis();
            tree.construct(seq);
            end = System.currentTimeMillis();
            System.out.format("|%-22s|%-20s|%-20s|%-10s|%-10s|%n", " Constructie", " " + (end - start), " SuffixTree1", " " + seq.size() + "/" + query.size(), "");
            reset();
            start = System.currentTimeMillis();
            tree2.construct(seq);
            end = System.currentTimeMillis();
            System.out.format("|%-22s|%-20s|%-20s|%-10s|%-10s|%n", " Constructie", " " + (end - start), " SuffixTree2", " " + seq.size() + "/" + query.size(), "");
            reset();
            start = System.currentTimeMillis();
            alt.construct(seq);
            end = System.currentTimeMillis();
            System.out.format("|%-22s|%-20s|%-20s|%-10s|%-10s|%n", " Constructie", " " + (end - start), " AltInterval", " " + seq.size() + "/" + query.size(), "");
            reset();
            start = System.currentTimeMillis();
            boolean treebool = tree.contains(query);
            end = System.currentTimeMillis();
            System.out.format("|%-22s|%-20s|%-20s|%-10s|%-10s|%n", " Contains", " " + (end - start), " SuffixTree1", " " + seq.size() + "/" + query.size(), "");
            reset();
            start = System.currentTimeMillis();
            boolean tree2bool = tree2.contains(query);
            end = System.currentTimeMillis();
            System.out.format("|%-22s|%-20s|%-20s|%-10s|%-10s|%n", " Contains", " " + (end - start), " SuffixTree2", " " + seq.size() + "/" + query.size(), "");
            reset();
            start = System.currentTimeMillis();
            boolean altbool = alt.contains(query);
            end = System.currentTimeMillis();
            System.out.format("|%-22s|%-20s|%-20s|%-10s|%-10s|%n", " Contains", " " + (end - start), " AltInterval", " " + seq.size() + "/" + query.size(), (treebool == altbool) && (tree2bool==altbool));
            reset();
            if(!((treebool == altbool) && (tree2bool==altbool))){
                System.out.println("treebool="+treebool);
                System.out.println("tree2bool="+tree2bool);
                System.out.println("altbool="+altbool);
            }
            start = System.currentTimeMillis();
            int treeint = tree.count(query);
            end = System.currentTimeMillis();
            System.out.format("|%-22s|%-20s|%-20s|%-10s|%-10s|%n", " Count", " " + (end - start), " SuffixTree1", " " + seq.size() + "/" + query.size(), "");
            reset();
            start = System.currentTimeMillis();
            int tree2int = tree2.count(query);
            end = System.currentTimeMillis();
            System.out.format("|%-22s|%-20s|%-20s|%-10s|%-10s|%n", " Count", " " + (end - start), " SuffixTree2", " " + seq.size() + "/" + query.size(), "");
            reset();
            start = System.currentTimeMillis();
            int altint = alt.count(query);
            end = System.currentTimeMillis();
            System.out.format("|%-22s|%-20s|%-20s|%-10s|%-10s|%n", " Count", " " + (end - start), " AltInterval", " " + seq.size() + "/" + query.size(), treeint == altint && tree2int == altint);
            if (!(treeint == altint && tree2int == altint)){
                System.out.println("treeint="+treeint);
                System.out.println("tree2int="+tree2int);
                System.out.println("altint="+altint);
            }
            reset();
            start = System.currentTimeMillis();
            Set<Integer> treelist = new HashSet<Integer>();
            treelist = tree.locate(query);
            end = System.currentTimeMillis();
            System.out.format("|%-22s|%-20s|%-20s|%-10s|%-10s|%n", " Locate", " " + (end - start), " SuffixTree1", " " + seq.size() + "/" + query.size(), "");
            reset();
            start = System.currentTimeMillis();
            Set<Integer> tree2list = new HashSet<Integer>();
            tree2list = tree.locate(query);
            end = System.currentTimeMillis();
            System.out.format("|%-22s|%-20s|%-20s|%-10s|%-10s|%n", " Locate", " " + (end - start), " SuffixTree2", " " + seq.size() + "/" + query.size(), "");
            reset();
            start = System.currentTimeMillis();
            Set<Integer> altlist = new HashSet<Integer>();
            altlist = alt.locate(query);
            end = System.currentTimeMillis();
            System.out.format("|%-22s|%-20s|%-20s|%-10s|%-10s|%n", " Locate", " " + (end - start), " AltInterval", " " + seq.size() + "/" + query.size(), ((treelist.containsAll(altlist) && altlist.containsAll(treelist))&& (tree2list.containsAll(altlist) && altlist.containsAll(tree2list))));
            if(!((treelist.containsAll(altlist) && altlist.containsAll(treelist))&& (tree2list.containsAll(altlist) && altlist.containsAll(tree2list)))){
                System.out.println(treelist);
                System.out.println(tree2list);
                System.out.println(altlist);
            }
            querygrootte += 10;
            query.clear();
        }
        sequentiegrootte += 1000;
        querygrootte = 0;

    }

    public void reset() {
        start = 0;
        end = 0;

    }
}
