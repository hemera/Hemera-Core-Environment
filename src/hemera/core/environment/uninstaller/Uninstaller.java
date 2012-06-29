package hemera.core.environment.uninstaller;

import hemera.core.environment.enumn.DEnvironment;
import hemera.core.environment.util.UEnvironment;
import hemera.utility.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * <code>Uninstaller</code> defines the implementation
 * that provides the functionality to un-install the
 * current Hemera environment.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class Uninstaller {

	/**
	 * Start the un-installing process.
	 * @throws Exception If any processing failed.
	 */
	public void uninstall() throws Exception {
		// Remove Hemera from environment profile.
		this.removeEnvPath();
		// Remove existing home directory.
		final String homePath = UEnvironment.instance.getInstalledHomeDir();
		FileUtils.instance.delete(homePath);
		// Print further instructions.
		System.out.println("Hemera has been uninstalled successfully.");
		System.out.println("Please run 'source /etc/profile' or restart your shell session to complete un-installation.");
	}

	/**
	 * Remove Hemera from the environment path.
	 * @throws IOException If any file processing failed.
	 * @throws InterruptedException If waiting for shell
	 * command failed.
	 */
	private void removeEnvPath() throws IOException, InterruptedException {
		// Read in entire file as a string.
		final File file = new File("/etc/profile");
		final String originalContents = FileUtils.instance.readAsString(file);
		// Remove any existing Hemera exports.
		final String[] tokens = originalContents.split("\n");
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].contains(DEnvironment.ShellAccessPath.value)) {
				tokens[i] = null;
			}
		}
		// Create new contents.
		final StringBuilder updatedContents = new StringBuilder();
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i] != null) updatedContents.append(tokens[i]).append("\n");
		}
		// Write the new contents to a temporary file.
		final String currentDir = FileUtils.instance.getCurrentJarDirectory();
		final String tempFile = currentDir + "temp.env";
		final File temp = FileUtils.instance.writeAsString(updatedContents.toString(), tempFile);
		// Execute sudo command to update environment profile.
		final StringBuilder command = new StringBuilder();
		command.append("sudo mv ").append(temp.getAbsolutePath()).append(" ").append(file.getAbsolutePath());
		final Process process = Runtime.getRuntime().exec(command.toString());
		process.waitFor();
	}
}
