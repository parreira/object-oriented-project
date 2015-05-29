package poof;


/**
 * A file is an entry that saves text
 *
 */
public class File extends Entry{
	
	/*serial number*/
	private static final long serialVersionUID = 687904961107216474L;
	
	/*the text of this file*/
	private StringBuilder text;
	
	/**
	 * Creates a File
	 * 
	 * @param name
	 * 			the name of the file
	 * @param owner
	 * 			the owner of the file
	 * @param permission
	 * 			the permission of the file
	 */
	
	public File(String name, String owner, String permission){
		super(name, owner, permission, 0);
		this.text = new StringBuilder();
	}
	
	
	/**
	 * Add's text to the current content of the file
	 * 
	 * @param text
	 * 			the text we want to add to the content of the file
	 */
	public void addText(String text){
		super.setSize(super.getSize() + text.length() + 1);
		this.text.append(text);
		this.text.append('\n');
	}
	
	/**
	 * Getter to the content of the file
	 * 
	 * @return a String with the content of the file
	 */
	public String getText() {
		if(this.text.toString().isEmpty()){
			return null;
		}
		return this.text.toString();
	}

	
	/*the text representation of the directory is the syntax used in the list entry*/
	@Override
	@SuppressWarnings("nls")
	public String toString() {
		return "-" + " " + this.getPermission() + " " + this.getOwner() + " " + this.getSize() + " ";
	}
}
