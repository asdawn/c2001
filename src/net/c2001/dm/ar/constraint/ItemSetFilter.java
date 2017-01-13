package net.c2001.dm.ar.constraint;

import java.util.ArrayList;
import java.util.List;
import net.c2001.dm.ar.garmf.ItemSet;


/**
 * Filter for itemsets.
 * @author Lin Dong
 *
 */
abstract public class ItemSetFilter {
	/**
	 * Filtrate {@link List} of itemsets, delete all itemsets.
	 * according to the requirement (relies on the implementation). 
	 * @param itemsets {@link List} of {@link ItemSet}.
	 */
	public void filterate(List<ItemSet> itemsets) {
		List<ItemSet> toDell = new ArrayList<>();
		for (ItemSet itemSet : itemsets) {
			if(isValid(itemSet) == false)
				toDell.add(itemSet);
		}
		itemsets.removeAll(toDell);
	}
	
	/**
	 * Judge whether an itemset is valid. Invalid itemsets will be removed.
	 * @param itemset itemset.
	 * @return {@code true} for valid, {@code false} otherwise.
	 */
	abstract protected boolean isValid(ItemSet itemset);
}
