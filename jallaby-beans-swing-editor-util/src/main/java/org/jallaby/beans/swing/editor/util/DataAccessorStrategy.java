package org.jallaby.beans.swing.editor.util;

public interface DataAccessorStrategy<C extends Comparable<? super C>> {
	public C getElementAt(int index);
	public int getSize();
}