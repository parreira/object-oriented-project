package poof;

/**
 * If the entry already exist's we can't create another entry with the same name
 * 
 */
public class EntryExistsException extends SystemFilesException {

	/*serial number */
	private static final long serialVersionUID = -4477978105066682809L;

	
	/**
	 * Set's up the message
	 * 
	 * @param entry
	 * 			entry name
	 */
	public EntryExistsException(String entry){
		super(entry);
	}

	
}
