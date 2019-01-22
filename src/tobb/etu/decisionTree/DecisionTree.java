package tobb.etu.decisionTree;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DecisionTree {
	
	//Root of the tree
	private Node Root;
	//number of nodes in decision tree
	private int DTsize;
	private ArrayList<String> lines;
	private int lineIndex;
	
	public int size() {
		return DTsize;		
	}
	
	public Node getRoot() {
		return Root;		
	}
	
	//create and return a new node r storing element e and
	//make r the root of the tree.
	public Node addRoot(String s) {
		//IMPLEMENT HERE
		Node r = new Node(s);
		Root = r;
		DTsize = 1;
		return Root;
	}
	
    //create and return a new node w storing element el and 
    //make w the left (yes) child of v. Make sure to increment the size.
    //make sure to update both child's parent link and parent's child link.
    //throws a RuntimeException if the node has already a left child.
	public Node insertYes(Node v, String el) {
		//IMPLEMENT HERE
		Node w = new Node(el);
		if(!v.hasLeft()) {
			v.setLeft(w);
			w.setParent(v);
			DTsize++;
			w.setParent(v);
		}
		else
			throw new RuntimeException("Node has already a left child.");
		return w;
	}

    //create and return a new node w storing element el and 
    //make w the right (no) child of v. Make sure to increment the size.
    //make sure to update both child's parent link and parent's child link.
    //throws a RuntimeException if the node has already a right child.
	public Node insertNo(Node v, String el) {
		//IMPLEMENT HERE
		Node w = new Node(el);
		if(!v.hasRight()) {
			v.setRight(w);
			w.setParent(v);
			DTsize++;
		}
		else
			throw new RuntimeException("Node has already a right child.");
		return w;
	}
	
    //remove node v, replace it with its child, if any, and return 
    //the element stored at v. make sure to decrement the size.
    //make sure to update both child's parent link and parent's child link.
    //throws a RuntimeException if v has two children
	public String remove(Node v) {
		//IMPLEMENT HERE
		String element = v.getElement();
		if(v.hasLeft() && v.hasRight())
			throw new RuntimeException("Node has two children.");
		else if(v.hasLeft()) {
			v.setElement(null);
			v.getParent().setLeft(v.getLeft());
			v.getLeft().setParent(v.getParent());
			DTsize--;
			return element;
		}
		else if(v.hasRight()) {
			v.setElement(null);
			v.getParent().setRight(v.getRight());
			v.getRight().setParent(v.getParent());
			DTsize--;
			return element;
		}
		else {
			if(v.getParent().getLeft().getElement() == v.getElement()) {
				v.setElement(null);
				v.getParent().setLeft(null);
				v.setParent(null);
				DTsize--;
			}
			if(v.getParent().getRight().getElement() == v.getElement()) {
				v.setElement(null);
				v.getParent().setRight(null);
				v.setParent(null);
				DTsize--;
			}
			return element;
		}
	}

    //write to file in PREORDER traversal order
    //Handle file exceptions as follows: If an exception is thrown, return false.
    //Otherwise, return true.	
	public boolean save(String filename) {
		//IMPLEMENT HERE
		PrintWriter yazici = null;
		String s = preorderYaz(Root);
		//System.out.println(s);
		try {
			yazici = new PrintWriter(filename);
			yazici.println(s);
			yazici.close();
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}
	public String preorderYaz(Node node) {
		if (node == null)
            return "";
        return node.getElement() + "\n" + preorderYaz(node.getLeft()) + preorderYaz(node.getRight());
	}
	
	
    //load the DT from file that contains the output of PREORDER traversal
    //Handle file exceptions as follows: If an exception is thrown, return false.
    //Otherwise, return true.
    //You can distinguish a leaf node from an internal node as follows: internal nodes always end with
    //a question mark.	
	public boolean load(String filename) {
		//IMPLEMENT HERE
		Scanner input = null;
		lines = new ArrayList<String>();
		try{
			input = new Scanner(new File(filename));
		    while(input.hasNextLine()) {
		    	 lines.add(input.nextLine());
		    }
		    input.close();
		    //System.out.println(lines);
		    Root = makeTree();
		} 
		catch(Exception e){
		      return false;
		}
		return true;
    }

    public Node makeTree() {
        lineIndex = 0;
        Node root = addRoot(lines.get(lineIndex));
		makeTreeAux(root);
		return root;
	}
	
	private void makeTreeAux(Node n) {
        if (n.getElement().endsWith("?")) {
		    lineIndex++;
            Node left = insertYes(n, lines.get(lineIndex));
            makeTreeAux(left);
            
            lineIndex++;
			Node right = insertNo(n, lines.get(lineIndex));
			makeTreeAux(right);
		}
	}

}