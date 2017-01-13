package net.c2001.dm.ar.spatial;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.spatial.AreaCalculator;
import net.c2001.utils.CommonOps;

/**
 * Calculate the area of the intersection of spatial dataset specified by
 * itemsets. This calculator will keep a hash map and use fast intersect method
 * to save time. Fast-intersect can decrease the complexity of intersection from
 * o(n) to o(1), for details please refer to Lin Dong's article <i>"Spatial
 * association rule mining based on overlay analysis and area calculation"</i>.<br>
 * Note: for users, just remember {@code setMinimumArea(supmin*totalArea)},
 * where supmin is the minimum support threshold, and totalArea is the total
 * area of research region. The {@code setMinimumArea} method must have been
 * called, or minimum area is specified when this object is created.
 * 
 * 
 * 
 * @author Lin Dong
 * 
 */
abstract public class HashedAreaCalculator<E> extends AreaCalculator<E> {

	private static final long serialVersionUID = -321002086667145407L;

	/**
	 * The minimum area of itemsets to keep in hash.
	 */
	protected double area = 0;

	/**
	 * Do clean to save storage.
	 */
	protected boolean doClean = false;

	/**
	 * Mapping frequent itemsets and spatial datasets.
	 */
	protected HashMap<ItemSet, E> map = null;

	/**
	 * Current itemset size. When it growth the old itemsets and corresponding
	 * features will be removed from {@code map} and physically to save storage
	 * space.
	 */
	protected int currentItemSetSize = 1;

	/**
	 * Specify the minimum area of itemset to keep in hash. The hash will be
	 * used for fast intersect. It equals to supmin*totalArea.
	 * 
	 * @param area
	 *            the minimum area of itemsets to keep in hash.
	 */
	public void setMinimumArea(double area) {
		this.area = area;
	}

	/**
	 * Create a {@link AreaCalculator}.
	 * 
	 * @param datasets
	 *            spatial datasets as items.
	 * 
	 * @param doClean
	 *            remove temporary feature classes.
	 */
	public HashedAreaCalculator(List<E> datasets, boolean doClean) {
		super(datasets, false);
		this.map = new HashMap<>();
		this.doClean = doClean;
	}

	/**
	 * Create a {@link AreaCalculator}.
	 * 
	 * @param datasets
	 *            spatial datasets as items.
	 * @param area
	 *            the minimum area of itemset to keep in hash.
	 * 
	 */
	public HashedAreaCalculator(List<E> datasets, double area) {
		super(datasets, true);
		this.setMinimumArea(area);
		this.map = new HashMap<>();
		this.doClean = false;
	}

	/**
	 * Create a {@link AreaCalculator}.
	 * 
	 * @param datasets
	 *            spatial datasets as items.
	 * @param area
	 *            the minimum area of itemset to keep in hash.
	 * 
	 * @param doClean
	 *            remove temporary feature classes.
	 */
	public HashedAreaCalculator(List<E> datasets, boolean doClean, double area) {
		super(datasets, doClean);
		this.map = new HashMap<>();
		this.setMinimumArea(area);
	}

	/**
	 * Create a {@link AreaCalculator}.
	 * 
	 * @param datasets
	 *            spatial datasets as items.
	 */
	public HashedAreaCalculator(List<E> datasets) {
		super(datasets, true);
		this.map = new HashMap<>();
	}

	/**
	 * Remove all spatial datasets in current map. When finished to get area of
	 * (k+1)-itemsets, the k-itemsets is no longer needed.
	 * 
	 */
	protected void doClean() {
		List<ItemSet> itemsets = new ArrayList<>();
		for (ItemSet itemSet : this.map.keySet()) {
			// Clear k, current is k+2. Check it at the start of itemset loop.
			if (itemSet.getItemCount() == this.currentItemSetSize - 2)
				itemsets.add(itemSet);
		}
		for (ItemSet itemSet : itemsets) {
			E dataset = this.map.get(itemSet);
			doClean(dataset);
			map.remove(itemSet);
		}
		itemsets.clear();
	}

	/**
	 * Returns the area of intersection of spatial datasets corresponding to the
	 * given itemset. The {@code setMinimumArea} method must have been called,
	 * or minimum area is specified when this object is created.
	 * 
	 * @param itemset
	 *            {@link ItemSet}.
	 * @return {@code itemset} area corresponding to the itemset.
	 */
	public double getArea(ItemSet itemset) {
		if (this.area == 0)
			throw new InvalidParameterException("Minimum area not specified.");
		calls++;
		List<E> datasets = new ArrayList<>();
		short[] fieldNums = itemset.getItems();
		if (itemset.getItemCount() == 1) {
			short item = fieldNums[0];
			E dataset = this.datasets.get(item - 1);
			datasets.add(dataset);

		} else {// not 1-itemset
			// Do clean when start (k+1)-itemset
			if (fieldNums.length > this.currentItemSetSize) {
				currentItemSetSize++;
				if (doClean)
					doClean();
			}
			ItemSet a = new ItemSet();
			ItemSet b = new ItemSet();
			// Remove head or tail
			for (short i = 0; i < fieldNums.length; i++) {
				if (i != 0)
					a.addItem(fieldNums[i]);
				if (i != fieldNums.length - 1)
					b.addItem(fieldNums[i]);
			}
			E A = this.map.get(a);
			E B = this.map.get(b);
			if (A == null || B == null)
				throw new RuntimeException("Can not find itemset in hash.");
			datasets.add(A);
			datasets.add(B);
		}
		double area = getAreaAndUpdateHash(datasets, itemset);
		datasets.clear();
		return area;
	}

	/**
	 * Get the area of given itemset using spatial datasets listed in
	 * {@code datasets}. If the area is no less that {@code this.area} the hash
	 * map will add a new entry for this itemset.
	 * 
	 * @param datasets
	 *            two spatial datasets.
	 * @param itemset
	 *            the corresponding itemset.
	 * @return the area of given itemset/features.
	 */
	protected double getAreaAndUpdateHash(List<E> datasets, ItemSet itemset) {
		E currentDataset = null;
		if (datasets.size() == 1) {
			currentDataset = datasets.get(0);
		} else {
			long start = CommonOps.tick();
			currentDataset = getIntersection(datasets);
			intersectTime += CommonOps.tock(start);
			start = CommonOps.tick();
			if (currentDataset == null){				
				return -1;
			}
		}
		long start = CommonOps.tick();
		double a = getArea(currentDataset);
		areaCalcTime += CommonOps.tock(start);
		if (a == -1) {
			String msg = "Error occurred while area calculation,"
					+ " the area will be treated as 0.";
			if (this.getDebugOutputStatus())
				this.warning(this, msg);
			a = 0;
		}
		// update hash
		if (a >= this.area)
			this.map.put(itemset, currentDataset);
		else {
			/*
			 * Remove infrequent feature class, but source data (1-itemsets)
			 * should not be modified.
			 */
			if (this.doCleanEveryIntersection && itemset.getItemCount() > 1)
				doClean(currentDataset);
		}
		if (this.getDebugOutputStatus()) {
			this.text(this, currentDataset.toString());
			this.text(this, "Area: " + a);
		}
		return a;
	}

	/**
	 * Get the hash map maintained by this program.
	 * 
	 * @return the mapping of itemsets and spatial datasets.
	 */
	public HashMap<ItemSet, E> getHashMap() {
		return this.map;
	}

}
