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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import cz.hrnr.matroskabatch.restapi.RESTPath;

@XmlRootElement
public final class Track implements MuxingItem {

	@XmlElement(type=RESTPath.class)
	private Path path;
	private TrackProperties properties;
	private TrackType type;
	private int trackID;

	/**
	 * Constructor for JAXB
	 */
	@SuppressWarnings("unused")
	private Track() {
		path = null;
		properties = null;
		type = null;
		trackID = 0;
	}

	
	public Track(Path path, int trackID, TrackType type, TrackProperties properties) {
		this.path = path;
		this.properties = properties;
		this.type = type;
		this.trackID = trackID;
	}

	@Override
	@XmlTransient
	public Path getPath() {
		return path;
	}
	
	public void setPath(Path path) {
		this.path = path;
	}

	public int getTrackID() {
		return trackID;
	}

	public void setTrackID(int trackID) {
		this.trackID = trackID;
	}

	@Override
	public String getFileName() {
		return path.getFileName().toString();
	}
	
	public void setProperties(TrackProperties properties) {
		this.properties = properties;
	}

	@Override
	public TrackProperties getProperties() {
		return properties;
	}
	
	public void setType(TrackType trackType) {
		type = trackType;
	}

	@Override
	public TrackType getType() {
		return type;
	}

	@Override
	public String toString() {
		return type + ": " + getFileName() + " | " + getPath();
	}

	@Override
	public List<String> toCmdLine() {
		List<String> cmdline = new ArrayList<>();

		// header: track definition
		cmdline.addAll(type.toCmdLine(trackID));

		// properties
		cmdline.addAll(properties.toCmdLine(trackID));

		// source file
		cmdline.add(path.toAbsolutePath().toString());

		return cmdline;
	}
}
