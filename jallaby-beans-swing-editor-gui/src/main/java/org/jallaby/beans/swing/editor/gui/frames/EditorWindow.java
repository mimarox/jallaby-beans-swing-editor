package org.jallaby.beans.swing.editor.gui.frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Objects;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.TreeSelectionModel;

import org.jallaby.beans.swing.editor.gui.pages.PagesManager;
import org.jallaby.beans.swing.editor.gui.tree.TreeManager;
import org.jallaby.beans.swing.editor.gui.tree.TreeModel;
import org.jallaby.beans.swing.editor.gui.tree.TreeSelectionHandler;
import org.jallaby.beans.swing.editor.gui.util.GUIUtils;
import org.jallaby.beans.swing.editor.model.config.Configuration;
import org.jallaby.beans.swing.editor.model.workspace.Page;
import org.jallaby.beans.swing.editor.model.workspace.PageType;
import org.jallaby.beans.swing.editor.model.workspace.Project;
import org.jallaby.beans.swing.editor.model.workspace.SaveableWorkspaceSupplier;
import org.jallaby.beans.swing.editor.model.workspace.Workspace;

import net.sf.jetro.object.ObjectMapper;
import net.sf.jetro.object.serializer.SerializationContext;

public class EditorWindow extends JFrame {
	private class ExitHandler extends WindowAdapter implements ActionListener {
		
		@Override
		public void windowClosing(WindowEvent e){
			exit();
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			exit();
		}
		
		private void exit(){
			System.exit(0);
		}
	}
	
	private class WorkspaceFileFilter extends FileFilter {

		@Override
		public boolean accept(File pathname) {
			return pathname.isDirectory() || pathname.getAbsolutePath().endsWith(getFileExtension());
		}
		
		public String getFileExtension() {
			return ".ws";
		}

		@Override
		public String getDescription() {
			return "Workspace files";
		}
	}
	
	private class NewWorkspaceActionListener implements ActionListener {
		private Component parent;
		
		public NewWorkspaceActionListener(final Component parent) {
			Objects.requireNonNull(parent, "parent must not be null");
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			WorkspaceFileFilter fileFilter = new WorkspaceFileFilter();
			
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("New Workspace");
			chooser.setApproveButtonMnemonic(KeyEvent.VK_S);
			chooser.setApproveButtonToolTipText("Create new workspace");
			chooser.setFileFilter(fileFilter);
			
			if(chooser.showSaveDialog(EditorWindow.this) == JFileChooser.APPROVE_OPTION) {
				Cursor cursor = parent.getCursor();
				parent.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				
				try {
					File file = chooser.getSelectedFile();
					
					// Add file extension if necessary
					String fileName = file.getAbsolutePath();
					String fileExtension = fileFilter.getFileExtension();
					String regex = "^.*?" + fileExtension + "$";
					
					if (!fileName.matches(regex)) {
						file = new File(fileName + fileExtension);
					}
					
					if (!file.exists()) {
						createAndLoadWorkspace(file);
					} else {
						int choice = JOptionPane.showConfirmDialog(parent, 
								"The chosen file already exists.\n" +
								"Should it be overwritten?", 
								" - Create New Workspace",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE);
						
						if (choice == JOptionPane.YES_OPTION) {
							file.delete();
							createAndLoadWorkspace(file);
						} else if (choice == JOptionPane.NO_OPTION) {
							actionPerformed(null);
						}
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(parent, new String[] {
							"An error occurred while trying to create the new workspace.",
							"Please try again!"},
							"New Workspace", JOptionPane.INFORMATION_MESSAGE);
				} finally {
					parent.setCursor(cursor);
				}
			}
		}

		private void createAndLoadWorkspace(final File file) throws IOException {
			Workspace workspace = new Workspace();
			writeWorkspace(workspace, file);
			
			configuration.setWorkspace(file.getAbsolutePath());
			writeConfiguration(configuration);
			
			loadWorkspace();
		}
	}
	
	private class SwitchWorkspaceActionListener implements ActionListener {
		private Component parent;
		
		public SwitchWorkspaceActionListener(final Component parent) {
			Objects.requireNonNull(parent, "parent must not be null");
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			WorkspaceFileFilter fileFilter = new WorkspaceFileFilter();
			
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("New Workspace");
			chooser.setApproveButtonMnemonic(KeyEvent.VK_S);
			chooser.setApproveButtonToolTipText("Create new workspace");
			chooser.setFileFilter(fileFilter);
			
			if(chooser.showOpenDialog(EditorWindow.this) == JFileChooser.APPROVE_OPTION) {
				Cursor cursor = parent.getCursor();
				parent.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				
				try {
					File file = chooser.getSelectedFile();
					
					// Add file extension if necessary
					String fileName = file.getAbsolutePath();
					String fileExtension = fileFilter.getFileExtension();
					String regex = "^.*?" + fileExtension + "$";
					
					if (!fileName.matches(regex)) {
						file = new File(fileName + fileExtension);
					}
					
					switchAndLoadWorkspace(file);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(parent, new String[] {
							"An error occurred while trying to switch the workspace.",
							"Please try again!"},
							"Switch Workspace", JOptionPane.INFORMATION_MESSAGE);
				} finally {
					parent.setCursor(cursor);
				}
			}
		}

		private void switchAndLoadWorkspace(File file) throws IOException {
			configuration.setWorkspace(file.getAbsolutePath());
			writeConfiguration(configuration);
			loadWorkspace();
		}
	}
	
	private class ImportProjectActionListener implements ActionListener {
		private Component parent;
		
		public ImportProjectActionListener(final Component parent) {
			Objects.requireNonNull(parent, "parent must not be null");
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
				File directory = chooser.getSelectedFile();
				
				try {
					importProjectFromDirectory(directory);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(parent, new String[] {
							"An error occurred while trying to import a project into the workspace.",
							"Please try again!"},
							"Import Project", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

		private void importProjectFromDirectory(final File directory) throws IOException {
			boolean mavenProject = isMavenProject(directory);
			
			Project project = new Project();
			project.setPath(directory.getAbsolutePath());
			project.setUuid(UUID.randomUUID());
			project.setMavenProject(mavenProject);
			
			if (mavenProject) {
				project.setSourceFolder("src/main/java");
				project.setStateMachineXmlPath("src/main/resources");
				project.setStatesPackage(determineStatesPackage(
						directory, project.getSourceFolder()));
				project.setTransitionsPackage(determineTransitionsPackage(
						directory, project.getSourceFolder()));
			}
			
			workspace.getProjects().add(project);
			workspace.getPages().add(new Page(project.getUuid(), PageType.OVERVIEW));
			workspace.getPages().add(new Page(project.getUuid(), PageType.STATE_MACHINE_MODEL));
			writeWorkspace(workspace, getWorkspaceFile());
			
			treeManager.addProject(project);
			pagesManager.showPage(project, PageType.OVERVIEW);
			pagesManager.showPage(project, PageType.STATE_MACHINE_MODEL);
		}

		private boolean isMavenProject(final File directory) {
			return new File(String.format("%s%spom.xml", directory.getAbsolutePath(),
					System.getProperty("file.separator"))).exists();
		}

		private String determineStatesPackage(final File directory, final String sourceFolder) {
			return "statemachine.states";
		}

		private String determineTransitionsPackage(final File directory, final String sourceFolder) {
			return "statemachine.transitions";
		}
	}

	private class DefaultSaveableWorkspaceSupplier implements SaveableWorkspaceSupplier {

		@Override
		public Workspace get() {
			return workspace;
		}

		@Override
		public void saveWorkspace() throws IOException {
			writeWorkspace(workspace, getWorkspaceFile());
		}	
	}
	
	private static final long serialVersionUID = 1L;
	
	private ExitHandler exitHandler = new ExitHandler();
	
	private ImageIcon switchWorkspaceIcon;
	private ImageIcon importProjectIcon;
	private ImageIcon newWorkspaceIcon;
	
	private TreeManager treeManager = new TreeManager();
	private PagesManager pagesManager = new PagesManager();
	
	private JSplitPane mainPanel;
	private JScrollPane treeScrollPanel;
	private JTree projectsTree;
	private JTabbedPane tabbedPane;
	private JMenuItem exitMenuItem;
	private JMenuItem switchWorkspaceMenuItem;
	private JMenuItem importProjectMenuItem;
	private JMenuItem newWorkspaceMenuItem;

	private final ObjectMapper mapper = new ObjectMapper();
	private final SaveableWorkspaceSupplier workspaceSupplier = new DefaultSaveableWorkspaceSupplier();
	private Workspace workspace;
	private Configuration configuration;
	
	public EditorWindow() {
		GUIUtils.setDefaultLookAndFeel();
		
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("Jallaby Beans Editor");
		addWindowListener(exitHandler);
		
		loadIcons();
		
		setJMenuBar(createMenuBar());
		
		getContentPane().setLayout(new BorderLayout(0, 0));

		getContentPane().add(getToolBar(), BorderLayout.NORTH);
		getContentPane().add(getMainPanel(), BorderLayout.CENTER);
		
		loadWorkspace();
	}

	private void loadIcons() {
		newWorkspaceIcon = new ImageIcon(EditorWindow.class.getResource("/icons/page_world.png"));
		switchWorkspaceIcon = new ImageIcon(EditorWindow.class.getResource("/icons/arrow_switch.png"));
		importProjectIcon = new ImageIcon(EditorWindow.class.getResource("/icons/application_add.png"));
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		return menuBar;
	}
	
	private JMenu createFileMenu() {
		JMenu mnFile = new JMenu("File");
		mnFile.setMnemonic('f');
		
		mnFile.add(getNewWorkspaceMenuItem());
		mnFile.add(getSwitchWorkspaceMenuItem());
		mnFile.add(getImportProjectMenuItem());
		mnFile.add(getExitMenuItem());
		
		return mnFile;
	}
	
	private JMenuItem getNewWorkspaceMenuItem() {
		if (newWorkspaceMenuItem == null) {
			newWorkspaceMenuItem = new JMenuItem("New Workspace");
			newWorkspaceMenuItem.setIcon(newWorkspaceIcon);
			newWorkspaceMenuItem.setMnemonic('n');
			newWorkspaceMenuItem.addActionListener(new NewWorkspaceActionListener(this));
		}
		
		return newWorkspaceMenuItem;
	}
	
	private JMenuItem getSwitchWorkspaceMenuItem() {
		if (switchWorkspaceMenuItem == null) {
			switchWorkspaceMenuItem = new JMenuItem("Switch Workspace");			
			switchWorkspaceMenuItem.setIcon(switchWorkspaceIcon);
			switchWorkspaceMenuItem.setMnemonic('s');
			switchWorkspaceMenuItem.addActionListener(new SwitchWorkspaceActionListener(this));
		}
		
		return switchWorkspaceMenuItem;
	}
	
	private JMenuItem getImportProjectMenuItem() {
		if (importProjectMenuItem == null) {
			importProjectMenuItem = new JMenuItem("Import Project");
			importProjectMenuItem.setIcon(importProjectIcon);
			importProjectMenuItem.setMnemonic('i');
			importProjectMenuItem.addActionListener(new ImportProjectActionListener(this));
		}
		
		return importProjectMenuItem;
	}
	
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem("Exit");
			exitMenuItem.setMnemonic('e');
			KeyStroke keyStroke =
				KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK);
			exitMenuItem.setAccelerator(keyStroke);
			exitMenuItem.addActionListener(exitHandler);
		}
		
		return exitMenuItem;
	}
	
	private JToolBar getToolBar() {
		JToolBar toolBar = new JToolBar();
		
		JButton btnOpen = new JButton();
		btnOpen.setIcon(switchWorkspaceIcon);
		btnOpen.setMnemonic('o');
		toolBar.add(btnOpen);
		
		return toolBar;
	}
	
	private JSplitPane getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JSplitPane();
			mainPanel.setDividerLocation(200);
			mainPanel.setLeftComponent(getTreeScrollPanel());
			mainPanel.setRightComponent(getMainTabbedPane());
		}
		
		return mainPanel;
	}

	private JScrollPane getTreeScrollPanel() {
		if (treeScrollPanel == null) {
			treeScrollPanel = new JScrollPane();
			treeScrollPanel.setViewportView(getProjectsTree());
		}
		
		return treeScrollPanel;
	}
	
	private JTree getProjectsTree() {
		if (projectsTree == null) {
			TreeModel model = new TreeModel();
			
			projectsTree = new JTree(model);
			projectsTree.setEditable(false);
			projectsTree.getSelectionModel().
					setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			projectsTree.addTreeSelectionListener(
					new TreeSelectionHandler(this, pagesManager));
			
			treeManager.setProjectsTree(projectsTree);
		}
		
		return projectsTree;
	}

	private JTabbedPane getMainTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
			pagesManager.initialize(tabbedPane, workspaceSupplier);
		}
		
		return tabbedPane;
	}
	
	private void loadWorkspace() {
		Configuration configuration = getConfiguration();
		
		String location = configuration.getWorkspace() != null ?
				configuration.getWorkspace() : configuration.getDefaultWorkspace();
		
		Workspace workspace = getWorkspace(location);
		
		fillProjectsTree(workspace);
		openPages(workspace);
	}

	private File getConfigurationFile() {
		String configFile = String.format("%1$s%2$sconfig%2$sconfig.json",
				System.getProperty("user.dir"), System.getProperty("file.separator"));
		return new File(configFile);
	}
	
	private File getWorkspaceFile() {
		return new File(configuration.getWorkspace());
	}
	
	private Configuration getConfiguration() {
		try (FileInputStream stream = new FileInputStream(getConfigurationFile())) {
			ObjectMapper mapper = new ObjectMapper();
			configuration = mapper.fromJson(stream, Configuration.class);
		} catch (Exception e) {
			configuration = new Configuration();
		}
		
		return configuration;
	}
	
	private Workspace getWorkspace(final String location) {
		try (FileInputStream stream = new FileInputStream(new File(location))) {
			ObjectMapper mapper = new ObjectMapper();
			workspace = mapper.fromJson(stream, Workspace.class);
		} catch (Exception e) {
			workspace = new Workspace();
		}
		
		return workspace;
	}

	private void fillProjectsTree(final Workspace workspace) {
		treeManager.clearRoot();
		
		for (Project project : workspace.getProjects()) {
			treeManager.addProject(project);
		}
	}
	
	private void openPages(final Workspace workspace) {
		pagesManager.clearPages();
		
		int selectedPageIndex = workspace.getSelectedPageIndex();
		
		for (Page page : workspace.getPages()) {
			pagesManager.showPage(workspace.getProjectByUuid(page.getProjectUuid()),
					page.getPageType());
		}
		
		pagesManager.selectPage(selectedPageIndex);
	}
	
	private void writeConfiguration(final Configuration configuration) throws IOException {
		SerializationContext context = new SerializationContext();
		context.setIndent("\t");
		
		String json = mapper.toJson(configuration, context);
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				getConfigurationFile()), "UTF-8"));
		writer.write(json);
		writer.flush();
		writer.close();
	}
	
	private void writeWorkspace(final Workspace workspace, final File workspaceFile) throws IOException {
		SerializationContext context = new SerializationContext();
		context.setIndent("\t");
		
		String json = mapper.toJson(workspace, context);
		
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(workspaceFile), "UTF-8"));
		writer.write(json);
		writer.flush();
		writer.close();
	}
}
