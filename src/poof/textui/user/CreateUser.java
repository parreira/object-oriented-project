/** @version $Id: CreateUser.java,v 1.1 2014/10/01 22:45:55 david Exp $ */
package poof.textui.user;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import poof.FileSystemManager;
import poof.textui.AccessDeniedException;
import poof.textui.UserExistsException;
import poof.textui.user.Message;

// FIXME: import project-specific classes

/**
 * §2.3.1.
 */
public class CreateUser extends Command<FileSystemManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public CreateUser(FileSystemManager receiver) {
		super(MenuEntry.CREATE_USER, receiver);
	}

	
	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		
		String username =IO.readString(Message.usernameRequest());
		String name =IO.readString(Message.nameRequest());
		try{
			_receiver.createUser(username, name, "/home/" + username);
		}
		catch(poof.AccessDeniedException e){
			throw new AccessDeniedException(e.getMessage());
		}
		catch(poof.UserExistsException e){
			throw new UserExistsException(e.getMessage());
		}
		
	}
}
