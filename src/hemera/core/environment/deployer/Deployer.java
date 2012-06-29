package hemera.core.environment.deployer;

import hemera.core.environment.deployer.jsvc.JSVCScriptGenerator;
import hemera.core.environment.enumn.DEnvironment;
import hemera.core.environment.enumn.KBundleManifest;
import hemera.core.environment.util.UEnvironment;
import hemera.utility.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * <code>Deployer</code> defines the executable utility
 * unit that deploys a Hemera bundle file as a server
 * application that is hosted by the Hemera Runtime
 * environment.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class Deployer {
	/**
	 * The <code>String</code> path to the bundle file.
	 */
	private final String bundlePath;
	/**
	 * The <code>String</code> home directory path.
	 */
	private final String homeDir;

	/**
	 * Constructor of <code>Deployer</code>.
	 * @param bundlePath The <code>String</code> path to
	 * the bundle file.
	 * @throws ParserConfigurationException If the
	 * environment file is corrupted.
	 * @throws IOException If environment file cannot
	 * be located.
	 * @throws SAXException If environment file is
	 * corrupted.
	 */
	public Deployer(final String bundlePath) throws ParserConfigurationException, SAXException, IOException {
		if (!bundlePath.endsWith(DEnvironment.BundleExtension.value)) throw new IllegalArgumentException("Invalid bundle file.");
		this.bundlePath = bundlePath;
		this.homeDir = UEnvironment.instance.getInstalledHomeDir();
	}

	/**
	 * Start the deploying process.
	 * @throws Exception If any processing failed.
	 */
	public void start() throws Exception {
		try {
			// Deploy HAM file.
			final JarFile bundle = new JarFile(this.bundlePath);
			System.out.println("Deploying HAM...");
			this.deployHAM(bundle);
			// Deploy bundle library files.
			System.out.println("Deploying libraries...");
			this.deployLibrary(bundle);
			// Deploy modules.
			System.out.println("Deploying modules...");
			this.deployModules(bundle);
			// Deploy JSVC scripts.
			System.out.println("Depliying scripts...");
			this.deployJSVCScripts();
			// Create logging directory.
			final File logDir = new File(UEnvironment.instance.getLogDir(this.homeDir));
			logDir.mkdirs();
			// Delete temp directory.
			FileUtils.instance.delete(UEnvironment.instance.getInstalledTempDir());
			System.out.println("Completed: " + this.homeDir);
		} catch (final Exception e) {
			System.err.println("Deploying failed.");
			throw e;
		}
	}

	/**
	 * Deploy the HAM configuration file contained
	 * in the given bundle file.
	 * @param bundle The bundle <code>JarFile</code>.
	 * @throws IOException If parsing bundle file
	 * failed.
	 */
	private void deployHAM(final JarFile bundle) throws IOException {
		// Retrieve HAM entry.
		final Manifest manifest = bundle.getManifest();
		final String entryName = manifest.getMainAttributes().getValue(KBundleManifest.HAMFile.key);
		// Write entry to file in configuration directory.
		final String configDir = UEnvironment.instance.getConfigDir(this.bundlePath);
		final File hamFile = FileUtils.instance.writeToFile(bundle, entryName, configDir);
		// Update HAM file with home directory.
		final String logDir = UEnvironment.instance.getLogDir(this.homeDir);
		final String modulesDir = null; // TODO UEnvironment.instance.getModulesDir(this.homeDir);
		final String contents = FileUtils.instance.readAsString(hamFile);
//		final String updatedHome = contents.replace(DHAM.PlaceholderHomeDirectory.value, this.homeDir);
//		final String updatedLog = updatedHome.replace(DHAM.PlaceholderLoggingDirectory.value, logDir);
		final String updated = null; //updatedLog.replace(DHAM.PlaceholderModulesDirectory.value, modulesDir);
		// TODO;
		FileUtils.instance.writeAsString(updated, hamFile.getAbsolutePath());
	}

	/**
	 * Deploy all the library files contained in the
	 * given bundle file excluding the ones that are
	 * already installed with the environment.
	 * @param bundle The bundle <code>JarFile</code>.
	 * @throws IOException If parsing bundle file
	 * failed.
	 */
	private void deployLibrary(final JarFile bundle) throws IOException {
		// Retrieve the lib Jar entry.
		final Manifest manifest = bundle.getManifest();
		final String libEntryName = manifest.getMainAttributes().getValue(KBundleManifest.LibraryJarFile.key);
		// Bundle may not contain any library files.
		if (libEntryName == null || libEntryName.isEmpty()) return;
		// Write the lib Jar file to temp directory.
		final String tempDir = UEnvironment.instance.getInstalledTempDir();
		final File libFile = FileUtils.instance.writeToFile(bundle, libEntryName, tempDir);
		// Write all the contents of the lib Jar file to lib directory, excluding already installed.
		final String libDir = null; // TODO UEnvironment.instance.getLibDir(this.homeDir);
		final List<File> existing = FileUtils.instance.getFiles(libDir);
		FileUtils.instance.writeAll(libFile, libDir, existing);
		// Delete temporary lib Jar file.
		libFile.delete();
	}

	/**
	 * Deploy all the modules contained in the given
	 * bundle file.
	 * @param bundle The bundle <code>JarFile</code>.
	 * @throws IOException If parsing bundle file
	 * failed.
	 */
	private void deployModules(final JarFile bundle) throws IOException {
		final String tempDir = UEnvironment.instance.getInstalledTempDir();
		final String modulesDir = null; // TODO UEnvironment.instance.getModulesDir(this.homeDir);
		// Parse module Jar file names.
		final Manifest manifest = bundle.getManifest();
		final String moduleNamesStr = manifest.getMainAttributes().getValue(KBundleManifest.Modules.key);
		final String[] moduleEntries = moduleNamesStr.split(KBundleManifest.ModuleSeparator.key);
		// Deploy all modules.
		for (int i = 0; i < moduleEntries.length; i++) {
			// Write module Jar file to the temporary directory.
			final String moduleEntry = moduleEntries[i];
			final int extIndex = moduleEntry.lastIndexOf(".jar");
			final String moduleName = moduleEntry.substring(0, extIndex);
			final File moduleFile = FileUtils.instance.writeToFile(bundle, moduleEntry, tempDir);
			// Write all the contents of the module Jar file to the module directory.
			final String moduleDir = modulesDir + moduleName + File.separator;
			FileUtils.instance.writeAll(moduleFile, moduleDir);
			// Delete the temporary module Jar file.
			moduleFile.delete();
		}
	}
	
	/**
	 * Deploy the JSVC scripts that includes all the
	 * library files on the class path.
	 * @throws IOException If file processing failed.
	 */
	private void deployJSVCScripts() throws IOException {
		final String binDir = UEnvironment.instance.getBinDir(this.homeDir);
		// Start script.
		final String startScriptContents = JSVCScriptGenerator.instance.generateStartScript();
		final String startTarget = binDir + DEnvironment.JSVCStartScriptFile.value;
		FileUtils.instance.writeAsString(startScriptContents, startTarget);
		// Stop script.
		final String stopScriptContents = JSVCScriptGenerator.instance.generateStopScript();
		final String stopTarget = binDir + DEnvironment.JSVCStopScriptFile.value;
		FileUtils.instance.writeAsString(stopScriptContents, stopTarget);
	}
}
