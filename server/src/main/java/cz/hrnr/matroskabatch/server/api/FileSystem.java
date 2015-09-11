package cz.hrnr.matroskabatch.server.api;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.hrnr.matroskabatch.restapi.RESTBoolean;
import cz.hrnr.matroskabatch.restapi.RESTPath;
import cz.hrnr.restfilesystem.RESTFileSystem;

@Path("/filesystem")
public class FileSystem {
	private final Log logger = LogFactory.getLog(FileSystem.class);
	
	@Inject
	RESTFileSystem fs;
	
	@GET
	@Path("is-directory")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RESTBoolean isDirectory(@QueryParam("path") URI path) {
		return new RESTBoolean(Files.isDirectory(fs.getPath(path)));
	}
	
	@GET
	@Path("list")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RESTPath> listFiles(@QueryParam("path") URI directory) {
		try {
			return Files.list(fs.getPath(directory))
					.map(path -> fs.getRESTPath(path))
					.collect(Collectors.toList());
		} catch (IOException e) {
			logger.error("filesystem/list", e);
			throw new WebApplicationException(e);
		}
	}
	
	@GET
	@Path("root")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RESTPath getRoot() {
		return fs.getRESTRoot();
	}
	
	@GET
	@Path("parent")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RESTPath getParent(@QueryParam("path") URI path) {
		java.nio.file.Path realPath = fs.getPath(path);
		java.nio.file.Path parent = realPath.getParent();
		return fs.getRESTPath(parent != null ? parent.normalize() : realPath);
	}
	
	@GET
	@Path("resolve")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public RESTPath getParent(@QueryParam("path") URI path, @QueryParam("segment") String segment) {
		java.nio.file.Path realPath = fs.getPath(path);
		return fs.getRESTPath(realPath.resolve(segment));
	}
}
