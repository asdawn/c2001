package net.c2001.dm.ar.transaction.db.fuzzy;

import java.util.Arrays;
import java.util.List;

import net.c2001.db.SqlO;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.transaction.db.SQLData;
import net.c2001.dm.ar.transaction.db.bool.WeightedBooleanSQLData;

/**
 * {@link SQLData} for weighted fuzzy association rule mining.
 * The first field in the mining table/view should be the weight, 
 * and other fields should be memberships. <br>
 * Note: here we use SQL query instead of traditional scanning and 
 * subset generating.
 *@author Lin Dong
 */
public class WeightedFuzzySQLData extends WeightedBooleanSQLData
{

	private static final long serialVersionUID = -7823178584171696395L;
	

	/**
	 * Create a {@link WeightedFuzzySQLData}.
	 * @param op {@link SqlO} object for accessing the database to use.
	 * @param tableName table/view name.
	 */
	public WeightedFuzzySQLData(SqlO op, String tableName)
	{
		super(op, tableName);
	}

	@Override
	public double getItemSetCount(ItemSet itemset)
	{
		
		int i;
		double count = 0;
		StringBuilder builder = new StringBuilder();
		builder.append("select ");
		if (!isDerby)
			builder.append('`');
		builder.append(weightField);
		if (!isDerby)
			builder.append('`');
		builder.append(", "); 
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
		double[] mValues = new double[names.size()];
		double weight = 0;
		while (o.next() == true) {
			weight = o.getDouble(1);
			for (i = 0; i < names.size(); i++) {
				mValues[i] = o.getDouble(i + 2);
			}
			Arrays.sort(mValues);
			count += weight * mValues[0];
		}
		mValues = null;
		return count;
	}
}
