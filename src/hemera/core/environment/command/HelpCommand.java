package hemera.core.environment.command;

import hemera.core.environment.enumn.ECommand;
import hemera.core.environment.interfaces.ICommand;

/**
 * <code>HelpCommand</code> defines the unit of logic
 * that prints out all the available commands along
 * with their required arguments.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class HelpCommand implements ICommand {

	@Override
	public void execute(final String[] args) throws Exception {
		final ECommand[] commands = ECommand.values();
		for (int i = 0; i < commands.length; i++) {
			final ECommand command = commands[i];
			System.out.println(command.getDescription());
		}
	}

	@Override
	public String getKey() {
		return "help";
	}

	@Override
	public String getDescription() {
		return this.getKey();
	}
}
