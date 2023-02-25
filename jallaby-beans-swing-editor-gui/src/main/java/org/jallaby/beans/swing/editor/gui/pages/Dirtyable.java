package org.jallaby.beans.swing.editor.gui.pages;

import org.jallaby.beans.swing.editor.gui.event.DirtyListener;

public interface Dirtyable {
	void addDirtyListener(DirtyListener listener);
	void removeDirtyListener(DirtyListener listener);
	boolean isDirty();
	void setDirty(boolean dirty);
}
