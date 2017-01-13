package net.c2001.dm.ar.garmf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The names of items. {@link ItemSet} use numbers
 * to represent items, but the names of items is
 * necessary in rule mining. The {@link ItemNames} 
 * is developed for storing the names of items.
 * 
 * @author Lin Dong
 */
public class ItemNames implements Serializable
{
	static final long serialVersionUID = 1L;
	/**
	 * Names of items.
	 */
	private List<String> names = new ArrayList<String>(10);

	/**
	 * Create an {@link ItemNames} object.
	 */
	public ItemNames()
	{
	}

	/**
	 *  Create an {@link ItemNames} object with the given
	 *  name list.
	 * 
	 * @param names
	 *            names of items.
	 */
	public ItemNames(List<String> names)
	{
		this.setNames(names);
	}

	/**
	 * Set the names of items.
	 * 
	 * @param names
	 *            names of items.
	 */
	public void setNames(List<String> names)
	{
		int i;
		this.names.clear();
		for (i = 0; i < names.size(); i++)
			this.names.add(names.get(i).trim());
	}

	/**
	 * Returns the names of items.
	 * 
	 * @return names of items.
	 */
	public List<String> getNames()
	{
		return this.names;
	}

	/**
	 * Returns the count of item names.
	 * 
	 * @return the count of item names.
	 */
	public int size()
	{
		return names.size();
	}

	/**
	 * Returns the item names of the given itemset.
	 * 
	 * @param itemset
	 *            an {@link ItemSet} ojbect.
	 * @return the item names of the given itemset.<br>
	 *         Not: the numbers of items in{@link ItemSet}
	 *         start from 1, but the index in array
	 *         starts from 0. Remember it if you want to
	 *         modify the source code.
	 */
	public List<String> getName(ItemSet itemset)
	{
		ArrayList<String> names = new ArrayList<String>(10);
		short[] fn = itemset.getItems();
		int i = 0;
		for (i = 0; i < fn.length; i++)
		{
			names.add(this.names.get(fn[i] - 1));
		}
		return names;
	}
}