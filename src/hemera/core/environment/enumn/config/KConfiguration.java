package hemera.core.environment.enumn.config;

/**
 * <code>KConfiguration</code> defines the enumerations
 * of all the XML tags used in the configuration file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KConfiguration {
	/**
	 * The root tag.
	 */
	Root("hemera-runtime-environment"),
	/**
	 * The version tag.
	 */
	Version("version");	
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KConfiguration</code>.
	 * @param tag The <code>String</code> tag.
	 */
	private KConfiguration(final String tag) {
		this.tag = tag;
	}
}
