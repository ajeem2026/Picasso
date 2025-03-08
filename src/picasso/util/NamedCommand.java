package picasso.util;


/**
 * A command with a name (e.g., to display on a button)
 * 
 * @author Robert C Duvall
 */
public class NamedCommand<T> implements Command<T> {
	private String myName;
	private Command<T> myCommand;

	/**
	 * Create command with a name and an action.
	 */
	public NamedCommand(String name, Command<T> action) {
		myName = name;
		myCommand = action;
	}

	/**
	 * Returns name of this command.
	 */
	public String getName() {
		return myName;
	}

	/**
	 * Run the given command.
	 */
	public void execute(T target) {
		myCommand.execute(target);
	}
}
