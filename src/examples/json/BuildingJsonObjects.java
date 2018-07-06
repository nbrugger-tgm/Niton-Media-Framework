package examples.json;

import com.niton.media.json.basic.JsonObject;
import com.niton.media.json.types.JsonInt;

/**
 * This is the BuildingJson Class
 * @author Nils
 * @version 2018-07-06
 */
public class BuildingJsonObjects {
	public static void main(String[] args) {
		/*
		 * The Information we want to store is :   age = 16, gender = male, name = Nils
		 */
		
		
		//The top level container is an Object
		JsonObject me = new JsonObject();
		//fill in the data with an String - EEEAAASSYYY
		me.add("name", "Nils");
		//fill in the age (as Number)
		me.add("age", new JsonInt(16));
		//and the Gender is an Enum
		me.add("gender", Gender.MALE);
		
		//Now we have an Json Object. How to deal with it read in Writing JSONS
		
		
	}
	
	enum Gender{
		MALE,
		FEMALE
	}
}

