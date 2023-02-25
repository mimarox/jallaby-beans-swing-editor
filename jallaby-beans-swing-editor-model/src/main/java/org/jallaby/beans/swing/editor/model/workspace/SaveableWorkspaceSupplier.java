package org.jallaby.beans.swing.editor.model.workspace;

import java.io.IOException;
import java.util.function.Supplier;

public interface SaveableWorkspaceSupplier extends Supplier<Workspace> {	
	void saveWorkspace() throws IOException;
}
