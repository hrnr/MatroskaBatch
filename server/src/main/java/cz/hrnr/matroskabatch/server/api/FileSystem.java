package cz.hrnr.matroskabatch.server.api;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.hrnr.matroskabatch.restapi.RESTBoolean;
import cz.hrnr.matroskabatch.restapi.RESTPath;
import cz.hrnr.restfilesystem.RESTFileSystem;
/**
 * Resources for retrieving informations about filesystem.
 * 
 * Allows listing and resolving paths in remote filesystem. Doesn't allow
 * modifying files or creating directories.
 * @author hrnr
 *
 */
@Path("/filesystem")
public class FileSystem {
	private final Log logger = LogFactory.getLog(FileSystem.class);
	
	@Inject
	RESTFileSystem fs;
	
	/**
	 * Test whether file is a directory
	 * 
	 * @param path URI identifying RESTPath as retrieved from this API
	 * @return true if given path represents a directory, false otherwise
	 */
	@GET
	@Path("is-directory")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RESTBoolean isDirectory(@QueryParam("path") URI path) {
		return new RESTBoolean(Files.isDirectory(fs.getPath(path)));
	}
	
	/**
	 * Lists entries in directory
	 * 
	 * @param directory URI identifying RESTPath as retrieved from this API, should be directory
	 * @return entries in directory
	 * @throws WebApplicationException if given path is not directory or IO error occured on the server
	 */
	@GET
	@Path("list")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RESTPath> listFiles(@QueryParam("path") URI directory) {
		try {
			return Files.list(fs.getPath(directory))
					.map(path -> fs.getRESTPath(path))
					.collect(Collectors.toList());
		} catch (NotDirectoryException e) {
			throw new WebApplicationException(Status.EXPECTATION_FAILED);
		} catch (IOException e) {
			logger.error("filesystem/list", e);
			throw new WebApplicationException(e);
		}
	}
	
	/**
	 * Gets filesystem root
	 * 
	 * @return root of the filesystem used by MatroskaBatch
	 */
	@GET
	@Path("root")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RESTPath getRoot() {
		return fs.getRESTRoot();
	}
	
	/**
	 * Gets path parent
	 * 
	 * @param path URI identifying RESTPath as retrieved from this API
	 * @return paths parent, or path itself if path is root
	 */
	@GET
	@Path("parent")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RESTPath getParent(@QueryParam("path") URI path) {
		java.nio.file.Path realPath = fs.getPath(path);
		java.nio.file.Path parent = realPath.getParent();
		return fs.getRESTPath(parent != null ? parent.normalize() : realPath);
	}
	
	/**
	 * Resolves given segment agains path
	 * 
	 * @param path URI identifying RESTPath to resolve against as retrieved from this API
	 * @param segment String to convert to path and resolve against path
	 * @return resolved path
	 */
	@GET
	@Path("resolve")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RESTPath getParent(@QueryParam("path") URI path, @QueryParam("segment") String segment) {
		java.nio.file.Path realPath = fs.getPath(path);
		return fs.getRESTPath(realPath.resolve(segment));
	}
}
