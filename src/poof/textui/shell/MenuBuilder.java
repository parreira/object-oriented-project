/** @version $Id: MenuBuilder.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import poof.FileSystemManager;
import ist.po.ui.Command;
import ist.po.ui.Menu;

// FIXME: import project-specific classes

/**
 * Menu builder for shell operations.
 */
public class MenuBuilder {

	/**
	 * @param receiver
	 */
	public static void menuFor(FileSystemManager receiver) {
		Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
				new ListAllEntries(receiver /*FIXME: receiver argument*/),
				new ListEntry(receiver /*FIXME: receiver argument*/),
				new RemoveEntry(receiver/*FIXME: receiver argument*/),
				new ChangeWorkingDirectory(receiver /*FIXME: receiver argument*/),
				new CreateFile(receiver /*FIXME: receiver argument*/),
				new CreateDirectory(receiver /*FIXME: receiver argument*/),
				new ShowWorkingDirectory(receiver /*FIXME: receiver argument*/),
				new AppendDataToFile(receiver /*FIXME: receiver argument*/),
				new ShowFileData(receiver /*FIXME: receiver argument*/),
				new ChangeEntryPermissions(receiver /*FIXME: receiver argument*/),
				new ChangeOwner(receiver /*FIXME: receiver argument*/),
				new ListMainDir(receiver)
				});
		menu.open();
	}

}
