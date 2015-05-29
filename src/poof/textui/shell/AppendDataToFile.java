/** @version $Id: AppendDataToFile.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.FileSystemManager;
import poof.textui.AccessDeniedException;
import poof.textui.EntryUnknownException;
import poof.textui.IsNotFileException;
import poof.textui.shell.Message;

/**
 * §2.2.8.
 */
public class AppendDataToFile extends Command<FileSystemManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public AppendDataToFile(FileSystemManager receiver) {
		super(MenuEntry.APPEND, receiver);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {/*2,4,5*/
		String file =IO.readString(Message.fileRequest());
		String line =IO.readString(Message.textRequest());
		try{
			_receiver.writeFile(file, line);
		}
		catch(poof.IsNotFileException e){
			throw new IsNotFileException(e.getMessage());
		}
		catch(poof.EntryUnknownException e){
			throw new EntryUnknownException(e.getMessage());
		}
		catch(poof.AccessDeniedException e){
			throw new AccessDeniedException(e.getMessage());
		}
	}

}
