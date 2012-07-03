package hemera.core.environment.command;

import java.io.File;

import hemera.core.environment.enumn.EEnvironment;
import hemera.core.environment.interfaces.ICommand;
import hemera.core.environment.util.UEnvironment;

/**
 * <code>StatusCommand</code> defines the unit of logic
 * that checks the running status of the Hemera runtime
 * environment. It does not require any arguments.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class StatusCommand implements ICommand {

	@Override
	public void execute(final String[] args) throws Exception {
		// Check if PID file exists.
		final String binDir = UEnvironment.instance.getInstalledBinDir();
		final String path = binDir + EEnvironment.JSVCPIDFile.value;
		final File pidFile = new File(path);
		if (pidFile.exists()) {
			System.out.println("Hemera runtime environment is currently running.");
		} else {
			System.out.println("Hemera runtime environment is not running.");
		}
	}
	
	@Override
	public String getKey() {
		return "status";
	}

	@Override
	public String getDescription() {
		return this.getKey();
	}
}
