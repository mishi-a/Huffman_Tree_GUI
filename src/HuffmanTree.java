import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanTree 
{
	String toEncode;
	HuffmanTreeNode root;
	
	// constructor for the class
	public HuffmanTree(String toEncode)
	{
		this.toEncode = toEncode;
	}
	
	// this creates all the leaf nodes and adds them to the min heap
	private PriorityQueue<HuffmanTreeNode> buildHeap()
	{
		PriorityQueue<HuffmanTreeNode> minHeap = new PriorityQueue<>(toEncode.length(), new HuffmanNodeComparator());
		
		// getting frequencies
		int[] frequencies = new int[65536];
		for(int i = 0;i < toEncode.length();i++)
			frequencies[(int)toEncode.charAt(i)]++;
		
		// creating leaf nodes and inserting them in priority queue
		for(int i = 0;i < 65536;i++)
		{
			if(frequencies[i] > 0)
			{
				HuffmanTreeNode leafNode = new HuffmanTreeNode((char)i, frequencies[i]);
				minHeap.add(leafNode);
			}
		}
		
		// returning this priority queue
		return minHeap;
	}
	
	private void buildHuffmanTree()
	{
		PriorityQueue<HuffmanTreeNode> minHeap = buildHeap();
		
		while(minHeap.size() > 1)
		{
			// extract two nodes with minimum frequency
			HuffmanTreeNode firstMin = minHeap.remove();
			HuffmanTreeNode secondMin = minHeap.remove();
			
			// create an internal node with sum of freq of these two
			HuffmanTreeNode internalNode = new HuffmanTreeNode(firstMin.frequency + secondMin.frequency, firstMin, secondMin);
			
			// add this node to the min heap
			minHeap.add(internalNode);
		}
		
		// remaining node is the root node of the tree
		root = minHeap.remove();
	}
	
	public HashMap<Character, String> getHuffmanEncoding()
	{
		// builds the huffman tree
		buildHuffmanTree();
		
		HashMap<Character, String> map = new HashMap<Character, String>();
		
		// recursive helper method
		getHuffmanEncoding(root, "", map);
		
		return map;
	}
	
	private void getHuffmanEncoding(HuffmanTreeNode root, String encoding, HashMap<Character, String> map)
	{
		// base condition
		if(root.nodeToLeft == null && root.nodeToRight == null)
		{
			map.put(root.character, encoding);
			return ;
		}
		
		// recursive calls
		getHuffmanEncoding(root.nodeToLeft, encoding + "0", map);
		getHuffmanEncoding(root.nodeToRight, encoding + "1", map);
	}
	
	// used for getting height of the tree
	public int getHeight()
	{
		return getHeight(root);
	}
	
	// recursivve helper function
	// returns height of the tree rooted at root
	private int getHeight(HuffmanTreeNode root)
	{
		if(root == null)
			return 0;
		
		int heightLeft = getHeight(root.nodeToLeft);
		int heightRight = getHeight(root.nodeToRight);
		
		return 1 + Math.max(heightLeft, heightRight);
	}
}

class HuffmanTreeDecoder
{
	HuffmanTree tree;
	String toDecode;
	
	// constructor
	// decoding requires a huffman tree and strig to decode
	public HuffmanTreeDecoder(HuffmanTree tree, String toDecode)
	{
		this.tree = tree;
		this.toDecode = toDecode;
	}
	
	// method for decoding the string
	public String decode()
	{
		int len = toDecode.length();
		String decoded = "";
		
		// contains current node of the tree which is being traversed
		HuffmanTreeNode current = tree.root;
		
		for(int i = 0;i < len;i++)
		{
			// if the leaf node is reached
			if(current.nodeToLeft == null && current.nodeToRight == null)
			{
				decoded += current.character;
				current = tree.root;
			}
			else
			{
				// invalid sequence condition
				if(i == len - 1)
					return null;
				
				// if encoding is zero go left else go right
				if(toDecode.charAt(i) == '0')
					current = current.nodeToLeft;
				else
					current = current.nodeToRight;
			}
		}
		
		return decoded;
	}
}

// this class is used for defining comparison for priority queue of HuffmanTreeNode
class HuffmanNodeComparator implements Comparator<HuffmanTreeNode>
{
	@Override
	public int compare(HuffmanTreeNode arg0, HuffmanTreeNode arg1) 
	{
		return (arg0.frequency - arg1.frequency);
	}	
}

// this is a structure of huffman tree node
// it contains the char, its frequency and pointers to two nodes left and right
class HuffmanTreeNode
{
	char character;
	int frequency;
	
	HuffmanTreeNode nodeToLeft;
	HuffmanTreeNode nodeToRight;
	
	// general constructor
	public HuffmanTreeNode(char character, int frequency)
	{
		this.character = character;
		this.frequency = frequency;
	}
	
	// used when this is a internal node
	public HuffmanTreeNode(int frequency, HuffmanTreeNode nodeToLeft, HuffmanTreeNode nodeToRight)
	{
		this.character = '~';
		this.frequency = frequency;
		this.nodeToLeft = nodeToLeft;
		this.nodeToRight = nodeToRight;
	}
}
