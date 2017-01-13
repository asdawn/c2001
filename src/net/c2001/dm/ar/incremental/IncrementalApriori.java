package net.c2001.dm.ar.incremental;

import java.util.ArrayList;
import java.util.List;

import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.CandidateGenerator;
import net.c2001.dm.ar.garmf.DefaultApriori;
import net.c2001.dm.ar.garmf.Rule;

/**
 * The implementation of the incremental Apriori algorithm.
 * 
 * @author Lin Dong
 * 
 */
abstract class IncrementalApriori extends DefaultApriori implements Incremental {

	private static final long serialVersionUID = 1158646267109593759L;

	/**
	 * Old mining results to update.
	 */
	protected AssociationRules oldResults = null;

	/**
	 * Create an {@link IncrementalApriori}.
	 */
	public IncrementalApriori() {
		super();
	}

	/**
	 * Create an {@link IncrementalApriori}.
	 * 
	 * @param oldResults
	 *            rules to update.
	 */
	public IncrementalApriori(AssociationRules oldResults) {
		super();
		setOldResults(oldResults);
	}

	/**
	 * Create an {@link IncrementalApriori} using given candidate generator. Some
	 * extensions of Apriori just modified the candidate generation Strategy,
	 * just make a subclass of {@link CandidateGenerator} is enough.
	 * 
	 * @param generator
	 *            {@link CandidateGenerator}.
	 */
	public IncrementalApriori(CandidateGenerator generator) {
		super(generator);
	}

	/**
	 * Create an {@link IncrementalApriori} using given candidate generator. Some
	 * extensions of Apriori just modified the candidate generation Strategy,
	 * just make a subclass of {@link CandidateGenerator} is enough.
	 * 
	 * @param generator
	 *            {@link CandidateGenerator}.
	 * @param rules
	 *            rules to update.
	 */
	public IncrementalApriori(CandidateGenerator generator,
			AssociationRules oldResults) {
		super(generator);
		setOldResults(oldResults);
	}

	/**
	 * Set the rules to update.
	 * 
	 * @param rules
	 *            rules to update.
	 */
	public void setOldResults(AssociationRules rules) {
		this.oldResults = rules;
	}

	@Override
	public AssociationRules updateConfmin(AssociationRules rules,
			double newConfMin) {
		if (rules.confmin < newConfMin)
			return selectRules(rules, newConfMin);
		else
			return recreateRules(rules, newConfMin);
	}

	/**
	 * Create rules using old frequent itemsets and new threshold.
	 * 
	 * @param rules
	 *            mined association rules.
	 * @param newConfMin
	 *            new minimum confidence threshold. It should be smaller than
	 *            the value used in mining {@code rules}.
	 * @return the updated rules.
	 */
	protected AssociationRules recreateRules(AssociationRules rules,
			double newConfMin) {
		AssociationRules newRules = new DefaultApriori().createRules(
				rules.getItemNames(), rules.getFrequentItemset(), newConfMin);
		newRules.supmin = rules.supmin;
		newRules.confmin = newConfMin;
		newRules.setFrequentItemset(rules.getFrequentItemset());
		newRules.setItemNames(rules.getItemNames());
		return newRules;
	}

	/**
	 * Create rules by filtrating the old rules.
	 * 
	 * @param rules
	 *            mined association rules.
	 * @param newConfMin
	 *            new minimum confidence threshold. It should be no less than
	 *            the value used in mining {@code rules}.
	 * @return the updated rules.
	 */
	protected AssociationRules selectRules(AssociationRules rules,
			double newConfMin) {
		AssociationRules newRules = new AssociationRules();
		newRules.supmin = rules.supmin;
		newRules.confmin = newConfMin;
		newRules.setFrequentItemset(rules.getFrequentItemset());
		newRules.setItemNames(rules.getItemNames());
		List<Rule> ruleList = new ArrayList<>();
		for (Rule rule : rules.getRules()) {
			if (rule.conf >= newConfMin)
				ruleList.add(rule);
		}
		newRules.setRules(ruleList);
		return newRules;
	}

}
