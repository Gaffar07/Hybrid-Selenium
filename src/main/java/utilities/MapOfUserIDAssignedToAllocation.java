package utilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public final class MapOfUserIDAssignedToAllocation {

	public static Map<String, String> listOfUserIDForExecution=null;

	private MapOfUserIDAssignedToAllocation() throws Exception {
		if(listOfUserIDForExecution==null)
		MapOfUserIDAssignedToAllocation.listOfUserIDForExecution =Collections.synchronizedMap(MapOfUserID(ConfigReader.getInstance().getValue("MyuserId").trim().split(",")));
	}
	
	public static MapOfUserIDAssignedToAllocation getInstance() throws Exception{
		return new MapOfUserIDAssignedToAllocation();
		
	}

	private HashMap<String,String> MapOfUserID(String[] arrayOfString) {
		HashMap<String,String> toReturn = new HashMap<String,String>();
		for (String s : arrayOfString) {
			toReturn.put(s,"Free");
		}
		return toReturn;
	}
}