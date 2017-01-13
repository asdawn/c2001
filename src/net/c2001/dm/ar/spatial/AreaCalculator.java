package net.c2001.dm.ar.spatial;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import net.c2001.base.Object;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.utils.CommonOps;

/**
 * Calculate the area of the intersection of spatial datasets specified by
 * itemsets.
 * 
 * @param <E>
 *            the type of spatial datasets.
 * @author Lin Dong
 */
abstract public class AreaCalculator<E> extends Object {

	private static final long serialVersionUID = 77310831119230379L;
	/**
	 * The time cost by area calculation.
	 */
	protected long areaCalcTime = 0;
	/**
	 * The time cost by intersecting layers.
	 */
	protected long intersectTime = 0;
	/**
	 * Total calls of current {@link AreaCalculator}.
	 */
	protected int calls = 0;
	/**
	 * The datasets to use.
	 */
	protected List<E> datasets = null;
	/**
	 * Remove temporary data after intersection or not.
	 */
	protected boolean doCleanEveryIntersection = false;

	/**
	 * Create a {@link AreaCalculator}.
	 * 
	 * @param datasets
	 *            datasets as items.
	 * @param doClean
	 *            remove temporary datasets.
	 */
	public AreaCalculator(List<E> datasets, boolean doClean) {
		init(datasets, doClean);
	}

	private void init(List<E> datasets, boolean doClean) {
		if (datasets == null)
			throw new NullPointerException();
		if (datasets.isEmpty())
			throw new InvalidParameterException(
					"At least one dataset is needed.");
		this.datasets = datasets;
		this.doCleanEveryIntersection = doClean;
	}

	/**
	 * Create a {@link AreaCalculator}.
	 * 
	 * @param datasets
	 *            datasets as items.
	 */
	public AreaCalculator(List<E> datasets) {
		init(datasets, true);
	}

	/**
	 * Returns the area of intersection of {@code datasets}. If there is only
	 * one dataset in {@code datasets}, its area will be returned.
	 * 
	 * @param datasets
	 *            datasets to get intersection and area.
	 * @return the area of the intersection on success, 0 on failure.
	 */
	protected double getArea(List<E> datasets) {
		double a = -1;
		if (datasets.size() == 1) {
			long start = CommonOps.tick();
			a = getArea(datasets.get(0));
			areaCalcTime += CommonOps.tock(start);
			if (this.getDebugOutputStatus())
				this.text(this, datasets.get(0).toString());
		} else {
			long start = CommonOps.tick();
			E dataset = getIntersection(datasets);
			intersectTime += CommonOps.tock(start);
			start = CommonOps.tick();
			if (dataset == null)
				a = -1;
			else {
				a = getArea(dataset);
			}
			areaCalcTime += CommonOps.tock(start);
			if (a == -1) {
				String msg = "Error occurred while area calculation,"
						+ " the area will be treated as 0.";
				if (this.getDebugOutputStatus())
					this.warning(this, msg);
				a = 0;
			}
			if (doCleanEveryIntersection)
				doClean(dataset);
		}
		if (this.getDebugOutputStatus())
			this.text(this, "Area: " + a);
		return a;
	}

	/**
	 * Get the intersection of given datasets.
	 * @param datasets datasets to intersect.
	 * @return the intersection on success, {@code null} on failure.
	 */
	abstract protected E getIntersection(List<E> datasets);

	/**
	 * Get the area of dataset.
	 * @param dataset dataset.
	 * @return the area on success, -1 on failure.
	 */
	abstract protected double getArea(E dataset);

	/**
	 * Do clean after getting area of a temporary intersection.
	 * 
	 * @param dataset
	 *            the dataset to remove.
	 */
	abstract protected void doClean(E dataset);

	/**
	 * Returns the area of intersection of datasets corresponding to the given
	 * itemset.
	 * 
	 * @param itemset
	 *            {@link ItemSet}.
	 * @return {@code itemset} area corresponding to the itemset.
	 */
	public double getArea(ItemSet itemset) {
		calls++;
		short[] fieldNums = itemset.getItems();
		List<E> datasets = new ArrayList<>();
		for (short number : fieldNums) {
			datasets.add(this.datasets.get(number - 1));
		}
		double area = getArea(datasets);
		datasets.clear();
		return area;
	}

	/**
	 * Show time consumption statistics.
	 */
	public void showStatistics() {
		this.text(this.getClass().getName());
		this.text("\nStatistics:");
		this.text("\ttotally " + calls + " calls,");
		this.text("\tareaCalcTime = " + areaCalcTime);
		this.text("\tintersectTime = " + intersectTime);
	}
}
