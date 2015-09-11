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

	public URI getPath() {
		return path;
	}
	public void setPath(URI path) {
		this.path = path;
	}
	public boolean isDirectory() {
		return directory;
	}
	public void setDirectory(boolean directory) {
		this.directory = directory;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
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
	
	@Override
	protected RESTPath clone() {
		return new RESTPath(path, directory, originalFileName);
	}
	
	@Override
	public String toString() {
		return originalFileName;
	}
}