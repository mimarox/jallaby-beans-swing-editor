package org.jallaby.beans.swing.editor.gui.tree;

import java.awt.Component;
import java.awt.Cursor;
import java.util.Objects;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import org.jallaby.beans.swing.editor.gui.pages.PagesManager;

public class TreeSelectionHandler implements TreeSelectionListener {
	private Component ui;
	private PagesManager pagesManager;
	
	public TreeSelectionHandler(final Component ui, final PagesManager pagesManager ) {
		Objects.requireNonNull(ui, "ui must not be null");
		Objects.requireNonNull(pagesManager, "pagesManager must not be null");
		
		this.ui = ui;
		this.pagesManager = pagesManager;
	}
	
	@Override
	public void valueChanged(final TreeSelectionEvent event) {
		TreePath selectedPath = event.getNewLeadSelectionPath();
		if(selectedPath != null){
			Cursor cursor = ui.getCursor();
			ui.setCursor(new Cursor(Cursor.WAIT_CURSOR));
			
			try {
				pagesManager.showPageForPath(selectedPath);
			} finally {
				ui.setCursor(cursor);
			}
		}
	}
}
