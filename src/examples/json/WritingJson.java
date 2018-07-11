package examples.json;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.niton.media.filesystem.NFile;
import com.niton.media.json.basic.JsonArray;
import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.basic.JsonString;
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
		
		//The Console
		JsonOutputStream jos = new JsonOutputStream(System.out);
		try {
			jos.write(toWrite);
			//We have to catch an IOException if the Output Stream fails
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//An File
		try {
		NFile target = new NFile("D:","Desktop","me.json");
			//way 1
			target.write(toWrite);
			//way 2
			JsonOutputStream jos2 = new JsonOutputStream(target.getOutputStream());
			jos2.write(toWrite);
			//I hope i dont need to explain way there are exceptions if you are playing with files
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public static JsonObject getAnJsonObject() {
		JsonObject me = new JsonObject();
		me.add("name", "Nils");
		me.add("age", new JsonInt(16));
		me.add("gender", Gender.MALE);
		JsonArray<JsonString> languages = new JsonArray<>();
		languages.add(new JsonString("Java"));
		languages.add(new JsonString("German"));
		languages.add(new JsonString("Java Script"));
		languages.add(new JsonString("English"));
		
		me.add("languages", languages);
		return me;
	}
}

