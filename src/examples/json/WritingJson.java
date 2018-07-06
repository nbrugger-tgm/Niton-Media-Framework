package examples.json;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.niton.media.filesystem.NFile;
import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonValue;
import com.niton.media.json.io.JsonOutputStream;
import com.niton.media.json.types.JsonInt;

import examples.json.BuildingJsonObjects.Gender;

/**
 * This is the WritingJson Class
 * @author Nils
 * @version 2018-07-06
 */
public class WritingJson {
	public static void main(String[] args) {
		JsonValue<?> toWrite = getAnJsonObject();
		/*
		 * ^	^	^	^
		 * |	|	|	|
		 * We have a Json Value (does not matter what type or where it is from)
		 * 
		 * What we want to do is to write it into:
		 * -An String (in Java)
		 * -The Console
		 * -A File on The Desktop
		 * -A Server
		 */
		
		// Into String
		String output = toWrite.getJson();
		
		//The Console
		JsonOutputStream jos = new JsonOutputStream(System.out);
		try {
			jos.write(toWrite);
			//We have to catch an IOException if the Output Stream fails
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * RESULT:
			{
				"gender" : "MALE",
				"name" : "Nils",
				"age" : "16"
			}
		 */
		
		
		
		
		//An File
		try {
		NFile target = new NFile("D:","Desktop","me.json");
			//way 1
			target.write(toWrite);
			//way 2
			JsonOutputStream jos2 = new JsonOutputStream(target);
			jos2.write(toWrite);
			//I hope i dont need to explain way there are exceptions if you are playing with files
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//The Server
		try {
			Socket s = new Socket("localhost", 123);
			JsonOutputStream jos3 = new JsonOutputStream(s.getOutputStream());
			jos3.write(toWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * Now You know how to make A Json out Of your Datas
		 */
	}
	
	
	public static JsonObject getAnJsonObject() {
		JsonObject me = new JsonObject();
		me.add("name", "Nils");
		me.add("age", new JsonInt(16));
		me.add("gender", Gender.MALE);
		return me;
	}
}

