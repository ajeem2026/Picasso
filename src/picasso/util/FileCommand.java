package picasso.util;

import javax.swing.JFileChooser;

/**
 * An abstract command that allows access to the file system through a
 * JFileChooser dialog box. Child classes determine what to do with the file
 * once it is chosen.
 * 
 * @author Robert C Duvall
 */
public abstract class FileCommand<T> implements Command<T> {
	// only one dialog box needed for an application
	private static final JFileChooser ourChooser = new JFileChooser(System.getProperties().getProperty("user.dir"));

	// what kind of dialog to open, see JFileChooser constants
	private int myDialogType;

	/**
	 * Create command that will pop-up the given type of JFileChooser dialog
	 * 
	 * @param dialogType the type of dialog (see JFileChooser constants)
	 */
	public FileCommand(int dialogType) {
		myDialogType = dialogType;
	}

	/**
	 * Pops up the appropriate file dialog box. Returns the name of the file chosen
	 * by the user or null if no file chosen.
	 * 
	 * @return the name of the file chosen by the user or null if no file chosen.
	 */
	protected String getFileName() {
		ourChooser.setDialogType(myDialogType);
		int response = ourChooser.showDialog(null, null);
		if (response == JFileChooser.APPROVE_OPTION) {
			return ourChooser.getSelectedFile().getPath();
		}
		return null;
	}
}
