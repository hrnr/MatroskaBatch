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

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cz.hrnr.matroskabatch.track.Container;
import cz.hrnr.matroskabatch.track.MuxingItem;
import cz.hrnr.matroskabatch.track.Track;
import cz.hrnr.matroskabath.utils.LocalUtils;
import cz.hrnr.matroskabath.utils.RemoteUtils;
import cz.hrnr.matroskabath.utils.Utils;

public final class MuxingTree {

	// maps masterTrack -> subTracks
	private final Set<Container> containers_ = new HashSet<>();
	// maps subTrack -> masterTrack
	private final Map<MuxingItem, Container> subtracks_mapping_ = new HashMap<>();
	private Path outputDir_;
	private Utils utils;
	
	public MuxingTree() {
		utils = new LocalUtils();
	}
	
	public MuxingTree(RemoteUtils remoteUtils) {
		utils = remoteUtils;
	}

	public void setUtils(Utils utils) {
		this.utils = utils;
	}

	public Path getOutputDir() {
		return outputDir_;
	}

	public void setOutputDir(Path outputDir) throws IOException {
		outputDir_ = outputDir;
		// set output for all masterTracks
		for(Container container : containers_) {
			container.setOutputDir(outputDir, utils);
		}
	}

	/**
	 * Adds given files to muxing tree
	 *
	 * @param files
	 * @throws java.io.IOException
	 */
	public void addFiles(List<Path> files) throws IOException {
		try {
			for (Path file : files) {
				// add masterTrack for each video file
				if (utils.isVideo(file))
					addMasterTrack(new Container(outputDir_, file, utils));

				// add subtracks
				addAllSubTracks(utils.getTracks(file));
			}
		} catch (InterruptedException e) {
			throw new IOException(e);
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
		if (!utils.isDirectory(dir)) {
			return false;
		}

		List<Path> dirList = utils.listFiles(dir);

		addFiles(dirList);
		return true;
	}

	public void addMasterTrack(Container container) {
		containers_.add(container);
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
	public void addAllSubTracks(MuxingItem treeTrack, List<Track> tracks) {
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
		Optional<Container> most_similar;

//		compares tracks using fuzzy matching, yields max 
		most_similar = containers_.stream()
				.max((x, y) -> StringUtils.getFuzzyDistance(x.getFileName(), t.getFileName(), Locale.getDefault())
						- StringUtils.getFuzzyDistance(y.getFileName(), t.getFileName(), Locale.getDefault()));

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
	 * @param track
	 *
	 * @return true if succeeded false otherwise
	 */
	public boolean addSubTrack(MuxingItem treeTrack, Track track) {
		// is master track
		Container container;

		if (containers_.contains(treeTrack)) {
			container = (Container) treeTrack;
		} else if (subtracks_mapping_.containsKey(treeTrack)) { // is subTrack
			container = subtracks_mapping_.get(treeTrack);
		} else { // track is not present in tree
			return false;
		}

		container.addChildren(track);
		subtracks_mapping_.put(track, container);
		return true;
	}

	/**
	 * Removes single Track or whole subtree if track t is master track
	 *
	 * @param t
	 * @return true if succeeded false otherwise
	 */
	public boolean removeTrack(MuxingItem t) {
		// is master track
		if (containers_.contains(t)) {
			Container container = (Container) t;
//			remove from reverse mapping
			container.getChildrenStream().forEach(x -> subtracks_mapping_.remove(x));
			containers_.remove(container);
			return true;
		} else if (subtracks_mapping_.containsKey(t)) { // is subTrack
			Track track = (Track) t;
			subtracks_mapping_.get(t).removeChildren(track);
			subtracks_mapping_.remove(track);
			return true;
		} else { // track is not present in tree
			return false;
		}
	}

	/**
	 * Removes all tracks from tree
	 */
	public void clear() {
		containers_.clear();
		subtracks_mapping_.clear();
	}

	/**
	 * Gets contents of MuxingTree as List of containers
	 *
	 * @return list of containers
	 */
	public List<Container> getMuxingData() {
		return new ArrayList<>(containers_);
	}
}
