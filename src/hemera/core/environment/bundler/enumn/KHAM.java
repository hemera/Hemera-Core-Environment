package hemera.core.environment.bundler.enumn;

/**
 * <code>KHAM</code> defines the enumerations of all
 * the XML tags used in the <code>ham</code>, Hemera
 * Application Model file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KHAM {
	/**
	 * The Hemera Application Model root tag.
	 */
	Root("hemera-application-model");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KHAM</code>.
	 * @param key The <code>String</code> tag.
	 */
	private KHAM(final String key) {
		this.tag = key;
	}
}
