package hemera.core.environment.enumn;

import java.util.concurrent.TimeUnit;

/**
 * <code>DEnvironment</code> defines the default values
 * used by the Hemera environment.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum DEnvironment {
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
	 * The environment XML file name under the home bin
	 * directory.
	 */
	EnvironmentFile("hemera.env"),
	/**
	 * The Hemera Application Bundle file extension.
	 */
	BundleExtension(".hab"),
	/**
	 * The JSVC start script file name.
	 */
	JSVCStartScriptFile("hemera-jsvc-start.sh"),
	/**
	 * The JSVC stop script file name.
	 */
	JSVCStopScriptFile("hemera-jsvc-stop.sh"),
	/**
	 * The default use scalable service flag.
	 */
	UseScalableService("false"),
	/**
	 * The assisted execution service executor count.
	 */
	AssistedServiceExecutorCount("1000"),
	/**
	 * The assisted execution service executor maximum
	 * buffer size.
	 */
	AssistedServiceMaxBufferSize("10"),
	/**
	 * The assisted execution service executor idle time.
	 */
	AssistedServiceIdleTime("200 " + TimeUnit.MILLISECONDS.name()),
	/**
	 * The scalable execution service minimum executor
	 * count.
	 */
	ScalableServiceMinExecutor("200"),
	/**
	 * The scalable execution service maximum executor
	 * count.
	 */
	ScalableServiceMaxExecutor("1000"),
	/**
	 * The scalable execution service executor timeout
	 * value.
	 */
	ScalableServiceTimeout("10 " + TimeUnit.SECONDS.name()),
	/**
	 * The HTTP port the Apache connection listener
	 * should listen to.
	 */
	HTTPPort("80"),
	/**
	 * The socket timeout value in milliseconds.
	 */
	SocketTimeout("200"),
	/**
	 * The socket buffer size value in bytes.
	 */
	SocketBufferSize("8192");

	/**
	 * The <code>String</code> value.
	 */
	public final String value;

	/**
	 * Constructor of <code>DEnvironment</code>.
	 * @param value The <code>String</code> value.
	 */
	private DEnvironment(final String value) {
		this.value = value;
	}
}
