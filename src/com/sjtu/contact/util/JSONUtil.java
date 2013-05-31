package com.sjtu.contact.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class JSONUtil {
		
	/** 
	* 从json HASH表达式中获取一个map，该map支持嵌套功能 
	* 形如：{"id" : "johncon", "name" : "小强"} 
	* 注意commons-collections版本，必须包含org.apache.commons.collections.map.MultiKeyMap 
	* @param object 
	* @return 
	 * @throws JSONException 
	*/ 
	public static Map getMapFromJson(String jsonString) {
		
		JSONTokener jsonParser = new JSONTokener(jsonString);
		
		Map map = new HashMap<String, Object>();
		
		try
		{
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
		
			map = JSONObj2Map(jsonObject);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return map;
	}
	
	
	public static ArrayList getListFromJson(String jsonString)
	{
		JSONTokener jsonParser = new JSONTokener(jsonString);
		
		ArrayList array = new ArrayList();
		
		try
		{
			JSONArray jsonArray = (JSONArray) jsonParser.nextValue();
		
			array = JSONArray2Array(jsonArray);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return array;
	}
	
	public static Map<String,Object> JSONObj2Map(JSONObject jsonObject)
	{
		Map map = new HashMap<String,Object>();

		try {
			for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
				String key = (String) iter.next();
				Object obj = jsonObject.get(key);
				if( obj instanceof JSONObject)
				{
					map.put(key, JSONObj2Map((JSONObject)obj));
				}
				else if ( obj instanceof JSONArray )
				{
					map.put(key, JSONArray2Array((JSONArray)obj));
				}
				else
				{
					map.put(key, obj);
				}
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}
	
	public static ArrayList<Object> JSONArray2Array(JSONArray jsonArray)
	{
		ArrayList<Object> array = new ArrayList<Object>();
		
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				Object obj = jsonArray.get(i);
				if(obj instanceof JSONArray )
				{
					array.add(JSONArray2Array((JSONArray)obj));
				}
				else if( obj instanceof JSONObject )
				{
					array.add(JSONObj2Map((JSONObject)obj));
				}
				else
				{
					array.add(obj);
				}
					
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return array;
		
	}
	
	
//	/** 
//     * 从json数组中得到相应java数组 
//     * json形如：["123", "456"] 
//     * @param jsonString 
//     * @return 
//     */ 
//    public static Object[] getObjectArrayFromJson(String jsonString) { 
//        JSONArray jsonArray = JSONArray.fromObject(jsonString); 
//        return jsonArray.toArray(); 
//    }
    
   	
   	
	public static void main(String[] args)
	{
		
	}
	
}
