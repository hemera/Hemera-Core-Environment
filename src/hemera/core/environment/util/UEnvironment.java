package hemera.core.environment.util;

import hemera.core.environment.enumn.EEnvironment;
import hemera.core.environment.util.config.Configuration;
import hemera.utility.FileUtils;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * <code>UEnvironment</code> defines the singleton that
 * provides Hemera environment file and directory access
 * utilities.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum UEnvironment {
	/**
	 * The singleton instance.
	 */
	instance;
	
	/**
	 * Retrieve the installed binary directory.
	 * @return The <code>String</code> binary
	 * directory.
	 */
	public String getInstalledBinDir() {
		final String homeDir = this.getInstalledHomeDir();
		return this.getBinDir(homeDir);
	}
	
	/**
	 * Retrieve the installed applications directory.
	 * @return The <code>String</code> applications
	 * directory.
	 */
	public String getInstalledAppsDir() {
		final String homeDir = this.getInstalledHomeDir();
		return this.getAppsDir(homeDir);
	}
	
	/**
	 * Retrieve the installed temporary directory.
	 * @return The <code>String</code> temporary
	 * directory.
	 */
	public String getInstalledTempDir() {
		return this.getInstalledHomeDir() + "temp" + File.separator;
	}

	/**
	 * Retrieve the installed home directory.
	 * @return The <code>String</code> home directory.
	 */
	public String getInstalledHomeDir() {
		final String binDir = FileUtils.instance.getCurrentJarDirectory();
		final int index = binDir.indexOf("/bin")+1;
		return binDir.substring(0, index);
	}
	
	/**
	 * Retrieve the binary directory under the home
	 * directory.
	 * @param homeDir The <code>String</code> home
	 * directory.
	 * @return The <code>String</code> binary directory.
	 */
	public String getBinDir(final String homeDir) {
		return FileUtils.instance.getValidDir(homeDir) + "bin" + File.separator;
	}

	/**
	 * Retrieve the logging directory under the home
	 * directory.
	 * @param homeDir The <code>String</code> home
	 * directory.
	 * @return The <code>String</code> logging directory.
	 */
	public String getLogDir(final String homeDir) {
		return FileUtils.instance.getValidDir(homeDir) + "log" + File.separator;
	}

	/**
	 * Retrieve the apps directory under the home
	 * directory.
	 * @param homeDir The <code>String</code> home
	 * directory.
	 * @return The <code>String</code> apps directory.
	 */
	public String getAppsDir(final String homeDir) {
		return FileUtils.instance.getValidDir(homeDir) + "apps" + File.separator;
	}

	/**
	 * Retrieve the configuration directory under the
	 * home directory.
	 * @param homeDir The <code>String</code> home
	 * directory.
	 * @return The <code>String</code> configuration
	 * directory.
	 */
	public String getConfigDir(final String homeDir) {
		return FileUtils.instance.getValidDir(homeDir) + "config" + File.separator;
	}
	
	/**
	 * Retrieve the configuration of the environment
	 * located at the given path.
	 * @param homeDir The <code>String</code> home
	 * directory.
	 * @return The <code>Configuration</code> of the
	 * runtime environment.
	 * @throws IOException If reading file failed.
	 * @throws SAXException If parsing file failed.
	 * @throws ParserConfigurationException If
	 * parsing file failed.
	 */
	public Configuration getConfiguration(final String homeDir) throws IOException, SAXException, ParserConfigurationException {
		final String path = this.getConfigurationFile(homeDir);
		final Document document = FileUtils.instance.readAsDocument(new File(path));
		return new Configuration(document);
	}
	
	/**
	 * Retrieve the configuration file path according
	 * to the specified home directory.
	 * @param homeDir The <code>String</code> home
	 * directory.
	 * @return The <code>String</code> configuration
	 * file path.
	 */
	public String getConfigurationFile(final String homeDir) {
		final String configDir = this.getConfigDir(homeDir);
		return configDir + EEnvironment.ConfigurationFile.value;
	}

	/**
	 * Retrieve the library directory of a particular
	 * application with the given application directory.
	 * @param appDir The <code>String</code> path to
	 * the application directory.
	 * @return The <code>String</code> application's
	 * library directory path.
	 */
	public String getApplicationLibDir(final String appDir) {
		return FileUtils.instance.getValidDir(appDir) + "lib" + File.separator;
	}
}
