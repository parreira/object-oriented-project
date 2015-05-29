package poof;

/**
 * When there is no FileSystem open
 *
 */
public class NoFileSystemException extends SystemFilesException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6211582848690338689L;

	/**
	 * 
	 */
	public NoFileSystemException() {
		super(null);
	}
	

}
