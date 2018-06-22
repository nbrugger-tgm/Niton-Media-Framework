package com.niton.media.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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

	/**
	 * Creates an Instance of JsonArray.java
	 * 
	 * @author Nils
	 * @version 2018-06-08
	 */
	public JsonArray() {
		setValue(new ArrayList<>());
	}

	/**
	 * @see com.niton.media.json.JsonValue#getJson()
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

	public final static Object[] getArray(Object val) {
		if (val instanceof Object[])
			return (Object[]) val;
		int arrlength = Array.getLength(val);
		Object[] outputArray = new Object[arrlength];
		for (int i = 0; i < arrlength; ++i) {
			outputArray[i] = Array.get(val, i);
		}
		return outputArray;
	}

	private enum PARSE {
		NUMBER, STRING, ARRAY, OBJECT, BOOLEAN, ENUM;
	}

	/**
	 * Description :
	 * 
	 * @author Nils
	 * @version 2018-06-10
	 * @param value
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public void read(Object value) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		PARSE p;
		Class<?> c = value.getClass().getComponentType();
		if (c.equals(boolean.class) || c.equals(Boolean.class))
			p = PARSE.BOOLEAN;
		else if (c.isEnum())
			p = PARSE.ENUM;
		else if (c.isPrimitive() || c.getSuperclass().equals(Number.class) || c.equals(Character.class))
			p = PARSE.NUMBER;
		else if (c.equals(String.class))
			p = PARSE.STRING;
		else if (c.isArray())
			p = PARSE.ARRAY;
		else
			p = PARSE.OBJECT;
		for (int i = 0; i < getValue().size(); i++) {
			Object subVallue = null;
			JsonValue<?> val = getValue().get(i);
			if (!(val instanceof JsonString && val.getValue().equals("null") && !c.equals(String.class)))

				switch (p) {
				case ARRAY:
					JsonArray<?> array = (JsonArray<?>) val;
					subVallue = Array.newInstance(c.getComponentType(), array.size());
					array.read(subVallue);
					break;
				case NUMBER:
					JsonNumber number;
					if (val instanceof JsonNumber)
						number = (JsonNumber) val;
					else
						number = new JsonNumber(Double.parseDouble((String) val.getValue()));
					if (c.isPrimitive())
						c = Array.get(Array.newInstance(c, 1), 0).getClass();
					if (!c.getName().contains("Integer"))
						subVallue = Double.class.getMethod(c.getSimpleName().toLowerCase() + "Value")
								.invoke(number.getValue());
					else
						subVallue = number.getValue().intValue();
					break;
				case OBJECT:
					JsonObject ob = (JsonObject) val;
					ob.read(subVallue);
					break;
				case STRING:
					subVallue = val.getValue();
					break;
				case BOOLEAN:
					subVallue = Boolean.parseBoolean((String) val.getValue());
					break;
				case ENUM:
					for (Object o : c.getEnumConstants()) {
						if (((Enum<?>) o).name().equals(val.getValue())) {
							subVallue = o;
							break;
						}
					}
					subVallue = null;

					break;
				default:
					break;
				}

			Array.set(value, i, subVallue);
		}
	}
}
