package hemera.core.environment.ham;

import hemera.core.environment.AbstractTag;
import hemera.core.environment.ham.key.KHAM;
import hemera.core.environment.ham.key.KHAMResource;
import hemera.core.environment.hbm.HBMResource;
import hemera.core.utility.FileUtils;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <code>HAMResource</code> defines the immutable unit
 * representing a single resource node in a HAM file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class HAMResource extends AbstractTag {
	/**
	 * The <code>String</code> fully qualified resource
	 * class name.
	 */
	public final String classname;
	/**
	 * The <code>String</code> optional resource
	 * configuration file path.
	 */
	public final String configFile;
	/**
	 * The <code>String</code> optional resources
	 * directory.
	 */
	public final String resourcesDir;

	/**
	 * Constructor of <code>HAMResource</code>.
	 * @param node The <code>Element</code> XML node.
	 */
	HAMResource(final Element node) {
		super(node, KHAMResource.Root.tag);
		this.classname = this.parseTagValue(node, KHAMResource.Classname.tag, false);
		this.configFile = this.parseTagValue(node, KHAMResource.ConfigFile.tag, true);
		this.resourcesDir = this.parseTagValue(node, KHAMResource.ResourcesDir.tag, true);
	}

	/**
	 * Constructor of <code>HAMResource</code>.
	 * <p>
	 * This constructor creates a HAM resource node
	 * based on the given HBM resource.
	 * @param hbmResource The <code>HBMResource</code>.
	 */
	HAMResource(final HBMResource hbmResource) {
		this.classname = hbmResource.classname;
		this.configFile = hbmResource.configFile;
		this.resourcesDir = hbmResource.resourcesDir;
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof HAMResource) {
			final HAMResource given = (HAMResource)o;
			final boolean classname = this.classname.equals(given.classname);
			final boolean config = (this.configFile==null) ? (given.configFile==null) : this.configFile.equals(given.configFile);
			return (classname && config);
		}
		return false;
	}

	/**
	 * Convert this HAM resource to a XML element.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @param shared The <code>HAMShared</code> data.
	 * @return The resource <code>Element</code>.
	 */
	public Element toXML(final Document document, final HAMShared shared) {
		final Element resource = document.createElement(KHAMResource.Root.tag);
		// Class-name tag.
		final Element classname = document.createElement(KHAMResource.Classname.tag);
		classname.setTextContent(this.classname);
		resource.appendChild(classname);
		// Configuration file tag.
		if ((this.configFile != null && this.configFile.length() > 0) ||
				(shared.configFile != null && shared.configFile.length() > 0)) {
			// Retrieve the configuration file name.
			String configFileName = null;
			if (this.configFile != null) {
				configFileName = new File(this.configFile).getName();
			} else {
				configFileName = new File(shared.configFile).getName();
			}
			// Build configuration file path after deployment.
			// HOME/apps/APPLICATION/RESOURCE/CONFIG.FILE
			final Element config = document.createElement(KHAMResource.ConfigFile.tag);
			final StringBuilder builder = new StringBuilder();
			builder.append(KHAM.PlaceholderAppsDir.tag);
			builder.append(this.classname).append(File.separator);
			builder.append(configFileName);
			config.setTextContent(builder.toString());
			resource.appendChild(config);
		}
		// Resources directory.
		if (this.resourcesDir != null && this.resourcesDir.length() > 0) {
			// Build resources directory after deployment.
			// HOME/apps/APPLICATION/RESOURCE/resources/
			final Element resources = document.createElement(KHAMResource.ResourcesDir.tag);
			final StringBuilder builder = new StringBuilder();
			builder.append(KHAM.PlaceholderAppsDir.tag);
			builder.append(this.classname).append(File.separator);
			builder.append("resources");
			resources.setTextContent(FileUtils.instance.getValidDir(builder.toString()));
			resource.appendChild(resources);
		}
		return resource;
	}
}
