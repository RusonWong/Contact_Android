package com.sjtu.contact.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpRequestUtil {
	public static String doGet(String url,Map<String,String> params) throws IOException
	{
		String ret="";
		String paramStr = "";
		
		if(!url.startsWith("http://"))
		{
			url = "http://"+url;
		}
		
		if(params != null && params.size() != 0)
		{
			paramStr = "?";
			Iterator<Entry<String, String>> entrySet=params.entrySet().iterator();
			for(int i = 0; i < params.size(); i++)
			{
				Entry<String,String> entryItem = entrySet.next();
				String key = entryItem.getKey();
				String value = entryItem.getValue();
				
				paramStr += key + "=" + value;
				
				if(i != params.size() - 1)
				{
					paramStr += "&";
				}
			}
		}
		
		url = url + paramStr;
		Logger.log("GET: "+url);
		
		URL getUrl = new URL(url); 
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection(); 
		connection.connect(); 
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 

        String line; 
        line = reader.readLine();
        ret = line;
        while ((line = reader.readLine()) != null) { 
                ret +="\n"+line;
        } 
        reader.close(); 
        
       // Logger.log("content of '"+url+"':\n"+ret);
		// ¶Ï¿ªÁ¬½Ó 
        connection.disconnect(); 
		return ret;
	}
	
	public static String doPost(String url,Map<String,String> params) throws IOException
	{
		String ret="";
		String paramStr = "";
		
		if(!url.startsWith("http://"))
		{
			url = "http://"+url;
		}
		
		if(params != null && params.size() != 0)
		{
			Iterator<Entry<String, String>> entrySet=params.entrySet().iterator();
			for(int i = 0; i < params.size(); i++)
			{
				Entry<String,String> entryItem = entrySet.next();
				String key = entryItem.getKey();
				String value = entryItem.getValue();
				
				paramStr += key + "=" + value;
				
				if(i != params.size() - 1)
				{
					paramStr += "&";
				}
			}
		}
		
		Logger.log("POST: "+url);
		Logger.log("params: "+paramStr);
		URL postUrl = new URL(url); 
		HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
		connection.setDoOutput(true);
        //connection.setDoInput(true);
        connection.setRequestMethod("POST"); 
       // connection.setUseCaches(false); 
       // connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", " application/x-www-form-urlencoded "); 
        connection.connect(); 
        
        // OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "8859_1"); 
        DataOutputStream out = new DataOutputStream(connection.getOutputStream()); 
        out.writeBytes(paramStr);
        out.flush(); 
        out.close();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
        String line; 
        line = reader.readLine();
        ret = line;
        while ((line = reader.readLine()) != null) { 
                ret +="\n"+line;
        } 
        reader.close(); 
        
       // Logger.log("content of '"+url+"':\n"+ret);
        connection.disconnect();
        return ret;
	}
	
	 public static void main(String[] args) { 
		 
		 Logger.log("start...");
         try { 
        	 	Logger.log("start....");
        	 	HashMap<String,String> params = new HashMap<String, String>();
        	 	params.put("id", 4+"");
        	 	//String url="http://localhost:8080/iReservation/StadiumAdmin/StadiumAdmin!getCategories";
        	 	String url="localhost/base.php/contact/get";
        	 //	?categoryId=1&newSName=H&newName=ymq
        	// 	params.put("categoryId", 1+"");
        	//	params.put("newSName", "X");
        	//	params.put("newName", "YYY");
        	 	
        	 	//String url="http://localhost:8080/iReservation/updateCategory";
        	 	String content = doGet(url,params);
        	 	System.out.println(content);
        	 	Map retMap = JSONUtil.getMapFromJson(content);
        	 	Logger.log("map size is :"+retMap.size());

         } catch (IOException e) {

                 e.printStackTrace(); 
         } 

  } 
}
