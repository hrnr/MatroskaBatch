package cz.hrnr.matroskabatch.utils;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import org.glassfish.jersey.uri.UriComponent;
import org.glassfish.jersey.uri.UriComponent.Type;

import cz.hrnr.matroskabatch.restapi.RESTBoolean;
import cz.hrnr.matroskabatch.restapi.RESTPath;
import cz.hrnr.matroskabatch.track.Track;

/**
 * Utilities which remoted to MatroskaBatchServer rest API
 * and gathers information from server rather than from local
 * filesystem
 *
 */
public class RemoteUtils extends Utils {
	private WebTarget target;
	
	/**
	 * Creates instance of RemoteUtils
	 * @param uri URI of MatroskabatchServer rest api
	 */
	public RemoteUtils(URI uri) {
		Client client = ClientBuilder.newClient();
		target = client.target(uri);
	}
	
	@Override
	public List<Track> getTracks(Path path) throws IOException, InterruptedException {
		return (List<Track>) target.path("utils").path("tracks")
			.queryParam("path", encodeQueryParam(path))
			.request().get(new GenericType<List<Track>>(){});
	}

	@Override
	public boolean isVideo(Path path) throws IOException {
		return target.path("utils").path("is-video")
				.queryParam("path", encodeQueryParam(path))
				.request().get(RESTBoolean.class).get();
	}

	@Override
	public boolean isDirectory(Path path) {
		if(!(path instanceof RESTPath)) {
			throw new IllegalArgumentException("provided path must be instance of RESTPath");
		} else {
			RESTPath restPath = (RESTPath) path;
			return restPath.isDirectory();
		}
			
	}

	@Override
	public List<Path> listFiles(Path directory) throws IOException {
		List<Path> directoryContent = new ArrayList<>();
		List<RESTPath> response = target.path("filesystem").path("list")
				.queryParam("path", encodeQueryParam(directory))
				.request().get(new GenericType<List<RESTPath>>() {});
		directoryContent.addAll(response);
		return directoryContent;
	}
	
	/**
	 * Gets root of remote filesystem
	 * @return root
	 * @throws IOException
	 */
	public RESTPath getRemoteRoot() throws IOException {
		RESTPath response = target.path("filesystem").path("root")
				.request().get(RESTPath.class);
		return response;
	}
	
	@Override
	public Path getFileParent(Path path) throws IOException {
		return target.path("filesystem").path("parent")
				.queryParam("path", encodeQueryParam(path))
				.request().get(RESTPath.class);
	}
	
	@Override
	public Path resolve(Path path, String segment) {
		return target.path("filesystem").path("resolve")
				.queryParam("path", encodeQueryParam(path))
				.queryParam("segment", encodeQueryParam(segment))
				.request().get(RESTPath.class);
	}
	
	/**
	 * Encodes query parameter according to RFC
	 * @param path path which URI will be encoded as query parameter
	 * @return encoded URI part
	 */
	private String encodeQueryParam(Path path) {
		return UriComponent.encode(path.toUri().toString(), Type.QUERY_PARAM_SPACE_ENCODED);
	}
	
	/**
	 * Encodes query parameter according to RFC
	 * @param param parameter to encode
	 * @return encoded URI part
	 */
	private String encodeQueryParam(String param) {
		return UriComponent.encode(param, Type.QUERY_PARAM_SPACE_ENCODED);
	}
}
