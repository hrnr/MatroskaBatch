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
package cz.hrnr.matroskabatch.gui;

import cz.hrnr.matroskabatch.restapi.RESTPath;
import cz.hrnr.matroskabatch.track.MuxingItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Icons {
	private static Image videoIcon = new Image("/icons/video.png");
	private static Image audioIcon = new Image("/icons/audio.png");
	private static Image subtitlesIcon = new Image("/icons/subtitles.png");
	private static Image directoryIcon = new Image("/icons/folder.png");
	private static Image fileIcon = new Image("/icons/text-x-generic.png");

	public static ImageView getVideoIcon() {
		return new ImageView(videoIcon);
	}

	public static ImageView getAudioIcon() {
		return new ImageView(audioIcon);
	}

	public static ImageView getSubtitlesIcon() {
		return new ImageView(subtitlesIcon);
	}
	
	public static ImageView getDirectoryIcon() {
		return new ImageView(directoryIcon);
	}
	
	public static ImageView getFileIcon() {
		return new ImageView(fileIcon);
	}
	
	/**
	 * Gets icon visually representing this track
	 *
	 * @return icon appropriate for this track
	 */
	public static ImageView getIcon(MuxingItem track) {
		switch (track.getType()) {
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
	
	public static ImageView getIcon(RESTPath path) {
		if(path.isDirectory()) {
			return Icons.getDirectoryIcon();
		} else {
			return Icons.getFileIcon();
		}
	}
}
