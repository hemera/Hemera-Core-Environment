package hemera.core.environment.config.key;

/**
 * <code>KConfigHandler</code> defines the enumerations
 * of all the XML tags used in the execution service
 * exception handler section of the configuration file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KConfigHandler {
	/**
	 * The exception handler tag.
	 */
	Root("exception-handler"),
	/**
	 * The exception handler Jar file tag.
	 */
	JarFile("jar-file"),
	/**
	 * The exception handler fully qualified class name
	 * tag.
	 */
	Classname("classname");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KConfigHandler</code>.
	 * @param tag The <code>String</code> tag.
	 */
	private KConfigHandler(final String tag) {
		this.tag = tag;
	}
}
