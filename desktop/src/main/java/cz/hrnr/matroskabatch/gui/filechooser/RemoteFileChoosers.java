package cz.hrnr.matroskabatch.gui.filechooser;

import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cz.hrnr.matroskabatch.restapi.RESTPath;
import cz.hrnr.matroskabath.utils.RemoteUtils;
import javafx.stage.Window;

public class RemoteFileChoosers implements FileChoosers {
	private RemoteUtils remoteUtils;
	private RemoteFileChooserController remoteFileChooser;
	
	public RemoteFileChoosers(RemoteUtils utils) {
		remoteUtils = utils;
		remoteFileChooser = new RemoteFileChooserController(remoteUtils);
	}
	
	public RemoteFileChoosers(URI uri) {
		remoteUtils = new RemoteUtils(uri);
		remoteFileChooser = new RemoteFileChooserController(remoteUtils);
	}

	@Override
	public Path showDirectoryChooser(Window w) {
		remoteFileChooser.showAndWait(false);
		return remoteFileChooser.getResultDirectory();
	}

	@Override
	public Path showSingleFileChooser(Window w) {
		List<RESTPath> result = remoteFileChooser.showAndWait(false);
		
		if(result != null && !result.isEmpty()) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Path> showMultipleFileChooser(Window w) {
		List<RESTPath> result = remoteFileChooser.showAndWait(true);
		
		if(result != null) {
			return result.stream().filter(x -> !x.isDirectory()).map(x -> (Path)x).collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

}
