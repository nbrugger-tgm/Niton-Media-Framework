package com.niton.media.json.basic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.niton.media.json.JsonType;
import com.niton.media.json.io.StringInputStream;

/**
 * This is the JsonArray Class
 * 
 * @author Nils
 * @version 2018-06-05
 */
public class JsonArray<T extends JsonValue<?>> extends JsonValue<ArrayList<T>> implements Collection<T> {
	public JsonArray(ArrayList<T> array) {
		super(array);
	}
	public JsonArray() {
		super(new ArrayList<>());
	}
	public JsonArray(T[] value) {
		ArrayList<T> array = new ArrayList<>(value.length);
		for (int i = 0; i < value.length; i++) {
			T t = value[i];
			array.add(t);
		}
		setValue(array);
	}

	/**
	 * @see com.niton.media.json.basic.JsonValue#getJson()
	 */
	@Override
	public String getJson() {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		builder.append('\n');
		for (JsonValue<?> value : getValue()) {
			String[] subValue = value.getJson().split("\n");
			for (int i = 0; i < subValue.length; i++) {
				String string = subValue[i];
				// builder.append('\n');
				builder.append('\t');
				builder.append(string);
				if (i + 1 != subValue.length)
					builder.append('\n');
			}
			if (value != getValue().get(size() - 1)) {
				builder.append(',');
			}
			builder.append('\n');
		}
		builder.append(']');
		return builder.toString();
	}

	public T get(int i) {
		return getValue().get(i);
	}

	/**
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size() {
		return getValue().size();
	}

	/**
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return getValue().isEmpty();
	}

	/**
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object paramObject) {
		return getValue().contains(paramObject);
	}

	/**
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return getValue().iterator();
	}

	/**
	 * @see java.util.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		return getValue().toArray();
	}

	/**
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 */
	@Override
	public <E> E[] toArray(E[] paramArrayOfT) {
		return getValue().toArray(paramArrayOfT);
	}

	/**
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public boolean add(T paramE) {
		return getValue().add(paramE);
	}

	/**
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object paramObject) {
		return getValue().remove(paramObject);
	}

	/**
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> paramCollection) {
		return getValue().containsAll(paramCollection);
	}

	/**
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends T> paramCollection) {
		return getValue().addAll(paramCollection);
	}

	/**
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> paramCollection) {
		return getValue().removeAll(paramCollection);
	}

	/**
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> paramCollection) {
		return getValue().retainAll(paramCollection);
	}

	/**
	 * @see java.util.Collection#clear()
	 */
	@Override
	public void clear() {
		getValue().clear();
	}
	/**
	 * @see com.niton.media.json.basic.JsonValue#readNext(com.niton.media.json.io.StringInputStream)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void readNext(StringInputStream sis) throws IOException {
		setValue(new ArrayList<>());
		JsonValue<?> last = null;
		while(sis.hasNext()) {
			char c = sis.readChar();
//			if(c == JsonType.ARRAY.getOpenToken()) {
//				setValue(new ArrayList<>());
//			}
			if(c == JsonType.ARRAY.getCloseToken()){
				if(last != null)
					add((T) last);
				return;
			}
			else if (c == ',') {
				add((T) last);
			}else {
				for(JsonType t : JsonType.values()) {
					if(t.getOpenToken() == c) {
						switch (t) {
						case ARRAY:
							last = new JsonArray<>();
							break;
						case OBJECT:
							last = new JsonObject();
							break;
						case STRING:
							last = new JsonString();
							break;
						}
						last.readNext(sis);
						break;
					}
				}
			}
		}
	}
}
