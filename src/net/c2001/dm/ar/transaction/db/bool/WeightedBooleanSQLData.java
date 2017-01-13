package net.c2001.dm.ar.transaction.db.bool;

import java.util.List;
import net.c2001.db.SqlO;
import net.c2001.dm.ar.garmf.ItemNames;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.transaction.db.SQLData;


/**
 * {@link SQLData} for weighted boolean association rule mining.
 * The first field in the mining table/view should be the weight, 
 * and other fields should be of boolean type. <br>
 * Note: here we use SQL query instead of traditional scanning and 
 * subset generating.
 *@author Lin Dong
 */
public class WeightedBooleanSQLData extends BooleanSQLData
{
	private static final long serialVersionUID = 7921706719486404696L;
	
	private double recordCount = 0;
	
	/**
	 * name of the weight field.
	 */
	protected String weightField;

	/**
	 * Create a {@link WeightedBooleanSQLData}.
	 * @param op {@link SqlO} object for accessing the database to use.
	 * @param tableName table/view name.
	 */
	public WeightedBooleanSQLData(SqlO op, String tableName)
	{
		super(op, tableName, false);
	}
	
	@Override
	public double getRecordCount() {
		//0 is not allowed!
		if(recordCount == 0) {
			this.o.setSQLStr("select sum("+weightField+") from "+tableName);
			o.getResultSetS();
			o.next();
			recordCount = o.getDouble(1);
		}
		return recordCount;
	};

	@Override
	public double getItemSetCount(ItemSet itemset)
	{
		int i;
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(");
		if (!isDerby)
			builder.append('`');
		builder.append(weightField);
		if (!isDerby)
			builder.append('`');
		builder.append(") from ");
		builder.append(tableName);
		builder.append(" where ");
		List<String> names = this.names.getName(itemset);		
		for(i=0;i<names.size()-1;i++)
		{
			if (!isDerby)
				builder.append('`');
			builder.append(names.get(i));
			if (!isDerby)
				builder.append('`');
			builder.append('=');
			builder.append(trueFlag);
			builder.append(" and ");
		}
		if (!isDerby)
			builder.append('`');
		builder.append(names.get(i));
		if (!isDerby)
			builder.append('`');
		builder.append("=");
		builder.append(trueFlag);
		o.setSQLStr(builder.toString());
		o.getResultSetS();
		boolean ok = o.next();
		if(ok == true) 
			return o.getDouble(1);
		else 
			return 0;	
	}

	@Override
	protected void initialize()
	{
		o.setSQLStr("select * from "+tableName);
		o.getResultSetS();
		names = new ItemNames();
		List<String> n = o.getResultSetColL();
		// the weight field!
		weightField = n.get(0);
		n.remove(0);
		names.setNames(n);
		getRecordCount();		
		fieldCount = names.getNames().size();	
	}		
}
