package net.c2001.dm.ar.garmf;

import java.io.Serializable;

/**
 * An association rule.
 * @author Lin Dong
 */

public class Rule implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Precedent of this association rule.
	 */
	public ItemSet src;
	/**
	 * Consequence of this association rule.
	 */
	public ItemSet dst;
	/**
	 * Confidence of this association rule.
	 */
	public double conf;
	
	/**
	 * Support of this association rule.
	 */
	public double sup;
	
	/**
     * Create a {@link Rule} instance.
     */
    public Rule()
    {
    }
    
    /**
     * Create a {@link Rule} instance and set the content.
     * @param src Precedent of this association rule.
     * @param dst Consequence of this association rule.
     * @param conf Confidence of this association rule.
     * @param sup Support of this association rule.
    */
    public Rule(ItemSet src, ItemSet dst, double conf, double sup)
    {
    	this.src = src;
    	this.dst = dst;
    	this.conf = conf;
    	this.sup = sup;
    }
}