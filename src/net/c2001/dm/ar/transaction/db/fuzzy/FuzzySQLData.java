package net.c2001.dm.ar.transaction.db.fuzzy;

import java.util.Arrays;
import java.util.List;
import net.c2001.db.SqlO;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.transaction.db.SQLData;
import net.c2001.dm.ar.transaction.db.bool.BooleanSQLData;

/**
 * {@link SQLData} for fuzzy association rule mining.
 * A table or view that contains only membership fields is
 * suggested, though queries are okay.<br>
 * Note: here we use SQL query instead of traditional 
 * scanning and subset generating.
 *@author Lin Dong
 */
public class FuzzySQLData extends BooleanSQLData
{

	private static final long serialVersionUID = -69583049340199935L;

	/**
	 * Create a {@link FuzzySQLData}.
	 * @param op {@link SqlO} object for accessing the database to use.
	 * @param tableName table/view name or SQL query. The table/view
	 * or result of query should contains membership fields only.
	 * @param isSQLString  {@code true} means {@code tableName} is a 
	 * query string, otherwise table/view name.<br>
	 * Note: If tableName is a query string, a pair of brackets will 
	 * be added to it.
	 */
	public FuzzySQLData(SqlO op, String tableName, boolean isSQLString)
	{
		super(op, tableName, isSQLString);
	}

	@Override
	public double getItemSetCount(ItemSet itemset)
	{
		int i;
		double count = 0;
		StringBuilder builder = new StringBuilder();
		builder.append("select "); 
		List<String> names = this.names.getName(itemset);		
		for(i=0;i<names.size()-1;i++)
		{
			if (!isDerby)
				builder.append('`');
			builder.append(names.get(i));
			if (!isDerby)
				builder.append('`');
			builder.append(',');		
		}
		if (!isDerby)
			builder.append('`');
		builder.append(names.get(i));
		if (!isDerby)
			builder.append('`');
		builder.append(" from ");
		builder.append(tableName);
		builder.append(" where ");
		for(i=0;i<names.size()-1;i++)
		{
			if (!isDerby)
				builder.append('`');
			builder.append(names.get(i));
			if (!isDerby)
				builder.append('`');
			builder.append('>');
			builder.append(0);
			builder.append(" and ");
		}
		if (!isDerby)
			builder.append('`');
		builder.append(names.get(i));
		if (!isDerby)
			builder.append('`');
		builder.append(">");
		builder.append(0);
		o.setSQLStr(builder.toString());
		o.getResultSetS();			
		/**
		 * Here we use the minimum norm.
		 * See Guoqing Chen's article for details.
		 */
		double[] mValues = new double[names.size()];
		while (o.next() == true) {
			for (i = 0; i < names.size(); i++) {
				mValues[i] = o.getDouble(i + 1);
			}
			Arrays.sort(mValues);
			count += mValues[0];
		}
		mValues = null;
		return count;
	}
	
	
}
