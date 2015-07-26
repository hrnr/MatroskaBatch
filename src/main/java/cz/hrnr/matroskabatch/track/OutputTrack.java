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

import cz.hrnr.matroskabatch.muxing.MuxingHelpers;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public final class OutputTrack extends Track {

	private final Path originalFileName_;

	/**
	 * Creates new OutputTrack
	 *
	 * Appropriate filename will be deduced from inputFile
	 *
	 * @param outputDir
	 * @param inputFile
	 */
	public OutputTrack(Path outputDir, Path inputFile) {
		originalFileName_ = new File(inputFile.toUri()).toPath();
		properties_ = null;
		type_ = TrackType.CONTAINER;
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
}