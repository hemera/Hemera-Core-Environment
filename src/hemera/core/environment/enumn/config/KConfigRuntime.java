package hemera.core.environment.enumn.config;

/**
 * <code>KConfigRuntime</code> defines the enumerations
 * of all the XML tags used in the runtime section of
 * the configuration file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KConfigRuntime {
	/**
	 * The runtime environment tag.
	 */
	Root("runtime"),
	/**
	 * The runtime environment launcher implementation
	 * tag.
	 */
	Launcher("launcher");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KConfigRuntime</code>.
	 * @param tag The <code>String</code> tag.
	 */
	private KConfigRuntime(final String tag) {
		this.tag = tag;
	}
}
