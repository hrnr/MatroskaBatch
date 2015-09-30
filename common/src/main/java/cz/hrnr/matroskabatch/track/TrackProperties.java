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

import cz.hrnr.matroskabatch.track.properties.BoolProperty;
import cz.hrnr.matroskabatch.track.properties.LangProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents some of tracks properties track can have in Matroska
 * container
 *  
 * @see mkvpropedit -l
 */
@XmlRootElement
public class TrackProperties {
//	matroska properties
	protected String name;
	protected LangProperty language;
	protected BoolProperty defaultF;
	protected BoolProperty forcedF;

	/**
	 * Initialize Track properties to default values
	 */
	public TrackProperties() {
		this.name = "";
		this.language = LangProperty.UND;
		this.defaultF = BoolProperty.DEFAULT;
		this.forcedF = BoolProperty.NO;
	}
	
	/**
	 * Initilizes properties with provided values
	 * @param name
	 * @param language
	 * @param defaultF
	 * @param forcedF
	 */
	public TrackProperties(String name, LangProperty language, BoolProperty defaultF, BoolProperty forcedF) {
		this.name = name;
		this.language = language;
		this.defaultF = defaultF;
		this.forcedF = forcedF;
	}

	/**
	 * @return Name: A human-readable track name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Language: Specifies the language of the track in the
	 * Matroska languages form.
	 */
	public LangProperty getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(LangProperty language) {
		this.language = language;
	}

	/**
	 * @return 'Default track' flag: Set if that track (audio, video or subs)
	 *  SHOULD be used if no language found matches the user preference.
	 */
	public BoolProperty getDefaultF() {
		return defaultF;
	}

	/**
	 * @param defaultF the defaultF to set
	 */
	public void setDefaultF(BoolProperty defaultF) {
		this.defaultF = defaultF;
	}

	/**
	 * @return 'Forced display' flag: Set if that track MUST be used
	 * during playback. There can be many forced track for a kind 
	 * (audio, video or subs). The player should select the one 
	 * whose language matches the user preference or the default 
	 * + forced track.
	 */
	public BoolProperty getForcedF() {
		return forcedF;
	}

	/**
	 * @param forcedF the forcedF to set
	 */
	public void setForcedF(BoolProperty forcedF) {
		this.forcedF = forcedF;
	}
	
	/**
	 * Creates arguments for mkvmerge representing stored properties.
	 * 
	 * @param trackID Matroska track ID
	 * {@link http://www.bunkus.org/videotools/mkvtoolnix/doc/mkvmerge.html#mkvmerge.track_ids}
	 * @return mkvmerge cmdline
	 */
	public List<String> toCmdLine(int trackID) {
		List<String> cmdline = new ArrayList<>();

		// track name
		cmdline.addAll(Arrays.asList("--track-name", trackID + ":" + getName()));

		// language
		cmdline.addAll(Arrays.asList("--language", trackID + ":" + getLanguage().toISOId()));

		// default flag
		if (getDefaultF() != BoolProperty.DEFAULT) {
			cmdline.addAll(Arrays.asList("--default-track", trackID + ":" + getDefaultF().toCmdLine()));
		}

		// forced flag
		cmdline.addAll(Arrays.asList("--forced-track", trackID + ":" + getForcedF().toCmdLine()));

		return cmdline;
	}
}
