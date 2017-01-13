package net.c2001.dm.ar.constraint;

import java.io.Serializable;

/**
 * Constraints on candidate and frequent itemsets.
 * @author Lin Dong
 *
 */
public class Constraints implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6781964158684066565L;
	/**
	 * {@link ItemSetFilter} for frequent itemsets.
	 */
	public ItemSetFilter frequentFilter = null;
	/**
	 * {@link ItemSetFilter} for candidate itemsets.
	 */
	public ItemSetFilter candidateFilter = null;
}
