package net.c2001.dm.ar.garmf;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import net.c2001.utils.CommonOps;

/**
 * {@link ItemSet} is a set of items, here we use short integers (starting form
 * 1) to represent the items, and {@link HashSet} is selected as the container
 * of items. For example, if we mine rule from a table with boolean fields A, B,
 * C and D, the filed numbers should be 1, 2, 3 and 4, then we use these numbers
 * as items.
 * 
 * @author Lin Dong
 */
public class ItemSet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8011195065727110667L;

	/**
	 * The content of this itemset.
	 */
	private Set<Short> itemset = null;

	/**
	 * The support of this itemset. Support of an itemset equals to (the
	 * support count)/(total record count).
	 */
	private Double support = null;

	/**
	 * Create an empty {@link ItemSet}.
	 */
	public ItemSet() {
		this.itemset = new HashSet<Short>();
	}

	/**
	 * Returns the number of items in this itemset.
	 * 
	 * @return the number of items in this itemset.
	 */
	public int getItemCount() {
		return this.itemset.size();
	}

	/**
	 * Add an item to this itemset. Items are represented with the corresponding
	 * field numbers(start from 1).
	 * 
	 * @param item
	 *            item to add.
	 */
	public void addItem(short item) {
		this.itemset.add(item);
	}

	/**
	 * Remove an item from this itemset. Items are represented with the
	 * corresponding field numbers(starting from 1).
	 * 
	 * @param item
	 *            item to remove.
	 */
	public void removeItem(short item) {
		this.itemset.remove((Short) item);
	}

	/**
	 * Remove all items in the given itemset from this itemset.
	 * 
	 * @param itemset
	 *            the given itemset.
	 */
	public void removeItemSet(ItemSet itemset) {
		this.itemset.removeAll(itemset.itemset);
	}

	/**
	 * Create a clone of this itemset.
	 * 
	 * @return a clone of this itemset.<br>
	 *         Only items will be duplicated, the support count will not be
	 *         copied.
	 */
	public ItemSet clone() {
		ItemSet is = new ItemSet();
		is.itemset.addAll(this.itemset);
		return is;
	}

	/**
	 * Return the numbers of items in this itemset.
	 * 
	 * @return the numbers of items in this itemset.
	 */
	public short[] getItems() {
		int size = itemset.size();
		short[] items = new short[size];
		int i = 0;
		for (short s : itemset) {
			items[i] = s;
			i++;
		}
		Arrays.sort(items);
		return items;
	}

	/**
	 * Test if this itemset contains the given item.
	 * 
	 * @param itemNumber
	 *            item number (in database it is the field number).
	 * @return {@code true} for yes£¬{@code false} for no.
	 */
	public boolean contains(short itemNumber) {
		return this.itemset.contains(itemNumber);
	}

	/**
	 * Test if this itemset contains the given itemset.
	 * 
	 * @param itemset2
	 *            an itemset.
	 * @return {@code true} for yes£¬{@code false} for no.
	 */
	public boolean contains(ItemSet itemset2) {
		return this.itemset.containsAll(itemset2.itemset);
	}

	/**
	 * Test if the given itemset equals to this itemset. If item numbers in two
	 * itemsets are the same, they will be considered equal. For example,
	 * itemset {1,2,3} and {3,2,1} are equal, though the order of item numbers
	 * seems different.
	 * 
	 * @param itemset2
	 *            an itemset.
	 * @return {@code true} if the given itemset equals to this itemset,
	 *         {@code false} otherwise.
	 * 
	 */
	public boolean equals(ItemSet itemset2) {
		return this.itemset.equals(itemset2.itemset);
	}
	
	/**
	 * Test if the given itemset equals to this itemset. This method overrides
	 * the {@code equals()} method of the {@link Object} class.
	 * 
	 * @param o
	 *            the {@link Object} to compare.
	 * @return {@code true} if {@code o} is an instance of {@link ItemSet} and
	 *         they are equal; {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof ItemSet && this.equals((ItemSet) o))
			return true;
		else
			return false;
	}

	@Override
	/**
	 * Returns the string format of this itemset. The names
	 * of items will be returned, separated with comma.
	 * @return the string format of this itemset.
	 */
	public String toString() {
		short[] items = this.getItems();
		Arrays.sort(items);
		StringBuilder stringBuilder = new StringBuilder();
		for (short s : items) {
			stringBuilder.append(s);
			stringBuilder.append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		return stringBuilder.toString();
	}

	/**
	 * Resolve an itemset from the string format. The numbers of items are
	 * separated with comma in this string, without any other characters, for
	 * exaple string "1,2,3" will be resolved to itemset {1,2,3}. If the number
	 * in the string is not an legal short, {@code Short.parseShort()} will
	 * throw an {@link NumberFormatException}.
	 * 
	 * @param str
	 *            string format of an itemset, in which numbers of items are
	 *            separated with comma.
	 * @return an {@link ItemSet}.
	 */
	public static ItemSet parseItemSet(String str) {
		Pattern p = Pattern.compile(",");
		String[] strs = p.split(str);
		ItemSet iSet = new ItemSet();
		for (String string : strs) {
			iSet.addItem(Short.parseShort(string));
		}
		return iSet;
	}

	/**
	 * Set the support of this itemset. <br>
	 * Note: support of an itemset = (support count)/(total record count).
	 * 
	 * @param support
	 *            the support of this itemset.
	 */
	public void setSupport(Double support) {
		this.support = support;
	}

	/**
	 * Return the support count of this itemset. <br>
	 * Note: support of an itemset = (support count)/(total record count).
	 */
	public Double getSupport() {
		return support;
	}

	/**
	 * Returns all (k-1) subsets of a k-itemset.
	 * @return all (k-1) subsets of this k-itemset.
	 */
	public ItemSet[] getLargeSubsets() {
		int n = this.getItemCount();
		ItemSet[] subsets = new ItemSet[n];
		short[] fields = this.getItems();
		for(int i = 0; i < n; i++){
			subsets[i] = this.clone();
			subsets[i].removeItem(fields[i]);
		}
		return subsets;
	}

	/**
	 * Add all items in {@code iset} to this itemset.
	 * 
	 * @param iset
	 *            an itemset.
	 */
	public void addItems(ItemSet iset) {
		this.itemset.addAll(iset.itemset);
	}

	/**
	 * Add items to this itemset.
	 * 
	 * @param items
	 *            item numbers.
	 */
	public void addItems(short[] items) {
		for (short s : items) {
			this.addItem(s);
		}
	}

	/**
	 * Returns the MD5 of the string format of this itemset.
	 * 
	 * @return the MD5 of the string format of this itemset, return {@code null}
	 *         if this itemset is empty.
	 */
	public String getMD5() {
		if (this.getItemCount() == 0)
			return null;
		return CommonOps.getMD5(this.toString());
	}

	/**
	 * Compare the total value of two itemsets. The value of an item is relative
	 * to its number, smaller the number is, larger the value it has. The value
	 * of item number x is twice of item number (x+1). For example, total value
	 * of {1,2} is larger than {2,3,4}.
	 * 
	 * @param i1
	 *            one itemset .
	 * @param i2
	 *            another itemset.
	 * @return 0 if the itemsets are equal, 1 if {@code i1} is larger, -1
	 *         otherwise.
	 */
	public static int compare(ItemSet i1, ItemSet i2) {
		if (i1 == null || i1.getItemCount() == 0) {
			if (i2 == null || i2.getItemCount() == 0)
				return 0;
			else
				return -1;
		} else {
			if (i2 == null || i2.getItemCount() == 0)
				return 1;
			else {
				short[] items1 = i1.getItems();
				short[] items2 = i2.getItems();
				int count = Math.min(i1.getItemCount(), i2.getItemCount());
				for (int i = 0; i < count; i++) {
					// remember 1 is larger than 1/2+1/4+...1/x
					if (items1[i] < items2[i])
						return 1;
					else if (items1[i] > items2[i])
						return -1;
				}
				if (i1.getItemCount() == i2.getItemCount())
					return 0;
				else if (i1.getItemCount() > i2.getItemCount())
					return 1;
				else
					return -1;

			}
		}
	}
	
	@Override
	public int hashCode() {		
		return this.toString().hashCode();
	}
	
}
