package hemera.core.environment.util.config;

import hemera.core.environment.enumn.KConfiguration;

import java.util.concurrent.TimeUnit;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>ConfigScalableService</code> defines the structure
 * of the scalable execution service configuration.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class ConfigScalableService {
	/**
	 * The <code>int</code> scalable execution service
	 * minimum executor count.
	 */
	public final int minExecutor;
	/**
	 * The <code>int</code> scalable execution service
	 * maximum executor count.
	 */
	public final int maxExecutor;
	/**
	 * The <code>String</code> scalable execution service
	 * executor timeout value.
	 */
	public final String timeout;
	
	/**
	 * Constructor of <code>ConfigScalableService</code>.
	 */
	public ConfigScalableService() {
		this.minExecutor = 200;
		this.maxExecutor = 1000;
		this.timeout = "10 " + TimeUnit.SECONDS.name();
	}
	
	/**
	 * Constructor of <code>ConfigScalableService</code>.
	 * @param execution The <code>Element</code> of
	 * execution service tag to parse from.
	 */
	public ConfigScalableService(final Element execution) {
		final Element scalable = this.parseScalable(execution);
		this.minExecutor = this.parseMinExecutor(scalable);
		this.maxExecutor = this.parseMaxExecutor(scalable);
		this.timeout = this.parseTimeout(scalable);
	}
	
	/**
	 * Parse the scalable service tag.
	 * @param execution The <code>Element</code> of
	 * execution service tag to parse from.
	 * @return The scalable service <code>Element</code>.
	 */
	private Element parseScalable(final Element execution) {
		final NodeList list = execution.getElementsByTagName(KConfiguration.ScalableService.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid execution service configuration. Must contain one scalable service tag.");
		}
		return (Element)list.item(0);
	}
	
	/**
	 * Parse the minimum executor count value.
	 * @param scalable The <code>Element</code> of the
	 * scalable service to parse from.
	 * @return The <code>int</code> value.
	 */
	private int parseMinExecutor(final Element scalable) {
		final NodeList list = scalable.getElementsByTagName(KConfiguration.ScalableServiceMinExecutor.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid scalable service configuration. Must contain one minimum executor count tag.");
		}
		return Integer.valueOf(list.item(0).getTextContent());
	}
	
	/**
	 * Parse the maximum executor count value.
	 * @param scalable The <code>Element</code> of the
	 * scalable service to parse from.
	 * @return The <code>int</code> value.
	 */
	private int parseMaxExecutor(final Element scalable) {
		final NodeList list = scalable.getElementsByTagName(KConfiguration.ScalableServiceMaxExecutor.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid scalable service configuration. Must contain one maximum executor count tag.");
		}
		return Integer.valueOf(list.item(0).getTextContent());
	}
	
	/**
	 * Parse the timeout value.
	 * @param scalable The <code>Element</code> of the
	 * scalable service to parse from.
	 * @return The <code>String</code> value.
	 */
	private String parseTimeout(final Element scalable) {
		final NodeList list = scalable.getElementsByTagName(KConfiguration.ScalableServiceTimeout.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid scalable service configuration. Must contain one timeout tag.");
		}
		return list.item(0).getTextContent();
	}
	
	/**
	 * Create the scalable execution service tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for scalable
	 * service.
	 */
	public Element toXML(final Document document) {
		final Element scalable = document.createElement(KConfiguration.ScalableService.tag);
		// Minimum executor count tag.
		final Element minimum = document.createElement(KConfiguration.ScalableServiceMinExecutor.tag);
		minimum.setTextContent(String.valueOf(this.minExecutor));
		scalable.appendChild(minimum);
		// Maximum executor count tag.
		final Element maximum = document.createElement(KConfiguration.ScalableServiceMaxExecutor.tag);
		maximum.setTextContent(String.valueOf(this.maxExecutor));
		scalable.appendChild(maximum);
		// Time out tag.
		final Element timeout = document.createElement(KConfiguration.ScalableServiceTimeout.tag);
		timeout.setTextContent(this.timeout);
		scalable.appendChild(timeout);
		return scalable;
	}
}
