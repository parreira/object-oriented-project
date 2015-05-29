/** @version $Id: ChangeOwner.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.FileSystemManager;
import poof.textui.AccessDeniedException;
import poof.textui.EntryUnknownException;
import poof.textui.UserUnknownException;

// FIXME: import project-specific classes

/**
 * §2.2.11.
 */
public class ChangeOwner extends Command<FileSystemManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public ChangeOwner(FileSystemManager receiver /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.CHOWN, receiver /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String ent =IO.readString(Message.nameRequest());
		String user =IO.readString(Message.usernameRequest());
		try{
			_receiver.changeOwner(user, ent);
		}
		catch(poof.EntryUnknownException e){
			throw new EntryUnknownException(e.getMessage());
		}
		catch(poof.AccessDeniedException e){
			throw new AccessDeniedException(e.getMessage());
		}
		catch(poof.UserUnknownException e){
			throw new UserUnknownException(e.getMessage());
		}
		
		
		
	}

}
