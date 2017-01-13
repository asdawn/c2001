package net.c2001.dm.ar.transaction.db.bool;

import java.util.List;

import net.c2001.db.DerbyO;
import net.c2001.db.SqlO;
import net.c2001.dm.ar.garmf.ItemNames;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.transaction.db.SQLData;

/**
 * {@link SQLData} for boolean association rule mining. A table or view that
 * contains only boolean fields is suggested, though queries are okay.<br>
 * Note: here we use SQL query instead of traditional scanning and subset
 * generating.
 * 
 * @author Lin Dong
 */
public class BooleanSQLData extends SQLData {
	private static final long serialVersionUID = -5745885425001139287L;
	/**
	 * True flag in SQL. The default is "true", but some database systems use
	 * other values.
	 */
	protected String trueFlag = "true";

	/**
	 * Derby do not support '`'.
	 */
	protected boolean isDerby = false;

	/**
	 * Create a {@link BooleanSQLData}.
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
	public BooleanSQLData(SqlO op, String tableName, boolean isSQLString) {
		super(op, tableName, isSQLString);
		if (op instanceof DerbyO)
			isDerby = true;
	}

	@Override
	public double getItemSetCount(ItemSet is) {
		int i;
		StringBuffer buffer = new StringBuffer();
		buffer.append("select count(*) from ");
		buffer.append(tableName);
		buffer.append(" where ");
		List<String> names = this.names.getName(is);
		for (i = 0; i < names.size() - 1; i++) {
			if (!isDerby)
				buffer.append('`');
			buffer.append(names.get(i));
			if (!isDerby)
				buffer.append('`');
			buffer.append('=');
			buffer.append(trueFlag);
			buffer.append(" and ");
		}
		if (!isDerby)
			buffer.append('`');
		buffer.append(names.get(i));
		if (!isDerby)
			buffer.append('`');
		buffer.append("=");
		buffer.append(trueFlag);
		o.setSQLStr(buffer.toString());
		o.getResultSetS();
		boolean ok = o.next();
		if (ok == true)
			return o.getInt(1);
		else
			return 0;
	}

	/**
	 * Set the true flag in queries.
	 * 
	 * @param trueFlag
	 *            the true flag, a simple string expression, for example "true"
	 *            or "-1".
	 */
	public void setTrueFlag(String trueFlag) {
		this.trueFlag = trueFlag;
	}

	/**
	 * Set the true flag used in queries by this class..
	 * 
	 * @return the true flag.
	 */
	public String getTrueFlag() {
		return trueFlag;
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

}
