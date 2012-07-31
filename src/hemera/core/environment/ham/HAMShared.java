package hemera.core.environment.ham;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hemera.core.environment.AbstractTag;
import hemera.core.environment.enumn.EEnvironment;
import hemera.core.environment.ham.key.KHAM;
import hemera.core.environment.ham.key.KHAMShared;
import hemera.core.environment.hbm.HBMShared;
import hemera.core.utility.FileUtils;

/**
 * <code>HAMShared</code> defines the data structure
 * of the shared section of a Hemera Application Model.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class HAMShared extends AbstractTag {
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
	 * Constructor of <code>HBMShared</code>.
	 * @param node The shared root <code>Element</code>.
	 */
	HAMShared(final Element node) {
		super(node, KHAM.Shared.tag);
		this.configFile = this.parseTagValue(node, KHAMShared.ConfigFile.tag, true);
		this.resourcesDir = this.parseTagValue(node, KHAMShared.ResourcesDir.tag, true);
	}
	
	/**
	 * Constructor of <code>HAMShared</code>.
	 * @param hbmShared The <code>HBMShared</code> data.
	 */
	HAMShared(final HBMShared hbmShared) {
		this.configFile = hbmShared.configFile;
		this.resourcesDir = hbmShared.resourcesDir;
	}
	
	/**
	 * Convert this HAM shared node to a XML element.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @return The shared <code>Element</code>.
	 */
	public Element toXML(final Document document) {
		final Element shared = document.createElement(KHAM.Shared.tag);
		// Library directory.
		final StringBuilder libBuilder = new StringBuilder();
		libBuilder.append(KHAM.PlaceholderAppsDir.tag);
		libBuilder.append(EEnvironment.AppSharedDir.value).append(File.separator);
		libBuilder.append(EEnvironment.AppSharedLibDir.value).append(File.separator);
		final Element lib = document.createElement(KHAMShared.LibraryDir.tag);
		lib.setTextContent(libBuilder.toString());
		shared.appendChild(lib);
		// Resources directory.
		if (this.resourcesDir != null && this.resourcesDir.length() > 0) {
			// Build resources directory after deployment.
			// HOME/apps/APPLICATION/shared/resources/
			final Element resources = document.createElement(KHAMShared.ResourcesDir.tag);
			final StringBuilder builder = new StringBuilder();
			builder.append(KHAM.PlaceholderAppsDir.tag);
			builder.append(EEnvironment.AppSharedDir.value).append(File.separator);
			builder.append(EEnvironment.AppSharedResourcesDir.value).append(File.separator);
			resources.setTextContent(FileUtils.instance.getValidDir(builder.toString()));
			shared.appendChild(resources);
		}
		return shared;
	}
}
