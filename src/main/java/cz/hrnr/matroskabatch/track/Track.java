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

import cz.hrnr.matroskabatch.gui.Icons;
import java.nio.file.Path;
import java.util.List;
import javafx.scene.image.ImageView;

public abstract class Track implements Comparable<Track> {

	protected Path f_;
	protected TrackProperties properties_;
	protected TrackType type_;

	public TrackProperties getProperties() {
		return properties_;
	}

	public String getFullPath() {
		return f_.toAbsolutePath().toString();
	}

	public String getFileName() {
		return f_.getFileName().toString();
	}

	/**
	 * Gets icon visually representing this track
	 *
	 * @return icon appropriate for this track
	 */
	public ImageView getIcon() {
		switch (type_) {
			case VIDEO:
				return Icons.getVideoIcon();
			case AUDIO:
				return Icons.getAudioIcon();
			case SUBTITLES:
				return Icons.getSubtitlesIcon();
			default:
				return null;
		}
	}

	@Override
	public int compareTo(Track o) {
		return type_.getNumVal() - o.type_.getNumVal();
	}

	@Override
	public String toString() {
		return (type_ != TrackType.CONTAINER ? type_ + ": " : "") + getFileName() + " | " + getFullPath();
	}

	public abstract List<String> toCmdLine();
}
