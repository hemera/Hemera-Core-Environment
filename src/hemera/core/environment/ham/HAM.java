package hemera.core.environment.ham;

import hemera.core.environment.AbstractTag;
import hemera.core.environment.ham.key.KHAM;
import hemera.core.environment.ham.key.KHAMResource;
import hemera.core.environment.hbm.HBM;
import hemera.core.environment.hbm.HBMResource;

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
 * @version 1.0.4
 */
public class HAM extends AbstractTag {
	/**
	 * The <code>String</code> application name.
	 */
	public final String applicationName;
	/**
	 * The optional <code>String</code> application path.
	 */
	public final String applicationPath;
	/**
	 * The optional <code>HAMShared</code> data.
	 */
	public final HAMShared shared;
	/**
	 * The <code>List</code> of <code>HAMResource</code>
	 * the application contains.
	 */
	public final List<HAMResource> resources;

	/**
	 * Constructor of <code>HAM</code>.
	 * <p>
	 * This constructor creates a HAM configuration
	 * based on the given bundle.
	 * @param hbm The <code>HBM</code> bundle.
	 */
	public HAM(final HBM hbm) {
		this.applicationName = hbm.applicationName;
		this.applicationPath = hbm.applicationPath;
		this.shared = (hbm.shared==null) ? null : new HAMShared(hbm.shared);
		final int size = hbm.resources.size();
		this.resources = new ArrayList<HAMResource>(size);
		for (int i = 0; i < size; i++) {
			final HBMResource hbmResource = hbm.resources.get(i);
			this.resources.add(new HAMResource(hbmResource));
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
		this.applicationPath = this.parseTagValue(docElement, KHAM.ApplicationPath.tag, true);
		final NodeList list = docElement.getElementsByTagName(KHAM.Shared.tag);
		if (list == null || list.getLength() != 1) this.shared = null;
		else this.shared = new HAMShared((Element)list.item(0));
		this.resources = this.parseResources(document);
	}

	/**
	 * Parse the given XML document and retrieve the
	 * resources list.
	 * @param document The <code>Document</code> to
	 * be parsed.
	 * @return The <code>List</code> of all the parsed
	 * <code>HAMResource</code>.
	 */
	private List<HAMResource> parseResources(final Document document) {
		// Verify document tag.
		final String doctag = document.getDocumentElement().getTagName();
		if (!doctag.equalsIgnoreCase(KHAM.Root.tag)) {
			throw new IllegalArgumentException("Invalid document: " + doctag);
		}
		// Retrieve resources tag.
		final NodeList resourcestag = document.getElementsByTagName(KHAM.Resources.tag);
		if (resourcestag == null || resourcestag.getLength() != 1) {
			throw new IllegalArgumentException("Invalid HAM file. Must contain one resources tag.");
		}
		final Element resources = (Element)resourcestag.item(0);
		// Parse resources.
		final NodeList resourceList = resources.getElementsByTagName(KHAMResource.Root.tag);
		if (resourceList == null || resourceList.getLength() <= 0) {
			throw new IllegalArgumentException("Invalid HAM file. Must contain at least one resource tags.");
		}
		final int length = resourceList.getLength();
		final ArrayList<HAMResource> store = new ArrayList<HAMResource>(length);
		for (int i = 0; i < length; i++) {
			final Element resourceElement = (Element)resourceList.item(i);
			final HAMResource resource = new HAMResource(resourceElement);
			store.add(resource);
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
		// Application path tag.
		final Element appPath = document.createElement(KHAM.ApplicationPath.tag);
		appPath.setTextContent(this.applicationPath);
		root.appendChild(appPath);
		// Optional shared tag.
		if (this.shared != null) {
			final Element sharedElement = this.shared.toXML(document);
			root.appendChild(sharedElement);
		}
		// Resources tag.
		final Element resources = this.buildResourcesTag(document);
		root.appendChild(resources);
		return document;
	}

	/**
	 * Create the resource list tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The resource list <code>Element</code>.
	 */
	private Element buildResourcesTag(final Document document) {
		final Element resources = document.createElement(KHAM.Resources.tag);
		// Create a new tag for each resource.
		final int size = this.resources.size();
		for (int i = 0; i < size; i++) {
			final HAMResource node = this.resources.get(i);
			final Element resource = node.toXML(document, this.shared);
			resources.appendChild(resource);
		}
		return resources;
	}
}
