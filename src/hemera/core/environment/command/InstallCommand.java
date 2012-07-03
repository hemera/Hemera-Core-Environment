package hemera.core.environment.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import hemera.core.environment.enumn.EEnvironment;
import hemera.core.environment.interfaces.ICommand;
import hemera.core.environment.util.JSVCScriptGenerator;
import hemera.core.environment.util.UEnvironment;
import hemera.core.environment.util.config.Configuration;
import hemera.core.utility.FileUtils;

/**
 * <code>InstallCommand</code> defines the logic that
 * installs the environment. It requires the following
 * arguments:
 * <p>
 * @param homeDir the <code>String</code> directory
 * to install the environment to.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class InstallCommand implements ICommand {
	

	@Override
	public void execute(final String[] args) throws Exception {
		if (args == null || args.length < 1) {
			throw new IllegalArgumentException("Hemera home directory must be specified.");
		}
		final String homeDir = FileUtils.instance.getValidDir(args[0]);
		try {
			// Remove existing home directory.
			System.out.println("Removing existing home directory...");
			FileUtils.instance.delete(homeDir);
			// Create directories.
			final String binDir = this.createDirectories(homeDir);
			// Create the configuration file.
			System.out.println("Creating runtime environment configuration...");
			this.createConfigFile(homeDir);
			// Install internal libraries.
			System.out.println("Installing internal libraries...");
			this.installLibraries(binDir);
			// Update binary files.
			System.out.println("Installing binaries...");
			this.updateBinaries(binDir);
			// Install JSVC scripts.
			System.out.println("Generating scripts...");
			JSVCScriptGenerator.instance.exportScripts(homeDir);
			// Export environment path.
			System.out.println("Exporting environment path...");
			this.exportEnvironment(binDir);
			// Print out further instructions.
			System.out.println("Hemera has been installed successfully.");
			System.out.println("Please run 'source /etc/profile' or restart your shell session to complete installation.");
		} catch (final Exception e) {
			System.err.println("Installation failed.");
			throw e;
		}
	}
	
	/**
	 * Create all the necessary directories.
	 * @param homeDir The specified <code>String</code>
	 * home directory.
	 * @return The <code>String</code> binary directory.
	 * @throws IOException If file processing failed.
	 */
	private String createDirectories(final String homeDir) throws IOException {
		// Create bin directory.
		final String binDir = UEnvironment.instance.getBinDir(homeDir);
		final File bin = new File(binDir);
		bin.mkdirs();
		// Create log directory.
		final String logDir = UEnvironment.instance.getLogDir(homeDir);
		final File log = new File(logDir);
		log.mkdirs();
		// Create apps directory.
		final String appsDir = UEnvironment.instance.getAppsDir(homeDir);
		final File apps = new File(appsDir);
		apps.mkdirs();
		// Create config directory.
		final String configDir = UEnvironment.instance.getConfigDir(homeDir);
		final File config = new File(configDir);
		config.mkdirs();
		return binDir;
	}

	/**
	 * Create the environment configuration file.
	 * @param homeDir The specified <code>String</code>
	 * home directory.
	 * @return The environment <code>File</code>.
	 * @throws IOException If any file processing
	 * failed.
	 * @throws ParserConfigurationException If XML
	 * processing failed.
	 * @throws TransformerException If writing XML
	 * file failed.
	 */
	private File createConfigFile(final String homeDir) throws IOException, ParserConfigurationException, TransformerException {
		// Generate the document based on default configuration.
		final Configuration defaultConfig = new Configuration(homeDir);
		final Document document = defaultConfig.toDocument();
		// Write to file.
		final String target = UEnvironment.instance.getConfigurationFile(homeDir);
		return FileUtils.instance.writeDocument(document, target);
	}

	/**
	 * Install the internal library files.
	 * @param binDir The <code>String</code> home
	 * bin directory path.
	 * @throws IOException If file processing failed.
	 */
	private void installLibraries(final String binDir) throws IOException {
		// Retrieve current executing Jar file.
		final File jarFile = FileUtils.instance.getCurrentJarFile();
		final JarFile jar = new JarFile(jarFile);
		// Retrieve all the entries in the resources directory and all of
		// its sub-directories.
		// Explicitly use slash here since entry path is platform independent.
		final String resourcesPath = "hemera/core/environment/resources/";
		JarInputStream input = null;
		try {
			input = new JarInputStream(new FileInputStream(jarFile));
			JarEntry entry = input.getNextJarEntry();
			while (entry != null) {
				entry = input.getNextJarEntry();
				if (entry != null) {
					final String entryName = entry.getName();
					// Entry's name must start with the resource path but does not end with a path separator.
					// Explicitly use slash here since entry path is platform independent.
					if (entryName.startsWith(resourcesPath) && !entryName.endsWith("/")) {
						// Write the entry to lib directory.
						FileUtils.instance.writeToFile(jar, entryName, binDir);
					}
				}
			}
		} finally {
			if (input != null) input.close();
		}
	}
	
	/**
	 * Move the binary files to the bin directory and
	 * update necessary files with correct path.
	 * @param binDir The <code>String</code> home
	 * bin directory path.
	 * @throws IOException If file processing failed.
	 * @throws InterruptedException If waiting for
	 * chmod command is interrupted.
	 */
	private void updateBinaries(final String binDir) throws IOException, InterruptedException {
		// Move current Jar to bin directory.
		final File jar = FileUtils.instance.getCurrentJarFile();
		final String newJarPath = binDir + jar.getName();
		jar.renameTo(new File(newJarPath));
		// Move script to bin directory.
		final String currentDir = FileUtils.instance.getCurrentJarDirectory();
		final File script = new File(currentDir + EEnvironment.ScriptFile.value);
		final String newScriptPath = binDir + script.getName();
		script.renameTo(new File(newScriptPath));
		// Update script with bin directory.
		final String scriptContents = FileUtils.instance.readAsString(new File(newScriptPath));
		final String updatedScript = scriptContents.replace(jar.getName(), newJarPath);
		FileUtils.instance.writeAsString(updatedScript, newScriptPath);
		// Make script executable.
		final Process scriptChmod = Runtime.getRuntime().exec("chmod +x " + newScriptPath);
		int result = scriptChmod.waitFor();
		if (result != 0) throw new IOException("Making hemera script executable failed.");
		// Make JSVC executable.
		String jsvcFile = null;
		if (UEnvironment.instance.isOSX()) {
			jsvcFile = binDir + EEnvironment.JSVCOSX.value;
		} else if (UEnvironment.instance.isLinux()) {
			jsvcFile = binDir + EEnvironment.JSVCLinux.value;
		}
		final Process jsvcChmod = Runtime.getRuntime().exec("chmod +x " + jsvcFile);
		result = jsvcChmod.waitFor();
		if (result != 0) throw new IOException("Making JSVC script executable failed.");
	}

	/**
	 * Export the Hemera home directory path to the
	 * environment path.
	 * @param binDir The <code>String</code> home
	 * bin directory path.
	 * @throws IOException If any file processing failed.
	 * @throws InterruptedException If waiting for shell
	 * command failed.
	 */
	private void exportEnvironment(final String binDir) throws IOException, InterruptedException {
		// Read in entire file as a string.
		final File file = new File("/etc/profile");
		final String originalContents = FileUtils.instance.readAsString(file);
		// Remove any existing Hemera exports.
		final String[] tokens = originalContents.split("\n");
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].contains(EEnvironment.ShellAccessPath.value.toLowerCase()) ||
					tokens[i].contains(EEnvironment.ShellAccessPath.value.toUpperCase())) {
				tokens[i] = null;
			}
		}
		// Create new contents.
		final StringBuilder updatedContents = new StringBuilder();
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i] != null) updatedContents.append(tokens[i]).append("\n");
		}
		// Append new environment path exports.
		updatedContents.append("export ").append(EEnvironment.ShellAccessPath.value).append("=\"").append(binDir).append("\"\n");
		updatedContents.append("export PATH=$").append(EEnvironment.ShellAccessPath.value).append(":$PATH\n");
		// Write the new contents to a temporary file.
		final String currentDir = FileUtils.instance.getCurrentJarDirectory();
		final String tempFile = currentDir + "temp.env";
		final File temp = FileUtils.instance.writeAsString(updatedContents.toString(), tempFile);
		// Execute sudo command to update environment profile.
		final StringBuilder command = new StringBuilder();
		command.append("sudo mv ").append(temp.getAbsolutePath()).append(" ").append(file.getAbsolutePath());
		final Process process = Runtime.getRuntime().exec(command.toString());
		final int result = process.waitFor();
		if (result != 0) throw new IOException("Overwriting existing environment file failed.");
	}
}
