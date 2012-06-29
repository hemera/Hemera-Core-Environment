package hemera.core.environment.command;

import hemera.core.environment.interfaces.ICommand;
import hemera.core.environment.uninstaller.Uninstaller;

/**
 * <code>UninstallCommand</code> defines the logic that
 * un-installs the environment. It does not require any
 * arguments.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class UninstallCommand implements ICommand {

	@Override
	public void execute(final String[] args) throws Exception {
		new Uninstaller().uninstall();
	}
}
