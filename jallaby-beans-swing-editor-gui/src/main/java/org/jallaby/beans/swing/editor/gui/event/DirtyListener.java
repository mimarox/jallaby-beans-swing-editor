package org.jallaby.beans.swing.editor.gui.event;

@FunctionalInterface
public interface DirtyListener {
	void pageDirty(DirtyEvent event);
}
