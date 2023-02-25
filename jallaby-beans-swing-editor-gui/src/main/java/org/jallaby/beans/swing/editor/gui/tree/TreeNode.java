package org.jallaby.beans.swing.editor.gui.tree;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = 1643355691103150921L;

	public TreeNode() {
	}

	public TreeNode(Object userObject) {
		super(userObject);
	}

	public TreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}
}
