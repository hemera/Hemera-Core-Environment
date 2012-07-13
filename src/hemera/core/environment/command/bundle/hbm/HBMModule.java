package hemera.core.environment.command.bundle.hbm;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>HBMModule</code> defines the immutable unit
 * representing a single module node in a HBM file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class HBMModule {
	/**
	 * The <code>String</code> source directory.
	 */
	public final String srcDir;
	/**
	 * The <code>String</code> library directory.
	 */
	public final String libDir;
	/**
	 * The <code>String</code> fully qualified module
	 * class name.
	 */
	public final String classname;
	/**
	 * The <code>String</code> optional module
	 * configuration file path.
	 */
	public final String configFile;
	/**
	 * The <code>String</code> optional resources
	 * directory.
	 */
	public final String resourcesDir;
	
	/**
	 * Constructor of <code>HBMModule</code>.
	 * @param node The <code>Element</code> XML node.
	 */
	HBMModule(final Element node) {
		// Verify tag name.
		final String tagname = node.getTagName();
		if (!tagname.equalsIgnoreCase(KHBM.Module.tag)) {
			throw new IllegalArgumentException("Invalid module tag: " + tagname);
		}
		// Source tag.
		final NodeList srcList = node.getElementsByTagName(KHBM.ModuleSourceDir.tag);
		if (srcList == null || srcList.getLength() != 1) {
			throw new IllegalArgumentException("Invalid module tag. Each module tag must contain one source directory tag.");
		}
		this.srcDir = srcList.item(0).getTextContent();
		// Lib tag.
		final NodeList libList = node.getElementsByTagName(KHBM.ModuleLibraryDir.tag);
		if (libList == null || libList.getLength() != 1) {
			throw new IllegalArgumentException("Invalid module tag. Each module tag must contain one library directory tag.");
		}
		this.libDir = libList.item(0).getTextContent();
		// Class name tag.
		final NodeList classList = node.getElementsByTagName(KHBM.ModuleClassname.tag);
		if (classList == null || classList.getLength() != 1) {
			throw new IllegalArgumentException("Invalid module tag. Each module tag must contain one class name tag.");
		}
		this.classname = classList.item(0).getTextContent();
		// Optional configuration file.
		final NodeList configlist = node.getElementsByTagName(KHBM.ModuleConfigFile.tag);
		if (configlist == null || configlist.getLength() <= 0) this.configFile = null;
		else this.configFile = configlist.item(0).getTextContent();
		// Optional resources directory.
		final NodeList resourceList = node.getElementsByTagName(KHBM.ModuleResourceDir.tag);
		if (resourceList == null || resourceList.getLength() <= 0) this.resourcesDir = null;
		else this.resourcesDir = resourceList.item(0).getTextContent();
	}
	
	@Override
	public boolean equals(final Object o) {
		if (o instanceof HBMModule) {
			final HBMModule given = (HBMModule)o;
			final boolean src = this.srcDir.equals(given.srcDir);
			final boolean lib = this.libDir.equals(given.libDir);
			final boolean classname = this.classname.equals(given.classname);
			final boolean config = (this.configFile==null) ? (given.configFile==null) : this.configFile.equals(given.configFile);
			final boolean resources = (this.resourcesDir==null) ? (given.resourcesDir==null) : this.resourcesDir.equals(given.resourcesDir);
			return (src && lib && classname && config && resources);
		}
		return false;
	}
}
