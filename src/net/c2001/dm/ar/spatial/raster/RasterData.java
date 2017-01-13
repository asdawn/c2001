package net.c2001.dm.ar.spatial.raster;

import java.util.List;

import org.gdal.gdal.Dataset;

import net.c2001.dm.ar.constraint.Constraints;
import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.garmf.DefaultApriori;
import net.c2001.dm.ar.garmf.ItemNames;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.spatial.AreaCalculator;
import net.c2001.dm.ar.spatial.RasterItems;

/**
 * {@link Data} using raster datasets as items.
 * 
 * @author Lin Dong
 * 
 */
public class RasterData extends Data {

	private static final long serialVersionUID = 2443740626157917961L;
	/**
	 * Area of research region, you can consider it as the total count of
	 * records.
	 */
	protected double totalArea;
	protected AreaCalculator<Dataset> ac;

	/**
	 * create a {@link RasterData}.
	 * 
	 * @param ritems
	 *            {@link RasterItems} object.
	 * @param totalArea
	 *            area of research region.
	 * @param calc
	 *            {@link RasterAC}.
	 */
	protected RasterData(RasterItems ritems, double totalArea,
			AreaCalculator<Dataset> calc) {
		init(ritems, totalArea, calc);
	}

	/**
	 * create a {@link RasterData}.
	 * 
	 * @param ritems
	 *            {@link RasterItems} object.
	 * @param totalArea
	 *            area of research region.
	 */
	public RasterData(RasterItems ritems, double totalArea) {
		init(ritems, totalArea, new RasterAC(ritems.getDatasets()));
	}

	/**
	 * Setup parameters for {@link RasterData}.
	 * 
	 * @param ritems
	 *            {@link RasterItems} object.
	 * @param totalArea
	 *            area of research region.
	 * @param calc
	 *            {@link RasterAC}.
	 */
	protected void init(RasterItems ritems, double totalArea,
			AreaCalculator<Dataset> calc) {
		this.totalArea = totalArea;
		miner = new DefaultApriori();
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

	@Override
	public int getItemCount() {
		return names.size();
	}

	@Override
	public double getItemSetCount(ItemSet itemset) {
		double area = ac.getArea(itemset);
		if (this.getDebugOutputStatus()) {
			this.text(itemset.toString());
			this.text("Area: " + area);
		}
		return area;
	}

	@Override
	public double getRecordCount() {
		return totalArea;
	}

	@Override
	public AssociationRules mine(double supmin, double confmin,
			Constraints constraints) {
		DefaultApriori defaultMiner = (DefaultApriori) miner;
		defaultMiner.setConstraints(constraints);
		List<List<ItemSet>> frequents = defaultMiner.minePatterns(this, supmin);
		defaultMiner.showStatistics();
		AssociationRules rules = defaultMiner.createRules(names, frequents,
				confmin);
		rules.confmin = confmin;
		rules.supmin = supmin;
		this.ac.showStatistics();
		return rules;
	}

}
