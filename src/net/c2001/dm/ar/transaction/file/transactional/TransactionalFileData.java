package net.c2001.dm.ar.transaction.file.transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.c2001.dm.ar.garmf.Data;
import net.c2001.dm.ar.garmf.DefaultApriori;
import net.c2001.dm.ar.garmf.ItemNames;
import net.c2001.dm.ar.garmf.ItemSet;
import net.c2001.dm.ar.transaction.file.FileData;

/**
 * Transactional transaction data saved in files. Here we assume the input file
 * is a .CSV file with fields like below:<br>
 * TID, item<br>
 * and the first line is table header.
 * 
 * @author Lin Dong
 * 
 */
public class TransactionalFileData extends FileData {
	private static final long serialVersionUID = 5834863043627285556L;

	private List<String> fn = null;

	private int tc = 0;

	private HashMap<String, Short> map = null;

	/**
	 * Create a {@link TransactionalFileData} with given file. If the given file
	 * is not valid transactional data file, {@link InvalidParameterException}
	 * will be thrown.
	 * 
	 * @param file
	 *            a tabular data file.
	 */
	public TransactionalFileData(File file) {
		this.dataFile = file;
		if (!file.exists() || !file.isFile())
			throw new InvalidParameterException("Invalid input file: "
					+ file.getPath());
		this.miner = new DefaultApriori() {
			private static final long serialVersionUID = 1L;

			// improve the efficiency of support counting
			@Override
			protected List<ItemSet> getFrequent(Data data,
					List<ItemSet> candidates, double threshold) {
				List<ItemSet> fsets = new ArrayList<>();
				try (BufferedReader reader = new BufferedReader(new FileReader(
						dataFile))) {
					// skip the first line
					reader.readLine();
					ItemSet transaction = readTransaction(reader);
					while (transaction != null) {
						for (ItemSet c : candidates) {
							if (transaction.contains(c)) {
								if (c.getSupport() == null)
									c.setSupport(1.0);
								else
									c.setSupport(c.getSupport() + 1);
							}
						}
						transaction = readTransaction(reader);
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
				} catch (Exception e) {
					e.printStackTrace();
					error(e, e.getMessage());
				}
				return fsets;
			}
		};
		this.addElementObject(miner);
		this.getInfo();
		this.names = new ItemNames(fn);
		this.map = new HashMap<>();
		for (short i = 0; i < fn.size(); i++) {
			map.put(fn.get(i), (short) (i + 1));
		}
	}

	@Override
	public double getRecordCount() {
		return this.tc;
	}

	@Override
	public int getItemCount() {
		return fn.size();
	}

	private void getInfo() {
		fn = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
			br.readLine();
			String values = br.readLine();
			String current = "";
			int tc = 0;
			while (values != null) {
				String tid = values.split(",")[0].trim();
				if (!tid.equals(current)) {
					tc++;
					current = tid;
				}
				String item = values.split(",")[1].trim();
				if (!fn.contains(item)) {
					fn.add(item);
				}
				values = br.readLine();
			}
			this.tc = tc;
		} catch (Exception e) {
			e.printStackTrace();
			this.exception(this, e, null);
		}
	}

	@Override
	public double getItemSetCount(ItemSet iset) {
		// In fact here we do not use it.
		return 0;
	}

	@Override
	public ItemSet readTransaction(BufferedReader reader) throws IOException {
		
		ItemSet itemset = new ItemSet();
		// the first line of a transaction
		String line = reader.readLine();
		if (line == null)
			return null;
		String[] values = line.split(",");
		String tid = values[0].trim();
		String item = values[1].trim();
		itemset.addItem(this.map.get(item));
		String current = tid;
		
		do {
			reader.mark(256);
			line = reader.readLine();
			if (line == null)
				break;
			values = line.split(",");
			tid = values[0].trim();
			if(!tid.equals(current)){
				reader.reset();
				break;
			}
			item = values[1].trim();
			itemset.addItem(this.map.get(item));
		}while(true);
		return itemset;
	}
	/*
	public static void main(String[] args) {
		TransactionalFileData d = new TransactionalFileData(new File(
				"c:/tmp/TransactionalFileData.csv"));
		d.addMessageProcessor(DefaultMessageProcessor.getInstance());
		AssociationRules ar = d.mine(0.000001f,0.000001f,null);
		RuleViewer rv = new RuleViewer(null, ar);
		rv.setVisible(true);
		System.exit(0);
	}
	 */
}
