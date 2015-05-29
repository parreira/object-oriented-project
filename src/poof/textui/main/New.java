/** @version $Id: New.java,v 1.1 2014/10/01 22:45:53 david Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.FileNotSavedException;
import poof.FileSystemManager;

/**
 * Open a new file.
 */
public class New extends Command<FileSystemManager> {

	/**
	 * @param receiver
	 */
	public New(FileSystemManager receiver) {
		super(MenuEntry.NEW, receiver);
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
			_receiver.newFileSystem();
		}
	}
}
