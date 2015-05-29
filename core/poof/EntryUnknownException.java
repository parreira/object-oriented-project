package poof;

/**
 * If the entry doens't exist
 *
 */
public class EntryUnknownException extends SystemFilesException {

	/*serial number*/
	private static final long serialVersionUID = -7016264193182138144L;
	/**
	 * Set's up the entry on the exception message
	 * 
	 * @param entry
	 * 		entry name
	 */
	public EntryUnknownException(String entry){
		super(entry);
	}
}
