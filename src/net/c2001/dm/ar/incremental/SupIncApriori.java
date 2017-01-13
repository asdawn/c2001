package net.c2001.dm.ar.incremental;

import java.util.ArrayList;
import java.util.List;

import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.CandidateGenerator;
import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.garmf.ItemSet;


/**
 * The implementation of the incremental Apriori algorithm for support
 * changes.
 * 
 * @author Lin Dong
 * 
 */
public class SupIncApriori extends IncrementalApriori {

	private static final long serialVersionUID = -2400005185746503175L;
	
	/**
	 * Create a {@link SupIncApriori}.
	 */
	public SupIncApriori() {
		super();
	}

	/**
	 * Create a {@link SupIncApriori}.
	 * 
	 * @param oldResults
	 *            rules to update.
	 */
	public SupIncApriori(AssociationRules oldResults) {
		super();
		setOldResults(oldResults);
	}

	/**
	 * Create a {@link SupIncApriori} using given candidate generator. Some
	 * extensions of Apriori just modified the candidate generation Strategy,
	 * just make a subclass of {@link CandidateGenerator} is enough.
	 * 
	 * @param generator
	 *            {@link CandidateGenerator}.
	 */
	public SupIncApriori(CandidateGenerator generator) {
		super(generator);
	}

	/**
	 * Create a {@link SupIncApriori} using given candidate generator. Some
	 * extensions of Apriori just modified the candidate generation Strategy,
	 * just make a subclass of {@link CandidateGenerator} is enough.
	 * 
	 * @param generator
	 *            {@link CandidateGenerator}.
	 * @param oldResults
	 *            rules to update.
	 */
	public SupIncApriori(CandidateGenerator generator,
			AssociationRules oldResults) {
		super(generator);
		setOldResults(oldResults);
	}
	
	/**
	 * Find frequent patterns in candidates according to given threshold and
	 * mined results.
	 * 
	 * @param data
	 *            data to analysis.
	 * @param candidates
	 *            candidate patterns.
	 * @param threshold
	 *            the threshold for mining.
	 * @return list of frequent patterns. Always return a {@link List} object,
	 *         if there're no frequent patterns it should be empty.
	 */
	protected List<ItemSet> getFrequent(Data data, List<ItemSet> candidates,
			double threshold) {
		if(candidates.size() == 0){
			return new ArrayList<>();
		}
		List<ItemSet> frequents = new ArrayList<>();
		List<ItemSet> known = new ArrayList<>();
		int size = candidates.get(0).getItemCount();
		double recordCount = data.getRecordCount();
		if(this.oldResults != null && this.oldResults.getFrequentItemset()!= null){
			for (ItemSet itemSet : this.oldResults.getFrequentItemset()) {
				if(itemSet.getItemCount() == size)
					known.add(itemSet);
			}
		}
		for (ItemSet itemSet : candidates) {
			double support = 0;
			int index = known.indexOf(itemSet);
			if (index != -1) {
				support = known.get(index).getSupport();
			} else {
				support = data.getItemSetCount(itemSet) / recordCount;
			}
			if (support >= threshold) {
				itemSet.setSupport(support);
				frequents.add(itemSet);
			}
		}
		known.clear();
		return frequents;
	}
	
}
