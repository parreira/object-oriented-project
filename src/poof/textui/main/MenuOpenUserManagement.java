/** @version $Id: MenuOpenUserManagement.java,v 1.1 2014/10/01 22:45:53 david Exp $ */
package poof.textui.main;

import poof.FileSystemManager;
import poof.NoFileSystemException;
import ist.po.ui.Command;
import ist.po.ui.ValidityPredicate;

// FIXME: import project-specific classes

/**
 * Open user management menu.
 */
public class MenuOpenUserManagement extends Command<FileSystemManager> /* FIXME: select core type for receiver */ {

	/**
	 * @param receiver
	 */
	public MenuOpenUserManagement(FileSystemManager receiver) {
		
		super(MenuEntry.MENU_USER_MGT, receiver,new ValidityPredicate<FileSystemManager>(receiver){
			  public boolean isValid(){
				     try {
						_receiver.hasFileSystem();
						return true;
					} catch (NoFileSystemException e) {
						return false;
					}
				  }
				});
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() {
		poof.textui.user.MenuBuilder.menuFor(_receiver);
	}

}
