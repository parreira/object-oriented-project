package poof;

import java.util.Map;
import java.util.TreeMap;


/**
 * Directorys have entry's (Directory's and Files).
 *
 */
public class Directory  extends Entry {

	/*Serial number. */
	private static final long serialVersionUID = 5823523033148559836L;
	
	
	/*The children of this directory */
	private Map<String,Entry> entry_list;


	/**
	 * Creates the father directory (aka "\" ) and the TreeMap of children
	 * and saves him self in the map as the father and the actual directory
 	 * 
	 * @param name
	 * 			the directory name
	 * @param owner
	 * 			the owner of the directory
	 * @param permission
	 * 			the permission of the directory
	 * @param size
	 * 			the size of the directory
	 */
	
	public Directory(String name, String owner, String permission, int size){
		super(name, owner, permission, size);
		this.entry_list = new TreeMap<String,Entry>();
		this.entry_list.put(".", this);
		this.entry_list.put("..", this);
	}
	
	
	/**
	 * Creates a directory and the TreeMap of children, in this constructor
	 * we specified the father of the Directory we are creating.
	 * 
	 * @param name
	 * 			the name of the directory we are creating
	 * @param father
	 * 			the directory father of this one
	 * @param owner
	 * 			the owner of this directory
	 * @param permission
	 * 			the permission of this directory
	 * @param size
	 * 			the size of this directory
	 */
	
	public Directory(String name, Directory father, String owner, String permission, int size){
		super(name, owner, permission, size);
		this.entry_list = new TreeMap<String,Entry>();
		this.entry_list.put(".", this);
		this.entry_list.put("..", father);
	}
	
	/**
	 * Getter for the Map of the directory
	 * 
	 * @return the map we user to put the entry's.
	 */
	public Map<String,Entry> getEntrys() {
		return this.entry_list;
	}
	
	
	/**
	 * @param entryname the entry name
	 * @return the entry or null if it doesn't exist
	 */
	public Entry getEntry(String entryname) {
		return this.entry_list.get(entryname);
	}
	

	/**
	 * Adds a new entry to the directory
	 * 
	 * @param entry
	 * 			can be either a file or a directory
	 */
	public void addEntry(Entry entry) {
		this.entry_list.put(entry.getName(),entry);
		this.setSize(this.getSize() + ENTRY_SIZE);
	}
	
	/**
	 * Removes the entry of Map of this directory.
	 * 
	 * @param entry
	 * 			the entry we want to remove
	 */
	 public void removeEntry(String entry) {
		this.entry_list.remove(entry);
		this.setSize(this.getSize() - ENTRY_SIZE);
	}

	/*the text representation of the directory is the syntax used in the list entry*/
	@Override
	@SuppressWarnings("nls")
	public String toString() {
		return "d"+" "+this.getPermission()+" "+this.getOwner()+" "+this.getSize()+" ";
	}
	
	
}
