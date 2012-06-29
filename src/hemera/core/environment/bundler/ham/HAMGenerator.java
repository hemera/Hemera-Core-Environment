package hemera.core.environment.bundler.ham;

import hemera.core.environment.bundler.enumn.KHAM;
import hemera.core.environment.bundler.enumn.KHBM;
import hemera.core.environment.bundler.hbm.HBMModule;
import hemera.core.environment.bundler.hbm.HBundle;
import hemera.core.environment.util.UEnvironment;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <code>HAMGenerator</code> defines the implementation
 * that provides the functionality to generate a HAM,
 * Hemera Application Model XML document based on given
 * bundle.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class HAMGenerator {

	/**
	 * Generate a HAM document based on the given
	 * application bundle.
	 * @param bundle The <code>HBundle</code> node.
	 * @return The generated <code>Document</code>.
	 * @throws ParserConfigurationException If the
	 * document creation failed.
	 */
	public Document generate(final HBundle bundle) throws ParserConfigurationException {
		// Create the document.
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.newDocument();
		// Root tag.
		final Element root = document.createElement(KHAM.Root.tag);
		document.appendChild(root);
		// Application name tag.
		final Element appName = document.createElement(KHBM.ApplicationName.tag);
		appName.setTextContent(bundle.applicationName);
		root.appendChild(appName);
		// Modules tag.
		final Element modules = this.buildModulesTag(document, bundle);
		root.appendChild(modules);
		return document;
	}

	/**
	 * Create the module list tag.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @param bundle The <code>HBundle</code> node.
	 * @return The module list <code>Element</code>.
	 */
	private Element buildModulesTag(final Document document, final HBundle bundle) {
		final Element modules = document.createElement(KHBM.Modules.tag);
		// Create a new tag for each module.
		final int size = bundle.modules.size();
		for (int i = 0; i < size; i++) {
			final HBMModule node = bundle.modules.get(i);
			final Element module = this.buildModuleTag(document, node, bundle);
			modules.appendChild(module);
		}
		return modules;
	}

	/**
	 * Create a single module tag with given node.
	 * @param document The <code>Document</code> to
	 * create the new tags from.
	 * @param node The <code>HBMModule</code> node.
	 * @param bundle The <code>HBundle</code> node.
	 * @return The module list <code>Element</code>.
	 * @return The module <code>Element</code>.
	 */
	private Element buildModuleTag(final Document document, final HBMModule node, final HBundle bundle) {
		final Element module = document.createElement(KHBM.Module.tag);
		// Class-name tag.
		final Element classname = document.createElement(KHBM.ModuleClassname.tag);
		classname.setTextContent(node.classname);
		module.appendChild(classname);
		// Configuration file tag.
		if (node.configFile != null && node.configFile.length() > 0) {
			// Retrieve the configuration file name.
			final Element config = document.createElement(KHBM.ModuleConfigFile.tag);
			final int index = node.configFile.lastIndexOf(File.separator)+1;
			final String configFileName = node.configFile.substring(index);
			// Build configuration file path after deployment.
			// HOME/apps/APPLICATION/MODULE/CONFIG.FILE
			final StringBuilder builder = new StringBuilder();
			builder.append(UEnvironment.instance.getInstalledAppsDir());
			builder.append(bundle.applicationName).append(File.separator);
			builder.append(node.classname).append(File.separator);
			builder.append(configFileName);
			config.setTextContent(builder.toString());
			module.appendChild(config);
		}
		return module;
	}
}
