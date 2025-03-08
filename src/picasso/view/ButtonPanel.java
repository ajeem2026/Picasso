package picasso.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JButton;

import picasso.model.Pixmap;
import picasso.util.Command;
import picasso.util.NamedCommand;

/**
 * The collection of commands represented as buttons that apply to the active
 * image.
 * 
 * @author Robert C Duvall
 */
@SuppressWarnings("serial")
public class ButtonPanel extends JPanel {
	private Canvas myView;

	/**
	 * Create panel that will update the given picasso.view.
	 * 
	 * @param view the Canvas that the panel's buttons update
	 */
	public ButtonPanel(Canvas view) {
		myView = view;
	}

	/**
	 * Add the given Command as a button with the given button text. When the button
	 * is clicked, the command is executed and the associated canvas is updated.
	 * 
	 * @param buttonText the text for the button
	 * @param action     the action associated with the new button
	 */
	public void add(String buttonText, final Command<Pixmap> action) {
		JButton button = new JButton(buttonText);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action.execute(myView.getPixmap());
				myView.refresh();
			}
		});
		add(button);
	}

	/**
	 * Add the given Command as a button. The button's text will be the given
	 * command's name.
	 * 
	 * @param action the command associated with the new button
	 */
	public void add(NamedCommand<Pixmap> action) {
		add(action.getName(), action);
	}
}
