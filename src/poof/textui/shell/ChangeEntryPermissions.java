/** @version $Id: ChangeEntryPermissions.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.FileSystemManager;
import poof.textui.AccessDeniedException;
import poof.textui.EntryUnknownException;

// FIXME: import project-specific classes

/**
 * §2.2.10.
 */
public class ChangeEntryPermissions extends Command<FileSystemManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public ChangeEntryPermissions(FileSystemManager receiver) {
		super(MenuEntry.CHMOD, receiver /*FIXME: receiver argument*/);
	}
	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String ent =IO.readString(Message.nameRequest());
		String perm;
		if(IO.readBoolean(Message.publicAccess())){
			perm="w";
		}
		else{
			perm="-";
		}
		try{
			_receiver.changePermission(perm, ent);
		}
		catch(poof.EntryUnknownException e){
			throw new EntryUnknownException(e.getMessage());
		}
		catch(poof.AccessDeniedException e){
			throw new AccessDeniedException(e.getMessage());
		}
			
	}
}
