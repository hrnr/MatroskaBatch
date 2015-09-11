package cz.hrnr.matroskabatch.server.common;

import org.glassfish.jersey.server.ResourceConfig;

public class MatroskaBatchServerApplication extends ResourceConfig {
	public MatroskaBatchServerApplication() {
		register(new DefaultBinder());
	}
}
