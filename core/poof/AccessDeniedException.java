package poof;

/**
 * User logged doesn't have permission to perform command's
 *
 */
public class AccessDeniedException extends SystemFilesException {

	/*Serial number*/
	private static final long serialVersionUID = -3750818268072271966L;	
	
	/**
	 * @param username
	 * 				the username 
	 */
	public AccessDeniedException(String username){
		super(username);		
	}
	

	
	
	
	
}
