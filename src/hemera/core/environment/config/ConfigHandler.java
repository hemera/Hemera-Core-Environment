package hemera.core.environment.config;

import hemera.core.environment.config.key.KConfigHandler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>ConfigHandler</code> defines the structure of
 * the execution service exception handler configuration.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class ConfigHandler {
	/**
	 * The <code>String</code> exception handler JAR
	 * file location. <code>null</code> means the
	 * internal exception handler is used.
	 */
	public final String jarLocation;
	/**
	 * The <code>String</code> exception handler fully
	 * qualified class name. <code>null</code> means
	 * the internal exception handler is used.
	 */
	public final String classname;
	
	/**
	 * Constructor of <code>ConfigHandler</code>.
	 */
	public ConfigHandler() {
		this.jarLocation = null;
		this.classname = null;
	}
	
	/**
	 * Constructor of <code>ConfigHandler</code>.
	 * @param execution The <code>Element</code> of
	 * execution service tag.
	 */
	public ConfigHandler(final Element execution) {
		final Element handler = this.parseHandler(execution);
		this.jarLocation = this.parseJarFile(handler);
		this.classname = this.parseClassname(handler);
	}
	
	/**
	 * Parse the exception handler tag.
	 * @param execution The <code>Element</code> of
	 * execution service tag to parse from.
	 * @return The exception handler <code>Element</code>.
	 */
	private Element parseHandler(final Element execution) {
		final NodeList list = execution.getElementsByTagName(KConfigHandler.Root.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid execution service configuration. Must contain one exception handler tag.");
		}
		return (Element)list.item(0);
	}
	
	/**
	 * Parse the Jar file value.
	 * @param handler The <code>Element</code> of the
	 * exception handler tag to parse from.
	 * @return The <code>String</code> value.
	 */
	private String parseJarFile(final Element handler) {
		final NodeList list = handler.getElementsByTagName(KConfigHandler.JarFile.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid exception handler configuration. Must contain one Jar file tag.");
		}
		final String text = list.item(0).getTextContent();
		if (text == null || text.isEmpty()) return null;
		else return text;
	}
	
	/**
	 * Parse the class name value.
	 * @param handler The <code>Element</code> of the
	 * exception handler tag to parse from.
	 * @return The <code>String</code> value.
	 */
	private String parseClassname(final Element handler) {
		final NodeList list = handler.getElementsByTagName(KConfigHandler.Classname.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid exception handler configuration. Must contain one class name tag.");
		}
		final String text = list.item(0).getTextContent();
		if (text == null || text.isEmpty()) return null;
		else return text;
	}
	
	/**
	 * Create the execution service exception handler
	 * tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for exception
	 * handler.
	 */
	public Element toXML(final Document document) {
		final Element handler = document.createElement(KConfigHandler.Root.tag);
		// Jar tag.
		final Element jar = document.createElement(KConfigHandler.JarFile.tag);
		if (this.jarLocation != null) {
			jar.setTextContent(this.jarLocation);
		}
		handler.appendChild(jar);
		// Class name tag.
		final Element classname = document.createElement(KConfigHandler.Classname.tag);
		if (this.classname != null) {
			classname.setTextContent(this.classname);
		}
		handler.appendChild(classname);
		return handler;
	}
}
