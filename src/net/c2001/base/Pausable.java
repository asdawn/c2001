package net.c2001.base;

/**
 * Any class implements this interface should support pause and restore.
 * 
 * @author Lin Dong
 *
 */
public interface Pausable {
	
	/**
	 * Pause the works doing by current object. 
	 * @return {@code true} on success, {@code false} otherwise.
	 */
	abstract public boolean pause();
	
	/**
	 * Restore after a pause.
	 * @return {@code true} on success, {@code false} otherwise.
	 */
	abstract public boolean restore();
	

}
