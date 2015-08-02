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
package cz.hrnr.matroskabatch.muxing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Pattern;
import java.nio.file.Path;

public final class MuxingHelpers {

	private MuxingHelpers() {
	}

	private static final Pattern SUPPORTED_EXT = Pattern.compile(".*\\.((264)|(265)|(aac)|(ac3)|(ass)|(avc)|(avi)|(btn)|(caf)|(drc)|(dts)|(dtshd)|(dts-hd)|(eac3)|(evo)|(evob)|(flac)|(flv)|(h264)|(h265)|(hevc)|(idx)|(ivf)|(m1v)|(m2ts)|(m2v)|(m4a)|(m4v)|(mk3d)|(mka)|(mks)|(mkv)|(mov)|(mp2)|(mp3)|(mp4)|(mpeg)|(mpg)|(mpls)|(mpv)|(mts)|(ogg)|(ogm)|(ogv)|(opus)|(ra)|(ram)|(rm)|(rmvb)|(rv)|(srt)|(ssa)|(sup)|(thd)|(thd+ac3)|(truehd)|(true-hd)|(ts)|(tta)|(usf)|(vc1)|(vob)|(wav)|(webm)|(webma)|(webmv)|(wv)|(x264)|(x265)|(xml))");
	private static final Pattern MATROSKA_EXT = Pattern.compile(".*\\.mkv");

	public static boolean isVideo(Path p) throws IOException {
		return Files.probeContentType(p).matches("video/.*");
	}

	/**
	 * Gets valid output path for given file
	 *
	 * @param outputDir
	 * @param muxableFile
	 * @return Valid output path to which given file could be muxed
	 */
	public static Path getDefaultOutput(Path outputDir, Path muxableFile) {
//		assert isMuxable(muxableFile);

		String new_name;

		if (SUPPORTED_EXT.matcher(muxableFile.getFileName().toString()).matches()) {
			// it has sane extension, remove it
			new_name = muxableFile.getFileName().toString().replaceFirst("\\.[^.]+$", ".mkv");
		} else {
			// no sane extension, just add .mkv
			new_name = muxableFile.getFileName() + ".mkv";
		}

		if (outputDir == null || outputDir.equals(muxableFile.getParent())) {
			// output will be in current directory
			if (MATROSKA_EXT.matcher(muxableFile.getFileName().toString()).matches()) {
				// it has mkv extension - sanitize
				new_name = muxableFile.getFileName().toString().replaceFirst("\\.mkv$", ".muxed.mkv");
			}

			return new File(muxableFile.getParent().toFile(), new_name).toPath();
		} else {
			return new File(outputDir.toFile(), new_name).toPath();
		}
	}
}
