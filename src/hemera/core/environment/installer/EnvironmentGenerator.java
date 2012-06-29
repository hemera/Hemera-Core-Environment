package hemera.core.environment.installer;

import hemera.core.environment.enumn.DEnvironment;
import hemera.core.environment.enumn.KEnvironment;
import hemera.core.environment.util.UEnvironment;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <code>EnvironmentGenerator</code> defines the utility
 * implementation that generates a default environment
 * configuration.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class EnvironmentGenerator {
	/**
	 * The <code>String</code> home directory.
	 */
	private final String homeDir;
	
	/**
	 * Constructor of <code>EnvironmentGenerator</code>.
	 * @param homeDir The <code>String</code> home
	 * directory.
	 */
	public EnvironmentGenerator(final String homeDir) {
		this.homeDir = homeDir;
	}

	/**
	 * Generate the default environment configuration
	 * document.
	 * @return The <code>Document</code> instance.
	 * @throws ParserConfigurationException If document
	 * generation failed.
	 */
	public Document generate() throws ParserConfigurationException {
		// Create the document.
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.newDocument();
		// Root tag.
		final Element root = document.createElement(KEnvironment.Root.tag);
		document.appendChild(root);
		// Version tag.
		final Element version = document.createElement(KEnvironment.Version.tag);
		version.setTextContent(DEnvironment.Version.value);
		root.appendChild(version);
		// Runtime tag.
		final Element runtime = this.buildRuntimeTag(document);
		root.appendChild(runtime);
		// Apps tag.
		final Element apps = document.createElement(KEnvironment.Applications.tag);
		root.appendChild(apps);
		return document;
	}
	
	/**
	 * Create the default runtime configuration tag
	 * using the given document.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for runtime
	 * configuration.
	 */
	private Element buildRuntimeTag(final Document document) {
		final Element runtime = document.createElement(KEnvironment.Runtime.tag);
		// Execution service tag.
		final Element executionService = this.buildExecutionServiceTag(document);
		runtime.appendChild(executionService);
		// Socket tag.
		final Element socket = this.buildSocketTag(document);
		runtime.appendChild(socket);
		// Logging tag.
		final Element logging = this.buildLoggingTag(document);
		runtime.appendChild(logging);
		return runtime;
	}
	
	/**
	 * Create the default execution service tag using
	 * the given document.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for execution
	 * service.
	 */
	private Element buildExecutionServiceTag(final Document document) {
		final Element executionService = document.createElement(KEnvironment.ExecutionService.tag);
		// Use scalable service tag.
		final Element useScalable = document.createElement(KEnvironment.UseScalableService.tag);
		useScalable.setTextContent(String.valueOf(false));
		executionService.appendChild(useScalable);
		// Listener tag.
		final Element listener = this.buildExecutionServiceListenerTag(document);
		executionService.appendChild(listener);
		// Exception handler tag.
		final Element handler = this.buildExecutionServiceExceptionHandlerTag(document);
		executionService.appendChild(handler);
		// Assisted service tag.
		final Element assisted = this.buildExecutionServiceAssistedTag(document);
		executionService.appendChild(assisted);
		// Scalable service tag.
		final Element scalable = this.buildExecutionServiceScalableTag(document);
		executionService.appendChild(scalable);
		return executionService;
	}

	/**
	 * Create the default execution service listener
	 * tag using the given document.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The listener <code>Element</code>.
	 */
	private Element buildExecutionServiceListenerTag(final Document document) {
		final Element listener = document.createElement(KEnvironment.ExecutionListener.tag);
		// Empty Jar and class name tags.
		final Element jar = document.createElement(KEnvironment.ExecutionListenerJarFile.tag);
		final Element classname = document.createElement(KEnvironment.ExecutionListenerClassname.tag);
		listener.appendChild(jar);
		listener.appendChild(classname);
		return listener;
	}

	/**
	 * Create the default execution service exception
	 * handler tag using the given document.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for exception
	 * handler.
	 */
	private Element buildExecutionServiceExceptionHandlerTag(final Document document) {
		final Element handler = document.createElement(KEnvironment.ExceptionHandler.tag);
		// Empty Jar and class name tags.
		final Element jar = document.createElement(KEnvironment.ExceptionHandlerJarFile.tag);
		final Element classname = document.createElement(KEnvironment.ExceptionHandlerClassname.tag);
		handler.appendChild(jar);
		handler.appendChild(classname);
		return handler;
	}

	/**
	 * Create the default execution service assisted
	 * service tag using the given document.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for assisted
	 * service.
	 */
	private Element buildExecutionServiceAssistedTag(final Document document) {
		final Element assisted = document.createElement(KEnvironment.AssistedService.tag);
		// Executor count tag.
		final Element executorCount = document.createElement(KEnvironment.AssistedServiceExecutorCount.tag);
		executorCount.setTextContent(DEnvironment.AssistedServiceExecutorCount.value);
		assisted.appendChild(executorCount);
		// Buffer size tag.
		final Element bufferSize = document.createElement(KEnvironment.AssistedServiceMaxBufferSize.tag);
		bufferSize.setTextContent(DEnvironment.AssistedServiceMaxBufferSize.value);
		assisted.appendChild(bufferSize);
		// Idle time tag.
		final Element idleTime = document.createElement(KEnvironment.AssistedServiceIdleTime.tag);
		idleTime.setTextContent(DEnvironment.AssistedServiceIdleTime.value);
		assisted.appendChild(idleTime);
		return assisted;
	}

	/**
	 * Create the default execution service scalable
	 * service tag using the given document.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for scalable
	 * service.
	 */
	private Element buildExecutionServiceScalableTag(final Document document) {
		final Element scalable = document.createElement(KEnvironment.ScalableService.tag);
		// Minimum executor count tag.
		final Element minimum = document.createElement(KEnvironment.ScalableServiceMinExecutor.tag);
		minimum.setTextContent(DEnvironment.ScalableServiceMinExecutor.value);
		scalable.appendChild(minimum);
		// Maximum executor count tag.
		final Element maximum = document.createElement(KEnvironment.ScalableServiceMaxExecutor.tag);
		maximum.setTextContent(DEnvironment.ScalableServiceMaxExecutor.value);
		scalable.appendChild(maximum);
		// Time out tag.
		final Element timeout = document.createElement(KEnvironment.ScalableServiceTimeout.tag);
		timeout.setTextContent(DEnvironment.ScalableServiceTimeout.value);
		scalable.appendChild(timeout);
		return scalable;
	}

	/**
	 * Create the default socket configuration tag
	 * using the given document.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for socket
	 * configuration.
	 */
	private Element buildSocketTag(final Document document) {
		final Element socket = document.createElement(KEnvironment.Socket.tag);
		// Port tag.
		final Element port = document.createElement(KEnvironment.SocketPort.tag);
		port.setTextContent(DEnvironment.HTTPPort.value);
		socket.appendChild(port);
		// Timeout tag.
		final Element timeout = document.createElement(KEnvironment.SocketTimeout.tag);
		timeout.setTextContent(DEnvironment.SocketTimeout.value);
		socket.appendChild(timeout);
		// Buffer size tag.
		final Element buffersize = document.createElement(KEnvironment.SocketBufferSize.tag);
		buffersize.setTextContent(DEnvironment.SocketBufferSize.value);
		socket.appendChild(buffersize);
		return socket;
	}

	/**
	 * Create the default logging configuration tag
	 * using the given document.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The <code>Element</code> for logging
	 * configuration.
	 */
	private Element buildLoggingTag(final Document document) {
		final Element logging = document.createElement(KEnvironment.Logging.tag);
		// Directory tag.
		final Element directory = document.createElement(KEnvironment.LoggingDirectory.tag);
		directory.setTextContent(UEnvironment.instance.getLogDir(this.homeDir));
		logging.appendChild(directory);
		return logging;
	}
}
