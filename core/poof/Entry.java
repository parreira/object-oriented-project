package poof;


/**
 * Entry's can be either directory's or file's 
 *
 */
public abstract class Entry implements java.io.Serializable{

	/*serial number */
	private static final long serialVersionUID = 2762297931559496979L;
	
	/*the owner of this entry*/
	private String owner;
	
	/*the permission of this entry*/
	private String permission;
	
	/*the name of this entry*/
	private String name;
	
	/*the size of this entry*/
	private int size;
	
	protected static final int ENTRY_SIZE = 8;
	
	/**
	 * @param name
	 * 			the name of the entry
	 * @param owner
	 * 			the owner of the entry
	 * @param permission
	 * 			the permission of the entry
	 * @param size
	 * 			the size of the entry
	 */
	public Entry(String name, String owner, String permission, int size){
		this.name = name;
		this.owner = owner;
		this.permission = permission;
		this.size = size;
	}
	
	/**
	 * @return the name of this entry
	 */
	public String getName(){
		return this.name;
	}
	
	
	
	/**
	 * Set's up the permission of this entry
	 * 
	 * @param permission 
	 * 			the permission of this entry
	 * 
	 */
	public void setPermission(String permission){
		this.permission = permission;
	}
	
	/**
	 * @return this entry's permission
	 */
	public String getPermission(){
		return this.permission;
	}
	
	/**
	 * Set's up the owner of this entry
	 * 
	 * @param owner
	 * 			the owner of this entry
	 */
	public void setOwner(String owner){
		this.owner = owner;
	}
	
	/**
	 * @return this entry's owner
	 */
	public String getOwner(){
		return this.owner;
	}
	
	/**
	 * Set's up the size of this entry
	 * 
	 * @param size
	 */
	public void setSize(int size){
		this.size = size;
	}
	
	
	/**
	 * @return this entry's size
	 */
	public int getSize(){
		return this.size;
	}
	
	/**
	 * @return the default entry size (8)
	 */
	public static int getEntrySize(){
		return ENTRY_SIZE;
	}
	
	
	
}
