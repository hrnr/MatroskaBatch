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
package cz.hrnr.matroskabatch.gui;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import cz.hrnr.matroskabatch.gui.filechooser.FileChoosers;
import cz.hrnr.matroskabatch.gui.filechooser.LocalFileChoosers;
import cz.hrnr.matroskabatch.gui.filechooser.RemoteFileChoosers;
import cz.hrnr.matroskabatch.mkvmerge.MatroskaMerge;
import cz.hrnr.matroskabatch.muxing.AbstractMuxingService;
import cz.hrnr.matroskabatch.muxing.MuxingService;
import cz.hrnr.matroskabatch.muxing.MuxingTree;
import cz.hrnr.matroskabatch.muxing.RemoteMuxingService;
import cz.hrnr.matroskabatch.track.Container;
import cz.hrnr.matroskabatch.track.MuxingItem;
import cz.hrnr.matroskabatch.track.TrackProperties;
import cz.hrnr.matroskabatch.track.properties.BoolProperty;
import cz.hrnr.matroskabatch.track.properties.LangProperty;
import cz.hrnr.matroskabath.utils.LocalUtils;
import cz.hrnr.matroskabath.utils.RemoteUtils;
import cz.hrnr.matroskabath.utils.Utils;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Window;

public class MainWinController implements Initializable {

	// tree managing muxing order
	private MuxingTree t = new MuxingTree();
	private AbstractMuxingService muxingService = new MuxingService();
	private FileChoosers fileChoosers = new LocalFileChoosers();
	private DialogHelpers dialogHelpers = new DialogHelpers();
	private Utils utils = new LocalUtils();

	@FXML
	private TreeView<MuxingItem> mainTree;
	@FXML
	private TextField txtTrackName;
	@FXML
	private ComboBox<LangProperty> cbTrackLang;
	@FXML
	private ComboBox<BoolProperty> cbTrackDefaultF;
	@FXML
	private ComboBox<BoolProperty> cbTrackForcedF;
	@FXML
	private ProgressBar pgbBottom;
	@FXML
	private MenuItem mnbStartMux;
	@FXML
	private MenuItem mnbStopMux;
	@FXML
	private MenuItem mnbConnect;
	@FXML
	private MenuItem mnbDisconnect;

	@FXML
	private void addDirectory(ActionEvent e) {
		Path f = fileChoosers.showDirectoryChooser(getWindow());
		if (f != null) {
			try {
				t.addDirectory(f);
			} catch (IOException ex) {
				dialogHelpers.showErrorDialog("Can't add directory", "Sorry. Couldn't read required files.");
			}
		}
		refreshMainTree();
	}

	@FXML
	private void addFiles(ActionEvent e) {
		List<Path> f = fileChoosers.showMultipleFileChooser(getWindow());
		if (f != null) {
			try {
				t.addFiles(f);
			} catch (IOException ex) {
				dialogHelpers.showErrorDialog("Can't add files", "Sorry. Couldn't read required files.");
			}
		}
		refreshMainTree();
	}

	@FXML
	private void treeVRemoveItem(ActionEvent e) {
		TreeItem<MuxingItem> selected = mainTree.getSelectionModel().getSelectedItem();
		if (selected != null) {
			t.removeTrack(selected.getValue());
			refreshMainTree();
		}
	}

	@FXML
	private void treeVAddItem(ActionEvent e) {
		TreeItem<MuxingItem> selected = mainTree.getSelectionModel().getSelectedItem();
		if (selected != null) {
			Path f = fileChoosers.showSingleFileChooser(getWindow());
			if (f != null) {
				try {
					t.addAllSubTracks(selected.getValue(), utils.getTracks(f));
					refreshMainTree();
				} catch (IOException | InterruptedException ex) {
					dialogHelpers.showErrorDialog("Can't add file", "Sorry. Couldn't read required files.");
				}
			}
		}
	}

	@FXML
	private void propTrackNameChanged(ActionEvent e) {
		TreeItem<MuxingItem> selected = mainTree.getSelectionModel().getSelectedItem();
		if (selected != null && selected.getValue().getProperties() != null) {
			selected.getValue().getProperties().setName(txtTrackName.getText());
		}
	}

	@FXML
	private void propTrackLangChanged(ActionEvent e) {
		TreeItem<MuxingItem> selected = mainTree.getSelectionModel().getSelectedItem();
		if (selected != null && selected.getValue().getProperties() != null) {
			selected.getValue().getProperties().setLanguage(cbTrackLang.getValue());
		}
	}

	@FXML
	private void propTrackDefaultFChanged(ActionEvent e) {
		TreeItem<MuxingItem> selected = mainTree.getSelectionModel().getSelectedItem();
		if (selected != null && selected.getValue().getProperties() != null) {
			selected.getValue().getProperties().setDefaultF(cbTrackDefaultF.getValue());
		}
	}

	@FXML
	private void propTrackForcedFChanged(ActionEvent e) {
		TreeItem<MuxingItem> selected = mainTree.getSelectionModel().getSelectedItem();
		if (selected != null && selected.getValue().getProperties() != null) {
			selected.getValue().getProperties().setForcedF(cbTrackForcedF.getValue());
		}
	}

	@FXML
	private void removeAll(ActionEvent e) {
		t.clear();
		refreshMainTree();
	}

	@FXML
	private void setOutputDir(ActionEvent e) {
		Path f = fileChoosers.showDirectoryChooser(getWindow());
		if (f != null) {
			try {
				t.setOutputDir(f);
			} catch (IOException e1) {
				dialogHelpers.showErrorDialog("couldn't set output directory", "couldn't retrive necessary info");
			}
		}
		refreshMainTree();
	}

	@FXML
	private void startMuxing(ActionEvent e) {
		pgbBottom.setProgress(0);
		mnbStopMux.setDisable(false);
		mnbStartMux.setDisable(true);

		muxingService.progressProperty().addListener(
				(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
				-> Platform.runLater(() -> {
					pgbBottom.setProgress(newValue.doubleValue());
					if (newValue.doubleValue() == 1) {
						completedMuxing();
					}
				})
		);
		muxingService.addTree(t);
	}

	@FXML
	private void stopMuxing(ActionEvent e) {
		mnbStopMux.setDisable(true);
		mnbStartMux.setDisable(false);

		muxingService.shutdown();
	}

	@FXML
	private void closeWin(ActionEvent e) {
		stopMuxing(e);
		Platform.exit();
	}
	
	@FXML
	private void remoteConnect(ActionEvent e) {
		String rawUri = dialogHelpers.showTextInput("Connect to remote MatroskaBatch server", 
				"URI:");
		if (rawUri == null)
			return;
		
		URI uri;
		try {
			uri = new URI(rawUri);
		} catch (URISyntaxException e1) {
			dialogHelpers.showErrorDialog("Invalid URI", "Sorry. Couldn't read provided uri.");
			return;
		}

		injectRemoteUtils(uri);
		setDisableConnect(true);
	}
	
	@FXML
	private void remoteDisconnect(ActionEvent e) {
		injectLocalUtils();
		setDisableConnect(false);
	}

	@FXML
	private void showAbout(ActionEvent e) {
		dialogHelpers.showInfoDialog("Matroska Batch Merge", "version 1.0 (\"Flying Circus\")\n© 2015 Jiri Horner\nThis is free software licensed under MIT License");
	}

	private void completedMuxing() {
		mnbStopMux.setDisable(true);
		mnbStartMux.setDisable(false);

		dialogHelpers.showInfoDialog("Muxing completed", "All files has been succesfully proceeded. Output was written to chosen directory.");
		muxingService.resetProgress();
	}

	private Window getWindow() {
		return mainTree.getScene().getWindow();
	}

	private void refreshMainTree() {
		List<Container> containers = t.getMuxingData();
		final List<TreeItem<MuxingItem>> muxingItems = 
			containers.stream().
				map(container -> {
					TreeItem<MuxingItem> mi = new TreeItem<>(container);
					mi.getChildren().setAll(
						container.getChildrenStream()
							.map(track -> new TreeItem<MuxingItem>(track))
							.peek(t -> t.setGraphic(Icons.getIcon(t.getValue())))
							.collect(Collectors.toList())
					);

					mi.setExpanded(true);
					return mi;
				})
				.collect(Collectors.toList());

		mainTree.getRoot().getChildren().setAll(muxingItems);
	}

	private void refreshProperties() {
		TreeItem<MuxingItem> selected = mainTree.getSelectionModel().getSelectedItem();
		if (selected != null && selected != mainTree.getRoot()) {
			TrackProperties prop = selected.getValue().getProperties();
			if (prop != null) {
				setDisableProperties(false);
				txtTrackName.setText(prop.getName());
				cbTrackLang.setValue(prop.getLanguage());
				cbTrackDefaultF.setValue(prop.getDefaultF());
				cbTrackForcedF.setValue(prop.getForcedF());
			} else {
				setDisableProperties(true);
			}
		} else {
			setDisableProperties(true);
		}
	}

	private void setDisableProperties(boolean state) {
		txtTrackName.setDisable(state);
		cbTrackLang.setDisable(state);
		cbTrackDefaultF.setDisable(state);
		cbTrackForcedF.setDisable(state);

		if (state) {
			txtTrackName.setText(null);
			cbTrackLang.setValue(null);
			cbTrackDefaultF.setValue(null);
			cbTrackForcedF.setValue(null);
		}
	}
	
	private void setDisableConnect(boolean status) {
		mnbConnect.setDisable(status);
		mnbDisconnect.setDisable(!status);
	}

	private void checkMkvMerge() {
		try {
			if (!MatroskaMerge.available()) {
				throw new ReflectiveOperationException();
			}
		} catch (IOException | InterruptedException | ReflectiveOperationException ex) {
			dialogHelpers.showErrorDialog("mkvmerge not found", "mkvmerge must be installed in PATH\nlocal muxing will not work, check README");
//			Platform.exit();
		}
	}
	
	private void injectRemoteUtils(URI uri) {
		RemoteUtils remoteUtils = new RemoteUtils(uri);
		
		utils = remoteUtils;
		t = new MuxingTree(remoteUtils);
		fileChoosers = new RemoteFileChoosers(remoteUtils);
		muxingService = new RemoteMuxingService(uri);
		
		refreshMainTree();
	}
	
	private void injectLocalUtils() {
		LocalUtils localUtils = new LocalUtils();
		
		utils = localUtils;
		t = new MuxingTree();
		fileChoosers = new LocalFileChoosers();
		muxingService = new MuxingService();
		
		refreshMainTree();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		TreeItem<MuxingItem> root = new TreeItem<>();
		root.setExpanded(true);
		mainTree.setRoot(root);
		mainTree.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TreeItem<MuxingItem>> observable, TreeItem<MuxingItem> oldValue, TreeItem<MuxingItem> newValue) -> {
			refreshProperties();
		});

		txtTrackName.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			propTrackNameChanged(null);
		});

		cbTrackLang.setItems(FXCollections.observableArrayList(LangProperty.values()));
		cbTrackDefaultF.setItems(FXCollections.observableArrayList(BoolProperty.values()));
		cbTrackForcedF.setItems(FXCollections.observableArrayList(new BoolProperty[] {BoolProperty.NO, BoolProperty.YES}));

		setDisableProperties(true);

		checkMkvMerge();
	}

}
