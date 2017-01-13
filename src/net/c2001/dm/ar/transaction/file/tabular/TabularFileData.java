package net.c2001.dm.ar.transaction.file.tabular;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.garmf.DefaultApriori;
import net.c2001.dm.ar.garmf.ItemNames;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.transaction.file.FileData;

/**
 * Tabular transaction data saved in files. Here we assume the input file is a
 * .CSV file with fields like below:<br> 
 * TID, ItemName1, ItemName2, ... ItemNamen<br>
 * and the first line is table header. In the data area, 1 stands for {@code true},
 * 0 or empty stands for {@code false}.
 * 
 * @author Lin Dong
 *
 */
public class TabularFileData extends FileData {

	private static final long serialVersionUID = 2022790635594803303L;

	/**
	 * Transaction count.
	 */
	private int n = -1;
	
	private List<String> fn = null;

	private int ic = -1;

	/**
	 * Create a {@link TabularFileData} with given file. If the given file is
	 * not valid tabular data file, {@link InvalidParameterException} will be
	 * thrown.
	 * 
	 * @param file
	 *            a tabular data file.
	 */
	public TabularFileData(File file) {		
		init(file);
	}
	
	protected void init(File file){
		this.dataFile = file;
		if (!file.exists() || !file.isFile())
			throw new InvalidParameterException("Invalid input file: "
					+ file.getPath());
		if (this.getRecordCount() < 1)
			throw new InvalidParameterException(
					"No transaction in the given file.");
		this.miner = new DefaultApriori() {
			private static final long serialVersionUID = 1L;

			// improve the efficiency of support counting
			@Override
			protected List<ItemSet> getFrequent(Data data,
					List<ItemSet> candidates, double threshold) {
				List<ItemSet> fsets = new ArrayList<>();
				try (BufferedReader reader = new BufferedReader(new FileReader(
						dataFile))) {
					//skip the first line
					reader.readLine();
					ItemSet transaction = readTransaction(reader);
					while(transaction != null){
						for (ItemSet c : candidates) {
							if(transaction.contains(c)){
								if(c.getSupport() == null)
									c.setSupport(1.0);
								else
									c.setSupport(c.getSupport()+1);
							}
						}
						transaction = readTransaction(reader);
					}
					for (ItemSet c : candidates) {
						if(c.getSupport() == null)
							continue;
						double support = c.getSupport() / getRecordCount();
						if (support >= threshold) {
							c.setSupport(support);
							fsets.add(c);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					error(e, e.getMessage());
				}
				return fsets;
			}
		};
		this.addElementObject(miner);
		this.getFieldNames();
		this.names = new ItemNames(fn);
	}

	@Override
	public double getRecordCount() {
		return countLines(dataFile) - 1;
	}

	/**
	 * Get the line count of current file.
	 * @param dataFile data file.
	 * @return line count.
	 */
	protected int countLines(File dataFile) {
		if (n == -1) {
			int lc = 0;
			try (BufferedReader br = new BufferedReader(
					new FileReader(dataFile))) {
				while (br.readLine() != null)
					lc++;
				n = lc;
			} catch (Exception e) {
				this.exception(this, e, null);
			}

		}
		return n;
	}

	@Override
	public int getItemCount() {
		if (ic == -1) {
			int c = 0;
			try (BufferedReader br = new BufferedReader(
					new FileReader(dataFile))) {
				c = br.readLine().split(",").length;
				ic = c;
			} catch (Exception e) {
				this.exception(this, e, null);
			}
		}
		return ic - 1;
	}

	/**
	 * Read field names.
	 */
	protected void getFieldNames() {
		if (fn == null) {
			fn = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(
					new FileReader(dataFile))) {
				String[] fields = br.readLine().split(",");
				for(int i=1;i<fields.length;i++){
					fn.add(fields[i].trim());
				}
			} catch (Exception e) {
				this.exception(this, e, null);
			}
		}
	}

	@Override
	public double getItemSetCount(ItemSet iset) {
		// In fact here we do not use it.
		return 0;
	}

	@Override
	public ItemSet readTransaction(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		if (line == null)
			return null;
		String[] values = line.split(",");
		ItemSet itemset = new ItemSet();
		for (short i = 1; i < values.length; i++) {
			if (values[i].trim().equals("1")) {
				itemset.addItem(i);
			}
		}
		return itemset;
	}
	/*
	public static void main(String[] args) {
		TabularFileData d = new TabularFileData(new File(
				"c:/tmp/TabularFileData.csv"));
		d.addMessageProcessor(DefaultMessageProcessor.getInstance());
		AssociationRules ar = d.mine(0.000001f,0.000001f,null);
		RuleViewer rv = new RuleViewer(null, ar);
		rv.setVisible(true);
		System.exit(0);
	}
	*/


}
