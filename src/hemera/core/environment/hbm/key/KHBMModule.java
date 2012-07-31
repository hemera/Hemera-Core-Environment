package hemera.core.environment.hbm.key;

/**
 * <code>KHBMModule</code> defines the enumerations
 * of all the XML tags used in the module section of
 * a <code>hbm</code>, Hemera Bundle Model file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KHBMModule {
	/**
	 * The root tag.
	 */
	Root("module"),
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
	 * The dependencies tag.
	 */
	Dependencies("dependencies");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KHBMModule</code>.
	 * @param key The <code>String</code> tag.
	 */
	private KHBMModule(final String key) {
		this.tag = key;
	}
}
