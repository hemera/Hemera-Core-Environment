package hemera.core.environment.hbm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import hemera.core.environment.AbstractTag;
import hemera.core.environment.enumn.EDependencyType;
import hemera.core.environment.hbm.key.KHBMDependency;
import hemera.core.utility.Compiler;
import hemera.core.utility.FileUtils;

/**
 * <code>HBMDependency</code> defines the data structure
 * of an application or module dependency.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class HBMDependency extends AbstractTag {
	/**
	 * The <code>EDependencyType</code> type.
	 */
	public final EDependencyType type;
	/**
	 * The <code>String</code> value.
	 */
	public final String value;
	
	/**
	 * Constructor of <code>HBMDependency</code>.
	 * @param node The dependency <code>Element</code>.
	 */
	HBMDependency(final Element node) {
		super(node, KHBMDependency.Root.tag);
		final String typeStr = this.parseTagValue(node, KHBMDependency.Type.tag, false);
		this.type = EDependencyType.parse(typeStr);
		this.value = this.parseTagValue(node, KHBMDependency.Value.tag, false);
	}
	
	/**
	 * Process this dependency and compile the source
	 * files and retrieve Jar files to form a list of
	 * dependency Jar files.
	 * @param tempDir The <code>String</code> path of
	 * the temporary directory.
	 * @return The <code>List</code> of all the Jar
	 * dependency <code>File</code>.
	 * @throws Exception If compiling sources failed.
	 */
	public List<File> process(final String tempDir) throws Exception {
		if (this.type == EDependencyType.JarDirectory) {
			return FileUtils.instance.getFiles(this.value, ".jar");
		} else {
			// Compile to a temporary build directory.
			final String dependencyName = this.value.replace(File.separator, ".");
			final String buildDir = FileUtils.instance.getValidDir(tempDir + dependencyName);
			final Compiler compiler = new Compiler();
			compiler.compile(this.value, buildDir, null);
			// Package all class files into a Jar file.
			final List<File> classFiles = FileUtils.instance.getFiles(buildDir, ".class");
			final String jarTarget = buildDir + dependencyName + ".jar";
			final List<File> jarFiles = new ArrayList<File>(1);
			final File jarFile = FileUtils.instance.jarFiles(classFiles, jarTarget);
			jarFiles.add(jarFile);
			return jarFiles;
		}
	}
}
