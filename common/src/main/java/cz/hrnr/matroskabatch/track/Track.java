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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class Track extends MuxingItem {

	int trackID_ = 0;

	public Track(Path path, int trackID, TrackType type, TrackProperties properties) {
		f_ = path;
		properties_ = properties;
		type_ = type;
		trackID_ = trackID;
	}

	public void setPath(String path) {
		f_ = new File(path).toPath();
	}

	@Override
	public List<String> toCmdLine() {
		List<String> cmdline = new ArrayList<>();

		// header: track definition
		cmdline.addAll(type_.toCmdLine(trackID_));

		// properties
		cmdline.addAll(properties_.toCmdLine(trackID_));

		// source file
		cmdline.add(f_.toAbsolutePath().toString());

		return cmdline;
	}
}
