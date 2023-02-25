package org.jallaby.beans.swing.editor.gui.components;

public interface TabClosingListener {
    /**
     * @param tabIndex the index of the tab that is about to be closed
     * @return true if the tab can be really closed
     */
    public boolean tabClosing(int tabIndex);

    /**
     * @param tabIndex the index of the tab that is about to be closed
     * @return true if the tab should be selected before closing
     */
    public boolean selectTabBeforeClosing(int tabIndex);
}
