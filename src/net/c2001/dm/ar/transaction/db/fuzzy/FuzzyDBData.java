package net.c2001.dm.ar.transaction.db.fuzzy;

import java.util.ArrayList;
import java.util.List;

import net.c2001.db.SqlO;
import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.garmf.DefaultApriori;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.transaction.db.DBData;
import net.c2001.dm.ar.transaction.db.bool.BooleanDBData;
import net.c2001.dm.gmf.Algorithm;

/**
 * {@link DBData} for fuzzy association rule mining. A table or view that
 * contains only membership fields is suggested, though queries are okay.
 * 
 * @author Lin Dong
 */
public class FuzzyDBData extends BooleanDBData {

	private static final long serialVersionUID = 7977396773948557669L;
	
	private double[] m = null;
	

	/**
	 * Create a {@link FuzzyDBData}.
	 * 
	 * @param op
	 *            {@link SqlO} object for accessing the database to use.
	 * @param tableName
	 *            table/view name or SQL query. The table/view or result of
	 *            query should contains membership fields only.
	 * @param isSQLString
	 *            {@code true} means {@code tableName} is a query string,
	 *            otherwise table/view name.<br>
	 *            Note: If tableName is a query string, a pair of brackets will
	 *            be added to it.
	 */
	public FuzzyDBData(SqlO op, String tableName, boolean isSQLString) {
		super(op, tableName, isSQLString);
		if(this.m == null){
			this.m = new double[this.fieldCount];
		}
	}

	@Override
	protected Algorithm<AssociationRules> getMiner() {
		return new DefaultApriori() {
			private static final long serialVersionUID = 7004715576939584255L;

			@Override
			protected List<ItemSet> getFrequent(Data data,
					List<ItemSet> candidates, double threshold) {
				List<ItemSet> fsets = new ArrayList<>();
				rewind();
				double[] membership = readMembership();
				while (membership != null) {
					for (ItemSet c : candidates) {
						short[] items = c.getItems();
						double min = 1;
						for (short i : items) {
							// fuzzy norm MIN
							if(membership[i-1]<min)
								min = membership[i-1];
						}
						if(c.getSupport() == null){
							c.setSupport(min);
						}else {
							c.setSupport(c.getSupport()+min);
						}						
					}
					membership = readMembership();
				}
				for (ItemSet c : candidates) {
					if (c.getSupport() == null)
						continue;
					double support = c.getSupport() / getRecordCount();
					if (support >= threshold) {
						c.setSupport(support);
						fsets.add(c);
					}
				}
				return fsets;
			}
		};
	}

	/**
	 * Read the next transaction from membership table.
	 * 
	 * @return membership values on success, {@code null} on failure or end of table.
	 */
	protected double[] readMembership() {
		if (!this.o.next()){
			return null;
		}
		for (short i = 1; i <= this.fieldCount; i++) {
			this.m[i-1] = this.o.getDouble(i);
		}
		return this.m;
	}



}
