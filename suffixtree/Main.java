/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package suffixtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class Main {

    public Main() {
        List<Short> sequence = new ArrayList<Short>();
        sequence.add((short) 1);
        SuffixTree3 tree = new SuffixTree3();
        SuffixTree2 tree2 = new SuffixTree2();
        tree2.construct(sequence);
        //tree.construct(sequence);
        tree2.printShiz();
        //System.out.println(tree.toString());



    }

    public static void main(String[] args) {
        Main main = new Main();
    }

 
}
