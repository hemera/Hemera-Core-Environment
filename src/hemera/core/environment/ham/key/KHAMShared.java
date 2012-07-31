package hemera.core.environment.ham.key;

/**
 * <code>KHAMShared</code> defines the enumerations of
 * all the XML tags used in the shared section of the
 * <code>ham</code>, Hemera Application Model file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KHAMShared {
	/**
	 * The configuration file tag.
	 */
	ConfigFile("config-file"),
	/**
	 * The resources directory tag.
	 */
	ResourcesDir("resources-dir"),
	/**
	 * The library directory tag.
	 */
	LibraryDir("lib-dir");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KHAMShared</code>.
	 * @param key The <code>String</code> tag.
	 */
	private KHAMShared(final String key) {
		this.tag = key;
	}
}
