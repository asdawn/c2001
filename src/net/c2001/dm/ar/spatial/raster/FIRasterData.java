package net.c2001.dm.ar.spatial.raster;

import java.util.HashMap;
import java.util.List;

import net.c2001.dm.ar.constraint.Constraints;
import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.garmf.DefaultApriori;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.spatial.RasterItems;

/**
 * {@link Data} using raster datasets as items. This is the fast-intersect 
 * version.
 * 
 * @author Lin Dong
 * 
 */
public class FIRasterData extends RasterData {


	private static final long serialVersionUID = -6358441002839262290L;

	
	/**
	 * create a {@link FIRasterData}.
	 * 
	 * @param ritems
	 *            {@link RasterItems} object.
	 * @param totalArea
	 *            area of research region.
	 */
	public FIRasterData(RasterItems ritems, double totalArea) {
		super(ritems, totalArea, new HashedRasterAC(ritems.getDatasets()));
		
	}	

	@Override
	public AssociationRules mine(double supmin, double confmin,
			Constraints constraints) {
		DefaultApriori defaultMiner = (DefaultApriori) miner;
		defaultMiner.setConstraints(constraints);
		HashedRasterAC ac = (HashedRasterAC) this.ac;
		ac.setMinimumArea(supmin*this.totalArea);
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
	 * Remove all temporary files and empty the {@link HashMap}.
	 * Do not call this method unless you don't want to keep the
	 * layers corresponding to frequent itemsets.
	 */
	public void doClean() {
		if(this.ac == null)
			return;
		HashedRasterAC ac = (HashedRasterAC) this.ac;
		for (ItemSet itemset : ac.getHashMap().keySet()) {
			if(itemset.getItemCount() > 1)
				ac.doClean(ac.getHashMap().get(itemset));
		}
		ac.getHashMap().clear();		
	}

}
