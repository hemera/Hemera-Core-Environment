package hemera.core.environment.hbm.key;

/**
 * <code>KHBMShared</code> defines the enumerations of
 * all the XML tags used in the shared section of the
 * <code>hbm</code>, Hemera Bundle Model file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KHBMShared {
	/**
	 * The configuration file tag.
	 */
	ConfigFile("config-file"),
	/**
	 * The resources directory tag.
	 */
	ResourcesDir("resources-dir"),
	/**
	 * The dependencies tag.
	 */
	Dependencies("dependencies");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KHBMShared</code>.
	 * @param key The <code>String</code> tag.
	 */
	private KHBMShared(final String key) {
		this.tag = key;
	}
}
