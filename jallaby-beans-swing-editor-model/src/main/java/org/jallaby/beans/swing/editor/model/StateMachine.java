package org.jallaby.beans.swing.editor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StateMachine {
	private List<Event> events = new ArrayList<>();
	
	public boolean addEvent(final Event event) {
		return events.add(event);
	}
	
	public boolean removeEvent(final Event event) {
		return events.remove(event);
	}
	
	public List<Event> getEvents() {
		return Collections.unmodifiableList(events);
	}
}
