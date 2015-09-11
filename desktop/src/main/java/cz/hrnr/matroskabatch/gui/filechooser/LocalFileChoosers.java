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
package cz.hrnr.matroskabatch.gui.filechooser;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class LocalFileChoosers implements FileChoosers {
	public LocalFileChoosers() {
		dc = new DirectoryChooser();
		dc.setTitle("Select directory");
		
		fc = new FileChooser();
	}

	private DirectoryChooser dc;
	private File lastDir;
	private FileChooser fc;

	/* (non-Javadoc)
	 * @see cz.hrnr.matroskabatch.gui.FileChoosers#showDirectoryChooser(javafx.stage.Window)
	 */
	@Override
	public Path showDirectoryChooser(Window w) {
		File f;
		dc.setInitialDirectory(lastDir);
		f = dc.showDialog(w);
		if (f != null) {
			lastDir = f;
		}

		return f.toPath();
	}

	/* (non-Javadoc)
	 * @see cz.hrnr.matroskabatch.gui.FileChoosers#showSingleFileChooser(javafx.stage.Window)
	 */
	@Override
	public Path showSingleFileChooser(Window w) {
		File f;
		fc.setInitialDirectory(lastDir);
		fc.setTitle("Select file");
		f = fc.showOpenDialog(w);
		if (f != null) {
			lastDir = f.getParentFile();
		}

		return f.toPath();
	}

	/* (non-Javadoc)
	 * @see cz.hrnr.matroskabatch.gui.FileChoosers#showMultipleFileChooser(javafx.stage.Window)
	 */
	@Override
	public List<Path> showMultipleFileChooser(Window w) {
		List<File> f;
		fc.setInitialDirectory(lastDir);
		fc.setTitle("Select file");
		f = fc.showOpenMultipleDialog(w);
		if (f != null && !f.isEmpty()) {
			lastDir = f.get(0).getParentFile();
		}
		
		
		List<Path> paths = null;
		if(f!=null) {
			paths = f.stream().map(x -> x.toPath()).collect(Collectors.toList());
		}

		return paths;
	}
}
