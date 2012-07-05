package hemera.core.environment.util.config;

import hemera.core.environment.enumn.config.KConfigAssistedService;

import java.util.concurrent.TimeUnit;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>ConfigAssistedService</code> defines the structure
 * of assisted execution service configuration.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class ConfigAssistedService {
	/**
	 * The <code>int</code> assisted execution service
	 * executor count.
	 */
	public final int executorCount;
	/**
	 * The <code>int</code> assisted execution service
	 * executor maximum buffer size.
	 */
	public final int maxBufferSize;
	/**
	 * The <code>String</code> assisted execution service
	 * executor idle time.
	 */
	public final String idleTime;
	
	/**
	 * Constructor of <code>ConfigAssistedService</code>.
	 */
	public ConfigAssistedService() {
		this.executorCount = 1000;
		this.maxBufferSize = 10;
		this.idleTime = "200 " + TimeUnit.MILLISECONDS.name();
	}
	
	/**
	 * Constructor of <code>ConfigAssistedService</code>.
	 * @param execution The <code>Element</code> of
	 * execution service tag to parse from.
	 */
	public ConfigAssistedService(final Element execution) {
		final Element assisted = this.parseAssisted(execution);
		this.executorCount = this.parseExecutorCount(assisted);
		this.maxBufferSize = this.parseMaxBufferSize(assisted);
		this.idleTime = this.parseIdleTime(assisted);
	}
	
	/**
	 * Parse the assisted service tag.
	 * @param execution The <code>Element</code> of
	 * execution service tag to parse from.
	 * @return The assisted service <code>Element</code>.
	 */
	private Element parseAssisted(final Element execution) {
		final NodeList list = execution.getElementsByTagName(KConfigAssistedService.Root.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid execution service configuration. Must contain one assisted service tag.");
		}
		return (Element)list.item(0);
	}
	
	/**
	 * Parse the executor count value.
	 * @param assisted The assisted <code>Element</code>
	 * to parse from.
	 * @return The <code>int</code> value.
	 */
	private int parseExecutorCount(final Element assisted) {
		final NodeList list = assisted.getElementsByTagName(KConfigAssistedService.ExecutorCount.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid assisted service configuration. Must contain one executor count tag.");
		}
		return Integer.valueOf(list.item(0).getTextContent());
	}
	
	/**
	 * Parse the maximum buffer size value.
	 * @param assisted The assisted <code>Element</code>
	 * to parse from.
	 * @return The <code>int</code> value.
	 */
	private int parseMaxBufferSize(final Element assisted) {
		final NodeList list = assisted.getElementsByTagName(KConfigAssistedService.MaxBufferSize.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid assisted service configuration. Must contain one maximum buffer size tag.");
		}
		return Integer.valueOf(list.item(0).getTextContent());
	}
	
	/**
	 * Parse the idle time value.
	 * @param assisted The assisted <code>Element</code>
	 * to parse from.
	 * @return The <code>String</code> value.
	 */
	private String parseIdleTime(final Element assisted) {
		final NodeList list = assisted.getElementsByTagName(KConfigAssistedService.IdleTime.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid assisted service configuration. Must contain one idle time tag.");
		}
		return list.item(0).getTextContent();
	}
	
	/**
	 * Create the assisted execution service tag. 
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for assisted
	 * service.
	 */
	public Element toXML(final Document document) {
		final Element assisted = document.createElement(KConfigAssistedService.Root.tag);
		// Executor count tag.
		final Element executorCount = document.createElement(KConfigAssistedService.ExecutorCount.tag);
		executorCount.setTextContent(String.valueOf(this.executorCount));
		assisted.appendChild(executorCount);
		// Buffer size tag.
		final Element bufferSize = document.createElement(KConfigAssistedService.MaxBufferSize.tag);
		bufferSize.setTextContent(String.valueOf(this.maxBufferSize));
		assisted.appendChild(bufferSize);
		// Idle time tag.
		final Element idleTime = document.createElement(KConfigAssistedService.IdleTime.tag);
		idleTime.setTextContent(this.idleTime);
		assisted.appendChild(idleTime);
		return assisted;
	}
}
