package cz.hrnr.matroskabatch.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

import cz.hrnr.matroskabatch.track.Track;

/**
 * Various utility functions for getting info about
 * media files and basic filesystem operations.
 *
 */
public abstract class Utils {
	/**
	 * Gets tracks from given mediafile
	 *
	 * If it's not supported file, empty list is returned
	 *
	 * @param p
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public abstract List<Track> getTracks(Path path) throws IOException, InterruptedException;

	/**
	 * Test whether given file is a video mediafile
	 * @param p
	 * @return true if path represents a video file, false other wise
	 * @throws IOException
	 */
	public abstract boolean isVideo(Path p) throws IOException;

	/**
	 * Test whether file is a directory
	 * 
	 * @param path 
	 * @return true if given path represents a directory, false otherwise
	 * @see Files#isDirectory(Path, java.nio.file.LinkOption...)
	 */
	public abstract boolean isDirectory(Path path);

	/**
	 * Lists entries in directory
	 * 
	 * @param directory dir to list
	 * @return entries in directory
	 * @throws IOException
	 * @see {@link Files#list(Path)}
	 */
	public abstract List<Path> listFiles(Path directory) throws IOException;

	/**
	 * Gets parent of given path
	 * 
	 * @param path
	 * @return parent of given path
	 * @throws IOException
	 * @see {@link Path#getParent()}
	 */
	public abstract Path getFileParent(Path path) throws IOException;
	
	/**
	 * Resolves path same way as {@link Path#resolve(String)}
	 * 
	 * @param path
	 * @param segment
	 * @return resolved path
	 * @see Path#resolve(String)
	 */
	public abstract Path resolve(Path path, String segment);

	private static final Pattern SUPPORTED_EXT = Pattern.compile(".*\\.((264)|(265)|(aac)|(ac3)|(ass)|(avc)|(avi)|(btn)|(caf)|(drc)|(dts)|(dtshd)|(dts-hd)|(eac3)|(evo)|(evob)|(flac)|(flv)|(h264)|(h265)|(hevc)|(idx)|(ivf)|(m1v)|(m2ts)|(m2v)|(m4a)|(m4v)|(mk3d)|(mka)|(mks)|(mkv)|(mov)|(mp2)|(mp3)|(mp4)|(mpeg)|(mpg)|(mpls)|(mpv)|(mts)|(ogg)|(ogm)|(ogv)|(opus)|(ra)|(ram)|(rm)|(rmvb)|(rv)|(srt)|(ssa)|(sup)|(thd)|(thd+ac3)|(truehd)|(true-hd)|(ts)|(tta)|(usf)|(vc1)|(vob)|(wav)|(webm)|(webma)|(webmv)|(wv)|(x264)|(x265)|(xml))");
	private static final Pattern MATROSKA_EXT = Pattern.compile(".*\\.mkv");
	
	/**
	 * Gets valid output path for given file
	 *
	 * @param outputDir
	 * @param muxableFile
	 * @return Valid output path to which given file could be muxed
	 * @throws IOException 
	 */
	public Path getDefaultOutput(Path outputDir, Path muxableFile) throws IOException {
		String new_name;

		if (SUPPORTED_EXT.matcher(muxableFile.getFileName().toString()).matches()) {
			// it has sane extension, remove it
			new_name = muxableFile.getFileName().toString().replaceFirst("\\.[^.]+$", ".mkv");
		} else {
			// no sane extension, just add .mkv
			new_name = muxableFile.getFileName() + ".mkv";
		}

		if (outputDir == null || outputDir.equals(getFileParent(muxableFile))) {
			// output will be in current directory
			if (MATROSKA_EXT.matcher(muxableFile.getFileName().toString()).matches()) {
				// it has mkv extension - sanitize
				new_name = muxableFile.getFileName().toString().replaceFirst("\\.mkv$", ".muxed.mkv");
			}

			return resolve(getFileParent(muxableFile), new_name);
		} else {
			return resolve(outputDir, new_name);
		}
	}
}
