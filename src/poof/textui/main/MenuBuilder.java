/** @version $Id: MenuBuilder.java,v 1.1 2014/10/01 22:45:52 david Exp $ */
package poof.textui.main;

import poof.FileSystemManager;
import ist.po.ui.Command;
import ist.po.ui.Menu;

// FIXME: import project-specific classes


/**
 * Menu builder.
 */
public abstract class MenuBuilder {

	/**
	 * @param receiver
	 */
	public static void menuFor(FileSystemManager receiver) {
		Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
				new New(receiver),
				new Open(receiver),
				new Save(receiver),
				new Login(receiver),
				new MenuOpenShell(receiver),
				new MenuOpenUserManagement(receiver)
		});
		menu.open();
	}

}
