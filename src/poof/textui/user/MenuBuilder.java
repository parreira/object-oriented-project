/** @version $Id: MenuBuilder.java,v 1.1 2014/10/01 22:45:55 david Exp $ */
package poof.textui.user;

import poof.FileSystemManager;
import ist.po.ui.Command;
import ist.po.ui.Menu;

// FIXME: import project-specific classes

/**
 * Menu builder for search operations.
 */
public class MenuBuilder {

	/**
	 * @param _receiver 
	 */
	public static void menuFor(FileSystemManager _receiver) {
		Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
				new CreateUser(_receiver),
				new ListAllUsers(_receiver),
				new RemoveUser(_receiver),
				});
		menu.open();
	}

}
