package com.akshay.flickrimages;

import com.akshay.adapters.FSImageAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FullScreenFragment extends Fragment {

	static final String DEBUG_TAG = "FullScreenFragment";
	ViewPager viewPager;
	FSImageAdapter adapter;
	int position;

	public FullScreenFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_full_screen_view, container,
				false);
		
		viewPager = (ViewPager) rootView.findViewById(R.id.pager);
		position = getArguments().getInt("position");
		Log.e(DEBUG_TAG, "pos " + position);
		adapter = new FSImageAdapter(getActivity(),getArguments().getParcelableArrayList("images"));
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(position);	
		return rootView;
	}

}

