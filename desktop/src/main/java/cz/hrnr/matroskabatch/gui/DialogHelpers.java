package cz.hrnr.matroskabatch.gui;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

/**
 * Helper class for showing some basic dialogs.
 * 
 * Uses Alert class which is included only in Java8u40
 * and later.
 *
 */
public class DialogHelpers {

	public DialogHelpers() {
		super();
	}
	
	/**
	 * Shows error dialog.
	 * @param header
	 * @param text
	 * @see AlertType#ERROR
	 */
	public void showErrorDialog(String header, String text) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}
	
	/**
	 * Shows info dialog.
	 * @param header
	 * @param text
	 * @see AlertType#INFORMATION
	 */
	public void showInfoDialog(String header, String text) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}
	
	/**
	 * Shows text input dialog dialog.
	 * @param header
	 * @param text
	 * @return entered text or null
	 * @see TextInputDialog
	 */
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