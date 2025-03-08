package picasso.util;

/**
 * An abstract command class that operates on some target object.
 * 
 * @author Robert C Duvall
 */
public interface Command<T> {
	/**
	 * Implementations determine how to update the given target object
	 */
	public void execute(T target);
}
