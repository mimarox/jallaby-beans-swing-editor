package org.jallaby.beans.swing.editor.model.workspace;

import java.util.Objects;

public class Page {
	private String projectUuid;
	private PageType pageType;
	
	/**
	 * Standard Ctor.
	 */
	public Page() {
	}
	
	/**
	 * Ctor taking parameters.
	 * 
	 * @param projectUuid the project uuid
	 * @param pageType the pageType
	 */
	public Page(final String projectUuid, final PageType pageType) {
		this.projectUuid = projectUuid;
		this.pageType = pageType;
	}
	
	/**
	 * @return the projectUuid
	 */
	public String getProjectUuid() {
		return projectUuid;
	}
	
	/**
	 * @param projectUuid the projectUuid to set
	 */
	public void setProjectUuid(String projectUuid) {
		this.projectUuid = projectUuid;
	}
	
	/**
	 * @return the pageType
	 */
	public PageType getPageType() {
		return pageType;
	}
	
	/**
	 * @param pageType the pageType to set
	 */
	public void setPageType(PageType pageType) {
		this.pageType = pageType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pageType, projectUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Page other = (Page) obj;
		return pageType == other.pageType && Objects.equals(projectUuid, other.projectUuid);
	}
}
