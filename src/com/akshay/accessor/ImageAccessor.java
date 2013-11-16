package com.akshay.accessor;


import java.io.InputStream;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.akshay.flickrimages.ImageViewActicity;
import com.akshay.util.AppConstants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class ImageAccessor extends AsyncTask<String, Void, Bitmap>{

	private static final String DEBUG_TAG = "ImageAccessor"; 
	ImageViewActicity act;
	
	public ImageAccessor(ImageViewActicity activity) {
		act = activity;
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		InputStream inputStream = null;
		Bitmap img = null; 
		
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
			img = BitmapFactory.decodeStream(inputStream);

		}
		catch(Exception e)
		{
			Log.e(DEBUG_TAG, "Connection Error " + e.toString());
		}
		return img;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		act.getImages(result,AppConstants.IMAGE_COUNT);
		AppConstants.IMAGE_COUNT++;
	}
	
	

}
