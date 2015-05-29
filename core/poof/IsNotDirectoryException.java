package poof;

/**
 * It is not a directory
 *
 */
public class IsNotDirectoryException extends SystemFilesException {

	/*serial number*/
	private static final long serialVersionUID = -3991789789764418263L;

	/**
	 * Set's up the message of the exception
	 * 
	 * @param entry
	 * 		the entry
	 */
	public IsNotDirectoryException(String entry){
		super(entry);
	}

	
}
