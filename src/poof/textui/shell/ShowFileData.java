/** @version $Id: ShowFileData.java,v 1.1 2014/10/01 22:45:54 david Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.FileSystemManager;
import poof.textui.EntryUnknownException;
import poof.textui.IsNotFileException;

// FIXME: import project-specific classes

/**
 * §2.2.9.
 */
public class ShowFileData extends Command<FileSystemManager>  {
	/**
	 * @param receiver
	 */
	public ShowFileData(FileSystemManager receiver) {
		super(MenuEntry.CAT, receiver );
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String file =IO.readString(Message.fileRequest());
		String output;
		try{
			if((output =_receiver.seeFile(file)) == null){
				return;
			}
			else{
				IO.println(output);
			}
		}
		catch(poof.EntryUnknownException e){
			throw new EntryUnknownException(e.getMessage());
		}
		catch(poof.IsNotFileException e){
			throw new IsNotFileException(e.getMessage());
		}
		
		
	}
}
