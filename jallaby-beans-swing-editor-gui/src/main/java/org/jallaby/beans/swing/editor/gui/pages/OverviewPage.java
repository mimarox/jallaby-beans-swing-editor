package org.jallaby.beans.swing.editor.gui.pages;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jallaby.beans.swing.editor.gui.event.DirtyEvent;
import org.jallaby.beans.swing.editor.gui.event.DirtyListener;
import org.jallaby.beans.swing.editor.model.workspace.Project;

public class OverviewPage extends JPanel implements Dirtyable {
    private static final long serialVersionUID = 1L;
	
	private class DefaultKeyListener extends KeyAdapter {

		@Override
		public void keyTyped(KeyEvent e) {
			fireDirty();
		}
	}
	
    private final List<DirtyListener> dirtyListeners = new ArrayList<>();
	
	private boolean dirty;
	
	private JTextField pathTextField;
	private JTextField sourceFolderTextField;
	private JTextField statesPackageTextField;
	private JTextField transitionsPackageTextField;
	private JTextField stateMachineXmlPathTextField;
	private JTextField nameTextField;
	private JTextField descriptionTextField;
	
	public OverviewPage(final Project project) {
		Objects.requireNonNull(project, "project must not be null");
		
		setLayout(new BorderLayout(0, 0));
		
		JLabel projectOverviewLabel = new JLabel("Project Overview", JLabel.CENTER);
		projectOverviewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(projectOverviewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 1};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MAX_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MAX_VALUE};
		panel.setLayout(gbl_panel);
		
		JCheckBox mavenProjectCheckbox = new JCheckBox("Maven Project");
		mavenProjectCheckbox.setHorizontalAlignment(SwingConstants.LEFT);
		mavenProjectCheckbox.setEnabled(false);
		mavenProjectCheckbox.setSelected(project.isMavenProject());
		GridBagConstraints gbc_mavenProjectCheckbox = new GridBagConstraints();
		gbc_mavenProjectCheckbox.gridwidth = 2;
		gbc_mavenProjectCheckbox.insets = new Insets(0, 0, 5, 0);
		gbc_mavenProjectCheckbox.gridx = 0;
		gbc_mavenProjectCheckbox.gridy = 0;
		panel.add(mavenProjectCheckbox, gbc_mavenProjectCheckbox);
		
		JLabel pathLabel = new JLabel("Path:");
		pathLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_pathLabel = new GridBagConstraints();
		gbc_pathLabel.insets = new Insets(0, 0, 5, 5);
		gbc_pathLabel.anchor = GridBagConstraints.EAST;
		gbc_pathLabel.gridx = 0;
		gbc_pathLabel.gridy = 1;
		panel.add(pathLabel, gbc_pathLabel);
		
		pathTextField = new JTextField();
		pathTextField.setEditable(false);
		GridBagConstraints gbc_pathTextField = new GridBagConstraints();
		gbc_pathTextField.insets = new Insets(0, 0, 5, 0);
		gbc_pathTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_pathTextField.gridx = 1;
		gbc_pathTextField.gridy = 1;
		panel.add(pathTextField, gbc_pathTextField);
		pathTextField.setColumns(100);
		pathTextField.setText(project.getPath());
		
		JLabel sourceFolderLabel = new JLabel("Source folder:");
		sourceFolderLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_sourceFolderLabel = new GridBagConstraints();
		gbc_sourceFolderLabel.anchor = GridBagConstraints.EAST;
		gbc_sourceFolderLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sourceFolderLabel.gridx = 0;
		gbc_sourceFolderLabel.gridy = 2;
		panel.add(sourceFolderLabel, gbc_sourceFolderLabel);
		
		sourceFolderTextField = new JTextField();
		sourceFolderTextField.addKeyListener(new DefaultKeyListener());
		GridBagConstraints gbc_sourceFolderTextField = new GridBagConstraints();
		gbc_sourceFolderTextField.insets = new Insets(0, 0, 5, 0);
		gbc_sourceFolderTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_sourceFolderTextField.gridx = 1;
		gbc_sourceFolderTextField.gridy = 2;
		panel.add(sourceFolderTextField, gbc_sourceFolderTextField);
		sourceFolderTextField.setColumns(100);
		sourceFolderTextField.setText(project.getSourceFolder());
		
		JLabel statesPackageLabel = new JLabel("States package:");
		statesPackageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_statesPackageLabel = new GridBagConstraints();
		gbc_statesPackageLabel.anchor = GridBagConstraints.EAST;
		gbc_statesPackageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_statesPackageLabel.gridx = 0;
		gbc_statesPackageLabel.gridy = 3;
		panel.add(statesPackageLabel, gbc_statesPackageLabel);
		
		statesPackageTextField = new JTextField();
		statesPackageTextField.addKeyListener(new DefaultKeyListener());
		GridBagConstraints gbc_statesPackageTextField = new GridBagConstraints();
		gbc_statesPackageTextField.insets = new Insets(0, 0, 5, 0);
		gbc_statesPackageTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_statesPackageTextField.gridx = 1;
		gbc_statesPackageTextField.gridy = 3;
		panel.add(statesPackageTextField, gbc_statesPackageTextField);
		statesPackageTextField.setColumns(100);
		statesPackageTextField.setText(project.getStatesPackage());
		
		JLabel transitionsPackageLabel = new JLabel("Transitions package:");
		transitionsPackageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_transitionsPackageLabel = new GridBagConstraints();
		gbc_transitionsPackageLabel.anchor = GridBagConstraints.EAST;
		gbc_transitionsPackageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_transitionsPackageLabel.gridx = 0;
		gbc_transitionsPackageLabel.gridy = 4;
		panel.add(transitionsPackageLabel, gbc_transitionsPackageLabel);
		
		transitionsPackageTextField = new JTextField();
		transitionsPackageTextField.addKeyListener(new DefaultKeyListener());
		GridBagConstraints gbc_transitionsPackageTextField = new GridBagConstraints();
		gbc_transitionsPackageTextField.insets = new Insets(0, 0, 5, 0);
		gbc_transitionsPackageTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_transitionsPackageTextField.gridx = 1;
		gbc_transitionsPackageTextField.gridy = 4;
		panel.add(transitionsPackageTextField, gbc_transitionsPackageTextField);
		transitionsPackageTextField.setColumns(100);
		transitionsPackageTextField.setText(project.getTransitionsPackage());
		
		JLabel stateMachineXmlPathLabel = new JLabel("State machine XML path:");
		stateMachineXmlPathLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_stateMachineXmlPathLabel = new GridBagConstraints();
		gbc_stateMachineXmlPathLabel.anchor = GridBagConstraints.EAST;
		gbc_stateMachineXmlPathLabel.insets = new Insets(0, 0, 5, 5);
		gbc_stateMachineXmlPathLabel.gridx = 0;
		gbc_stateMachineXmlPathLabel.gridy = 5;
		panel.add(stateMachineXmlPathLabel, gbc_stateMachineXmlPathLabel);
		
		stateMachineXmlPathTextField = new JTextField();
		statesPackageTextField.addKeyListener(new DefaultKeyListener());
		GridBagConstraints gbc_stateMachineXmlPathTextField = new GridBagConstraints();
		gbc_stateMachineXmlPathTextField.insets = new Insets(0, 0, 5, 0);
		gbc_stateMachineXmlPathTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_stateMachineXmlPathTextField.gridx = 1;
		gbc_stateMachineXmlPathTextField.gridy = 5;
		panel.add(stateMachineXmlPathTextField, gbc_stateMachineXmlPathTextField);
		stateMachineXmlPathTextField.setColumns(100);
		stateMachineXmlPathTextField.setText(project.getStateMachineXmlPath());
		
		JLabel stateMachineXmlPathSuffixLabel = new JLabel("/META-INF/state-machine.xml");
		stateMachineXmlPathSuffixLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_stateMachineXmlPathSuffixLabel = new GridBagConstraints();
		gbc_stateMachineXmlPathSuffixLabel.anchor = GridBagConstraints.EAST;
		gbc_stateMachineXmlPathSuffixLabel.insets = new Insets(0, 0, 5, 5);
		gbc_stateMachineXmlPathSuffixLabel.gridx = 2;
		gbc_stateMachineXmlPathSuffixLabel.gridy = 5;
		panel.add(stateMachineXmlPathSuffixLabel, gbc_stateMachineXmlPathSuffixLabel);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.EAST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 6;
		panel.add(nameLabel, gbc_nameLabel);
		
		nameTextField = new JTextField();
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 1;
		gbc_nameTextField.gridy = 6;
		panel.add(nameTextField, gbc_nameTextField);
		nameTextField.setColumns(100);
		nameTextField.setText(project.getName());
		
		if (project.isMavenProject()) {
			nameTextField.setEditable(false);
		} else {
			nameTextField.addKeyListener(new DefaultKeyListener());
		}
		
		JLabel descriptionLabel = new JLabel("Description:");
		descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_descriptionLabel = new GridBagConstraints();
		gbc_descriptionLabel.anchor = GridBagConstraints.EAST;
		gbc_descriptionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_descriptionLabel.gridx = 0;
		gbc_descriptionLabel.gridy = 7;
		panel.add(descriptionLabel, gbc_descriptionLabel);
		
		descriptionTextField = new JTextField();
		GridBagConstraints gbc_descriptionTextField = new GridBagConstraints();
		gbc_descriptionTextField.insets = new Insets(0, 0, 5, 0);
		gbc_descriptionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_descriptionTextField.gridx = 1;
		gbc_descriptionTextField.gridy = 7;
		panel.add(descriptionTextField, gbc_descriptionTextField);
		descriptionTextField.setColumns(100);
		descriptionTextField.setText(project.getDescription());
		
		if (project.isMavenProject()) {
			descriptionTextField.setEditable(false);
		} else {
			descriptionTextField.addKeyListener(new DefaultKeyListener());
		}
	}

	@Override
	public void addDirtyListener(DirtyListener listener) {
		Objects.requireNonNull(listener, "listener must not be null");
		dirtyListeners.add(listener);
	}

	@Override
	public void removeDirtyListener(DirtyListener listener) {
		dirtyListeners.remove(listener);
	}
	
	private void fireDirty() {
		dirty = true;
		DirtyEvent event = new DirtyEvent();
		
		for (DirtyListener listener : dirtyListeners) {
			listener.pageDirty(event);
		}
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	@Override
	public void setDirty(final boolean dirty) {
		this.dirty = dirty;
	}

	public void saveInto(final Project project) {
		project.setSourceFolder(sourceFolderTextField.getText().trim());
		project.setStateMachineXmlPath(stateMachineXmlPathTextField.getText().trim());
		project.setStatesPackage(statesPackageTextField.getText().trim());
		project.setTransitionsPackage(transitionsPackageTextField.getText().trim());
		
		if (project.isMavenProject()) {
			project.setDescription(null);
			project.setName(null);
		} else {
			project.setDescription(descriptionTextField.getText().trim());
			project.setName(nameTextField.getText().trim());
		}
	}
}
