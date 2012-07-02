package hemera.core.environment.util.config;

import hemera.core.environment.enumn.KConfiguration;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>ConfigJVM</code> defines the structure of the
 * JVM configuration.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class ConfigJVM {
	/**
	 * The <code>String</code> minimum JVM memory.
	 */
	public final String memoryMin;
	/**
	 * The <code>String</code> maximum JVM memory.
	 */
	public final String memoryMax;
	/**
	 * The <code>String</code> JVM file encoding.
	 */
	public final String fileEncoding;
	
	/**
	 * Constructor of <code>ConfigJVM</code>.
	 */
	public ConfigJVM() {
		this.memoryMin = "256m";
		this.memoryMax = "1024m";
		this.fileEncoding = "UTF-8";
	}
	
	/**
	 * Constructor of <code>ConfigJVM</code>.
	 * @param environment The <code>Element</code> of
	 * the root environment to parse from.
	 */
	public ConfigJVM(final Element environment) {
		final Element jvm = this.parseJVM(environment);
		this.memoryMin = this.parseMinMemory(jvm);
		this.memoryMax = this.parseMaxMemory(jvm);
		this.fileEncoding = this.parseFileEncoding(jvm);
	}
	
	/**
	 * Parse the JVM configuration element.
	 * @param environment The <code>Element</code> of
	 * the root environment to parse from.
	 * @return The JVM <code>Element</code> tag.
	 */
	private Element parseJVM(final Element environment) {
		final NodeList list = environment.getElementsByTagName(KConfiguration.JVM.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid environment configuration. Must contain one JVM tag.");
		}
		return (Element)list.item(0);
	}
	
	/**
	 * Parse the JVM minimum memory value.
	 * @param jvm The JVM <code>Element</code> to read
	 * the value from.
	 * @return The <code>String</code> value.
	 */
	private String parseMinMemory(final Element jvm) {
		final NodeList list = jvm.getElementsByTagName(KConfiguration.MemoryMinimum.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid JVM configuration. Must contain one minimum memory tag.");
		}
		return list.item(0).getTextContent();
	}
	
	/**
	 * Parse the JVM maximum memory value.
	 * @param jvm The JVM <code>Element</code> to read
	 * the value from.
	 * @return The <code>String</code> value.
	 */
	private String parseMaxMemory(final Element jvm) {
		final NodeList list = jvm.getElementsByTagName(KConfiguration.MemoryMaximum.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid JVM configuration. Must contain one maximum memory tag.");
		}
		return list.item(0).getTextContent();
	}
	
	/**
	 * Parse the JVM file encoding value.
	 * @param jvm The JVM <code>Element</code> to read
	 * the value from.
	 * @return The <code>String</code> value.
	 */
	private String parseFileEncoding(final Element jvm) {
		final NodeList list = jvm.getElementsByTagName(KConfiguration.FileEncoding.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid JVM configuration. Must contain one maximum memory tag.");
		}
		return list.item(0).getTextContent();
	}
	
	/**
	 * Create the JVM configuration tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for the JVM
	 * configuration.
	 */
	public Element toXML(final Document document) {
		final Element jvm = document.createElement(KConfiguration.JVM.tag);
		// Minimum memory tag.
		final Element memoryMin = document.createElement(KConfiguration.MemoryMinimum.tag);
		memoryMin.setTextContent(this.memoryMin);
		jvm.appendChild(memoryMin);
		// Maximum memory tag.
		final Element memoryMax = document.createElement(KConfiguration.MemoryMaximum.tag);
		memoryMax.setTextContent(this.memoryMax);
		jvm.appendChild(memoryMax);
		// File encoding tag.
		final Element fileEncoding = document.createElement(KConfiguration.FileEncoding.tag);
		fileEncoding.setTextContent(this.fileEncoding);
		jvm.appendChild(fileEncoding);
		return jvm;
	}
}
