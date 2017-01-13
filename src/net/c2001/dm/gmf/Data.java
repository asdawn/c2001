package net.c2001.dm.gmf;

/**
 * Data in General Mining Framework.
 * @author Lin Dong
 * {@code P} is the type of mining result (patterns).
 */
abstract public class Data<P extends Pattern> extends net.c2001.base.Object{
	private static final long serialVersionUID = 3505013065521564856L;
	protected Algorithm<P> miner;
	
	/**
	 * Mine data using {@code algorithm}. 
	 * @return a {@link Pattern} object on success, {@code null} otherwise .
	 */
	abstract public P mine();
}
