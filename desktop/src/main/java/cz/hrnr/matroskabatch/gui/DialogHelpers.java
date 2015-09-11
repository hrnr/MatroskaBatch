package cz.hrnr.matroskabatch.gui;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

public class DialogHelpers {

	public DialogHelpers() {
		super();
	}

	public void showErrorDialog(String header, String text) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

	public void showInfoDialog(String header, String text) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}
	
	public String showTextInput(String header, String text) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText(header);
		dialog.setContentText(text);
		
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent())
			return result.get();
		else
			return null;
	}

}