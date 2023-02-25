package org.jallaby.beans.swing.editor.gui.tree;

import java.util.Objects;

import javax.swing.tree.DefaultTreeModel;

import org.jallaby.beans.swing.editor.util.DataAccessorStrategy;
import org.jallaby.beans.swing.editor.util.OrderedIndexFinder;

public class TreeModel extends DefaultTreeModel {
	private class TreeNodeAccessor implements DataAccessorStrategy<String> {
		private TreeNode parent;
		
		public TreeNodeAccessor(TreeNode parent){
			Objects.requireNonNull(parent, "parent must not be null");
			this.parent = parent;
		}
		
		@Override
		public String getElementAt(int index) {
			return ((TreeNode) parent.getChildAt(index)).getUserObject().toString();
		}

		@Override
		public int getSize() {
			return parent.getChildCount();
		}
	}

	private static final long serialVersionUID = 4768164643651009864L;

	private TreeNode root = new TreeNode("Projects");
	
	public TreeModel(){
		super(null, true);
		setRoot(root);
	}

	public void setRoot(final TreeNode root) {
		super.setRoot(root);
		this.root = root;
	}
	
	public void addProject(final TreeNode child) {
		int position = getNodePosition(child, root);
		insertNodeInto(child, root, position);
		insertNodeInto(new TreeNode("Project Overview"), child, 0);
		insertNodeInto(new TreeNode("State Machine Model"), child, 1);
	}
	
	private int getNodePosition(TreeNode node, TreeNode parent){
		TreeNodeAccessor accessor = new TreeNodeAccessor(parent);
		
		OrderedIndexFinder<String> finder =
			new OrderedIndexFinder<String>(accessor);
		
		String input = node.getUserObject().toString();
		
		return finder.getOrderedIndex(input);
	}
}
