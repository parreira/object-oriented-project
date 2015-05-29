/** @version $Id: ListAllUsers.java,v 1.1 2014/10/01 22:45:55 david Exp $ */
package poof.textui.user;
import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.FileSystemManager;

// FIXME: import project-specific classes

/**
 * §2.3.2.
 */
public class ListAllUsers extends Command<FileSystemManager>{
	/**
	 * @param receiver
	 */
	public ListAllUsers(FileSystemManager receiver) {
		super(MenuEntry.LIST_USERS,receiver );
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException  {
		IO.println(_receiver.listUsers());
	}
}
