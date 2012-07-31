package hemera.core.environment.hbm;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import hemera.core.environment.AbstractTag;
import hemera.core.environment.hbm.key.KHBM;
import hemera.core.environment.hbm.key.KHBMDependency;
import hemera.core.environment.hbm.key.KHBMShared;

/**
 * <code>HBMShared</code> defines the data structure
 * of the shared section of a Hemera Bundle Model.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class HBMShared extends AbstractTag {
	/**
	 * The <code>String</code> optional shared
	 * configuration file.
	 */
	public final String configFile;
	/**
	 * The <code>String</code> optional shared resources
	 * directory.
	 */
	public final String resourcesDir;
	/**
	 * The <code>List</code> of <code>HBMDepdency</code>
	 * shared by all modules. <code>null</code> if there
	 * are no shared dependencies.
	 */
	public final List<HBMDependency> dependencies;

	/**
	 * Constructor of <code>HBMShared</code>.
	 * @param node The shared root <code>Element</code>.
	 */
	HBMShared(final Element node) {
		super(node, KHBM.Shared.tag);
		this.configFile = this.parseTagValue(node, KHBMShared.ConfigFile.tag, true);
		this.resourcesDir = this.parseTagValue(node, KHBMShared.ResourcesDir.tag, true);
		this.dependencies = this.parseDependencies(node);
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
		final NodeList list = document.getElementsByTagName(KHBMShared.Dependencies.tag);
		if (list == null || list.getLength() != 1) return null;
		final Element dependenciesRoot = (Element)list.item(0);
		final NodeList dependenciesList = dependenciesRoot.getElementsByTagName(KHBMDependency.Root.tag);
		final int size = dependenciesList.getLength();
		final ArrayList<HBMDependency> store = new ArrayList<HBMDependency>();
		for (int i = 0; i < size; i++) {
			final HBMDependency dependency = new HBMDependency((Element)dependenciesList.item(i));
			store.add(dependency);
		}
		return store;
	}
}
