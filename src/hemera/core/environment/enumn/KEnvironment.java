package hemera.core.environment.enumn;

/**
 * <code>KEnvironment</code> defines the enumerations
 * of all the XML tags used in the environment file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum KEnvironment {
	/**
	 * The root tag.
	 */
	Root("hemera-runtime-environment"),
	/**
	 * The version tag.
	 */
	Version("version"),
	/**
	 * The runtime environment tag.
	 */
	Runtime("runtime"),
	/**
	 * The execution service tag.
	 */
	ExecutionService("execution-service"),
	/**
	 * The execution service use scalable service flag
	 * tag.
	 */
	UseScalableService("use-scalable-service"),
	/**
	 * The execution service listener tag.
	 */
	ExecutionListener("listener"),
	/**
	 * The execution service listener Jar file tag.
	 */
	ExecutionListenerJarFile("jar-file"),
	/**
	 * The execution service listener fully qualified
	 * class name tag.
	 */
	ExecutionListenerClassname("classname"),
	/**
	 * The execution service exception handler tag.
	 */
	ExceptionHandler("exception-handler"),
	/**
	 * The execution service exception handler Jar file
	 * tag.
	 */
	ExceptionHandlerJarFile("jar-file"),
	/**
	 * The execution service exception handler fully
	 * qualified class name tag.
	 */
	ExceptionHandlerClassname("classname"),
	/**
	 * The execution service assisted service tag.
	 */
	AssistedService("assisted-service"),
	/**
	 * The execution service assisted service executor
	 * count tag.
	 */
	AssistedServiceExecutorCount("executor-count"),
	/**
	 * The execution service assisted service maximum
	 * buffer size tag.
	 */
	AssistedServiceMaxBufferSize("buffer-size"),
	/**
	 * The execution service assisted service eager-
	 * waiting idle time tag.
	 */
	AssistedServiceIdleTime("idle-time"),
	/**
	 * The execution service scalable service tag.
	 */
	ScalableService("scalable-service"),
	/**
	 * The execution service scalable service minimum
	 * executor count tag.
	 */
	ScalableServiceMinExecutor("min-executor"),
	/**
	 * The execution service scalable service maximum
	 * executor count tag.
	 */
	ScalableServiceMaxExecutor("max-executor"),
	/**
	 * The execution service scalable service timeout
	 * tag.
	 */
	ScalableServiceTimeout("timeout"),
	/**
	 * The socket configuration tag.
	 */
	Socket("socket"),
	/**
	 * The socket port tag.
	 */
	SocketPort("port"),
	/**
	 * The socket timeout tag.
	 */
	SocketTimeout("timeout"),
	/**
	 * The socket buffer size tag.
	 */
	SocketBufferSize("buffer-size"),
	/**
	 * The logging configuration tag.
	 */
	Logging("logging"),
	/**
	 * The logging directory tag.
	 */
	LoggingDirectory("dir"),
	/**
	 * The applications tag.
	 */
	Applications("applications");
	
	/**
	 * The <code>String</code> tag.
	 */
	public final String tag;
	
	/**
	 * Constructor of <code>KEnvironment</code>.
	 * @param tag The <code>String</code> tag.
	 */
	private KEnvironment(final String tag) {
		this.tag = tag;
	}
}
