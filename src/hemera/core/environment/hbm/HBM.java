package hemera.core.environment.hbm;

import hemera.core.environment.AbstractTag;
import hemera.core.environment.hbm.key.KHBM;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>HBM</code> defines the data structure of a
 * Hemera Bundle Model of an application.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class HBM extends AbstractTag {
	/**
	 * The <code>String</code> application name.
	 */
	public final String applicationName;
	/**
	 * The <code>String</code> optional shared
	 * configuration file.
	 */
	public final String sharedConfigFile;
	/**
	 * The <code>String</code> optional shared resources
	 * directory.
	 */
	public final String sharedResourcesDir;
	/**
	 * The <code>List</code> of <code>HBMDepdency</code>
	 * shared by all modules. <code>null</code> if there
	 * are no shared dependencies.
	 */
	public final List<HBMDependency> sharedDependencies;
	/**
	 * The <code>List</code> of <code>HBMModule</code>
	 * the bundle contains.
	 */
	public final List<HBMModule> modules;

	/**
	 * Constructor of <code>HBM</code>.
	 * @param document The <code>Document</code> to
	 * parse from.
	 */
	public HBM(final Document document) {
		super(document.getDocumentElement(), KHBM.Root.tag);
		final Element docElement = document.getDocumentElement();
		this.applicationName = this.parseTagValue(docElement, KHBM.ApplicationName.tag, false);
		this.sharedConfigFile = this.parseTagValue(docElement, KHBM.SharedConfigFile.tag, true);
		this.sharedResourcesDir = this.parseTagValue(docElement, KHBM.SharedResourcesDir.tag, true);
		this.sharedDependencies = this.parseDependencies(docElement);
		this.modules = this.parseModules(docElement);
	}

	/**
	 * Parse the given XML document and retrieve the
	 * modules list.
	 * @param document The <code>Element</code> to
	 * be parsed.
	 * @return The <code>List</code> of all the parsed
	 * <code>HBMModule</code>.
	 */
	private List<HBMModule> parseModules(final Element document) {
		// Retrieve modules tag.
		final NodeList modulestag = document.getElementsByTagName(KHBM.Modules.tag);
		if (modulestag == null || modulestag.getLength() != 1) {
			throw new IllegalArgumentException("Invalid HBM file. Must contain one modules tag.");
		}
		final Element modules = (Element)modulestag.item(0);
		// Parse modules.
		final NodeList modulelist = modules.getChildNodes();
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
	
	/**
	 * Parse the given XML document and retrieve the
	 * dependencies list.
	 * @param document The <code>Element</code> to
	 * be parsed.
	 * @return The <code>List</code> of all the parsed
	 * <code>HBMDependency</code>.
	 */
	private List<HBMDependency> parseDependencies(final Element document) {
		final NodeList list = document.getElementsByTagName(KHBM.SharedDependencies.tag);
		if (list == null || list.getLength() != 1) return null;
		final Element dependenciesRoot = (Element)list.item(0);
		final NodeList dependenciesList = dependenciesRoot.getChildNodes();
		final int size = dependenciesList.getLength();
		final ArrayList<HBMDependency> store = new ArrayList<HBMDependency>();
		for (int i = 0; i < size; i++) {
			final HBMDependency dependency = new HBMDependency((Element)dependenciesList.item(i));
			store.add(dependency);
		}
		return store;
	}
}
