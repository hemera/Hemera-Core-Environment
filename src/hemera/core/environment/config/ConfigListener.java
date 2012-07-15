package hemera.core.environment.config;

import hemera.core.environment.config.key.KConfigListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>ConfigListener</code> defines the structure of
 * the execution service listener configuration.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class ConfigListener {
	/**
	 * The <code>String</code> execution service listener
	 * JAR file location. <code>null</code> means the
	 * internal logging listener is used.
	 */
	public final String jarLocation;
	/**
	 *  The <code>String</code> execution service listener
	 * fully qualified class name. <code>null</code> means
	 * the internal logging listener is used.
	 */
	public final String classname;
	
	/**
	 * Constructor of <code>ConfigListener</code>.
	 */
	public ConfigListener() {
		this.jarLocation = null;
		this.classname = null;
	}
	
	/**
	 * Constructor of <code>ConfigListener</code>.
	 * @param execution The <code>Element</code> of
	 * execution service tag.
	 */
	public ConfigListener(final Element execution) {
		final Element listener = this.parseListener(execution);
		this.jarLocation = this.parseJarFile(listener);
		this.classname = this.parseClassname(listener);
	}
	
	/**
	 * Parse the service listener tag.
	 * @param execution The <code>Element</code> of
	 * execution service tag to parse from.
	 * @return The service listener <code>Element</code>.
	 */
	private Element parseListener(final Element execution) {
		final NodeList list = execution.getElementsByTagName(KConfigListener.Root.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid execution service configuration. Must contain one service listener tag.");
		}
		return (Element)list.item(0);
	}
	
	/**
	 * Parse the Jar file value.
	 * @param listener The <code>Element</code> of the
	 * service listener tag to parse from.
	 * @return The <code>String</code> value.
	 */
	private String parseJarFile(final Element listener) {
		final NodeList list = listener.getElementsByTagName(KConfigListener.JarFile.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid service listener configuration. Must contain one Jar file tag.");
		}
		final String text = list.item(0).getTextContent();
		if (text == null || text.isEmpty()) return null;
		else return text;
	}
	
	/**
	 * Parse the class name value.
	 * @param listener The <code>Element</code> of the
	 * service listener tag to parse from.
	 * @return The <code>String</code> value.
	 */
	private String parseClassname(final Element listener) {
		final NodeList list = listener.getElementsByTagName(KConfigListener.Classname.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid service listener configuration. Must contain one class name tag.");
		}
		final String text = list.item(0).getTextContent();
		if (text == null || text.isEmpty()) return null;
		else return text;
	}
	
	/**
	 * Create the execution service listener tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The listener <code>Element</code>.
	 */
	public Element toXML(final Document document) {
		final Element listener = document.createElement(KConfigListener.Root.tag);
		// Jar tag.
		final Element jar = document.createElement(KConfigListener.JarFile.tag);
		if (this.jarLocation != null) {
			jar.setTextContent(this.jarLocation);
		}
		listener.appendChild(jar);
		// Class name tag.
		final Element classname = document.createElement(KConfigListener.Classname.tag);
		if (this.classname != null) {
			classname.setTextContent(this.classname);
		}
		listener.appendChild(classname);
		return listener;
	}
}
