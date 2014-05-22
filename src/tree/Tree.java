package tree;

public class Tree<T> {
	
    private Node<T> root;
    private Node<T> currentParentNode;
   
	public void setRoot(Node<T> root) {
		this.root = root;
		this.currentParentNode = null;
	}

	public Node<T> getRoot() {
		return root;
	}
	
	public Node<T> getCurrentParentNode() {
		return currentParentNode;
	}
	
	public void goUp() {
		if(currentParentNode != root)
			currentParentNode = currentParentNode.getParent();
	}

	public void addChild(Node<T> parent, Node<T> child) {
		
		if (root == null)
			root = child;
		else
			parent.addChild(child);
		currentParentNode = child;
	}
}