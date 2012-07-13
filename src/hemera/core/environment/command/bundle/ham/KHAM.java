package hemera.core.environment.command.bundle.ham;

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
	Root("hemera-application-model"),
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
	ModuleResourceDir("resource-dir"),
	/**
	 * The application directory place-holder that is
	 * replaced with the proper application directory
	 * after the bundle is deployed.
	 */
	PlaceholderAppsDir("{APPDIR}");
	
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
