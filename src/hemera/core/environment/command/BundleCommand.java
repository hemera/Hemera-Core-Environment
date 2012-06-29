package hemera.core.environment.command;

import hemera.core.environment.bundler.Bundler;
import hemera.core.environment.interfaces.ICommand;

/**
 * <code>BundleCommand</code> defines the logic that
 * creates a Hemera Application Bundle. It requires
 * the following arguments:
 * <p>
 * @param hbmPath The <code>String</code> path to the
 * <code>hbm</code> file.
 * @param bundlePath The <code>String</code> path to
 * put the final bundle file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class BundleCommand implements ICommand {

	@Override
	public void execute(final String[] args) throws Exception {
		if (args == null || args.length < 2) {
			throw new IllegalArgumentException("hbm file path and bundle target directory must be specified.");
		}
		final String hbmPath = args[0];
		final String bundlePath = args[1];
		new Bundler().bundle(hbmPath, bundlePath);
	}
}
