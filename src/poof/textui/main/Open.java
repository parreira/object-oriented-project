/** @version $Id: Open.java,v 1.1 2014/10/01 22:45:53 david Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.FileNotFoundException;
import java.io.IOException;

import poof.FileNotSavedException;
import poof.FileSystemManager;

/**
 * Open existing file.
 */
public class Open extends Command<FileSystemManager>  {

	/**
	 * @param receiver
	 */
	public Open(FileSystemManager receiver) {
		super(MenuEntry.OPEN, receiver);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		try{
			_receiver.changeFileSystem();
		}
		catch(FileNotSavedException e){
			if(IO.readBoolean(Message.saveBeforeExit())){
				_receiver.saveFileSystem(IO.readString(Message.saveAs()));
			}
		}
		finally{
			try{
				_receiver.openFileSystem(IO.readString(Message.openFile()));
			}
			catch(FileNotFoundException | ClassNotFoundException e){
				IO.println(Message.fileNotFound());
			}
		}
	}

}
