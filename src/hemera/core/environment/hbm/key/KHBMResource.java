package hemera.core.environment.hbm.key;

/**
 * <code>KHBMResource</code> defines the enumerations
 * of all the XML tags used in the resource section of
 * a <code>hbm</code>, Hemera Bundle Model file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KHBMResource {
	/**
	 * The root tag.
	 */
	Root("resource"),
	/**
	 * The source directory tag.
	 */
	SourceDir("src-dir"),
	/**
	 * The fully qualified class name tag.
	 */
	Classname("classname"),
	/**
	 * The optional configuration file path tag.
	 */
	ConfigFile("config-file"),
	/**
	 * The optional resources directory tag.
	 */
	ResourcesDir("resources-dir"),
	/**
	 * The optional dependencies tag.
	 */
	Dependencies("dependencies");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KHBMResource</code>.
	 * @param key The <code>String</code> tag.
	 */
	private KHBMResource(final String key) {
		this.tag = key;
	}
}
