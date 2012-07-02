package hemera.core.environment.enumn;

import hemera.core.environment.command.BundleCommand;
import hemera.core.environment.command.DeployCommand;
import hemera.core.environment.command.InstallCommand;
import hemera.core.environment.command.StartCommand;
import hemera.core.environment.command.StopCommand;
import hemera.core.environment.command.UndeployCommand;
import hemera.core.environment.command.UninstallCommand;
import hemera.core.environment.interfaces.ICommand;

/**
 * <code>ECommand</code> defines the enumerations of
 * all the supported commands.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum ECommand implements ICommand {
	/**
	 * The install command.
	 */
	Install("install", new InstallCommand()),
	/**
	 * The bundle command.
	 */
	Bundle("bundle", new BundleCommand()),
	/**
	 * The deploy command.
	 */
	Deploy("deploy", new DeployCommand()),
	/**
	 * The un-deploy command.
	 */
	Undeploy("undeploy", new UndeployCommand()),
	/**
	 * The start command.
	 */
	Start("start", new StartCommand()),
	/**
	 * The stop command.
	 */
	Stop("stop", new StopCommand()),
	/**
	 * The un-install command.
	 */
	Uninstall("uninstall", new UninstallCommand());
	
	/**
	 * Parse the given value into the corresponding
	 * command.
	 * @param value The <code>String</code> input.
	 * @return The <code>ECommand</code> instance.
	 * <code>null</code> if there is no such command.
	 */
	public static ECommand parse(final String value) {
		if (value.equals(ECommand.Install.value)) {
			return ECommand.Install;
		} else if (value.equals(ECommand.Bundle.value)) {
			return ECommand.Bundle;
		} else if (value.equals(ECommand.Deploy.value)) {
			return ECommand.Deploy;
		} else if (value.equals(ECommand.Undeploy.value)) {
			return ECommand.Undeploy;
		} else if (value.equals(ECommand.Start.value)) {
			return ECommand.Start;
		} else if (value.equals(ECommand.Stop.value)) {
			return ECommand.Stop;
		} else if (value.equals(ECommand.Uninstall.value)) {
			return ECommand.Uninstall;
		} else {
			return null;
		}
	}
	
	/**
	 * The <code>String</code> value.
	 */
	private final String value;
	/**
	 * The <code>ICommand</code> instance.
	 */
	private final ICommand command;
	
	/**
	 * Constructor of <code>ECommand</code>.
	 * @param value The <code>String</code> value.
	 * @param command The <code>ICommand</code>.
	 */
	private ECommand(final String value, final ICommand command) {
		this.value = value;
		this.command = command;
	}


	@Override
	public void execute(final String[] args) throws Exception {
		this.command.execute(args);
	}
}
