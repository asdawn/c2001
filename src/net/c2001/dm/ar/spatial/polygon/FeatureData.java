package net.c2001.dm.ar.spatial.polygon;

import java.util.List;

import org.gdal.ogr.Layer;

import net.c2001.dm.ar.constraint.Constraints;
import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.garmf.DefaultApriori;
import net.c2001.dm.ar.garmf.ItemNames;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.spatial.AreaCalculator;
import net.c2001.dm.ar.spatial.FeatureItems;

/**
 * {@link Data} using feature layers as items.
 * 
 * @author Lin Dong
 * 
 */
public class FeatureData extends Data {
	private static final long serialVersionUID = -2187694277176298233L;
	/**
	 * Area of research region, you can consider it as the total count of
	 * records.
	 */
	protected double totalArea;
	protected AreaCalculator<Layer> ac;

	/**
	 * create a {@link FeatureData}.
	 * 
	 * @param fitems
	 *            {@link FeatureItems} object.
	 * @param totalArea
	 *            area of research region.
	 * @param calc
	 *            {@link AreaCalculator} for feature layers.
	 */
	public FeatureData(FeatureItems fitems, double totalArea,
			AreaCalculator<Layer> calc) {
		this.totalArea = totalArea;
		miner = new DefaultApriori();
		names = new ItemNames();
		names.setNames(fitems.getNames());
		ac = calc;
		/*
		 * miner, ac and fitems are treated as elements and share the message
		 * processor.
		 */
		this.addElementObject(miner);
		this.addElementObject(ac);
		this.addElementObject(fitems);
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
		return  totalArea;
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
