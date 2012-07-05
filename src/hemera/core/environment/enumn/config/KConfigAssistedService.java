package hemera.core.environment.enumn.config;

/**
 * <code>KConfigAssistedService</code> defines the
 * enumerations of all the XML tags used in the assisted
 * execution service section of the configuration file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KConfigAssistedService {
	/**
	 * The assisted service tag.
	 */
	Root("assisted-service"),
	/**
	 * The assisted service executor count tag.
	 */
	ExecutorCount("executor-count"),
	/**
	 * The assisted service maximum buffer size tag.
	 */
	MaxBufferSize("buffer-size"),
	/**
	 * The assisted service eager-waiting idle time tag.
	 */
	IdleTime("idle-time");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KConfigAssistedService</code>.
	 * @param tag The <code>String</code> tag.
	 */
	private KConfigAssistedService(final String tag) {
		this.tag = tag;
	}
}
