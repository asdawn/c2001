package net.c2001.dm.ar.rfl.base;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.garmf.Rule;
import net.c2001.dm.ar.rfl.comparators.FloatAttribute;
import net.c2001.dm.ar.rfl.comparators.RuleComparatorFactory;

/**
 * Subclass of {@link RuleSelector}, for the selection of rules saved in
 * {@link AssociationRules} objects.
 * 
 * @author Lin Dong
 * 
 */
public class MemRuleSelector extends RuleSelector {

	private static MemRuleSelector selector = null;

	/**
	 * Singleton.
	 */
	private MemRuleSelector() {

	}

	/**
	 * Get the {@code MemRuleSelector} instance in current JVM.
	 * 
	 * @return {@code MemRuleSelector}
	 */
	public static MemRuleSelector getInstance() {
		if (selector == null) {
			selector = new MemRuleSelector();
		}
		return selector;
	}

	@Override
	public AssociationRules getRulesByContent(AssociationRules rule,
			Collection<ItemSet> src, boolean inSrc, boolean isSrc,
			Collection<ItemSet> dst, boolean inDst, boolean isDst) {
		AssociationRules temp = getRulesBySrc(rule, src, inSrc, isSrc);
		return getRulesByDst(temp, dst, inDst, isDst);
	}

	private AssociationRules getRulesBySrc(AssociationRules rule,
			Collection<ItemSet> src, boolean inSrc, boolean isSrc) {
		if (rule == null || rule.getRules() == null)
			throw new NullPointerException("Rules should not be null.");
		if (src == null || src.size() == 0)
			return fullCopyRule(rule);
		AssociationRules newRule = copyRule(rule);
		boolean add = false;
		for (Rule r : rule.getRules()) {
			if (inSrc && isSrc) {
				add = false;
				for (ItemSet itemSet : src) {
					if (itemSet.equals(r.src)) {
						add = true;
						break;
					}
				}
			} else if (inSrc && !isSrc) {
				add = false;
				for (ItemSet itemSet : src) {
					if (r.src.contains(itemSet)) {
						add = true;
						break;
					}
				}
			} else if (!inSrc && isSrc) {
				add = true;
				for (ItemSet itemSet : src) {
					if (itemSet.equals(r.src)) {
						add = false;
						break;
					}
				}
			} else {
				add = true;
				for (ItemSet itemSet : src) {
					if (r.src.contains(itemSet)) {
						add = false;
						break;
					}
				}
			}
			if (add == true)
				newRule.getRules().add(r);

		}
		return newRule;
	}

	private AssociationRules getRulesByDst(AssociationRules rule,
			Collection<ItemSet> dst, boolean inDst, boolean isDst) {
		if (rule == null || rule.getRules() == null)
			throw new NullPointerException("Rules should not be null.");
		if (dst == null || dst.size() == 0)
			return fullCopyRule(rule);
		AssociationRules newRule = copyRule(rule);
		boolean add = false;
		for (Rule r : rule.getRules()) {
			if (inDst && isDst) {
				add = false;
				for (ItemSet itemSet : dst) {
					if (itemSet.equals(r.dst)) {
						add = true;
						break;
					}
				}
			} else if (inDst && !isDst) {
				add = false;
				for (ItemSet itemSet : dst) {
					if (r.dst.contains(itemSet)) {
						add = true;
						break;
					}
				}
			} else if (!inDst && isDst) {
				add = true;
				for (ItemSet itemSet : dst) {
					if (itemSet.equals(r.dst)) {
						add = false;
						break;
					}
				}
			} else {
				add = true;
				for (ItemSet itemSet : dst) {
					if (r.dst.contains(itemSet)) {
						add = false;
						break;
					}
				}
			}
			if (add == true)
				newRule.getRules().add(r);

		}
		return newRule;
	}

	@Override
	public AssociationRules getRulesByIndex(AssociationRules rule, int start,
			int end) {
		if (rule == null || rule.getRules() == null)
			throw new NullPointerException("Rules should not be null.");
		AssociationRules newRule = copyRule(rule);
		if (start < 0)
			start = 0;
		if (end > rule.getRules().size() - 1)
			end = rule.getRules().size() - 1;
		for (int i = start; i <= end; i++)
			newRule.getRules().add(rule.getRules().get(i));
		return newRule;
	}

	@Override
	public AssociationRules getRulesConfBetween(AssociationRules rule,
			float bound, boolean equal, float bound2, boolean equal2) {
		float lBound = bound;
		float uBound = bound2;
		AssociationRules newRule = copyRule(rule);
		if (uBound <= lBound)
			return newRule;
		if (lBound < CONF_MIN)
			lBound = CONF_MIN;
		if (uBound > CONF_MAX)
			uBound = CONF_MAX;
		for (Rule r : rule.getRules()) {
			if (equal && equal2) {
				if (r.conf >= lBound && r.conf <= uBound)
					newRule.getRules().add(r);
			} else if (equal && !equal2) {
				if (r.conf >= lBound && r.conf < uBound)
					newRule.getRules().add(r);
			} else if (!equal && equal2) {
				if (r.conf > lBound && r.conf <= uBound)
					newRule.getRules().add(r);
			} else {
				if (r.conf > lBound && r.conf < uBound)
					newRule.getRules().add(r);
			}
		}
		return newRule;
	}

	@Override
	public AssociationRules getRulesSupBetween(AssociationRules rule,
			float bound, boolean equal, float bound2, boolean equal2) {
		float lBound = bound;
		float uBound = bound2;
		AssociationRules newRule = copyRule(rule);
		if (uBound <= lBound)
			return newRule;
		if (lBound < SUP_MIN)
			lBound = SUP_MIN;
		if (uBound > SUP_MAX)
			uBound = SUP_MAX;
		for (Rule r : rule.getRules()) {
			if (equal && equal2) {
				if (r.sup >= lBound && r.sup <= uBound)
					newRule.getRules().add(r);
			} else if (equal && !equal2) {
				if (r.sup >= lBound && r.sup < uBound)
					newRule.getRules().add(r);
			} else if (!equal && equal2) {
				if (r.sup > lBound && r.sup <= uBound)
					newRule.getRules().add(r);
			} else {
				if (r.sup > lBound && r.sup < uBound)
					newRule.getRules().add(r);
			}
		}
		return newRule;
	}

	@Override
	public AssociationRules sortRules(AssociationRules rule, SortBy method) {
		if (rule == null || rule.getRules() == null)
			throw new NullPointerException("Rules should not be null.");
		int count = rule.getRules().size();
		if (count == 0)
			return copyRule(rule);
		AssociationRules newRule = fullCopyRule(rule);
		Comparator<Rule> comparator = RuleComparatorFactory
				.getRuleComparator(method);
		if (comparator == null)
			return null;
		Collections.sort(newRule.getRules(), comparator);
		return newRule;
	}

	@Override
	public AssociationRules getRulesByItemCount(AssociationRules rule,
			int start, int end) {
		if (rule == null || rule.getRules() == null)
			throw new NullPointerException("Rules should not be null.");
		if (end < 2 || end < start)
			return copyRule(rule);
		AssociationRules newRule = copyRule(rule);
		for (Rule Rule : rule.getRules()) {
			int count = Rule.src.getItemCount() + Rule.dst.getItemCount();
			if (count >= start && count <= end)
				newRule.getRules().add(Rule);
		}
		return newRule;
	}

	@Override
	public AssociationRules getRulesBySrcItemCount(AssociationRules rule,
			int start, int end) {
		if (rule == null || rule.getRules() == null)
			throw new NullPointerException("Rules should not be null.");
		if (end < 1 || end < start)
			return copyRule(rule);
		AssociationRules newRule = copyRule(rule);
		for (Rule Rule : rule.getRules()) {
			int count = Rule.src.getItemCount();
			if (count >= start && count <= end)
				newRule.getRules().add(Rule);
		}
		return newRule;
	}

	@Override
	public AssociationRules getRulesByDstItemCount(AssociationRules rule,
			int start, int end) {
		if (rule == null || rule.getRules() == null)
			throw new NullPointerException("Rules should not be null.");
		if (end < 1 || end < start)
			return copyRule(rule);
		AssociationRules newRule = copyRule(rule);
		for (Rule Rule : rule.getRules()) {
			int count = Rule.dst.getItemCount();
			if (count >= start && count <= end)
				newRule.getRules().add(Rule);
		}
		return newRule;
	}

	/**
	 * Select rules according to the number of items. If in a rule the numbers
	 * of items in antecedent is smaller than that in consequent, this rule will
	 * be selected. This is useful when you want to get sequential patterns.
	 * 
	 * @param rule
	 *            {@link AssociationRules}.
	 * @return result of selection, an AssociationRules object which use the
	 *         frequent itemsets' and item names' reference in the input rules.
	 *         If there is no rule in the result, the AssociationRules's
	 *         getRules() method will return an empty vector (not null).
	 */
	public AssociationRules selectSequenceRules(AssociationRules rule) {
		AssociationRules newRule = copyRule(rule);
		boolean ok = true;
		for (Rule r : rule.getRules()) {
			short srcMax = r.src.getItems()[r.src.getItemCount() - 1];
			short dstMin = r.dst.getItems()[0];
			if (dstMin > srcMax)
				ok = true;
			else
				ok = false;
			if (ok)
				newRule.getRules().add(r);
		}
		return newRule;
	}

	/**
	 * Create a copy of given {@link AssociationRules}. Rules will be set to an
	 * empty vector of {@link Rule}.
	 * 
	 * @param rule
	 *            {@link AssociationRules}.
	 * @return a new {@link AssociationRules}, use frequent itemsets' and item
	 *         names' reference in {@code rule}, and a copy of rules in
	 *         {@code rule}.
	 * 
	 */
	private AssociationRules copyRule(AssociationRules rule) {
		if (rule == null || rule.getRules() == null)
			throw new NullPointerException("Rules should not be null.");
		AssociationRules newRule = new AssociationRules();
		newRule.setFrequentItemset(rule.getFrequentItemset());
		newRule.setItemNames(rule.getItemNames());
		Vector<Rule> rules = new Vector<Rule>();
		newRule.setRules(rules);
		return newRule;
	}

	/**
	 * Create a full copy of given {@link AssociationRules}.
	 * 
	 * @param rule
	 *            {@link AssociationRules}.
	 * @return a new {@link AssociationRules}, use frequent itemsets' and item
	 *         names' reference in {@code rule} and a copy of rules in
	 *         {@code rule}.
	 */
	private AssociationRules fullCopyRule(AssociationRules rule) {
		AssociationRules newRule = copyRule(rule);
		for (Rule r : rule.getRules())
			newRule.getRules().add(r);
		return newRule;
	}

	@Override
	public AssociationRules sortRules(AssociationRules rule, float[] values,
			SortBy method) {
		AssociationRules newRule = copyRule(rule);
		if (values == null || rule.getRules().size() != values.length) {
			throw new InvalidParameterException(
					"Element count of float array and"
							+ "rule count are not equal.");
		}
		FloatAttribute[] attributes = new FloatAttribute[values.length];
		for (int i = 0; i < values.length; i++) {
			attributes[i] = new FloatAttribute();
			attributes[i].index = i;
			attributes[i].value = values[i];
		}
		RuleComparatorFactory.getFloatComparator(method);
		Comparator<FloatAttribute> comparator = RuleComparatorFactory
				.getFloatComparator(method);
		if (comparator == null)
			return null;
		Arrays.sort(attributes, comparator);
		for (FloatAttribute floatAttribute : attributes) {
			newRule.getRules().add(rule.getRules().get(floatAttribute.index));
		}
		return newRule;
	}
}
