package net.c2001.dm.ar.garmf;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of apriori_gen(). 
 * 
 * @author Lin Dong
 * 
 */
public class CandidateGenerator
{
	/**
	 * Create an {@link CandidateGenerator} object.
	 */
	public CandidateGenerator()
	{
	}

	/**
	 * 
	 * Generate candidate (i+1)-itemsets according
	 * to the given frequent i-itemsets. Make sure
	 * all itemsets given have the same size. 
	 * 
	 * @param in
	 *            frequent i-itemsets. If the itemsets
	 *            given have different sizes, an 
	 *            {@link InvalidParameterException} 
	 *            will be thrown. 
	 * @return candidate (i+1)-itemsets; {@code null} if the input
	 * is empty.
	 */
	public List<ItemSet> gen(List<ItemSet> in)
	{
		if (in == null || in.size() == 0)
		{
			return null;
		}
		else
		{
			for (ItemSet itemSet : in)
			{
				if (itemSet.getItemCount() != in.get(0).getItemCount())
					throw new InvalidParameterException
					("Size of itemsets are not equal:" + itemSet);
			}
			int i, j;
			ItemSet a, b;
			List<ItemSet> cur = new ArrayList<ItemSet>(10);
			int count = in.size();
			for (i = 0; i < count - 1; i++)
				for (j = i + 1; j < count; j++)
				{
					a = in.get(i);
					b = in.get(j);					
					ItemSet tmp = gen(a, b);
					if(tmp == null)
						continue;
					/*
					 * Clean the candidate itemsets according to "a priori"
					 * principle. Any (i+1)-itemset has infrequent i-subset
					 * won't be frequent. 
					 */
					boolean hasInfrequentSubsets = false;
					ItemSet[] subsets = tmp.getLargeSubsets();
					for (ItemSet subSet : subsets) {
						if (in.contains(subSet) == false) {
							hasInfrequentSubsets = true;
							break;
						}
					}
					if(hasInfrequentSubsets)
						continue;
					else
						cur.add(tmp);
				}
			return cur;
		}
	}

	/**
	 * 
	 * Join two itemsets in the frequent i-itemsets to
	 * get a candidate (i+1) itemset. If the first (i-1)
	 * items in the itemsets are the same, and the i-th
	 * items are different, they can be joined.
	 * 
	 * @param a
	 *            frequent itemset a.
	 * @param b
	 *            frequent itemset a.
	 * @return a candidate itemset if a and b can be joined,
	 * {@code null} otherwise.
	 */
	public ItemSet gen(ItemSet a, ItemSet b)
	{
		ItemSet tmp = null;
		if (CandidateGenerator.samen(a, b) == true)
		{
			tmp = new ItemSet();
			tmp.addItems(a);
			tmp.addItems(b);
		}
		return tmp;
	}

	/**
	 * Compare the given itemsets to see if they can be joined.
	 * If the first (i-1) items in the itemsets are the same, 
	 * and the i-th items are different, two frequent i-itemsets
	 * can be joined to a candidate (i+1) itemset.
	 * 
	 * @param a
	 *            a frequent itemset.
	 * @param b
	 *            another frequent itemset.}
	 * @return  {@code true} if the given itemsets have the same
	 * size, and only the last item of them are not equal;
	 * {@code false} otherwise.
	 */
	protected static Boolean samen(ItemSet a, ItemSet b)
	{

		int i, eqno = 0;
		short[] fn1 = a.getItems();
		short[] fn2 = b.getItems();
		if (fn1.length == fn2.length)
		{
			for (i = 0; i < fn1.length; i++)
			{
				if (fn1[i] == fn2[i])
				{
					eqno++;
				}
				else
				{
					break;
				}
			}
			if (eqno == a.getItemCount() - 1)
				return true;
			else
				return false;
		}
		else
			return false;
	}

}
