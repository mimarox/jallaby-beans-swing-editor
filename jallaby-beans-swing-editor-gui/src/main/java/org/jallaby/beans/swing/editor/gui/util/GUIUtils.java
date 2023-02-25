package org.jallaby.beans.swing.editor.gui.util;

import javax.swing.UIManager;

public class GUIUtils {
	private GUIUtils() {
	} // so it can't be instantiated
	
	public static void setDefaultLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager
					.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
	}
}
