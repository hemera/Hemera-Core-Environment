package hemera.core.environment.hbm.key;

/**
 * <code>KHBM</code> defines the enumerations of all
 * the XML tags used in the <code>hbm</code>, Hemera
 * Bundle Model file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.4
 */
public enum KHBM {
	/**
	 * The Hemera Bundle Model root tag.
	 */
	Root("hemera-bundle-model"),
	/**
	 * The application name tag.
	 */
	ApplicationName("application-name"),
	/**
	 * The optional application path tag.
	 */
	ApplicationPath("application-path"),
	/**
	 * The shared tag.
	 */
	Shared("shared"),
	/**
	 * The resources tag.
	 */
	Resources("resources");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KHBM</code>.
	 * @param key The <code>String</code> tag.
	 */
	private KHBM(final String key) {
		this.tag = key;
	}
}
