package hemera.core.environment.config;

import hemera.core.environment.config.key.KConfigSocket;

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
	 * The <code>String</code> optional path to the
	 * SSL certificate.
	 */
	public final String certPath;
	/**
	 * The <code>String</code> optional password used
	 * to protect the certificate public key.
	 */
	public final String keyPass;

	/**
	 * Constructor of <code>ConfigSocket</code>.
	 */
	public ConfigSocket() {
		this.port = 80;
		this.timeout = 51200;
		this.bufferSize = 524288;
		this.certPath = null;
		this.keyPass = null;
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
		this.certPath = this.parseCertPath(socket);
		this.keyPass = this.parseKeyPassword(socket);
	}

	/**
	 * Parse the socket tag.
	 * @param runtime The <code>Element</code> of
	 * runtime tag to parse from.
	 * @return The socket <code>Element</code>.
	 */
	private Element parseSocket(final Element runtime) {
		final NodeList list = runtime.getElementsByTagName(KConfigSocket.Root.tag);
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
		final NodeList list = socket.getElementsByTagName(KConfigSocket.Port.tag);
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
		final NodeList list = socket.getElementsByTagName(KConfigSocket.Timeout.tag);
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
		final NodeList list = socket.getElementsByTagName(KConfigSocket.BufferSize.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid socket configuration. Must contain one buffer size tag.");
		}
		return Integer.valueOf(list.item(0).getTextContent());
	}

	/**
	 * Parse optional certificate path value.
	 * @param socket The <code>Element</code> of the
	 * socket tag to parse from.
	 * @return The <code>String</code> value.
	 */
	private String parseCertPath(final Element socket) {
		final NodeList list = socket.getElementsByTagName(KConfigSocket.Certificate.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid socket configuration. Must contain one certificate tag.");
		}
		final String text = list.item(0).getTextContent();
		if (text == null || text.isEmpty()) return null;
		else return text;
	}

	/**
	 * Parse optional certificate password.
	 * @param socket The <code>Element</code> of the
	 * socket tag to parse from.
	 * @return The <code>String</code> value.
	 */
	private String parseKeyPassword(final Element socket) {
		final NodeList list = socket.getElementsByTagName(KConfigSocket.KeyPassword.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid socket configuration. Must contain one certificate password tag.");
		}
		final String text = list.item(0).getTextContent();
		if (text == null || text.isEmpty()) return null;
		else return text;
	}

	/**
	 * Create the socket configuration tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for socket
	 * configuration.
	 */
	public Element toXML(final Document document) {
		final Element socket = document.createElement(KConfigSocket.Root.tag);
		// Port tag.
		final Element port = document.createElement(KConfigSocket.Port.tag);
		port.setTextContent(String.valueOf(this.port));
		socket.appendChild(port);
		// Timeout tag.
		final Element timeout = document.createElement(KConfigSocket.Timeout.tag);
		timeout.setTextContent(String.valueOf(this.timeout));
		socket.appendChild(timeout);
		// Buffer size tag.
		final Element buffersize = document.createElement(KConfigSocket.BufferSize.tag);
		buffersize.setTextContent(String.valueOf(this.bufferSize));
		socket.appendChild(buffersize);
		// Certificate tag.
		final Element certPath = document.createElement(KConfigSocket.Certificate.tag);
		if (this.certPath != null) {
			certPath.setTextContent(this.certPath);
		}
		socket.appendChild(certPath);
		// Key password tag.
		final Element keyPass = document.createElement(KConfigSocket.KeyPassword.tag);
		if (this.keyPass != null) {
			keyPass.setTextContent(this.keyPass);
		}
		socket.appendChild(keyPass);
		return socket;
	}
}
