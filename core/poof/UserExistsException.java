package poof;

/**
 * Exception used if the user already exists in the file system
 *
 */
public class UserExistsException extends SystemFilesException {

	/*serial number*/
	private static final long serialVersionUID = -6862060953846362654L;
	
	/**
	 * @param username
	 * 			the message to send 
	 */
	public UserExistsException(String username){
		super(username);
	}



}
