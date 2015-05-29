package poof;

/**
 * Is not a file
 *
 */
public class IsNotFileException extends SystemFilesException {

	/*serial number*/
	private static final long serialVersionUID = 8916851311902259004L;

	/**
	 * Set's up the message of the exception
	 * 
	 * @param entry
	 * 			entry name
	 */
	public IsNotFileException(String entry){
		super(entry);
		
	}

}
