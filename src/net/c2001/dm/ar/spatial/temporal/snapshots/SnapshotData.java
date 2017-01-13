package net.c2001.dm.ar.spatial.temporal.snapshots;

import java.util.List;

import net.c2001.dm.ar.constraint.Constraints;
import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.garmf.DefaultApriori;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.spatial.AreaCalculator;
import net.c2001.dm.ar.spatial.temporal.SpatiotemporalData;

/**
 * Several series of snapshots as {@link Data}. 
 * @author Lin Dong
 *
 */
public class SnapshotData<E, T extends SnapShots<E>, S extends SnapshotsItems<E, T>> extends SpatiotemporalData{

	private static final long serialVersionUID = -2187694277176298233L;
	/**
	 * The measure of the whole dataset.
	 */
	protected double totalMeasure;
	protected AreaCalculator<T> m;

	/**
	 * create a {@link SnapshotData}.
	 * 
	 * @param fitems
	 *            items.
	 * @param measure
	 *            measure of research region.
	 * @param calc
	 *            {@link AreaCalculator} for {@link Snapshots}.
	 */
	public SnapshotData(S items, double measure,
			AreaCalculator<T> calc) {
		this.totalMeasure = measure;
		miner = new DefaultApriori();
		names = items.getItemNames();
		m = calc;
		/*
		 * miner, ac and fitems are treated as elements and share the message
		 * processor.
		 */
		this.addElementObject(miner);
		this.addElementObject(m);
		this.addElementObject(items);
	}
	
	

	@Override
	public int getItemCount() {
		return names.size();
	}

	@Override
	public double getItemSetCount(ItemSet itemset) {
		double measure = m.getArea(itemset);
		if (this.getDebugOutputStatus()) {
			this.text(itemset.toString());
			this.text("Measure: " + measure);
		}
		return measure;
	}

	@Override
	public double getRecordCount() {
		return  totalMeasure;
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
		this.m.showStatistics();
		return rules;
	}
}
