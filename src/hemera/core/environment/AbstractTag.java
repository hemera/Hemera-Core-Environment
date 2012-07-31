package hemera.core.environment;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>AbstractTag</code> defines the abstraction
 * of a tag that can parse values.
 *
 * @author Yi Wang (Neakor)
 * @version 1.0.0
 */
public class AbstractTag {

	/**
	 * Constructor of <code>AbstractTag</code>.
	 * @param node The <code>Element</code> node.
	 * @param expectedName The <code>String</code>
	 * expected name of this tag.
	 */
	protected AbstractTag(final Element node, final String expectedName) {
		// Verify tag name.
		final String tagname = node.getTagName();
		if (!tagname.equalsIgnoreCase(expectedName)) {
			throw new IllegalArgumentException("Invalid tag: " + tagname);
		}
	}

	/**
	 * Parse a tag string value with given tag and
	 * optional flag.
	 * @param node The <code>Element</code> XML node.
	 * @param tag The <code>String</code> tag to parse.
	 * @param optional <code>true</code> if the value
	 * can be <code>null</code>. <code>false</code>
	 * otherwise.
	 * @return The <code>String</code> tag value. Or
	 * <code>null</code> if there is no such tag and
	 * it is optional.
	 */
	protected String parseTagValue(final Element node, final String tag, final boolean optional) {
		final NodeList list = node.getElementsByTagName(tag);
		if (list == null || list.getLength() != 1) {
			if (optional) return null;
			else throw new IllegalArgumentException("Invalid tag. Must contain one " + tag + ".");
		}
		return list.item(0).getTextContent();
	}
}
