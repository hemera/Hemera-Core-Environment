package hemera.core.environment.command;

import hemera.core.environment.installer.Installer;
import hemera.core.environment.interfaces.ICommand;

/**
 * <code>InstallCommand</code> defines the logic that
 * installs the environment. It requires the following
 * arguments:
 * <p>
 * @param homeDir the <code>String</code> directory
 * to install the environment to.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class InstallCommand implements ICommand {

	@Override
	public void execute(final String[] args) throws Exception {
		if (args == null || args.length < 1) {
			throw new IllegalArgumentException("Hemera home directory must be specified.");
		}
		final String homeDir = args[0];
		new Installer().install(homeDir);
	}
}
