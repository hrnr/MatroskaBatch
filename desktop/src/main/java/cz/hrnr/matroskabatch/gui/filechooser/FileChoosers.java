package cz.hrnr.matroskabatch.gui.filechooser;

import java.nio.file.Path;
import java.util.List;

import javafx.stage.Window;

public interface FileChoosers {

	Path showDirectoryChooser(Window w);

	Path showSingleFileChooser(Window w);

	List<Path> showMultipleFileChooser(Window w);
}