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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents track's mediatype.
 * 
 * track is either one of supported types in Matroska (video, audio, subtitles) or
 * Matroska container itself.
 *
 */
public enum TrackType {

	CONTAINER(0, Arrays.asList("")),
	VIDEO(1, Arrays.asList("-A", "-S", "-d")),
	AUDIO(2, Arrays.asList("-D", "-S", "-a")),
	SUBTITLES(3, Arrays.asList("-D", "-A", "-s"));
	
//	see #getNumVal
	private final int numVal;
//	arguments for mkvmerge to catch on this track
	private final List<String> cmdVal;

	TrackType(int numVal, List<String> cmdVal) {
		this.numVal = numVal;
		this.cmdVal = cmdVal;
	}

	/**
	 * Gets numeric representation. Has no direct relation
	 * with Matroska representation, but may be used for consistent sorting etc.
	 * 
	 * @return non-negative integer representing order
	 */
	public int getNumVal() {
		return numVal;
	}
	
	/**
	 * Converts to mkvmerge cmdline representation.
	 * 
	 * @param trackID mkvmerge trackID 
	 * {@link http://www.bunkus.org/videotools/mkvtoolnix/doc/mkvmerge.html#mkvmerge.track_ids}
	 * @return mkvmerge cmdline arguments
	 */
	public List<String> toCmdLine(int trackID) {
		List<String> l = new ArrayList<>(cmdVal);
		l.add(Integer.toString(trackID));

		return l;
	}

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}
