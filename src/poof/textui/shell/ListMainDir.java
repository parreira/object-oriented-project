package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.FileSystemManager;
import poof.textui.EntryUnknownException;
import poof.textui.user.Message;
import poof.textui.IsNotDirectoryException;


// FIXME: import project-specific classes

/**
 * §2.2.2.
 */
public class ListMainDir extends Command<FileSystemManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public ListMainDir(FileSystemManager receiver /*FIXME: add receiver declaration: type must agree with the above*/) {
		super("Listar a directory princial de um utilizador", receiver /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException { /*2*/
		String ent =IO.readString(Message.usernameRequest());
		try{
			IO.println(_receiver.listaMainDir(ent));
		}
		catch(poof.EntryUnknownException e){
			throw new EntryUnknownException(e.getMessage());
		} catch (poof.IsNotDirectoryException e) {
			throw new IsNotDirectoryException(e.getMessage());
		}
		
		
	}

}
