package cz.hrnr.matroskabatch.server.common;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Application configuration according to JAX-RS
 *
 */
public class MatroskaBatchServerApplication extends ResourceConfig {
	public MatroskaBatchServerApplication() {
		register(new DefaultBinder());
	}
}
