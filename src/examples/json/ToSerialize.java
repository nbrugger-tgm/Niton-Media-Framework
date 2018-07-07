package examples.json;

import java.util.HashMap;

import com.niton.media.State;
import com.niton.media.crypt.Cluster;

/**
 * This is the ToSerialize Class
 * 
 * @author Nils
 * @version 2018-07-07
 */
public class ToSerialize {
	private int number = 238;
	private Object[] objectArray = new Object[] { 123, new int[] { 1, 2, 3 }, State.PLAYING };
	private HashMap<String, Object> map = new HashMap<>();

	public ToSerialize() {
		map.put("key1", State.NOT_RUNNING);
		map.put("key2", new Cluster(2, (byte) 2));
	}
}
