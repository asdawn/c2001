package net.c2001.dm.ar.garmf;

import java.util.List;

import net.c2001.base.Controllable;
import net.c2001.dm.ar.constraint.Constraints;
import net.c2001.dm.gmf.Algorithm;

/**
 * Data for frequent pattern/association rule mining. <br>
 * Note the support of and itemset A is the estimation of 
 * P(A), so the total count of records and the support counts
 * of itemsets are not necessarily to be the values in 
 * reality, representations in ration or any other way are 
 * also acceptable if P(A) can be correctly calculated.<br>
 * Note: the {@code mine} method inherited from {@link net.c2001.dm.gmf.Data}
 * is a wrapper of the {@code mine} method in this class with parameters
 * 0.1f, 0.9f and {@code null}. 
 * 
 * @author Lin Dong
 */
abstract public class Data extends net.c2001.dm.gmf.Data<AssociationRules> implements Controllable
{
	
	private volatile boolean stop = false;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5333267992746287259L;

	/**
	 * The implementation of the Apriori algorithm. Different
	 * types of data may need different implementations, so 
	 * it is bonded to {@link Data}. 
	 */
	protected Algorithm<AssociationRules> miner = null;
	
	/**
	 * Mine association rules from data.
	 * @param supmin minimum support threshold.
	 * @param confmin minimum confidence threshold.
	 * @param constraints constraints over candidate and frequent itemsets. 
	 * {@code null} for no constraints.
	 * @return rules on success, {@code null} otherwise.
	 */
	public AssociationRules mine(double supmin, double confmin,
			Constraints constraints) {
		DefaultApriori defaultMiner = (DefaultApriori) miner;
		defaultMiner.setConstraints(constraints);
		List<List<ItemSet>> frequents = defaultMiner.minePatterns(this, supmin);
		defaultMiner.showStatistics();
		AssociationRules rules = defaultMiner.createRules(names, frequents, confmin);
		rules.confmin = confmin;
		rules.supmin = supmin;
		return rules;			
	}
	
	/**
	 * Mine association rules from data.
	 * @param supmin minimum support threshold.
	 * @param confmin minimum confidence threshold.
	 * @return rules on success, {@code null} otherwise.
	 */
	public AssociationRules mine(double supmin, double confmin) {
		return mine(supmin, confmin, null);
	}
	
	
	/**
	 * Set default mining algorithm for data.
	 * @param algorithm mining algorithm.
	 */
	public void setAlgorithm(Algorithm<AssociationRules> algorithm){
		this.miner = algorithm;
	}
	
	/**
	 * Names of items.
	 */
	public ItemNames names = null;

	/**
	 * Return the total count of records in mining.
	 * @return the total count of records in mining.
	 */
	abstract public double getRecordCount();

	/**
	 * Returns the total count of items in mining.
	 * 
	 * @return the total count of items in mining.
	 */
	abstract public int getItemCount();

	/**
	 * Returns the support count of the given itemset.
	 * 
	 * @param iset
	 *            an itemset.
	 * @return the support count of the given itemset.
	 */
	abstract public double getItemSetCount(ItemSet iset);

	/**
	 * Get the support count of a group of itemsets. This method will be
	 * called by mining algorithm, override it if necessary.
	 * 
	 * @param itemsets
	 *            {@link List} of {@link ItemSet}.
	 * @return {@code double} array which stores the support count
	 * of {@code itemsets}.
	 */
	public double[] getItemSetsCount(List<ItemSet> itemsets)
	{
		double[] result = null;
		if (itemsets != null)
		{
			result = new double[itemsets.size()];
			for (int i = 0; i < result.length; i++)
			{
				if(stop == true){
					stop = false;
					Thread.currentThread().interrupt();
				}
				result[i] = getItemSetCount(itemsets.get(i));				
			}
		}
		return result;
	}
	
	@Override
	public AssociationRules mine() {
		return mine(0.1, 0.9, null);
	}
	
	@Override
	public boolean pause() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean restore() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public synchronized boolean stop() {
		if(Thread.currentThread().isAlive()){
			this.stop = true;
		}
		return true;
	}
}
