package cz.hrnr.matroskabatch.server.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.hrnr.matroskabatch.muxing.MuxingService;
import cz.hrnr.matroskabatch.restapi.RESTPath;
import cz.hrnr.matroskabatch.track.Container;
import cz.hrnr.matroskabatch.track.Track;
import cz.hrnr.restfilesystem.RESTFileSystem;

@Path("/muxing")
public class Muxing {
	private final Log logger = LogFactory.getLog(Muxing.class);
	
	@Inject
	RESTFileSystem fs;
	@Inject
	MuxingService muxingService;
	
	@PUT
	@Path("add-to-muxing")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void addMuxingData(List<Container> data) {
//		resolve paths in data
		for(Container container : data) {
			RESTPath remotePath = (RESTPath)container.getPath();
			java.nio.file.Path resolved = fs.getPath(remotePath.getPath());
			container.setPath(resolved);
			for(Track track : container.getChildren()) {
				remotePath = (RESTPath) track.getPath();
				resolved = fs.getPath(remotePath.getPath());
				track.setPath(resolved);
			}
		}
		
		if(logger.isDebugEnabled())
			logger.debug("adding muxing data: " + data);
		muxingService.addMuxingData(data);
	}
	
	@GET
	@Path("progress")
	public double getProgress() {
		return muxingService.getProgress();
	}

}
