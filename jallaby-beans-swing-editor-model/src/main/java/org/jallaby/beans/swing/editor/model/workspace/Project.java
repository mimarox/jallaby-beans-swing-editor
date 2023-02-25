package org.jallaby.beans.swing.editor.model.workspace;

import java.util.UUID;

public class Project {
	private boolean mavenProject;
	private String uuid;
	private String path;
	private String sourceFolder;
	private String statesPackage;
	private String transitionsPackage;
	private String stateMachineXmlPath;
	private String name = "";
	private String description = "";
	
	/**
	 * @return the mavenProject
	 */
	public boolean isMavenProject() {
		return mavenProject;
	}
	
	/**
	 * @param mavenProject the mavenProject to set
	 */
	public void setMavenProject(final boolean mavenProject) {
		this.mavenProject = mavenProject;
	}
	
	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
	
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(final UUID uuid) {
		this.uuid = uuid.toString();
	}
	
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @param path the path to set
	 */
	public void setPath(final String path) {
		this.path = path;
	}
	
	/**
	 * @return the source folder
	 */
	public String getSourceFolder() {
		return sourceFolder;
	}
	
	/**
	 * @param sourceFolder the source folder to set
	 */
	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}
	
	/**
	 * @return the statesPackage
	 */
	public String getStatesPackage() {
		return statesPackage;
	}
	
	/**
	 * @param statesPackage the statesPackage to set
	 */
	public void setStatesPackage(final String statesPackage) {
		this.statesPackage = statesPackage;
	}
	
	/**
	 * @return the transitionsPackagePath
	 */
	public String getTransitionsPackage() {
		return transitionsPackage;
	}
	
	/**
	 * @param transitionsPackage the transitionsPackage to set
	 */
	public void setTransitionsPackage(final String transitionsPackage) {
		this.transitionsPackage = transitionsPackage;
	}

	/**
	 * @return the stateMachineXmlPath
	 */
	public String getStateMachineXmlPath() {
		return stateMachineXmlPath;
	}

	/**
	 * @param stateMachineXmlPath the stateMachineXmlPath to set
	 */
	public void setStateMachineXmlPath(final String stateMachineXmlPath) {
		this.stateMachineXmlPath = stateMachineXmlPath;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
