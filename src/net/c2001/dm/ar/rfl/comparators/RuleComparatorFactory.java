package net.c2001.dm.ar.rfl.comparators;

import java.util.Comparator;

import net.c2001.dm.ar.garmf.Rule;
import net.c2001.dm.ar.rfl.base.RuleSelector.SortBy;

/**
 * Factory of comparators of association rules.
 * @author Lin Dong
 *
 */
public final class RuleComparatorFactory {
	/**
	 * Returns a comparator of association rules according to 
	 * given method.
	 * @param method method of comparison, enumeration constant in
	 * {@link SortBy}({@code FloatInc} and {@code FloaDesc} should
	 * not be used here).
	 * @return the required comparator on success, {@code null} if
	 * there's no such comparator or other errors occurred.
	 */
	public static Comparator<Rule> getRuleComparator(SortBy method) {
		switch (method) {
		case ConfDesc:
			return Comparators.getConfDescComparator();
		case ConfInc:
			return Comparators.getConfIncComparator();
		case SupDesc:
			return Comparators.getSupDescComparator();
		case SupInc :
			return Comparators.getSupIncComparator();
		case ItemCountDesc :
			return Comparators.getItemCountDescComparator();
		case ItemCountInc :
			return Comparators.getItemCountIncComparator();
		case SrcItemCountDesc :
			return Comparators.getSrcItemCountDescComparator();
		case SrcItemCountInc :
			return Comparators.getSrcItemCountIncComparator();
		case DstItemCountDesc :
			return Comparators.getDstItemCountDescComparator();
		case DstItemCountInc :
			return Comparators.getDstItemCountIncComparator();		
		case SrcDesc :
			return Comparators.getSrcDescComparator();
		case SrcInc :
			return Comparators.getSrcIncComparator();
		case DstDesc :
			return Comparators.getDstDescComparator();
		case DstInc :
			return Comparators.getDstIncComparator();
		default:
			return null;
		}
	}

	/**
	 * Returns a comparator of association rules according to 
	 * given method.
	 * @param method method method of comparison, enumeration constant in
	 * {@link SortBy}, {@code FloatInc} or {@code FloaDesc}.
	 * @return the required comparator on success, {@code null} if
	 * there's no such comparator or other errors occurred.
	 */
	public static Comparator<FloatAttribute> getFloatComparator(SortBy method) {
		switch (method) {
		case FloatDesc:
			return Comparators.getFloatDescComparator();
		case FloatInc:
			return Comparators.getFloatIncComparator();
		default:
			return null;
		}
	}

}
