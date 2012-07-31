package hemera.core.environment.hbm.key;

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
	 * The shared configuration file tag.
	 */
	SharedConfigFile("shared-config-file"),
	/**
	 * The shared resources directory tag.
	 */
	SharedResourcesDir("shared-resources-dir"),
	/**
	 * The shared dependencies tag.
	 */
	SharedDependencies("shared-dependencies"),
	/**
	 * The modules tag.
	 */
	Modules("modules");
	
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
