package com.desudesu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
public class NetworkHandler {
	
	public NetworkHandler(){
		
	}
	
	  public String getBoardJson(String sBoardLetter, int page) {
		    StringBuilder builder = new StringBuilder();
		    HttpClient client = new DefaultHttpClient();
		    HttpGet httpGet = new HttpGet("http://api.4chan.org/" + sBoardLetter + "/" + page + ".json");
		    try {
		      HttpResponse response = client.execute(httpGet);
		      StatusLine statusLine = response.getStatusLine();
		      int statusCode = statusLine.getStatusCode();
		      if (statusCode == 200) {
		        HttpEntity entity = response.getEntity();
		        InputStream content = entity.getContent();
		        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		        String line;
		        while ((line = reader.readLine()) != null) {
		          builder.append(line);
		        }
		      } else {
		        //Fail
		    	  Log.w("test","ERROR HERE");
		      }
		    } catch (ClientProtocolException e) {
		    	Log.w("test","ERROR HERE 2");
		      e.printStackTrace();
		    } catch (IOException e) {
		    	Log.w("test","ERROR HERE 3");
		      e.printStackTrace();
		    }
		    return builder.toString();
		  }
}
