package hemera.core.environment.config;

import hemera.core.environment.config.key.KConfigRuntime;

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
	 * The <code>String</code> launcher fully qualified
	 * class name. <code>null</code> means the default
	 * Apache runtime launcher is used.
	 */
	public final String launcher;
	/**
	 * The <code>ConfigExecutionService</code> instance.
	 */
	public final ConfigExecutionService execution;
	/**
	 * The <code>ConfigSocket</code> instance.
	 */
	public final ConfigSocket socket;
	/**
	 * The <code>ConfigLogging</code> instance.
	 */
	public final ConfigLogging logging;
	
	/**
	 * Constructor of <code>ConfigRuntime</code>.
	 * @param homeDir the <code>String</code> install
	 * home directory.
	 */
	public ConfigRuntime(final String homeDir) {
		this.launcher = null;
		this.execution = new ConfigExecutionService();
		this.socket = new ConfigSocket();
		this.logging = new ConfigLogging(homeDir);
	}
	
	/**
	 * Constructor of <code>ConfigRuntime</code>.
	 * @param environment The <code>Element</code> of
	 * the root environment to parse from.
	 */
	public ConfigRuntime(final Element environment) {
		final Element runtime = this.parseRuntime(environment);
		this.launcher = this.parseLauncher(runtime);
		this.execution = new ConfigExecutionService(runtime);
		this.socket = new ConfigSocket(runtime);
		this.logging = new ConfigLogging(runtime);
	}
	
	/**
	 * Parse the runtime tag.
	 * @param environment The <code>Element</code> of
	 * the root environment to parse from.
	 * @return The runtime <code>Element</code>.
	 */
	private Element parseRuntime(final Element environment) {
		final NodeList list = environment.getElementsByTagName(KConfigRuntime.Root.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid environment configuration. Must contain one runtime tag.");
		}
		return (Element)list.item(0);
	}
	
	/**
	 * Parse the launcher class name value.
	 * @param runtime The <code>Element</code> of the
	 * runtime tag to parse from.
	 * @return The <code>String</code> value.
	 */
	private String parseLauncher(final Element runtime) {
		final NodeList list = runtime.getElementsByTagName(KConfigRuntime.Launcher.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid runtime configuration. Must contain one launcher tag.");
		}
		final String text = list.item(0).getTextContent();
		if (text == null || text.isEmpty()) return null;
		else return text;
	}
	
	/**
	 * Create the runtime configuration tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for runtime
	 * configuration.
	 */
	public Element toXML(final Document document) {
		final Element runtime = document.createElement(KConfigRuntime.Root.tag);
		// Launcher tag.
		final Element launcher = document.createElement(KConfigRuntime.Launcher.tag);
		if (this.launcher != null) {
			launcher.setTextContent(this.launcher);
		}
		runtime.appendChild(launcher);
		// Execution service tag.
		final Element executionService = this.execution.toXML(document);
		runtime.appendChild(executionService);
		// Socket tag.
		final Element socket = this.socket.toXML(document);
		runtime.appendChild(socket);
		// Logging tag.
		final Element logging = this.logging.toXML(document);
		runtime.appendChild(logging);
		return runtime;
	}
}
