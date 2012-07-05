package hemera.core.environment.enumn.config;

/**
 * <code>KConfigLogging</code> defines the enumerations
 * of all the XML tags used in the logging section of the
 * configuration file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KConfigLogging {
	/**
	 * The logging configuration tag.
	 */
	Root("logging"),
	/**
	 * The enabled tag.
	 */
	Enabled("enabled"),
	/**
	 * The logging directory tag.
	 */
	Directory("dir"),
	/**
	 * The file size tag. Refer to <code>CLogging</code>
	 * <code>FileSize</code> for details.
	 */
	FileSize("file-size"),
	/**
	 * The file count tag. Refer to <code>CLogging</code>
	 * <code>FileCount</code> for details.
	 */
	FileCount("file-count");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KConfigLogging</code>.
	 * @param tag The <code>String</code> tag.
	 */
	private KConfigLogging(final String tag) {
		this.tag = tag;
	}
}
