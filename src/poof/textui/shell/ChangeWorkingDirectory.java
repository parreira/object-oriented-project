/** @version $Id: ChangeWorkingDirectory.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.FileSystemManager;
import poof.textui.EntryUnknownException;
import poof.textui.IsNotDirectoryException;


// FIXME: import project-specific classes

/**
 * §2.2.4.
 */
public class ChangeWorkingDirectory extends Command<FileSystemManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public ChangeWorkingDirectory(FileSystemManager receiver /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.CD, receiver /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {/*2,3*/
		String newdir =IO.readString(Message.directoryRequest());
		try{
			_receiver.changeDir(newdir);
			
		}
		catch(poof.EntryUnknownException e){
			throw new EntryUnknownException(e.getMessage());
			
		}
		catch(poof.IsNotDirectoryException e){
			throw new IsNotDirectoryException(e.getMessage());
		}
		
	}

}
