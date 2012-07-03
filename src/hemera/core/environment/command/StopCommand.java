package hemera.core.environment.command;

import hemera.core.environment.enumn.EEnvironment;
import hemera.core.environment.interfaces.ICommand;
import hemera.core.environment.util.UEnvironment;
import hemera.core.utility.shell.Shell;
import hemera.core.utility.shell.ShellResult;

/**
 * <code>StopCommand</code> defines the unit of logic
 * that stops the Hemera runtime environment using the
 * generated JSVC script. This command does not require
 * any arguments.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class StopCommand implements ICommand {

	@Override
	public void execute(final String[] args) throws Exception {
		System.out.println("Stopping Hemera runtime environment...");
		// Check if runtime is running.
		final boolean running = UEnvironment.instance.isRunning();
		if (!running) {
			System.out.println("No PID file found. Hemera is currently not running.");
		} else {
			// Execute the script as root.
			final String binDir = UEnvironment.instance.getInstalledBinDir();
			final ShellResult result = Shell.instance.executeAsRoot(binDir+EEnvironment.JSVCStopScriptFile.value);
			if (result.code != 0) {
				if (result.code == 255) {
					System.err.println("No PID file found. Hemera is currently not running.");
				} else {
					System.err.println("Executing JSVC script failed: " + result.code);
					System.err.println(result.output);
				}
			}
			else System.out.println("Hemera runtime environment is now stopped.");
		}
	}
	
	@Override
	public String getKey() {
		return "stop";
	}

	@Override
	public String getDescription() {
		return this.getKey();
	}
}
