package cz.hrnr.matroskabatch.server.common;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import cz.hrnr.matroskabatch.muxing.MuxingService;
import cz.hrnr.restfilesystem.RESTFileSystem;

/**
 * Binder for HK2 dependency injection
 */
public class DefaultBinder extends AbstractBinder {
    @Override
    protected void configure() {
    	bind(RESTFileSystem.class).to(RESTFileSystem.class).in(Singleton.class);
    	bind(MuxingService.class).to(MuxingService.class).in(Singleton.class);
    }
}
