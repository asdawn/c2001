package net.c2001.dm.ar.ui.ruleviewer;
/**
 * To store the name and value of evaluation indicator of association rules
 * or itemsets.
 * @author Lin Dong
 *
 */
public class ValueAndName {
	
	/**
	 * Values of rules or itemsets. 
	 */
	public float[] value;
	/**
	 * Name of the evaluation indicator.
	 */
	public String name;
	
	/**
	 * Create a {@link ValueAndName} object.
	 * @param value values of rules or itemsets. 
	 * @param name name of the evaluation indicator.
	 */
	public ValueAndName(float[] value, String name) {
		this.value = value;
		this.name = name;
	}
	
	/**
	 * Create a {@link ValueAndName} object.
	 */
	public ValueAndName() {
	}
}
