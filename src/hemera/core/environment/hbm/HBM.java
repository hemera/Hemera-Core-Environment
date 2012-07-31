package hemera.core.environment.hbm;

import hemera.core.environment.AbstractTag;
import hemera.core.environment.hbm.key.KHBM;
import hemera.core.environment.hbm.key.KHBMModule;

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
	 * The optional <code>HBMShared</code> section.
	 */
	public final HBMShared shared;
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
		this.shared = this.parseShared(docElement);
		this.modules = this.parseModules(docElement);
	}

	/**
	 * Parse the given XML document and retrieve the
	 * shared section.
	 * @param document The <code>Element</code> to be
	 * parsed.
	 * @return The <code>HBMShared</code> structure.
	 * Or <code>null</code> if there are no shared tag.
	 */
	private HBMShared parseShared(final Element document) {
		// Retrieve shared tag.
		final NodeList sharedtag = document.getElementsByTagName(KHBM.Shared.tag);
		if (sharedtag == null || sharedtag.getLength() != 1) return null;
		// Parse shared.
		final Element shared = (Element)sharedtag.item(0);
		return new HBMShared(shared);
	}

	/**
	 * Parse the given XML document and retrieve the
	 * modules list.
	 * @param document The <code>Element</code> to be
	 * parsed.
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
		final NodeList modulelist = modules.getElementsByTagName(KHBMModule.Root.tag);
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
