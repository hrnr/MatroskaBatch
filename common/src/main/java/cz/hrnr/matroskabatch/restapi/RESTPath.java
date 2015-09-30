package cz.hrnr.matroskabatch.restapi;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Wraps basic path repsentation from MatroskaBatchMergeServer
 * API into Path interface.
 * 
 * WARNING: This is just a simple wrapper, not full-blown part of Java filesystem api,
 * most methods will throw {@link UnsupportedOperationException}. You should call only
 * methods from Path interface that are explicitely mentioned as save to use on this
 * wrapper.
 *
 */
@XmlRootElement
public class RESTPath implements Path, Cloneable {
	private URI path;
	private boolean directory;
	private String originalFileName;
	
	public RESTPath() {}
	
	public RESTPath(URI path, boolean directory, String originalFileName) {
		super();
		this.path = path;
		this.directory = directory;
		this.originalFileName = originalFileName;
	}
	
	/**
	 * Gets URI repsenting resource location in API.
	 * @return file location
	 */
	public URI getPath() {
		return path;
	}

	/**
	 * @see #getPath()
	 */

	public void setPath(URI path) {
		this.path = path;
	}

	/**
	 * Flag indicating if this path represents a directory
	 * @return true if this path represents a directory, false otherwise
	 */
	public boolean isDirectory() {
		return directory;
	}

	/**
	 * @param directory true if this path represents a directory, false otherwise
	 * @see #isDirectory()
	 */
	public void setDirectory(boolean directory) {
		this.directory = directory;
	}

	/**
	 * Original filename of remote resource
	 * @return filename as string as interpreted by remote system
	 */
	public String getOriginalFileName() {
		return originalFileName;
	}

	/**
	 * @param originalFileName
	 * @see #getOriginalFileName()
	 */
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	@Override
	public FileSystem getFileSystem() {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean isAbsolute() {
		throw new UnsupportedOperationException();
	}
	@Override
	public Path getRoot() {
		throw new UnsupportedOperationException();
	}
	/**
	 * Save to call on this wrapper.
	 * 
	 * Return always the same object since all
	 * paths are absolute in MatroskaBatch api.
	 * 
	 * @return always this
	 */
	@Override
	public Path getFileName() {
		return this;
	}
	@Override
	@XmlTransient
	public Path getParent() {
		throw new UnsupportedOperationException();
	}
	@Override
	public int getNameCount() {
		throw new UnsupportedOperationException();
	}
	@Override
	public Path getName(int index) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Path subpath(int beginIndex, int endIndex) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean startsWith(Path other) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean startsWith(String other) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean endsWith(Path other) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean endsWith(String other) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Path normalize() {
		throw new UnsupportedOperationException();
	}
	@Override
	public Path resolve(Path other) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Path resolve(String other) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Path resolveSibling(Path other) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Path resolveSibling(String other) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Path relativize(Path other) {
		throw new UnsupportedOperationException();
	}
	/**
	 * Save to call on this wrapper.
	 * 
	 * Same as {@link #getPath()}
	 */
	@Override
	public URI toUri() {
		return path;
	}
	@Override
	public Path toAbsolutePath() {
		return this;
	}
	@Override
	public Path toRealPath(LinkOption... options) throws IOException {
		throw new UnsupportedOperationException();
	}
	@Override
	public File toFile() {
		throw new UnsupportedOperationException();
	}
	@Override
	public WatchKey register(WatchService watcher, Kind<?>[] events, Modifier... modifiers) throws IOException {
		throw new UnsupportedOperationException();
	}
	@Override
	public WatchKey register(WatchService watcher, Kind<?>... events) throws IOException {
		throw new UnsupportedOperationException();
	}
	@Override
	public Iterator<Path> iterator() {
		throw new UnsupportedOperationException();
	}
	@Override
	public int compareTo(Path other) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Save to call on this wrapper.
	 */
	@Override
	protected RESTPath clone() {
		return new RESTPath(path, directory, originalFileName);
	}
	
	/**
	 * Save to call on this wrapper.
	 */
	@Override
	public String toString() {
		return originalFileName;
	}
}