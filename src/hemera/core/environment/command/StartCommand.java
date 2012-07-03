package hemera.core.environment.command;

import hemera.core.environment.enumn.EEnvironment;
import hemera.core.environment.interfaces.ICommand;
import hemera.core.environment.util.UEnvironment;

/**
 * <code>StartCommand</code> defines the unit of logic
 * that starts the Hemera runtime environment using the
 * generated JSVC script. This command does not require
 * any arguments.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class StartCommand implements ICommand {

	@Override
	public void execute(final String[] args) throws Exception {
		// Execute the script as root.
		System.out.println("Starting Hemera runtime environment...");
		final String binDir = UEnvironment.instance.getInstalledBinDir();
		final StringBuilder command = new StringBuilder();
		command.append("sudo ").append(binDir).append(EEnvironment.JSVCStartScriptFile.value);
		final Process process = Runtime.getRuntime().exec(command.toString());
		final int result = process.waitFor();
		if (result != 0) System.err.println("Executing JSVC script failed.");
		else System.out.println("Hemera runtime environment is now running.");
	}
}
