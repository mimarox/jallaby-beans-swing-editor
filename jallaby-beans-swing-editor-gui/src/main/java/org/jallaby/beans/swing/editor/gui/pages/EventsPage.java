package org.jallaby.beans.swing.editor.gui.pages;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jallaby.beans.swing.editor.gui.event.DirtyEvent;
import org.jallaby.beans.swing.editor.gui.event.DirtyListener;
import org.jallaby.beans.swing.editor.model.Event;
import org.jallaby.beans.swing.editor.model.EventModifier;
import org.jallaby.beans.swing.editor.model.StateMachine;
import org.jallaby.beans.swing.editor.model.workspace.Project;

public class EventsPage extends JPanel implements Dirtyable {
	private static final long serialVersionUID = 1L;

	private final List<DirtyListener> dirtyListeners = new ArrayList<>();
	
	private boolean dirty;
	private JTextField eventNameTextField;
	private JTable eventsTable;
	private JTable eventPropertiesTable;
	
	public EventsPage(final Project project, final StateMachine stateMachine) {
		setLayout(new BorderLayout(0, 0));
		
		JLabel eventsLabel = new JLabel("Events");
		eventsLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(eventsLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 
				0.0, 1.0, 0.0, 0.0, 1.0};
		panel.setLayout(gbl_panel);
		
		JLabel newEventLabel = new JLabel("New Event");
		newEventLabel.setHorizontalAlignment(SwingConstants.LEFT);
		newEventLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_newEventLabel = new GridBagConstraints();
		gbc_newEventLabel.insets = new Insets(0, 0, 5, 5);
		gbc_newEventLabel.gridx = 0;
		gbc_newEventLabel.gridy = 0;
		panel.add(newEventLabel, gbc_newEventLabel);
		
		JLabel eventNameLabel = new JLabel("Event Name:");
		eventNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_eventNameLabel = new GridBagConstraints();
		gbc_eventNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_eventNameLabel.anchor = GridBagConstraints.EAST;
		gbc_eventNameLabel.gridx = 0;
		gbc_eventNameLabel.gridy = 1;
		panel.add(eventNameLabel, gbc_eventNameLabel);
		
		eventNameTextField = new JTextField();
		GridBagConstraints gbc_eventNameTextField = new GridBagConstraints();
		gbc_eventNameTextField.gridwidth = 2;
		gbc_eventNameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_eventNameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_eventNameTextField.gridx = 1;
		gbc_eventNameTextField.gridy = 1;
		panel.add(eventNameTextField, gbc_eventNameTextField);
		eventNameTextField.setColumns(10);
		
		JLabel eventExtendsLabel = new JLabel("Extends:");
		eventExtendsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_eventExtendsLabel = new GridBagConstraints();
		gbc_eventExtendsLabel.anchor = GridBagConstraints.EAST;
		gbc_eventExtendsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_eventExtendsLabel.gridx = 0;
		gbc_eventExtendsLabel.gridy = 2;
		panel.add(eventExtendsLabel, gbc_eventExtendsLabel);
		
		Vector<Event> eventsToExtend = new Vector<>(stateMachine.getEvents());
		eventsToExtend.add(null);
		
		JComboBox<Event> eventExtendsComboBox = new JComboBox<>(eventsToExtend);
		GridBagConstraints gbc_eventExtendsComboBox = new GridBagConstraints();
		gbc_eventExtendsComboBox.gridwidth = 2;
		gbc_eventExtendsComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_eventExtendsComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_eventExtendsComboBox.gridx = 1;
		gbc_eventExtendsComboBox.gridy = 2;
		panel.add(eventExtendsComboBox, gbc_eventExtendsComboBox);
		
		JLabel eventModifierLabel = new JLabel("Modifier:");
		eventModifierLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_eventModifierLabel = new GridBagConstraints();
		gbc_eventModifierLabel.insets = new Insets(0, 0, 5, 5);
		gbc_eventModifierLabel.gridx = 0;
		gbc_eventModifierLabel.gridy = 3;
		panel.add(eventModifierLabel, gbc_eventModifierLabel);
		
		JCheckBox abstractEventCheckBox = new JCheckBox("Abstract");
		GridBagConstraints gbc_abstractEventCheckBox = new GridBagConstraints();
		gbc_abstractEventCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_eventModifierLabel.insets = new Insets(0, 0, 0, 5);
		gbc_abstractEventCheckBox.gridx = 1;
		gbc_abstractEventCheckBox.gridy = 3;
		panel.add(abstractEventCheckBox, gbc_abstractEventCheckBox);
		
		JCheckBox finalEventCheckBox = new JCheckBox("Final");
		GridBagConstraints gbc_finalEventCheckBox = new GridBagConstraints();
		gbc_finalEventCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_finalEventCheckBox.gridx = 2;
		gbc_finalEventCheckBox.gridy = 3;
		panel.add(finalEventCheckBox, gbc_finalEventCheckBox);
		
		abstractEventCheckBox.addActionListener(e -> {
			if (abstractEventCheckBox.isSelected()) {
				finalEventCheckBox.setEnabled(false);
			} else {
				finalEventCheckBox.setEnabled(true);
			}
		});
		
		finalEventCheckBox.addActionListener(e -> {
			if (finalEventCheckBox.isSelected()) {
				abstractEventCheckBox.setEnabled(false);
			} else {
				abstractEventCheckBox.setEnabled(true);
			}
		});
		
		JButton createEventButton = new JButton("Create Event");
		GridBagConstraints gbc_createEventButton = new GridBagConstraints();
		gbc_createEventButton.insets = new Insets(0, 0, 5, 5);
		gbc_createEventButton.gridx = 1;
		gbc_createEventButton.gridy = 4;
		panel.add(createEventButton, gbc_createEventButton);
		
		createEventButton.addActionListener(e -> {
			Event event = new Event();
			event.setName(eventNameTextField.getText());
			
			Event eventExtends = (Event) eventExtendsComboBox.getSelectedItem();
			
			event.setEventExtends(eventExtends);
			eventExtends.addChildEvent(event);
			
			if (abstractEventCheckBox.isSelected()) {
				event.setEventModifier(EventModifier.ABSTRACT);
			} else if (finalEventCheckBox.isSelected()) {
				event.setEventModifier(EventModifier.FINAL);
			}

			stateMachine.addEvent(event);
			
			fireDirty();
			
			if (!finalEventCheckBox.isSelected()) {
				eventsToExtend.add(event);	
			}
			
			//TODO Update events table
			
			eventNameTextField.setText("");
			eventExtendsComboBox.setSelectedItem(null);
			abstractEventCheckBox.setEnabled(true);
			abstractEventCheckBox.setSelected(false);
			finalEventCheckBox.setEnabled(true);
			finalEventCheckBox.setSelected(false);
		});
		
		JButton clearButton = new JButton("Clear");
		GridBagConstraints gbc_clearButton = new GridBagConstraints();
		gbc_clearButton.insets = new Insets(0, 0, 5, 5);
		gbc_clearButton.gridx = 2;
		gbc_clearButton.gridy = 4;
		panel.add(clearButton, gbc_clearButton);
		
		clearButton.addActionListener(e -> {
			eventNameTextField.setText("");
			eventExtendsComboBox.setSelectedItem(null);
			abstractEventCheckBox.setEnabled(true);
			abstractEventCheckBox.setSelected(false);
			finalEventCheckBox.setEnabled(true);
			finalEventCheckBox.setSelected(false);
		});
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 5;
		panel.add(verticalStrut, gbc_verticalStrut);
		
		JLabel eventsTableLabel = new JLabel("Events");
		eventsTableLabel.setHorizontalAlignment(SwingConstants.LEFT);
		eventsTableLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_eventsTableLabel = new GridBagConstraints();
		gbc_eventsTableLabel.insets = new Insets(0, 0, 5, 5);
		gbc_eventsTableLabel.gridx = 0;
		gbc_eventsTableLabel.gridy = 6;
		panel.add(eventsTableLabel, gbc_eventsTableLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 7;
		panel.add(scrollPane, gbc_scrollPane);
		
		eventsTable = new JTable();
		scrollPane.setViewportView(eventsTable);
		
		Component verticalStrut2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut2 = new GridBagConstraints();
		gbc_verticalStrut2.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut2.gridx = 0;
		gbc_verticalStrut2.gridy = 8;
		panel.add(verticalStrut2, gbc_verticalStrut2);
		
		JLabel eventPropertiesLabel = new JLabel("Event Properties");
		eventPropertiesLabel.setHorizontalAlignment(SwingConstants.LEFT);
		eventPropertiesLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_eventPropertiesLabel = new GridBagConstraints();
		gbc_eventPropertiesLabel.gridwidth = 2;
		gbc_eventPropertiesLabel.insets = new Insets(0, 0, 5, 5);
		gbc_eventPropertiesLabel.gridx = 0;
		gbc_eventPropertiesLabel.gridy = 9;
		panel.add(eventPropertiesLabel, gbc_eventPropertiesLabel);
		
		JButton newPropertyButton = new JButton("New Property");
		GridBagConstraints gbc_newPropertyButton = new GridBagConstraints();
		gbc_newPropertyButton.insets = new Insets(0, 0, 5, 5);
		gbc_newPropertyButton.gridx = 2;
		gbc_newPropertyButton.gridy = 9;
		panel.add(newPropertyButton, gbc_newPropertyButton);
		
		JButton deletePropertyButton = new JButton("Delete Property");
		deletePropertyButton.setEnabled(false);
		GridBagConstraints gbc_deletePropertyButton = new GridBagConstraints();
		gbc_deletePropertyButton.insets = new Insets(0, 0, 5, 0);
		gbc_deletePropertyButton.gridx = 3;
		gbc_deletePropertyButton.gridy = 9;
		panel.add(deletePropertyButton, gbc_deletePropertyButton);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 4;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 10;
		panel.add(scrollPane_1, gbc_scrollPane_1);
		
		eventPropertiesTable = new JTable();
		scrollPane_1.setViewportView(eventPropertiesTable);
	}

	@Override
	public void addDirtyListener(final DirtyListener listener) {
		Objects.requireNonNull(listener, "listener must not be null");
		dirtyListeners.add(listener);
	}

	@Override
	public void removeDirtyListener(final DirtyListener listener) {
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
}
