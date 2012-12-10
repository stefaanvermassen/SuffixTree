/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package suffixtree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author Stefaan Vermassen <Stefaan.Vermassen@UGent.be>
 */
public class Utils {


	
	/**
	 * Generates a .dot format string for visualizing a suffix tree.
	 * 
	 * @param tree
	 *            The tree for which we are generating a dot file.
	 * @return A string containing the contents of a .dot representation of the
	 *         tree.
	 */
	static String printTreeForGraphViz(SuffixTree3 tree, boolean printSuffixLinks) {
		LinkedList<Node3> stack = new LinkedList<Node3>();
		stack.add(tree.getRoot());
		Map<Node3, Integer> nodeMap = new HashMap<Node3, Integer>();
		nodeMap.put(tree.getRoot(), 0);
		int nodeId = 1;

		StringBuilder sb = new StringBuilder(
				"\ndigraph suffixTree{\n node [shape=circle, label=\"\", fixedsize=true, width=0.1, height=0.1]\n");

		while (stack.size() > 0) {
			LinkedList<Node3> childNodes = new LinkedList<Node3>();
			for (Node3 node : stack) {

				// List<Edge> edges = node.getEdges();
				for (Object e : node) {
                                    Edge edge = (Edge) e;
					int id = nodeId++;
					if (edge.getEndNode()!= null) {
						childNodes.push(edge.getEndNode());
						nodeMap.put(edge.getEndNode(), id);
					}

					sb.append(nodeMap.get(node)).append(" -> ").append(id)
							.append(" [label=\"");

					for (Object item : edge) {
						//if(item != null)
							sb.append(item.toString()+",");
					}
                                        sb.append("("+edge.getIndexFromEndNode()+")");
					sb.append("\"];\n");
				}
			}
			stack = childNodes;
		}
		sb.append("}");
		return (sb.toString());
	}
        
        
}