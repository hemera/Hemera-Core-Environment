package hemera.core.environment.command.bundle.ham;

import hemera.core.environment.command.bundle.hbm.HBM;
import hemera.core.environment.command.bundle.hbm.HBMModule;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>HAM</code> defines the structure of the Hemera
 * Application Model.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class HAM {
	/**
	 * The <code>String</code> application name.
	 */
	public final String applicationName;
	/**
	 * The <code>List</code> of <code>HAMModule</code>
	 * the application contains.
	 */
	public final List<HAMModule> modules;
	
	/**
	 * Constructor of <code>HAM</code>.
	 * <p>
	 * This constructor creates a HAM configuration
	 * based on the given bundle.
	 * @param hbm The <code>HBM</code> bundle.
	 */
	public HAM(final HBM hbm) {
		this.applicationName = hbm.applicationName;
		final int size = hbm.modules.size();
		this.modules = new ArrayList<HAMModule>(size);
		for (int i = 0; i < size; i++) {
			final HBMModule hbmModule = hbm.modules.get(i);
			this.modules.add(new HAMModule(hbmModule));
		}
	}

	/**
	 * Constructor of <code>HAM</code>.
	 * @param document The <code>Document</code> to
	 * parse from.
	 */
	public HAM(final Document document) {
		this.applicationName = this.parseName(document);
		this.modules = this.parseModules(document);
	}
	
	/**
	 * Parse the given XML document and retrieve the
	 * application name tag value.
	 * @param document The <code>Document</code> to
	 * be parsed.
	 * @return The <code>String</code> application
	 * name.
	 */
	private String parseName(final Document document) {
		final NodeList list = document.getElementsByTagName(KHAM.ApplicationName.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid HAM file. Must contain one application name.");
		}
		return list.item(0).getTextContent();
	}
	
	/**
	 * Parse the given XML document and retrieve the
	 * modules list.
	 * @param document The <code>Document</code> to
	 * be parsed.
	 * @return The <code>List</code> of all the parsed
	 * <code>HAMModule</code>.
	 */
	private List<HAMModule> parseModules(final Document document) {
		// Verify document tag.
		final String doctag = document.getDocumentElement().getTagName();
		if (!doctag.equalsIgnoreCase(KHAM.Root.tag)) {
			throw new IllegalArgumentException("Invalid document: " + doctag);
		}
		// Retrieve modules tag.
		final NodeList modulestag = document.getElementsByTagName(KHAM.Modules.tag);
		if (modulestag == null || modulestag.getLength() != 1) {
			throw new IllegalArgumentException("Invalid HAM file. Must contain one modules tag.");
		}
		final Element modules = (Element)modulestag.item(0);
		// Parse modules.
		final NodeList modulelist = modules.getElementsByTagName(KHAM.Module.tag);
		if (modulelist == null || modulelist.getLength() <= 0) {
			throw new IllegalArgumentException("Invalid HAM file. Must contain at least one module tags.");
		}
		final int length = modulelist.getLength();
		final ArrayList<HAMModule> store = new ArrayList<HAMModule>(length);
		for (int i = 0; i < length; i++) {
			final Element moduleelement = (Element)modulelist.item(i);
			final HAMModule module = new HAMModule(moduleelement);
			store.add(module);
		}
		return store;
	}

	/**
	 * Convert the HAM configuration into a XML document.
	 * @return The XML <code>Document</code>.
	 * @throws ParserConfigurationException If document
	 * creation failed.
	 */
	public Document toXML() throws ParserConfigurationException {
		// Create the document.
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.newDocument();
		// Root tag.
		final Element root = document.createElement(KHAM.Root.tag);
		document.appendChild(root);
		// Application name tag.
		final Element appName = document.createElement(KHAM.ApplicationName.tag);
		appName.setTextContent(this.applicationName);
		root.appendChild(appName);
		// Modules tag.
		final Element modules = this.buildModulesTag(document);
		root.appendChild(modules);
		return document;
	}
	
	/**
	 * Create the module list tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The module list <code>Element</code>.
	 */
	private Element buildModulesTag(final Document document) {
		final Element modules = document.createElement(KHAM.Modules.tag);
		// Create a new tag for each module.
		final int size = this.modules.size();
		for (int i = 0; i < size; i++) {
			final HAMModule node = this.modules.get(i);
			final Element module = node.toXML(document);
			modules.appendChild(module);
		}
		return modules;
	}
}
