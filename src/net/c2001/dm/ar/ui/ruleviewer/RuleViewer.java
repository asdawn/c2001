package net.c2001.dm.ar.ui.ruleviewer;

import java.awt.Frame;

import net.c2001.dm.ar.garmf.AssociationRules;

/**
 * Viewer of association rules. It is a simplified
 * {@link UniViewer}.
 * 
 * @author Lin Dong
 */
public class RuleViewer extends UniViewer
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create a {@link RuleViewer}.
	 * 
	 * @param owner
	 *            the owner of this dialog.
	 * @param r
	 *            {@link AssociationRules} to view.
	 */
	public RuleViewer(Frame owner, AssociationRules r)
	{
		super(owner, r);		
	}
	
}