package hemera.core.environment.command.bundle.hbm;


import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>HBMParser</code> defines the implementation
 * that provides the functionality to parse a HBM file
 * into an application bundle..
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class HBMParser {
	
	/**
	 * Parse the HBM file located at given path into
	 * a Hemera bundle.
	 * @param path The <code>String</code> path to the
	 * HBM file.
	 * @return The <code>HBundle</code> instance.
	 * @throws Exception If parsing failed.
	 */
	public HBundle parse(final String path) throws Exception {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.parse(path);
		// Parse data.
		final String name = this.parseName(document);
		final List<HBMModule> modules = this.parseModules(document);
		return new HBundle(name, modules);
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
		final NodeList list = document.getElementsByTagName(KHBM.ApplicationName.tag);
		if (list == null || list.getLength() != 1) {
			throw new IllegalArgumentException("Invalid HBM file. Must contain one application name.");
		}
		return list.item(0).getTextContent();
	}
	
	/**
	 * Parse the given XML document and retrieve the
	 * modules list.
	 * @param document The <code>Document</code> to
	 * be parsed.
	 * @return The <code>List</code> of all the parsed
	 * <code>HBMModule</code>.
	 */
	private List<HBMModule> parseModules(final Document document) {
		// Verify document tag.
		final String doctag = document.getDocumentElement().getTagName();
		if (!doctag.equalsIgnoreCase(KHBM.Root.tag)) {
			throw new IllegalArgumentException("Invalid document: " + doctag);
		}
		// Retrieve modules tag.
		final NodeList modulestag = document.getElementsByTagName(KHBM.Modules.tag);
		if (modulestag == null || modulestag.getLength() != 1) {
			throw new IllegalArgumentException("Invalid HBM file. Must contain one modules tag.");
		}
		final Element modules = (Element)modulestag.item(0);
		// Parse modules.
		final NodeList modulelist = modules.getElementsByTagName(KHBM.Module.tag);
		if (modulelist == null || modulelist.getLength() <= 0) {
			throw new IllegalArgumentException("Invalid HBM file. Must contain at least one module tags.");
		}
		final int length = modulelist.getLength();
		final ArrayList<HBMModule> store = new ArrayList<HBMModule>(length);
		for (int i = 0; i < length; i++) {
			final Element moduleelement = (Element)modulelist.item(i);
			final HBMModule module = new HBMModule(moduleelement);
			store.add(module);
		}
		return store;
	}
}
