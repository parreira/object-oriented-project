package poof.textui.user;

import java.io.IOException;

import static ist.po.ui.Dialog.IO;
import poof.FileSystemManager;
import poof.textui.user.Message;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import poof.textui.AccessDeniedException;
import poof.textui.UserUnknownException;
/**
 * @author parreira
 *
 */
public class RemoveUser extends Command<FileSystemManager> {
	
	
	/**
	 * @param receiver
	 */
	public RemoveUser(FileSystemManager receiver) {
		super("Remover utilizador", receiver );
	}

	@Override
	public void execute() throws DialogException, IOException {
		try {
			_receiver.removeUser(IO.readString(Message.nameRequest()));
		} catch (poof.AccessDeniedException e) {
			throw new AccessDeniedException(e.getMessage());
		} catch (poof.UserUnknownException e) {
			throw new UserUnknownException(e.getMessage());
		}
	}
}
