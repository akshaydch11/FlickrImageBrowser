package com.akshay.flickrimages;

import com.akshay.accessor.FlickrDataAccessor;
import com.akshay.adapters.GVImageAdapter;
import com.akshay.util.AppConstants;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;

public class GridViewFragment extends Fragment{

	GridView gridView;
	GVImageAdapter adapter;
	private int columnWidth;
	static final String DEBUG_TAG = "GridViewFragment"; 
	
	// Number of columns of Grid View
	public static final int NUM_OF_COLUMNS = 3;

	// Gridview image padding
	public static final int GRID_PADDING = 8; 

	public GridViewFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate( R.layout.fragment_grid_view, container,
				false);

		gridView = (GridView) rootView.findViewById(R.id.grid_view);
		InitilizeGridLayout();

		adapter = new GVImageAdapter((ImageViewActicity)getActivity(), getArguments().getParcelableArrayList("images"), columnWidth);
		gridView.setAdapter(adapter);

		
		this.setHasOptionsMenu(true);
		AppConstants.GRID_IMAGE_POS = 0;
		//Log.e(DEBUG_TAG, "onCreateView");
		return rootView;
	}

	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((getScreenWidth() - ((NUM_OF_COLUMNS + 1) * padding)) / NUM_OF_COLUMNS);

		gridView.setNumColumns(NUM_OF_COLUMNS);
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}

	public int getScreenWidth() {
		int columnWidth;
		WindowManager wm = (WindowManager) getActivity().getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		final Point point = new Point();
		display.getSize(point);
		columnWidth = point.x;
		return columnWidth;
	}

	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		//Log.e(DEBUG_TAG, "onCreateoptions");
		getActivity().getMenuInflater().inflate(R.menu.image_view_activity, menu);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		//Log.e(DEBUG_TAG, "onPrepareOptionsMenu");
		menu.getItem(0).setVisible(true);
		menu.getItem(1).setVisible(true);
		menu.getItem(2).setVisible(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//Log.e(DEBUG_TAG, "onOptionsItemSelected");
				//Log.e(DEBUG_TAG, "onOptionsItemSelected");
		FlickrDataAccessor fdacc = new FlickrDataAccessor((ImageViewActicity)getActivity());
		switch(item.getItemId()) {
		case R.id.action_refresh:
			fdacc.execute(ImageViewActicity.URL);
			break;
		case R.id.action_next:
			if (AppConstants.PAGE < AppConstants.TOTAL_PAGES - 1) {
				AppConstants.PAGE = AppConstants.PAGE + 1;
				ImageViewActicity.URL = AppConstants.PHOTO_URL + "&api_key=" 
				+ AppConstants.API_KEY + "&per_page=" + AppConstants.PER_PAGE 
				+ "&page=" + AppConstants.PAGE + "&format=" + AppConstants.RESPONSE_FORMAT;
				fdacc.execute(ImageViewActicity.URL);
			}
			break;
		case R.id.action_prev:
			if (!(AppConstants.PAGE == 1) && AppConstants.PAGE < AppConstants.TOTAL_PAGES-1){
				AppConstants.PAGE = AppConstants.PAGE - 1;
				ImageViewActicity.URL = AppConstants.PHOTO_URL + "&api_key=" 
				+ AppConstants.API_KEY + "&per_page=" + AppConstants.PER_PAGE 
				+ "&page=" + AppConstants.PAGE + "&format=" + AppConstants.RESPONSE_FORMAT;
				fdacc.execute(ImageViewActicity.URL);
			}
			break;
		default:
			break;
		}
		return true;
	}
	
}
