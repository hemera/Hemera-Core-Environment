package hemera.core.environment.enumn.config;

/**
 * <code>KConfigExecutionService</code> defines the
 * enumerations of all the XML tags used in the execution
 * service section of the configuration file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KConfigExecutionService {
	/**
	 * The execution service tag.
	 */
	Root("execution-service"),
	/**
	 * The execution service use scalable service flag
	 * tag.
	 */
	UseScalableService("use-scalable-service");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KConfigExecutionService</code>.
	 * @param tag The <code>String</code> tag.
	 */
	private KConfigExecutionService(final String tag) {
		this.tag = tag;
	}
}
