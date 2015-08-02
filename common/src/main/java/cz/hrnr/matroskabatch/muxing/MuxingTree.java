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

import cz.hrnr.matroskabatch.track.TrackList;
import cz.hrnr.matroskabatch.track.OutputTrack;
import cz.hrnr.matroskabatch.track.Track;
import cz.hrnr.matroskabatch.mkvmerge.MatroskaMerge;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.scene.control.TreeItem;
import net.ricecode.similarity.LevenshteinDistanceStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

public final class MuxingTree {

	// maps masterTrack -> subTracks
	private final HashMap<OutputTrack, TrackList> map_ = new HashMap<>();
	// maps subTrack -> masterTrack
	private final HashMap<Track, OutputTrack> reverse_map_ = new HashMap<>();

	private Path outputDir_;

	public Path getOutputDir() {
		return outputDir_;
	}

	public void setOutputDir(Path outputDir) {
		outputDir_ = outputDir;
		// set output for all masterTracks
		map_.keySet().forEach(x -> x.setOutputDir(outputDir));
	}

	/**
	 * Adds given files to muxing tree
	 *
	 * @param files
	 * @throws java.io.IOException
	 */
	public void addFiles(List<Path> files) throws IOException {
		// WARNING awful obfuscation happens here
		// lambdas can't declare exceptions so unchecked exception is thrown and then catched
		try {
			// add masterTrack for each video file
			files.stream()
					.filter(x -> {
						try {
							return MuxingHelpers.isVideo(x);
						} catch (IOException ex) {
							throw new RuntimeException();
						}
					})
					.forEach(x -> addMasterTrack(new OutputTrack(outputDir_, x)));

			// add subtracks
			files.stream()
					.forEach(x -> {
						try {
							addAllSubTracks(MatroskaMerge.getTracks(x));
						} catch (IOException | InterruptedException ex) {
							throw new RuntimeException();
						}
					});
		} catch (RuntimeException ex) {
			throw new IOException();
		}
	}

	/**
	 * Adds directory to muxing tree
	 *
	 * @param dir
	 * @return true if succeeded false otherwise
	 * @throws IOException
	 */
	public boolean addDirectory(Path dir) throws IOException {
		if (!Files.isDirectory(dir)) {
			return false;
		}

		List<Path> dirList = Files.list(dir)
				.collect(Collectors.toList());

		addFiles(dirList);
		return true;
	}

	public void addMasterTrack(OutputTrack t) {
		map_.put(t, new TrackList());
	}

	/**
	 * Add subtracks to tree
	 *
	 * This is effectively the same as calling addSubTrack to track in tracks
	 *
	 * @param tracks
	 */
	public void addAllSubTracks(List<Track> tracks) {
		tracks.forEach(x -> addSubTrack(x));
	}

	/**
	 * Add subtracks to given treeTrack
	 *
	 * This is effectively the same as calling addSubTrack(Track, Track) to
	 * track in tracks
	 *
	 * @param treeTrack
	 * @param tracks
	 */
	public void addAllSubTracks(Track treeTrack, List<Track> tracks) {
		tracks.forEach(x -> addSubTrack(treeTrack, x));
	}

	/**
	 * Add subtrack to closest matching masterTrack
	 *
	 * if no such masterTrack exists, it does effectively nothing
	 *
	 * @todo similarity boundary
	 *
	 * @param t
	 *
	 * @return true if succeeded false otherwise
	 */
	public boolean addSubTrack(Track t) {
		final StringSimilarityService s = new StringSimilarityServiceImpl(new LevenshteinDistanceStrategy());
		Optional<OutputTrack> most_similar;

		most_similar = map_.keySet().stream()
				.max((x, y) -> (int) ((s.score(x.getFileName(), t.getFileName())
						- s.score(y.getFileName(), t.getFileName())) * 1000000));

		if (most_similar.isPresent()) {
			return addSubTrack(most_similar.get(), t);
		} else {
			return false;
		}
	}

	/**
	 * Add t to given treeTrack
	 *
	 * This will add t as sibling track if treeTrack is subTrack, or as subtrack
	 * if treeTrack is masterTrack
	 *
	 * @param treeTrack may be both masterTrack or subTrack
	 * @param t
	 *
	 * @return true if succeeded false otherwise
	 */
	public boolean addSubTrack(Track treeTrack, Track t) {
		// is master track
		if (map_.containsKey(treeTrack)) {
			map_.get(treeTrack).add(t);
			reverse_map_.put(t, (OutputTrack) treeTrack);
			return true;
		} else if (reverse_map_.containsKey(treeTrack)) { // is subTrack
			OutputTrack masterTrack = reverse_map_.get(treeTrack);
			map_.get(masterTrack).add(t);
			reverse_map_.put(t, masterTrack);
			return true;
		} else // track is not present in tree
		{
			return false;
		}
	}

	/**
	 * Removes single Track or whole subtree if track t is master track
	 *
	 * @param t
	 * @return true if succeeded false otherwise
	 */
	public boolean removeTrack(Track t) {
		// is master track
		if (map_.containsKey(t)) {
			map_.get(t).stream().forEach(x -> reverse_map_.remove(x));
			map_.remove(t);
			return true;
		} else if (reverse_map_.containsKey(t)) { // is subTrack
			map_.get(reverse_map_.get(t)).remove(t);
			reverse_map_.remove(t);
			return true;
		} else // track is not present in tree
		{
			return false;
		}
	}

	/**
	 * Removes all tracks from tree
	 */
	public void clear() {
		map_.clear();
		reverse_map_.clear();
	}

	/**
	 * Gets tree contents as tree of TreeItems
	 *
	 * @return List of TreeItems -- masterTracks with associated tracks
	 */
	public List<TreeItem<Track>> getTree() {
		return map_.keySet().stream()
				.map(x -> new TreeItem<Track>(x))
				.peek(x -> {
					x.getChildren().addAll(map_.get(x.getValue()).toTreeList());
					x.setExpanded(true);
				})
				.collect(Collectors.toList());
	}
}
