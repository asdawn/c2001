package net.c2001.dm.ar.garmf;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Vector;

import net.c2001.dm.ar.rfl.base.MemRuleSelector;
import net.c2001.dm.ar.rfl.base.RuleSelector.SortBy;
import net.c2001.dm.gmf.Pattern;

/**
 * A set of association rules, contains not only a set of {@link Rule},
 * but also the names of the items. Frequent itemsets can
 * be stored in an {@link AssociationRules}, too.
 *  
 * @author Lin Dong
 */
public class AssociationRules extends Pattern
{

	private static final long serialVersionUID = 6907937854248338502L;
	/**
	 * Names of items.
	 */
	private ItemNames names = null;
	/**
	 * Association rules, note {@link ItemSet} only contains item numbers.  
	 */
	private List<Rule> rules = null;
	/**
	 * Frequent itemsets.
	 */
	private ItemSet[] frequentSet = null;
	
	/**
	 * The minimum confidence threshold.
	 */
	public Double confmin = null;
	
	/**
	 * The minimum support threshold.
	 */
	public Double supmin = null;

	/**
	 * Create an {@link AssociationRules} instance.
	 */
	public AssociationRules()
	{
		
	}

	/**
	 * Create an {@link AssociationRules} instance, and 
	 * set the names of items and association rules.
	 * 
	 * @param names
	 *            names of items, an {@link ItemNames} object.
	 * @param rules
	 *            {@link List} of {@link Rule}.
	 */
	public AssociationRules(ItemNames names, List<Rule> rules)
	{
		this.setRules(rules);
		this.setItemNames(names);
	}

	/**
	 * Returns the association rule stored in this object. 
	 * 
	 * @return {@link List} of {@link Rule}.
	 */
	public List<Rule> getRules()
	{
		return rules;
	}

	/**
	 * Return the names of items.
	 * 
	 * @return the names of items.
	 */
	public ItemNames getItemNames()
	{
		return this.names;
	}

	/**
	 * Set the association rules in this object.
	 * 
	 * @param r
	 *           	{@link List} of {@link Rule}.
	 */
	public void setRules(List<Rule> r)
	{
		rules = r;
	}

	/**
	 * Set item names of this set of association rules.
	 * 
	 * @param names
	 *            {@link ItemNames} object.
	 */
	public void setItemNames(ItemNames names)
	{
		this.names = names;
	}

	/**
	 * Set the frequent itemsets.
	 * 
	 * @param frequentSet
	 *            array of {@link ItemSet}.<br>
	 *            Note: make sure the {@code recordCount}
	 *            field of {@link ItemSet} objects is
	 *            updated to support
	 *            (support count/ total record count).
	 */
	public void setFrequentItemset(ItemSet[] frequentSet)
	{
		this.frequentSet = frequentSet;
	}

	/**
	 * Returns frequent itemsets stored in this object.
	 * 
	 * @return array of {@link ItemSet}.
	 */
	public ItemSet[] getFrequentItemset()
	{
		return frequentSet;
	}

	/**
	 * Load an {@link AssociationRules} object from file.
	 * @param file that stores an {@link AssociationRules} object.
	 * @return {@link AssociationRules} object on success, {@code null}
	 * otherwise.
	 */
	public static AssociationRules loadRulesFromFile(File file)
	{
		AssociationRules rule = new AssociationRules();
		Object object = rule.loadFromFile(file);
		if(object instanceof AssociationRules)
			return (AssociationRules)object;
		else
			return null;
	}

	/**
	 * Translate to new rules with the given item names,
	 * including the association rules and the frequent itemset.
	 * Make sure the old and new names of items are process with the
	 * {@code trim()} method of {@link String}, or there might be some
	 * problems difficult to find -- for example, "name " and "name"
	 * seems the same, but they are not equal in programming languages. 
	 * 
	 * @param newItemNames
	 *            a new set of item names, it should be the old one's 
	 *            superset, or an {@link InvalidParameterException}
	 *            will be thrown.
	 * @return new association rules, names of items are changed to 
	 * {@code newFields}, and the content of rules are updated 
	 * correspondingly; {@code null} if the parameter is {@code null}
	 * or the old rules are empty.
	 */
	public AssociationRules translate(ItemNames newItemNames)
	{
		if (this.rules==null || this.rules.size()==0 || newItemNames==null)
			return null;
		ItemNames oldItemNames = this.getItemNames();
		List<String> newNames = newItemNames.getNames();
		List<String> oldNames = oldItemNames.getNames();
		/*
		 * translation can not be done if oldNames is not a
		 * subset of newNames 
		 */
		short[] newIndex = new short[oldNames.size()];
		int i = 0;
		/*
		 * get the new numbers (newIndex) of items. Make sure
		 * the new item names is super set of the old names.
		 * Be caution to the spaces in the names -- especially
		 * in the new item names!
		 */
		for (String fieldName : oldNames)
		{
			text(AssociationRules.class, fieldName);
			if (newNames.contains(fieldName.trim()) == false)
				throw new InvalidParameterException(
						"Can not find corresponding field name in new names"
				+ fieldName);
			newIndex[i] = (short) newNames.indexOf(fieldName.trim());
			i++;
		}
		List<Rule> oRules = this.getRules();
		AssociationRules newRules = new AssociationRules();
		newRules.setItemNames(newItemNames);
		Vector<Rule> nRules = new Vector<Rule>();
		/*
		 * Transalte the association rules.
		 */
		for (Rule crule : oRules)
		{
			ItemSet oldSrc = crule.src;
			ItemSet oldDst = crule.dst;
			ItemSet newSrc = new ItemSet();
			ItemSet newDst = new ItemSet();
			short[] oldSrcNumbers = oldSrc.getItems();
			short[] oldDstNumbers = oldDst.getItems();
			for(i = 0; i < oldSrcNumbers.length; i++)
			{
				oldSrcNumbers[i] =  (short) (newIndex[oldSrcNumbers[i] - 1] + 1);
			}
			for(i = 0; i < oldDstNumbers.length; i++)
			{
				oldDstNumbers[i] =  (short) (newIndex[oldDstNumbers[i] - 1] + 1);
			}
			newSrc.addItems(oldSrcNumbers);
			newDst.addItems(oldDstNumbers);
			Rule newCrule = new Rule(newSrc, newDst, crule.conf, crule.sup);
			nRules.add(newCrule);
		}
		newRules.setRules(nRules);
		/*
		 * Tranlate the frequent itemset.
		 */
		if(this.frequentSet != null && this.frequentSet.length > 0)
		{
			ItemSet[] newSets = new ItemSet[this.frequentSet.length];
			int index = 0;
			for (ItemSet fset : this.frequentSet)
			{
				short[] numbers = fset.getItems();
				for(i = 0; i < numbers.length; i++)
				{
					numbers[i] =  (short) (newIndex[numbers[i] - 1] + 1);
				}
				ItemSet itemset = new ItemSet();
				itemset.addItems(numbers);
				newSets[index] = itemset;
				index ++;
			}
			newRules.frequentSet = newSets;
		}
		return newRules;
	}
	
	/**
	 * Sort the association rules stored in current object.
	 * @param method sorting method.
	 * @return an new instance of {@link AssociationRules}, the rules
	 * are sorted, the frequent itemsets and item names are the same 
	 * to current object.<br>
	 * Note: this method create a new instance, and do not modify
	 * the content of the current object.
	 */
	public AssociationRules sort(SortBy method) {
		return MemRuleSelector.getInstance().sortRules(this, method);
	}
	
	/**
	 * Return the first n rules.
	 * @param n the number of rules to get.
	 * @return an new instance of {@link AssociationRules}, the rules
	 * are the first n rules in current object, the frequent itemsets
	 * and item names are the same to the current object. 
	 */
	public AssociationRules getFirstNRules(int n) {
		return MemRuleSelector.getInstance().getFirstNRules(this, n);
	}
	
	/**
	 * Return the last n rules.
	 * @param n the number of rules to get.
	 * @return an new instance of {@link AssociationRules}, the rules
	 * are the last n rules in current object, the frequent itemsets
	 * and item names are the same to the current object. 
	 * 
	 */
	public AssociationRules getLastNRules(int n) {
		return MemRuleSelector.getInstance().getLastNRules(this, n);
	}

	/**
	 * Get the total number of association rules in this object.
	 * @return the total number of association rules.
	 */
	public int getRuleCount() {
		if(this.rules == null)
			return 0;
		else
			return this.rules.size();
	}

	/**
	 * Returns the antecedent of the {@code n}th rule. <br>
	 * Note: {@code n} starts from 0. 	 
	 * @param n index of the rule to access.
	 * @return antecedent of the {@link ItemSet} on success,
	 * {@code null} on failure.
	 */
	public ItemSet getSrc(int n) {
		Rule rule = getRule(n);
		if(rule == null)
			return null;
		else
			return rule.src;
	}

	/**
	 * Returns the consequent of the {@code n}th rule. <br>
	 * Note: {@code n} starts from 0. 	 
	 * @param n index of the rule to access.
	 * @return consequent of the {@link ItemSet} on success,
	 * {@code null} on failure.
	 */
	public ItemSet getDst(int n) {
		Rule rule = getRule(n);
		if(rule == null)
			return null;
		else
			return rule.dst;
	}

	/**
	 * Returns the {@code n}th rule. <br>
	 * Note: {@code n} starts from 0. 	 
	 * @param n index of the rule to access.
	 * @return the {@link ItemSet} wanted on success,
	 * {@code null} on failure.
	 */
	public Rule getRule(int n) {
		if(getRuleCount() > n)
			return this.rules.get(n);
		else
			return null;
	}

	
	
	
	
}
