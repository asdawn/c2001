package net.c2001.dm.ar.spatial.raster;

import java.util.List;

import org.gdal.gdal.Dataset;

import net.c2001.dm.ar.constraint.Constraints;
import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.garmf.ItemNames;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.incremental.SupIncApriori;
import net.c2001.dm.ar.spatial.AreaCalculator;
import net.c2001.dm.ar.spatial.RasterItems;

/**
 * {@link Data} using raster datasets as items.
 * 
 * @author Lin Dong
 * 
 */
public class SupIncRasterData extends RasterData {

	private static final long serialVersionUID = -1733988064065305665L;

	/**
	 * create a {@link SupIncRasterData}.
	 * 
	 * @param ritems
	 *            {@link RasterItems} object.
	 * @param totalArea
	 *            area of research region.
	 * @param calc
	 *            {@link RasterAC}.
	 */
	protected SupIncRasterData(RasterItems ritems, double totalArea,
			AreaCalculator<Dataset> calc) {
		super(ritems, totalArea, calc);
	}

	/**
	 * create a {@link SupIncRasterData}.
	 * 
	 * @param ritems
	 *            {@link RasterItems} object.
	 * @param totalArea
	 *            area of research region.
	 */
	public SupIncRasterData(RasterItems ritems, double totalArea) {
		super(ritems, totalArea, new RasterAC(ritems.getDatasets()));
	}

	@Override
	protected void init(RasterItems ritems, double totalArea,
			AreaCalculator<Dataset> calc) {
		this.totalArea = totalArea;
		miner = new SupIncApriori();
		names = new ItemNames();
		names.setNames(ritems.getNames());
		ac = calc;
		/*
		 * miner, ac and ritems are treated as elements and share the message
		 * processor.
		 */
		this.addElementObject(miner);
		this.addElementObject(ac);
		this.addElementObject(ritems);
	}

	/**
	 * Update mined results using new minimum support threshold.
	 * The minimum confidence will be the old one save in
	 * {@code oldRules}.
	 * 
	 * @param oldRules
	 *            mined results.
	 * @param newSupport
	 *            new minimum support threshold.
	 * @param constraints
	 *            constraints in mining.
	 * @return updated rules on success, {@code null} on failure.
	 */
	public AssociationRules update(AssociationRules oldRules, double newSupport,
			Constraints constraints) {
		boolean smaller = false;
		if (newSupport <= 0)
			newSupport = Double.MIN_VALUE;
		else if (newSupport > 1)
			newSupport = 1;
		if (newSupport < oldRules.supmin) {
			smaller = true;
			oldRules.supmin = newSupport;
		}
		if (smaller) {
			return appendMining(oldRules, constraints);
		} else {
			return removeInfrequentItems(oldRules, newSupport);
		}
	}

	/**
	 * Remove itemsets and association rules which contain infrequent items. If
	 * the support of an item/itemset/rule is smaller than {@code newSupport},
	 * then it is infrequent.
	 * 
	 * @param oldRules
	 *            rules to update.
	 * @param newSupMin
	 *            new minimum support threshold.
	 * 
	 * @return updated rules on success, {@code null} on failure.
	 */
	protected AssociationRules removeInfrequentItems(AssociationRules oldRules,
			double newSupMin) {

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Re-mine data using updated data. The old threshold which save in the rule
	 * object will be used again.
	 * 
	 * @param oldRules
	 *            old rules to update.
	 * @param constraints
	 *            {@link Constraints} in mining.
	 * @return updated association rules.
	 */
	protected AssociationRules appendMining(AssociationRules oldRules,
			Constraints constraints) {
		SupIncApriori defaultMiner = (SupIncApriori) miner;
		defaultMiner.setOldResults(oldRules);
		defaultMiner.setConstraints(constraints);
		List<List<ItemSet>> frequents = defaultMiner.minePatterns(this,
				oldRules.supmin);
		defaultMiner.showStatistics();
		AssociationRules newRules = defaultMiner.createRules(this.names,
				frequents, oldRules.confmin);
		newRules.confmin = oldRules.confmin;
		newRules.supmin = oldRules.supmin;
		newRules.setItemNames(this.names);
		this.ac.showStatistics();
		return newRules;
	}

}
