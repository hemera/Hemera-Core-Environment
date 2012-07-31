package hemera.core.environment.enumn;

/**
 * <code>EDependencyType</code> defines the enumerations
 * of all types of dependencies.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum EDependencyType {
	/**
	 * The Jar file directory dependency.
	 */
	JarDirectory("jar-dir"),
	/**
	 * The Java source directory dependency.
	 */
	SourceDirectory("src-dir");
	
	/**
	 * The <code>String</code> type value.
	 */
	public final String value;
	
	/**
	 * Constructor of <code>EDependencyType</code>.
	 * @param value The <code>String</code> type value.
	 */
	private EDependencyType(final String value) {
		this.value = value;
	}
	
	/**
	 * Parse the given value into enumeration.
	 * @param value The <code>String</code> value.
	 * @return The <code>EDependencyType</code> value.
	 */
	public static EDependencyType parse(final String value) {
		if (value.equals(EDependencyType.JarDirectory.value)) {
			return EDependencyType.JarDirectory;
		} else if (value.equals(EDependencyType.SourceDirectory.value)) {
			return EDependencyType.SourceDirectory;
		} else {
			return null;
		}
	}
}
