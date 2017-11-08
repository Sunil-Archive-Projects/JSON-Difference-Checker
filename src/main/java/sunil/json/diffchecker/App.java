package sunil.json.diffchecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.flipkart.zjsonpatch.JsonDiff;

public class App 
{

	public static void JSONDifferenceChecker() throws IOException
	{
		int index = 0;
    	int uid_index = 0;
    	int slash_index = 0;
    	String diff_path = "";
    	String id_field = "encounterUID";
    	
    	ObjectMapper jackson = new ObjectMapper();
    	jackson.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

    	byte[] actual_json_file = Files.readAllBytes(Paths.get("./actual.json"));
    	byte[] expected_json_file = Files.readAllBytes(Paths.get("./expected.json")); 

    	JsonNode actual_json = jackson.readTree(actual_json_file); 
    	JsonNode expected_json = jackson.readTree(expected_json_file);
    	
    	actual_json = jackson.readTree(JSONRecordsSorter(actual_json.toString(), id_field));
    	expected_json = jackson.readTree(JSONRecordsSorter(expected_json.toString(), id_field));
        
    	JsonNode diff_expectedValues = JsonDiff.asJson(actual_json, expected_json);
    	JsonNode diff_actualValues = JsonDiff.asJson(expected_json, actual_json);
    	
    	System.out.println("\n --------------------JSON Compare RESULTS--------------------------- \n");
	
    	//If there are no differences between the JSONs then Quit
    	if(diff_expectedValues.size() == 0)
    	{
    		System.out.println("Expected JSON is matching with Actual JSON");
    		return;
    	}
    	
    	if(actual_json.size() != expected_json.size())
    	{
    		System.out.println("The Number of Records between the Actual and the Expected JSONs are not matching.");
    		System.out.println("Expected JSON has " + expected_json.size() + " records.");
    		System.out.println("Actual JSON has " + actual_json.size() + " records.");
    	}
    	
    	System.out.println("Expected JSON is NOT matching with the Actual JSON\n");
    	
    	//print UID and the differences between the JSONs
    	for(index = 0; index < diff_expectedValues.size() ; index++)
    	{
    		diff_path = diff_expectedValues.get(index).findValuesAsText("path").toString();
    		System.out.println("Actual : " + diff_actualValues.get(index));
    		System.out.println("Expected : " + diff_expectedValues.get(index));
    		
    		diff_path = diff_path.substring(2, diff_path.length());
    		slash_index = diff_path.indexOf("/");
    		if (slash_index != -1)
    		{
    			uid_index = Integer.parseInt(diff_path.substring(0,slash_index));
    			System.out.println(id_field+" : "+actual_json.get(uid_index).findValuesAsText(id_field).toString());
    		}
    		
    		System.out.println("\n");
    	}
    	
    	System.out.println("\n ------------------------------------------------------------------- \n");

	}
	
	public static String JSONRecordsSorter(String JSONString, final String key)
	{
 	    JSONArray jsonArray = new JSONArray(JSONString);
 	    JSONArray sortedJsonArray = new JSONArray();
 	    
 	    List<JSONObject> jsonValues = new ArrayList<JSONObject>();
 	    for (int i = 0; i < jsonArray.length(); i++) 
 	    {
 	        jsonValues.add(jsonArray.getJSONObject(i));
 	    }
 	    
 	    Collections.sort( jsonValues, new Comparator<JSONObject>() 
 	    {
 	        public int compare(JSONObject a, JSONObject b) 
 	        {
 	            String valA = new String();
 	            String valB = new String();
 	            try 
 	            {
 	                valA = (String) a.get(key);
 	                valB = (String) b.get(key);
 	            } 
 	            catch (JSONException e) 
 	            {
 	                System.out.println(e.toString());
 	            }
 	            return valA.compareTo(valB);
 	        }
 	    });
 	    

 	    for (int i = 0; i < jsonArray.length(); i++) 
 	    {
 	        sortedJsonArray.put(jsonValues.get(i));
 	    }
 	    
 	    
		return sortedJsonArray.toString();
		
	}
	
    public static void main( String[] args ) throws JsonProcessingException, IOException
    {
    	JSONDifferenceChecker();

    }
	
}