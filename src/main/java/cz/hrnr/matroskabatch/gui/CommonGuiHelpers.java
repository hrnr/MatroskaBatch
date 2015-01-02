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

import java.io.File;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class CommonGuiHelpers {

	private CommonGuiHelpers() {
	}

	private static DirectoryChooser dc;
	private static File lastDir;
	private static FileChooser fc;

	public static File showDirectoryChooser(Window w) {
		File f;
		dc.setInitialDirectory(lastDir);
		f = dc.showDialog(w);
		if (f != null) {
			lastDir = f;
		}

		return f;
	}

	public static File showSingleFileChooser(Window w) {
		File f;
		fc.setInitialDirectory(lastDir);
		fc.setTitle("Select file");
		f = fc.showOpenDialog(w);
		if (f != null) {
			lastDir = f.getParentFile();
		}

		return f;
	}

	public static List<File> showMultipleFileChooser(Window w) {
		List<File> f;
		fc.setInitialDirectory(lastDir);
		fc.setTitle("Select file");
		f = fc.showOpenMultipleDialog(w);
		if (f != null && !f.isEmpty()) {
			lastDir = f.get(0).getParentFile();
		}

		return f;
	}

	public static void showErrorDialog(String header, String text) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

	public static void showInfoDialog(String header, String text) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

	public static void initialize() {
		dc = new DirectoryChooser();
		dc.setTitle("Select directory");

		fc = new FileChooser();
	}
}
