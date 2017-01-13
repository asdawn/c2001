package net.c2001.dm.ar.ui.ruleviewer;

import java.util.List;

import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.ItemNames;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.garmf.Rule;


/**
 * Resolve association rules in {@link Rule} and {@link AssociationRules}. See
 * also {@link UniViewer}.
 * 
 * @author Lin Dong
 */
public class RuleResolver {
	
	/**
	 * Get item names according to the given itemset.
	 * 
	 * @param in
	 *            {@link ItemSet}.
	 * @param names
	 *            {@link ItemNames}.
	 * @return names of items in the given itemset on success, {@code null} on
	 *         failure.
	 */
	private String resolve(ItemSet in, ItemNames names) {
		StringBuilder temp = new StringBuilder();
		List<String> v = names.getName(in);
		int i = 0;
		for (i = 0; i < (v.size() - 1); i++) {
			temp.append(v.get(i));
			temp.append(' ');
		}
		temp.append(v.get(i));
		return temp.toString();
	}

	/**
	 * Returns the {@code Object[]} representation of an association rule
	 * 
	 * @param r
	 *            {@link AssociationRules}.
	 * @param id
	 *            the index of rule.
	 * @return {@code Object[]} stores an association rule.
	 */
	public Object[] resolveRuleObj(AssociationRules r, int id) {
		return resolveRuleObj(r, null, id);
	}

	/**
	 * Returns the {@code Object[]} representation of an association rule.
	 * 
	 * @param r
	 *            {@link AssociationRules}.
	 * @param vn
	 *            value and names of evaluation indicators.
	 * @param id
	 *            the index of rule.
	 * @return {@code Object[]} stores an association rule.
	 */
	public Object[] resolveRuleObj(AssociationRules r, ValueAndName[] vn, int id) {
		int vcount = 0;
		if (vn != null)
			vcount = vn.length;
		Rule rule = r.getRule(id);
		ItemNames names = r.getItemNames();
		Object[] rd = new Object[5 + vcount];
		rd[0] = (id + 1);		
		rd[1] = this.resolve(rule.src, names);
		rd[2] = this.resolve(rule.dst, names);
		rd[3] = rule.sup;
		rd[4] = rule.conf;
		for (int i = 5; i < 5 + vcount; i++) {
			rd[i] = vn[i - 5].value[id];
		}
		return rd;
	}	
	
	/**
	 * Change the {@code id}th itemset to {@code Object[]}.
	 * 
	 * @param r
	 *            {@link AssociationRules}.
	 * @param id
	 *            the index of itemset.
	 * @return {@code Object[]} stores an itemset.
	 */
	public Object[] resolveItemsetObj(AssociationRules r, int id) {
		Object[] rd = new Object[3];
		rd[0] = (id + 1);
		ItemSet[] fset = r.getFrequentItemset();		
		rd[1] = this.resolve(fset[id], r.getItemNames());
		rd[2] = fset[id].getSupport();
		return rd;
	}
	
	/**
	 * Returns the string representation of an association rule.
	 * 
	 * @param r
	 *            association rule.
	 * @param names
	 *            names of items.
	 * @return string expression of the rule.
	 */
	public String resolveRuleStr(Rule r, ItemNames names) {
		StringBuilder temp = new StringBuilder();
		temp.append(this.resolve(r.src, names));
		temp.append(" ==> ");
		temp.append(this.resolve(r.dst, names));
		return temp.toString();
	}

	/**
	 * Get the string representation of the {@code id}th association rule.
	 * 
	 * @param r
	 *            {@link AssociationRules}.
	 * @param id
	 *            index of the rule.
	 * @return string expression of the rule.
	 */
	public String resolveRuleStr(AssociationRules r, int id) {
		return this.resolveRuleStr(r.getRules().get(id), r.getItemNames());
	}

}
