package poof;

/**
 * Exception that happens when we want to load another filesystem
 * but the actual one is not saved
 *
 */
public class FileNotSavedException extends SystemFilesException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 478376478454981416L;

	/**
	 * Nothing to do
	 */
	public FileNotSavedException(){
		super(null);
	}
	

}
