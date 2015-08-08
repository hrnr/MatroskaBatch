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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import cz.hrnr.matroskabatch.muxing.MuxingHelpers;

public final class Container extends MuxingItem {

	private final Path originalFileName_;
	private Set<Track> tracks_;

	/**
	 * Creates new OutputTrack
	 *
	 * Appropriate filename will be deduced from inputFile
	 *
	 * @param outputDir
	 * @param inputFile
	 */
	public Container(Path outputDir, Path inputFile) {
		originalFileName_ = new File(inputFile.toUri()).toPath();
		properties_ = null;
		type_ = TrackType.CONTAINER;
		tracks_ = new TreeSet<>();
		setOutputDir(outputDir);
	}

	public void setOutputDir(Path outputDir) {
		assert Files.isDirectory(outputDir);

		f_ = MuxingHelpers.getDefaultOutput(outputDir, originalFileName_);
	}

	@Override
	public List<String> toCmdLine() {
		return Arrays.asList("-o", f_.toAbsolutePath().toString());
	}

	public void addChildren(Track t) {
		tracks_.add(t);
	}

	public void removeChildren(Track t) {
		tracks_.remove(t);
	}

	public Collection<Track> getChildren() {
		return tracks_;
	}

	public List<Track> getChildrenAsList() {
		return new ArrayList<>(tracks_);
	}

	public Stream<Track> getChildrenStream() {
		return tracks_.stream();
	}
}
