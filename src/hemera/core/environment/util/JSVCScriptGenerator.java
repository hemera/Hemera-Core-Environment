package hemera.core.environment.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import hemera.core.environment.enumn.EEnvironment;
import hemera.core.environment.util.config.Configuration;
import hemera.core.utility.FileUtils;

import org.xml.sax.SAXException;

/**
 * <code>JSVCScriptGenerator</code> defines the singleton
 * implementation that provides the functionality to
 * generate shell scripts that execute the JSVC Apache
 * daemon process.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public enum JSVCScriptGenerator {
	/**
	 * The singleton instance.
	 */
	instance;

	/**
	 * Export both start and stop scripts based on the
	 * current environment setup to the runtime binary
	 * directory derived from the specified home.
	 * @param homeDir The <code>String</code> runtime
	 * home directory.
	 * @throws IOException If file processing failed.
	 * @throws ParserConfigurationException If parsing
	 * configuration failed.
	 * @throws SAXException If parsing configuration
	 * failed.
	 * @throws InterruptedException If the command was
	 * interrupted.
	 */
	public void exportScripts(final String homeDir) throws IOException, SAXException, ParserConfigurationException, InterruptedException {
		final String binDir = UEnvironment.instance.getBinDir(homeDir);
		// Start script.
		final String startScriptContents = JSVCScriptGenerator.instance.generateStartScript(homeDir);
		final String startTarget = binDir + EEnvironment.JSVCStartScriptFile.value;
		FileUtils.instance.writeAsString(startScriptContents, startTarget);
		this.makeExecutable(startTarget);
		// Stop script.
		final String stopScriptContents = JSVCScriptGenerator.instance.generateStopScript(homeDir);
		final String stopTarget = binDir + EEnvironment.JSVCStopScriptFile.value;
		FileUtils.instance.writeAsString(stopScriptContents, stopTarget);
		this.makeExecutable(stopTarget);
	}

	/**
	 * Generate the JSVC stop script. This script will
	 * scan all internal libraries as well as all the
	 * deployed applications and their libraries.
	 * @param homeDir The <code>String</code> runtime
	 * home directory.
	 * @return The <code>String</code> script.
	 * @throws SAXException If loading configuration
	 * from file failed.
	 * @throws IOException If loading configuration
	 * from file failed.
	 * @throws ParserConfigurationException If loading
	 * configuration from file failed.
	 */
	private String generateStopScript(final String homeDir) throws SAXException, IOException, ParserConfigurationException {
		final String header = this.buildHeader(homeDir);
		final String classpath = this.buildClasspath(homeDir);
		final String configPath = UEnvironment.instance.getConfigurationFile(homeDir);
		// Build script.
		final StringBuilder builder = new StringBuilder();
		builder.append(header).append(" -stop -wait 20 -cp ").append(classpath).append(" ");
		builder.append("hemera.ext.apache.ApacheRuntimeLauncher ").append(configPath);
		return builder.toString();
	}

	/**
	 * Generate the JSVC start script. This script will
	 * scan all internal libraries as well as all the
	 * deployed applications and their libraries.
	 * @param homeDir The <code>String</code> runtime
	 * home directory.
	 * @return The <code>String</code> script.
	 * @throws SAXException If loading configuration
	 * from file failed.
	 * @throws IOException If loading configuration
	 * from file failed.
	 * @throws ParserConfigurationException If loading
	 * configuration from file failed.
	 */
	private String generateStartScript(final String homeDir) throws SAXException, IOException, ParserConfigurationException {
		final String header = this.buildHeader(homeDir);
		final String classpath = this.buildClasspath(homeDir);
		final String configPath = UEnvironment.instance.getConfigurationFile(homeDir);
		// Build script.
		final StringBuilder builder = new StringBuilder();
		builder.append(header).append(" -wait 20 -cp ").append(classpath).append(" ");
		builder.append("hemera.ext.apache.ApacheRuntimeLauncher ").append(configPath);
		return builder.toString();
	}

	/**
	 * Build the header section of the script.
	 * @param homeDir The <code>String</code> runtime
	 * home directory.
	 * @return The <code>String</code> header section
	 * of the script.
	 * @throws SAXException If loading configuration
	 * from file failed.
	 * @throws IOException If loading configuration
	 * from file failed.
	 * @throws ParserConfigurationException If loading
	 * configuration from file failed.
	 */
	private String buildHeader(final String homeDir) throws SAXException, IOException, ParserConfigurationException {
		final String binDir = UEnvironment.instance.getBinDir(homeDir);
		final Configuration config = UEnvironment.instance.getConfiguration(homeDir);
		final StringBuilder builder = new StringBuilder();
		builder.append("#!/bin/sh\n\n");
		// Export Java home based on operating system.
		if (UEnvironment.instance.isOSX()) {
			builder.append("export JAVA_HOME=$(/usr/libexec/java_home)");
		} else if (UEnvironment.instance.isLinux()) {
			builder.append("export JAVA_HOME=/usr/lib/jvm/default-java");
		}
		builder.append("sudo ").append(binDir).append("jsvc -jvm server ");
		builder.append("-Xms").append(config.jvm.memoryMin).append(" -Xmx").append(config.jvm.memoryMax).append(" ");
		builder.append("-Dfile.encoding=").append(config.jvm.fileEncoding);
		return builder.toString();
	}
	
	/**
	 * Build the class path section of the script by
	 * scanning the binary directory, the applications
	 * modules directories and all the applications
	 * library directories.
	 * @return The <code>String</code> class path
	 * section.
	 */
	private String buildClasspath(final String homeDir) {
		final StringBuilder builder = new StringBuilder();
		// Scan internal library directory.
		final String libDir = UEnvironment.instance.getBinDir(homeDir);
		final List<File> libList = FileUtils.instance.getFiles(libDir, ".jar");
		this.appendFiles(builder, libList);
		// Scan applications modules directories and library directories.
		final String appsDir = UEnvironment.instance.getAppsDir(homeDir);
		final List<File> appsList = FileUtils.instance.getFiles(appsDir, ".jar");
		this.appendFiles(builder, appsList);
		// Remove the last path separator.
		builder.deleteCharAt(builder.length()-1);
		return builder.toString();
	}
	
	/**
	 * Append all the files in the given list to the
	 * given string builder with class path format.
	 * @param builder The <code>StringBuilder</code>
	 * to append to.
	 * @param files The <code>List</code> of all the
	 * <code>File</code> to append.
	 */
	private void appendFiles(final StringBuilder builder, final List<File> files) {
		final int size = files.size();
		for (int i = 0; i < size; i++) {
			final File file = files.get(i);
			builder.append(file.getAbsolutePath());
			builder.append(File.pathSeparator);
		}
	}
	
	/**
	 * Make the given target executable.
	 * @param target The <code>String</code> target
	 * path.
	 * @throws IOException If changing mode failed.
	 * @throws InterruptedException If the command
	 * is interrupted.
	 */
	private void makeExecutable(final String target) throws IOException, InterruptedException {
		final Process chmod = Runtime.getRuntime().exec("chmod +x " + target);
		final int result = chmod.waitFor();
		if (result != 0) throw new IOException("Making hemera script executable failed.");
	}
}