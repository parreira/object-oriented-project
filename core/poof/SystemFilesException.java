package poof;

/**
 * All the poof.SystemFiles exceptions
 *
 */
public abstract class SystemFilesException extends Exception {
	/* Serial Number*/
	private static final long serialVersionUID = -8581290825578605277L;
	/**
	 * Saves the message of the exception
	 * 
	 * @param message
	 * 		the message
	 */
	public SystemFilesException(String message) {
		super(message);
	}
}
