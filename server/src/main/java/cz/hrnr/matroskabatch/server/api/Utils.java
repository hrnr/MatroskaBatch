package cz.hrnr.matroskabatch.server.api;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.hrnr.matroskabatch.mkvmerge.MatroskaMerge;
import cz.hrnr.matroskabatch.restapi.RESTBoolean;
import cz.hrnr.matroskabatch.restapi.RESTPath;
import cz.hrnr.matroskabatch.track.Track;
import cz.hrnr.restfilesystem.RESTFileSystem;

/**
 * Media files utilities
 * 
 * @author hrnr
 *
 */
@Path("/utils")
public class Utils {
	private final Log logger = LogFactory.getLog(Utils.class);
	
	@Inject
	RESTFileSystem fs;
	
	/**
	 * Gets tracks from media file.
	 * 
	 * Extracts list of tracks that can be muxed by mkvmerge.
	 * This tracks may represent audio, video or subtitle.
	 * 
	 * @param path URI identifying RESTPath as retrieved from this API
	 * @return list of tracks contained in path
	 */
	@GET
	@Path("tracks")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Track> getTracks(@QueryParam("path") URI path) {
		try {
			List<Track> tracks = MatroskaMerge.getTracks(fs.getPath(path));
			for(Track track : tracks) {
				RESTPath remotePath = fs.getRESTPath(track.getPath());
				track.setPath(remotePath);
			}
			return tracks;
		} catch(IOException | InterruptedException e) {
			logger.error("utils/tracks", e);
			throw new WebApplicationException(e);
		}
	}
	
	/**
	 * Tells whether given path represents a video
	 * 
	 * @param path URI identifying RESTPath as retrieved from this API
	 * @return true if given path represents video false otherwise
	 * @throws WebApplicationException if IO error occured on server
	 */
	@GET
	@Path("is-video")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RESTBoolean isVideo(@QueryParam("path") URI path) {
		try {
			return new RESTBoolean(Files.probeContentType(fs.getPath(path)).matches("video/.*"));
		} catch (IOException e) {
			logger.error("utils/is-video", e);
			throw new WebApplicationException(e); 
		}
	}
}
