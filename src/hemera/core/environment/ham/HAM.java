package hemera.core.environment.ham;

import hemera.core.environment.AbstractTag;
import hemera.core.environment.ham.key.KHAM;
import hemera.core.environment.ham.key.KHAMModule;
import hemera.core.environment.hbm.HBM;
import hemera.core.environment.hbm.HBMModule;

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
public class HAM extends AbstractTag {
	/**
	 * The <code>String</code> application name.
	 */
	public final String applicationName;
	/**
	 * The optional <code>HAMShared</code> data.
	 */
	public final HAMShared shared;
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
		this.shared = (hbm.shared==null) ? null : new HAMShared(hbm.shared);
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
		super(document.getDocumentElement(), KHAM.Root.tag);
		final Element docElement = document.getDocumentElement();
		this.applicationName = this.parseTagValue(docElement, KHAM.ApplicationName.tag, false);
		final NodeList list = docElement.getElementsByTagName(KHAM.Shared.tag);
		if (list == null || list.getLength() != 1) this.shared = null;
		else this.shared = new HAMShared((Element)list.item(0));
		this.modules = this.parseModules(document);
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
		final NodeList moduleList = modules.getElementsByTagName(KHAMModule.Root.tag);
		if (moduleList == null || moduleList.getLength() <= 0) {
			throw new IllegalArgumentException("Invalid HAM file. Must contain at least one module tags.");
		}
		final int length = moduleList.getLength();
		final ArrayList<HAMModule> store = new ArrayList<HAMModule>(length);
		for (int i = 0; i < length; i++) {
			final Element moduleElement = (Element)moduleList.item(i);
			final HAMModule module = new HAMModule(moduleElement);
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
		final DocumentBuilder docBuilder = factory.newDocumentBuilder();
		final Document document = docBuilder.newDocument();
		// Root tag.
		final Element root = document.createElement(KHAM.Root.tag);
		document.appendChild(root);
		// Application name tag.
		final Element appName = document.createElement(KHAM.ApplicationName.tag);
		appName.setTextContent(this.applicationName);
		root.appendChild(appName);
		// Optional shared tag.
		if (this.shared != null) {
			final Element sharedElement = this.shared.toXML(document);
			root.appendChild(sharedElement);
		}
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
