/** @version $Id: Login.java,v 1.1 2014/10/01 22:45:52 david Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FileSystemManager;
import poof.NoFileSystemException;
import poof.textui.UserUnknownException;
import poof.textui.shell.Message;


/**
 * §2.1.2.
 */
public class Login extends Command<FileSystemManager> {

	/**
	 * @param receiver
	 */
	public Login(FileSystemManager receiver) {
		super(MenuEntry.LOGIN, receiver, new ValidityPredicate<FileSystemManager>(receiver){
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
		try{
			_receiver.logIn(IO.readString(Message.usernameRequest()));
		}
		catch(poof.UserUnknownException e){
			throw new UserUnknownException(e.getMessage());
		}
	}
}
