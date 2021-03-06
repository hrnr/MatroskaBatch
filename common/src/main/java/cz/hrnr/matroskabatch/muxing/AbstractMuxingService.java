package cz.hrnr.matroskabatch.muxing;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import cz.hrnr.matroskabatch.track.Container;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;

/**
 * Executing service which muxes provided muxing data
 * into Matroska containers.
 * 
 * Tasks may be executed in any order.
 * 
 * Methods are not generally guaranteed to be thread-safe.
 * However implementations may guarantee thread-safety of some methods.
 *
 */
public abstract class AbstractMuxingService {
	protected AtomicInteger totalTasks = new AtomicInteger(0);
	protected AtomicInteger completedTasks = new AtomicInteger(0);
//	access to progress property should be synchronized
	protected DoubleProperty progress = new SimpleDoubleProperty(0);
	
	/**
	 * Add muxing data to muxing queue
	 *
	 * @param data containers to mux
	 */
	public abstract void addMuxingData(List<Container> data);
	
	/**
	 * Gets overall progress, i.e. number of proceeded tasks vs.
	 * all scheduled tasks.
	 * 
	 * This method is thread-safe.
	 * @return progress in percents in [0..1]
	 */
	public double getProgress() {
		// progress may be accesed by concurent workers
		synchronized(progress) {
			return progress.get();
		}
	}

	/**
	 * Property indicating completed tasks of this service
	 *
	 * This is value in [0..1] indication number of completed tasks in percent
	 *
	 * @return
	 */
	public ObservableDoubleValue progressProperty() {
		return progress;
	}
	
	/**
	 * Convenient method for adding muxing data to service.
	 * 
	 * Same as calling #addMuxingData() with {@link MuxingTree#getMuxingData()}
	 * 
	 * @param tree
	 */
	public void addTree(MuxingTree tree) {
		addMuxingData(tree.getMuxingData());
	}
	
	/**
	 * Resets progress as if not tasks had been submitted
	 * to MuxingService.
	 * 
	 * This method is not thread-save. This should be called only when service
	 * is not running, i.e. is shutdown.
	 */
	public void resetProgress() {
		completedTasks.set(0);
		totalTasks.set(0);
		synchronized (progress) {
			progress.set(0);
		}
	}
	
	/**
	 * Stops MuxingService from processing further tasks. Service
	 * may or may not complete tasks that has been already started.
	 */
	public abstract void shutdown();

}