/** @version $Id: ListAllEntries.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;
import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.FileSystemManager;

/**
 * §2.2.1.
 */
public class ListAllEntries extends Command<FileSystemManager> {
	/**
	 * @param receiver
	 */
	public ListAllEntries(FileSystemManager receiver ) {
		super(MenuEntry.LS, receiver);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		IO.println(_receiver.list());
	}

}
