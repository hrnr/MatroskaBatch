package cz.hrnr.matroskabatch.gui.filechooser;

import java.nio.file.Path;
import java.util.List;

import javafx.stage.Window;

/**
 * Interface for file choosing dialogs.
 * 
 * Dialogs are expected to present use an interface to choose filesystem
 * entries and return selection result.
 *
 */
public interface FileChoosers {
	/**
	 * Presents user an interface to choose a single direcotry
	 * @param w window to use as Parent
	 * @return directory chooosen by user
	 */
	Path showDirectoryChooser(Window w);

	/**
	 * Presents user an interface to choose a single file
	 * @param w window to use as Parent
	 * @return file chooosen by user
	 */
	Path showSingleFileChooser(Window w);

	/**
	 * Presents user an interface to choose multiple files
	 * @param w window to use as Parent
	 * @return files choosen by user
	 */
	List<Path> showMultipleFileChooser(Window w);
}