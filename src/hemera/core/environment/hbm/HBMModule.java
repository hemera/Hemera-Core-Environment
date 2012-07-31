package hemera.core.environment.hbm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import hemera.core.environment.AbstractTag;
import hemera.core.environment.hbm.key.KHBMDependency;
import hemera.core.environment.hbm.key.KHBMModule;
import hemera.core.utility.FileUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * <code>HBMModule</code> defines the immutable unit
 * representing a single module node in a HBM file.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class HBMModule extends AbstractTag {
	/**
	 * The <code>String</code> source directory.
	 */
	public final String srcDir;
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
	 * The <code>List</code> of <code>HBMDependency</code>
	 * used by the module. This list may be empty if
	 * the module does not have specific dependencies.
	 */
	public final List<HBMDependency> dependencies;

	/**
	 * Constructor of <code>HBMModule</code>.
	 * @param node The <code>Element</code> XML node.
	 */
	HBMModule(final Element node) {
		super(node, KHBMModule.Root.tag);
		this.srcDir = this.parseTagValue(node, KHBMModule.SourceDir.tag, false);
		this.classname = this.parseTagValue(node, KHBMModule.Classname.tag, false);
		this.configFile = this.parseTagValue(node, KHBMModule.ConfigFile.tag, true);
		this.resourcesDir = this.parseTagValue(node, KHBMModule.ResourcesDir.tag, true);
		this.dependencies = new ArrayList<HBMDependency>();
		// Parse dependencies.
		final NodeList list = node.getElementsByTagName(KHBMModule.Dependencies.tag);
		if (list != null && list.getLength() > 0) {
			final Element dependenciesRoot = (Element)list.item(0);
			final NodeList dependenciesList = dependenciesRoot.getElementsByTagName(KHBMDependency.Root.tag);
			final int size = dependenciesList.getLength();
			for (int i = 0; i < size; i++) {
				final HBMDependency dependency = new HBMDependency((Element)dependenciesList.item(i));
				this.dependencies.add(dependency);
			}
		}
	}
	
	/**
	 * Process the module configuration by appending the
	 * application shared configuration content to the
	 * module's local configuration content and export
	 * the file into the given temporary directory.
	 * @param shared The <code>HBMShared</code> shared
	 * data structure.
	 * @param tempDir The <code>String</code> path of
	 * the temporary directory.
	 * @return The <code>File</code> of the processed
	 * appended configuration. <code>null</code> if
	 * the module does not have a configuration and the
	 * given shared configuration is <code>null</code>.
	 * @throws IOException If reading file failed.
	 * @throws SAXException If parsing file failed.
	 * @throws ParserConfigurationException If
	 * parsing file failed.
	 * @throws TransformerException If writing the
	 * XML document failed.
	 */
	public File processConfig(final HBMShared shared, final String tempDir) throws IOException, SAXException, ParserConfigurationException, TransformerException {
		if (shared == null || shared.configFile == null) {
			if (this.configFile == null) return null;
			else return new File(this.configFile);
		} else {
			// Read in shared configuration.
			final File sharedFile = new File(shared.configFile);
			final Document sharedDoc = FileUtils.instance.readAsDocument(sharedFile);
			// Retrieve all shared configuration children tags.
			final NodeList sharedChildren = sharedDoc.getDocumentElement().getChildNodes();
			// Read in local configuration.
			Document localDoc = null;
			if (this.configFile == null) {
				final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				final DocumentBuilder docBuilder = factory.newDocumentBuilder();
				localDoc = docBuilder.newDocument();
				// Create a root tag.
				final Element root = localDoc.createElement(this.classname);
				localDoc.appendChild(root);
			} else {
				localDoc = FileUtils.instance.readAsDocument(new File(this.configFile));
			}
			// Append all shared children to local document root tag.
			final int size = sharedChildren.getLength();
			final Node localRoot = localDoc.getFirstChild();
			for (int i = 0; i < size; i++) {
				localRoot.appendChild(localDoc.importNode(sharedChildren.item(i), true));
			}
			// Write the appended local document to a temporary file.
			String fileName = null;
			if (this.configFile == null) {
				fileName = this.classname + ".xml";
			} else {
				fileName = new File(this.configFile).getName();
			}
			return FileUtils.instance.writeDocument(localDoc, tempDir+fileName);
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof HBMModule) {
			final HBMModule given = (HBMModule)o;
			final boolean src = this.srcDir.equals(given.srcDir);
			final boolean classname = this.classname.equals(given.classname);
			final boolean config = (this.configFile==null) ? (given.configFile==null) : this.configFile.equals(given.configFile);
			final boolean resources = (this.resourcesDir==null) ? (given.resourcesDir==null) : this.resourcesDir.equals(given.resourcesDir);
			return (src && classname && config && resources);
		}
		return false;
	}
}
