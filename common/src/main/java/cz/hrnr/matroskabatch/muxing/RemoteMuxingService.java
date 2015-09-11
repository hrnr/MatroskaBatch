package cz.hrnr.matroskabatch.muxing;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;

import cz.hrnr.matroskabatch.track.Container;

public class RemoteMuxingService extends AbstractMuxingService {
	private WebTarget target;
	
	public RemoteMuxingService(URI uri) {
		Client client = ClientBuilder.newClient();
		target = client.target(uri);
	}


	@Override
	public void addMuxingData(List<Container> data) {
		GenericType<List<Container>> containerType = new GenericType<List<Container>>() {};
		GenericEntity<List<Container>> dataEntity = new GenericEntity<List<Container>>(data, containerType.getType());
		target.path("muxing").path("add-to-muxing")
			.request().put(Entity.xml(dataEntity));
		
		synchronized (progress) {
			progress.set(1);
		}
	}

	@Override
	public void shutdown() {
	}
}