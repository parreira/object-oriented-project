/** @version $Id: Save.java,v 1.1 2014/10/01 22:45:53 david Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FileSystemManager;
import poof.NoFileSystemException;

/**
 * Save to file under current name (if unnamed, query for name).
 */
public class Save extends Command<FileSystemManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public Save(FileSystemManager receiver) {
		super(MenuEntry.SAVE, receiver,new ValidityPredicate<FileSystemManager>(receiver){
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
	public final void execute() throws DialogException, IOException {
		_receiver.saveFileSystem(IO.readString(Message.newSaveAs()));
	}
}
