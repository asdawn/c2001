package net.c2001.dm.ar.spatial.polygon;

import java.util.HashMap;
import java.util.List;

import net.c2001.dm.ar.constraint.Constraints;
import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.garmf.DefaultApriori;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.spatial.FeatureItems;

/**
 * {@link Data} using feature layers as items. This is the fast-intersect
 * version.
 * 
 * @author Lin Dong
 * 
 */
public class FIFeatureData extends FeatureData {

	private static final long serialVersionUID = -6358441002839262290L;

	/**
	 * create a {@link FIFeatureData}.
	 * 
	 * @param fitems
	 *            {@link FeatureItems} object.
	 * @param totalArea
	 *            area of research region.
	 * @param calc
	 *            {@link HashedFeatureAC}.
	 */
	public FIFeatureData(FeatureItems fitems, double totalArea,
			HashedFeatureAC calc) {
		super(fitems, totalArea, calc);
	}

	@Override
	public AssociationRules mine(double supmin, double confmin,
			Constraints constraints) {
		DefaultApriori defaultMiner = (DefaultApriori) miner;
		defaultMiner.setConstraints(constraints);
		HashedFeatureAC ac = (HashedFeatureAC) this.ac;
		ac.setMinimumArea(supmin * this.totalArea);
		List<List<ItemSet>> frequents = defaultMiner.minePatterns(this, supmin);
		defaultMiner.showStatistics();
		AssociationRules rules = defaultMiner.createRules(names, frequents,
				confmin);
		this.ac.showStatistics();
		rules.confmin = confmin;
		rules.supmin = supmin;
		return rules;
	}

	/**
	 * Remove all temporary files and empty the {@link HashMap}. Do not call
	 * this method unless you don't want to keep the layers corresponding to
	 * frequent itemsets.
	 */
	public void doClean() {
		if (this.ac == null)
			return;
		HashedFeatureAC ac = (HashedFeatureAC) this.ac;
		for (ItemSet itemset : ac.getHashMap().keySet()) {
			if (itemset.getItemCount() > 1)
				ac.doClean(ac.getHashMap().get(itemset));
		}
		ac.getHashMap().clear();
	}

}
