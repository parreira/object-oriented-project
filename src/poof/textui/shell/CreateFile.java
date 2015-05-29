/** @version $Id: CreateFile.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
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
 * §2.2.5.
 */
public class CreateFile extends Command<FileSystemManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public CreateFile(FileSystemManager receiver /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.TOUCH, receiver /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException { /*1,5*/
		String filename =IO.readString(Message.fileRequest());
		
		try{
			_receiver.createFile(filename);
		}
		catch(poof.EntryExistsException e){
			throw new EntryExistsException(e.getMessage());
			
		}
		catch(poof.AccessDeniedException e){
			throw new AccessDeniedException(e.getMessage());
		}
		
		
	}

}
