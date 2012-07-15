package hemera.core.environment.config.key;

/**
 * <code>KConfigListener</code> defines the enumerations
 * of all the XML tags used in the execution service
 * listener section of the configuration file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KConfigListener {
	/**
	 * The execution service listener tag.
	 */
	Root("listener"),
	/**
	 * The execution service listener Jar file tag.
	 */
	JarFile("jar-file"),
	/**
	 * The execution service listener fully qualified
	 * class name tag.
	 */
	Classname("classname");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KConfigListener</code>.
	 * @param tag The <code>String</code> tag.
	 */
	private KConfigListener(final String tag) {
		this.tag = tag;
	}
}
