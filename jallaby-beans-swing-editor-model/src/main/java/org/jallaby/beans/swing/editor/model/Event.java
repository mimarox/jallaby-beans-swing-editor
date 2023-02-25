package org.jallaby.beans.swing.editor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Event {
	private String name;
	private Event eventExtends;
	private List<Event> children = new ArrayList<>();
	private EventModifier eventModifier;
	private List<Property> properties = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public Event getEventExtends() {
		return eventExtends;
	}
	
	public void setEventExtends(final Event eventExtends) {
		this.eventExtends = eventExtends;
	}
	
	public boolean addChildEvent(final Event event) {
		return children.add(event);
	}
	
	public boolean hasChildren() {
		return children.size() > 0;
	}
	
	public boolean removeChildEvent(final Event event) {
		return children.remove(event);
	}
	
	public List<Event> getChildren() {
		return Collections.unmodifiableList(children);
	}
	
	public EventModifier getEventModifier() {
		return eventModifier;
	}
	
	public void setEventModifier(final EventModifier eventModifier) {
		this.eventModifier = eventModifier;
	}
	
	public boolean addProperty(final Property property) {
		return properties.add(property);
	}
	
	public boolean removeProperty(final Property property) {
		return properties.remove(property);
	}
	
	public List<Property> getProperties() {
		return Collections.unmodifiableList(properties);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
