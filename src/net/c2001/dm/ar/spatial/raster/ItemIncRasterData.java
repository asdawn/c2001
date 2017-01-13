package net.c2001.dm.ar.spatial.raster;

import java.util.List;

import org.gdal.gdal.Dataset;

import net.c2001.dm.ar.constraint.Constraints;
import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.garmf.ItemNames;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.incremental.ItemIncApriori;
import net.c2001.dm.ar.spatial.AreaCalculator;
import net.c2001.dm.ar.spatial.RasterItems;

/**
 * {@link Data} using raster datasets as items.
 * 
 * @author Lin Dong
 * 
 */
public class ItemIncRasterData extends RasterData {

	private static final long serialVersionUID = -1733988064065305665L;

	/**
	 * create a {@link ItemIncRasterData}.
	 * 
	 * @param ritems
	 *            {@link RasterItems} object.
	 * @param totalArea
	 *            area of research region.
	 * @param calc
	 *            {@code RasterAC}.
	 */
	protected ItemIncRasterData(RasterItems ritems, double totalArea,
			AreaCalculator<Dataset> calc) {
		super(ritems, totalArea, calc);
	}

	/**
	 * create a {@link ItemIncRasterData}.
	 * 
	 * @param ritems
	 *            {@link RasterItems} object.
	 * @param totalArea
	 *            area of research region.
	 */
	public ItemIncRasterData(RasterItems ritems, double totalArea) {
		super(ritems, totalArea, new RasterAC(ritems.getDatasets()));
	}

	@Override
	protected void init(RasterItems ritems, double totalArea,
			AreaCalculator<Dataset> calc) {
		this.totalArea = totalArea;
		miner = new ItemIncApriori();
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
	 * Update mined results using updated data.
	 * 
	 * @param oldRules
	 *            mined results.
	 * @param constraints
	 *            constraints in mining.
	 * @return updated rules on success, {@code null} on failure.
	 */
	public AssociationRules update(AssociationRules oldRules,
			Constraints constraints) {
		boolean append = false;
		if (this.names.getNames().size() > (oldRules.getItemNames().getNames().size())) {
			append = true;
		}
		if (append) {
			return appendMining(oldRules, constraints);
		} else {
			return removeInvalidItems(oldRules);
		}
	}

	/**
	 * Remove itemsets and association rules which contain invalid itemsets. If
	 * the name of an item in {@code oldRules} do not exist in current object's
	 * {@code names}, it will be considered invalid.
	 * 
	 * @param oldRules
	 *            rules to update.
	 * 
	 * @return updated rules on success, {@code null} on failure.
	 */
	protected AssociationRules removeInvalidItems(AssociationRules oldRules) {

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
		ItemIncApriori defaultMiner = (ItemIncApriori) miner;
		defaultMiner.setOldResults(oldRules);
		defaultMiner.setConstraints(constraints);
		List<List<ItemSet>> frequents = defaultMiner.minePatterns(this,
				oldRules.supmin);
		defaultMiner.showStatistics();
		AssociationRules newRules = defaultMiner.createRules(this.names, frequents,
				oldRules.confmin);
		newRules.confmin = oldRules.confmin;
		newRules.supmin = oldRules.supmin;
		newRules.setItemNames(this.names);
		this.ac.showStatistics();
		return newRules;
	}

}
