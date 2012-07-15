package hemera.core.environment.config.key;

/**
 * <code>KConfigJVM</code> defines the enumerations
 * of all the XML tags used in the JVM section of the
 * configuration file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KConfigJVM {
	/**
	 * The JVM tag.
	 */
	Root("jvm"),
	/**
	 * The minimum memory tag.
	 */
	MemoryMinimum("memory-min"),
	/**
	 * The maximum memory tag.
	 */
	MemoryMaximum("memory-max"),
	/**
	 * The file encoding tag.
	 */
	FileEncoding("file-encoding");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KConfigJVM</code>.
	 * @param tag The <code>String</code> tag.
	 */
	private KConfigJVM(final String tag) {
		this.tag = tag;
	}
}
