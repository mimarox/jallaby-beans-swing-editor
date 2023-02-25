package org.jallaby.beans.swing.editor.gui.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTabbedPane;

import org.jallaby.beans.swing.editor.gui.event.DirtyEvent;
import org.jallaby.beans.swing.editor.gui.event.DirtyListener;
import org.jallaby.beans.swing.editor.model.StateMachine;
import org.jallaby.beans.swing.editor.model.workspace.Project;

public class StateMachineModelMultiPage extends JTabbedPane implements Dirtyable {
	private static final long serialVersionUID = -9187344200041348847L;

	private final EventsPage eventsPage;
	private final StatesPage statesPage;
	
	private final List<DirtyListener> dirtyListeners = new ArrayList<>();
	private boolean dirty;

	public StateMachineModelMultiPage(final Project project) {
		super(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);
		
		Objects.requireNonNull(project, "project must not be null");
		
		DirtyListener listener = event -> fireDirty();
		
		StateMachine stateMachine = loadStateMachine(project);
		
		eventsPage = new EventsPage(project, stateMachine);
		eventsPage.addDirtyListener(listener);
		addTab("Events", eventsPage);
		
		statesPage = new StatesPage(project);
		statesPage.addDirtyListener(listener);
		addTab("States", statesPage);
	}

	private StateMachine loadStateMachine(final Project project) {
		// TODO Auto-generated method stub
		return new StateMachine();
	}

	@Override
	public void addDirtyListener(final DirtyListener listener) {
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

	public void save() {
		// TODO Auto-generated method stub
		
	}
}
