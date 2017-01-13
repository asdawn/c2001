package net.c2001.dm.ar.rfl.comparators;

import java.util.Comparator;

import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.garmf.Rule;



/**
 * Comparators of association rules.
 * @author Lin Dong
 *
 */
public class Comparators {
	/**
	 * Support descending.
	 */
	private static Comparator<Rule> supDescComparator =null;
	
	/**
	 * Returns the support descending {@link Comparator}.
	 * @return {@link Comparator}.
	 */
	public static Comparator<Rule> getSupDescComparator() {
		if(supDescComparator ==null) {
			supDescComparator = new Comparator<Rule>(){
				@Override
				public int compare(Rule rule1, Rule rule2) {
					return Double.compare(rule2.sup, rule1.sup);										
				}};
		}
		return supDescComparator;
	}
	
	/**
	 * Support ascending.
	 */
	private static Comparator<Rule> supIncComparator =null;
	
	/**
	 * Returns the support ascending {@link Comparator}.
	 * @return {@link Comparator}.
	 */	
	public static Comparator<Rule> getSupIncComparator() {
		if(supIncComparator ==null) {
			supIncComparator = new Comparator<Rule>(){
				@Override
				public int compare(Rule rule1, Rule rule2) {
					return Double.compare(rule1.sup, rule2.sup);							
				}};
		}
		return supIncComparator;
	}
	
	/**
	 * Confidence descending.
	 */
	private static Comparator<Rule> confDescComparator =null;
	/**
	 * Returns the confidence descending {@link Comparator}.
	 * @return {@link Comparator}.
	 */
	public static Comparator<Rule> getConfDescComparator() {
		if(confDescComparator == null) {
			confDescComparator = new Comparator<Rule>(){
				@Override
				public int compare(Rule rule1, Rule rule2) {
					return Double.compare(rule2.conf, rule1.conf);							
				}};
		}
		return confDescComparator;
	}

	/**
	 * Confidence ascending.
	 */
	private static Comparator<Rule> confIncComparator =null;
	
	/**
	 * Returns the confidence ascending {@link Comparator}.
	 * @return {@link Comparator}.
	 */
	public static Comparator<Rule> getConfIncComparator() {
		if(confIncComparator == null) {
			confIncComparator = new Comparator<Rule>() {
				@Override
				public int compare(Rule rule1, Rule rule2) {
					return Double.compare(rule1.conf, rule2.conf);						
				}};
				
		}
		return confIncComparator;
	}

	/**
	 * Item count in rule, descending.
	 */
	private static Comparator<Rule> itemCountDescComparator =null; 
	
	/**
	 * Returns the {@link Comparator} which can be used to sort
	 * rules according to item count in rule in descending order.
	 * @return {@link Comparator}.
	 */
	public static Comparator<Rule> getItemCountDescComparator() {
		if(itemCountDescComparator == null) {
			itemCountDescComparator = new Comparator<Rule>(){

				@Override
				public int compare(Rule rule1, Rule rule2) {
					return Integer.compare(
							rule2.src.getItemCount()+rule2.dst.getItemCount(),
							rule1.src.getItemCount()+rule1.dst.getItemCount());
				}};
		}
			
		return itemCountDescComparator;
	}

	/**
	 * Item count in rule, ascending.
	 */
	private static Comparator<Rule> itemCountIncComparator =null; 
	
	/**
	 * Returns the {@link Comparator} which can be used to sort
	 * rules according to item count in rule in ascending order.
	 * @return {@link Comparator}.
	 */
	public static Comparator<Rule> getItemCountIncComparator() {
		if(itemCountIncComparator == null) {
			itemCountIncComparator = new Comparator<Rule>(){

				@Override
				public int compare(Rule rule1, Rule rule2) {
					return Integer.compare(
							rule1.src.getItemCount()+rule1.dst.getItemCount(),
							rule2.src.getItemCount()+rule2.dst.getItemCount());
				}};
		}
		return itemCountIncComparator;
	}

	/**
	 * Item count in antecedent, descending.
	 */
	private static Comparator<Rule> srcItemCountDescComparator =null;
	
	/**
	 * Returns the {@link Comparator} which can be used to sort
	 * rules according to item count in antecedent in descending order.
	 * @return {@link Comparator}.
	 */
	public static Comparator<Rule> getSrcItemCountDescComparator() {
		if(srcItemCountDescComparator == null) {
			srcItemCountDescComparator = new Comparator<Rule>(){

				@Override
				public int compare(Rule rule1, Rule rule2) {
					return Integer.compare(
							rule2.src.getItemCount(), rule1.src.getItemCount());
				}};
		}
		return srcItemCountDescComparator;
	}

	/**
	 * Item count in antecedent, ascending.
	 */
	private static Comparator<Rule> srcItemCountIncComparator =null;
	
	/**
	 * Returns the {@link Comparator} which can be used to sort
	 * rules according to item count in antecedent in ascending order.
	 * @return {@link Comparator}.
	 */
	public static Comparator<Rule> getSrcItemCountIncComparator() {
		if(srcItemCountIncComparator == null) {
			srcItemCountIncComparator = new Comparator<Rule>(){

				@Override
				public int compare(Rule rule1, Rule rule2) {
					return Integer.compare(
							rule1.src.getItemCount(), rule2.src.getItemCount());
				}};
		}
		return srcItemCountIncComparator;
	}

	/**
	 * Item count in consequent, descending.
	 */
	private static Comparator<Rule> dstItemCountDescComparator =null;
	
	/**
	 * Returns the {@link Comparator} which can be used to sort
	 * rules according to item count in consequent in descending order.
	 * @return {@link Comparator}.
	 */
	public static Comparator<Rule> getDstItemCountDescComparator() {
		if(dstItemCountDescComparator == null) {
			dstItemCountDescComparator = new Comparator<Rule>(){

				@Override
				public int compare(Rule rule1, Rule rule2) {
					return Integer.compare(
							rule2.dst.getItemCount(), rule1.dst.getItemCount());
				}};
		}
		return dstItemCountDescComparator;
	}

	/**
	 * Item count in consequent, ascending.
	 */
	private static Comparator<Rule> dstItemCountIncComparator =null;
	
	/**
	 Returns the {@link Comparator} which can be used to sort
	 * rules according to item count in consequent in ascending order.
	 * @return {@link Comparator}.
	 */
	public static Comparator<Rule> getDstItemCountIncComparator() {
		if(dstItemCountIncComparator == null) {
			dstItemCountIncComparator = new Comparator<Rule>(){

				@Override
				public int compare(Rule rule1, Rule rule2) {
					return Integer.compare(
							rule1.dst.getItemCount(), rule2.dst.getItemCount());
				}};
		}
		return dstItemCountIncComparator;
	}

	/**
	 * Antecedent descending.
	 */
	private static Comparator<Rule> srcDescCountDesComparator =null;
	
	/**
	 * Returns the antecedent descending {@link Comparator}.
	 * @return {@link Comparator}.
	 */
	public static Comparator<Rule> getSrcDescComparator() {
		if(srcDescCountDesComparator == null) {
			srcDescCountDesComparator = new Comparator<Rule>(){

				@Override
				public int compare(Rule rule1, Rule rule2) {
					return ItemSet.compare(rule2.src, rule1.src);
				}};
		}
		return srcDescCountDesComparator;
	}

	/**
	 * Antecedent ascending.
	 */
	private static Comparator<Rule> srcIncCountDesComparator =null;
	
	/**
	 * Returns the antecedent ascending {@link Comparator}.
	 * @return {@link Comparator}.
	 */
	public static Comparator<Rule> getSrcIncComparator() {
		if(srcIncCountDesComparator == null) {
			srcIncCountDesComparator = new Comparator<Rule>(){

				@Override
				public int compare(Rule rule1, Rule rule2) {
					return ItemSet.compare(rule1.src, rule2.src);
				}};
		}
		return srcIncCountDesComparator;
	}

	/**
	 * Consequent descending.
	 */
	private static Comparator<Rule> dstDescComparator =null;
	
	/**
	 * Returns the consequent descending {@link Comparator}.
	 * @return {@link Comparator}.
	 */
	public static Comparator<Rule> getDstDescComparator() {
		if(dstDescComparator == null) {
			dstDescComparator = new Comparator<Rule>(){

				@Override
				public int compare(Rule rule1, Rule rule2) {
					return ItemSet.compare(rule2.dst, rule1.dst);
				}};
		}
		return dstDescComparator;
	}

	/**
	 * Consequent ascending.
	 */
	private static Comparator<Rule> dstIncComparator =null;

	/**
	 * Returns the consequent ascending {@link Comparator}.
	 * @return {@link Comparator}.
	 */
	public static Comparator<Rule> getDstIncComparator() {
		if(dstIncComparator == null) {
			dstIncComparator = new Comparator<Rule>(){

				@Override
				public int compare(Rule rule1, Rule rule2) {
					return ItemSet.compare(rule1.dst, rule2.dst);
				}};
		}
		return dstIncComparator;
	}
	
	/**
	 * Float value(evaluation indicator) descending.
	 */
	private static Comparator<FloatAttribute> floatDescComparator = null;

	/**
	 * Returns the float value descending {@link Comparator}. This comparator
	 * can be use for compare rules according to evaluation other than
	 * support and confidence. 
	 * @return {@link Comparator}.
	 */
	public static Comparator<FloatAttribute> getFloatDescComparator() {
		if(floatDescComparator == null) {
			floatDescComparator = new Comparator<FloatAttribute>(){

				@Override
				public int compare(FloatAttribute value1, FloatAttribute value2) {
					return Float.compare(value2.value, value1.value);
				}};
		}
		return floatDescComparator;
	}
	
	/**
	 * Float value(evaluation indicator) ascending.
	 */
	private static Comparator<FloatAttribute> floatIncComparator = null;

	/**
	 * Returns the float value ascending {@link Comparator}. This comparator
	 * can be use for compare rules according to evaluation other than
	 * support and confidence. 
	 * @return {@link Comparator}.
	 */
	public static Comparator<FloatAttribute> getFloatIncComparator() {
		if(floatIncComparator == null) {
			floatIncComparator = new Comparator<FloatAttribute>(){

				@Override
				public int compare(FloatAttribute value1, FloatAttribute value2) {
					return Float.compare(value1.value, value2.value);
				}};
		}
		return floatIncComparator;
	}
	
}

