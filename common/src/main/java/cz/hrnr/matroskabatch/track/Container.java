/* 
 * The MIT License
 *
 * Copyright 2015 Jiri Horner.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package cz.hrnr.matroskabatch.track;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import cz.hrnr.matroskabatch.restapi.RESTPath;
import cz.hrnr.matroskabath.utils.Utils;

@XmlRootElement
public final class Container implements MuxingItem {

	private Set<Track> tracks;
	@XmlElement(type=RESTPath.class)
	private final Path originalFileName;
	@XmlElement(type=RESTPath.class)
	private Path fileName;
	
	/**
	 * Constructor for JAXB
	 */
	@SuppressWarnings("unused")
	private Container() {
		fileName = null;
		originalFileName = null;
		tracks = new TreeSet<>();
	}

	/**
	 * Creates new OutputTrack
	 *
	 * Appropriate filename will be deduced from inputFile
	 *
	 * @param outputDir
	 * @param inputFile
	 * @throws IOException 
	 */
	public Container(Path outputDir, Path inputFile, Utils utils) throws IOException {
		originalFileName = inputFile;
		tracks = new TreeSet<>();
		setOutputDir(outputDir, utils);
	}

	@XmlElementWrapper(name = "tracks")
	@XmlElement(name = "track")
	public Collection<Track> getChildren() {
		return tracks;
	}

	public Path getOriginalFileName_() {
		return originalFileName;
	}
	
	public void setPath(Path path) {
		fileName = path;
	}

	@Override
	@XmlTransient
	public Path getPath() {
		return fileName.toAbsolutePath();
	}

	@Override
	public String getFileName() {
		return fileName.getFileName().toString();
	}

	@Override
	public TrackProperties getProperties() {
		return null;
	}

	@Override
	public TrackType getType() {
		return TrackType.CONTAINER;
	}

	public void setOutputDir(Path outputDir, Utils utils) throws IOException {
		assert Files.isDirectory(outputDir);

		fileName = utils.getDefaultOutput(outputDir, originalFileName);
	}

	@Override
	public String toString() {
		return getFileName() + " | " + getPath().toUri().getPath();
	}

	@Override
	public List<String> toCmdLine() {
		return Arrays.asList("-o", fileName.toAbsolutePath().toString());
	}

	public void addChildren(Track t) {
		tracks.add(t);
	}

	public void removeChildren(Track t) {
		tracks.remove(t);
	}

	public List<Track> getChildrenAsList() {
		return new ArrayList<>(tracks);
	}

	public Stream<Track> getChildrenStream() {
		return tracks.stream();
	}
}
