package com.akshay.accessor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.akshay.flickrimages.ImageViewActicity;

import android.os.AsyncTask;
import android.util.Log;

public class FlickrDataAccessor extends AsyncTask<String, Void, JSONObject> {

	static final String DEBUG_TAG = "FlickrDataAccessor"; 
	ImageViewActicity act;
	
	public FlickrDataAccessor(ImageViewActicity activity) {
		act = activity;
	}
	
	@Override
	protected JSONObject doInBackground(String... params) {

		InputStream inputStream = null;
		JSONObject jsonObject = null;
		String jsonString = "";
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			//Take the URL and create a HttpPost
			HttpPost httppost = new HttpPost(params[0]);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
		}
		catch(Exception e)
		{
			Log.e(DEBUG_TAG, "Connection Error " + e.toString());
		}
    	//Convert Response
    	try
    	{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
	        String stringBuilder = "";
	        String line = null;
	        while ((line = reader.readLine()) != null) 
	        {
	        	stringBuilder += line;
	        }
	        inputStream.close();
	        if(!stringBuilder.toString().equalsIgnoreCase("null\t"))
	        {
	        	jsonString = stringBuilder.toString();
	        }
    	}
    	catch(Exception e)
    	{
    		Log.e(DEBUG_TAG, "Converting Error " + e.toString());
    	}
		//Parse the String to JSON Object
		try
		{
			jsonObject = new JSONObject(jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}") + 1));
		}
		catch(JSONException e)
		{
			Log.e(DEBUG_TAG, "Error parsing data " + e.toString());
		}
		return jsonObject;
	}
	
	@Override
    protected void onPostExecute(JSONObject jsonObject) 
    {
		super.onPostExecute(jsonObject);
		//Log.e(DEBUG_TAG, "onpostExecute");
		act.handleJSONData(jsonObject);
    }

}
