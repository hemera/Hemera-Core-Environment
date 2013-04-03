package hemera.core.environment.enumn;

/**
 * <code>EEnvironment</code> defines the enumerations
 * of various system values used.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.2
 */
public enum EEnvironment {
	/**
	 * The current version number.
	 */
	Version("1.0.2"),
	/**
	 * The configuration XML file name under the home bin
	 * directory.
	 */
	ConfigurationFile("hemera.conf"),
	/**
	 * The JSVC PID file.
	 */
	JSVCPIDFile("jsvc.pid"),
	/**
	 * The Hemera Application model file extension.
	 */
	HAMExtension(".ham"),
	/**
	 * The fully qualified class name of the default
	 * runtime launcher.
	 */
	DefaultLauncher("hemera.core.apache.ApacheRuntimeLauncher"),
	/**
	 * The application shared directory name.
	 */
	AppSharedDir("shared"),
	/**
	 * The application shared library directory name.
	 */
	AppSharedLibDir("lib"),
	/**
	 * The application shared resources directory name.
	 */
	AppSharedResourcesDir("resources");

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
