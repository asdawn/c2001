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
 * {@link DBData} for boolean association rule mining. A table or view that
 * contains only boolean fields is suggested, though queries are okay.
 * 
 * @author Lin Dong
 */
public class BooleanDBData extends DBData {

	private static final long serialVersionUID = 7977396773948557669L;
	

	/**
	 * Create a {@link BooleanDBData}.
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
	public BooleanDBData(SqlO op, String tableName, boolean isSQLString) {
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
								c.setSupport(1.0);
							else
								c.setSupport(c.getSupport() + 1);
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
		
		if (!this.o.next()){
			return null;
		}
		ItemSet t = new ItemSet();
		for (short i = 1; i <= this.fieldCount; i++) {
			if (this.o.getBoolean(i))
				t.addItem(i);
		}
		//System.out.println(t);
		return t;
	}

	protected void rewind() {
		this.o.setSQLStr("select * from " + tableName);
		this.o.getResultSetS();
	}

	@Override
	public int getItemCount() {
		return fieldCount;
	}

	@Override
	public double getRecordCount() {
		return recordCount;
	}

	@Override
	protected void initialize() {
		o.setSQLStr("select * from " + tableName);
		o.getResultSetS();
		names = new ItemNames();
		names.setNames(o.getResultSetColL());
		recordCount = (int) o.getCount();
		fieldCount = names.getNames().size();
	}

	@Override
	public double getItemSetCount(ItemSet iset) {
		// In fact here we do not use it.
		return 0;
	}


}
