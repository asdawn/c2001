package net.c2001.dm.ar.rfl.similarity;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import net.c2001.dm.ar.garmf.AssociationRules;
import net.c2001.dm.ar.garmf.ItemSet;

/**
 * Consult matrix.
 * 
 * @author Lin Dong
 * 
 */
public class ConsultMatrix {
	/**
	 * item name --> item number
	 */
	private HashMap<String, Short> nameMap;
	/**
	 * consult matrix
	 */
	private float[][] matrix;

	public ConsultMatrix(AssociationRules r) {
		if (r.getItemNames() == null || r.getItemNames().size() == 0) {
			throw new NullPointerException("Item names are required.");
		}
		List<String> ns = r.getItemNames().getNames();
		createNameMap(ns);
		createMatrix(ns);
	}

	private void createNameMap(List<String> names) {
		this.nameMap = new HashMap<>();
		for (short i = 0; i < names.size(); i++) {
			this.nameMap.put(names.get(i), (short) (i + 1));
		}
	}

	private void createMatrix(List<String> ns) {
		int size = ns.size();
		this.matrix = new float[size][size];
		// self-consult is 1.
		for (int i = 0; i < size; i++) {
			this.matrix[i][i] = 1;
		}
	}

	/**
	 * Get the consult matrix. The default matrix is filled with 0s.
	 * 
	 * @return consult matrix.
	 */
	public float[][] getMatrix() {
		return this.matrix;
	}

	/**
	 * Update the consult matrix according to given items. Class A means the
	 * consult between them are 1.
	 * 
	 * @param items
	 *            a list of item numbers.
	 */
	public void updateClassA(short... items) {
		if (items == null || items.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; i < items.length; i++) {
				int row = items[i] - 1;
				int col = items[j] - 1;
				this.matrix[row][col] = 1;
			}
		}
	}

	/**
	 * Update the consult matrix according to given items. Class A means the
	 * consult between them are 1.
	 * 
	 * @param names
	 *            a list of item names.
	 */
	public void updateClassA(String... names) {
		if (names == null || names.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		short[] numbers = new short[names.length];
		for (int i = 0; i < names.length; i++) {
			numbers[i] = nameMap.get(names[i]);
		}
		updateClassA(numbers);
	}

	/**
	 * Update the consult matrix according to given items. Class B means
	 * consult(a,b)=1-abs(indexA-indexB)/size.
	 * 
	 * @param items
	 *            a list of item numbers.
	 */
	public void updateClassB(short... items) {
		if (items == null || items.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; i < items.length; i++) {
				if (i == j)
					continue;
				int row = items[i] - 1;
				int col = items[j] - 1;
				this.matrix[row][col] = 1 - Math.abs(i - j) / items.length;
			}
		}
	}

	/**
	 * Update the consult matrix according to given items. Class B means
	 * consult(a,b)=1-abs(indexA-indexB)/size.
	 * 
	 * @param names
	 *            a list of item names.
	 */
	public void updateClassB(String... names) {
		if (names == null || names.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		short[] numbers = new short[names.length];
		for (int i = 0; i < names.length; i++) {
			numbers[i] = nameMap.get(names[i]);
		}
		updateClassB(numbers);
	}

	/**
	 * Update the consult matrix according to given items. Class E means
	 * consult(a,b)={(n-1)-2abs(indexA-indexB)}/n-1.
	 * 
	 * @param items
	 *            a list of item numbers.
	 */
	public void updateClassE(short... items) {
		if (items == null || items.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		int n = items.length;
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; i < items.length; i++) {
				if (i == j)
					continue;
				int row = items[i] - 1;
				int col = items[j] - 1;
				this.matrix[row][col] = ((n - 1) - 2 * Math.abs(i - j))
						/ (n - 1);
			}
		}
	}

	/**
	 * Update the consult matrix according to given items. Class E means
	 * consult(a,b)={(n-1)-2abs(indexA-indexB)}/n-1.
	 * 
	 * @param names
	 *            a list of item names.
	 */
	public void updateClassE(String... names) {
		if (names == null || names.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		short[] numbers = new short[names.length];
		for (int i = 0; i < names.length; i++) {
			numbers[i] = nameMap.get(names[i]);
		}
		updateClassE(numbers);
	}

	/**
	 * Update the consult matrix according to given items. Class C means the
	 * consult between them are a number in (0,1).
	 * 
	 * @param c
	 *            the consult value.
	 * @param items
	 *            a list of item numbers.
	 */
	public void updateClassC(float c, short... items) {
		if (items == null || items.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		if (c <= 0 || c >= 1)
			throw new InvalidParameterException(
					"The consult of class C should be in (0,1).");
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; i < items.length; i++) {
				if (i == j)
					continue;
				int row = items[i] - 1;
				int col = items[j] - 1;
				this.matrix[row][col] = c;
			}
		}
	}

	/**
	 * Update the consult matrix according to given items. Class C means the
	 * consult between them are a number in (0,1).
	 * 
	 * @param c
	 *            the consult value.
	 * @param names
	 *            a list of item names.
	 */
	public void updateClassC(float c, String... names) {
		if (names == null || names.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		short[] numbers = new short[names.length];
		for (int i = 0; i < names.length; i++) {
			numbers[i] = nameMap.get(names[i]);
		}
		updateClassC(c, numbers);
	}

	/**
	 * Update the consult matrix according to given items. Class D means the
	 * consult between them are 1/size.
	 * 
	 * @param items
	 *            a list of item numbers.
	 */
	public void updateClassD(short... items) {
		if (items == null || items.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; i < items.length; i++) {
				if (i == j)
					continue;
				int row = items[i] - 1;
				int col = items[j] - 1;
				this.matrix[row][col] = 1 / items.length;
			}
		}
	}

	/**
	 * Update the consult matrix according to given items. Class D means the
	 * consult between them are 1/size.
	 * 
	 * @param names
	 *            a list of item names.
	 */
	public void updateClassD(String... names) {
		if (names == null || names.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		short[] numbers = new short[names.length];
		for (int i = 0; i < names.length; i++) {
			numbers[i] = nameMap.get(names[i]);
		}
		updateClassD(numbers);
	}

	/**
	 * Update the consult matrix according to given items. Class F means the
	 * consult between them are a number in (-1,0).
	 * 
	 * @param c
	 *            the consult value.
	 * @param items
	 *            a list of item numbers.
	 */
	public void updateClassF(float c, short... items) {
		if (items == null || items.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		if (c <= -1 || c >= 0)
			throw new InvalidParameterException(
					"The consult of class C should be in (-1,0).");
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; i < items.length; i++) {
				if (i == j)
					continue;
				int row = items[i] - 1;
				int col = items[j] - 1;
				this.matrix[row][col] = c;
			}
		}
	}

	/**
	 * Update the consult matrix according to given items. Class F means the
	 * consult between them are a number in (-1,0).
	 * 
	 * @param c
	 *            the consult value.
	 * @param names
	 *            a list of item names.
	 */
	public void updateClassF(float c, String... names) {
		if (names == null || names.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		short[] numbers = new short[names.length];
		for (int i = 0; i < names.length; i++) {
			numbers[i] = nameMap.get(names[i]);
		}
		updateClassF(c, numbers);
	}

	/**
	 * Update the consult matrix according to given items. Class G means the
	 * consult between them are -1/size.
	 * 
	 * @param items
	 *            a list of item numbers.
	 */
	public void updateClassG(short... items) {
		if (items == null || items.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; i < items.length; i++) {
				if (i == j)
					continue;
				int row = items[i] - 1;
				int col = items[j] - 1;
				this.matrix[row][col] = -1 / items.length;
			}
		}
	}

	/**
	 * Update the consult matrix according to given items. Class G means the
	 * consult between them are -1/size.
	 * 
	 * @param names
	 *            a list of item names.
	 */
	public void updateClassG(String... names) {
		if (names == null || names.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		short[] numbers = new short[names.length];
		for (int i = 0; i < names.length; i++) {
			numbers[i] = nameMap.get(names[i]);
		}
		updateClassG(numbers);
	}

	/**
	 * Update the consult matrix according to given items. Class H means
	 * consult(a,b)=abs(indexA-indexB)/size -1.
	 * 
	 * @param items
	 *            a list of item numbers.
	 */
	public void updateClassH(short... items) {
		if (items == null || items.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; i < items.length; i++) {
				if (i == j)
					continue;
				int row = items[i] - 1;
				int col = items[j] - 1;
				this.matrix[row][col] = Math.abs(i - j) / items.length - 1;
			}
		}
	}

	/**
	 * Update the consult matrix according to given items. Class B means
	 * consult(a,b)=abs(indexA-indexB)/size -1.
	 * 
	 * @param names
	 *            a list of item names.
	 */
	public void updateClassH(String... names) {
		if (names == null || names.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		short[] numbers = new short[names.length];
		for (int i = 0; i < names.length; i++) {
			numbers[i] = nameMap.get(names[i]);
		}
		updateClassH(numbers);
	}

	/**
	 * Update the consult matrix according to given items. Class I means the
	 * consult between them are -1.
	 * 
	 * @param items
	 *            a list of item numbers.
	 */
	public void updateClassI(short... items) {
		if (items == null || items.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; i < items.length; i++) {
				if (i == j)
					continue;
				int row = items[i] - 1;
				int col = items[j] - 1;
				this.matrix[row][col] = -1;
			}
		}
	}

	/**
	 * Update the consult matrix according to given items. Class I means the
	 * consult between them are -1.
	 * 
	 * @param names
	 *            a list of item names.
	 */
	public void updateClassI(String... names) {
		if (names == null || names.length == 1)
			throw new InvalidParameterException(
					"At least 2 items should be specified.");
		short[] numbers = new short[names.length];
		for (int i = 0; i < names.length; i++) {
			numbers[i] = this.nameMap.get(names[i]);
		}
		updateClassI(numbers);
	}

	/**
	 * Get consult(i,j).
	 * 
	 * @param i
	 *            an item.
	 * @param j
	 *            an item.
	 * @return consult(i,j).
	 */
	public float consult(short i, short j) {
		return this.matrix[i - 1][j - 1];
	}

	/**
	 * Get consult(a,b).
	 * 
	 * @param a
	 *            an item.
	 * @param b
	 *            an item.
	 * @return consult(a,b).
	 */
	public float consult(String a, String b) {
		short i = this.nameMap.get(a);
		short j = this.nameMap.get(b);
		return consult(i, j);
	}
	
	/**
	 * Get consult(X,a).
	 * @param X an itemset.
	 * @param a an item.
	 * @return consult(X,a).
	 */
	public float consult(ItemSet X, short a){
		short[] items = X.getItems();
		float value = 0;
		for (short s : items) {
			float consult = consult(s, a); 
			if(Math.abs(consult)>Math.abs(value)){
				value = consult;
			}
		}
		return value;
	}
	
	/**
	 * Get consult(X,A).
	 * @param X an itemset.
	 * @param A an itemset.
	 * @return consult(X,A).
	 */
	public float consult(ItemSet X, ItemSet A){
		short[] items = A.getItems();
		int count = 0;
		float max = 0;
		for (short a : items) {
			float consult = consult(X, a);
			if(consult < 0)
				count++;
			if(Math.abs(consult)>Math.abs(max)){
				max = consult;
			}
		}
		if(count == 0)
			return Math.abs(max);
		else if(count ==1)
			return -Math.abs(max);
		else
			return 0;					
					
	}

}
