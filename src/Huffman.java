import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;

import com.sun.javafx.collections.MappingChange.Map;
 
// node class is the basic structure
class HuffmanNode {
 
    int data;
    char c;
 
    HuffmanNode left;
    HuffmanNode right;
}
 
// comparator class helps to compare the node
// on the basis of one of its attribute.
// Here we will be compared
// on the basis of data values of the nodes.
class MyComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y)
    {
 
        return x.data - y.data;
    }
}
 
public class Huffman {
 
    // recursive function to print the
    // huffman-code through the tree traversal.
    // Here s is the huffman - code generated.
	static HashMap<Character, String> map;
	public static HuffmanNode root;
	public static void main(String[] args)
    {
    	findHuffman("welcome ayush");
    }
    public static void printCode(HuffmanNode root1, String s)
    {
 
        // base case; if the left and right are null
        // then its a leaf node and we print
        // the code s generated by traversing the tree.
        if (root1.left
                == null
            && root1.right
                   == null
            && root1.c <260) {
 
            // c is the character in the node
            //System.out.println(root.c + ":" + s);
            map.put(new Character(root1.c),s);
 
            return;
        }
 
        // if we go to left then add "0" to the code.
        // if we go to the right add"1" to the code.
 
        // recursive calls for left and
        // right sub-tree of the generated tree.
        printCode(root1.left, s + "0");
        printCode(root1.right, s + "1");
    }
    public static HashMap<Character, String> findHuffman(String s)
    {
    	System.out.println(s);
    	map = new HashMap<Character,String>();
    	int[] charFreq = new int[260];
    	for(int i=0;i<s.length();i++)
    	{
    	//	if(s.charAt(i)-'a'>=0 && s.charAt(i)-'a'<26)
    		{
    			charFreq[s.charAt(i)]++;
    		}
    	}
//    	for(int i=0;i<260;i++)
//    	{
//    		System.out.println(i+" "+charFreq[i]);
//    	}
    	PriorityQueue<HuffmanNode> q  = new PriorityQueue<HuffmanNode>(s.length(), new MyComparator());

    	for (int i = 0; i <260; i++) {
	
	        // creating a huffman node object
        // and adding it to the priority-queue.
        HuffmanNode hn = new HuffmanNode();
        
        if(charFreq[i]>0)
        {
        	hn.c = (char) (i);
            hn.data = charFreq[i];
            
            hn.left = null;
            hn.right = null;
            // add functions adds
            // the huffman node to the queue.
            q.add(hn);
        }
        
    }
    	System.out.println(q.size());
    // create a root node
     root = null;

    // Here we will extract the two minimum value
    // from the heap each time until
    // its size reduces to 1, extract until
    // all the nodes are extracted.
    while (q.size() > 1) {

        // first min extract.
        HuffmanNode x = q.peek();
        q.poll();

        // second min extarct.
        HuffmanNode y = q.peek();
        q.poll();

        // new node f which is equal
        HuffmanNode f = new HuffmanNode();

        // to the sum of the frequency of the two nodes
        // assigning values to the f node.
        f.data = x.data + y.data;
        f.c = (char)1265;

        // first extracted node as left child.
        f.left = x;

        // second extracted node as the right child.
        f.right = y;

        // marking the f node as the root node.
        root = f;

        // add this node to the priority-queue.
        q.add(f);
    }

    // print the codes by traversing the tree
    System.out.println((char)1265);
    printCode(root, "");
    System.out.println(map);
    return map;
 }
    public static int findHeight(HuffmanNode root)
    {
    	if(root == null)
    		return 0;
    	else
    	{
    		return 1+Math.max(findHeight(root.left),findHeight(root.right));
    	}
    }
    
}