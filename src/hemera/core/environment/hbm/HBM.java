package hemera.core.environment.hbm;

import hemera.core.environment.AbstractTag;
import hemera.core.environment.hbm.key.KHBM;
import hemera.core.environment.hbm.key.KHBMResource;

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
	 * The <code>List</code> of <code>HBMResource</code>
	 * the bundle contains.
	 */
	public final List<HBMResource> resources;

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
		this.resources = this.parseResources(docElement);
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
	 * resources list.
	 * @param document The <code>Element</code> to be
	 * parsed.
	 * @return The <code>List</code> of all the parsed
	 * <code>HBMResource</code>.
	 */
	private List<HBMResource> parseResources(final Element document) {
		// Retrieve resources tag.
		final NodeList resourcestag = document.getElementsByTagName(KHBM.Resources.tag);
		if (resourcestag == null || resourcestag.getLength() != 1) {
			throw new IllegalArgumentException("Invalid HBM file. Must contain one resources tag.");
		}
		final Element resources = (Element)resourcestag.item(0);
		// Parse resources.
		final NodeList resourcelist = resources.getElementsByTagName(KHBMResource.Root.tag);
		if (resourcelist == null || resourcelist.getLength() <= 0) {
			throw new IllegalArgumentException("Invalid HBM file. Must contain at least one resource tags.");
		}
		final int length = resourcelist.getLength();
		final ArrayList<HBMResource> store = new ArrayList<HBMResource>(length);
		for (int i = 0; i < length; i++) {
			final Element resourceelement = (Element)resourcelist.item(i);
			final HBMResource resource = new HBMResource(resourceelement);
			store.add(resource);
		}
		return store;
	}
}
