package poof;



import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * The point where user interface and core meets 
 *
 */
public class FileSystemManager {
	
	/* The filesystem we are working into */
	private FileSystem receiver;
	
	/* The user who is logged */
	private User logged;
	
	/*the Map that contains all the user's*/
	private Map<String,User> users; 
	
	/*the map that contains all the users removed*/
	private Map<String,User> remusers;
	
	/*the default entry size*/
	private final int ENTRY_SIZE = Entry.getEntrySize();
	
	/**
	 * The constructor of the manager
	 */
	public FileSystemManager(){
		receiver = null;
		logged = null;
	}
	

	
	/*******************************/
	/*****FUNCIONALIDADES MAIN******/
	/*******************************/
	
	/**
	 * Used to log in a user
	 * 
	 * @param username
	 * 				the user we want to log in
	 * @throws UserUnknownException
	 * 				the user does not exist
	 */
	public void logIn(String username) throws UserUnknownException{
		User newlog= this.users.get(username);
		if((newlog == null) || (this.remusers.containsKey(username)) ){
			throw new UserUnknownException(username);
		}
		else{
			this.logged=newlog;
			this.receiver.setActualDir(this.receiver.getFatherDir());
			try {
				this.receiver.changeDir("home");
				this.receiver.changeDir(this.logged.getUsername()); 
			} catch (SystemFilesException e) {
				//if for some reason maindir doesn't exist
			}
		}
	}
	
	/**
	 *  check's if the manager want's to change the actual filesystem for another
	 * @throws FileNotSavedException if the actual filesystem is not saved
	 */
	public void changeFileSystem() throws FileNotSavedException{
		if( this.hasChanged() ){
			throw new FileNotSavedException();
		}
	}
	
	/**
	 *  Creates a FileSystem and set's the change control variable to true
	 */
	public void newFileSystem(){
		this.receiver=new FileSystem();
		this.users = new TreeMap<String,User>();
		
		this.remusers = new TreeMap<String,User>();
		this.users.put("root", new User("root","Super User"));
		try {
			logIn("root");
		} catch (SystemFilesException e) {
			/*if for some reasone main dir doesn't exist*/
		}
	}
	
	/**
	 * Opens a FileSystem that exists in disc
	 * 
	 * @param filename
	 * 			file name
	 * @throws IOException
	 * 			if some IO problem 
	 * @throws ClassNotFoundException 
	 * 			if class of the file not found
	 * @throws FileNotFoundException 
	 * 			if file not found
	 */
	@SuppressWarnings("unchecked")
	public void openFileSystem(String filename) throws IOException, ClassNotFoundException{		
		try {
			FileInputStream fileIn;
			ObjectInputStream in ;
			fileIn = new FileInputStream("/tmp/"+filename+".ser");
			in = new ObjectInputStream(fileIn);
			this.receiver = (FileSystem) in.readObject();			
			this.logged = (User) in.readObject();
			this.users = (Map<String, User>) in.readObject();
			in.close();	
			fileIn.close();

			logIn(this.logged.getUsername());
			this.receiver.setChanged(false);
			
		} catch (UserUnknownException e) {
			//never happen if list of users is not corrupted
		}
		
	}
	
	/**
	 * Saves the actual File System
	 * 
	 * @param filename
	 * 			file name
	 * @throws IOException 
	 * 			if some IO problem
	 */
	public void saveFileSystem(String filename) throws IOException {
		this.receiver.setChanged(false);
		FileOutputStream fileOut;
		ObjectOutputStream out;
		fileOut = new FileOutputStream("/tmp/"+filename+".ser");
		out = new ObjectOutputStream(fileOut);
		out.writeObject(this.receiver);
		out.writeObject(this.logged);
		out.writeObject(this.users);
		out.flush();
		out.close();
        fileOut.close();

	}
	

	/*****************/
	/**** IMPORTS ****/
	/*****************/
	
	/**
	 * If there is an actual import to read, this creates a new filesystem, and read's
	 * line by line the import and writes what the line means into the filesystem
	 * then log's as the root
	 * 
	 * @param datafile the filename
	 * @throws IOException 
	 * 					if some IO problem/error
	 */
	public void readImport(String datafile) throws IOException{
		this.receiver= new FileSystem();
		this.users = new TreeMap<String,User>();
		this.remusers = new TreeMap<String,User>();
		
		this.users.put("root", new User("root","Super User"));
		this.logged= this.users.get("root");
		
		BufferedReader in = new BufferedReader(new FileReader(datafile));
		String line=new String();
		while((line = in.readLine()) != null) {
			parseAndWrite(line);
		}
		in.close();
		try{ logIn("root");}
		catch(SystemFilesException e){/*never happens*/}
	}
	
	/**
	 * USED FOR INTERNAL OPERATIONS
	 * 
	 * Check's out if it is a USER / DIRECTORY / FILE command
	 * 
	 * @param line
	 * 			the line of the import text
	 * 
	 * @param filesystem
	 * 			the new filesystem
	 * @return nothing
	 * @throws IOException 
	 * 
	 * @throws all the exceptions he can get but it will never happen since
	 * 			THE INPUT OF THE IMPORT FILE IS RIGHT
	 * 
	 */
	private void parseAndWrite(String line) {
		String[] lineSplited = line.split("\\|");
		if(lineSplited[0].equals("USER")){
			WriteUser(lineSplited[1],lineSplited[2],lineSplited[3]);
		} else if(lineSplited[0].equals("DIRECTORY")){
			WriteDir(lineSplited[1],lineSplited[2],lineSplited[3]);
				
		} else if(lineSplited[0].equals("FILE")){
			WriteFile(lineSplited[1],lineSplited[2],lineSplited[3],lineSplited[4]);
			
		}
	}

	/**
	 * USED FOR INTERNAL OPERATIONS
	 * 
	 * Creates a file in the file system
	 * 
	 * @param caminho
	 * 			the path to the file
	 * @param dono
	 * 			the owner of the file
	 * @param permissao
	 * 			the permission of the file
	 * @param conteudo
	 * 			the content of the file
	 */
	private void WriteFile(String path, String owner, String permission,String content) {
		String[] caminhoSplit=path.replaceFirst("^/", "").split("/");
		
		this.receiver.setActualDir(this.receiver.getFatherDir());
		for(String line : caminhoSplit){
			if(this.receiver.getActualDir().getEntry(line) != null){
				this.receiver.setActualDir((Directory) this.receiver.getActualDir().getEntry(line));
			}
		}
		File newfile =new File(caminhoSplit[caminhoSplit.length-1],owner,getPermissionSintaxe(permission));
		newfile.addText(content);
		receiver.getActualDir().addEntry(newfile);
	}

	/**
	 * USED FOR INTERNAL OPERATIONS
	 * 
	 * Creates a directory in the file system
	 * 
	 * @param caminho
	 * 		the path to the directory
	 * @param dono
	 * 		the owner of the directory
	 * @param permissao
	 * 		the permission of the directory
	 * @throws all the exceptions he can get but it will never happen since
	 * 			THE INPUT OF THE IMPORT FILE IS RIGHT
	 */
	private void WriteDir(String path, String owner, String permission)  {
		String[] lineSplited=path.replaceFirst("^/", "").split("/");
		this.receiver.setActualDir(this.receiver.getFatherDir());
		for(String line : lineSplited){
			if(this.receiver.getActualDir().getEntry(line) == null){
				this.receiver.getActualDir().addEntry(new Directory(line,this.receiver.getActualDir(),owner,getPermissionSintaxe(permission),ENTRY_SIZE * 2));
				this.receiver.setActualDir((Directory) this.receiver.getActualDir().getEntry(line));
			}
			else{
				this.receiver.setActualDir((Directory) this.receiver.getActualDir().getEntry(line));
			}
		}
		
	}
	
	/**
	 * USED FOR INTERNAL OPERATIONS
	 * 
	 * Turns the syntax of the import file into the filesystem syntax
	 * 
	 * @param permissao
	 * 			the permission 
	 * 
	 * @returns the type of the permission
	 */
	private String getPermissionSintaxe(String permission) {
		if(permission.equals("private")){
			return "-";
		}
		else{
			return "w";
		}
	}

	/**
	 * USED FOR INTERNAL OPERATIONS
	 * 
	 * Creates a user in the file system
	 * 
	 * @param username
	 * 			the user username
	 * @param nome
	 * 			the user name
	 * @param directoria
	 * 			the main directory of the user
	 * @throws IOException 
	 * 
	 * @throws all the exceptions he can get but it will never happen since
	 * 			THE INPUT OF THE IMPORT FILE IS RIGHT
	 */
	private void WriteUser(String username, String name, String directory) {
		try {
			this.createUser(username, name, directory);
		} catch (SystemFilesException e) {
			/*should never happen*/
		}
	}
	
	/*******************************/
	/*****FUNCIONALIDADES SHELL*****/
	/*******************************/
	
	/**
	 * 
	 * @return the list of all the entry's in the actual directory
	 */
	public String list() {	
		Map<String,Entry> allentrys = this.receiver.getActualDir().getEntrys();
		StringBuilder output = new StringBuilder();
		for(Map.Entry<String, Entry> entry : allentrys.entrySet()) {
			output.append( entry.getValue() + entry.getKey() + "\n" );
		}
		output.setLength(output.length() - 1);
		
		return output.toString();		
	}

	/**
	 * Lists a specified entry
	 * 
	 * @param entry_name
	 * 				the name of the entry we want to list
	 * @return 
	 * @throws EntryUnknownException
	 * 				the entry does not exist
	 */
	public String listEntry(String entry_name) throws EntryUnknownException {
		Entry entry = this.receiver.getActualDir().getEntry(entry_name);
		if(entry == null){
			throw new EntryUnknownException(entry_name);
		}
		else{
			return entry + entry_name;
		}
	}
	
	/**
	 * Removes an entry
	 * 
	 * @param entry
	 * 				the name of the entry we want to remove
	 * @throws EntryUnknownException
	 * 				the entry does not exist
	 * @throws AccessDeniedException
	 * 				the access to remove the entry is denied
	 * @throws IllegalRemovalException
	 * 				the entry we want to remove can't be removed
	 */
	public void removeEntry(String entry) throws EntryUnknownException,AccessDeniedException, IllegalRemovalException {
		this.receiver.removeEntry(entry,this.logged.getUsername());
	}

	/**
	 * Changes the directory
	 * 
	 * @param new_directory
	 * 				the directory we want to change to
	 * @throws IsNotDirectoryException
	 * 				the entry is not a directory
	 * @throws EntryUnknownException
	 * 				the entry does not exist
	 */
	public void changeDir(String new_directory) throws IsNotDirectoryException, EntryUnknownException {
		this.receiver.changeDir(new_directory);
	}

	/**
	 * Creates a new directory
	 * 
	 * @param new_directory
	 * 					the name of the new directory
	 * @throws EntryExistsException
	 * 					the entry already exists
	 * @throws AccessDeniedException
	 * 					the access to create a directory is denied
	 */
	public void createDir(String new_directory) throws EntryExistsException, AccessDeniedException {
		this.receiver.createDir(new_directory,this.logged.getUsername());
	}
	
	/**
	 * Used to create a file
	 * 
	 * @param filename
	 * 					the name of the file we want to create
	 * @throws EntryExistsException
	 * 					the entry already exists
	 * @throws AccessDeniedException
	 * 					the access to create a file is denied
	 */
	public void createFile(String filename) throws EntryExistsException, AccessDeniedException {
		this.receiver.createFich(filename,this.logged.getUsername());
	}
	
	/**
	 * Used to add content to a file
	 * 
	 * @param filename
	 * 					the name of the file we want to add the content to
	 * @param line
	 * 					the content we want to add to the file
	 * @throws EntryUnknownException
	 * 					the entry does not exist
	 * @throws IsNotFileException
	 * 					the entry is not a file
	 * @throws AccessDeniedException
	 * 					the access to change the file is denied
	 */
	public void writeFile(String filename, String line) throws EntryUnknownException, IsNotFileException, AccessDeniedException{
		this.receiver.writeFile(filename, line,this.logged.getUsername());
	}

	/**
	 * Used to see the content of the file
	 * 
	 * @param filename
	 * 				the name of the file 
	 * @return
	 * 				the content of the file
	 * @throws EntryUnknownException
	 * 				the entry does not exist
	 * @throws IsNotFileException
	 * 				the entry is not a file
	 */
	public String seeFile(String filename) throws EntryUnknownException, IsNotFileException{
		Entry ent = this.receiver.getActualDir().getEntry(filename);
		if( ent == null){
			throw new EntryUnknownException(filename);
		}
		else{
			if(!(ent instanceof File)){
				throw new IsNotFileException(filename);
			}
			else{
				File file = (File) ent;
				return file.getText();
			}
		}
	}
	
	/**
	 * 
	 * @return the path of the current directory
	 */
	public String showPath() {
		Directory dir = this.receiver.getActualDir();
		StringBuilder output;
		if(dir.getName().equals("/")){
			return "/";
		}
		output = new StringBuilder();
		while(!dir.getName().equals("/")){
			output.insert(0,"/"+dir.getName());
			dir=(Directory) dir.getEntry("..");
		}
		return output.toString();
	}
	
	/**
	 * Changes the permission of an entry
	 * 
	 * @param permission
	 * 					the permission we want on the entry
	 * @param entry
	 * 					the name of the entry
	 * @throws EntryUnknownException
	 * 					the entry does not exist
	 * @throws AccessDeniedException
	 * 					the access to change the permission is denied
	 */
	public void changePermission(String permission, String entry) throws EntryUnknownException, AccessDeniedException{
		this.receiver.changePermission(permission, entry,this.logged.getUsername());
	}

	/**
	 * Changes the owner of an entry
	 * 
	 * @param username
	 * 				the user we want to be the owner of the entry
	 * @param entry
	 * 				the name of the entry
	 * @throws EntryUnknownException
	 * 				the entry does not exist
	 * @throws AccessDeniedException
	 * 				the access to change the owner is denied
	 * @throws UserUnknownException
	 * 				the user does not exist
	 */
	public void changeOwner(String username, String entry)throws EntryUnknownException, AccessDeniedException, UserUnknownException {
		if(this.users.get(username) == null){
			throw new UserUnknownException(username);
		}
		else{
			this.receiver.changeOwner(username, entry,this.logged.getUsername());
		}
	}
	
	
	/*******************************/
	/*****FUNCIONALIDADES USER******/
	/*******************************/
	
	/**
	 * Creates a user
	 * 
	 * @param username
	 * 					the username of the new user
	 * @param name
	 * 					the name of the new user
	 * @param maindir
	 * 					the main directory path for this user
	 * 
	 * @throws AccessDeniedException
	 * 					the access to create a new user is denied
	 * @throws UserExistsException
	 * 					the user already exists
	 */
	public void createUser(String username, String name , String maindir) throws AccessDeniedException, UserExistsException{
		if(!logged.getUsername().equals("root")){
			throw new AccessDeniedException(logged.getUsername());
		}
		else{
			if(this.users.get(username)!= null){
				throw new UserExistsException(username);
			}
			else{
				Directory home = (Directory) this.receiver.getFatherDir().getEntry("home");
				if(home != null){ //caso home exista
					if(home.getEntry(username) == null){ // caso nao exista a main directory
						home.addEntry(new Directory(username,home,username, "-", ENTRY_SIZE * 2));
					}
					else{ // caso exista a main directory
						home.removeEntry(username);
						home.addEntry(new Directory(username,home, username, "-", ENTRY_SIZE * 2));
					}
				}
				else{ // caso a home nao exista : CRIA-SE HOME E A RESPECTIVA MAIN DIRECTORY
					this.receiver.getFatherDir().addEntry(new Directory("home",this.receiver.getFatherDir(),"root","-", ENTRY_SIZE * 2));
					home = (Directory) this.receiver.getFatherDir().getEntry("home");
					home.addEntry(new Directory(username,home ,username, "-", ENTRY_SIZE * 2));
				}
				this.users.put(username, new User(username, name , maindir));
				this.receiver.setChanged(true) ;
			}
		}
	}

	/**
	 * Removes a user from the Map
	 * 
	 * @param username
	 * 				the username of the user we want to remove
	 * 
	 * @throws AccessDeniedException
	 * 					the access to create a new user is denied
	 * @throws UserUnknownException
	 * 					the user doesn't exist
	 */
	public void removeUser(String username) throws AccessDeniedException, UserUnknownException{
		if(!logged.getUsername().equals("root") || username.equals("root")){
			throw new AccessDeniedException(logged.getUsername());
		}
		else{
			if(this.users.get(username)== null){
				throw new UserUnknownException(username);
			}
			else{
				//this.users.remove(username);
				this.receiver.setChanged(true);
				//this.eraseOwner(username,this.receiver.getFatherDir());
				this.remusers.put(username, this.users.get(username));
				
			}
		}
	}

	/**
	 * Gives all this user's ownerships to the root
	 * 
	 * @param username the user to remove
	 * @param the directory to check for this user
	 */
	private void eraseOwner(String username, Directory dir) {
		for(Map.Entry<String, Entry> entry : dir.getEntrys().entrySet()) {
			if(!(entry.getKey().equals(".") || entry.getKey().equals(".."))){
				if(entry.getValue() instanceof Directory ){
					Directory nextdir = (Directory) entry.getValue();
					eraseOwner(username,nextdir);
				}
				if(entry.getValue().getOwner().equals(username)){
					entry.getValue().setOwner("root");
				}		
			}
		}
	}


	/**
	 * 
	 * @return the list of all the users
	 */
	public String listUsers() {
		StringBuilder output = new StringBuilder();
		for(Map.Entry<String,User> entry : this.users.entrySet()) {
			output.append(entry.getValue().toString());
			output.append("\n");
		} 
		return output.toString().substring(0, output.length()-1);
	}

	/*******************************/
	/*****SOME FILESYSTEM INFO******/
	/*******************************/
	/**
	 * 
	 * @throws NoFileSystemException when there is no fileSystem
	 */
	public void hasFileSystem() throws NoFileSystemException {
		if(this.receiver == null){
			throw new NoFileSystemException();
		}
	}
	
	
	/**
	 * 
	 * @return true if the filsystem has changed
	 */
	private boolean hasChanged() {
		if(this.receiver == null){
			return false;
		}
		else{
			return this.receiver.isChanged();
		}
	}
	
	
	
	/**
	 * @param user
	 * @return
	 * @throws EntryUnknownException 
	 * @throws IsNotDirectoryException 
	 */
	public String listaMainDir(String user) throws EntryUnknownException, IsNotDirectoryException{
		String maindirpath = this.users.get(user).getMainDir();
		String[] path = maindirpath.replaceFirst("^/", "").split("/");
		Directory thisdir = this.receiver.getFatherDir();
		for(String dir : path){
			thisdir = (Directory) thisdir.getEntry(dir);
			if(thisdir == null){
				throw new EntryUnknownException(dir);
			}
			else{
				if(!(thisdir instanceof Directory)){
					throw new IsNotDirectoryException(dir);
				}
			}
		}
		
		Map<String,Entry> allentrys = thisdir.getEntrys();
		StringBuilder output = new StringBuilder();
		for(Map.Entry<String, Entry> entry : allentrys.entrySet()) {
			output.append( entry.getValue() + entry.getKey() + "\n" );
		}
		output.setLength(output.length() - 1);
		return output.toString();
	}
	
	
	
	
}
