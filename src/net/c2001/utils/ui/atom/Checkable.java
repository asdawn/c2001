package net.c2001.utils.ui.atom;

/**
 * For initialization and availability checking of UI components.
 * @author Lin Dong
 *
 */
public interface Checkable
{
	/**
	 * Check whether the initialization of components is successful.
	 * @return {@code true} on success, {@code false} on failure.
	 */
	abstract public boolean checkInit();

	/**
	 * Check whether the components are available (initialized and 
	 * assigned properly).
	 * 
	 * @return {@code true} on success, {@code false} on failure.
	 */
	abstract public boolean checkAll();

}
