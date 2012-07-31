package hemera.core.environment.hbm.key;

/**
 * <code>KHBMDependency</code> defines the enumerations
 * of all the XML tags used in the dependency section
 * of a <code>hbm</code>, Hemera Bundle Model file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KHBMDependency {
	/**
	 * The root tag.
	 */
	Root("dependency"),
	/**
	 * The type tag.
	 */
	Type("type"),
	/**
	 * The value tag.
	 */
	Value("value"),
	/**
	 * The optional library directory tag.
	 */
	LibraryDirectory("lib-dir");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KHBMDependency</code>.
	 * @param key The <code>String</code> tag.
	 */
	private KHBMDependency(final String key) {
		this.tag = key;
	}
}
