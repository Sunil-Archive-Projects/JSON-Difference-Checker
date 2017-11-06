package sunil.json.diffchecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;


public class App 
{
    public static void main( String[] args ) throws JsonProcessingException, IOException
    {
    	int index = 0;
    	int uid_index = 0;
    	int slash_index = 0;
    	String diff_path = "";
    	String id_field = "_id";
    	
    	ObjectMapper jackson = new ObjectMapper();
    	
    	byte[] actual_json_file = Files.readAllBytes(Paths.get("./actual.json"));
    	byte[] expected_json_file = Files.readAllBytes(Paths.get("./expected.json")); 
    	
    	JsonNode actual_json = jackson.readTree(actual_json_file); 
    	JsonNode expected_json = jackson.readTree(expected_json_file);
    	
    	JsonNode diff_expectedValues = JsonDiff.asJson(actual_json, expected_json);
    	JsonNode diff_actualValues = JsonDiff.asJson(expected_json, actual_json);
	
    	//If there are no differences between the JSONs then Quit
    	if(diff_expectedValues.size() == 0)
    	{
    		System.out.println("Expected JSON is matching with Actual JSON");
    		return;
    	}
    	
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
    			System.out.println("Difference at Record Number : "+uid_index);
    			System.out.println(id_field+" : "+actual_json.get(uid_index).findValuesAsText(id_field).toString());    			
    		}
    		
    		System.out.println("\n");
    	}
    }
}
