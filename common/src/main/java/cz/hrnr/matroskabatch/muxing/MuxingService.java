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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cz.hrnr.matroskabatch.mkvmerge.MatroskaMerge;
import cz.hrnr.matroskabatch.track.Container;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;

public class MuxingService {

	private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	private int totalTasks_ = 0;
	private int completedTasks_ = 0;
	private final DoubleProperty progress = new SimpleDoubleProperty(0);

	public double getProgress() {
		return progress.get();
	}

	/**
	 * Property indicating completed tasks of this service
	 *
	 * This is value in [0, 1] indication number of completed tasks in percent
	 *
	 * @return
	 */
	public ObservableDoubleValue progressProperty() {
		return progress;
	}

	/**
	 * Add whole tree to muxing queue
	 *
	 * @param tree
	 */
	public void addTree(MuxingTree tree) {
		for (Container container : tree.getMuxingData()) {
			++totalTasks_;
			service.execute(
				// Runnable instance here
				() -> {
					try {
						MatroskaMerge.muxTracks(container);
					} catch (IOException | InterruptedException ex) {
						System.err.println("Exception occured during muxing");
					}
					taskCompleted();
				});
		}
	}

	synchronized private void taskCompleted() {
		++completedTasks_;
		progress.set((double) completedTasks_ / totalTasks_);
	}

	synchronized public void shutdown() {
		service.shutdownNow();
	}
}
