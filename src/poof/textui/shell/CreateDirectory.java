/** @version $Id: CreateDirectory.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.FileSystemManager;
import poof.textui.AccessDeniedException;
import poof.textui.EntryExistsException;

// FIXME: import project-specific classes

/**
 * §2.2.6.
 */
public class CreateDirectory extends Command<FileSystemManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public CreateDirectory(FileSystemManager receiver /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.MKDIR, receiver /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String newdir =IO.readString(Message.directoryRequest());
		try{
			_receiver.createDir(newdir);
		}
		catch(poof.EntryExistsException e){
			throw new EntryExistsException(e.getMessage());
			
		}
		catch(poof.AccessDeniedException e){
			throw new AccessDeniedException(e.getMessage());
		}
		
		
	}

}
