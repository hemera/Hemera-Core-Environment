package hemera.core.environment.interfaces;

/**
 * <code>ICommand</code> defines the interface of a
 * set of logic that can be executed based on given
 * arguments.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public interface ICommand {

	/**
	 * Execute the command with given arguments.
	 * @param args The <code>String</code> array of
	 * arguments.
	 * @throws Exception If any processing failed.
	 */
	public void execute(final String[] args) throws Exception;
}
