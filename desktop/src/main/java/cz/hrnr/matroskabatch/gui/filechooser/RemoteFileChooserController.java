package cz.hrnr.matroskabatch.gui.filechooser;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import cz.hrnr.matroskabatch.gui.DialogHelpers;
import cz.hrnr.matroskabatch.gui.Icons;
import cz.hrnr.matroskabatch.restapi.RESTPath;
import cz.hrnr.matroskabatch.utils.RemoteUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * JavaFX controller for file chooser dialog.
 *
 */
public class RemoteFileChooserController implements Initializable {
	private RemoteUtils remoteUtils;
	private DialogHelpers dialogHelpers = new DialogHelpers();

	private RESTPath currentPath;
	boolean multiselect;

	@FXML
	private ProgressIndicator prgIndicator;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnOpen;
	@FXML
	private ListView<RESTPath> mainList;
	@FXML
	private TextField txtPathBar;

	public RemoteFileChooserController(RemoteUtils remoteUtils) {
		this.remoteUtils = remoteUtils;
	}
	
	/**
	 * Gets directory where user finished selection
	 * @return direcotry where selection finished
	 */
	public Path getResultDirectory() {
		return currentPath;
	}

	@FXML
	private void cancel(ActionEvent e) {
		Stage stage = (Stage) btnCancel.getScene().getWindow();
		currentPath = null;
		stage.close();
	}

	@FXML
	private void open(ActionEvent e) {
		Stage stage = (Stage) btnCancel.getScene().getWindow();

		List<RESTPath> selected = mainList.getSelectionModel().getSelectedItems();
		stage.setUserData(selected);
		stage.close();
	}

	@FXML
	private void listMouseClicked(MouseEvent e) {
		RESTPath selected = mainList.getSelectionModel().getSelectedItem();

		if (e.getButton().equals(MouseButton.PRIMARY) &&
				e.getClickCount() == 2) {
			loadDirectory(selected);
		}
	}

	@FXML
	private void listKeyboardPressed(KeyEvent e) {
		RESTPath selected = mainList.getSelectionModel().getSelectedItem();
		if (e.getCode().equals(KeyCode.ENTER)) {
			loadDirectory(selected);
		}
	}

	private void loadDirectory(RESTPath path) {
		if(!path.isDirectory())
			return;
		
		try {
			prgIndicator.setVisible(true);
			ObservableList<RESTPath> newList = FXCollections.observableArrayList();
	
			RESTPath back = (RESTPath) remoteUtils.getFileParent(path);
			newList.add(back);
	
			List<Path> paths = remoteUtils.listFiles(path);
			newList.addAll(paths.stream().map(x -> (RESTPath) x).collect(Collectors.toList()));
	
			mainList.setItems(newList);
			txtPathBar.setText(path.getPath().toString());
			currentPath = path;
		} catch (IOException e) {
			dialogHelpers.showErrorDialog("can't open", "couldn't fetch directory listing from remote server");
			return;
		} finally {
			prgIndicator.setVisible(false);
		}
	}
	
	/**
	 * Shows dialog and waits for result.
	 * @param multiselect if selection of multiple items should be allowed
	 * @return entries selected by user
	 */
	@SuppressWarnings("unchecked")
	public List<RESTPath> showAndWait(boolean multiselect) {
		this.multiselect = multiselect;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/RemoteFileChooser.fxml"));
		fxmlLoader.setController(this);
		Parent root;
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		Scene scene = new Scene(root);
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Select files or directory");
		stage.setScene(scene);
		stage.showAndWait();

		return (List<RESTPath>) stage.getUserData();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainList.getSelectionModel().setSelectionMode(multiselect ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
		mainList.setCellFactory(new Callback<ListView<RESTPath>, ListCell<RESTPath>>() {
			@Override
			public ListCell<RESTPath> call(ListView<RESTPath> param) {
				return new RESTPathListCell();
			}
		});
		Platform.runLater(() -> {
			try {
				currentPath = remoteUtils.getRemoteRoot();
				loadDirectory(currentPath);
			} catch (IOException e) {
				throw new RuntimeException("couldn't get root", e);
			}
		});
	}

	private static class RESTPathListCell extends ListCell<RESTPath> {
		@Override
		protected void updateItem(RESTPath item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setGraphic(null);
				setText(null);
			} else {
				if(getIndex() == 0)
					setText("..");
				else
					setText(item.toString());
				
				setGraphic(Icons.getIcon(item));
			}
		}
	}
}
