package hemera.core.environment.enumn;

/**
 * <code>EEnvironment</code> defines the enumerations
 * of various system values used.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum EEnvironment {
	/**
	 * The current version number.
	 */
	Version("1.0.0"),
	/**
	 * The script file name.
	 */
	ScriptFile("hemera"),
	/**
	 * The environment access path set in the shell
	 * profile.
	 */
	ShellAccessPath("HEMERA"),
	/**
	 * The configuration XML file name under the home bin
	 * directory.
	 */
	ConfigurationFile("hemera.conf"),
	/**
	 * The Hemera Application Bundle file extension.
	 */
	BundleExtension(".hab"),
	/**
	 * The Hemera Application model file extension.
	 */
	HAMExtension(".ham"),
	/**
	 * The JSVC start script file name.
	 */
	JSVCStartScriptFile("hemera-jsvc-start"),
	/**
	 * The JSVC stop script file name.
	 */
	JSVCStopScriptFile("hemera-jsvc-stop");

	/**
	 * The <code>String</code> value.
	 */
	public final String value;

	/**
	 * Constructor of <code>EEnvironment</code>.
	 * @param value The <code>String</code> value.
	 */
	private EEnvironment(final String value) {
		this.value = value;
	}
}
