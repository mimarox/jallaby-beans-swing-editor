package org.jallaby.beans.swing.editor.model.workspace;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Workspace {
	private List<Project> projects = new ArrayList<>();
	private List<Page> pages = new ArrayList<>();
	private int selectedPageIndex;
	
	/**
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}
	
	/**
	 * @param projects the projects to set
	 */
	public void setProjects(final List<Project> projects) {
		this.projects = projects;
	}
	
	/**
	 * @return the pages
	 */
	public List<Page> getPages() {
		return pages;
	}
	
	/**
	 * @param pages the pages to set
	 */
	public void setPages(final List<Page> pages) {
		this.pages = pages;
	}
	
	/**
	 * @return the selectedPageIndex
	 */
	public int getSelectedPageIndex() {
		return selectedPageIndex;
	}
	
	/**
	 * @param selectedPageIndex the selected page index to set
	 */
	public void setSelectedPageIndex(final int selectedPageIndex) {
		this.selectedPageIndex = selectedPageIndex;
	}
	
	/**
	 * Retrieves the project with the given uuid.
	 * 
	 * @param uuid the uuid
	 * @return the project with the given uuid, if any, else null
	 */
	public Project getProjectByUuid(final UUID uuid) {
		return getProjectByUuid(uuid.toString());
	}
	
	/**
	 * Rerrieves the project with the given uuid.
	 * 
	 * @param uuid the uuid
	 * @return the project with the given uuid, if any, else null
	 */
	public Project getProjectByUuid(final String uuid) {
		return projects.stream().filter(
				p -> p.getUuid().equals(uuid)).findFirst().orElse(null);
	}
}
