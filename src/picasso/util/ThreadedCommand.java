package picasso.util;

import javax.swing.JComponent;


/**
 * Execute a long running command in a separate thread and display the results
 * in a component while the command is running.
 * 
 * @author Robert C Duvall
 * @author Sara Sprenkle - fixed bug with initial isDone parameter
 */
public class ThreadedCommand<T> implements Command<T> {
	private static final int DELAY = 400; // in milliseconds

	private Command<T> myCommand;
	private JComponent myView;
	private boolean isDone;

	/**
	 * Create a command that runs the given command and updates the given view
	 * over time.
	 */
	public ThreadedCommand(JComponent view, Command<T> command) {
		myCommand = command;
		myView = view;
		isDone = true;
	}

	/**
	 * Run the command on the target or cancel it if is already running.
	 */
	public void execute(final T target) {
		if (isDone) {
			// just starting ...
			isDone = false;

			// start thread for long task that does visible work
			Thread worker = new Thread() {
				public void run() {
					myCommand.execute(target);
					isDone = true;
				}
			};
			worker.start();

			// start thread to view changes worker does
			Thread painter = new Thread() {
				public void run() {
					try {
						while (!isDone) {
							Thread.sleep(DELAY);
							myView.repaint();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			painter.start();
		} else {
			// allow action to be canceled
			isDone = true;
		}
	}
}
