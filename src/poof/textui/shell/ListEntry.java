/** @version $Id: ListEntry.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.FileSystemManager;
import poof.textui.EntryUnknownException;

// FIXME: import project-specific classes

/**
 * §2.2.2.
 */
public class ListEntry extends Command<FileSystemManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public ListEntry(FileSystemManager receiver /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.LS_ENTRY, receiver /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException { /*2*/
		String ent =IO.readString(Message.nameRequest());
		try{
			IO.println(_receiver.listEntry(ent));
		}
		catch(poof.EntryUnknownException e){
			throw new EntryUnknownException(e.getMessage());
		}
		
		
	}

}
