/** @version $Id: Shell.java,v 1.1 2014/10/01 22:45:51 david Exp $ */
package poof.textui;

import static ist.po.ui.Dialog.IO;

import java.io.IOException;

import poof.FileSystemManager;

/**
 * Class that starts the application's textual interface.
 */
public class Shell {
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FileSystemManager FSB= new FileSystemManager();
		
		String datafile = System.getProperty("import"); //$NON-NLS-1$
		if (datafile != null) {
			FSB.readImport(datafile);
		}
		poof.textui.main.MenuBuilder.menuFor(FSB);
		IO.closeDown();
	}

}
