package hemera.core.environment.command.bundle.hbm;

import java.util.List;

/**
 * <code>HBundle</code> defines the data structure of
 * a Hemera Bundle of an application.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class HBundle {
	/**
	 * The <code>String</code> application name.
	 */
	public final String applicationName;
	/**
	 * The <code>List</code> of <code>HBMModule</code>
	 * the bundle contains.
	 */
	public final List<HBMModule> modules;
	
	/**
	 * Constructor of <code>HBundle</code>.
	 * @param name The <code>String</code> application
	 * name.
	 * @param modules The <code>List</code> of all
	 * <code>HBMModule</code> the bundle contains.
	 */
	public HBundle(final String name, final List<HBMModule> modules) {
		this.applicationName = name;
		this.modules = modules;
	}
}
