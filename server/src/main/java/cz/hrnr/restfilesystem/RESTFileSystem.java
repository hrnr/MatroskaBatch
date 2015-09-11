package cz.hrnr.restfilesystem;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.hrnr.matroskabatch.restapi.RESTPath;

@Singleton
public class RESTFileSystem {
	private final Log logger = LogFactory.getLog(RESTFileSystem.class);
	
	private Path root;
	private URI restRoot;
	
	public RESTFileSystem(@Context ServletContext context) {
		String mediaRoot = context.getInitParameter("mediaRoot");
		String defaultURI = context.getInitParameter("rootURI");

		if(mediaRoot != null) {
			this.root = Paths.get(mediaRoot);
		} else {
			for (Path path : FileSystems.getDefault().getRootDirectories()) {
				this.root = path;
				break;
			}
		}
		logger.info("using root: " + root);
		
		try {
			if(defaultURI != null)
				restRoot = new URI("rest://" + defaultURI);
			else
				restRoot = new URI("rest://" + getClass().getName());
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("defaultURI must be valid uri", e);
		}
				
	}
	
	public RESTFileSystem(Path root) {
		this.root = root;
		try {
			restRoot = new URI("rest://" + getClass().getName());
		} catch (URISyntaxException e) {
			throw new InternalError(e);
		}
	}
	
	public RESTPath getRESTPath(Path path) {
		RESTPath restPath = new RESTPath();
		String relativePath = root.relativize(path).toString();
		UriBuilder uriBuilder = UriBuilder.fromUri(restRoot);
		
		uriBuilder.scheme("rest");
		uriBuilder.path(relativePath);
		
		restPath.setPath(uriBuilder.build());
		restPath.setDirectory(Files.isDirectory(path));
		restPath.setOriginalFileName(path.getFileName() != null ? path.getFileName().toString() : "");
		
		return restPath;
	}
	
	public Path getPath(URI path) {
		if (logger.isDebugEnabled()) 
			logger.debug("resolving path for: " + path.toString());
		if (!"rest".equals(path.getScheme()))
			throw new WebApplicationException("given URI must be a rest path", Status.NOT_IMPLEMENTED);
		
		URI relative = restRoot.relativize(path);
		if (logger.isDebugEnabled()) {
			logger.debug("resolved URI: " + relative.toString());
			logger.debug("decoded path: " + relative.getPath());
			logger.debug("raw path: " + relative.getRawPath());
		}	
		Path file = root.resolve(Paths.get(relative.getPath()));
		
		if (logger.isDebugEnabled()) 
			logger.debug("resolved path: " + file.toString());
		return file;
	}
	
	public RESTPath getRESTRoot() {
		RESTPath root = new RESTPath();
		root.setDirectory(true);
		root.setPath(restRoot);
		root.setOriginalFileName(root.getFileName().toString());
		return root;
	}
	
	public Path getRoot() {
		return root;
	}
}
