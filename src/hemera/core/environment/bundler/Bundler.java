package hemera.core.environment.bundler;

import hemera.core.environment.bundler.ham.HAMGenerator;
import hemera.core.environment.bundler.hbm.HBMModule;
import hemera.core.environment.bundler.hbm.HBMParser;
import hemera.core.environment.bundler.hbm.HBundle;
import hemera.core.environment.enumn.DEnvironment;
import hemera.core.environment.enumn.KBundleManifest;
import hemera.core.environment.util.UEnvironment;
import hemera.utility.FileUtils;
import hemera.utility.java.Compiler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.w3c.dom.Document;

/**
 * <code>Bundler</code> defines the utility unit that
 * creates a <code>hab</code>, Hemera Application Bundle
 * file, which can then be deployed in the Hemera runtime
 * environment. This process requires a <code>hbm</code>,
 * Hemera Bundle Model file that defines the application
 * bundle.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class Bundler {
	/**
	 * The <code>String</code> temporary working
	 * directory.
	 */
	private final String tempDir;
	
	/**
	 * Constructor of <code>Bundler</code>.
	 */
	public Bundler() {
		this.tempDir = UEnvironment.instance.getInstalledTempDir();
	}
	
	/**
	 * Start the bundling process.
	 * @param hbmPath The <code>String</code> path to
	 * the hbm file.
	 * @param targetDir The <code>String</code> bundle
	 * file target directory.
	 * @throws Exception If any processing failed.
	 */
	public void bundle(final String hbmPath, final String targetDir) throws Exception {
		try {
			// Create the temp directory.
			FileUtils.instance.delete(this.tempDir);
			final File tempDir = new File(this.tempDir);
			tempDir.mkdirs();
			// Parse bundle from HBM file.
			System.out.println("Parsing Hemera Bundle Model (HBM) file...");
			final HBundle bundle = new HBMParser().parse(hbmPath);
			// Generate HAM file.
			System.out.println("Generating Hemera Application Model (HAM) file...");
			final Document ham = new HAMGenerator().generate(bundle);
			final String hamTarget = this.tempDir + bundle.applicationName.toLowerCase() + ".ham";
			final File hamFile = FileUtils.instance.writeDocument(ham, hamTarget);
			// Build modules.
			System.out.println("Building modules...");
			final Compiler compiler = new Compiler();
			final int size = bundle.modules.size();
			final ArrayList<File> moduleJars = new ArrayList<File>(size);
			for (int i = 0; i < size; i++) {
				final HBMModule module = bundle.modules.get(i);
				final File moduleJar = this.buildModule(module, compiler);
				moduleJars.add(moduleJar);
			}
			// Package all the module library files into a single Jar file.
			System.out.println("Packaging module library files...");
			final File libJar = this.buildModuleLib(bundle.modules);
			// Create a manifest file for the bundle.
			final Manifest manifest = this.createManifest(moduleJars, libJar, hamFile);
			// Package all module Jar files, module library Jar file, and
			// the HAM file into a single bundle Jar file.
			System.out.println("Packaging final bundle...");
			final String bundleTarget = FileUtils.instance.getValidDir(targetDir) + bundle.applicationName + DEnvironment.BundleExtension.value;
			final ArrayList<File> files = new ArrayList<File>();
			files.addAll(moduleJars);
			files.add(libJar);
			files.add(hamFile);
			FileUtils.instance.jarFiles(files, bundleTarget, manifest);
			// Remove temp directory.
			FileUtils.instance.delete(this.tempDir);
			System.out.println("Bundling completed: " + bundleTarget);
		} catch (final Exception e) {
			System.err.println("Bundling failed.");
			throw e;
		}
	}
	
	/**
	 * Build the HBM module node by packaging all the
	 * compiled module class files and its configuration
	 * file if there is one into a single Jar file under
	 * the bundler temp directory.
	 * @param module The <code>HBMModule</code> to be
	 * built.
	 * @param compiler The <code>Compiler</code> instance
	 * to compile the module classes.
	 * @return The packaged module Jar <code>File</code>.
	 * @throws Exception If any processing failed.
	 */
	private File buildModule(final HBMModule module, final Compiler compiler) throws Exception {
		// Each module gets a separate build directory.
		final String buildDir = this.tempDir + module.classname + File.separator;
		// Compile classes.
		final String classDir = buildDir + "classes" + File.separator;
		final List<File> classfiles = compiler.compile(module.srcDir, module.libDir, classDir);
		// Package class files into a Jar file.
		final String classjarPath = buildDir + module.classname + ".jar";
		final File classjar = FileUtils.instance.jarFiles(classfiles, classjarPath);
		// Package class Jar file and module configuration file into a module Jar file.
		final String modulejarPath = this.tempDir + module.classname + ".jar";
		final ArrayList<File> modulefiles = new ArrayList<File>();
		modulefiles.add(classjar);
		if (module.configFile != null) {
			final File configFile = new File(module.configFile);
			if (!configFile.exists()) {
				throw new IllegalArgumentException("Module configuration file: " + module.configFile + " does not exist.");
			}
			modulefiles.add(configFile);
		}
		final File modulejar = FileUtils.instance.jarFiles(modulefiles, modulejarPath);
		// Remove the build directory.
		FileUtils.instance.delete(buildDir);
		return modulejar;
	}
	
	/**
	 * Package all the library files of all the modules
	 * in the given list into a single Jar file under
	 * the bundler temp directory.
	 * @param modules The <code>List</code> of all the
	 * <code>HBMModule</code> to check for library files.
	 * @return The library Jar <code>File</code>.
	 * @throws IOException If any file processing failed.
	 */
	private File buildModuleLib(final List<HBMModule> modules) throws IOException {
		// Extract all the module library files.
		final ArrayList<File> libFiles = new ArrayList<File>();
		final int size = modules.size();
		for (int i = 0; i < size; i++) {
			final HBMModule module = modules.get(i);
			final List<File> modulelibs = FileUtils.instance.getFiles(module.libDir);
			// Exclude duplicates.
			final int libsize = modulelibs.size();
			for (int j = 0; j < libsize; j++) {
				final File file = modulelibs.get(j);
				if (!libFiles.contains(file)) libFiles.add(file);
			}
		}
		// Package all files into a single Jar file.
		final String libjarPath = this.tempDir + "lib.jar";
		final File libjar = FileUtils.instance.jarFiles(libFiles, libjarPath);
		return libjar;
	}
	
	/**
	 * Create the bundle manifest file.
	 * @param modulejars The <code>List</code> of all
	 * module Jar <code>File</code> to be included in
	 * the final bundle file.
	 * @param libjar The <code>File</code> of the
	 * single Jar file containing all module library
	 * files to be included in the final bundle file.
	 * @param hamfile The HAM <code>File</code> to be
	 * included in the final bundle file.
	 * @return The <code>Manifest</code> instance.
	 */
	private Manifest createManifest(final List<File> modulejars, final File libjar, final File hamfile) {
		final Manifest manifest = new Manifest();
		// Must include the basic attributes.
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		manifest.getMainAttributes().put(Attributes.Name.SPECIFICATION_VENDOR, "Hemera");
		manifest.getMainAttributes().put(Attributes.Name.SPECIFICATION_VERSION, DEnvironment.Version.value);
		// Add modules list attribute.
		final StringBuilder modulesAttribute = new StringBuilder();
		final int size = modulejars.size();
		final int last = size - 1;
		for (int i = 0; i < size; i++) {
			final File modulejar = modulejars.get(i);
			modulesAttribute.append(modulejar.getName());
			if (i != last) modulesAttribute.append(KBundleManifest.ModuleSeparator.key);
		}
		manifest.getMainAttributes().putValue(KBundleManifest.Modules.key, modulesAttribute.toString());
		// Add library Jar file attribute.
		manifest.getMainAttributes().putValue(KBundleManifest.LibraryJarFile.key, libjar.getName());
		// Add HAM file attribute.
		manifest.getMainAttributes().putValue(KBundleManifest.HAMFile.key, hamfile.getName());
		return manifest;
	}
}
