package hemera.core.environment.enumn;

/**
 * <code>KBundleManifest</code> defines the enumerations
 * of all the keys used in the manifest file of the
 * application bundle.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KBundleManifest {
	/**
	 * The key for modules list attribute. The value
	 * is a <code>String</code> of module Jar file
	 * names separated by <code>ModuleSeparator</code>
	 * character.
	 */
	Modules("modules"),
	/**
	 * The module separator that separates the module
	 * Jar file names for the <code>Modules</code>
	 * manifest value.
	 */
	ModuleSeparator(";"),
	/**
	 * The key for the library Jar file name attribute.
	 * The value is the <code>String</code> file name
	 * of the library Jar file.
	 */
	LibraryJarFile("lib_jar"),
	/**
	 * The key for the HAM file file name attribute.
	 * The value is the <code>String</code> file name
	 * of the HAM file.
	 */
	HAMFile("ham_file");
	
	/**
	 * The <code>String</code> key.
	 */
	public final String key;
	
	/**
	 * Constructor of <code>KBundleManifest</code>.
	 * @param key The <code>String</code> key.
	 */
	private KBundleManifest(final String key) {
		this.key = key;
	}
}
