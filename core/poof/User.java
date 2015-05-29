package poof;

import java.io.Serializable;

/**
 * A simple user
 *
 */
public class User implements Serializable{
	/*serial number*/
	private static final long serialVersionUID = 8455417648816216988L;
	/*the user username*/
	private final String username;
	/*the user name*/
	private final String name;
	/*the main directory path*/
	private String maindirpath;
	
	/**
	 * @param username
	 * 				the user username
	 * @param name the user name
	 * 
	 */
	public User (String username,String name){
		this.username = username;
		this.name = name;
		this.maindirpath = "/home/"+username;
	}
	
	/**
	 * @param username
	 * 				the user username
	 * @param name the user name
	 * @param maindirpath the user main directory path
	 */
	public User (String username, String name, String maindirpath){
		this.username = username;
		this.name = name;
		this.maindirpath = maindirpath;
	}
	
	/**
	 * @return the user name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * @return the user username
	 */
	public String getUsername(){
		return this.username;
	}
	
	/**
	 * @return the main directory path
	 */
	public String getMainDir() {
		return this.maindirpath;
	}
	
	/**
	 * @param dirpath the main directory path
	 */
	public void setMainDir(String dirpath){
		this.maindirpath=dirpath;
	}
	
	
	@Override
	public String toString(){
		return this.username+":"+this.name+":"+this.maindirpath;
	}


	
	
	
}
