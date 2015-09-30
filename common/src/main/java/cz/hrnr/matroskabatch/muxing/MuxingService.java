/* 
 * The MIT License
 *
 * Copyright 2015 Jiri Horner.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package cz.hrnr.matroskabatch.muxing;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cz.hrnr.matroskabatch.mkvmerge.MatroskaMerge;
import cz.hrnr.matroskabatch.track.Container;

/**
 * Muxing service for local muxing.
 * 
 * Service will run in as many parallel threads a runtime provides
 * 
 * @see Runtime#availableProcessors()
 *
 */
@Singleton
public class MuxingService extends AbstractMuxingService {
	private static final Log logger = LogFactory.getLog(MuxingService.class);
	private ExecutorService service;
	
	public MuxingService() {
		initService();
	}
	
	/**
	 * Add muxing data to muxing queue
	 * 
	 * This method may not be thread-safe.
	 *
	 * @param tree
	 */
	@Override
	public void addMuxingData(List<Container> data) {
		if(service.isShutdown())
			initService();
		
		for (Container container : data) {
			++totalTasks_;
			service.execute(
				// Runnable instance here
				() -> {
					try {
						MatroskaMerge.muxTracks(container);
					} catch (IOException | InterruptedException e) {
						logger.error("Exception occured during muxing", e);
					}
					taskCompleted();
				});
		}
	}
	
	/**
	 * Internal method called from workers when finished.
	 * 
	 * This method is thread-safe. This method will be called
	 * by concurrent workers.
	 */
	synchronized private void taskCompleted() {
		++completedTasks_;
		progress.set((double) completedTasks_ / totalTasks_);
	}

	@Override
	public void shutdown() {
		service.shutdownNow();
	}
	
	/**
	 * (Re)initializes executing service
	 * 
	 * This method is not thread-safe.
	 * 
	 */
	private void initService() {
		service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}
}
