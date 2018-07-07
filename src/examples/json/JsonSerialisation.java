package examples.json;

import java.io.IOException;

import com.niton.media.json.io.JsonInputStream;
import com.niton.media.json.io.JsonOutputStream;

/**
 * This is the JsonSerialisation Class
 * @author Nils
 * @version 2018-07-07
 */
public class JsonSerialisation {
	public static void main(String[] args) {
		JsonOutputStream jos = new JsonOutputStream(System.out);
		try {
			jos.write(new ToSerialize());
		} catch (InstantiationException | IllegalAccessException | IOException e) {
			e.printStackTrace();
		}
		//The Output is to big to output here
		
		
		JsonInputStream jis = new JsonInputStream(SOME_INPUT_SOURCE);
		ToSerialize serial = (ToSerialize) jis.readNextObject();
	}
	
}

