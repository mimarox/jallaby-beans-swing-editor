package org.jallaby.beans.swing.editor.gui.launcher;

import javax.swing.SwingUtilities;

import org.jallaby.beans.swing.editor.gui.frames.EditorWindow;

public class Launcher {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			EditorWindow window = new EditorWindow();
			window.setVisible(true);
		});
	}
}
