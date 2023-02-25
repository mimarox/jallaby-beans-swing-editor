package org.jallaby.beans.swing.editor.gui.pages;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.tree.TreePath;

import org.jallaby.beans.swing.editor.gui.components.ButtonTabComponent;
import org.jallaby.beans.swing.editor.gui.components.TabClosingListener;
import org.jallaby.beans.swing.editor.gui.tree.ProjectNameAndPath;
import org.jallaby.beans.swing.editor.gui.tree.TreeNode;
import org.jallaby.beans.swing.editor.model.workspace.Page;
import org.jallaby.beans.swing.editor.model.workspace.PageType;
import org.jallaby.beans.swing.editor.model.workspace.Project;
import org.jallaby.beans.swing.editor.model.workspace.SaveableWorkspaceSupplier;
import org.jallaby.beans.swing.editor.model.workspace.Workspace;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;

public class PagesManager {
	private class OverviewPageTabClosingListener implements TabClosingListener {
		private final int index;
		private final Project project;
		private final OverviewPage overviewPage;
		
		public OverviewPageTabClosingListener(final int index, final Project project,
				final OverviewPage overviewPage) {
			Objects.requireNonNull(project, "project must not be null");
			Objects.requireNonNull(overviewPage, "overviewPage must not be null");
			
			this.index = index;
			this.project = project;
			this.overviewPage = overviewPage;
		}
		
		@Override
		public boolean tabClosing(int tabIndex) {
			if (tabIndex == index) {
				return doTabClosing();
			}
			
			return false;
		}
		
		private boolean doTabClosing() {
			if (overviewPage.isDirty()) {
				int option = JOptionPane.showConfirmDialog(tabbedPane, new String[] {
						"This project has unsaved changes.",
						"Do you want to save the changes before closing the tab?"
				}, "Save Changes?", JOptionPane.YES_NO_CANCEL_OPTION);
				
				if (option == JOptionPane.CANCEL_OPTION) {
					return false;
				} else if (option == JOptionPane.YES_OPTION) {
					return saveProjectAndClosePage();
				} else {
					return closePage();
				}
			} else {
				return closePage();
			}
		}
		
		private boolean saveProjectAndClosePage() {
			Project p = workspaceSupplier.get().getProjectByUuid(project.getUuid());
			overviewPage.saveInto(p);
			return closePage();
		}

		private boolean closePage() {
			Page page = new Page(project.getUuid(), PageType.OVERVIEW);
			List<Page> pages = workspaceSupplier.get().getPages();
			
			try {
				pages.remove(page);
				workspaceSupplier.saveWorkspace();						
				return true;
			} catch (IOException e) {
				pages.add(index, page);
				
				JOptionPane.showMessageDialog(tabbedPane, new String[] {
						"An error occurred while trying to save the workspace.",
						"Please try again!"},
						"Save Workspace", JOptionPane.INFORMATION_MESSAGE);
				
				return false;
			}
		}
		
		@Override
		public boolean selectTabBeforeClosing(int tabIndex) {
			if (tabIndex == index) {
				return overviewPage.isDirty();
			}
			
			return false;
		}		
	}
	
	private class StateMachineModelMultiPageTabClosingListener implements TabClosingListener {
		private final int index;
		private final Project project;
		private final StateMachineModelMultiPage stateMachineModelMultiPage;
		
		public StateMachineModelMultiPageTabClosingListener(final int index, final Project project,
				final StateMachineModelMultiPage stateMachineModelMultiPage) {
			Objects.requireNonNull(project, "project must not be null");
			Objects.requireNonNull(stateMachineModelMultiPage,
					"stateMachineModelMultiPage must not be null");
			
			this.index = index;
			this.project = project;
			this.stateMachineModelMultiPage = stateMachineModelMultiPage;
		}
		
		@Override
		public boolean tabClosing(int tabIndex) {
			if (tabIndex == index) {
				return doTabClosing();
			}
			
			return false;
		}
		
		private boolean doTabClosing() {
			if (stateMachineModelMultiPage.isDirty()) {
				int option = JOptionPane.showConfirmDialog(tabbedPane, new String[] {
						"This state machine model has unsaved changes.",
						"Do you want to save the changes before closing the tab?"
				}, "Save Changes?", JOptionPane.YES_NO_CANCEL_OPTION);
				
				if (option == JOptionPane.CANCEL_OPTION) {
					return false;
				} else if (option == JOptionPane.YES_OPTION) {
					return saveProjectAndClosePage();
				} else {
					return closePage();
				}
			} else {
				return closePage();
			}
		}
		
		private boolean saveProjectAndClosePage() {
			stateMachineModelMultiPage.save();
			return closePage();
		}

		private boolean closePage() {
			Page page = new Page(project.getUuid(), PageType.STATE_MACHINE_MODEL);
			List<Page> pages = workspaceSupplier.get().getPages();
			
			try {
				pages.remove(page);
				workspaceSupplier.saveWorkspace();						
				return true;
			} catch (IOException e) {
				pages.add(index, page);
				
				JOptionPane.showMessageDialog(tabbedPane, new String[] {
						"An error occurred while trying to save the workspace.",
						"Please try again!"},
						"Save Workspace", JOptionPane.INFORMATION_MESSAGE);
				
				return false;
			}
		}
		
		@Override
		public boolean selectTabBeforeClosing(int tabIndex) {
			if (tabIndex == index) {
				return stateMachineModelMultiPage.isDirty();
			}
			
			return false;
		}
	}
	
	private JTabbedPane tabbedPane;
	private SaveableWorkspaceSupplier workspaceSupplier;
	
	public void initialize(final JTabbedPane tabbedPane,
			final SaveableWorkspaceSupplier workspaceSupplier) {
		Objects.requireNonNull(tabbedPane, "tabbedPane must not be null");
		Objects.requireNonNull(workspaceSupplier, "workspaceSupplier must not be null");
		
		this.tabbedPane = tabbedPane;
		this.workspaceSupplier = workspaceSupplier;
		
		tabbedPane.addChangeListener(event -> {
			Workspace workspace = workspaceSupplier.get();
			int selectedPageIndex = workspace.getSelectedPageIndex();
			
			try {
				workspace.setSelectedPageIndex(tabbedPane.getSelectedIndex());
				workspaceSupplier.saveWorkspace();
			} catch (IOException e) {
				workspace.setSelectedPageIndex(selectedPageIndex);
				JOptionPane.showMessageDialog(tabbedPane, new String[] {
						"An error occurred while trying to save the workspace.",
						"Please try again!"},
						"Save Workspace", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	public void showPageForPath(final TreePath selectedPath) {
		if (selectedPath.getPathCount() == 3) {
			ProjectNameAndPath projectNameAndPath = (ProjectNameAndPath)
					((TreeNode) selectedPath.getPathComponent(1)).getUserObject();
			String nodeCaption = (String)
					((TreeNode) selectedPath.getLastPathComponent()).getUserObject();
			Project project = workspaceSupplier.get().getProjectByUuid(projectNameAndPath.getUuid());
			
			switch (nodeCaption) {
				case "Project Overview":
					showOrSelectPage(project, PageType.OVERVIEW);
					break;
				case "State Machine Model":
					showOrSelectPage(project, PageType.STATE_MACHINE_MODEL);
					break;
			}
		}
	}

	private void showOrSelectPage(final Project project, final PageType pageType) {
		Page page = new Page(project.getUuid(), pageType);
		List<Page> pages = workspaceSupplier.get().getPages();
		
		if (pages.contains(page)) {
			tabbedPane.setSelectedIndex(pages.indexOf(page));
		} else {
			try {
				pages.add(page);
				workspaceSupplier.saveWorkspace();
				showPage(project, pageType);
			} catch (IOException e) {
				pages.remove(page);
				JOptionPane.showMessageDialog(tabbedPane, new String[] {
						"An error occurred while trying to save the workspace.",
						"Please try again!"},
						"Save Workspace", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	public void showPage(final Project project, final PageType pageType) {
		switch (pageType) {
			case OVERVIEW:
				showOverviewPage(project);
				break;
			case STATE_MACHINE_MODEL:
				showStateMachineModelPage(project);
				break;
		}
	}

	private void showOverviewPage(final Project project) {
		addNameAndDescriptionFromPOM(project);
		
		String tabTitle = project.getPath() + " - Project Overview";
		
		OverviewPage page = new OverviewPage(project);
		tabbedPane.addTab(tabTitle, page);
		
		int index = tabbedPane.getTabCount() - 1;
		TabClosingListener listener = new OverviewPageTabClosingListener(index, project, page);
		
		tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane, listener));
		tabbedPane.setSelectedIndex(index);
		page.addDirtyListener(event -> tabbedPane.setTitleAt(index, "* " + tabTitle));
	}

	private void addNameAndDescriptionFromPOM(final Project project) {
		if (project.isMavenProject()) {
			//read project name and description from POM
			String pomFile = String.format("%s%spom.xml", project.getPath(), System.getProperty(
					"file.separator"));
			
			try (BufferedInputStream input = new BufferedInputStream(
					new FileInputStream(new File(pomFile)))) {
				Namespace namespace = Namespace.getNamespace("http://maven.apache.org/POM/4.0.0");
				
				SAXBuilder builder = new SAXBuilder(XMLReaders.NONVALIDATING);
				Element rootElement = builder.build(input).detachRootElement();
				
				String name = rootElement.getChildText("name", namespace);
				
				if (name != null) {
					project.setName(name);
				}
				
				String description = rootElement.getChildText("description", namespace);
				
				if (description != null) {
					project.setDescription(description);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void showStateMachineModelPage(final Project project) {		
		String tabTitle = project.getPath() + " - State Machine Model";
		
		StateMachineModelMultiPage page = new StateMachineModelMultiPage(project);
		tabbedPane.addTab(tabTitle, page);
		
		int index = tabbedPane.getTabCount() - 1;
		TabClosingListener listener = new StateMachineModelMultiPageTabClosingListener(
				index, project, page);
		
		tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane, listener));
		tabbedPane.setSelectedIndex(index);
		page.addDirtyListener(event -> tabbedPane.setTitleAt(index, "* " + tabTitle));
	}

	public void clearPages() {
		tabbedPane.removeAll();
	}
	
	public void selectPage(final int pageIndex) {
		tabbedPane.setSelectedIndex(pageIndex);
	}
}
