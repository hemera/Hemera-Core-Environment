package hemera.core.environment.util.config;

import hemera.core.environment.enumn.KConfiguration;
import hemera.core.environment.util.UEnvironment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>ConfigRuntime</code> defines the structure of
 * the runtime configuration.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class ConfigRuntime {
	/**
	 * The <code>String</code> home directory.
	 */
	public final String homeDir;
	/**
	 * The <code>ConfigExecutionService</code> instance.
	 */
	public final ConfigExecutionService execution;
	/**
	 * The <code>ConfigSocket</code> instance.
	 */
	public final ConfigSocket socket;
	/**
	 * The <code>String</code> logging directory.
	 */
	public final String loggingDir;
	
	/**
	 * Constructor of <code>ConfigRuntime</code>.
	 * @param homeDir the <code>String</code> home
	 * directory.
	 */
	public ConfigRuntime(final String homeDir) {
		this.homeDir = homeDir;
		this.execution = new ConfigExecutionService();
		this.socket = new ConfigSocket();
		this.loggingDir = UEnvironment.instance.getLogDir(homeDir);
	}
	
	/**
	 * Constructor of <code>ConfigRuntime</code>.
	 * @param environment The <code>Element</code> of
	 * the root environment to parse from.
	 */
	public ConfigRuntime(final Element environment) {
		this.homeDir = UEnvironment.instance.getInstalledHomeDir();
		final Element runtime = this.parseRuntime(environment);
		this.execution = new ConfigExecutionService(runtime);
		this.socket = new ConfigSocket(runtime);
		final Element logging = this.parseLogging(runtime);
		this.loggingDir = this.parseLoggingDir(logging);
	}
	
	/**
	 * Parse the runtime tag.
	 * @param environment The <code>Element</code> of
	 * the root environment to parse from.
	 * @return The runtime <code>Element</code>.
	 */
	private Element parseRuntime(final Element environment) {
		final NodeList list = environment.getElementsByTagName(KConfiguration.Runtime.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid environment configuration. Must contain one runtime tag.");
		}
		return (Element)list.item(0);
	}
	
	/**
	 * Parse the logging tag.
	 * @param runtime The <code>Element</code> of the
	 * runtime tag to parse from.
	 * @return The logging <code>Element</code>.
	 */
	private Element parseLogging(final Element runtime) {
		final NodeList list = runtime.getElementsByTagName(KConfiguration.Logging.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid runtime configuration. Must contain one logging tag.");
		}
		return (Element)list.item(0);
	}
	
	/**
	 * Parse the logging directory value.
	 * @param logging The <code>Element</code> of the
	 * logging tag to parse from.
	 * @return The <code>String</code> value.
	 */
	private String parseLoggingDir(final Element logging) {
		final NodeList list = logging.getElementsByTagName(KConfiguration.LoggingDirectory.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid logging configuration. Must contain one directory tag.");
		}
		return list.item(0).getTextContent();
	}
	
	/**
	 * Create the runtime configuration tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for runtime
	 * configuration.
	 */
	public Element toXML(final Document document) {
		final Element runtime = document.createElement(KConfiguration.Runtime.tag);
		// Execution service tag.
		final Element executionService = this.execution.toXML(document);
		runtime.appendChild(executionService);
		// Socket tag.
		final Element socket = this.socket.toXML(document);
		runtime.appendChild(socket);
		// Logging tag.
		final Element logging = this.buildLoggingTag(document);
		runtime.appendChild(logging);
		return runtime;
	}
	
	/**
	 * Create the logging configuration tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for logging
	 * configuration.
	 */
	private Element buildLoggingTag(final Document document) {
		final Element logging = document.createElement(KConfiguration.Logging.tag);
		// Directory tag.
		final Element directory = document.createElement(KConfiguration.LoggingDirectory.tag);
		directory.setTextContent(this.loggingDir);
		logging.appendChild(directory);
		return logging;
	}
}
