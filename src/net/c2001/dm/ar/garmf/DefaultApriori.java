package net.c2001.dm.ar.garmf;


import java.util.ArrayList;
import java.util.List;

import net.c2001.dm.ar.constraint.Constraints;
import net.c2001.dm.base.AbstractApriori;
import net.c2001.utils.CommonOps;

/**
 * The implementation of the Apriori algorithm, it can be used for association
 * rule mining. Extensions can be created as subclasses of this class.
 * 
 * @author Lin Dong
 * 
 */
public class DefaultApriori extends AbstractApriori<Data,  AssociationRules, ItemSet> {

	/**
	 * Constraints of itemsets.
	 */
	protected Constraints constraints = null;

	/**
	 * Generator of candidate itemsets.
	 */
	protected CandidateGenerator generator = null;

	private static final long serialVersionUID = 5526625497578426562L;

	/**
	 * Create a {@link DefaultApriori}.
	 */
	public DefaultApriori() {
		this.generator = new CandidateGenerator();
	}

	/**
	 * Create a {@link DefaultApriori} using given candidate generator. Some
	 * extensions of Apriori just modified the candidate generation Strategy,
	 * just make a subclass of {@link CandidateGenerator} is enough.
	 * 
	 * @param generator
	 *            {@link CandidateGenerator}.
	 */
	public DefaultApriori(CandidateGenerator generator) {
		this.generator = generator;
	}

	@Override
	protected List<ItemSet> getFrequent(Data data, List<ItemSet> candidates,
			double threshold) {
		List<ItemSet> frequents = new ArrayList<>();
		double recordCount = data.getRecordCount();
		for (ItemSet itemSet : candidates) {
			double support = data.getItemSetCount(itemSet) / recordCount;
			if (support >= threshold) {
				itemSet.setSupport(support);
				frequents.add(itemSet);
			}
		}
		return frequents;
	}

	@Override
	protected List<ItemSet> makeCandidatek(int k, List<ItemSet> list) {
		return this.generator.gen(list);
	}

	@Override
	protected List<ItemSet> makeCandidate1(Data data) {
		int itemCount = data.getItemCount();
		List<ItemSet> candidate = new ArrayList<>();
		for (short i = 0; i < itemCount; i++) {
			ItemSet itemset = new ItemSet();
			itemset.addItem((short) (i + 1));
			candidate.add(itemset);
		}
		return candidate;
	}

	/**
	 * Get statistics of the mining progress.
	 * 
	 * @return statistics of the mining progress.
	 */
	public List<Statistics> getStatistics() {
		return this.statistics;
	}

	/**
	 * Show statistics of the mining progress.
	 */
	public void showStatistics() {
		this.text("Statistics:");
		long totalTime = 0;
		int totalFrequent = 0;
		int totalCandidate = 0;
		for (Statistics statistic : statistics) {
			StringBuilder builder = new StringBuilder();
			builder.append(statistic.size + "-itemsets:");
			builder.append("\n\t");
			builder.append("Generated " + statistic.candidateCount
					+ " candidate itemsets, ");
			builder.append("cost " + statistic.timeCosts[0]);
			builder.append("\n\t");
			builder.append("Find " + statistic.frequentCount
					+ " frequent itemsets, ");
			builder.append("cost " + statistic.timeCosts[1]);
			builder.append("\n\t");
			builder.append("cost " + statistic.timeCosts[2] + " in total.");
			this.text(builder.toString());
			totalTime += statistic.timeCosts[2];
			totalFrequent += statistic.frequentCount;
			totalCandidate += statistic.candidateCount;
		}
		this.text(totalFrequent + " frequent itemsets are found from "
				+ totalCandidate + " candidates, cost " + totalTime
				+ " in total.");
	}

	@Override
	protected String resolvePattern(ItemSet itemset, Data data) {
		List<String> names = data.names.getName(itemset);
		/*
		 * It's a simple way, the content should be separated by comma. Override
		 * it if necessary.
		 */
		return names.toString();
	}

	/**
	 * Find association rules according to the frequent itemsets and the minimum
	 * confidence threshold.
	 * 
	 * @param names
	 *            {@link ItemNames}.
	 * @param frequents
	 *            frequent itemsets.
	 * @param confmin
	 *            minimum confidence threshold.
	 * @return {@link AssociationRules} on success.
	 */
	public AssociationRules createRules(ItemNames names,
			List<List<ItemSet>> frequents, double confmin) {
		this.progress(99.9, "Apriori", "Creating association rules");
		try {
			long start = CommonOps.tick();
			int size = 0;
			for (List<ItemSet> list : frequents) {
				size += list.size();
			}
			ItemSet[] itemsets = new ItemSet[size];
			int index = 0;
			for (List<ItemSet> list : frequents) {
				for (ItemSet itemset : list) {
					itemsets[index] = itemset;
					index++;
				}
			}
			AssociationRules associationRules = createRules(names, itemsets,
					confmin);
			long cost = CommonOps.tock(start);
			this.text(associationRules.getRuleCount() + " rules created, cost "
					+ cost);
			this.progress(100, "Apriori", "Association rules created");
			return associationRules;
		} catch (Exception e) {
			if (this.getDebugOutputStatus())
				exception(this, e, "Failed to create rules.");
			this.progress(-1, "Apriori", "Failed to create association rules");
			return null;
		}
	}

	/**
	 * Find association rules according to the frequent itemsets and the minimum
	 * confidence threshold.
	 * 
	 * @param names
	 *            {@link ItemNames}.
	 * @param itemsets
	 *            frequent itemsets.
	 * @param confmin
	 *            minimum confidence threshold.
	 * @return {@link AssociationRules} on success.
	 */
	public AssociationRules createRules(ItemNames names, ItemSet[] itemsets,
			double confmin) {
		Rule temp;
		int i, j;
		double src, dst;
		ArrayList<Rule> rules = new ArrayList<Rule>(100);
		for (i = 0; i < itemsets.length - 1; i++) {
			for (j = i + 1; j < itemsets.length; j++) {
				/*
				 * if itemsets[i] is subset of itemsets[j], then rule
				 * itemsets[i]=>itemsets[j]-itemsets[i] may exist, the
				 * confidence of this rule equals to
				 * sup(itemsets[j])/sup(itemsets[i])
				 */
				if (itemsets[j].getItemCount() > 1
						&& itemsets[j].contains(itemsets[i])) {
					/*
					 * The consequence should have only one item in it, as the
					 * beginning definition of association rule (SIGMOD 93), and
					 * doing so can reduce the total number of rules.
					 */
					if (itemsets[j].getItemCount() - itemsets[i].getItemCount() != 1)
						continue;
					/*
					 * The support count should be stored in the {@link ItemSet}
					 * objects. If not, we have to calculate again using {@link
					 * Data}.{@code getItemSetCount()}.
					 */
					src = itemsets[j].getSupport();
					dst = itemsets[i].getSupport();
					double confidence = src / dst;
					if (confidence >= confmin) {
						temp = new Rule();
						temp.conf = confidence;
						temp.src = itemsets[i];
						temp.sup = src;
						// Rule: fitemsets[i]=>fitemsets[j]-fitemsets[i]
						ItemSet copy = itemsets[j].clone();
						copy.removeItemSet(itemsets[i]);
						temp.dst = copy;
						rules.add(temp);
					}// if inner end
				}// if outer end
			}// for j end
		}// for i end
		AssociationRules associationRules = new AssociationRules(names, rules);
		// Do not forget frequent itemsets. They are more important.
		associationRules.setFrequentItemset(itemsets);
		return associationRules;
	}

	@Override
	protected void filtrateCandidates(List<ItemSet> candidates) {
		if (constraints != null && constraints.candidateFilter != null)
			constraints.candidateFilter.filterate(candidates);
	}

	@Override
	protected void filtrateFrequents(List<ItemSet> frequents) {
		if (constraints != null && constraints.frequentFilter != null)
			constraints.frequentFilter.filterate(frequents);
	}

	/**
	 * Set constraints for mining. The constraints should be cleared before
	 * mining if no constraints are needed, or constraints are changed.
	 * 
	 * @param constraints
	 *            constraints for mining.
	 */
	public void setConstraints(Constraints constraints) {
		this.constraints = constraints;
	}




}
