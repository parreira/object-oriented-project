package poof;

/**
 * The filesystem that holds the directory's and the user's
 *
 */
public class FileSystem implements  java.io.Serializable{

	private static final long serialVersionUID = 7181398136198832550L;
	
	/*the first of all directorys ("/")*/
	private final Directory father_directory;
	
	/*the actual directory in work*/
	private Directory actual_directory;

	/*control variable that is set to true everytime something is changed in the filesystem*/
	private boolean changed;
	
	/*the entry size constant*/
	private final int ENTRY_SIZE = Entry.getEntrySize();
	

	/**
	 * Default constructor of a filesystem.
	 */
	public FileSystem(){
		this.father_directory= new Directory("/","root","-",2 * 8);
		this.father_directory.addEntry(new Directory("home",this.father_directory,"root","-", ENTRY_SIZE * 2));
		this.actual_directory=(Directory) this.father_directory.getEntry("home");
		this.actual_directory.addEntry(new Directory("root",this.actual_directory ,"root", "-", ENTRY_SIZE * 2));
		this.actual_directory=(Directory) this.actual_directory.getEntry("root");
		this.setChanged(true) ;
	}

	/***************************************/
	/***********GETTERS && SETTERS**********/
	/***************************************/
	
	/**
	 * @return the changed
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * @param changed the changed to set
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	
	/**
	 * 
	 * @return the filesystem father directory
	 */
	public Directory getFatherDir() {
		return this.father_directory;
	}
	
	/**
	 * 
	 * @return the actual directory of the filesystem
	 */
	public Directory getActualDir() {
		return this.actual_directory;
	}
	
	/**
	 * Setter to the actual directory
	 * @param directory
	 * 				the directory we want as the actual directory
	 */
	public void setActualDir(Directory directory){
		this.actual_directory = directory;
	}
	
	/***************************************/
	/*********PERMISSIONS && OWNERS*********/
	/***************************************/
	
	
	
	/**
	 * Used to check permission of different parameters
	 * 
	 * @param username
	 * 				the username we want check for permission
	 * @param owner
	 * 				the owner of an entry
	 * @param permission
	 * 				the permission of an entry
	 * 
	 * @return true is the user has permission, false if not.
	 */
	private boolean checkPermission(String username, String owner, String permission) {
		if(owner.equals(username) || permission.equals("w") || username.equals("root")){
			return true;
		}
		return false;
	}
	
	/**
	 * Changes the permission of an entry if it's able to do it.
	 * 
	 * @param permission
	 * 				the permission of the entry
	 * @param ent
	 * 				the name of the entry
	 * @param logged
	 * 				the user who is logged at the moment
	 * @throws EntryUnknownException
	 * 				the entry does not exist
	 * @throws AccessDeniedException
	 * 				the permission to change the permission is negated
	 */
	public void changePermission(String permission, String ent, String logged) throws EntryUnknownException, AccessDeniedException{
		Entry entry = this.actual_directory.getEntry(ent);
		if( entry == null){
			throw new EntryUnknownException(ent);
		}
		else{
			if(!(logged.equals(entry.getOwner()))){
				throw new AccessDeniedException(logged);
			}
			else{
				entry.setPermission(permission);
				this.setChanged(true) ;
				
			}
		}	
	}
	
	/**
	 * Changes the owner of an entry if it has the permission to do it
	 * @param user
	 * 				the user we want to change the owner to
	 * @param ent
	 * 				the entry we want to change the owner
	 * @param logged
	 * 				the owner of the entry
	 * @throws EntryUnknownException
	 * 				the entry does not exist
	 * @throws AccessDeniedException
	 * 				the access to change the owner is denied
	 * @throws UserUnknownException
	 * 				the user does not exist
	 */
	public void changeOwner(String user, String ent,String logged)throws EntryUnknownException, AccessDeniedException, UserUnknownException {
			Entry entry = this.actual_directory.getEntry(ent);
			if( entry == null){
				throw new EntryUnknownException(ent);
			}
			else{
				if(!(logged.equals(entry.getOwner()))){
					throw new AccessDeniedException(logged);
				}
				else{
					entry.setOwner(user);
					this.setChanged(true) ;
				}
			}	
		
	}
	
	/***************************************/
	/***************ENTRYS******************/
	/***************************************/
	
	/**
	 * Removes an entry if the logged used has the permissions to do it
	 * 
	 * @param ent
	 * 				the entry we want to remove
	 * @param logged
	 * 				the user who wants to remove the entry
	 * @throws EntryUnknownException
	 * 				the entry does not exist
	 * @throws AccessDeniedException
	 * 				the access to remove the entry is denied
	 * @throws IllegalRemovalException
	 * 				the "." and ".." can't be removed
	 */
	public void removeEntry(String ent,String logged) throws EntryUnknownException,AccessDeniedException, IllegalRemovalException {
		Entry entry;
		if(ent.equals(".")|| ent.equals("..")){
			throw new IllegalRemovalException();
		}
		else{
			if((entry = this.actual_directory.getEntry(ent)) == null){
				throw new EntryUnknownException(ent);
			}
			else {
				if(checkPermission(logged,entry.getOwner(),entry.getPermission()) == false || 
				   checkPermission(logged,this.actual_directory.getOwner(),this.actual_directory.getPermission()) == false){
					throw new AccessDeniedException(logged);
				}
				else{
					this.actual_directory.removeEntry(ent);
					this.setChanged(true) ;
					
				}
			}
		}
		
	}

			/***************************************/
			/**************DIRECTORYS***************/
			/***************************************/
	
	/**
	 * Used to change the actual directory
	 * 
	 * @param new_directory
	 * 				the directory we want to have as the new actual directory
	 * @throws IsNotDirectoryException
	 * 				the entry is not a directory
	 * @throws EntryUnknownException
	 * 				the directory does not exist
	 */
	public void changeDir(String new_directory) throws IsNotDirectoryException, EntryUnknownException {
		Entry entry = this.actual_directory.getEntry(new_directory);
		if(entry == null ){
			throw new EntryUnknownException(new_directory);
		}
		else{
			if(!(entry instanceof Directory)){
				throw new IsNotDirectoryException(new_directory);
			}
			else{
				this.actual_directory = (Directory) entry;
				this.setChanged(true) ;
				
			}
		}
	}

	/**
	 * Creates a new directory
	 * 
	 * @param new_directory
	 * 					the name of the directory we want to create
	 * @param logged
	 * 					the user who is logged at the moment
	 * @throws EntryExistsException
	 * 					the entry we want to create already exists
	 * @throws AccessDeniedException
	 * 					the access to create a directory is denied
	 */

	public void createDir(String new_directory,String logged) throws EntryExistsException, AccessDeniedException {
		Entry ent = this.actual_directory.getEntry(new_directory);
		if(ent != null){
			throw new EntryExistsException(new_directory);
		}
		else{
			if(!this.checkPermission(logged,this.actual_directory.getOwner(), this.actual_directory.getPermission())){
				throw new AccessDeniedException(logged);
			}
			else{
				this.actual_directory.addEntry(new Directory(new_directory, this.actual_directory,logged,"-", ENTRY_SIZE * 2));
				this.setChanged(true) ;
				
			}
		}
	}
	
			/***************************************/
			/*****************FILES*****************/
			/***************************************/
	
	
	
	/**
	 * Creates a new file
	 * 
	 * @param filename
	 * 					the name of the new file
	 * @param logged
	 * 					the user who is logged at the moment
	 * @throws EntryExistsException
	 * 					the entry we want to create already exists
	 * @throws AccessDeniedException
	 * 					the access to create a new file is denied
	 */
	public void createFich(String filename,String logged) throws EntryExistsException, AccessDeniedException {
		Entry ent = this.actual_directory.getEntry(filename);
		if(ent != null){
			throw new EntryExistsException(filename);
		}
		else{
			if(!this.checkPermission(logged,this.actual_directory.getOwner(), this.actual_directory.getPermission())){
				throw new AccessDeniedException(logged);
			}
			else{
				this.actual_directory.addEntry(new File(filename, logged,"-"));
				this.setChanged(true) ;
				
			}
		}
		
		
	}



	/**
	 * Used to add content to the file
	 * 
	 * @param filename
	 * 				the name of the file we want to add content to
	 * @param line
	 * 				the text we want to add to the file
	 * @param logged
	 * 				the user who is logged at the moment
	 * @throws EntryUnknownException
	 * 				the file does not exist
	 * @throws IsNotFileException
	 * 				the entry is not a file
	 * @throws AccessDeniedException
	 * 				the access to change the file is denied
	 */
	public void writeFile(String filename, String line,String logged) throws EntryUnknownException, IsNotFileException, AccessDeniedException{
		Entry ent = this.actual_directory.getEntry(filename);
		if( ent == null){
			throw new EntryUnknownException(filename);
		}
		else{
			if(!(ent instanceof File)){
				throw new IsNotFileException(filename);
			}
			else{
				File file = (File) ent;
				if(!this.checkPermission(logged, ent.getOwner(), ent.getPermission())){
					throw new AccessDeniedException(logged);
				}
				else{
					file.addText(line);
					this.setChanged(true) ;
					
				}
			}
		}
		
	}
	
}