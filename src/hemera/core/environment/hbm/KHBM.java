package hemera.core.environment.hbm;

/**
 * <code>KHBM</code> defines the enumerations of all
 * the XML tags used in the <code>hbm</code>, Hemera
 * Bundle Model file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
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
	 * The modules list tag.
	 */
	Modules("modules"),
	/**
	 * The module tag.
	 */
	Module("module"),
	/**
	 * The module source directory tag.
	 */
	ModuleSourceDir("src-dir"),
	/**
	 * The module class-path library directory tag.
	 */
	ModuleLibraryDir("lib-dir"),
	/**
	 * The module fully qualified class name tag.
	 */
	ModuleClassname("classname"),
	/**
	 * The module optional configuration file path tag.
	 */
	ModuleConfigFile("config-file"),
	/**
	 * The module optional resource directory tag.
	 */
	ModuleResourceDir("resource-dir");
	
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
