package net.c2001.dm.ar.transaction.db.fuzzy;

import java.util.ArrayList;
import java.util.List;
import net.c2001.db.SqlO;
import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.garmf.DefaultApriori;
import net.c2001.dm.ar.garmf.ItemNames;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.transaction.db.DBData;
import net.c2001.dm.gmf.Algorithm;

/**
 * {@link DBData} for weighted boolean association rule mining. The first field
 * in the mining table/view should be the weight, and other fields should be
 * membership fields. <br>
 * 
 * @author Lin Dong
 */
public class WeightedFuzzyDBData extends FuzzyDBData {
	
	private double[] m = null;

	private static final long serialVersionUID = 7977396773948557669L;
	/**
	 * name of the weight field.
	 */
	protected String weightField;

	/**
	 * Create a {@link WeightedFuzzyDBData}.
	 * 
	 * @param op
	 *            {@link SqlO} object for accessing the database to use.
	 * @param tableName
	 *            table/view name or SQL query. The table/view or result of
	 *            query should contains weight and membership fileds only.
	 * @param isSQLString
	 *            {@code true} means {@code tableName} is a query string,
	 *            otherwise table/view name.<br>
	 *            Note: If tableName is a query string, a pair of brackets will
	 *            be added to it.
	 */
	public WeightedFuzzyDBData(SqlO op, String tableName, boolean isSQLString) {
		super(op, tableName, isSQLString);
		if(this.m == null){
			this.m = new double[this.fieldCount+1];
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
							if(membership[i]<min)
								min = membership[i];
						}
						// membership[0] is weight
						if(c.getSupport() == null){
							c.setSupport(min*membership[0]);
						}else {
							c.setSupport(c.getSupport()+min*membership[0]);
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
		for (short i = 0; i <= this.fieldCount; i++) {
			this.m[i] = this.o.getDouble(i+1);
		}
		return this.m;
	}


	@Override
	protected void initialize() {
		o.setSQLStr("select * from " + tableName);
		o.getResultSetS();
		names = new ItemNames();
		List<String> n = o.getResultSetColL();
		// the weight field!
		weightField = n.get(0);
		n.remove(0);
		names.setNames(n);
		o.setSQLStr("select sum(" + weightField + ") from " + tableName);
		o.getResultSetS();
		o.next();
		recordCount = o.getDouble(1);
		fieldCount = names.getNames().size();
	}




}
