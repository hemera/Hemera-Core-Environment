package hemera.core.environment.util.config;

import hemera.core.environment.enumn.config.KConfigLogging;
import hemera.core.environment.util.UEnvironment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>ConfigLogging</code> defines the structure of
 * the logging configuration.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class ConfigLogging {
	/**
	 * The <code>Boolean</code> value indicating if the
	 * system logging is enabled.
	 */
	public final boolean enabled;
	/**
	 * The <code>String</code> value indicating the
	 * logging file directory.
	 */
	public final String directory;
	/**
	 * The <code>int</code> value indicating the
	 * maximum size of a single log file before a new
	 * log file is rotated in.
	 */
	public final int fileSize;
	/**
	 * The <code>int</code> value indicating the
	 * total number of log files to use for rotation.
	 */
	public final int fileCount;
	
	/**
	 * Constructor of <code>ConfigLogging</code>.
	 * @param homeDir The <code>String</code> install
	 * home directory.
	 */
	public ConfigLogging(final String homeDir) {
		this.enabled = true;
		this.directory = UEnvironment.instance.getLogDir(homeDir);
		this.fileSize = 1048576;
		this.fileCount = 1;
	}
	
	/**
	 * Constructor of <code>ConfigLogging</code>.
	 * @param runtime The <code>Element</code> of the
	 * runtime tag.
	 */
	public ConfigLogging(final Element runtime) {
		final Element logging = this.parseLogging(runtime);
		this.enabled = this.parseEnabled(logging);
		this.directory = this.parseDirectory(logging);
		this.fileSize = this.parseFileSize(logging);
		this.fileCount = this.parseFileCount(logging);
	}
	
	/**
	 * Parse the logging tag.
	 * @param runtime The <code>Element</code> of
	 * runtime tag to parse from.
	 * @return The logging <code>Element</code>.
	 */
	private Element parseLogging(final Element runtime) {
		final NodeList list = runtime.getElementsByTagName(KConfigLogging.Root.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid runtime configuration. Must contain one logging tag.");
		}
		return (Element)list.item(0);
	}
	
	/**
	 * Parse the enabled value.
	 * @param logging The <code>Element</code> of the
	 * logging tag to parse from.
	 * @return The <code>boolean</code> value.
	 */
	private boolean parseEnabled(final Element logging) {
		final NodeList list = logging.getElementsByTagName(KConfigLogging.Enabled.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid logging configuration. Must contain one enabled tag.");
		}
		return Boolean.valueOf(list.item(0).getTextContent());
	}
	
	/**
	 * Parse the directory value.
	 * @param logging The <code>Element</code> of the
	 * logging tag to parse from.
	 * @return The <code>String</code> value.
	 */
	private String parseDirectory(final Element logging) {
		final NodeList list = logging.getElementsByTagName(KConfigLogging.Directory.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid logging configuration. Must contain one directory tag.");
		}
		return list.item(0).getTextContent();
	}
	
	/**
	 * Parse the file size value.
	 * @param logging The <code>Element</code> of the
	 * logging tag to parse from.
	 * @return The <code>int</code> value.
	 */
	private int parseFileSize(final Element logging) {
		final NodeList list = logging.getElementsByTagName(KConfigLogging.FileSize.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid logging configuration. Must contain one file size tag.");
		}
		return Integer.valueOf(list.item(0).getTextContent());
	}
	
	/**
	 * Parse the file count value.
	 * @param logging The <code>Element</code> of the
	 * logging tag to parse from.
	 * @return The <code>int</code> value.
	 */
	private int parseFileCount(final Element logging) {
		final NodeList list = logging.getElementsByTagName(KConfigLogging.FileCount.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid logging configuration. Must contain one file count tag.");
		}
		return Integer.valueOf(list.item(0).getTextContent());
	}
	
	/**
	 * Create the logging configuration tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for logging
	 * configuration.
	 */
	public Element toXML(final Document document) {
		final Element logging = document.createElement(KConfigLogging.Root.tag);
		// Enabled tag.
		final Element enabled = document.createElement(KConfigLogging.Enabled.tag);
		enabled.setTextContent(String.valueOf(this.enabled));
		logging.appendChild(enabled);
		// Directory tag.
		final Element directory = document.createElement(KConfigLogging.Directory.tag);
		directory.setTextContent(this.directory);
		logging.appendChild(directory);
		// File size tag.
		final Element fileSize = document.createElement(KConfigLogging.FileSize.tag);
		fileSize.setTextContent(String.valueOf(this.fileSize));
		logging.appendChild(fileSize);
		// File count tag.
		final Element fileCount = document.createElement(KConfigLogging.FileCount.tag);
		fileCount.setTextContent(String.valueOf(this.fileCount));
		logging.appendChild(fileCount);
		return logging;
	}
}
