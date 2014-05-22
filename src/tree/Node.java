package tree;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
	
	private T data;
	private Node<T> parent;
	private int level;
	private List<Node<T>> children;
	
	public Node(T data, int level) {
		this.data = data;
		this.level = level;
		this.parent = null;
		children = new ArrayList<Node<T>>();
	}
	
	public Node(T data, int level, Node<T> parent) {
		this.data = data;
		this.level = level;
		this.parent = parent;
		children = new ArrayList<Node<T>>();
	}

	public T getData() {
		return data;
	}
	
	public int getLevel() {
		return level;
	}

	public Node<T> getParent() {
		return parent;
	}

	public List<Node<T>> getChildren() {
		return children;
	}
	
	public void addChild(Node<T> node){
		children.add(node);
	}

}