package com.niton.media.json.types.advanced;

/**
 * This is the HashPair Class
 * @author Nils
 * @version 2018-07-02
 */
class HashPair {
	private Object key;
	private Object value;
	/**
	 * @return the key
	 */
	public Object getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(Object key) {
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	public HashPair(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}
	public HashPair() {

	}
}

