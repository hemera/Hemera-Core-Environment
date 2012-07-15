package hemera.core.environment.config.key;

/**
 * <code>KConfigScalableService</code> defines the
 * enumerations of all the XML tags used in the scalable
 * execution service section of the configuration file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KConfigScalableService {
	/**
	 * The scalable service tag.
	 */
	Root("scalable-service"),
	/**
	 * The scalable service minimum executor count tag.
	 */
	MinExecutor("min-executor"),
	/**
	 * The scalable service maximum executor count tag.
	 */
	MaxExecutor("max-executor"),
	/**
	 * The scalable service timeout tag.
	 */
	Timeout("timeout");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KConfigScalableService</code>.
	 * @param tag The <code>String</code> tag.
	 */
	private KConfigScalableService(final String tag) {
		this.tag = tag;
	}
}
