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
package cz.hrnr.matroskabatch.mkvmerge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.hrnr.matroskabatch.track.Container;
import cz.hrnr.matroskabatch.track.Track;
import cz.hrnr.matroskabatch.track.TrackProperties;
import cz.hrnr.matroskabatch.track.TrackType;

public class MatroskaMerge {
	private static final Log logger = LogFactory.getLog(MatroskaMerge.class); 

	/**
	 * Runs mkvmerge with args
	 *
	 * @param args
	 * @param output Command output as lines
	 * @return exit code
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static int MkvMerge(List<String> args, List<String> output) throws IOException, InterruptedException {
		List<String> cmdline = new ArrayList<>();
		cmdline.add("mkvmerge");
		cmdline.addAll(args);
		int exit_code;

		ProcessBuilder pb = new ProcessBuilder(cmdline);
		if(logger.isDebugEnabled()) {
			pb.redirectErrorStream(true);
			logger.debug("executing mkvmerge: " + cmdline);
		}
		Process process = pb.start();
		exit_code = process.waitFor();		

		if (output != null) {
			try (BufferedReader b = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				output.addAll(b.lines()
						.collect(Collectors.toList()));
			}
		}

		return exit_code;
	}

	/**
	 * Checks is mkvmerge is available at standard PATH
	 *
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static boolean available() throws IOException, InterruptedException {
		return MkvMerge(Arrays.asList("-V"), null) == 0;
	}

	/**
	 * Gets tracks from given Path
	 *
	 * If it's not supported file, empty list is returned
	 *
	 * @param p
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static List<Track> getTracks(Path p) throws IOException, InterruptedException {
		List<Track> l = new ArrayList<>();
		List<String> output = new ArrayList<>();
		int exit_code;

		exit_code = MkvMerge(Arrays.asList("-i", p.toAbsolutePath().toString()), output);

		if (exit_code != 0) {
			return l;
		}

		output.stream()
				.filter(x -> x.matches("^Track ID.*"))
				.map(x -> x.split("\\s+"))
				.filter(x -> x[3].matches("(video)|(audio)|(subtitles)"))
				.forEach(x -> {
					// TODO fetch track properties
					l.add(
							new Track(
									p.toAbsolutePath(),
									Integer.parseInt(x[2].replace(":", "")),
									TrackType.valueOf(x[3].toUpperCase()),
									new TrackProperties()));
				});

		return l;
	}

	public static void muxTracks(Container container) throws IOException, InterruptedException {
		List<String> output = null;
		if(logger.isDebugEnabled())
			output = new ArrayList<>();
		List<String> cmdline = new ArrayList<>();
		List<Track> tracks = container.getChildrenAsList();
		boolean exit_failed;

		cmdline.addAll(container.toCmdLine());
		tracks.forEach(x -> cmdline.addAll(x.toCmdLine()));

		exit_failed = MkvMerge(cmdline, null) == 2;

		if (exit_failed) {
			throw new IOException("mkvmerge execution failed: " + output);
		}
	}
}
