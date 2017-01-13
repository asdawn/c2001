package net.c2001.dm.ar.transaction.db.bool;

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
 * in the mining table/view should be the weight, and other fields should be of
 * boolean type. <br>
 * Note: here we use SQL query instead of traditional scanning and subset
 * generating.
 * 
 * @author Lin Dong
 */
public class WeightedBooleanDBData extends BooleanDBData {

	private static final long serialVersionUID = 7977396773948557669L;
	/**
	 * name of the weight field.
	 */
	protected String weightField;

	/**
	 * Create a {@link WeightedBooleanDBData}.
	 * 
	 * @param op
	 *            {@link SqlO} object for accessing the database to use.
	 * @param tableName
	 *            table/view name or SQL query. The table/view or result of
	 *            query should contains boolean fields only.
	 * @param isSQLString
	 *            {@code true} means {@code tableName} is a query string,
	 *            otherwise table/view name.<br>
	 *            Note: If tableName is a query string, a pair of brackets will
	 *            be added to it.
	 */
	public WeightedBooleanDBData(SqlO op, String tableName, boolean isSQLString) {
		super(op, tableName, isSQLString);
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
				ItemSet transaction = readTransaction();
				while (transaction != null) {
					for (ItemSet c : candidates) {
						if (transaction.contains(c)) {
							if (c.getSupport() == null)
								c.setSupport(transaction.getSupport());
							else
								c.setSupport(c.getSupport()
										+ transaction.getSupport());
						}
					}
					transaction = readTransaction();
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
	 * Read the next transaction from table.
	 * 
	 * @return {@link ItemSet} on success, {@code null} if there's nothing else.
	 */
	protected ItemSet readTransaction() {

		if (!this.o.next()) {
			return null;
		}
		ItemSet t = new ItemSet();
		double weight = this.o.getDouble(1);
		for (short i = 1; i <= this.fieldCount; i++) {
			if (this.o.getBoolean(i+1))
				t.addItem(i);
		}
		// System.out.println(t);
		t.setSupport(weight);
		return t;
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
