package poof;

/**
 * Exception used if the user doens't exist
 *
 */
public class UserUnknownException extends SystemFilesException {

	/*serial number*/
	private static final long serialVersionUID = 5241169084037597392L;
		
	/**
	 * @param username
	 * 			the message of the exception
	 */
	public UserUnknownException(String username){
		super(username);
	}

}
