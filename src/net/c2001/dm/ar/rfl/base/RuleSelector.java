package net.c2001.dm.ar.rfl.base;

import java.util.Collection;
import java.util.Vector;

import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.ItemSet;


/**
 * This class can be used for association rule filtering.
 * 
 * @author Lin Dong
 * 
 */
public abstract class RuleSelector {
	/**
	 * Minimum support value, 0.
	 */
	protected final float SUP_MIN = 0f;
	/**
	 * Maximum support value, 1.
	 */
	protected final float SUP_MAX = 1f;
	/**
	 * Minimum confidence value, 0.
	 */
	protected final float CONF_MIN = 0f;
	/**
	 * Maximum confidence value, 1.
	 */
	protected final float CONF_MAX = 0f;

	/**
	 * Sort method of association rules.
	 * @author Lin Dong
	 *
	 */
	public static enum SortBy {
		/**
		 * Support descending.
		 */
		SupDesc,
		/**
		 * Support ascending.
		 */
		SupInc, 
		/**
		 * Confidence descending.
		 */
		ConfDesc,
		/**
		 * Confidence ascending.
		 */
		ConfInc,
		/**
		 * Count of items in the rule, descending.
		 */
		ItemCountDesc, 
		/**
		 * Count of items in the rule, ascending.
		 */
		ItemCountInc,
		/**
		 * Count of items in antecedent, descending.
		 */
		SrcItemCountDesc, 
		/**
		 * Count of items in antecedent, ascending.
		 */
		SrcItemCountInc, 
		/**
		 * Count of items in consequent, descending.
		 */
		DstItemCountDesc, 
		/**
		 * Count of items in consequent, ascending.
		 */
		DstItemCountInc,
		/**
		 * Antecedent, descending.
		 */
		SrcDesc, 
		/**
		 * Antecedent, ascending.
		 */
		SrcInc, 
		/**
		 * Consequent, descending.
		 */
		DstDesc, 
		/**
		 * Consequent, ascending.
		 */
		DstInc,
		/**
		 * Float value, descending.
		 */
		FloatDesc,
		/**
		 * Float value, ascending.
		 */
		FloatInc
	};

	/**
	 * Select rules that have support lower than given value.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param support
	 *            support threshold.
	 * @return result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 * 
	 */
	public AssociationRules getRulesSupLowerThan(AssociationRules rule,
			float support) {
		return getRulesSupBetween(rule, SUP_MIN, true, support, false);
	}

	/**
	 * Select rules that have support lower than or equal to given value.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param support
	 *            support threshold.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesSupLowerThanOrEqualTo(AssociationRules rule,
			float support) {
		return getRulesSupBetween(rule, SUP_MIN, true, support, true);
	}

	/**
	 * Select rules that have support larger than given value.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param support
	 *            support threshold.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesSupGreaterThan(AssociationRules rule,
			float support) {
		return getRulesSupBetween(rule, support, false, SUP_MAX, true);
	}

	/**
	 * Select rules that have support larger than or equal to given value.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param support
	 *            support threshold.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesSupGreaterThanOrEqualTo(
			AssociationRules rule, float support) {
		return getRulesSupBetween(rule, support, true, SUP_MAX, true);
	}

	/**
	 * Select rules that have support in given interval.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param lBound
	 *            lower bound of the interval.
	 * @param lEqual
	 *            whether the lower bound is closed.
	 * @param uBound
	 *            upper bound of the interval.
	 * @param uEqual
	 *            whether the lower bound is closed.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	abstract public AssociationRules getRulesSupBetween(AssociationRules rule,
			float lBound, boolean lEqual, float uBound, boolean uEqual);

	/**
	 * Select rules that have confidence lower than given value.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param confidence
	 *            confidence threshold.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesConfLowerThan(AssociationRules rule,
			float confidence) {
		return getRulesSupBetween(rule, CONF_MIN, true, confidence, false);
	}

	/**
	 * Select rules that have confidence lower than or equal to given value.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param confidence
	 *            confidence threshold.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesConfLowerThanOrEqualTo(AssociationRules rule,
			float confidence) {
		return getRulesSupBetween(rule, CONF_MIN, true, confidence, true);
	}

	/**
	 * Select rules that have confidence larger than given value.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param confidence
	 *            confidence threshold.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesConfGreaterThan(AssociationRules rule,
			float confidence) {
		return getRulesSupBetween(rule, confidence, false, CONF_MAX, true);
	}

	/**
	 * Select rules that have confidence lower than or equal to given value.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param confidence
	 *            confidence threshold.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesConfGreaterThanOrEqualTo(
			AssociationRules rule, float confidence) {
		return getRulesSupBetween(rule, confidence, true, CONF_MAX, true);
	}

	/**
	 * Select rules that have confidence in given interval.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param lBound
	 *            lower bound of the interval.
	 * @param lEqual
	 *            whether the lower bound is closed.
	 * @param uBound
	 *            upper bound of the interval.
	 * @param uEqual
	 *            whether the lower bound is closed.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	abstract public AssociationRules getRulesConfBetween(AssociationRules rule,
			float lBound, boolean lEqual, float uBound, boolean uEqual);

	/**
	 * Select rules which contains the given itemsets in antecedent.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param src
	 *            collection of itemset. If the antecedent of a rule contains
	 *            any itemset in this collection it will be selected. If
	 *            the collection is null or empty no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesInAntecedent(AssociationRules rule,
			Collection<ItemSet> src) {
		return getRulesByContent(rule, src, true, false, null, false, false);
	}

	/**
	 * Select rules which do not contain the given itemsets in antecedent.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param src
	 *            collection of itemset. If the antecedent of a rule contains
	 *            any itemset in this collection it will not be selected. If
	 *            the collection is null or empty no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesNotInAntecedent(AssociationRules rule,
			Collection<ItemSet> src) {
		return getRulesByContent(rule, src, false, false, null, false, false);

	}
	
	/**
	 * Select rules which contains the given itemset in antecedent.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param src
	 *            itemset. If the antecedent of a rule contains
	 *            this itemset it will be selected. If the collection 
	 *            is null or empty no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesInAntecedent(AssociationRules rule, ItemSet src) {
		if(src == null || src.getItemCount() == 0)
			return getRulesByContent(rule, null, false, false, null, false, false);
		else {
			Vector<ItemSet> iset = new Vector<ItemSet>();
			iset.add(src);
			return getRulesByContent(rule, iset, true, false, null, false, false);			
		}
	}

	/**
	 * Select rules which do not contain the given itemset in antecedent.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param src
	 *            itemset. If the antecedent of a rule do not contain
	 *            this itemset it will be selected. If the collection 
	 *            is null or empty no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesNotInAntecedent(AssociationRules rule, ItemSet src) {
		if(src == null || src.getItemCount() == 0)
			return getRulesByContent(rule, null, false, false, null, false, false);
		else {
			Vector<ItemSet> iset = new Vector<ItemSet>();
			iset.add(src);
			return getRulesByContent(rule, iset, false, false, null, false, false);			
		}
	}
	
	

	/**
	 * Select rules which contains the given itemsets in consequent.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param dst
	 *            collection of itemset. If the consequent of a rule contains
	 *            any itemset in this collection it will be selected. If
	 *            the collection is null or empty no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesInConsequence(AssociationRules rule,
			Collection<ItemSet> dst) {
		return getRulesByContent(rule, null, false, false, dst, true, false);
	}

	/**
	 * Select rules which do not contain the given itemsets in consequent.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param dst
	 *            collection of itemset. If the consequent of a rule do not
	 *            contain any itemset in this collection it will be selected. 
	 *            If the collection is null or empty no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesNotInConsequence(AssociationRules rule,
			Collection<ItemSet> dst) {
		return getRulesByContent(rule, null, false, false, dst, false, false);

	}
	
	/**
	 * Select rules which contain the given itemset in consequent.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param dst
	 *            itemset. If the consequent of a rule do not contain
	 *            this itemset it will be selected. If the collection 
	 *            is null or empty no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesInConsequence(AssociationRules rule, ItemSet dst) {
		if(dst == null || dst.getItemCount() == 0)
			return getRulesByContent(rule, null, false, false, null, false, false);
		else {
			Vector<ItemSet> iset = new Vector<ItemSet>();
			iset.add(dst);
			return getRulesByContent(rule, null, false, false, iset, true, false);			
		}
	}
	
	/**
	 * Select rules which do not contain the given itemset in consequent.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param dst
	 *            itemset. If the consequent of a rule do not contain
	 *            this itemset it will be selected. If the collection 
	 *            is null or empty no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesNotInConsequence(AssociationRules rule, ItemSet dst) {
		if(dst == null || dst.getItemCount() == 0)
			return getRulesByContent(rule, null, false, false, null, false, false);
		else {
			Vector<ItemSet> iset = new Vector<ItemSet>();
			iset.add(dst);
			return getRulesByContent(rule, null, false, false, iset, false, false);			
		}
	}
	
	/**
	 * Select rules according to antecedent. If the antecedent of a rule 
	 * is in the given list, this rule will be selected. 
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param src
	 *            a collection of itemsets. If the antecedent of
	 *            a rule is in the given list, 
	 *            this rule will be selected. If the collection 
	 *            is null or empty no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesAntecedentIs(AssociationRules rule,
			Collection<ItemSet> src) {
		return getRulesByContent(rule, src, true, true, null, false, false);
	}

	/**
	 * Select rules according to antecedent. If the antecedent of a rule 
	 * is in the given list, this rule will not be selected. 
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param src
	 *            a collection of itemsets.If the antecedent of 
	 *            a rule is in the given list, 
	 *            this rule willnot  be selected. If the collection 
	 *            is null or empty no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesAntecedentIsNot(AssociationRules rule,
			Collection<ItemSet> src) {
		return getRulesByContent(rule, src, false, true, null, false, false);

	}
	
	/**
	 * Select rules according to antecedent. If the antecedent of a rule 
	 * is the given itemset, this rule will be selected. 
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param src
	 *            a itemset. If the antecedent of a rule equals to this itemset,
	 *            the rule will be selected. If {@code src} is {@code null} 
	 *            no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesAntecedentIs(AssociationRules rule, ItemSet src) {
		if(src == null || src.getItemCount() == 0)
			return getRulesByContent(rule, null, false, false, null, false, false);
		else {
			Vector<ItemSet> iset = new Vector<ItemSet>();
			iset.add(src);
			return getRulesByContent(rule, iset, true, true, null, false, false);			
		}
	}

	/**
	 * Select rules according to antecedent. If the antecedent of a rule 
	 * is not the given itemset, this rule will be selected. 
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param src
	 *            a itemset. If the antecedent of a rule equals to this itemset,
	 *            the rule will not be selected. If {@code src} is {@code null} 
	 *            no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesAntecedentIsNot(AssociationRules rule, ItemSet src) {
		if(src == null || src.getItemCount() == 0)
			return getRulesByContent(rule, null, false, false, null, false, false);
		else {
			Vector<ItemSet> iset = new Vector<ItemSet>();
			iset.add(src);
			return getRulesByContent(rule, iset, false, true, null, false, false);			
		}
	}
	
	/**
	 * Select rules according to consequent. If the consequent of a rule 
	 * is in the given list, this rule will be selected. 
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param dst
	 *            a collection of itemset. If the consequent of
	 *            a rule is in the given list, 
	 *            this rule will be selected. If the collection 
	 *            is null or empty no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesConsequenceIs(AssociationRules rule,
			Collection<ItemSet> dst) {
		return getRulesByContent(rule, null, false, false, dst, true, true);
	}

	/**
	 * Select rules according to consequent. If the consequent of a rule 
	 * is in the given list, this rule will not be selected. 
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param dst
	 *            a collection of itemsets.If the antecedent of 
	 *            a rule is in the given list, 
	 *            this rule willnot  be selected. If the collection 
	 *            is null or empty no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesConsequenceIsNot(AssociationRules rule,
			Collection<ItemSet> dst) {
		return getRulesByContent(rule, null, false, false, dst, false, true);

	}
	
	/**
	 * Select rules according to consequent. If the consequent of a rule 
	 * is the given itemset, this rule will be selected. 
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param dst
	 *            a itemset. If the consequent of a rule equals to this itemset,
	 *            the rule will be selected. If {@code src} is {@code null} 
	 *            no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesConsequenceIs(AssociationRules rule, ItemSet dst) {
		if(dst == null || dst.getItemCount() == 0)
			return getRulesByContent(rule, null, false, false, null, false, false);
		else {
			Vector<ItemSet> iset = new Vector<ItemSet>();
			iset.add(dst);
			return getRulesByContent(rule, null, false, false, iset, true, true);			
		}
	}

	/**
	 * Select rules according to consequent. If the consequent of a rule 
	 * is not the given itemset, this rule will be selected. 
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param dst
	 *            a itemset. If the consequent of a rule equals to this itemset,
	 *            the rule will not be selected. If {@code src} is {@code null} 
	 *            no selection will be done.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesConsequenceIsNot(AssociationRules rule, ItemSet dst) {
		if(dst == null || dst.getItemCount() == 0)
			return getRulesByContent(rule, null, false, false, null, false, false);
		else {
			Vector<ItemSet> iset = new Vector<ItemSet>();
			iset.add(dst);
			return getRulesByContent(rule, null, false, false, iset, false, true);			
		}
	}

	/**
	 * Select rules according to consequent and antecedent.
	 * 
	 * @param rule
	 *            rules to filtrate.
	 * @param src
	 *            collection of itemsets as blacklist or whitelist of antecednet.
	 *            If this collection is {@code null} or empty, selection 
	 *            according to antecedent will not be excuted.
	 * @param inSrc
	 *            {@code true} means {@code src} is a whitelist,
	 *            {@code false} means {@code src} is a blacklist.
	 * @param isSrc {@code true} means {@code src} is a strick list, subset will 
	 * not be considered. {@code true} means {@code src} subset is okay. <br> 
	 * For example, if {@code inSrc} is {@code true}, itemset {1,2} satisfies 
	 * list {{1,2},{3,4}}, but itemset {1} do not. if {@code inSrc} is 
	 * {@code true}, {1} also satisfies list {{1,2},{3,4}}.
	 * 
	 * @param dst
	 *            collection of itemsets as blacklist or whitelist of consequent.
	 *            If this collection is {@code null} or empty, selection 
	 *            according to consequent will not be excuted.
	 * @param inDst
	 *            {@code true} means {@code dst} is a whitelist,
	 *            {@code false} means {@code dst} is a blacklist.
	 * @param isDst {@code true} means {@code dst} is a strick list, subset will 
	 * not be considered. {@code true} means {@code dst} subset is okay. <br> 
	 * For example, if {@code inSrc} is {@code true}, itemset {1,2} satisfies 
	 * list {{1,2},{3,4}}, but itemset {1} do not. if {@code inSrc} is 
	 * {@code true}, {1} also satisfies list {{1,2},{3,4}}.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	abstract public AssociationRules getRulesByContent(AssociationRules rule,
			Collection<ItemSet> src, boolean inSrc, boolean isSrc, Collection<ItemSet> dst,
			boolean inDst, boolean isDst);
	
	/**
	 * Get the first n rules.
	 * @param rule rules to filtrate.
	 * @param n number of rules to get.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getFirstNRules(AssociationRules rule, int n) {
		return getRulesByIndex(rule, 0, n-1);
	}
	
	/**
	 * Get the last n rules.
	 * @param rule rules to filtrate.
	 * @param n number of rules to get.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getLastNRules(AssociationRules rule, int n) {
		int end, start;
		if(rule == null || rule.getRules().size() == 0)
			end = 0;
		else
			end = rule.getRules().size() - 1;
		start = end - n + 1;
		return getRulesByIndex(rule, start, end);
	}
	
	/**
	 * Select rules by index. The {@code start}th to the {@code end}th
	 * rule will be selected.
	 * @param rule rules to filtrate.
	 * @param start minimum index, start from 0.
	 * @param end maximum index.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	abstract public AssociationRules getRulesByIndex(AssociationRules rule, int start, int end);
	
	/**
	 * Sort rules. 
	 * @param rule rules to filtrate.
	 * @param method sort method, enumberation in {@link SortBy}. 
	 * {@code FloatInc} and {@code FloaDesc} are not applicable here.
	 * @return  result of sorting, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. 
	 */
	abstract public AssociationRules sortRules(AssociationRules rule, SortBy method);

	/**
	 * Sort rules according to given float array. 
	 * @param rule rules to filtrate.
	 * @param values array of float numbers, stores some evaluation indicator
	 * of rules.
	 * @param method sort method, enumeration in {@link SortBy}.{@code FloatInc}
	 * or {@code FloaDesc}.
	 * @return  result of sorting, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. 
	 */
	abstract public AssociationRules sortRules(AssociationRules rule, float[] values, SortBy method);

	/**
	 *Select rules that have {@code start} to {@code end} items.
	 * @param rule rules to filtrate.
	 * @param start minimux count of items.
	 * @param end maximum count of items.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	abstract public AssociationRules getRulesByItemCount(AssociationRules rule, 
			int start, int end);
	
	/**
	 * Select rules that have {@code start} to {@code end} items in their
	 * antecedents. 
	 * @param rule rules to filtrate.
	 * @param start minimux count of items.
	 * @param end maximum count of items.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	abstract public AssociationRules getRulesBySrcItemCount(AssociationRules rule, 
			int start, int end);
	
	/**
	 * Select rules that have {@code start} to {@code end} items in their
	 * consequents. 
	 * @param rule rules to filtrate.
	 * @param start minimux count of items.
	 * @param end maximum count of items.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	abstract public AssociationRules getRulesByDstItemCount(AssociationRules rule, 
			int start, int end);
	
	/**
	 * Select rules that have {@code n} items.
	 * @param rule rules to filtrate.
	 * @param n count of items
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesByItemCount(AssociationRules rule, int n) {
		return getRulesByItemCount(rule, n, n);
	}
	
	/**
	 * Select rules that have fewer than {@code n} items.
	 * @param rule rules to filtrate.
	 * @param n count of items
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesItemCountLessThan(AssociationRules rule, int n) {
		return getRulesByItemCount(rule, 1, n - 1);
	}
	
	/**
	 * Select rules that have no more than {@code n} items.
	 * @param rule rules to filtrate.
	 * @param n count of items
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesItemCountLessThanOrEqualTo(AssociationRules rule, int n) {
		return getRulesByItemCount(rule, 1, n);
	}
	
	/**
	 * Select rules that have more than {@code n} items.
	 * @param rule rules to filtrate.
	 * @param n count of items
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesItemCountGreaterThan(AssociationRules rule, int n) {
		return getRulesByItemCount(rule, n + 1, Short.MAX_VALUE);
	}
	
	/**
	 * Select rules that have {@code n} or more items.
	 * @param rule rules to filtrate.
	 * @param n count of items
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesItemCountGreaterThanOrEqualTo(AssociationRules rule, int n) {
		return getRulesByItemCount(rule, n, Short.MAX_VALUE);
	}
	
	/**
	 * Select rules that have {@code n} items in antecedent.
	 * @param rule rules to filtrate.
	 * @param n count of items
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesBySrcItemCount(AssociationRules rule, int n) {
		return getRulesBySrcItemCount(rule, n, n);
	}
	
	/**
	 * Select rules that have fewer than {@code n}items in antecedent.
	 * @param rule rules to filtrate.
	 * @param n count of items
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesSrcItemCountLessThan(AssociationRules rule, int n) {
		return getRulesBySrcItemCount(rule, 1, n - 1);
	}
	
	/**
	 * Select rules that have no more than {@code n}items in antecedent.
	 * @param rule rules to filtrate.
	 * @param n count of items
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesSrcItemCountLessThanOrEqualTo(AssociationRules rule, int n) {
		return getRulesBySrcItemCount(rule, 1, n);
	}
	
	/**
	 * Select rules that have more than {@code n}items in antecedent.
	 * @param rule rules to filtrate.
	 * @param n count of items
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesSrcItemCountGreaterThan(AssociationRules rule, int n) {
		return getRulesBySrcItemCount(rule, n + 1, Short.MAX_VALUE);
	}
	
	/**
	 * Select rules that have {@code n} or more items in antecedent.
	 * @param rule rules to filtrate.
	 * @param n count of items
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesSrcItemCountGreaterThanOrEqualTo(AssociationRules rule, int n) {
		return getRulesBySrcItemCount(rule, n, Short.MAX_VALUE);
	}

	/**
	 * Select rules that have {@code n} items in consequent.
	 * @param rule rules to filtrate.
	 * @param n count of items.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesByDstItemCount(AssociationRules rule, int n) {
		return getRulesByDstItemCount(rule, n, n);
	}
	
	/**
	 * Select rules that have less than {@code n} items in consequent.
	 * @param rule rules to filtrate.
	 * @param n count of items.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesDstItemCountLessThan(AssociationRules rule, int n) {
		return getRulesByDstItemCount(rule, 1, n - 1);
	}
	
	/**
	 * Select rules that have {@code n} or fewer items in consequent.
	 * @param rule rules to filtrate.
	 * @param n count of items.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesDstItemCountLessThanOrEqualTo(AssociationRules rule, int n) {
		return getRulesByDstItemCount(rule, 1, n);
	}
	
	/**
	 * Select rules that have more than {@code n} items in consequent.
	 * @param rule rules to filtrate.
	 * @param n count of items.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesDstItemCountGreaterThan(AssociationRules rule, int n) {
		return getRulesByDstItemCount(rule, n + 1, Short.MAX_VALUE);
	}
	
	/**
	 * Select rules that have {@code n} or more items in consequent.
	 * @param rule rules to filtrate.
	 * @param n count of items.
	 * @return  result of selection, an {@link AssociationRules} object
	 * which use the frequent itemsets' and item names' reference in
	 * the input rules. If there is no rule in the result, the 
	 * {@link AssociationRules}'s getRules() method will return an
	 * empty vector (not null).
	 */
	public AssociationRules getRulesDstItemCountGreaterThanOrEqualTo(AssociationRules rule, int n) {
		return getRulesByDstItemCount(rule, n, Short.MAX_VALUE);
	}

}