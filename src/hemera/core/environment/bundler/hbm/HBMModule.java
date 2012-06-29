package hemera.core.environment.bundler.hbm;

import hemera.core.environment.bundler.enumn.KHBM;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>HBMModule</code> defines the immutable unit
 * representing a single module node in a HBM file. It
 * provides the functionality to parse a given XML tag
 * and set the proper instance values.
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
	 * The <code>String</code> module configuration
	 * file path.
	 */
	public final String configFile;
	
	/**
	 * Constructor of <code>HBMModule</code>.
	 * @param node The <code>Element</code> XML node.
	 */
	HBMModule(final Element node) {
		// Verify tag name.
		final String tagname = node.getTagName();
		if (!tagname.equalsIgnoreCase(KHBM.Module.tag)) throw new IllegalArgumentException("Invalid module node tag: " + tagname);
		// Parse values.
		this.srcDir = node.getElementsByTagName(KHBM.ModuleSourceDir.tag).item(0).getTextContent();
		this.libDir = node.getElementsByTagName(KHBM.ModuleLibraryDir.tag).item(0).getTextContent();
		this.classname = node.getElementsByTagName(KHBM.ModuleClassname.tag).item(0).getTextContent();
		// Optional configuration file.
		final NodeList configlist = node.getElementsByTagName(KHBM.ModuleConfigFile.tag);
		if (configlist == null || configlist.getLength() <= 0) this.configFile = null;
		else this.configFile = configlist.item(0).getTextContent();
	}
	
	@Override
	public boolean equals(final Object o) {
		if (o instanceof HBMModule) {
			final HBMModule given = (HBMModule)o;
			final boolean src = this.srcDir.equals(given.srcDir);
			final boolean lib = this.libDir.equals(given.libDir);
			final boolean classname = this.classname.equals(given.classname);
			final boolean config = (this.configFile==null) ? (given.configFile==null) : this.configFile.equals(given.configFile);
			return (src && lib && classname && config);
		}
		return false;
	}
}
