package com.akshay.flickrimages;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.akshay.accessor.FlickrDataAccessor;
import com.akshay.accessor.ImageAccessor;
import com.akshay.util.AppConstants;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

public class ImageViewActicity extends FragmentActivity implements
ActionBar.OnNavigationListener {

	final String DEBUG_TAG = "ImageViewActicity";

	static String URL;
	static ArrayList<Bitmap> m_images;
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_view);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		SpinnerAdapter adp = new ArrayAdapter<String>(actionBar.getThemedContext(),
				android.R.layout.simple_list_item_1,
				android.R.id.text1, new String[] {
			getString(R.string.grid_view),
			getString(R.string.full_screen) });

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(adp, this);

		URL = AppConstants.PHOTO_URL + "&api_key=" + AppConstants.API_KEY + "&per_page=" + AppConstants.PER_PAGE 
				+ "&page=" + AppConstants.PAGE + "&format=" + AppConstants.RESPONSE_FORMAT;

		if(m_images == null) {
			m_images = new ArrayList<Bitmap>();
			FlickrDataAccessor fdacc = new FlickrDataAccessor(this);
			fdacc.execute(URL);
		}

		//Log.e(DEBUG_TAG, "end on create");


	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}




	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		//Log.e(DEBUG_TAG, "onNavigationItemSelected " + position +  " " + id);
		switch (position) {
		case 0:
			Fragment fragment = new GridViewFragment();
			Bundle args = new Bundle();
			args.putParcelableArrayList("images", m_images);
			fragment.setArguments(args);
			getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
			break;
		case 1:
			Fragment fragment1 = new FullScreenFragment();
			Bundle args1 = new Bundle();
			args1.putParcelableArrayList("images", m_images);
			args1.putInt("position", AppConstants.GRID_IMAGE_POS);
			fragment1.setArguments(args1);
			getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
			break;
		}
		return true;
	}


	public void handleJSONData (JSONObject json) {
		//Log.e(DEBUG_TAG, "json  " + json);
		//http://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
		try {
			JSONObject jsonObj = json.getJSONObject("photos");
			AppConstants.TOTAL_PAGES = jsonObj.getInt("pages");
			JSONArray jsonArray = jsonObj.getJSONArray("photo");

			m_images.clear();
			AppConstants.IMAGE_COUNT = 0;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject child = jsonArray.getJSONObject(i);
				String image_url = "http://farm" + child.getString(AppConstants.FARM_ID)+ ".staticflickr.com/" 
						+ child.getString(AppConstants.SERVER_ID) + "/" +  child.getString(AppConstants.PHOTO_ID) 
						+ "_" + child.getString(AppConstants.SECRET) + ".jpg";
				//Log.e(DEBUG_TAG, "i=" + i + "  " + image_url );
				(new ImageAccessor(this)).execute(image_url);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void getImages (Bitmap bitmap,int count) {
		//Log.e(DEBUG_TAG, "in getImages " + count);
		m_images.add(count, bitmap);
		Fragment frg = getSupportFragmentManager().getFragments().get(0);
		if (frg instanceof GridViewFragment) {
			((GridViewFragment) frg).adapter.notifyDataSetChanged();
		} else {
			((FullScreenFragment) frg).adapter.notifyDataSetChanged();
		}
	}




}

