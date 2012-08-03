package hemera.core.environment.ham.key;

/**
 * <code>KHAMResource</code> defines the enumerations
 * of all the XML tags used in the resource section of
 * a <code>ham</code>, Hemera Application Model file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KHAMResource {
	/**
	 * The root tag.
	 */
	Root("resource"),
	/**
	 * The fully qualified class name tag.
	 */
	Classname("classname"),
	/**
	 * The optional configuration file path tag.
	 */
	ConfigFile("config-file"),
	/**
	 * The optional resource directory tag.
	 */
	ResourcesDir("resources-dir");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KHAMResource</code>.
	 * @param key The <code>String</code> tag.
	 */
	private KHAMResource(final String key) {
		this.tag = key;
	}
}
