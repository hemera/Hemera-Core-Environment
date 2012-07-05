package hemera.core.environment.command;

import hemera.core.environment.enumn.ECommand;
import hemera.core.environment.interfaces.ICommand;
import hemera.core.environment.util.JSVCScriptGenerator;
import hemera.core.environment.util.UEnvironment;
import hemera.core.environment.util.config.Configuration;
import hemera.core.utility.FileUtils;

/**
 * <code>UndeployCommand</code> defines the logic that
 * removes deployed a Hemera Application. It requires
 * the following arguments:
 * <p>
 * @param appName The <code>String</code> name of the
 * application to un-deploy.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class UndeployCommand implements ICommand {

	@Override
	public void execute(final String[] args) throws Exception {
		if (args == null || args.length < 1) {
			throw new IllegalArgumentException("Application name must be specified.");
		}
		final String appName = args[0];
		// Remove the application folder.
		final String path = UEnvironment.instance.getApplicationDir(appName);
		FileUtils.instance.delete(path);
		// Regenerate JSVC scripts.
		final String homeDir = UEnvironment.instance.getInstalledHomeDir();
		final Configuration config = UEnvironment.instance.getConfiguration(homeDir);
		JSVCScriptGenerator.instance.exportScripts(homeDir, config);
		System.out.println(appName + " successfully removed.");
		// Restart the runtime.
		System.out.println("Hemera Runtime Environment will restart now...");
		ECommand.Stop.execute(null);
		ECommand.Start.execute(null);
	}

	@Override
	public String getKey() {
		return "undeploy";
	}

	@Override
	public String getDescription() {
		return this.getKey() + " \"The name of the application to undeploy\"";
	}
}
