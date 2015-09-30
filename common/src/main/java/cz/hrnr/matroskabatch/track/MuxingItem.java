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
import java.util.List;

/**
 * Represents muxable track by mkvmerge. Either mkv container itself
 * or supported media track.
 *
 */
public interface MuxingItem extends Comparable<MuxingItem> {
	/**
	 * Gets filesystem location of track or container item is included in.
	 * @return path to mediafile containing muxing item
	 */
	public Path getPath();
	
	/**
	 * Convenient method for getting filename of underlining mediafile.
	 * 
	 * Same as {@link #getPath()}.getFilename()
	 * 
	 * @return filename
	 */
	public String getFileName();

	/**
	 * Returns properties associated with item
	 * 
	 * May be null if muxing item couldn't provide relevat properties.
	 * @return properties
	 */
	public TrackProperties getProperties();

	/**
	 * Returns item's mediatype(video, audio, subs etc.) or container type
	 * @return type of underlinig track or container
	 */
	public TrackType getType();

	/**
	 * Converts to mkvmerge cmdline representation
	 * @return mkvmerge cmdline arguments
	 */
	public List<String> toCmdLine();

	@Override
	default public int compareTo(MuxingItem o) {
		return getType().getNumVal() - o.getType().getNumVal();
	}
}
