package net.c2001.dm.ar.garmf;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link CandidateGenerator} for trajectory mining. It only create temporal
 * trajectory candidates, that is, for candidate 3-itemsets, the time of items
 * should be [tx, t(x+1), t(x+2)]. if the time is not continuous, it is not a
 * valid candidate.
 * 
 * 
 * @author Lin Dong
 * 
 */
public abstract class TrajectoryCandidateGenerator extends CandidateGenerator {
	/**
	 * Create an {@link TrajectoryCandidateGenerator} object.
	 */
	public TrajectoryCandidateGenerator() {
	}

	@Override
	public List<ItemSet> gen(List<ItemSet> in) {
		if (in == null || in.size() == 0) {
			return null;
		} else {
			for (ItemSet itemSet : in) {
				if (itemSet.getItemCount() != in.get(0).getItemCount())
					throw new InvalidParameterException(
							"Size of itemsets are not equal:" + itemSet);
			}
			int i, j;
			ItemSet a, b;
			List<ItemSet> cur = new ArrayList<ItemSet>(10);
			int count = in.size();
			for (i = 0; i < count - 1; i++)
				for (j = i + 1; j < count; j++) {
					a = in.get(i);
					b = in.get(j);
					ItemSet tmp = gen(a, b);
					if (tmp == null)
						continue;
					//Do not have to check it.
					cur.add(tmp);
				}
			return cur;
		}
	}

	@Override
	public ItemSet gen(ItemSet a, ItemSet b) {
		ItemSet tmp = new ItemSet();
		tmp.addItems(a);
		tmp.addItems(b);
		if(tmp.getItemCount() != a.getItemCount()+1)
			return null;		
		if(this.adjacentTemporally(tmp))
			return tmp;
		else
			return null;
	}
	
	/**
	 * Test whether the items in given itemset is temporally 
	 * adjacent, that is, any two items should not be of
	 * the same time tag, and all the time tags should be
	 * continuous. For example, if there are 5 times tags
	 * denoted as t1, t2, t3, t4 and t5, t2, t3, t4 are temporally 
	 * adjacent, but t1, t3, t4 is not. For any itemset 
	 * adjacent temporally, it should occupy {@code getItemCount()}
	 * adjacent time tags.
	 * @param itemset {@link ItemSet} to test.
	 * @return {@code true} for yes, {@code false} otherwise.
	 */
	abstract protected boolean adjacentTemporally (ItemSet itemset);

}
