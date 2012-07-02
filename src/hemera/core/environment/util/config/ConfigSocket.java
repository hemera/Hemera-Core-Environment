package hemera.core.environment.util.config;

import hemera.core.environment.enumn.KConfiguration;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>ConfigSocket</code> defines the structure of
 * the socket configuration.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class ConfigSocket {
	/**
	 * The <code>int</code> HTTP port Apache connection
	 * listener should listen to.
	 */
	public final int port;
	/**
	 * The <code>int</code> socket timeout value in
	 * milliseconds.
	 */
	public final int timeout;
	/**
	 * The <code>int</code> socket buffer size value in
	 * bytes.
	 */
	public final int bufferSize;
	
	/**
	 * Constructor of <code>ConfigSocket</code>.
	 */
	public ConfigSocket() {
		this.port = 80;
		this.timeout = 200;
		this.bufferSize = 8192;
	}
	
	/**
	 * Constructor of <code>ConfigSocket</code>.
	 * @param runtime The <code>Element</code> of the
	 * runtime tag.
	 */
	public ConfigSocket(final Element runtime) {
		final Element socket = this.parseSocket(runtime);
		this.port = this.parsePort(socket);
		this.timeout = this.parseTimeout(socket);
		this.bufferSize = this.parseBufferSize(socket);
	}
	
	/**
	 * Parse the socket tag.
	 * @param runtime The <code>Element</code> of
	 * runtime tag to parse from.
	 * @return The socket <code>Element</code>.
	 */
	private Element parseSocket(final Element runtime) {
		final NodeList list = runtime.getElementsByTagName(KConfiguration.Socket.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid runtime configuration. Must contain one socket tag.");
		}
		return (Element)list.item(0);
	}
	
	/**
	 * Parse the port value.
	 * @param socket The <code>Element</code> of the
	 * socket tag to parse from.
	 * @return The <code>int</code> value.
	 */
	private int parsePort(final Element socket) {
		final NodeList list = socket.getElementsByTagName(KConfiguration.SocketPort.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid socket configuration. Must contain one port tag.");
		}
		return Integer.valueOf(list.item(0).getTextContent());
	}
	
	/**
	 * Parse the timeout value.
	 * @param socket The <code>Element</code> of the
	 * socket tag to parse from.
	 * @return The <code>int</code> value.
	 */
	private int parseTimeout(final Element socket) {
		final NodeList list = socket.getElementsByTagName(KConfiguration.SocketTimeout.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid socket configuration. Must contain one timeout tag.");
		}
		return Integer.valueOf(list.item(0).getTextContent());
	}
	
	/**
	 * Parse the buffer size value.
	 * @param socket The <code>Element</code> of the
	 * socket tag to parse from.
	 * @return The <code>int</code> value.
	 */
	private int parseBufferSize(final Element socket) {
		final NodeList list = socket.getElementsByTagName(KConfiguration.SocketBufferSize.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid socket configuration. Must contain one buffer size tag.");
		}
		return Integer.valueOf(list.item(0).getTextContent());
	}
	
	/**
	 * Create the socket configuration tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for socket
	 * configuration.
	 */
	public Element toXML(final Document document) {
		final Element socket = document.createElement(KConfiguration.Socket.tag);
		// Port tag.
		final Element port = document.createElement(KConfiguration.SocketPort.tag);
		port.setTextContent(String.valueOf(this.port));
		socket.appendChild(port);
		// Timeout tag.
		final Element timeout = document.createElement(KConfiguration.SocketTimeout.tag);
		timeout.setTextContent(String.valueOf(this.timeout));
		socket.appendChild(timeout);
		// Buffer size tag.
		final Element buffersize = document.createElement(KConfiguration.SocketBufferSize.tag);
		buffersize.setTextContent(String.valueOf(this.bufferSize));
		socket.appendChild(buffersize);
		return socket;
	}
}
