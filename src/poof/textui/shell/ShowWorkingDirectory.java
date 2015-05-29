/** @version $Id: ShowWorkingDirectory.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import poof.FileSystemManager;
import ist.po.ui.Command;

// FIXME: import project-specific classes

/**
 * §2.2.7.
 */
public class ShowWorkingDirectory extends Command<FileSystemManager>  {
	/**
	 * @param receiver
	 */
	public ShowWorkingDirectory(FileSystemManager receiver ) {
		super(MenuEntry.PWD, receiver);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() {
		IO.println(_receiver.showPath());
	}

}
