package org.jallaby.beans.swing.editor.gui.tree;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import javax.swing.JTree;

import org.jallaby.beans.swing.editor.model.workspace.Project;

public class TreeManager {
	private JTree projectsTree;
	
	public void setProjectsTree(final JTree projectsTree) {
		Objects.requireNonNull(projectsTree, "projectsTree must not be null");
		this.projectsTree = projectsTree;
	}

	public void clearRoot() {
		((TreeModel) projectsTree.getModel()).setRoot(new TreeNode("Projects", true));
	}

	public void addProject(Project project) {
		((TreeModel) projectsTree.getModel()).addProject(new TreeNode(new ProjectNameAndPath(
				UUID.fromString(project.getUuid()), Paths.get(project.getPath()))));
	}
}
