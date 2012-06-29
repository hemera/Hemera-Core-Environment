package hemera.core.environment.command;

import hemera.core.environment.deployer.Deployer;
import hemera.core.environment.interfaces.ICommand;

/**
 * <code>DeployCommand</code> defines the logic that
 * deploys a Hemera Application Bundle. It requires
 * the following arguments:
 * <p>
 * @param bundlePath The <code>String</code> path to
 * the bundle file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class DeployCommand implements ICommand {

	@Override
	public void execute(final String[] args) throws Exception {
		if (args == null || args.length < 1) {
			throw new IllegalArgumentException("Bundle file path must be specified.");
		}
		final String bundlePath = args[0];
		new Deployer(bundlePath).start();
	}
}
