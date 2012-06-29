package hemera.core.environment.deployer.jsvc;

/**
 * <code>JSVCScriptGenerator</code> defines the singleton
 * implementation that provides the functionality to
 * generate shell scripts that execute the JSVC Apache
 * daemon process.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum JSVCScriptGenerator {
	/**
	 * The singleton instance.
	 */
	instance;

	/**
	 * Generate the JSVC stop script.
	 * @return The <code>String</code> script.
	 */
	public String generateStopScript() {
		
		return null;
	}

	/**
	 * Generate the JSVC start script.
	 * @return The <code>String</code> script.
	 */
	public String generateStartScript() {
		
		return null;
	}
}
