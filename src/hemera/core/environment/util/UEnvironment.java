package hemera.core.environment.util;

import hemera.core.environment.enumn.DEnvironment;
import hemera.utility.FileUtils;

import java.io.File;

/**
 * <code>UEnvironment</code> defines the singleton
 * implementation that provides Hemera environment
 * related utilities.
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
	 * Retrieve the environment file path according
	 * to the specified home directory.
	 * @param homeDir The <code>String</code> home
	 * directory.
	 * @return The <code>String</code> environment
	 * file path.
	 */
	public String getEnvironmentFile(final String homeDir) {
		final String configDir = this.getConfigDir(homeDir);
		return configDir + DEnvironment.EnvironmentFile.value;
	}
}
