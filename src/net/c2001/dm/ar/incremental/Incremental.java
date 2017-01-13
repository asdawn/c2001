package net.c2001.dm.ar.incremental;

import net.c2001.dm.ar.garmf.AssociationRules;


/**
 * Updater for mined rules.
 * 
 * @author Lin Dong
 * 
 */
public interface Incremental {

	/**
	 * Update association rules using new minimum confidence threshold. When the
	 * threshold is larger than before, selections is enough, otherwise repeat
	 * the rule generation progress.
	 * 
	 * @param rules
	 *            old rules to update.
	 * @param newConfMin
	 *            the new minimum confidence threshold to use.
	 * @return the updated {@link AssociationRules} on success (directly update
	 *         the input object), {@code null} on failure.
	 */
	public AssociationRules updateConfmin(AssociationRules rules,
			double newConfMin);

}
