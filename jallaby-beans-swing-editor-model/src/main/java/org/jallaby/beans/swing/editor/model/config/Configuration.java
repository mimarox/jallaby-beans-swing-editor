package org.jallaby.beans.swing.editor.model.config;

public class Configuration {
	private final String defaultWorkspace = String.format("%1$s%2$sworkspace%2$sdefault.ws",
			System.getProperty("user.dir"), System.getProperty("file.separator"));
	
	private String workspace;

	/**
	 * @return the workspace
	 */
	public String getWorkspace() {
		return workspace;
	}

	/**
	 * @param workspace the workspace to set
	 */
	public void setWorkspace(final String workspace) {
		this.workspace = workspace;
	}

	/**
	 * @return the defaultWorkspace
	 */
	public String getDefaultWorkspace() {
		return defaultWorkspace;
	}
}
