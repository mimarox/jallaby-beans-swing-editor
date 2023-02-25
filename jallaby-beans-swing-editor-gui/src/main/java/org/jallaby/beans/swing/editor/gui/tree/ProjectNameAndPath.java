package org.jallaby.beans.swing.editor.gui.tree;

import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

public class ProjectNameAndPath {
	private UUID uuid;
	private Path path;
	
	public ProjectNameAndPath(final UUID uuid, final Path path) {
		Objects.requireNonNull(uuid, "uuid must not be null");
		Objects.requireNonNull(path, "path must not be null");

		this.uuid = uuid;
		this.path = path;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public Path getPath() {
		return path;
	}
	
	public String getName() {
		return path.getFileName().toString();
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
