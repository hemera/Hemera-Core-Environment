package hemera.core.environment.config;

import hemera.core.environment.config.key.KConfiguration;
import hemera.core.environment.enumn.EEnvironment;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>Configuration</code> defines the utility that
 * provides access to the environment configuration. 
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class Configuration {
	/**
	 * The <code>String</code> version.
	 */
	public final String version;
	/**
	 * The <code>ConfigJVM</code> instance.
	 */
	public final ConfigJVM jvm;
	/**
	 * The <code>ConfigRuntime</code> instance.
	 */
	public final ConfigRuntime runtime;

	/**
	 * Constructor of <code>Configuration</code>.
	 * <p>
	 * This constructor creates a default configuration
	 * with the specified home directory.
	 * @param homeDir the <code>String</code> home
	 * directory.
	 */
	public Configuration(final String homeDir) {
		this.version = EEnvironment.Version.value;
		this.jvm = new ConfigJVM();
		this.runtime = new ConfigRuntime(homeDir);
	}

	/**
	 * Constructor of <code>Configuration</code>.
	 * @param document The XML <code>Document</code>
	 * to parse and set the values.
	 */
	public Configuration(final Document document) {
		final Element environment = this.parseEnvironment(document);
		this.version = this.parseVersion(environment);
		this.jvm = new ConfigJVM(environment);
		this.runtime = new ConfigRuntime(environment);
	}

	/**
	 * Parse the root environment tag.
	 * @param document The XML <code>Document</code>
	 * to parse and set the values.
	 * @return The root environment <code>Element</code>.
	 */
	private Element parseEnvironment(final Document document) {
		final NodeList list = document.getElementsByTagName(KConfiguration.Root.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid configuration. Must contain one root environment tag.");
		}
		return (Element)list.item(0);
	}

	/**
	 * Parse the version value.
	 * @param environment The <code>Element</code> of
	 * the root environment to parse from.
	 * @return The <code>String</code> value.
	 */
	private String parseVersion(final Element environment) {
		final NodeList list = environment.getElementsByTagName(KConfiguration.Version.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid environment configuration. Must contain one version tag.");
		}
		return list.item(0).getTextContent();
	}

	/**
	 * Convert this environment configuration to a XML
	 * document.
	 * @return The XML <code>Document</code> instance.
	 * @throws ParserConfigurationException If document
	 * generation failed.
	 */
	public Document toDocument() throws ParserConfigurationException {
		// Create the document.
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.newDocument();
		// Root tag.
		final Element root = document.createElement(KConfiguration.Root.tag);
		document.appendChild(root);
		// Version tag.
		final Element version = document.createElement(KConfiguration.Version.tag);
		version.setTextContent(EEnvironment.Version.value);
		root.appendChild(version);
		// JVM tag.
		final Element jvm = this.jvm.toXML(document);
		root.appendChild(jvm);
		// Runtime tag.
		final Element runtime = this.runtime.toXML(document);
		root.appendChild(runtime);
		return document;
	}
}
