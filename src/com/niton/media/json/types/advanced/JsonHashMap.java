package com.niton.media.json.types.advanced;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.StringInputStream;
import com.niton.media.json.types.JsonSerialArray;

/**
 * This is the JsonHashMap Class
 * @author Nils
 * @version 2018-07-02
 */
public class JsonHashMap<K,V> extends JsonValue<HashMap<K,V>> {
	public JsonHashMap() {
	}

	public JsonHashMap(HashMap<K, V> value) {
		super(value);
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		JsonSerialArray<HashPair> list = new JsonSerialArray<>();
		ArrayList<HashPair> pairs = new ArrayList<>();
		for(K key : getValue().keySet()) {
			HashPair pair = new HashPair();
			pair.setKey(key);
			pair.setValue(getValue().get(key));
			pairs.add(pair);
		}
		list.setValue(pairs.toArray());
		return list.getJson();
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void readNext(StringInputStream sis) throws IOException {
		JsonSerialArray<HashPair> list = new JsonSerialArray<>();
		list.setToRead(Object.class);
		list.readNext(sis);
		setValue(new HashMap<>());
		for(Object p : JsonSerialArray.getArray(list.getValue())) {
			HashPair pair = (HashPair) p;
			getValue().put((K)pair.getKey(), (V) pair.getValue());
		}
	}
}

