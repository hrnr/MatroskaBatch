package cz.hrnr.matroskabatch.muxing;

import java.util.List;

import cz.hrnr.matroskabatch.track.Container;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;

public abstract class AbstractMuxingService {

	protected int totalTasks_ = 0;

	public abstract void addMuxingData(List<Container> data);

	protected int completedTasks_ = 0;
	protected DoubleProperty progress = new SimpleDoubleProperty(0);

	public AbstractMuxingService() {
		super();
	}

	public synchronized double getProgress() {
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

	public synchronized void addTree(MuxingTree tree) {
		addMuxingData(tree.getMuxingData());
	}

	public synchronized void resetProgress() {
		completedTasks_ = 0;
		progress = new SimpleDoubleProperty(0);
		totalTasks_ = 0;
	}

	public abstract void shutdown();

}