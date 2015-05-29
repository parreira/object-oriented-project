package poof;

/**
 * Trying to remove "." or ".." entry's
 *
 */
public class IllegalRemovalException extends SystemFilesException{


	/*serial number*/
	private static final long serialVersionUID = 9179709267832316442L;



	/**
	 *  No message to be saved
	 */
	public IllegalRemovalException() {
		super(null);
	}
	
}
