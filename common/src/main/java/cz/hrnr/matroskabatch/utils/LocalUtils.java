package cz.hrnr.matroskabatch.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import cz.hrnr.matroskabatch.mkvmerge.MatroskaMerge;
import cz.hrnr.matroskabatch.track.Track;

/**
 * Wrapper around standard library functions to
 * provide Utils interface.
 * 
 * Basic functions using local filesystem.
 *
 */
public class LocalUtils extends Utils {
	
	@Override
	public List<Track> getTracks(Path path) throws IOException, InterruptedException {
		return MatroskaMerge.getTracks(path);
	}
	
	@Override
	public boolean isVideo(Path p) throws IOException {
		return Files.probeContentType(p).matches("video/.*");
	}
	
	@Override
	public boolean isDirectory(Path path) {
		return Files.isDirectory(path);
	}
	
	@Override
	public List<Path> listFiles(Path directory) throws IOException {
		return Files.list(directory)
				.collect(Collectors.toList());
	}

	@Override
	public Path getFileParent(Path path) throws IOException {
		return path.getParent();
	}

	@Override
	public Path resolve(Path path, String segment) {
		return path.resolve(segment);
	}
}
