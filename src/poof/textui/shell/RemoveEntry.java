/** @version $Id: RemoveEntry.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.FileSystemManager;
import poof.textui.AccessDeniedException;
import poof.textui.EntryUnknownException;
import poof.textui.IllegalRemovalException;

// FIXME: import project-specific classes

/**
 * §2.2.3.
 */
public class RemoveEntry extends Command<FileSystemManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public RemoveEntry(FileSystemManager receiver /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.RM,receiver /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {/*2,5,6*/
		String ent =IO.readString(Message.nameRequest());
		try{
			_receiver.removeEntry(ent);
			
		}
		catch(poof.IllegalRemovalException e){
			throw new IllegalRemovalException();
		}
		catch(poof.EntryUnknownException e){
			throw new EntryUnknownException(e.getMessage());
		}
		catch(poof.AccessDeniedException e){
			throw new AccessDeniedException(e.getMessage());
		}
		
		
	}
}
