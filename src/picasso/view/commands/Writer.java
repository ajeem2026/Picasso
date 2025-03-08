package picasso.view.commands;

import javax.swing.JFileChooser;

import picasso.model.Pixmap;
import picasso.util.FileCommand;

/**
 * Save the chosen file.
 * 
 * @author Robert C Duvall
 */
public class Writer extends FileCommand<Pixmap> {
	public Writer() {
		super(JFileChooser.SAVE_DIALOG);
	}

	public void execute(Pixmap target) {
		String fileName = getFileName();
		if (fileName != null) {
			target.write(fileName);
		}
	}
}
