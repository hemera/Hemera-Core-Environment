package hemera.core.environment.config;

import hemera.core.environment.config.key.KConfigExecutionService;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>ConfigExecutionService</code> defines the
 * structure of the execution service configuration.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class ConfigExecutionService {
	/**
	 * The <code>boolean</code> use scalable service
	 * instead of default assisted service flag.
	 */
	public final boolean useScalableService;
	/**
	 * The <code>ConfigListener</code> instance.
	 */
	public final ConfigListener listener;
	/**
	 * The <code>ConfigHandler</code> instance.
	 */
	public final ConfigHandler handler;
	/**
	 * The <code>ConfigAssistedService</code> instance.
	 */
	public final ConfigAssistedService assisted;
	/**
	 * The <code>ConfigScalableService</code> instance.
	 */
	public final ConfigScalableService scalable;
	
	/**
	 * Constructor of <code>ConfigExecutionService</code>.
	 */
	public ConfigExecutionService() {
		this.useScalableService = false;
		this.listener = new ConfigListener();
		this.handler = new ConfigHandler();
		this.assisted = new ConfigAssistedService();
		this.scalable = new ConfigScalableService();
	}
	
	/**
	 * Constructor of <code>ConfigExecutionService</code>.
	 * @param runtime The <code>Element</code> of the
	 * runtime tag.
	 */
	public ConfigExecutionService(final Element runtime) {
		final Element execution = this.parseExecutionService(runtime);
		this.useScalableService = this.parseUseScalableService(execution);
		this.listener = new ConfigListener(execution);
		this.handler = new ConfigHandler(execution);
		this.assisted = new ConfigAssistedService(execution);
		this.scalable = new ConfigScalableService(execution);
	}
	
	/**
	 * Parse the execution service tag.
	 * @param runtime The <code>Element</code> of
	 * runtime tag to parse from.
	 * @return The execution <code>Element</code>.
	 */
	private Element parseExecutionService(final Element runtime) {
		final NodeList list = runtime.getElementsByTagName(KConfigExecutionService.Root.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid runtime configuration. Must contain one execution service tag.");
		}
		return (Element)list.item(0);
	}
	
	/**
	 * Parse the use scalable service value.
	 * @param execution The <code>Element</code> of the
	 * execution service tag to parse from.
	 * @return The <code>int</code> value.
	 */
	private boolean parseUseScalableService(final Element execution) {
		final NodeList list = execution.getElementsByTagName(KConfigExecutionService.UseScalableService.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid execution service configuration. Must contain one use scalable service tag.");
		}
		return Boolean.valueOf(list.item(0).getTextContent());
	}
	
	/**
	 * Create the execution service tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for execution
	 * service.
	 */
	public Element toXML(final Document document) {
		final Element executionService = document.createElement(KConfigExecutionService.Root.tag);
		// Use scalable service tag.
		final Element useScalable = document.createElement(KConfigExecutionService.UseScalableService.tag);
		useScalable.setTextContent(String.valueOf(false));
		executionService.appendChild(useScalable);
		// Listener tag.
		final Element listener = this.listener.toXML(document);
		executionService.appendChild(listener);
		// Exception handler tag.
		final Element handler = this.handler.toXML(document);
		executionService.appendChild(handler);
		// Assisted service tag.
		final Element assisted = this.assisted.toXML(document);
		executionService.appendChild(assisted);
		// Scalable service tag.
		final Element scalable = this.scalable.toXML(document);
		executionService.appendChild(scalable);
		return executionService;
	}
}
